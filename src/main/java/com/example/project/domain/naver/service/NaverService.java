package com.example.project.domain.naver.service;

import com.example.project.domain.member.entity.Member;
import com.example.project.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NaverService {

    private final MemberRepository memberRepository;

    // 토큰 요청
    public static String askToken(String token_url) throws IndexOutOfBoundsException, InterruptedException, IOException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(token_url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response =client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // 사용자정보 요청
    public String askUserInfo(String accessToken) throws IOException, InterruptedException {

        // 사용자 정보 요청 URL
        String URL = "https://openapi.naver.com/v1/nid/me";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("Authorization", "Bearer " + accessToken)
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response =client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // 회원 유무 확인
    public boolean isMemberExists(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);

        return member.isPresent();
    }

}
