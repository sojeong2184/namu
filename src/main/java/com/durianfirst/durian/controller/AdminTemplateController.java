package com.durianfirst.durian.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adminTemplate")
public class AdminTemplateController {

    @GetMapping("/application-chat")
    public void application_chat(){}

    @GetMapping("/application-checkout")
    public void application_checkout(){}

    @GetMapping("/application-email")
    public void application_email(){}

    @GetMapping("/application-gallery")
    public void application_gallery(){}

    @GetMapping("/auth-login")
    public void auth_login(){}

    @GetMapping("/auth-register")
    public void auth_register(){}

    @GetMapping("/extra-component-avatar")
    public void extra_component_avatar(){}

    @GetMapping("/extra-component-divider")
    public void extra_component_divider(){}

    @GetMapping("/extra-component-rating")
    public void extra_component_rating(){}

    @GetMapping("/extra-component-sweetalert")
    public void extra_component_sweetalert(){}

    @GetMapping("/extra-component-toastify")
    public void extra_component_toastify(){}

    @GetMapping("/form-editor-ckeditor")
    public void form_editor_ckeditor(){}

    @GetMapping("/form-editor-quill")
    public void form_editor_quill(){}

    @GetMapping("/form-editor-summernote")
    public void form_editor_summernote(){}

    @GetMapping("/form-editor-tinymce")
    public void form_editor_tinymce(){}

    @GetMapping("/form-element-checkbox")
    public void form_editor_checkbox(){}

    @GetMapping("/form-element-input")
    public void form_editor_input(){}

    @GetMapping("/form-element-input-group")
    public void form_editor_input_group(){}

    @GetMapping("/form-element-radio")
    public void form_editor_radio(){}

    @GetMapping("/form-element-select")
    public void form_editor_select(){}

    @GetMapping("/form-element-textarea")
    public void form_editor_textarea(){}

    @GetMapping("/form-layout")
    public void form_editor_layout(){}

    @GetMapping("/layout-default")
    public void layout_default(){}

    @GetMapping("/layout-horizontal")
    public void layout_horizontal(){}

    @GetMapping("/layout-vertical-1-column")
    public void layout_vertical_1_column(){}

    @GetMapping("/layout-vertical-navbar")
    public void layout_vertical_navbar(){}

    @GetMapping("/sidebar-items")
    public void sidebar_items(){}

    @GetMapping("/table")
    public void table(){}

    @GetMapping("/table-datatable")
    public void table_datatable(){}

    @GetMapping("/ui-chart-apexcharts")
    public void ui_chart_apexcharts(){}

    @GetMapping("/ui-chart-chartjs")
    public void ui_chart_chartjs(){}

    @GetMapping("/ui-file-uploader")
    public void ui_file_uploader(){}

    @GetMapping("/ui-icons-bootstrap-icons")
    public void ui_icons_bootstrap_icons(){}

    @GetMapping("/ui-icons-dripicons")
    public void ui_icons_dripicons(){}

    @GetMapping("/ui-icons-fontawesome")
    public void ui_icons_fontawesome(){}

    @GetMapping("/ui-map-google-map")
    public void ui_map_google_map(){}

    @GetMapping("/ui-map-jsvectormap")
    public void ui_map_jsvectormap(){}

    @GetMapping("/ui-widgets-chatbox")
    public void ui_widgets_chatbox(){}

    @GetMapping("/ui-widgets-pricing")
    public void ui_widgets_pricing(){}

    @GetMapping("/ui-widgets-todolist")
    public void ui_widgets_todolist(){}




}
