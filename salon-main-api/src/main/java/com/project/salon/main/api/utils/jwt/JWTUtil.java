package com.project.salon.main.api.utils.jwt;

import com.project.salon.main.api.dto.constant.admin.AdminRole;
import com.project.salon.main.api.utils.Common;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static com.project.salon.main.api.utils.Common.AUTHCHAR;

@Component
public class JWTUtil {

    private Long expirationTime;
    private SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}")String secret,
                   @Value("${spring.jwt.expiration_time}")Long expirationTime) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.expirationTime = expirationTime;
    }

    public String getUsername(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token) {

        boolean result;

        try {
            result =  Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
        } catch (Exception e) {
            result = true;
        }

        return result;
    }

    public Map<String, Object> decodeAccessToken(final String accessToken) {
        String decToken = Common.decryptStringSalt(accessToken.replace("Bearer ", ""));
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(decToken);
        }
        catch (Exception e) {

        }
        return claims.getBody();
    }

    public String createAuthToken(String username, String userID, UUID userGuid, AdminRole role) {
        return Jwts.builder()
                .claim("username", username)
                .claim("userID", userID)
                .claim("userGuid", userGuid)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime)) // 10시간
//                .expiration(new Date(System.currentTimeMillis() + 1000)) // 1초
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken(String userID) {
        return Jwts.builder()
                .claim("userID", userID)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime * 24 * 7)) // 7일
                .signWith(secretKey)
                .compact();
    }
}
