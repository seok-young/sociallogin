package com.example.project.domain.naver.controller;

import com.example.project.domain.naver.service.NaverService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class NaverController {

    private final NaverService naverService;

    @Value("${spring.oauth2.naver.client-id}")
    private String clientId;

    @Value("${spring.oauth2.naver.url.redirect-uri}")
    private String redirectURI;

//    @GetMapping("/api/auth/naver")
//    public void loginPage(HttpServletResponse response) throws IOException {
//        // redirect_uri 인코딩
//        String encodedRedirectUri = URLEncoder.encode(redirectURI, "UTF-8");
//
//        // 최종 URL 생성
//        String url = String.format("https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=%s&state=123456&redirect_uri=%s",
//                clientId, encodedRedirectUri);
//
//        // URL로 리디렉션
//        response.sendRedirect(url);
//    }

    // 네이버 사용자 인증 후
    @GetMapping("/api/auth/naver")
    public void loginCallback(@RequestParam("code") String code, @RequestParam("state") String state, HttpServletResponse response) throws IOException, InterruptedException {
        // code와 state 값 확인
        System.out.println("Code: " + code);
        System.out.println("State: " + state);

        // 액세스 토큰 요청 url
        String token_url = "https://nid.naver.com/oauth2.0/token?"
                + "grant_type=authorization_code"
                + "&client_id=hjXAKRZSt03GUwcpKpVm"
                + "&client_secret=cOZFIDt4t9"
                + "&code=" + code
                + "&state=" + state;

        // 액세스 토큰 요청
        String token = naverService.askToken(token_url);
        System.out.println("token: " + token);

        // json 객체 변환 >> 이후에 Resttemplate 활용 예정
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(token);

        // 액세스 토큰 추출
        String accessToken = jsonNode.get("access_token").asText();
        System.out.println("Access Token: " + accessToken);

        // 사용자 정보 요청
        String userInfo = naverService.askUserInfo(accessToken);
        System.out.println("User Info: " + userInfo);

        // json 객체 변환 >> 이후에 Resttemplate 활용 예정
        ObjectMapper objectMapper_u = new ObjectMapper();
        JsonNode jsonNode_u = objectMapper_u.readTree(userInfo);
        JsonNode jsonNode_r = jsonNode_u.get("response");

        // 사용자 정보 추출
        String id = jsonNode_r.get("id").asText();
        String nickname = jsonNode_r.get("nickname").asText();
        String email = jsonNode_r.get("email").asText();
        String name = jsonNode_r.get("name").asText();

        System.out.println("id: " + id);
        System.out.println("nickname: " + nickname);
        System.out.println("email: " + email);
        System.out.println("name: " + name);

        // 사용자 정보 있는지 확인(email 활용)
        boolean exists = naverService.isMemberExists(email);

        if (exists) {
            System.out.println("회원 존재");
        } else {
            System.out.println("회원 비존재");
        }


    }
}