package com.example.project.domain.member.controller;

import com.example.project.domain.member.dto.MemberDto;
import com.example.project.domain.member.dto.MemberLogin;
import com.example.project.domain.member.dto.MemberRequest;
import com.example.project.domain.member.entity.Member;
import com.example.project.domain.member.repository.MemberRepository;
import com.example.project.domain.member.service.MemberService;
import com.example.project.global.jwt.JwtProvider;
import com.example.project.global.rsData.RsData;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class ApiV1MemberController {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @PostMapping("/signup")
    public String join(@Valid @ModelAttribute MemberRequest memberRequest) {
        Member member = memberService.join(
                memberRequest.getEmail(),
                memberRequest.getPassword(),
                memberRequest.getName(),
                memberRequest.getNickname(),
                memberRequest.getProviderId());
        System.out.println("회원가입 성공"+ member.getName());
        return "signup finished";
    }

    @PostMapping("/login")
    public RsData<Void> login(@Valid @RequestBody MemberLogin memberLogin, HttpServletResponse response) {
        Member member = memberService.getMember(memberLogin.getEmail());
        String token = jwtProvider.genAccessToken(member);


//        // 토큰 DB에 저장
//        member.setAccessToken(token);
//        memberRepository.save(member);

        // 응답 데이터에 accessToken 이름으로 토큰 발급
        Cookie cookie = new Cookie("accessToken", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);

        String refreshToken = member.getRefreshToken();
            Cookie refreshTokenCookie  = new Cookie("refreshToken", refreshToken);
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(true);
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge(60 * 60);
        response.addCookie(refreshTokenCookie);

        return new RsData<>("200", "Login Success");
    }


    @GetMapping("/logout")
    public void logout() {
        System.out.println("logout");
    }

    @GetMapping("/me")
    public RsData<MemberDto> me(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String accessToken = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("accessToken")) {
                accessToken = cookie.getValue();
            }
        }
        Map<String, Object> claims = jwtProvider.getClaims(accessToken);
        System.out.println(claims);
        String email = (String) claims.get("email");
        System.out.println(email);



        Member member = this.memberService.getMember(email);

        return new RsData("200", "회원정보 조회 성공",
                new MemberDto(member));
    }
}
