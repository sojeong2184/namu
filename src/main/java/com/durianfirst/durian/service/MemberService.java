package com.durianfirst.durian.service;

import com.durianfirst.durian.dto.MemberJoinDTO;
import com.durianfirst.durian.dto.PageRequestDTO;
import com.durianfirst.durian.dto.PageResultDTO;
import com.durianfirst.durian.entity.Member;

public interface MemberService {

    static class MidExistException extends Exception{} //static으로 선언해 MidExistException가 필요한 곳에 쓰이도록 함

    void register(MemberJoinDTO memberJoinDTO) throws MidExistException; //만일 같은 아이디가 존재하면 예외 발생

    PageResultDTO<MemberJoinDTO, Member> getList(PageRequestDTO requestDTO);

    MemberJoinDTO readOne(String mid);

    String updateMember(MemberJoinDTO memberJoinDTO);

    boolean deleteMember(String mid, String mpw);

    Member getUser(String mid);

    String updatePassword(MemberJoinDTO memberJoinDTO);

    boolean checkPassword(String username, String enteredPassword);

    Object count(); //총회원수

    default Member dtoToEntity(MemberJoinDTO dto) {
        Member entity = Member.builder()
                .mid(dto.getMid())
                .mname(dto.getMname())
                .maddress(dto.getMaddress())
                .mbirthday(dto.getMbirthday())
                .memail(dto.getMemail())
                .mpw(dto.getMpw())
                .mphone(dto.getMphone())
                .build();
        return entity;
    }

    default MemberJoinDTO entityToDto(Member entity) {

        MemberJoinDTO dto = MemberJoinDTO.builder()
                .mid(entity.getMid())
                .mname(entity.getMname())
                .maddress(entity.getMaddress())
                .mbirthday(entity.getMbirthday())
                .memail(entity.getMemail())
                .mname(entity.getMname())
                .mpw(entity.getMpw())
                .mphone(entity.getMphone())
                .build();

        return dto;

    }
}
