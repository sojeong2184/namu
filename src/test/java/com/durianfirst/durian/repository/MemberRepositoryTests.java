package com.durianfirst.durian.repository;

import com.durianfirst.durian.constant.MemberRole;
import com.durianfirst.durian.entity.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.IntStream;

@Log4j2
@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertMembers(){
        IntStream.rangeClosed(1,10).forEach(i -> {

            Member member = Member.builder()
                    .mid("member" + i)
                    .mpw(passwordEncoder.encode("1111"))
                    .memail("member" + i + "@email.com")
                    .build();

            member.addRole(MemberRole.USER);

            if(i>5){
                member.addRole(MemberRole.ADMIN);
            }
            memberRepository.save(member);
        });
    }//insertMembers

    @Test
    public void testRead(){

        Optional<Member> result = memberRepository.getWithRoles("member2");

        Member member = result.orElseThrow();

        log.info(member);
        log.info(member.getRoleSet());

        member.getRoleSet().forEach(memberRole -> log.info(memberRole.name()));


    }
}
