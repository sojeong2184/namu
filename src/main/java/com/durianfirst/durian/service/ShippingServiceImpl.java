package com.durianfirst.durian.service;


import com.durianfirst.durian.dto.PageRequestDTO;
import com.durianfirst.durian.dto.PageResultDTO;
import com.durianfirst.durian.dto.ShippingDTO;
import com.durianfirst.durian.entity.Member;
import com.durianfirst.durian.entity.QShipping;
import com.durianfirst.durian.entity.Shipping;
import com.durianfirst.durian.repository.MemberRepository;
import com.durianfirst.durian.repository.ShippingRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ShippingServiceImpl  implements ShippingService {

    private final ModelMapper modelMapper;                  // 엔티티(Board)와 DTO(BoardDTO) 간의 변환
    private final ShippingRepository shippingRepository;    // 엔티티 객체 연결
    private final MemberRepository memberRepository;

    @Override
    public Long register(ShippingDTO shippingDTO) {

        Shipping shipping = modelMapper.map(shippingDTO, Shipping.class);
        Member member = memberRepository.findByMid(shippingDTO.getMid());
        // 기본 배송지 체크 여부 확인
        if (shippingDTO.isDefaultShipping()) { //체크 여부를 확인함
            // 현재 회원의 다른 배송지들 중에서 기본 배송지를 해제
            shippingRepository.updateDefaultShipping(shippingDTO.getMid());
        }
        shipping.setMember(member);
        return shippingRepository.save(shipping).getSno();
    }

    // 기본 배송지 업데이트
    @Override
    public void updateDefaultShipping(String mid) {
        // 현재 기본 배송지를 해제하는 로직 추가
        shippingRepository.updateDefaultShipping(mid);
    }


    // 조회 -> mid(사용자 아이디)로 배송 주소 DTO 전체 리스트 리턴
    @Override
    public List<ShippingDTO> readAll(String mid) {
        List<Shipping> shippingList = shippingRepository.listOfShipping(mid);

        List<ShippingDTO> shippingDTOList = shippingList.stream()
                .map(data -> modelMapper.map(data, ShippingDTO.class))
                .collect(Collectors.toList());

        return shippingDTOList;
    }


    @Override
    public PageResultDTO<ShippingDTO, Shipping> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("sno").descending());

        BooleanBuilder booleanBuilder = getSerch(requestDTO);

        Page<Shipping> result = shippingRepository.findAll(booleanBuilder, pageable);

        Function<Shipping, ShippingDTO> fn = (entity -> ShippingDTO.of((entity)));

        return new PageResultDTO<>(result, fn);
    }

    private BooleanBuilder getSerch(PageRequestDTO requestDTO) {

        String type = requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QShipping qShipping = QShipping.shipping;

        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qShipping.sno.gt(0L);

        booleanBuilder.and(expression);

        if (type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();

          /*if (type.contains("t")){
            conditionBuilder.or(qShipping.title.contains(keyword));
        }
        if (type.contains("c")){
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        }
        if (type.contains("w")){
            conditionBuilder.or(qGuestbook.writer.contains(keyword));
        }*/

        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }

    @Override
    public ShippingDTO readOne(Long sno) {
        Optional<Shipping> result = shippingRepository.findBySno(sno);
        Shipping shipping = result.orElseThrow();
        ShippingDTO shippingDTO = modelMapper.map(shipping, ShippingDTO.class);

        return shippingDTO;
    }

    @Override
    public void modify(ShippingDTO shippingDTO) {

        Optional<Shipping> result = shippingRepository.findBySno(shippingDTO.getSno());
        Shipping shipping = result.orElseThrow();
        shipping.change(shippingDTO.getSname(), shippingDTO.getSperson(), shippingDTO.getSzonecode(), shippingDTO.getAddress(), shippingDTO.getSaddress(), shippingDTO.getSphone());
        shippingRepository.save(shipping);

    }


    @Override
    public void remove(Long sno) {

        shippingRepository.deleteById(sno);

    }

    @Override
    public ShippingDTO getDefaultAddress(String mid) {
        // 여기에 해당 mid에 해당하는 회원의 기본 배송지 정보를 가져오는 로직을 작성

        log.info("service mid : " + mid);
        Member member = memberRepository.findByMid(mid);

        log.info("service member : " + member);

        if (member != null) {
            ShippingDTO shippingList = shippingRepository.getDefaultAddress(mid);

            log.info("service shippingList : " + shippingList);

            return shippingList;
        } else {
            // 회원이 존재하지 않을 경우 또는 기본 배송지 정보가 없을 경우 예외처리 또는 기본 값 반환
            throw new RuntimeException("해당 회원의 기본 배송지 정보를 찾을 수 없습니다.");
        }

      /* @Override
    public void modifySdefault(String mid) {

        shippingRepository.updateSdefault(mid);

    }*/
    }
}
