package com.durianfirst.durian.service;

import com.durianfirst.durian.dto.HeartDTO;
import com.durianfirst.durian.entity.Heart;
import com.durianfirst.durian.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService {

    private final HeartRepository heartRepository;


    @Override
    public Long register(HeartDTO heartDTO) {

        log.info("찜 등록");
        log.info(heartDTO);

        Heart entity = dtoToEntity(heartDTO);

        log.info(entity);

        heartRepository.save(entity);

        return entity.getHno();

    }

    @Override
    public HeartDTO read(Long hno) {

        Optional<Heart> result = heartRepository.findById(hno);

        return result.isPresent()? entityToDto(result.get()): null;
    }

    @Override
    public void remove(Long hno) {
        heartRepository.deleteById(hno);
    }



}
