1. REGISTRO DE USUARIO (Auth)
(Basado en tu UsuarioEntity)

URL: http://localhost:8090/api/auth/registro

JSON:

JSON

{
    "username": "alumno1",
    "password": "123",
    "nombreCompleto": "Carlos Perez"
}


2. LOGIN (Auth)
(Para obtener el Token)

URL: http://localhost:8090/api/auth/login

JSON:

JSON

{
    "username": "alumno1",
    "password": "123"
}

3. CREAR BECA (Admin)

URL: http://localhost:8090/api/becas

JSON:

JSON

{
    "nombre": "Beca Socioeconómica 2025",
    "descripcion": "Apoyo para estudiantes con recursos limitados",
    "fechaInicio": "2024-01-01",
    "fechaFin": "2025-12-31",
    "activa": true
}


4. POSTULAR (Estudiante)

URL: http://localhost:8090/api/postulaciones/aplicar


JSON

{
    "idBeca": 1,
    "promedioNotas": 16.5,
    "ingresosFamiliares": 950.00
}

5. EVALUAR (Admin)
(Para aprobar o rechazar) por el postulacionID?estado=APROBADO/RECHAZADO

URL: http://localhost:8090/api/postulaciones/admin/evaluar/1?estado=APROBADO

Params (Query Params en Postman):

KEY: estado

VALUE: APROBADO/RECHAZADO


6. VER RANKING (Admin)
Este endpoint no requiere enviar nada, solo estar logueado como Admin.

Método: GET

URL: http://localhost:8090/api/postulaciones/admin/ranking

Auth: Token del usuario admin.


Lo que recibirás (Response JSON):

JSON

[
    {
        "id": 1,
        "nombreEstudiante": "Carlos Perez",
        "nombreBeca": "Beca Socioeconómica 2025",
        "promedioNotas": 16.5,
        "ingresosFamiliares": 950.0,
        "estado": "PENDIENTE",
        "fechaPostulacion": "2024-10-30T10:00:00",
        "puntaje": 21.5
    }
]
7. MIS SOLICITUDES (Estudiante)
El estudiante consulta cómo va su trámite.

Método: GET

URL: http://localhost:8090/api/postulaciones/mis-solicitudes

Auth: Token del usuario alumno1 (Carlos Perez).

Lo que recibirás (Response JSON):

JSON

[
    {
        "id": 1,
        "nombreEstudiante": "Carlos Perez",
        "nombreBeca": "Beca Socioeconómica 2025",
        "promedioNotas": 16.5,
        "ingresosFamiliares": 950.0,
        "estado": "PENDIENTE", 
        "fechaPostulacion": "2024-10-30T10:00:00",
        "puntaje": 21.5
    }
]
(Nota: Cuando el Admin lo apruebe, al volver a consultar este endpoint, el campo estado cambiará a "APROBADO").
