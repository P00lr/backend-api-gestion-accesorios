package com.universidad.tecno.api_gestion_accesorios.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.universidad.tecno.api_gestion_accesorios.auth.SimpleGrantedAuthorityJsonCreator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.*;

import static com.universidad.tecno.api_gestion_accesorios.auth.TokenJwtConfig.*;

// Filtro personalizado que se encarga de validar el token JWT en cada petición HTTP protegida.
// Extiende de BasicAuthenticationFilter para integrarse con el sistema de seguridad de Spring.
public class JwtValidationFilter extends BasicAuthenticationFilter {

    // Constructor que recibe un AuthenticationManager necesario para la clase padre.
    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    // Método que se ejecuta para cada petición HTTP entrante.
    // Aquí se intercepta la petición para validar el JWT.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        // Obtenemos el valor del encabezado "Authorization" de la solicitud HTTP.
        String header = request.getHeader(HEADER_AUTHORIZATION);

        // Si no hay encabezado o no comienza con el prefijo esperado (por ejemplo, "Bearer "),
        // entonces dejamos que la petición continúe sin autenticación.
        if (header == null || !header.startsWith(PREFIX_TOKEN)) {
            chain.doFilter(request, response);
            return;
        }

        // Eliminamos el prefijo del token para obtener solo el JWT puro.
        String token = header.replace(PREFIX_TOKEN, "");

        try {
            // Parseamos y validamos el JWT usando la clave secreta definida (SECRET_KEY).
            // Si el token es inválido o está expirado, se lanzará una excepción.
            Claims claims = Jwts.parser()
                    .verifyWith(SECRET_KEY) // Verifica la firma del JWT.
                    .build()
                    .parseSignedClaims(token) // Parsea el JWT y extrae los "claims" (datos).
                    .getPayload(); // Obtenemos el contenido del token.

            // Extraemos el nombre de usuario (subject) desde el token.
            String username = claims.getSubject();

            // Extraemos el JSON con las autoridades (roles o permisos) desde el token.
            String authoritiesJson = (String) claims.get("authorities");

            // Convertimos el JSON de autoridades a objetos de tipo SimpleGrantedAuthority.
            // Usamos un mixin para ayudar a Jackson a reconstruir correctamente los objetos.
            SimpleGrantedAuthority[] roles = new ObjectMapper()
                    .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonCreator.class)
                    .readValue(authoritiesJson, SimpleGrantedAuthority[].class);

            // Creamos un token de autenticación con el nombre de usuario y las autoridades.
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, null, Arrays.asList(roles));

            // Establecemos el usuario autenticado en el contexto de seguridad de Spring.
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // Continuamos con la cadena de filtros para que la petición llegue a su destino.
            chain.doFilter(request, response);

            // (Opcional) Imprimimos en consola las autoridades del token para depuración.
            System.out.println("Authorities del token: " + Arrays.toString(roles));

        } catch (JwtException e) {
            // Si ocurre un error al procesar el token (inválido, expirado, etc.), 
            // devolvemos un error 401 (No autorizado) al cliente.

            Map<String, String> body = new HashMap<>();
            body.put("error", e.getMessage());
            body.put("message", "El token es invalido!");

            // Enviamos la respuesta como JSON con el mensaje de error.
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(401); // Código HTTP 401 Unauthorized.
            response.setContentType(CONTENT_TYPE); // Usualmente application/json
        }
    }
}

