package com.durianfirst.durian.service;

import com.durianfirst.durian.dto.NoticeDTO;
import com.durianfirst.durian.dto.PageRequestDTO;
import com.durianfirst.durian.dto.PageResultDTO;
import com.durianfirst.durian.entity.Notice;
import com.durianfirst.durian.entity.QNotice;
import com.durianfirst.durian.repository.NoticeRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    private final NoticeRepository repository;

    @Override
    public Long register(NoticeDTO dto) {

        log.info("DTO디티오!!!!!!!!!!!!!!!!!!!!");
        log.info(dto);

        Notice entity = dtoToEntity(dto);

        log.info(entity);

        repository.save(entity);

        return entity.getNno();
    }

    @Override
    public PageResultDTO<NoticeDTO, Notice> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("nno").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDTO); //검색조건처리

        Page<Notice> result = repository.findAll(booleanBuilder, pageable);
        //Querydsl 사용

        Function<Notice, NoticeDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public NoticeDTO read(Long nno) {

        Optional<Notice> result = repository.findById(nno);

        if (result.isPresent()) {
            Notice notice = result.get();

            // 조회수 증가
            notice.addViewCount();

            // 조회수가 증가된 엔티티 저장
            repository.save(notice);

            // 엔티티를 DTO로 변환하여 반환
            return entityToDto(notice);
        } else {
            return null;
        }

        /* return result.isPresent()? entityToDto(result.get()): null;*/
    }



    @Override
    public void remove(Long nno) {
        repository.deleteById(nno);
    }

    @Override
    public void modify(NoticeDTO dto) {
        //업데이트 하는 항목은 제목, 내용만

        Optional<Notice> result = repository.findById(dto.getNno());

        if(result.isPresent()) {

            Notice entity=result.get();

            entity.changeTitle(dto.getNtitle());
            entity.changeContent(dto.getNcontent());

            repository.save(entity);
        }
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type=requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QNotice qNotice = QNotice.notice;

        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qNotice.nno.gt(0L); //nno>0 조건만 생성

        booleanBuilder.and(expression);

        if(type==null || type.trim().length() == 0) {
            //검색조건이 없을때
            return booleanBuilder;
        }

        //검색 조건을 작성하기
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if(type.contains("t")){
            conditionBuilder.or(qNotice.ntitle.contains(keyword));
        }
        if(type.contains("c")){
            conditionBuilder.or(qNotice.ncontent.contains(keyword));
        }



        //모든 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;

    }

    /* 조회수 */
    @Override
    @Transactional
    public int updateView(Long nno) {
        return repository.updateView(nno);
    }
}

