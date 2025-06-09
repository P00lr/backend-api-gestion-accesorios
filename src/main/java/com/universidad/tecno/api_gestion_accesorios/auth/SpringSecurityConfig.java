package com.universidad.tecno.api_gestion_accesorios.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.universidad.tecno.api_gestion_accesorios.auth.filter.JwtAuthenticationFilter;
import com.universidad.tecno.api_gestion_accesorios.auth.filter.JwtValidationFilter;

@Configuration
public class SpringSecurityConfig {
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;
    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authz -> authz
                // Rutas públicas
                .requestMatchers(HttpMethod.GET, "/api/accessories/page/catalog/{page}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/users/{id}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users/change-password").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/users/{id}").permitAll()


                //-----------------------RUTAS PROTEGIDAS------------------------------------------

                // USERS
                .requestMatchers(HttpMethod.GET, "/api/users/page/{page}").hasAuthority("VER_USUARIO")
                .requestMatchers(HttpMethod.GET, "/api/users/with-permissions/{id}").hasAuthority("VER_USUARIO")
                .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasAuthority("ELIMINAR_USUARIO")
                
                 //ACCESSORIES
                .requestMatchers(HttpMethod.GET, "/api/accessories/page/{page}").hasAuthority("VER_ACCESORIO")
                .requestMatchers(HttpMethod.GET, "/api/accessories/{id}").hasAuthority("VER_ACCESORIO")
                .requestMatchers(HttpMethod.GET, "/api/accessories").hasAuthority("VER_ACCESORIO")
                .requestMatchers(HttpMethod.POST, "/api/accessories").hasAuthority("CREAR_ACCESORIO")
                .requestMatchers(HttpMethod.PUT, "/api/accessories/{id}").hasAuthority("EDITAR_ACCESORIO")
                .requestMatchers(HttpMethod.DELETE, "/api/accessories/{id}").hasAuthority("ELIMINAR_ACCESORIO")

                //PERMISSIONS
                .requestMatchers(HttpMethod.GET, "/api/permissions/page/{page}").hasAuthority("VER_PERMISO")
                .requestMatchers(HttpMethod.GET, "/api/permissions").hasAuthority("VER_PERMISO")
                .requestMatchers(HttpMethod.GET, "/api/permissions/{id}").hasAuthority("VER_PERMISO")
                .requestMatchers(HttpMethod.POST, "/api/permissions").hasAuthority("CREAR_PERMISO")
                .requestMatchers(HttpMethod.POST, "/api/permissions/assign-to-user").hasAuthority("ASIGNAR_PERMISOS_A_USER")
                .requestMatchers(HttpMethod.POST, "/api/permissions/assign-to-rol").hasAuthority("ASIGNAR_PERMISOS_A_ROL")
                .requestMatchers(HttpMethod.PUT, "/api/permissions/{id}").hasAuthority("EDITAR_PERMISO")
                .requestMatchers(HttpMethod.DELETE, "/api/permissions/{id}").hasAuthority("ELIMINAR_PERMISO")

                // CLIENTS
                .requestMatchers(HttpMethod.GET, "/api/clients/page/{page}").hasAuthority("VER_CLIENTE")
                .requestMatchers(HttpMethod.GET, "/api/clients/{id}").hasAuthority("VER_CLIENTE")
                .requestMatchers(HttpMethod.POST, "/api/clients").hasAuthority("CREAR_CLIENTE")
                .requestMatchers(HttpMethod.PUT, "/api/clients/{id}").hasAuthority("EDITAR_CLIENTE")
                .requestMatchers(HttpMethod.DELETE, "/api/clients/{id}").hasAuthority("ELIMINAR_CLIENTE")

                 // SUPPLIER
                .requestMatchers(HttpMethod.GET, "/api/suppliers/page/{page}").hasAuthority("VER_PROVEEDOR")
                .requestMatchers(HttpMethod.GET, "/api/suppliers").hasAuthority("VER_PROVEEDOR")
                .requestMatchers(HttpMethod.GET, "/api/suppliers/{id}").hasAuthority("VER_PROVEEDOR")
                .requestMatchers(HttpMethod.POST, "/api/suppliers").hasAuthority("CREAR_PROVEEDOR")
                .requestMatchers(HttpMethod.PUT, "/api/suppliers/{id}").hasAuthority("EDITAR_PROVEEDOR")
                .requestMatchers(HttpMethod.DELETE, "/api/suppliers/{id}").hasAuthority("ELIMINAR_PROVEEDOR")

                // CATEGORIES
                .requestMatchers(HttpMethod.GET, "/api/categories/page/{page}").hasAuthority("VER_CATEGORIA")
                .requestMatchers(HttpMethod.GET, "/api/categories").hasAuthority("VER_CATEGORIA")
                .requestMatchers(HttpMethod.GET, "/api/categories/{id}").hasAuthority("VER_CATEGORIA")
                .requestMatchers(HttpMethod.PUT, "/api/categories/{id}").hasAuthority("EDITAR_CATEGORIA")
                .requestMatchers(HttpMethod.POST, "/api/categories").hasAuthority("CREAR_CATEGORIA")
                .requestMatchers(HttpMethod.DELETE, "/api/categories/{id}").hasAuthority("ELIMINAR_CATEGORIA")

                // WAREHOUSES
                .requestMatchers(HttpMethod.GET, "/api/warehouses/page/{page}").hasAuthority("VER_ALMACEN")
                .requestMatchers(HttpMethod.GET, "/api/warehouses").hasAuthority("VER_ALMACEN")
                .requestMatchers(HttpMethod.GET, "/api/warehouses/details").hasAuthority("VER_ALMACEN")
                .requestMatchers(HttpMethod.GET, "/api/warehouses/{id}").hasAuthority("VER_ALMACEN")
                .requestMatchers(HttpMethod.PUT, "/api/warehouses/{id}").hasAuthority("EDITAR_ALMACEN")
                .requestMatchers(HttpMethod.POST, "/api/warehouses").hasAuthority("CREAR_ALMACEN")
                .requestMatchers(HttpMethod.DELETE, "/api/warehouses/{id}").hasAuthority("ELIMINAR_ALMACEN")

                //reporte y envio de email
                .requestMatchers(HttpMethod.GET, "/api/warehouses/report").hasAuthority("REPORTE_ALMACEN")
                .requestMatchers(HttpMethod.POST, "/api/email/inventory").hasAuthority("REPORTE_ALMACEN")


                // SALES
                .requestMatchers(HttpMethod.GET, "/api/sales/page/{page}").hasAuthority("VER_VENTA")
                .requestMatchers(HttpMethod.GET, "/api/sales/{id}").hasAuthority("VER_VENTA")
                .requestMatchers(HttpMethod.DELETE, "/api/sales/{id}").hasAuthority("ELIMINAR_VENTA")

                //--reporte de sale y envio de email
                .requestMatchers(HttpMethod.GET, "/api/sales/report").hasAuthority("REPORTE_VENTA")
                .requestMatchers(HttpMethod.POST, "/api/email/sale").hasAuthority("REPORTE_VENTA")




                // PURCHASES
                .requestMatchers(HttpMethod.GET, "/api/purchases/page/{page}").hasAuthority("VER_COMPRA")
                .requestMatchers(HttpMethod.GET, "/api/purchases/{id}").hasAuthority("VER_COMPRA")
                .requestMatchers(HttpMethod.POST, "/api/purchases").hasAuthority("CREAR_COMPRA")
                .requestMatchers(HttpMethod.DELETE, "/api/purchases/{id}").hasAuthority("ELIMINAR_COMPRA")

                // ADJUSTMENTS
                .requestMatchers(HttpMethod.GET, "/api/adjustments/page/{page}").hasAuthority("VER_AJUSTE")
                .requestMatchers(HttpMethod.GET, "/api/adjustments/{id}").hasAuthority("VER_AJUSTE")
                .requestMatchers(HttpMethod.POST, "/api/adjustments").hasAuthority("CREAR_AJUSTE")
                .requestMatchers(HttpMethod.DELETE, "/api/adjustments/{id}").hasAuthority("ELIMINAR_AJUSTE")

                // TRANSFERS
                .requestMatchers(HttpMethod.GET, "/api/transfers/page/{page}").hasAuthority("VER_TRASPASO")
                .requestMatchers(HttpMethod.GET, "/api/transfers/{id}").hasAuthority("VER_TRASPASO")
                .requestMatchers(HttpMethod.POST, "/api/transfers").hasAuthority("CREAR_TRASPASO")
                .requestMatchers(HttpMethod.DELETE, "/api/transfers/{id}").hasAuthority("ELIMINAR_TRASPASO")
                
                // ROLES
                .requestMatchers(HttpMethod.GET, "/api/roles/page/{page}").hasAuthority("VER_ROL")
                .requestMatchers(HttpMethod.GET, "/api/roles//with-permissions").hasAuthority("VER_ROL")
                .requestMatchers(HttpMethod.GET, "/api/roles/with-permissions/page/{page}").hasAuthority("VER_ROL")
                .requestMatchers(HttpMethod.GET, "/api/roles/{id}").hasAuthority("VER_ROL")
                .requestMatchers(HttpMethod.POST, "/api/roles").hasAuthority("CREAR_ROL")
                .requestMatchers(HttpMethod.PUT, "/api/roles/{id}").hasAuthority("EDITAR_ROL")
                .requestMatchers(HttpMethod.DELETE, "/api/roles/{id}").hasAuthority("ELIMINAR_ROL")




                // Otros endpoints pueden agregarse igual
                .anyRequest().authenticated())
                .cors(cors -> cors.configurationSource(configurationSource()))
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationManager()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
    @Bean
    CorsConfigurationSource configurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.setAllowedOrigins(Arrays.asList("http://localhost:4500"));
        config.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<CorsFilter>(
                new CorsFilter(this.configurationSource()));
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;
    }
}

