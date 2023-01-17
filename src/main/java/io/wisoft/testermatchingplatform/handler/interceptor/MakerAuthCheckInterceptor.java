package io.wisoft.testermatchingplatform.handler.interceptor;

import io.jsonwebtoken.Claims;
import io.wisoft.testermatchingplatform.jwt.JwtAuthException;
import io.wisoft.testermatchingplatform.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RequiredArgsConstructor
public class MakerAuthCheckInterceptor implements HandlerInterceptor {


    public static final String Authorization = "AUTHORIZATION";
    public static final String BEARER_PREFIX = "Bearer ";

    private final JwtProvider jwtProvider;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        // 인가 처리하기
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        String bearerToken = request.getHeader(Authorization);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            String token = bearerToken.substring(7);
            if (jwtProvider.validMakerToken(token)) {
                String refreshToken = jwtProvider.refreshMakerToken(token);
                response.setHeader(Authorization, BEARER_PREFIX + refreshToken);
                return true;
            }
        }
        throw new JwtAuthException("Auth Fail");
    }
}
