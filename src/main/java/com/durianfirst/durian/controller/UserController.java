package com.durianfirst.durian.controller;


import com.durianfirst.durian.dto.*;
import com.durianfirst.durian.entity.Items;
import com.durianfirst.durian.entity.Member;
import com.durianfirst.durian.repository.MemberRepository;
import com.durianfirst.durian.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
@Log4j2
@RequiredArgsConstructor
public class UserController {


    private final ItemService itemService;

    private final OrderService orderService;

    private final QuestionService questionService;

    private final AnswerService answerService;

    private final DaangnService daangnService;

    private final EventService eventService;

    private final MemberRepository memberRepository;

    private final MemberService memberService;

    private final NoticeService noticeService;

    
    @GetMapping("/")
    public String mainindex(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list...............");
        log.info("pageRequestDTO: " + pageRequestDTO);


        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(PageRequestDTO pageRequestDTO, Model model) throws Exception{

        log.info("pageRequestDTO: " + pageRequestDTO);


        model.addAttribute("item", itemService.getList(pageRequestDTO));

        List<Items> itemList = daangnService.getItemsDatas();
        model.addAttribute("news", itemList);

//        //principal로 로그인 정보 가져옴
//        // model로 index로 넘겨줌
//        if (principal != null) {
//
//            String mid = principal.getName();                   //mid에 로그인 정보를 받음
//            Member member = memberRepository.findBymid(mid);    //findbymid로 유저 정보 찾아서 member에 저장
//
//            log.info("유저 아이디 : " + principal.getName());
//
//            model.addAttribute("member",member);    //model로 member에 담긴 정보를 인덱스 프론트에 넘김
//
//            return "/index";
//        } else {
//            // 로그인이 안 된 경우 로그인 없는 뷰로 이동
//            return "/member/login";
//
//
//        }
        return "index";
    }

    @PostMapping("/search")
    public String ItemSearch(RedirectAttributes redirectAttributes, String iname, PageRequestDTO pageRequestDTO, Model model) {
        log.info("list.....");

        // 여기에서 iname을 이용하여 데이터 처리

        // 결과를 모델에 추가
        ItemDTO itemDTO = itemService.getIname(iname);
        model.addAttribute("dto", itemDTO);

        // 결과를 보여줄 페이지로 리다이렉트
        return "redirect:/search?iname=" + iname;
    }

    @GetMapping("/search")
    public void ItemSearchPage(String iname, PageRequestDTO pageRequestDTO, Model model) {

        ItemDTO itemDTO = itemService.getIname(iname);

        model.addAttribute("dto", itemDTO);


    }

    @GetMapping("/about")
    public void about() {

    }

    /*@GetMapping("/contact")
    public void contact(Principal principal, Model model) {
        String mid = principal.getName();
        MemberJoinDTO memberJoinDTO = memberService.readOne(mid);

        model.addAttribute("mdto", memberJoinDTO);
    }

    @PostMapping("/contact")
    public String contactpost(ItemDTO itemDTO, RedirectAttributes redirectAttributes,
                              MemberJoinDTO memberJoinDTO, Model model) {

        log.info("dto..." +itemDTO);

        Long ino = itemService.register(itemDTO);

        redirectAttributes.addFlashAttribute("msg", ino);

        model.addAttribute("mdto", memberJoinDTO);

        return "redirect:/index";
    }*/

    @GetMapping("/properties")
    public void properties(PageRequestDTO pageRequestDTO, Model model) {

        log.info("list.....");

        model.addAttribute("result", itemService.getList(pageRequestDTO));

    }

//    @GetMapping("/article")
//    public void propertysingle(Long ino, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model,MemberJoinDTO memberJoinDTO, Principal principal) {
//        log.info("ino : "+ino);
//
//        ItemDTO itemDTO = itemService.getItem(ino);
////        String mid=itemDTO.getMid();
////        Member member = memberRepository.findBymid(mid);
//
//        //principal로 로그인 정보 가져옴
//        // model로 index로 넘겨줌
//        if (principal != null) {
//            String mid = principal.getName();                   //mid에 로그인 정보를 받음
//            Member member = memberRepository.findByMid(mid);    //findbymid로 유저 정보 찾아서 member에 저장
//            model.addAttribute("mdto",member);
//
//            Member sellMember = memberRepository.findByMid(itemDTO.getMid());
//            model.addAttribute("sdto",sellMember);
//        } else {
//            // 로그인이 안 된 경우 로그인 없는 뷰로 이동
////            return "/member/login";
//        }
//
//
//        model.addAttribute("dto", itemDTO);
////        return "article?ino="+ino;
//    }

    @GetMapping("/article")
    public String propertysingle(Long ino, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model,MemberJoinDTO memberJoinDTO, Principal principal) {
        log.info("ino : "+ino);

        ItemDTO itemDTO = itemService.getItem(ino);
//        String mid=itemDTO.getMid();
//        Member member = memberRepository.findBymid(mid);

        //principal로 로그인 정보 가져옴
        // model로 index로 넘겨줌
        if(principal == null){
            return "member/login";
        }

        String mid = principal.getName();                   //mid에 로그인 정보를 받음
        Member member = memberRepository.findByMid(mid);    //findbymid로 유저 정보 찾아서 member에 저장
        model.addAttribute("mdto",member);

        Member sellMember = memberRepository.findByMid(itemDTO.getMid());
        model.addAttribute("sdto",sellMember);

        model.addAttribute("dto", itemDTO);
        return "article";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/article")
    public String order(Long ino, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model,OrderDTO orderDTO,
                        MemberJoinDTO memberJoinDTO, RedirectAttributes redirectAttributes) {

        Long ono = orderService.register(orderDTO);

        ItemDTO itemDTO = itemService.getItem(ino);

        redirectAttributes.addFlashAttribute("msg",ono);
        model.addAttribute("idto", itemDTO);
        model.addAttribute("odto", orderDTO);
        model.addAttribute("mdto", memberJoinDTO);


        return "redirect:/orderRead";
    }

    @GetMapping("/services")
    public void services(PageRequestedDTO pageRequestDTO, Model model) {
        PageResponsedDTO<QuestionDTO> responseDTO = questionService.list(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);

    }

    @GetMapping("/event/list")
    public void event(PageRequestDTO pageRequestDTO, Model model){

        log.info("pageRequestDTO: " + pageRequestDTO);


        model.addAttribute("eresult", eventService.getList(pageRequestDTO));

    }

    @GetMapping({"/event/eventread"})
    public void eread(long eno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {

        log.info("eno : "+eno);

        EventDTO eventDTO = eventService.getEvent(eno);

        model.addAttribute("edto", eventDTO);
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/orderRead")
    public void orderRead(@ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model, Principal principal) {

        log.info("나의 결제완료 페이지");

        String mid = principal.getName();
        MemberJoinDTO memberJoinDTO = memberService.readOne(mid);

        model.addAttribute("mdto", memberJoinDTO);

        OrderDTO orderDTO = orderService.getMid(mid);

        model.addAttribute("odto", orderDTO);

    }

    @GetMapping("/orderList")
    public void orderList(PageRequestDTO pageRequestDTO, Model model, Principal principal) {

        log.info("나의 결제 목록");

        String mid = principal.getName();
        OrderDTO orderDTO = orderService.getMid(mid);

        model.addAttribute("odto", orderDTO);

    }


}
