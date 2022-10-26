package io.wisoft.testermatchingplatform.jwt;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@WebFilter(value = "/testers/*")
public class JwtTesterTokenCheckFilter2 extends OncePerRequestFilter {


    public static final String ACCESS_TOKEN = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String accessToken = resolveAccessToken(request);
        if (request.getRequestURI().equals("/testers/login") || request.getRequestURI().equals("/testers/register")) {
            return;
        }
        // access Token 만료시
        if (!jwtProvider.isValidToken(accessToken)) {
            System.out.println("access token 만료");
            throw new JwtException("토큰 만료");
        } else {
            // 토큰의 정보 확인
            if (!jwtProvider.getTokenData(accessToken).get("roles").equals("tester")) {
                throw new JwtException("유효한 접근이 아님");
            }
            // access token 성공시
            Claims accessTokenData = jwtProvider.getTokenData(accessToken);
            UUID id = UUID.fromString(String.valueOf(accessTokenData.getSubject()));
            // 토큰 재발급
            accessToken = jwtProvider.createJwtAccessToken(id, "tester");
        }

        // client에 전송할 header setting
        response.setHeader("Authorization", accessToken);

        filterChain.doFilter(request, response);

    }

    // prefix 자르기
    private String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(ACCESS_TOKEN);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}