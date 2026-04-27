# Sistema de Gestión de Productos e Inventario

## Autora

**Megan Ibagué**
Ingeniera de Sistemas

---

# Descripción del Proyecto

Este proyecto implementa una arquitectura basada en **microservicios** para la gestión de productos, inventario y compras.

El sistema está compuesto por:

*  **product-service** → Gestión de productos
*  **inventory-service** → Gestión de inventario y compras
*  **frontend (Quasar + Vue.js)** → Interfaz de usuario
*  **RabbitMQ** → Comunicación asíncrona
*  **PostgreSQL** → Base de datos
*  **Docker Compose** → Orquestación

---

# ️ Arquitectura

El sistema sigue una arquitectura de microservicios:

```
Frontend (Quasar)
        |
        ↓
 -------------------------
 |       APIs            |
 |                       |
 | product-service       |
 | inventory-service     |
 -------------------------
        |
        ↓
 PostgreSQL  +  RabbitMQ
```

---

#  Comunicación entre servicios

* Comunicación principal: **HTTP REST (JSON API)**
* Comunicación asíncrona: **RabbitMQ**
* Seguridad básica: **API Key**
* Resiliencia:

  * ️ Timeouts configurados
  *  Reintentos (Retry)

---

# ️ Tecnologías utilizadas

### Backend

* Java 17+
* Spring Boot
* Spring Security
* Spring Data JPA
* RestTemplate
* Resilience (Retry + Timeout)

### Frontend

* Vue.js 3
* Quasar Framework
* Axios

### Infraestructura

* Docker
* Docker Compose
* PostgreSQL
* RabbitMQ

---

#  Cómo levantar el proyecto

##  Requisitos

* Docker Desktop
* Docker Compose
* Node.js (solo si ejecutas frontend local sin Docker)

---

##  Ejecutar todo con Docker

Desde la raíz del proyecto:

```bash
docker-compose down -v
docker-compose build --no-cache
docker-compose up
```

---

##  URLs del sistema

| Servicio          | URL                                   |
| ----------------- | ------------------------------------- |
| Frontend          | http://localhost:9000                 |
| Product Service   | http://localhost:8080                 |
| Inventory Service | http://localhost:8081                 |
| Swagger Product   | http://localhost:8080/swagger-ui.html |
| Swagger Inventory | http://localhost:8081/swagger-ui.html |
| RabbitMQ UI       | http://localhost:15672                |

---

#  Seguridad

Los servicios utilizan una API Key básica:

Header requerido:

```
X-API-KEY: secret123
```

---

#  Endpoints principales

##  Product Service

* POST `/products` → Crear producto
* GET `/products` → Listar productos
* GET `/products/{id}` → Obtener producto por ID

---

##  Inventory Service

* GET `/inventory/{productId}` → Obtener inventario
* PUT `/inventory/{productId}?quantity=` → Actualizar stock
* POST `/inventory/purchase` → Realizar compra

---

# 🖥 Frontend (Quasar)

El frontend incluye:

* Crear producto
* Listar productos
* Consultar producto por ID
* Actualizar stock
* Realizar compra
* Ver inventario
* Buscar por nombre

---

#  UI

* Uso de componentes Quasar (QCard, QTable, QForm)
* Diseño responsivo
* Validaciones:

  * Campos obligatorios
  * Valores positivos
  * Restricción de texto/números

---

#  Manejo de errores

Se implementa un formato estándar:

```json
{
  "errors": [
    {
      "status": "404",
      "title": "Product Not Found",
      "detail": "Producto no encontrado"
    }
  ],
  "timestamp": "..."
}
```

---

#  Resiliencia

En el `inventory-service`:

* Retry automático ante fallos
* Manejo de:

  * Timeout
  * Caídas del servicio
  * Errores HTTP

---

#  Pruebas

Se incluyen:

* Unit tests
* Integration tests
* Tests de excepciones
* Cobertura con JaCoCo

---

# Cobertura

Se excluyen:

* DTOs
* Configuración
* Modelos

Se enfoca en:

* Servicios
* Clientes HTTP
* Controladores

---

# Docker

Cada servicio tiene su propio Dockerfile:

* Backend → Spring Boot
* Frontend → Node build + Nginx

Docker Compose levanta:

* product-service
* inventory-service
* frontend
* postgres
* rabbitmq

---

# Conclusión

Este proyecto demuestra:

* Diseño de microservicios
* Comunicación síncrona y asíncrona
* Manejo de errores
* Resiliencia
* Testing
* Dockerización completa
* Integración frontend + backend

---

# Gracias

Proyecto desarrollado como prueba técnica.

---
