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


    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String BEARER_PREFIX = "Bearer ";
    private final JwtProvider jwtProvider;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        // 인가 처리하기

        // jwt token이 있는지 확인하기
        String bearerToken = request.getHeader(ACCESS_TOKEN);
        String accessToken;
        if (bearerToken == null) {
            throw new JwtAuthException("not found access token");
        }
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            accessToken = bearerToken.substring(7);
        } else {
            throw new JwtAuthException("not created access token");
        }

        // jwt token이 유효한지 확인하기
        if (!jwtProvider.isValidToken(accessToken)) {
            throw new JwtAuthException("valid time out access token");
        }
        else {
            if (!jwtProvider.getTokenData(accessToken).get("roles").equals("maker")) {
                throw new JwtAuthException("mismatch access token");
            }

            Claims accessTokenData = jwtProvider.getTokenData(accessToken);
            UUID id = UUID.fromString(String.valueOf(accessTokenData.getSubject()));
            // refresh token
            accessToken = jwtProvider.createJwtAccessToken(id, "maker");
        }

        response.setHeader(ACCESS_TOKEN, BEARER_PREFIX + accessToken);
        return true;
    }
}
