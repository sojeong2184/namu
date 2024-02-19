package com.durianfirst.durian.controller;

import com.durianfirst.durian.dto.PageRequestedDTO;
import com.durianfirst.durian.dto.PageResponsedDTO;
import com.durianfirst.durian.dto.QuestionDTO;
import com.durianfirst.durian.entity.Answer;
import com.durianfirst.durian.entity.AnswerForm;
import com.durianfirst.durian.entity.Member;
import com.durianfirst.durian.entity.Question;
import com.durianfirst.durian.service.AnswerService;
import com.durianfirst.durian.service.MemberService;
import com.durianfirst.durian.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
@Log4j2
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    private final QuestionService questionService;

    private final MemberService memberService;

    @GetMapping("/answer/create")
    public void create(Long qno, @ModelAttribute PageRequestedDTO pageRequestedDTO, Model model) {

        Question question = this.answerService.getQuestion(qno);
        model.addAttribute("question", question);

        QuestionDTO questionDTO = answerService.create(qno);

        log.info(questionDTO);

        model.addAttribute("dto", questionDTO);
    }

    @PostMapping("/answer/create/{qno}")
    public String createAnswer(Model model, @PathVariable("qno") Long qno, @Valid AnswerForm answerForm,
                               BindingResult bindingResult, Principal principal) {

        Question question = this.answerService.getQuestion(qno);
        Member member = this.memberService.getUser(principal.getName());

        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "admin/answer/create";
        }

        Answer answer = this.answerService.createa(question, answerForm.getAcontent(), member);

        return String.format("redirect:/admin/answer/create?qno=" + qno,
                answer.getAquestion().getQno(), answer.getAno());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/answer/delete/{ano}")
    public String answerDelete(Principal principal, @PathVariable("ano") Long ano) {
        Answer answer = this.answerService.getAnswer(ano);
        if (!answer.getMember().getMid().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.answerService.delete(answer);


        return String.format("redirect:/admin/answer/create?qno=%s", answer.getAquestion().getQno());
    }

    @GetMapping("/answer/list")
    public void list(PageRequestedDTO pageRequestedDTO, Model model){
        PageResponsedDTO<QuestionDTO> responseDTO = questionService.list(pageRequestedDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);
    }

    /* 사용 안함 */
    /*@GetMapping("/answer/list2")
    public void list2(PageRequestedDTO pageRequestedDTO, Model model) {

        PageResponsedDTO<QuestionDTO> responseDTO = questionService.list(pageRequestedDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);
    }*/

}
