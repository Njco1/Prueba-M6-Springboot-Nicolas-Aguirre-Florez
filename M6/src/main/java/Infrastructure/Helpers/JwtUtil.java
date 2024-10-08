package Infrastructure.Helpers;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.function.Function;
import Domain.Entities.Admin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    
    @Value("${jwt.secret}")
    private String secretKey; 

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(this.secretKey.getBytes()); // Crear la clave HMAC a partir de la clave secreta
    }

    // Método para generar el token JWT
    public String generateToken(Admin admin) {
        return Jwts.builder()
                .addClaims(Map.of(
                        "id", admin.getId(),
                        "role", admin.getRole(),
                        "name", admin.getName()
                ))
                .setSubject(admin.getEmail()) // Usar el email como sujeto
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Método para extraer el nombre de usuario del token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); // Extraer el sujeto del token
    }

    // Método para validar el token
    public boolean validateToken(String token, String email) {
        return (email.equals(extractUsername(token)) && !isTokenExpired(token)); // Comprobar si el token es válido
    }

    // Método para comprobar si el token ha expirado
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date()); // Verificar la fecha de expiración
    }

    // Método genérico para extraer una reclamación del token
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token)); // Aplicar el extractor a las reclamaciones
    }

    // Método para extraer todas las reclamaciones del token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey()) // Establecer la clave de firma
                .build()
                .parseClaimsJws(token) // Analizar el token
                .getBody(); // Devolver el cuerpo de las reclamaciones
    }
}
