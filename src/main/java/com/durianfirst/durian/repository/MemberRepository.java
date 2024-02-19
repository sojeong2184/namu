package com.durianfirst.durian.repository;


import com.durianfirst.durian.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String>, QuerydslPredicateExecutor<Member> {
    //로그인시 MemberRole을 같이 로딩
    @EntityGraph(attributePaths = "roleSet")
    @Query("select m from Member m where m.mid = :mid and m.msocial = false")
    Optional<Member> getWithRoles(String mid);
    //로그인 시에 MemberRole을 같이 로딩 할 수 있도록 하는 메소드
    /* 직접 로그인할 때는 소셜 서비스를 통해 회원 가입된 회원들이 같은 패스워드를 가지므로 일반 회원들만
    *  가져오도록 social 속성값이 false인 사용자들만을 대상으로 처리*/
    
    //아이디로 회원정보 조회
    @EntityGraph(attributePaths = "roleSet")
    Member findByMid(String mid);

    @EntityGraph(attributePaths = "roleSet")
    Member findByMidAndMemailAndMname(String mid,String memail,String mname);

    //총회원수
    @Query("SELECT COUNT(m) FROM Member m")
    Long countMembers();
}
