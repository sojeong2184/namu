package com.durianfirst.durian.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
@Log4j2
public class ErrorHandleController implements ErrorController {

    @GetMapping("/error/accessDenied")
    public String admin(){
        return "error/accessDenied";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(status != null){
            int statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()){
                log.info(statusCode + "에러 ");
                return "error/error-404";
            }

            if(statusCode == HttpStatus.FORBIDDEN.value()){
                log.info(statusCode + "에러 ");
                return "error/error-403";
            }

            if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
                log.info(statusCode + "에러 ");
                return "error/error-500";
            }
        }
        log.info("알 수 없는 에러");
        return "error/error";
    }

    /*@GetMapping("/error/error-403")
    public void error_403(){}

    @GetMapping("/error/error-404")
    public void error_404(){}

    @GetMapping("/error/error-500")
    public void error_500(){}*/

}