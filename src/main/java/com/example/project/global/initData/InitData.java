package com.example.project.global.initData;

//@Component
//@RequiredArgsConstructor
//public class InitData implements CommandLineRunner {
//
//    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) throws Exception {
//        String email = "blueblue987@naver.com";
//        String password = "12341234";
//        String name = "myname";
//        String nickname = "mynickname";
//        String providerId = "blueblue987";
//
//        memberRepository.findByEmail(email).ifPresentOrElse(
//                member -> System.out.println("Member already exists: " + member.getEmail()),
//                () -> {
//                    Member member = Member.builder()
//                            .email(email)
//                            .password(passwordEncoder.encode(password))
//                            .nickname(nickname)
//                            .name(name)
//                            .provider("naver")
//                            .providerId(providerId)
//                            .build();
//                    memberRepository.save(member);
//                    System.out.println("Member initialized: " + email);
//                }
//        );
//    }
//}