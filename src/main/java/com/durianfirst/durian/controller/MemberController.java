package com.durianfirst.durian.controller;

import com.durianfirst.durian.dto.MemberJoinDTO;
import com.durianfirst.durian.entity.Member;
import com.durianfirst.durian.repository.MemberRepository;
import com.durianfirst.durian.service.MemberImgService;
import com.durianfirst.durian.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.SecureRandom;
import java.util.Base64;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final MemberImgService memberImgService;

    @GetMapping("/member/login")
    public String login(@RequestParam(value = "error", required = false)String error, @RequestParam(value = "exception", required = false)String exception, String logout, Model model){
        log.info("================login get================");
        log.info("logout : " + logout);

        if(logout != null){
            log.info("====================user logout=====================");
        }

        /* 에러 */
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "member/login";
    }

    @GetMapping("/member/register")
    public void register(Model model){
        log.info("==================register get=====================");
        model.addAttribute("memberJoinDTO", new MemberJoinDTO());
    }
    @PostMapping("/member/register")
    public String registerPost(@Valid MemberJoinDTO memberJoinDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        log.info("========================register post============================");
        log.info(memberJoinDTO);

        if(bindingResult.hasErrors()){

            log.info("Validation errors: {}", bindingResult.getAllErrors());

            //회원가입 실패시 입력 데이터 값을 유지
            model.addAttribute("memberJoinDTO",memberJoinDTO);
            return "member/register";
        }

        try{
            memberService.register(memberJoinDTO);
        }catch(MemberService.MidExistException e){
            redirectAttributes.addFlashAttribute("error", "mid");
            return "redirect:/member/register"; //MidExistException 발생 시 /member/register로 redirect
        }

        redirectAttributes.addFlashAttribute("result", "success");

        return "redirect:/member/login"; //회원가입 후 로그인
    }

    @GetMapping("/member/checkPassword")
    public String memberWithdrawalForm() {
        return "member/checkPassword";
    }

    @PostMapping("/member/checkPassword")
    public String memberWithdrawal(@RequestParam String password, Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        boolean result = memberService.deleteMember(userDetails.getUsername(), password);

        if (result) {
            return "redirect:/";
        } else {
            model.addAttribute("mpw", "비밀번호가 맞지 않습니다.");
            return "member/checkPassword";
        }
    }

    @GetMapping("/member/forgotPassword")
    public String showForgotPasswordForm() {

        log.info("showForgotPasswordForm() get 메서드 호출됨");
        return "member/forgotPassword";
    }

    @PostMapping("/member/forgotPassword")
    public String processForgotPassword(@RequestParam String mid, @RequestParam String memail, @RequestParam String mname, Model model) {

       log.info("processForgotPassword() post 메서드 호출됨");

        // 입력된 정보로 사용자를 조회
        log.info("사용자 조회: mid={}, memail={}, mname={}", mid, memail, mname);
        Member member = memberRepository.findByMidAndMemailAndMname(mid, memail, mname);

        if (member != null) {
            // 일치하는 사용자가 있다면 임시 비밀번호 생성 및 저장
            String tempPassword = generateTempPassword();
            log.info("임시 비밀번호 생성: tempPassword={}", tempPassword);

            member.setMpw(encodePassword(tempPassword)); // 비밀번호를 안전하게 해싱하여 저장
            memberRepository.save(member);

            // 모달창에 임시 비밀번호 전달
            model.addAttribute("tempPassword",  tempPassword);

            log.info("비밀번호 재설정 성공: mid={}, memail={}, mname={}", mid, memail, mname);
            return "member/forgotPassword";
        } else {
            // 사용자가 일치하지 않는 경우 에러 메시지 반환
            log.info("일치하는 사용자 없음: mid={}, memail={}, mname={}", mid, memail, mname);
            model.addAttribute("error", "일치하는 사용자가 없습니다.");

            log.info("비밀번호 재설정 실패: mid={}, memail={}, mname={}", mid, memail, mname);
            return "member/forgotPassword";
        }

    }

    private String generateTempPassword() {
        // 보안적인 측면에서 안전한 방법으로 임시 비밀번호 생성
        SecureRandom secureRandom = new SecureRandom();
        byte[] tempPasswordBytes = new byte[8];
        secureRandom.nextBytes(tempPasswordBytes);
        return Base64.getEncoder().encodeToString(tempPasswordBytes);
    }

    private String encodePassword(String mpw) {
        // Spring Security를 사용하여 비밀번호를 안전하게 해싱
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(mpw);
    }
}
