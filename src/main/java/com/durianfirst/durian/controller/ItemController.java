package com.durianfirst.durian.controller;

import com.durianfirst.durian.dto.ItemDTO;
import com.durianfirst.durian.dto.MemberJoinDTO;
import com.durianfirst.durian.dto.PageRequestDTO;
import com.durianfirst.durian.entity.Items;
import com.durianfirst.durian.entity.Member;
import com.durianfirst.durian.repository.MemberRepository;
import com.durianfirst.durian.service.DaangnService;
import com.durianfirst.durian.service.ItemService;
import com.durianfirst.durian.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
@RequiredArgsConstructor //자동 주입을 위한 Annotation
public class ItemController {

    private final DaangnService daangnService;
    private final ItemService itemService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/daangnfind")
    public String df(Model model, Principal principal) throws Exception{

        if (principal != null) {

            String mid = principal.getName();                   //mid에 로그인 정보를 받음
            Member member = memberRepository.findByMid(mid);    //findbymid로 유저 정보 찾아서 member에 저장

            log.info("유저 아이디 : " + principal.getName());

            model.addAttribute("member",member);    //model로 member에 담긴 정보를 인덱스 프론트에 넘김
            MemberJoinDTO memberJoinDTO = memberService.readOne(mid);
            model.addAttribute("mdto", memberJoinDTO);
            List<Items> itemList = daangnService.getItemsDatas();
            model.addAttribute("news", itemList);
            return "daangnfind";
        } else {
            // 로그인이 안 된 경우 로그인 없는 뷰로 이동
            return "member/login";

        }

    }

    @PostMapping("/daangnfind")
    public String register(ItemDTO itemDTO, RedirectAttributes redirectAttributes, MemberJoinDTO memberJoinDTO, Model model) {
        log.info("itemDTO : "+itemDTO);

        Long ino = itemService.register(itemDTO);

        redirectAttributes.addFlashAttribute("msg",ino);

        model.addAttribute("mdto", memberJoinDTO);

        return "redirect:/daangnfind";

    }

    /* USER */
    @GetMapping("/item/register")
    public String contact(Principal principal, Model model) {
        if(principal == null){
            return "member/login";
        }
        String mid = principal.getName();
        MemberJoinDTO memberJoinDTO = memberService.readOne(mid);

        model.addAttribute("mdto", memberJoinDTO);
        return "item/register";
    }

    @PostMapping("/item/register")
    public String contactpost(ItemDTO itemDTO, RedirectAttributes redirectAttributes,
                              MemberJoinDTO memberJoinDTO, Model model) {

        log.info("dto..." +itemDTO);

        Long ino = itemService.register(itemDTO);

        redirectAttributes.addFlashAttribute("msg", ino);

        model.addAttribute("mdto", memberJoinDTO);

        return "redirect:/properties";
    }

    /* ADMIN */
    @GetMapping("/admin/item")
    public String index(){
        return "redirect:/admin/item/list";
    }

    @GetMapping("/admin/item/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        log.info("list.....");

        model.addAttribute("result", itemService.getList(pageRequestDTO));
    }

    @GetMapping("/admin/item/register")
    public void register(){
        log.info("register get....");
    }

    @PostMapping("/admin/item/register")
    public String registerPost(ItemDTO itemDTO, RedirectAttributes redirectAttributes){

        log.info("dto..." +itemDTO);

        Long ino = itemService.register(itemDTO);

        redirectAttributes.addFlashAttribute("msg", ino);

        return "redirect:/admin/item/list";
    }

    @GetMapping({"/admin/item/read", "/admin/item/modify"})
    public void read(long ino, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){

        log.info("ino: " + ino);

        ItemDTO itemDTO = itemService.read(ino);

        model.addAttribute("dto", itemDTO);
    }

    @PostMapping("/admin/item/remove")
    public String remove(long ino, RedirectAttributes redirectAttributes) {

        log.info("ino: " + ino);

        itemService.remove(ino);

        redirectAttributes.addFlashAttribute("msg", ino);

        return "redirect:/admin/item/list";
    }

    @PostMapping("/admin/item/modify")
    public String modify(ItemDTO itemDTO, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        log.info("post modify..........");
        log.info("dto: " + itemDTO);

        itemService.modify(itemDTO);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("ino", itemDTO.getIno());

        return "redirect:/admin/item/read";
    }

//    @PostMapping("/daangnfind")
//    public String registerMultipleItems(@ModelAttribute("news") List<ItemDTO> news, RedirectAttributes redirectAttributes) {
//        log.info("itemDTOList: " + news);
//
//        // 여러 개의 아이템을 반복적으로 저장
//        for (ItemDTO itemDTO : news) {
//            Long ino = itemService.register(itemDTO);
//            // 여기에서 다른 처리 로직을 수행하거나 필요한 경우 추가 작업을 수행할 수 있습니다.
//        }
//
//        redirectAttributes.addFlashAttribute("msg", "Items registered successfully");
//
//        return "redirect:/daangnfind";
//    }
}
