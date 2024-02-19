package com.durianfirst.durian.controller;


import com.durianfirst.durian.dto.ItemDTO;
import com.durianfirst.durian.dto.MemberJoinDTO;
import com.durianfirst.durian.dto.OrderDTO;
import com.durianfirst.durian.dto.PageRequestDTO;
import com.durianfirst.durian.repository.MemberRepository;
import com.durianfirst.durian.service.ItemService;
import com.durianfirst.durian.service.OrderService;
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

@Controller
@RequestMapping("/admin/order")
@RequiredArgsConstructor
@Log4j2
public class OrderController {

    private final OrderService orderService;

    private final ItemService itemService;

    private final MemberRepository memberRepository;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("pageRequestDTO: " + pageRequestDTO);

        model.addAttribute("result", orderService.getList(pageRequestDTO));

    }

    @GetMapping("/register")
    public void register() {

    }

    @PostMapping("/register")
    public String register(long ino, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model, OrderDTO orderDTO, MemberJoinDTO memberJoinDTO, RedirectAttributes redirectAttributes) {
        log.info("orderDTO : "+orderDTO);

        Long ono = orderService.register(orderDTO);

        ItemDTO itemDTO = itemService.getItem(ino);

        redirectAttributes.addFlashAttribute("msg",ono);
        model.addAttribute("idto", itemDTO);
        model.addAttribute("odto", orderDTO);
        model.addAttribute("mdto", memberJoinDTO);
        return "redirect:/orderRead";

    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping({"/read", "/modify"})
    public void read(long ono, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {

        log.info("ono : "+ono);

        OrderDTO orderDTO = orderService.getOrder(ono);

        model.addAttribute("dto", orderDTO);
    }


}
