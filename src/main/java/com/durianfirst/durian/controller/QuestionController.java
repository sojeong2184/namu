package com.durianfirst.durian.controller;

import com.durianfirst.durian.dto.PageRequestedDTO;
import com.durianfirst.durian.dto.PageResponsedDTO;
import com.durianfirst.durian.dto.QuestionDTO;
import com.durianfirst.durian.entity.Member;
import com.durianfirst.durian.entity.Question;
import com.durianfirst.durian.repository.MemberRepository;
import com.durianfirst.durian.service.AnswerService;
import com.durianfirst.durian.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/question/register") //등록처리
    public String registerGET(Principal principal) {

        if (principal != null) {

            String mid = principal.getName();
            Member member = memberRepository.findByMid(mid);

            log.info("유저 아이디 : " + principal.getName());

        } else {
            return "member/login";
        }
        return "question/register";
    }

    @PostMapping("/question/register")
    public String registerPost(@Valid QuestionDTO questionDTO, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) { //@Valid에서 문제 시 모든 에러를 errors 이름으로 redirectAttributes.addAttribute에 추가, 전송
            log.info("has errors.......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/question/register";
        }

        Long qno = questionService.register(questionDTO);

        redirectAttributes.addFlashAttribute("result", qno);

        return "redirect:/question/qna";
    }

    @GetMapping("/question/modify")
    public String modify(Long qno,PageRequestedDTO pageRequestDTO,Principal principal, Model model) {

        QuestionDTO questionDTO = questionService.readOne(qno);
        // DB에서 가져온 question의 카테고리 정보를 모델에 추가
        model.addAttribute("savedCategory", questionDTO.getQcate());
        // 기존의 카테고리 정보 가져오기 (예시로 리스트를 가져오도록 했습니다)
        List<String> savedCategories = Arrays.asList("기타","배송", "포인트", "신고");
        String mid = principal.getName();
        Member member = memberRepository.findByMid(mid);
        model.addAttribute("dto", questionDTO);
        model.addAttribute("member", member);
        model.addAttribute("savedCategories", savedCategories);

        return "/question/modify";
    }

    @PreAuthorize("principal.username == #questionDTO.member.mid")
    @PostMapping("/question/modify")
    public String modify(PageRequestedDTO pageRequestDTO,
                         @Valid QuestionDTO questionDTO,
                         BindingResult bindingResult, //유효성검사저장
                         RedirectAttributes redirectAttributes) {

        log.info("question modify post......" + questionDTO);

        if (bindingResult.hasErrors()) {
            log.info("has errors.....");

            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            redirectAttributes.addAttribute("qno", questionDTO.getQno());

            return "redirect:/question/modify?" + link;//수정시 문제있을때 수정페이지로
        }

        questionService.modify(questionDTO);

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("qno", questionDTO.getQno());

        return "redirect:/question/answer"; //수정시 문제없을시
    }

    @PostMapping("/question/remove")
    public String remove(Long qno, RedirectAttributes redirectAttributes) {

        log.info("remove post.. " + qno);

        questionService.remove(qno);

        redirectAttributes.addFlashAttribute("result", "remove");

        return "redirect:/question/qna";
    }

    @GetMapping("/question/qna")
    public void list3(PageRequestedDTO pageRequestDTO, Model model) {

        PageResponsedDTO<QuestionDTO> responseDTO = questionService.list(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);
    }

    @GetMapping("/question/answer")
    public String answer(Long qno, @RequestParam(required = false) String password, Model model, Principal principal) {

        if (principal != null) {
            String mid = principal.getName();
            Member member = memberRepository.findByMid(mid);

            log.info("유저 아이디 : " + principal.getName());
        } else {
            return "member/login";
        }

        Question question = this.answerService.getQuestion(qno);
        model.addAttribute("question", question);

        QuestionDTO questionDTO = answerService.create(qno);
        model.addAttribute("dto", questionDTO);
        questionService.updateView(qno);

        //질문이 비밀글이고, 비밀번호가 설정되어 있으면
        if (questionDTO.getSecret() && questionDTO.getPassword() != null) {
            //비밀번호가 입력되지 않았거나, 입력된 비밀번호가 일치하지 않으면
            if (password == null || !questionDTO.isPasswordValid(password, passwordEncoder)) {
                model.addAttribute("question", question);
                model.addAttribute("dto", questionDTO);
                //비밀번호 입력 폼으로 이동
                return "question/passwordForm";
            }
        }
        //일치하거나, 비밀번호가 없는경우에는 답변 페이지로 이동
        return "question/answer";
    }

    @PostMapping("/question/checkPassword")
    public String checkPassword(@RequestParam Long qno, @RequestParam String password, Model model, RedirectAttributes redirectAttributes) {
        QuestionDTO questionDTO = answerService.create(qno);
        Question question = this.answerService.getQuestion(qno);

        // 질문이 비밀글이고, 비밀번호가 설정되어 있으면
        if (questionDTO.getSecret() && questionDTO.getPassword() != null) {
            model.addAttribute("dto", questionDTO);
            model.addAttribute("question", question);
            log.info("question",qno);
            // 입력된 비밀번호가 일치하지 않으면
            if (!questionDTO.isPasswordValid(password, passwordEncoder)) {
                model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
                return "question/passwordForm";
            }
        }
        // 비밀번호가 일치하거나, 비밀번호가 없는 경우에는 답변 페이지로 이동
        model.addAttribute("question", question);
        model.addAttribute("dto", questionDTO);
        log.info("question",qno);
        return "question/answer";
    }



    /* 지금 사용 안함 */
    /*@GetMapping("/question/list")
    public void list(PageRequestedDTO pageRequestDTO, Model model) {

        PageResponsedDTO<QuestionDTO> responseDTO = questionService.list(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);

    }*/

    /*@GetMapping("/question/list2")
    public void list2(PageRequestedDTO pageRequestDTO, Model model) {

        PageResponsedDTO<QuestionDTO> responseDTO = questionService.list(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);

    }*/


}