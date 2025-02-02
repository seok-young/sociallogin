package com.example.project.domain.member.service;

import com.example.project.domain.member.entity.Member;
import com.example.project.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member join(String email, String password, String name, String nickname, String providerId) {

        // 존재하는 지 체크
        memberRepository.findByEmail(email)
                .ifPresent(member -> {
                    throw new IllegalArgumentException("member already exists with " + member);
                });

        Member member = Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .name(name)
                .provider("naver")
                .providerId(providerId)
                .build();



        return memberRepository.save(member);
    }

}
