package com.durianfirst.durian.service;

import com.durianfirst.durian.DataNotFoundException;
import com.durianfirst.durian.constant.MemberRole;
import com.durianfirst.durian.dto.MemberJoinDTO;
import com.durianfirst.durian.dto.PageRequestDTO;
import com.durianfirst.durian.dto.PageResultDTO;
import com.durianfirst.durian.entity.Member;
import com.durianfirst.durian.entity.QMember;
import com.durianfirst.durian.repository.MemberRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final ModelMapper modelMapper;

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    /* 회원가입 */
    @Override
    public void register(MemberJoinDTO memberJoinDTO) throws MidExistException{ //mid가 존재하는 경우 MidExistException을 발생

        String mid = memberJoinDTO.getMid();

        boolean exist = memberRepository.existsById(mid); //mid가 유일한지 체크하고 문제가 생기면 MidExistException을 발생시킴

        if(exist){
            throw new MidExistException();
        }

        Member member = modelMapper.map(memberJoinDTO, Member.class);
        member.changePassword(passwordEncoder.encode(memberJoinDTO.getMpw()));
        //정상적으로 회원 가입이 된 경우 PasswordEncoder를 이용해서 입력된 패스워드를 인코딩
        member.addRole(MemberRole.USER);

        log.info("==============================");
        log.info(member);
        log.info(member.getRoleSet());

        memberRepository.save(member); //해당 아이디가 존재하면 MemberRepository의 save()는 insert가 아니라 update로 실행
    }

    /* 회원 수정(비밀번호 제외) */
    @Override
    public String updateMember(MemberJoinDTO memberJoinDTO) {

        Member member = memberRepository.findByMid(memberJoinDTO.getMid());
        member.changeName(memberJoinDTO.getMname());
        member.changeEmail(memberJoinDTO.getMemail());
        member.changePhone(memberJoinDTO.getMphone());
        member.changeAddress(memberJoinDTO.getMaddress());

        memberRepository.save(member);

        return member.getMid();
    }

    /* 비밀번호 수정 */
    @Override
    public String updatePassword(MemberJoinDTO memberJoinDTO) {

        Member member = memberRepository.findByMid(memberJoinDTO.getMid());

        // 회원 비밀번호 수정을 위한 패스워드 암호화
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePw = encoder.encode(memberJoinDTO.getMpw());
        member.changePassword(encodePw);

        memberRepository.save(member);

        return member.getMid();
    }

    /* 회원 탈퇴 */
    @Override
    public boolean deleteMember(String mid, String mpw) {

        Member member = memberRepository.findByMid(mid);

        if (passwordEncoder.matches(mpw, member.getMpw())) {
            memberRepository.delete(member);
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPassword(String username, String enteredPassword) {
        Member member = memberRepository.findByMid(username);

        // 입력한 비밀번호와 저장된 비밀번호를 비교 (passwordEncoder를 사용하여 비교)
        return passwordEncoder.matches(enteredPassword, member.getMpw());
    }

    @Override
    public Member getUser(String mid) {
        Optional<Member> siteUser = this.memberRepository.getWithRoles(mid);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }

    @Override
    public PageResultDTO<MemberJoinDTO, Member> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("mid").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDTO); //검색조건처리

        Page<Member> result = memberRepository.findAll(booleanBuilder, pageable);
        //Querydsl 사용

        Function<Member, MemberJoinDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type = requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QMember qMember = QMember.member;

        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qMember.mid.gt("0L"); //nno>0 조건만 생성

        booleanBuilder.and(expression);

        if (type == null || type.trim().length() == 0) {
            //검색조건이 없을때
            return booleanBuilder;
        }

        //검색 조건을 작성하기
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("t")) {
            conditionBuilder.or(qMember.mname.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(qMember.mid.contains(keyword));
        }
        if (type.contains("w")) {
            conditionBuilder.or(qMember.memail.contains(keyword));
        }

        //모든 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;

    }

    @Override
    public MemberJoinDTO readOne(String mid) {

        Optional<Member> result = memberRepository.findById(mid);
        Member member = result.orElseThrow(() -> new RuntimeException("mid에 해당하는 회원이 없습니다: " + mid));

        // 권한 정보를 가져와서 MemberJoinDTO에 추가
        List<String> roles = member.getRoles(); // 여기서 getRoles()는 실제 Member 엔티티에 존재하는 권한 정보를 가져오는 메서드여야 합니다.

        MemberJoinDTO memberJoinDTO = new  MemberJoinDTO(
                member.getMid(),
                member.getMpw(),
                member.getMname(),
                member.getMemail(),
                member.getMbirthday(),
                member.getMaddress(),
                member.getMphone(),
                member.isMdel(),
                member.isMsocial(),
                roles
  // 이곳에서 권한 목록을 사용합니다
        );

        return memberJoinDTO;
    }

    public Object count() {
        memberRepository.count();
        return memberRepository.count();
    }

}
