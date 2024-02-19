package com.durianfirst.durian.config;

import com.durianfirst.durian.security.CustomUserDetailsService;
import com.durianfirst.durian.security.handler.Custom403Handler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Log4j2
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomSecurityConfig {

    private final DataSource dataSource;
    private final CustomUserDetailsService userDetailsService;
    /* 로그인 실패 핸들러 의존성 주입 */
    private final AuthenticationFailureHandler failureHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){ //CustomUserDetailsService가 정상적으로 동작하려면 CustomSecurityConfig에 주입해야함
        return new BCryptPasswordEncoder();
        //BCryptPasswordEncoder : 해시 알고리즘으르 암호화 처리되는데 같은 문자열이라고 해도 매번 해서 처리된 결과가 다름
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{ //로그인하지 않아도 볼 수 있도록 설정 (admin/adindex에 바로 접근 가능)
        log.info("==========================configure==============================");

        http.formLogin()
                .loginPage("/member/login") //POST방식 처리 역시 같은 경로로 스프링 시큐리티 내부에서 처리됨 / security에서 post방식도 처리함
                .defaultSuccessUrl("/")
                .failureHandler(failureHandler) //로그인 실패 핸들러
                .and()
             .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))   // 로그아웃 URL
                .logoutSuccessUrl("/index")                                     // 로그아웃 성공시 이동할 URL
                .invalidateHttpSession(true)                                           // 로그아웃 이후 세션 전체 삭제 여부
                .deleteCookies("JSESSIONID")
                .and()
             .authorizeRequests()
                .antMatchers("/admin/*").hasAnyAuthority("ROLE_ADMIN") //admin 권한이 있어야지 접근 가능
                .and()
             .exceptionHandling()
                .accessDeniedPage("/error/accessDenied");

        //로그인 진행한다는 설정
        //UserDetailsService : 실제로 인증을 처리 인터페이스
        //loadUserByUsername : 실제 인증을 처리할 때 호출 되는 부분
        //username = mid

        http.csrf().disable(); //CSRF 비활성화하면 username,password라는 파라미터만으로 로그인이 가능해짐
        // csrf토큰이 비활성화 되어있으면 get방식으로도 로그아웃이 가능함

        /* 자동 로그인 */
        http.rememberMe()
                .key("12345678") //쿠키의 값을 인코딩하기 위한 키값
                .tokenRepository(persistentTokenRepository()) //필요한 정보를 저장
                .userDetailsService(userDetailsService)
                .tokenValiditySeconds(60*60*24*30);

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() { //Custom403Handler가 동작하기 위해서 빈처리와 예외 처리를 지정해 주어야 함
        return new Custom403Handler();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){ //정적 자원 제외
        log.info("============================web configure========================");
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }
}
