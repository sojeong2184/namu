package com.durianfirst.durian.service;

import com.durianfirst.durian.dto.PageRequestedDTO;
import com.durianfirst.durian.dto.PageResponsedDTO;
import com.durianfirst.durian.dto.QuestionDTO;
import com.durianfirst.durian.entity.Question;
import com.durianfirst.durian.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final ModelMapper modelMapper;
    private final QuestionRepository questionRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Question> getList() {
        return this.questionRepository.findAll();

    }

    @Override
    public boolean checkPassword(Long qno, String password) {
        Question question = questionRepository.findById(qno).orElse(null);
        return question != null && question.getPassword().equals(password);
    }

    /* 조회수 */
    @Override
    @Transactional
    public int updateView(Long qno) {
        return questionRepository.updateView(qno);
    }

    @Override
    public Long register(QuestionDTO questionDTO) {
        //DTO에서 엔티티로 변환
        Question question = modelMapper.map(questionDTO, Question.class);

        //비밀번호 암호화
        if(questionDTO.getPassword() != null){
            question.encryptPassword(passwordEncoder);
        }

        //질문 저장
        Long qno = questionRepository.save(question).getQno();

        return qno;
    }

    @Override
    public QuestionDTO readOne(Long qno) {

        Optional<Question> result = questionRepository.findById(qno);

        Question question = result.orElseThrow();

        QuestionDTO questionDTO = modelMapper.map(question, QuestionDTO.class);

        return questionDTO;
    }

    @Override
    public void modify(QuestionDTO questionDTO) {

        Optional<Question> result = questionRepository.findById(questionDTO.getQno());

        Question question = result.orElseThrow();

        question.change(questionDTO.getQtitle(), questionDTO.getQcontent());

        questionRepository.save(question);
    }

    @Override
    public void remove(Long qno) {

        questionRepository.deleteById(qno);
    }

    @Override
    public PageResponsedDTO<QuestionDTO> list(PageRequestedDTO pageRequestedDTO) {

        String[] types = pageRequestedDTO.getTypes();
        String keyword = pageRequestedDTO.getKeyword();
        Pageable pageable = pageRequestedDTO.getPageable("qno");

        Page<Question> result = questionRepository.searchAll(types, keyword, pageable);

        List<QuestionDTO> dtoList = result.getContent().stream()
                .map(question -> modelMapper.map(question, QuestionDTO.class)).collect(Collectors.toList());


        return PageResponsedDTO.<QuestionDTO>withAll()
                .pageRequestedDTO(pageRequestedDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }

    @Override
    public boolean existsById(Long qno) {
        return questionRepository.existsById(qno);
    }

    @Override
    public Object count() {
        questionRepository.count();
        return questionRepository.count();
    }

    @Override
    // 사용자가 작성한 글만을 조회하는 메서드
    public List<Question> getQuestionsByMid(String mid) {
        return questionRepository.findByMember_Mid(mid);
    }
}
