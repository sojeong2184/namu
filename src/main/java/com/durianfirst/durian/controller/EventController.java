package com.durianfirst.durian.controller;


import com.durianfirst.durian.dto.EventDTO;
import com.durianfirst.durian.dto.PageRequestDTO;
import com.durianfirst.durian.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
@Log4j2
@RequiredArgsConstructor //자동 주입을 위한 Annotation
public class EventController {

    private final EventService eventService;

    @GetMapping("/event/register")
    public String register(Model model) {
        log.info("register get....");
        model.addAttribute("eventDTO", new EventDTO());

        return "admin/event/register";
    }
    @PostMapping("/event/register")
    public String register(@Valid EventDTO eventDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if(bindingResult.hasErrors()){
            model.addAttribute("eventDTO", eventDTO);
            return "admin/event/register";
        }

        log.info("eventDTO : "+eventDTO);

        Long eno = eventService.register(eventDTO);

        redirectAttributes.addFlashAttribute("msg",eno);

        return "redirect:/admin/event/list";
    }

    @GetMapping("/event/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        log.info("pageRequestDTO: " + pageRequestDTO);


        model.addAttribute("eresult", eventService.getList(pageRequestDTO));
    }
    @GetMapping({"/event/read", "/event/modify"})
    public void read(Long eno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {

        log.info("eno : "+eno);

        EventDTO eventDTO = eventService.getEvent(eno);

        model.addAttribute("edto", eventDTO);
    }

    @PostMapping("/event/modify")
    public String modify(EventDTO eventDTO, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        log.info("post modify..........");
        log.info("dto: " + eventDTO);

        eventService.modify(eventDTO);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("eno", eventDTO.getEno());

        return "redirect:/admin/event/read";
    }

    @PostMapping("/event/remove")
    public String remove(long eno, RedirectAttributes redirectAttributes) {
        log.info("eno : "+eno);

        eventService.remove(eno);

        redirectAttributes.addFlashAttribute("msg", eno);

        return "redirect:/admin/event/list";
    }

}
