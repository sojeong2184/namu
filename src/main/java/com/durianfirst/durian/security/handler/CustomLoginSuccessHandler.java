package com.durianfirst.durian.security.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Component
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        // 부모 클래스의 동작을 수행
        super.onAuthenticationSuccess(request, response, authentication);

        // 추가적인 동작 수행
        // Referer 헤더에서 이전 페이지 URL을 가져옴
        String targetUrl = getTargetUrl(request);
        // 원하는 추가 작업 수행 후 해당 URL로 리다이렉트
        response.sendRedirect(targetUrl);
    }

    private String getTargetUrl(HttpServletRequest request) {
        // Referer 헤더에서 이전 페이지 URL을 가져옴
        String targetUrl = request.getHeader("Referer");
        if (StringUtils.hasText(targetUrl) && !targetUrl.contains("/member/login")) {
            return targetUrl;
        } else {
            // 기본적으로 지정할 페이지 URL
            return "/";
        }
    }
}