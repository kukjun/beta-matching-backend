package io.wisoft.testermatchingplatform.jwt;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.xml.bind.DatatypeConverter;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

//@Component
public class JwtProvider {

    private static final String secretKey = "kukjunfighting";


    public String createJwtAccessToken(UUID id, String roles) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("gukjunLee")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(40).toMillis()))
                .setSubject(String.valueOf(id))
                .claim("roles", roles)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // 토큰 값 가져오기
    public Claims getTokenData(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(token).getBody();
    }

    public String getSubject(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(token).getBody().getSubject();
    }

    // 유효성 검사
    public boolean isValidToken(String token) {
        try {
            Claims claims = getTokenData(token);
            return !claims.getExpiration().before(new Date());
        } catch (JwtAuthException | NullPointerException exception) {
            return false;
        }
    }

    public boolean validMakerToken(String token) {
            if (isValidToken(token)) {
                if (getTokenData(token).get("roles").equals("maker")) {
                    return true;
                }
            }
        throw new JwtAuthException("Auth Fail: Maker");
    }

    public boolean validTesterToken(String token) {
        if (isValidToken(token)) {
            if (getTokenData(token).get("roles").equals("tester")) {
                return true;
            }
        }
        throw new JwtAuthException("Auth Fail: Tester");
    }

    public String refreshMakerToken(String token) {
        UUID id = UUID.fromString(getTokenData(token).getSubject());
        return createJwtAccessToken(id, "maker");
    }

    public String refreshTesterToken(String token) {
        UUID id = UUID.fromString(getTokenData(token).getSubject());
        return createJwtAccessToken(id, "tester");
    }

}