package com.universidad.tecno.api_gestion_accesorios.auth;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;

// Esta clase contiene configuraciones estáticas relacionadas con el manejo de tokens JWT.
// Centraliza constantes importantes para evitar valores "hardcoded" (escritos a mano) en todo el código.
public class TokenJwtConfig {

    // Tipo de contenido que se usará en las respuestas que devuelven información en formato JSON.
    // Se utiliza para que el cliente (frontend, Postman, etc.) sepa que recibirá un JSON.
    public static final String CONTENT_TYPE = "application/json";

    // Prefijo que va antes del token JWT en el header "Authorization".
    // Es un estándar que indica que el contenido del header es un token tipo Bearer.
    // Ejemplo: Authorization: Bearer eyJhbGciOi...
    public static final String PREFIX_TOKEN = "Bearer ";

    // Nombre del header HTTP que transportará el token JWT.
    // Este es el header que leerán los filtros para validar o generar el token.
    public static final String HEADER_AUTHORIZATION = "Authorization";

    // Clave secreta usada para firmar y verificar los JWT.
    // Aquí se genera dinámicamente una clave segura con el algoritmo HS256.
    // Esta clave debe mantenerse en secreto. En producción, normalmente se almacena en variables de entorno.
    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
}
