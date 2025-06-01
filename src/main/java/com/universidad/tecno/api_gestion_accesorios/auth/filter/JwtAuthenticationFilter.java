package com.universidad.tecno.api_gestion_accesorios.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.universidad.tecno.api_gestion_accesorios.auth.TokenJwtConfig;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.*;

// Este filtro se ejecuta cuando el usuario intenta iniciar sesión.
// Extiende de UsernamePasswordAuthenticationFilter que maneja automáticamente peticiones de login ("/login" por defecto).
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    // Inyectamos el AuthenticationManager necesario para realizar la autenticación del usuario.
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // Este método se llama automáticamente cuando un usuario envía una solicitud de login (POST a /login).
    // Aquí se procesan las credenciales que vienen en el cuerpo de la petición.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            // Leemos el cuerpo de la petición (JSON) y lo convertimos a un objeto User.
            com.universidad.tecno.api_gestion_accesorios.entities.User user = new ObjectMapper()
                    .readValue(request.getInputStream(),
                            com.universidad.tecno.api_gestion_accesorios.entities.User.class);

            // Creamos un token de autenticación con el username y password proporcionados.
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            // Le delegamos al AuthenticationManager la autenticación del usuario.
            // Este manager usa los UserDetailsService y PasswordEncoder configurados en el sistema.
            return authenticationManager.authenticate(authenticationToken);

        } catch (IOException e) {
            // Si ocurre un error al leer el JSON del cuerpo, lanzamos una excepción.
            throw new RuntimeException("Error al leer el JSON de login", e);
        }
    }

    // Este método se ejecuta automáticamente si el login fue exitoso (credenciales válidas).
    // Aquí se genera el token JWT y se envía de vuelta al cliente.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        // Obtenemos el usuario autenticado desde el resultado de la autenticación.
        User user = (User) authResult.getPrincipal();

        // Extraemos la lista de roles/permisos del usuario autenticado.
        List<String> roles = new ArrayList<>();
        authResult.getAuthorities().forEach(a -> roles.add(a.getAuthority()));

        // Creamos un mapa con los "claims" (datos personalizados) que queremos incluir en el token.
        Map<String, Object> claims = new HashMap<>();
        // Agregamos las autoridades como JSON para luego poder reconstruirlas en el filtro de validación.
        claims.put("authorities", new ObjectMapper().writeValueAsString(user.getAuthorities()));

        // Construimos el JWT usando la librería jjwt.
        String jwt = Jwts.builder()
                .subject(user.getUsername())                // Usuario autenticado.
                .claims(claims)                             // Roles/permisos.
                .issuedAt(new Date())                       // Fecha de emisión.
                .expiration(new Date(System.currentTimeMillis() + 3600000)) // Expira en 1 hora.
                .signWith(TokenJwtConfig.SECRET_KEY)        // Firmamos el token con la clave secreta.
                .compact();                                 // Generamos el JWT como string.

        // Agregamos el token JWT al encabezado de la respuesta.
        response.addHeader(TokenJwtConfig.HEADER_AUTHORIZATION, TokenJwtConfig.PREFIX_TOKEN + jwt);

        // También podemos incluir el token en el cuerpo de la respuesta como JSON.
        Map<String, String> body = new HashMap<>();
        body.put("token", jwt);
        body.put("message", "Autenticación correcta");
        body.put("username", user.getUsername());

        // Configuramos la respuesta como JSON y la enviamos.
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(TokenJwtConfig.CONTENT_TYPE); // application/json
        response.setStatus(200); // HTTP 200 OK
    }
}

