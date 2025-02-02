package com.example.project.domain.member.controller;

import com.example.project.domain.member.dto.MemberRequest;
import com.example.project.domain.member.entity.Member;
import com.example.project.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class ApiV1MemberController {

    private final MemberService memberService;

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
    public void login() {
        System.out.println("login");
    }

    @GetMapping("/logout")
    public void logout() {
        System.out.println("logout");
    }

    @GetMapping("/me")
    public void me() {
        System.out.println("me");
    }
}
