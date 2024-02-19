package com.durianfirst.durian.repository;

import com.durianfirst.durian.entity.Member;
import com.durianfirst.durian.entity.MemberImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberImgRepository extends JpaRepository<MemberImg,String> {

    MemberImg findByMember(Member member);
}
