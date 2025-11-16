# Trabajo Integrador Final – Programación II
### Sistema de Gestión de Usuarios con Credenciales de Acceso
Java + JDBC + MySQL + DAO + Service + Transacciones + AppMenu

##  Descripción General

Este proyecto implementa una aplicación de consola en Java para gestionar **Usuarios** y sus **Credenciales de Acceso**, siguiendo una arquitectura en capas y cumpliendo estrictamente con:

- Programación orientada a objetos  
- Patrón **DAO**  
- Capa **Service** con **transacciones JDBC**  
- Relación **1 → 1 unidireccional** (Usuario conoce a Credencial)  
- Persistencia en **MySQL** con scripts SQL  
- Menú de consola para operaciones CRUD completas  

##  Arquitectura

El proyecto sigue una arquitectura en capas:

```
src/main/java/
├── config/          
├── entities/        
├── dao/             
│   └── jdbc/        
├── service/         
└── main/            
```

Cada capa tiene responsabilidades claras y desacopladas.

##  Modelo de Dominio

Relación 1 → 1 unidireccional:
**Usuario conoce a CredencialAcceso**, pero la credencial solo almacena el ID del usuario.

##  Diagrama UML

La imagen del diagrama UML está incluida en el repositorio (generado en https://plantuml.com/es/starting):

```
UML.png
```

##  Base de Datos

Incluye:

- `schema.sql`: creación de tablas, claves primarias, únicas y foráneas
- `data.sql`: datos de prueba

Relación 1→1 recomendada usando:

```
usuario_id BIGINT UNIQUE,
FOREIGN KEY (usuario_id) REFERENCES usuario(id)
```

##  Persistencia con JDBC

`DatabaseConnection`:

- Lee `db.properties`
- Registra el JDBC driver
- Devuelve la conexión con `DriverManager`

## Patrón DAO

`GenericDao<T>` define CRUD:

- crear
- leer
- leerTodos
- actualizar
- eliminar

Implementaciones:

- `UsuarioDaoImpl`
- `CredencialAccesoDaoImpl`

Todas usan PreparedStatement y reciben una Connection externa.

##  Capa Service con Transacciones

Los servicios:

- Crean conexiones
- Desactivan autocommit
- Ejecutan operaciones múltiples
- Validan reglas de negocio
- Hacen commit o rollback
- Rehabilitan autocommit

##  Menú de Consola (AppMenu)

Incluye:

- CRUD completo de Usuarios
- CRUD completo de Credenciales
- Búsqueda por email
- Validación de entradas
- Manejo de excepciones
- Mensajes claros de resultado

El menú usa la capa Service.

## ▶ Cómo Ejecutar

### 1. Crear la base

Ejecutar:

```
scripts-sql/schema.sql
scripts-sql/data.sql
```

### 2. Configurar db.properties

```
db.url=jdbc:mysql://localhost:3306/app_usuarios
db.user=root
db.password=tu_clave
```

### 3. Ejecutar

Desde VS Code o consola:

```
java main.Main
```

##  Pruebas Sugeridas

1. Crear usuario + credencial  
2. Listar todos  
3. Buscar por email  
4. Actualizar usuario  
5. Probar duplicados de email  
6. Eliminar y verificar cascada  
7. Intentar crear segunda credencial para un usuario  

##  Explicación Técnica para el Video

- Diseño en capas  
- DAO manejan SQL  
- Services manejan transacciones  
- Menú solo muestra opciones  
- Implementación correcta de 1→1  
- Uso de baja lógica  
- Validaciones y excepciones  

##  Estado del Trabajo

| Consigna | Estado |
|---------|--------|
| UML | ✔ |
| Entidades | ✔ |
| Base y scripts SQL | ✔ |
| DAO | ✔ |
| Service | ✔ |
| Menú CRUD | ✔ |
| Búsqueda | ✔ |
| 1→1 | ✔ |

##  Autor
Joaquín Del Valle Lietti

## Link directo al video

https://www.youtube.com/watch?v=pbIaoniSk_Q

