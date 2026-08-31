# school-microservicios

Sistema escolar implementado con una arquitectura de microservicios: **Spring Boot 4** + **Spring Cloud 2025**, descubrimiento con **Eureka**, **API Gateway**, configuración centralizada con **Spring Cloud Config** (backend git), **Spring Boot Admin**, bases **H2** y documentación con **OpenAPI/Swagger**.

## Arquitectura

```
                    ┌─────────────────────┐
   navegador ─────► │  Gateway  (8080)     │
                    └──┬───────┬───────┬──┘
                       │       │       │
              ┌────────▼──┐ ┌──▼────────┐ ┌────────▼──┐
              │ MS-ALUMNOS │ │ MS-CURSOS │ │ MS-ADMINIS.│
              │  Alumno    │ │  Cursos   │ │ Docentes   │
              │  (8082)    │ │  (8081)   │ │  (8083)    │
              └────────────┘ └───────────┘ └────────────┘
                       │       │       │
              ┌────────▼───────▼───────▼──────┐
              │   Eureka Registry   (8761)     │  registro/servicios
              └───────────────────────────────┘
              ┌───────────────────────────────┐
              │  Spring Cloud Config (8888)    │  yml desde GitHub
              └───────────────────────────────┘
              ┌───────────────────────────────┐
              │  Spring Boot Admin   (8084)    │  monitoreo
              └───────────────────────────────┘
```

Todos los microservicios se conectan a **una sola base H2 compartida** y declaran sus entidades JPA **dentro de cada microservicio** (sin módulo `escuela-model`), con estas relaciones:

- `Docente` `1 ── * (cursos)` con `@OneToMany`
- `Cursos` `* ── 1 Docente` (`@ManyToOne`) y `1 ── * (alumnos)` con `@OneToMany`
- `Alumno` `* ── 1 Cursos` (`@ManyToOne`)

Cada servicio incluye en su paquete `domain` las entidades que necesita para mapear la base compartida:

| Microservicio | Entidades en su `domain` |
|---|---|
| `ms-alumnos` | `Alumno` (con `@ManyToOne` a `Cursos`) y `Cursos` |
| `ms-cursos` | `Cursos` (`@ManyToOne` a `Docente`, `@OneToMany` a `Alumno`), `Docente` y `Alumno` |
| `ms-administracion` | `Docente` (con `@OneToMany` a `Cursos`) y `Cursos` |

Las columnas de relación crean el vínculo por id (p. ej. `curso_id`, `docente_id`); no crean constraint FK en base (`@ForeignKey(ConstraintMode.NO_CONSTRAINT)`) y los `@ManyToOne` son `LAZY`, para que cada aplicación pueda arrancar y crear sus propias tablas sobre la misma base.

## Repositorios

| Repo | Rol | Acceso |
|---|---|---|
| `OscarFlores18/school-microservicios` | Código de los microservicios (este) | lectura/escritura |
| `OscarFlores18/school-config` | Configuración centralizada (yml por servicio) | solo lectura para los clientes (escritura: dueño) |

## Módulos y puertos

| Módulo | `spring.application.name` | Puerto | Yml que lee de `school-config` |
|---|---|---|---|
| `registry` (Eureka Server) | `eurekaserver` | 8761 | `application.yml` (compartido) |
| `configserver` | `configserver` | 8888 | — (es el servidor de config; backend git) |
| `gateway` | `gateway` | 8080 | `gateway.yml` |
| `adminservice` | `adminservice` | 8084 | `application.yml` (compartido) |
| `Alumno` | `ms-alumnos` | 8082 | `ms-alumnos.yml` |
| `Cursos` | `ms-cursos` | 8081 | `ms-cursos.yml` (+ datasource local) |
| `Administracion` | `ms-administracion` | 8083 | `ms-administracion.yml` |

Cada módulo importa su configuración desde el config server (`spring.config.import: configserver:http://localhost:8888`), que a su vez la lee del repositorio `school-config` (rama `main`).

## Stack / requisitos

- **Java 17** (obligatorio)
- **Git** y **acceso a internet**:
  - el primer build baja las dependencias de Maven Central;
  - el `configserver`, al arrancar, clona en memoria el repositorio público `school-config` desde GitHub.
- **Maven**: cada módulo incluye su wrapper (`mvnw`) — no es necesario instalarlo. La raíz del proyecto es un agregador (`pom.xml`) que compila los 7 módulos.

## Cómo correrlo

### Opción A: Eclipse
1. `git clone https://github.com/OscarFlores18/school-microservicios.git`
2. Importar como *Maven → Existing Maven Projects* los 7 módulos.
3. Correr los `*Application.java` en **este orden**:
   `registry` → `configserver` → `gateway`, `adminservice`, `Alumno`, `Cursos`, `Administracion`.

### Opción B: línea de comandos
```bash
git clone https://github.com/OscarFlores18/school-microservicios.git
cd school-microservicios
# compilar todo
mvn -f pom.xml clean package -DskipTests
# luego, en orden (o desde Eclipse):
java -jar registry/target/registry-0.0.1-SNAPSHOT.war
java -jar configserver/target/configserver-0.0.1-SNAPSHOT.jar
# ... y después el resto
```

> El `configserver` debe estar arriba antes que `gateway` y `Alumno` (importan la configuración de forma no opcional). Los tres microservicios deben apuntar (vía `school-config`) a **una sola base H2** (p. ej. `jdbc:h2:file:./data/escuela`), que se crea sola y vacía al arrancar el primero.

## Endpoints y URLs

**API vía Gateway (`http://localhost:8080`):**

| Ruta | Servicio destino |
|---|---|
| `/api/docentes/**` | MS-ADMINISTRACION |
| `/api/cursos/**` | MS-CURSOS |
| `/api/alumnos/**` | MS-ALUMNOS |

**Swagger / documentación:**

- `http://localhost:8080/swagger-all.html` → página unificada con los 3 swaggers
- `http://localhost:8080/alumnos/swagger-ui/index.html`
- `http://localhost:8080/cursos/swagger-ui/index.html`
- `http://localhost:8080/administracion/swagger-ui/index.html`
- `http://localhost:8080/{alumnos|cursos|administracion}/v3/api-docs`
- Swagger directo por servicio: `http://localhost:808X/swagger-ui/index.html` y `/v3/api-docs`

**Paneles:**

- Eureka: `http://localhost:8761`
- Spring Boot Admin: `http://localhost:8084`
- Consolas H2 (user `sa`, sin password) — las 3 abren la **misma base compartida**:
  - `http://localhost:8082/h2-console` (Alumno)
  - `http://localhost:8081/h2-console` (Cursos)
  - `http://localhost:8083/h2-console` (Administracion)
- Config server: `http://localhost:8888/{gateway|ms-alumnos|ms-cursos|ms-administracion}/default`

## Cargar datos de ejemplo

Las bases arrancan vacías; crearlos por la API (vía Gateway):

```bash
curl -X POST http://localhost:8080/api/docentes -H "Content-Type: application/json" -d '{"nombre":"Ana","apellido":"Diaz","email":"ana@escuela.com","especialidad":"Lengua"}'

curl -X POST http://localhost:8080/api/cursos -H "Content-Type: application/json" -d '{"nombre":"Lengua 1","descripcion":"Gramática","curso":"Primero","horario":"Martes 8-10","id_docente":1}'

curl -X POST http://localhost:8080/api/alumnos -H "Content-Type: application/json" -d '{"nombre":"Juan","apellido":"Perez","email":"juan@escuela.com","dni":"30111222","fechaNacimiento":"2010-05-04","cursoId":1}'
```

## Notas y limitaciones

- Las entidades viven **dentro de cada microservicio** y los **tres** apuntan a **una sola base H2** (configurada en `school-config`). Con eso, las relaciones `@OneToMany`/`@ManyToOne` declaradas enlazan filas reales de la misma base y las consultas que unen entidades entre servicios (p. ej., alumnos por nombre de curso, cursos por docente) funcionan.
- Las columnas de relación solo guardan el id (`curso_id`, `docente_id`): no crean constraint FK en base (`@ForeignKey(ConstraintMode.NO_CONSTRAINT)`) y los `@ManyToOne` son `LAZY`, para que cada aplicación pueda arrancar y crear sus propias tablas sobre la misma base.
- `school-config` es de solo lectura para los microservicios; su contenido lo actualiza el dueño del repositorio.