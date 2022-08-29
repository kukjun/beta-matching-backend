package io.wisoft.testermatchingplatform.web.jwt;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;


@Component
public class JwtTokenProvider {


    public String createJwtToken(String email, String nickname) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("gukjunLee")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis()))
                .claim("email", email)
                .claim("nickname", nickname)
                .signWith(SignatureAlgorithm.HS256, "secret")
                .compact();
    }
}
