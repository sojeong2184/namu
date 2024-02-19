package com.durianfirst.durian.controller;


import com.durianfirst.durian.dto.MemberJoinDTO;
import com.durianfirst.durian.dto.PageRequestedDTO;
import com.durianfirst.durian.dto.ShippingDTO;
import com.durianfirst.durian.entity.*;
import com.durianfirst.durian.repository.MemberRepository;
import com.durianfirst.durian.repository.ShippingRepository;
import com.durianfirst.durian.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/mypage")
@Log4j2
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()") // 로그인한 사용자만 조회 가능 -> 비로그인 상태일 경우 로그인 화면으로 이동
public class MypageController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ShippingService shippingService;
    private final ShippingRepository shippingRepository;
    private final ChatService chatService;
    private final OrderService orderService;
    private final QuestionService questionService;
    private final ItemService itemService;

    @GetMapping("/main")
    public void mainpage(){
        log.info("====main====");
    }

    /* info */
    @GetMapping({"/myinfo","/myinfo/info"})
    public String mypagedRead(Principal principal, Model model) {

        String mid = principal.getName();                   //mid에 로그인 정보를 받음
        Member member = memberRepository.findByMid(mid);    //findbymid로 유저 정보 찾아서 member에 저장

        log.info("유저 아이디 : " + principal.getName());

        model.addAttribute("member", member);    //model로 member에 담긴 정보를 인덱스 프론트에 넘김
        model.addAttribute("member", member);    //model로 member에 담긴 정보를 인덱스 프론트에 넘김
        return "mypage/myinfo/info";
    }

    /* 비밀번호를 제외한 나머지 수정*/
    @GetMapping("/myinfo/modify")
    public String modify(Principal principal, Model model) {

        String mid = principal.getName();
        Member member = memberRepository.findByMid(mid);

        model.addAttribute("member", member);

        return "mypage/myinfo/modify";
    }

    @PostMapping("/myinfo/modify")
    public String updateMember(MemberJoinDTO joinDTO, Model model) {

        model.addAttribute("member", joinDTO);
        memberService.updateMember(joinDTO);
        return "redirect:/mypage/myinfo/info";
    }

    /* Password Modify */
    @GetMapping("/myinfo/checkPassword")
    public String passwordCheckPage() {

        return "mypage/myinfo/checkPassword";
    }

    @PostMapping("/myinfo/checkPassword")
    public String checkPassword(@RequestParam("userPassword") String userPassword, Model model, Principal principal) {
        // 현재 비밀번호 확인 로직 추가
        // 비밀번호가 맞으면 비밀번호 변경 페이지로 이동
        // 맞지 않으면 오류 메시지를 모델에 추가하고 다시 비밀번호 입력 페이지로 이동

        if (memberService.checkPassword(principal.getName(), userPassword)) {
            return "redirect:/mypage/myinfo/passwordModify";  // 비밀번호가 맞으면 변경 페이지로 이동
        } else {
            model.addAttribute("mpw", "현재 비밀번호가 일치하지 않습니다.");
            return "mypage/myinfo/checkPassword";  // 비밀번호가 틀리면 다시 입력 페이지로 이동
        }
    }

    @GetMapping("/myinfo/passwordModify")
    public String modifypassword(Principal principal, Model model) {

        String mid = principal.getName();
        Member member = memberRepository.findByMid(mid);

        model.addAttribute("member", member);

        return "mypage/myinfo/passwordModify";
    }

    @PostMapping("/myinfo/passwordModify")
    public String updatePassword(MemberJoinDTO joinDTO, Model model) {

        model.addAttribute("member", joinDTO);
        memberService.updatePassword(joinDTO);

        return "redirect:/mypage/myinfo/info";
    }


    /* shipping */

    @GetMapping("/shipping/list")
    public String shippingList(Model model){
        List<Shipping> shippingItems = shippingRepository.findAll();
        model.addAttribute("shippingItems", shippingItems);
        return "mypage/shipping/list";
    }

    @GetMapping("/shipping/register")
    public String shippingRegister(Model model) {

        model.addAttribute("shippingDTO", new ShippingDTO());
        log.info("register");
        return "mypage/shipping/register";
    }

    @PostMapping("/shipping/register")
    public String shippingRegister(@Valid ShippingDTO shippingDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {

        log.info("배송지 등록");

        String loggedId = principal.getName();

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "mypage/shipping/register";
        }

        shippingDto.setMid(loggedId);

        // 기존의 기본 배송지 해제
        shippingService.updateDefaultShipping(loggedId);

        Long sno = shippingService.register(shippingDto);

        redirectAttributes.addFlashAttribute("result", sno);

        log.info(sno);

        return "redirect:/mypage/shipping/list";
    }


    @GetMapping("/shipping/read{sno}")
    public String read(long sno, Model model){

        ShippingDTO shippingDto = shippingService.readOne(sno);

        model.addAttribute("shippingDto", shippingDto);

        return "shipping/shippingRead";
    }

    @GetMapping("/shipping/modify")
    public String Modifyread(long sno, Model model){

        ShippingDTO shippingDto = shippingService.readOne(sno);

        model.addAttribute("shippingDto", shippingDto);

        return "shipping/shippingModify";
    }

    @PostMapping("/shipping/modify")
    public String shippingModify(ShippingDTO shippingDTO, RedirectAttributes redirectAttributes) {
        log.info("shipping modify");
        log.info("shippingDTO: " + shippingDTO);

        shippingService.modify(shippingDTO);

        // 'sno' 값을 RedirectAttributes에 추가하여 URL에 해당 값을 전달
        redirectAttributes.addAttribute("sno", shippingDTO.getSno());

        // Redirect 시, URL에 'sno' 값을 포함하여 리다이렉트합니다.
        return "shipping/read";
    }

    @PostMapping("/shipping/remove")
    public String remove(@RequestParam("sno") long sno, RedirectAttributes redirectAttributes){

        log.info("sno" + sno);

        shippingService.remove(sno);

        redirectAttributes.addFlashAttribute("msg", sno);

        return "shipping/shipping";
    }

    /*chat 구현*/
    @RequestMapping("/chat/chatList")
    public String chatList(Model model){
        List<ChatRoom> roomList = chatService.findAllRoom();
        model.addAttribute("roomList",roomList);
        return "mypage/chat/chatList";
    }

    @PostMapping("/chat/createRoom")
    public String createRoom(Model model, String username, Principal principal) {
        if (principal != null) {
            String userId = principal.getName();
            Member member = memberRepository.findByMid(userId);

            log.info("유저 아이디: " + userId);

            model.addAttribute("member", member);

            // 사용자의 아이디를 사용하여 ChatService의 createRoom 메서드 호출
            ChatRoom room = chatService.createRoom(userId);

            model.addAttribute("room", room);
            model.addAttribute("username", username);

            return "mypage/chat/chatRoom";  // 만든 사람이 채팅방 1빠로 들어가게 됩니다
        } else {
            return "member/login";
        }
    }

    @GetMapping("/chat/chatRoom")
    public String chatRoom(Model model, @RequestParam String roomId, Principal principal){

        if(principal != null){

            String mid = principal.getName();
            Member member = memberRepository.findByMid(mid);

            log.info("유저 아이디 : " + principal.getName());

            model.addAttribute("member", member);
        }else{
            return "member/login";
        }

        ChatRoom room = chatService.findRoomById(roomId);
        model.addAttribute("room",room);//현재 방에 들어오기위해서 필요한데...... 접속자 수 등등은 실시간으로 보여줘야 돼서 여기서는 못함
        return "mypage/chat/chatRoom";
    }

//    @GetMapping("/orderList")
//    public void orderList(PageRequestDTO pageRequestDTO, Model model, Principal principal) {
//
//        log.info("나의 결제 목록");
//
//        String mid = principal.getName();
//        OrderDTO orderDTO = orderService.getMid(mid);
//
//        model.addAttribute("odto", orderDTO);
//
//    }

    @GetMapping("/orderList")
    public String orderList(PageRequestedDTO pageRequestedDTO, Model model, Principal principal) {
        if (principal != null) {
            String mid = principal.getName();
            Member member = memberRepository.findByMid(mid);

            log.info("유저 아이디 : " + principal.getName());
        } else {
            return "member/login";
        }
        log.info("나의 결제 목록");
        // 현재 로그인한 사용자 정보 가져오기
        String mid = principal.getName();

        // 사용자 이름을 기반으로 사용자 정보 가져오기
        Member member = memberRepository.findByMid(mid);

        List<Order> orderList = orderService.getOrdersByMid(mid);

        model.addAttribute("orderList", orderList);

        return "mypage/orderList";
    }

    @GetMapping("/questionList")
    public String questionList(PageRequestedDTO pageRequestedDTO, Model model, Principal principal) {
        if (principal != null) {
            String mid = principal.getName();
            Member member = memberRepository.findByMid(mid);

            log.info("유저 아이디 : " + principal.getName());
        } else {
            return "member/login";
        }
        log.info("나의 결제 목록");
        // 현재 로그인한 사용자 정보 가져오기
        String mid = principal.getName();

        // 사용자 이름을 기반으로 사용자 정보 가져오기
        Member member = memberRepository.findByMid(mid);

        List<Question> questionList = questionService.getQuestionsByMid(mid);

        model.addAttribute("questionList", questionList);

        return "mypage/questionList";
    }
    @GetMapping("/itemList")
    public String itemList(Model model,Principal principal) {
        if (principal != null) {
            String mid = principal.getName();
            Member member = memberRepository.findByMid(mid);

            log.info("유저 아이디 : " + principal.getName());
        } else {
            return "member/login";
        }
        // 현재 로그인한 사용자 정보 가져오기
        String mid = principal.getName();
        // 사용자 이름을 기반으로 사용자 정보 가져오기
        Member member = memberRepository.findByMid(mid);

        // 사용자가 작성한 글 가져오기
        List<Item> itemList = itemService.getItemsByMid(mid);
        // 결과를 모델에 추가
        model.addAttribute("itemList", itemList);
        return "mypage/itemList";
    }

}