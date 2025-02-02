package com.example.project.domain.member.dto;

import com.example.project.domain.member.entity.Member;
import lombok.Getter;

@Getter
public class MemberDto {
    private String name;
    public MemberDto(Member member) {
        this.name = member.getName();
    }
}
