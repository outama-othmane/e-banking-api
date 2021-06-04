package ma.ac.ensa.ebankingapi.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ma.ac.ensa.ebankingapi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final String JWT_SECRET;

    private final Integer JWT_TTL;

    @Autowired
    public JwtUtil(@Value("jwt.secret") String jwt_secret,
                   @Value("#{ T(java.lang.Integer).parseInt('${jwt.ttl}')}") Integer jwt_ttl) {
        JWT_SECRET = jwt_secret;
        JWT_TTL = jwt_ttl;
    }

    public String generateToken(User user) {
        String subject = getSubjectFromUser(user);

        Map<String, Object> claims = new HashMap<>();
        // claims.put("authorities", user.getAuthorities());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * JWT_TTL))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean validateToken(String token, User user) {
        final String subject = extractSubject(token);
        return (subject.equals(getSubjectFromUser(user)) && !isTokenExpired(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String getSubjectFromUser(User user) {
        return user.getId().toString();
    }
}
