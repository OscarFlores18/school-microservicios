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

Todos los servicios comparten las entidades JPA del módulo `escuela-model`:

- `Docente` `1 ── * (cursos)` con `@OneToMany`
- `Cursos` `* ── 1 Docente` (`@ManyToOne`) y `1 ── * (alumnos)` con `@OneToMany`
- `Alumno` `* ── 1 Cursos` (`@ManyToOne`)

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
| `escuela-model` | — (librería) | — | — |

Cada módulo importa su configuración desde el config server (`spring.config.import: configserver:http://localhost:8888`), que a su vez la lee del repositorio `school-config` (rama `main`).

## Stack / requisitos

- **Java 17** (obligatorio)
- **Git** y **acceso a internet**:
  - el primer build baja las dependencias de Maven Central;
  - el `configserver`, al arrancar, clona en memoria el repositorio público `school-config` desde GitHub.
- **Maven**: cada módulo incluye su wrapper (`mvnw`) — no es necesario instalarlo. La raíz del proyecto es un agregador (`pom.xml`) que compila los 8 módulos.

## Cómo correrlo

### Opción A: Eclipse
1. `git clone https://github.com/OscarFlores18/school-microservicios.git`
2. Importar como *Maven → Existing Maven Projects* los 8 módulos.
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

> El `configserver` debe estar arriba antes que `gateway` y `Alumno` (importan la configuración de forma no opcional). Las bases H2 se crean solas, vacías, en la carpeta `data/` del directorio de trabajo de cada módulo.

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
- Consolas H2 (user `sa`, sin password):
  - `http://localhost:8082/h2-console` (Alumno → `jdbc:h2:file:./data/alumnos`)
  - `http://localhost:8081/h2-console` (Cursos → `jdbc:h2:file:./data/cursos`)
  - `http://localhost:8083/h2-console` (Administracion → `jdbc:h2:file:./data/administracion`)
- Config server: `http://localhost:8888/{gateway|ms-alumnos|ms-cursos|ms-administracion}/default`

## Cargar datos de ejemplo

Las bases arrancan vacías; crearlos por la API (vía Gateway):

```bash
curl -X POST http://localhost:8080/api/docentes -H "Content-Type: application/json" -d '{"nombre":"Ana","apellido":"Diaz","email":"ana@escuela.com","especialidad":"Lengua"}'

curl -X POST http://localhost:8080/api/cursos -H "Content-Type: application/json" -d '{"nombre":"Lengua 1","descripcion":"Gramática","curso":"Primero","horario":"Martes 8-10","id_docente":1}'

curl -X POST http://localhost:8080/api/alumnos -H "Content-Type: application/json" -d '{"nombre":"Juan","apellido":"Perez","email":"juan@escuela.com","dni":"30111222","fechaNacimiento":"2010-05-04","cursoId":1}'
```

## Notas y limitaciones

- Cada microservicio usa **su propia base H2** (configurada en `school-config`). Las relaciones `@OneToMany`/`@ManyToOne` están declaradas y enlazadas por id, pero las consultas que unen entidades entre servicios (p. ej., alumnos por nombre de curso) devuelven vacío, porque los datos viven en bases distintas. Apuntando los `ms-*.yml` de `school-config` a una sola base, todo funciona sin cambios de código.
- Las columnas de relación no crean constraint FK en base (`@ForeignKey(ConstraintMode.NO_CONSTRAINT)`) y los `@ManyToOne` son `LAZY`, para que el alta cruzada funcione con bases separadas.
- `school-config` es de solo lectura para los microservicios; su contenido lo actualiza el dueño del repositorio.