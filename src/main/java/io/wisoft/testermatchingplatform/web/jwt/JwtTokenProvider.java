//package io.wisoft.testermatchingplatform.web.jwt;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.wisoft.testermatchingplatform.domain.tester.Tester;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class JwtTokenProvider implements InitializingBean {
//
//    // Logger Setting
//    private final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
//
//    // Secret Key
//    private static String ENCRYPT_STRING = "pretty";
//
//    /// Auth Key
//    private static final String AUTHORITIES_KEY = "auth";
//
//    //Mapper
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    // Tester 정보를 이용해서 JWT 생성
//    public String createLoginToken(Tester tester) {
//
//        long curTime = System.currentTimeMillis();
//
//        return
//
//    }
//
//
//
//}
