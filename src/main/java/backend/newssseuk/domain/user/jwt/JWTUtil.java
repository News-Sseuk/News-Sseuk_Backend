package backend.newssseuk.domain.user.jwt;

import backend.newssseuk.domain.user.web.response.JwtToken;
import backend.newssseuk.payload.exception.GeneralException;
import backend.newssseuk.payload.status.ErrorStatus;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTUtil {

    private final SecretKey key;

    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        byte[] byteSecretKey = secret.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(byteSecretKey);
    }

    public String getUsername(String token) {
        Claims claims = parseClaims(token);
        return claims.getSubject();
    }

    public String getEmail(String token) {
        Claims claims = parseClaims(token);
        return claims.get("email", String.class);
    }

    public Boolean isExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new GeneralException(ErrorStatus.EXPIRED_ACCESS_TOKEN);
        } catch (SignatureException e) {
            throw new GeneralException(ErrorStatus.INVALID_TOKEN);
        } catch (Exception e) {
            throw new GeneralException(ErrorStatus.INVALID_TOKEN);
        }
    }

    public JwtToken createJwt(String username, String email) {
        String accessToken = Jwts.builder()
                .setSubject(username)
                .claim("category", "access")
                .claim("username", username)
                .claim("email", email)
                .claim("role", "ROLE_USER")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + 360000000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String recreateAccessToken(String username, String email, String roles) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime afterHalfHour = now.plus(30, ChronoUnit.MINUTES);
        Date accessTokenExpiresIn = convertToDate(afterHalfHour);

        return Jwts.builder()
                .setSubject(username)
                .claim("auth", roles)
                .claim("username", username)
                .claim("email", email)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    private Date convertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
