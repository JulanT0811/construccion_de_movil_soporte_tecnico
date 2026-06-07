# 🎫 Sistema de Gestión de Tickets de Soporte Técnico

Sistema web y API REST para la gestión de tickets de soporte técnico, desarrollado con **Django**, **Django REST Framework** y autenticación **JWT**.

## 📋 Descripción

Este proyecto permite administrar solicitudes de soporte técnico mediante un sistema de tickets. Los usuarios pueden crear, consultar y gestionar incidencias, mientras que los administradores pueden administrar categorías, prioridades y usuarios.

## 🚀 Características

* Autenticación mediante JWT.
* Gestión de usuarios.
* Gestión de tickets.
* Gestión de categorías.
* Gestión de prioridades.
* Comentarios en tickets.
* Adjuntos de archivos.
* API REST documentada mediante endpoints.
* Despliegue con Gunicorn y Nginx.
* Certificados SSL con Let's Encrypt.

## 🛠️ Tecnologías Utilizadas

### Backend

* Python 3
* Django
* Django REST Framework
* Simple JWT
* Gunicorn

### Infraestructura

* Ubuntu Server
* Nginx
* Let's Encrypt SSL

### Cliente

* Android (Jetpack Compose)
* Retrofit
* OkHttp

---

## 📁 Estructura del Proyecto

```text
soporte_tecnico/
├── config/
│   ├── settings.py
│   ├── urls.py
│   └── wsgi.py
│
├── soporte_tecnico/
│   ├── models/
│   ├── serializers/
│   ├── views/
│   ├── migrations/
│   ├── permissions.py
│   └── urls.py
│
├── manage.py
├── requerimientos.txt
└── README.md
```

---

## ⚙️ Instalación Local

### 1. Clonar repositorio

```bash
git clone https://github.com/JulanT0811/soporte_tecnico_tixi.git
cd soporte_tecnico_tixi
```

### 2. Crear entorno virtual

```bash
python -m venv .venv
```

### Windows

```bash
.venv\Scripts\activate
```

### Linux

```bash
source .venv/bin/activate
```

### 3. Instalar dependencias

```bash
pip install -r requerimientos.txt
```

### 4. Configurar variables de entorno

Crear archivo:

```env
DEBUG=False

SECRET_KEY=tu_clave_secreta

DB_NAME=nombre_bd
DB_USER=usuario
DB_PASSWORD=password
DB_HOST=localhost
DB_PORT=5432
```

### 5. Ejecutar migraciones

```bash
python manage.py migrate
```

### 6. Crear superusuario

```bash
python manage.py createsuperuser
```

### 7. Ejecutar servidor

```bash
python manage.py runserver
```

---

## 🔐 Autenticación JWT

### Obtener Token

**POST**

```http
/api/auth/token/
```

Body:

```json
{
  "username": "admin",
  "password": "password"
}
```

Respuesta:

```json
{
  "refresh": "token_refresh",
  "access": "token_access"
}
```

### Refrescar Token

**POST**

```http
/api/auth/token/refresh/
```

---

## 📡 Endpoints Principales

### Autenticación

```http
POST /api/auth/token/
POST /api/auth/login/
POST /api/auth/token/refresh/
POST /api/auth/logout/
```

### Tickets

```http
GET    /api/tickets/
POST   /api/tickets/
GET    /api/tickets/{id}/
PUT    /api/tickets/{id}/
DELETE /api/tickets/{id}/
```

### Categorías

```http
GET    /api/categories/
POST   /api/categories/
```

### Prioridades

```http
GET    /api/priorities/
POST   /api/priorities/
```

### Usuarios

```http
GET    /api/users/
POST   /api/users/
```

### Health Check

```http
GET /health/
GET /api/health/
```

---

## 🌐 Producción

Dominio:

```text
https://tixi-soporte.uaeftt-ute.site
```

API Base URL:

```text
https://tixi-soporte.uaeftt-ute.site/api/
```

---

## 👨‍💻 Autor

**Julian Tixi**

Estudiante de Ingeniería de Software – UTE

GitHub:
https://github.com/JulanT0811/construccion_de_movil_soporte_tecnico.git
