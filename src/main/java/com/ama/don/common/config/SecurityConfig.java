package com.ama.don.common.config;

import com.ama.don.admin.service.userManage.CustomUserDetailsService;
import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                .authorizeHttpRequests(auth -> auth
//                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
//                        .requestMatchers(
//                                "/",
//                                "/login_view",
//                                "/join_view",
//                                "/join",
//                                "/findLoginId_view",
//                                "/findPw_view",
//                                "/checkPwCode_view",
//                                "/emailSent_view",
//                                "/withdrawalSuccess_view",
//                                "/emailCheck",
//                                "/authenticate",
//                                "/find_loginId",
//                                "/css/**", "/js/**"
//                        ).permitAll()
//                        .anyRequest().authenticated()
//                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                )
                .formLogin(form -> form
                        .loginPage("/login_view")
                        .loginProcessingUrl("/authenticate")
                        .usernameParameter("loginId")
                        .passwordParameter("pw")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login_view?error=true")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .userDetailsService(customUserDetailsService)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // 로그인 없이 모두 허용
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
}