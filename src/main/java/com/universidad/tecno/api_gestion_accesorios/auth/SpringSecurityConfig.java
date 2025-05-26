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
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(configurationSource()))
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(HttpMethod.GET, "/api/sales").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/purchases").permitAll()
                // CLIENTS
                .requestMatchers(HttpMethod.GET, "/api/clients/page/{page}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/clients/{id}").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/clients/{id}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/clients").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/clients/{id}").permitAll()

                 // SUPPLIER
                .requestMatchers(HttpMethod.GET, "/api/suppliers/page/{page}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/suppliers/{id}").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/suppliers/{id}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/suppliers").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/suppliers/{id}").permitAll()

                // CATEGORIES
                .requestMatchers(HttpMethod.GET, "/api/categories/page/{page}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/categories").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/categories/{id}").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/categories/{id}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/categories").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/categories/{id}").permitAll()

                // WAREHOUSES
                .requestMatchers(HttpMethod.GET, "/api/warehouses/page/{page}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/warehouses").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/warehouses/details").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/warehouses/{id}").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/warehouses/{id}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/warehouses").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/warehouses/{id}").permitAll()

                // ACCESORIES
                .requestMatchers(HttpMethod.GET, "/api/accessories/page/{page}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/accessories/page/catalog/{page}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/accessories/{id}").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/accessories/{id}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/accessories").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/accessories/{id}").permitAll()

                // USERS
                
                .requestMatchers(HttpMethod.GET, "/api/users/page/{page}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/users/{id}").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/users/{id}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users/change-password").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").permitAll()

                // SALES
                .requestMatchers(HttpMethod.GET, "/api/sales/page/{page}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/sales/{id}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/sales").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/sales/{id}").permitAll()

                // PURCHASES
                .requestMatchers(HttpMethod.GET, "/api/purchases/page/{page}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/purchases/{id}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/purchases").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/purchases/{id}").permitAll()

                // ADJUSTMENTS
                .requestMatchers(HttpMethod.GET, "/api/adjustments/page/{page}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/adjustments/{id}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/adjustments").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/adjustments/{id}").permitAll()

                // TRANSFERSS
                .requestMatchers(HttpMethod.GET, "/api/transfers/page/{page}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/transfers/{id}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/transfers").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/transfers/{id}").permitAll()

                // PERMISSIONS
                .requestMatchers(HttpMethod.GET, "/api/permissions/page/{page}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/permissions/{id}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/permissions").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/permissions/assign-to-role").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/permissions/{id}").permitAll()

                // ROLES
                .requestMatchers(HttpMethod.GET, "/api/roles/page/{page}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/roles/with-permissions/page/{page}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/roles/{id}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/roles").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/roles/{id}").permitAll()


                        .anyRequest().authenticated())
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationManager()));
        return http.build();
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
