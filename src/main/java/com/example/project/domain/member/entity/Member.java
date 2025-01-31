package com.example.project.domain.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long member_id;

    String email;
    String password;
    String nickname;
    String profile_url;
    String role;
    String provider;
    String provider_id;
    String refreshToken;

    }
