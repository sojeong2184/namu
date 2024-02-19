package com.durianfirst.durian.controller;

import com.durianfirst.durian.entity.Member;
import com.durianfirst.durian.repository.MemberRepository;
import com.durianfirst.durian.service.ItemService;
import com.durianfirst.durian.service.MemberService;
import com.durianfirst.durian.service.OrderService;
import com.durianfirst.durian.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@Log4j2
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private QuestionService questionService;

    @GetMapping({"/admin/index","/admin"})
    public String index(Principal principal, Model model){
        if (principal != null) {

            String mid = principal.getName();                   //mid에 로그인 정보를 받음
            Member member = memberRepository.findByMid(mid);    //findbymid로 유저 정보 찾아서 member에 저장

            model.addAttribute("member",member);    //model로 member에 담긴 정보를 인덱스 프론트에 넘김

            //총회원수
            model.addAttribute("membercount", memberService.count());
            //총물품수
            model.addAttribute("itemcount",itemService.count());
            //총주문수
            model.addAttribute("ordercount",orderService.count());
            //총질문수
            model.addAttribute("questioncount",questionService.count());

            return "admin/index";
        } else {
            // 로그인이 안 된 경우 로그인 없는 뷰로 이동
            return "member/login";
        }
    }
}
