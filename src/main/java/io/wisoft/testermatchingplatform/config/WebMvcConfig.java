package io.wisoft.testermatchingplatform.config;

import io.wisoft.testermatchingplatform.handler.interceptor.MakerAuthCheckInterceptor;
import io.wisoft.testermatchingplatform.handler.interceptor.TesterAuthCheckInterceptor;
import io.wisoft.testermatchingplatform.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@EnableWebMvc
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .exposedHeaders("AUTHORIZATION")
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(testerAuthCheckInterceptor())
                .addPathPatterns("/testers/**");
        registry.addInterceptor(makerAuthCheckInterceptor())
                .addPathPatterns("/makers/**");
    }

    @Bean
    public TesterAuthCheckInterceptor testerAuthCheckInterceptor() {
        return new TesterAuthCheckInterceptor(jwtProvider());
    }

    @Bean
    public MakerAuthCheckInterceptor makerAuthCheckInterceptor() {
        return new MakerAuthCheckInterceptor(jwtProvider());
    }

    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider();
    }
}
