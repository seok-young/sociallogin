package com.example.project.domain.member.dto;

import com.example.project.domain.member.entity.Member;
import lombok.Getter;

@Getter
public class MemberDto {
    private String name;
    private String email;
    private String providerId;

    public MemberDto(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.providerId = member.getProviderId();

    }
}
