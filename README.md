# 👤 Member Microservice

## 📋 Descripción

Microservicio para la gestión de miembros del gimnasio (Puerto 8081). Permite registrar nuevos miembros, actualizar su información y realizar consultas sobre la membresía.

## 🔗 Endpoints

### 🙋‍♂️ Gestión de Miembros

- `POST /api/members` - Registrar un nuevo miembro
- `GET /api/members` - Obtener todos los miembros
- `GET /api/members/{id}` - Obtener miembro por ID
- `PUT /api/members/{id}` - Actualizar información de un miembro
- `DELETE /api/members/{id}` - Eliminar un miembro

### 🔍 Consultas Específicas

- `GET /api/members/email/{email}` - Buscar miembro por email

## 🛠️ Tecnologías

- Spring Boot
- Spring Data JPA
- H2 Database (en memoria)
- Eureka Client
