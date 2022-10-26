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

    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String BEARER_PREFIX = "Bearer ";
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//        System.out.println("request.toString() = " + request.toString());
//
//        System.out.println("request Method: " + request.getMethod());

        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "Origin, X-Requested-With, Content-Type, Accept, ACCESS_TOKEN");
        response.setHeader("Access-Control-Expose-Headers", "ACCESS_TOKEN");

//        if (request.getMethod().equals("OPTIONS")) {
//            response.setStatus(HttpServletResponse.SC_OK);
//        } else {



        String accessToken = resolveAccessToken(request);
        System.out.println("accessToken = " + accessToken);
        if (request.getRequestURI().equals("/makers/login") || request.getRequestURI().equals("/makers/register")) {
            filterChain.doFilter(request, response);
        }
        else if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
        }else {
            // access Token 만료시
            if (!jwtProvider.isValidToken(accessToken)) {
                System.out.println("access token 만료");
                throw new JwtException("토큰 만료");
            } else {
                // 토큰의 정보 확인
                if (!jwtProvider.getTokenData(accessToken).get("roles").equals("maker")) {
                    throw new JwtException("유효한 접근이 아님");
                }
                // access token 성공시
                Claims accessTokenData = jwtProvider.getTokenData(accessToken);
                UUID id = UUID.fromString(String.valueOf(accessTokenData.getSubject()));
                // 토큰 재발급
                accessToken = jwtProvider.createJwtAccessToken(id, "maker");
            }
            // client에 전송할 header setting
            response.setHeader(ACCESS_TOKEN, accessToken);
            filterChain.doFilter(request, response);
        }

    }


//    }

    // prefix 자르기
    private String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(ACCESS_TOKEN);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}