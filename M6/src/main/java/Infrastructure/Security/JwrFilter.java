package Infrastructure.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import Infrastructure.Helpers.JwtUtil;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwrFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public JwrFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    private UserDetailsService userDetailsService; // Servicio para cargar detalles del usuario

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException, java.io.IOException {

        final String authorizationHeader = request.getHeader("Authorization"); // Obtener el encabezado de autorización

        String name = null; // Cambiado de username a name
        String jwt = null;

        // Verificar si el encabezado de autorización está presente y comienza con "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Extraer el token JWT
            name = jwtUtil.extractUsername(jwt); // Extraer el nombre del usuario del token
        }

        // Si el nombre no es nulo y no hay autenticación en el contexto
        if (name != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(name); // Cargar los detalles del usuario

            // Validar el token
            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                // Crear un token de autenticación
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // Configurar detalles de autenticación
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken); // Establecer la autenticación en el contexto
            }
        }
        chain.doFilter(request, response); // Continuar con el siguiente filtro
    }

}
