package Infrastructure.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import Domain.Enums.Role;
import lombok.AllArgsConstructor;

@EnableWebSecurity
@AllArgsConstructor
@Configuration
@Component
public class SecurityConfig implements AuthenticationProvider {
  
    private final JwrFilter jwtFilter; 

    private final AuthenticationProvider authenticationProvider; // Proveedor de autenticación para manejar las credenciales

    @Autowired
    public SecurityConfig(@Lazy AuthenticationProvider authenticationProvider, JwrFilter jwtFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtFilter = jwtFilter;
    }


    // Definición de los endpoints públicos
    private final String[] PUBLIC_ENDPOINTS = { "/auth/login", "/user/register"};
    // Definición de los endpoints que solo pueden ser accedidos por ADMIN
    private final String[] ADMIN_ENDPOINTS = { "/api/pallets/", "/api/pallets/{id}","/api/loads/","/api/loads/{id}"};
    //Definicion de los endpoints que solo pueden ser accedidos por Carriers
    private final String[] CARRIERS_ENPOINTS = {"api/carriers/loads"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF ya que se usará JWT
                .authorizeHttpRequests(authRequest -> authRequest
                        .requestMatchers(ADMIN_ENDPOINTS).hasAuthority(Role.ADMIN.name())
                        .requestMatchers(CARRIERS_ENPOINTS).hasAuthority(Role.CARRIER.name())  // Permite solo a ADMIN acceder a los endpoints de ADMIN
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll() // Permite acceso a todos a los endpoints públicos
                        .anyRequest().authenticated()) // Cualquier otra solicitud requiere autenticación
                .authenticationProvider(authenticationProvider) // Establece el proveedor de autenticación
                .sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configura la sesión para que sea sin estado
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Agrega el filtro JWT antes del filtro de autenticación por nombre de usuario y contraseña
                .build(); 
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        throw new UnsupportedOperationException("Unimplemented method 'authenticate'");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        throw new UnsupportedOperationException("Unimplemented method 'supports'");
    }
} 
