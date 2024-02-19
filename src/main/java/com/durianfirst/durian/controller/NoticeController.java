package com.durianfirst.durian.controller;


import com.durianfirst.durian.dto.NoticeDTO;
import com.durianfirst.durian.dto.PageRequestDTO;
import com.durianfirst.durian.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
/*@RequestMapping("/admin")*/
@Log4j2
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    /* USER */

    @GetMapping({"/notice/list","/notice"})
    public String notice(PageRequestDTO pageRequestDTO, Model model) {

        log.info("list.............."+pageRequestDTO);

        model.addAttribute("result", noticeService.getList(pageRequestDTO));

        return "notice/list";
    }

    @GetMapping({"/notice/read"})
    public String nread(@RequestParam long nno, @ModelAttribute("requestDTO") PageRequestDTO
            requestDTO, Model model) {
        log.info("nno: "+nno );

        NoticeDTO dto = noticeService.read(nno);

        model.addAttribute("ndto", dto);

        noticeService.updateView(nno);
        return "notice/read";
    }

    /* ADMIN */

    @GetMapping("/admin/notice")
    public String index() {
        return "admin/notice/list";
    }

    @GetMapping("/admin/notice/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        log.info("list.............."+pageRequestDTO);

        model.addAttribute("result", noticeService.getList(pageRequestDTO));

    }

    @GetMapping("/admin/notice/register")
    public String register(Model model) {
        log.info("register get....");
        model.addAttribute("noticeDTO", new NoticeDTO());

        return "admin/notice/register";
    }

    @PostMapping("/admin/notice/register")
    public String registerPost(@Valid NoticeDTO dto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if(bindingResult.hasErrors()){
            model.addAttribute("noticeDTO", dto);
            return "admin/notice/register";
        }

        log.info("dto...."+dto);

        //새로추가된 엔티티번호임
        Long nno = noticeService.register(dto);

        redirectAttributes.addFlashAttribute("msg", nno);

        return "redirect:/admin/notice/list";
    }

    @GetMapping({"/admin/notice/read", "/admin/notice/modify"})
    public void read(long nno, @ModelAttribute("requestDTO") PageRequestDTO
            requestDTO, Model model) {
        log.info("nno: "+nno );

        NoticeDTO dto = noticeService.read(nno);

        model.addAttribute("ndto", dto);
    }

    @PostMapping("/admin/notice/remove")
    public String remove(long nno, RedirectAttributes redirectAttributes) {
        log.info("nno : "+nno);

        noticeService.remove(nno);

        redirectAttributes.addFlashAttribute("msg", nno);

        return "redirect:/admin/notice/list";
    }

    @PostMapping("/admin/notice/modify")
    public String modify(NoticeDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes){


        log.info("post modify.........................................");
        log.info("dto: " + dto);

        noticeService.modify(dto);

        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("type",requestDTO.getType());
        redirectAttributes.addAttribute("keyword",requestDTO.getKeyword());

        redirectAttributes.addAttribute("nno",dto.getNno());


        return "redirect:/admin/notice/read";
    }
}



