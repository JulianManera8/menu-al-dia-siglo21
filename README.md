# Menu Al Día — Prototipo AP2

Prototipo académico para la materia **Seminario EFIP**.

El sistema representa la gestión del menú diario de un único bar. El alcance del prototipo incluye algunas funcionalidades operacionales del sistema final, de acuerdo con la definición de prototipo utilizada en la actividad.

## Funcionalidades implementadas

- Registrar platos.
- Crear/consultar el menú de una fecha.
- Agregar platos al menú.
- Definir el precio del día.
- Marcar un plato como disponible o agotado.
- Publicar el menú.
- Persistir la información en MySQL.

No se implementa autenticación, ya que el alcance contempla un único operador interno.

## Tecnologías

- Java 17
- Spring Boot 3
- Spring MVC
- Thymeleaf
- Spring Data JPA / Hibernate
- MySQL
- Maven

## Estructura

```text
src/main/java/com/menualdia/
├── controller/
├── model/
├── repository/
├── service/
└── MenuAlDiaApplication.java

src/main/resources/
├── templates/
├── static/
└── application.properties

database/
├── schema.sql
└── consultas.sql
```

## Puesta en marcha

### 1. Crear la base de datos

Ejecutar:

```bash
mysql -u root -p < database/schema.sql
```

También puede ejecutarse `database/schema.sql` desde MySQL Workbench.

### 2. Configurar credenciales

La aplicación acepta las siguientes variables de entorno:

```bash
DB_USER=root
DB_PASSWORD=tu_password
DB_URL=jdbc:mysql://localhost:3306/menu_al_dia?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
```

Si no se define `DB_URL`, se utiliza la URL local incluida en `application.properties`.

### 3. Ejecutar

```bash
mvn spring-boot:run
```

Luego abrir:

```text
http://localhost:8080
```

## Consultas SQL

El archivo `database/consultas.sql` incluye ejemplos de:

- `INSERT`
- `SELECT`
- `UPDATE`
- `DELETE`
- consultas con `JOIN`

## Alcance académico

Este repositorio corresponde a un **prototipo operacional** y no pretende implementar la totalidad del sistema final. Se priorizaron los casos de uso principales para demostrar la integración entre Java, la lógica de negocio y MySQL.
