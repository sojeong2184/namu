package com.durianfirst.durian.controller;

import com.durianfirst.durian.entity.News;
import com.durianfirst.durian.service.DurianService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Log4j2
public class UserDurianController {

    private final DurianService durianService;


    public UserDurianController(DurianService durianService) {
        this.durianService = durianService;
    }

    @GetMapping("/hotelfind")
    public String hf(Model model) throws Exception {

        List<News> newsList = durianService.getNewsDatas();
        model.addAttribute("news", newsList);

        return "hotelfind";
    }


}
