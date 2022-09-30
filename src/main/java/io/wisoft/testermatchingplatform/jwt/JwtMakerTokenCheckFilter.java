//package io.wisoft.testermatchingplatform.jwt;
//
//import io.jsonwebtoken.Claims;
//import io.wisoft.testermatchingplatform.domain.maker.MakerRepository;
//import io.wisoft.testermatchingplatform.handler.exception.auth.JwtTokenException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.transaction.Transactional;
//import java.io.IOException;
//
//@RequiredArgsConstructor
////@WebFilter(urlPatterns = "/makers/*")
//public class JwtMakerTokenCheckFilter extends OncePerRequestFilter {
//
//    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
//
//    public static final String REFRESH_TOKEN = "REFRESH_TOKEN";
//    public static final String BEARER_PREFIX = "Bearer ";
//    private final JwtTokenProvider jwtTokenProvider;
//
//    private final MakerRepository makerRepository;
//
//    @Override
//    @Transactional
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//        String accessToken = resolveAccessToken(request);
//        String refreshToken = resolveRefreshToken(request);
//        Long id;
//        // 1. access Token 만료시
//        if (!jwtTokenProvider.isValidToken(accessToken)) {
//            System.out.println("access token 만료");
//            if (jwtTokenProvider.isValidToken(refreshToken)){
//                System.out.println("refresh token은 유효");
//                // refresh Token의 id를 확인
//                Claims refreshTokenData = jwtTokenProvider.getTokenData(refreshToken);
//                id = Long.parseLong(refreshTokenData.getSubject());
//                String refreshTokenFromDB = makerRepository.getRefreshToken(id);
//                // db토큰과 client토큰이 같으면
//                if (refreshTokenFromDB.equals(refreshToken)){
//                    // 새 토큰 발급
//                    accessToken = jwtTokenProvider.createJwtAccessToken(id,"maker");
//                    refreshToken = jwtTokenProvider.createJwtRefreshToken(id);
//                    makerRepository.setRefreshToken(id, refreshToken);
//                }
//                // 변조 토큰
//                else{
//                    throw new JwtTokenException("잘못된 토큰");
//                }
//            }
//            // refresh 토큰도 만료
//            else{
//                throw new JwtTokenException("jwt 토큰 만료");
//            }
//        }else{
//            // 토큰의 정보 확인
//            if (!jwtTokenProvider.getTokenData(accessToken).get("roles").equals("maker")){
//                throw new JwtTokenException("유효한 접근이 아님");
//            }
//            // access token 성공시
//            Claims refreshTokenData = jwtTokenProvider.getTokenData(refreshToken);
//            id = Long.parseLong(refreshTokenData.getSubject());
//            // 토큰 재발급
//            accessToken = jwtTokenProvider.createJwtAccessToken(id,"maker");
//            refreshToken = jwtTokenProvider.createJwtRefreshToken(id);
//            makerRepository.setRefreshToken(id, refreshToken);
//        }
//
//        // client에 전송할 header setting
//        response.setHeader("ACCESS_TOKEN", accessToken);
//        response.setHeader("REFRESH_TOKEN", refreshToken);
//
//        filterChain.doFilter(request, response);
//
//    }
//
//    // prefix 자르기
//    private String resolveAccessToken(HttpServletRequest request) {
//        String bearerToken = request.getHeader(ACCESS_TOKEN);
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//
//    private String resolveRefreshToken(HttpServletRequest request) {
//        String bearerToken = request.getHeader(REFRESH_TOKEN);
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//}
