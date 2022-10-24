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
@WebFilter(value = "/makers/*")
public class JwtMakerTokenCheckFilter2 extends OncePerRequestFilter {

    public static final String ACCESS_TOKEN = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String accessToken = resolveAccessToken(request);
        if (request.getRequestURI().equals("/makers/login") || request.getRequestURI().equals("/makers/register")) {
            return;
        } else {
            // access Token 만료시
            if (!jwtTokenProvider.isValidToken(accessToken)) {
                System.out.println("access token 만료");
                throw new JwtTokenException("토큰 만료");
            } else {
                // 토큰의 정보 확인
                if (!jwtTokenProvider.getTokenData(accessToken).get("roles").equals("maker")) {
                    throw new JwtTokenException("유효한 접근이 아님");
                }
                // access token 성공시
                Claims accessTokenData = jwtTokenProvider.getTokenData(accessToken);
                UUID id = UUID.fromString(String.valueOf(accessTokenData.getSubject()));
                // 토큰 재발급
                accessToken = jwtTokenProvider.createJwtAccessToken(id, "maker");
            }
            // client에 전송할 header setting
            response.setHeader("Authorization", accessToken);
        }
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