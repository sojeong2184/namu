package com.durianfirst.durian.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/component")
public class AdminComponentController {

    @GetMapping("/component-alert")
    public void component_alert(){}

    @GetMapping("/component-badge")
    public void component_badge(){}

    @GetMapping("/component-breadcrumb")
    public void component_breadcrumb(){}

    @GetMapping("/component-button")
    public void component_button(){}

    @GetMapping("/component-card")
    public void component_card(){}

    @GetMapping("/component-carousel")
    public void component_carousel(){}

    @GetMapping("/component-dropdown")
    public void component_dropdown(){}

    @GetMapping("/component-list-group")
    public void component_list_group(){}

    @GetMapping("/component-modal")
    public void component_modal(){}

    @GetMapping("/component-navs")
    public void component_navs(){}

    @GetMapping("/component-pagination")
    public void component_pagination(){}

    @GetMapping("/component-progress")
    public void component_progress(){}

    @GetMapping("/component-spinner")
    public void component_spinner(){}

    @GetMapping("/component-tooltip")
    public void component_tooltip(){}

}
