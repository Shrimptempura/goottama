package com.ama.don.common.config;

import com.ama.don.admin.service.userManage.CustomUserDetailsService;
import com.ama.don.interior.dev.DevAutoLoginBaseMember;
import com.ama.don.member.dao.LoginDao;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final LoginDao loginDao;
    private final CustomUserDetailsService customUserDetailsService;

    // 인테리어 사용
    @Bean
    public Filter devAutoLoginFilter() {
        return new DevAutoLoginBaseMember(loginDao, customUserDetailsService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(devAutoLoginFilter(), UsernamePasswordAuthenticationFilter.class)      // 인테리어
                .authorizeHttpRequests(auth -> auth
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers(
                                "/",
                                "/login_view",
                                "/join_view",
                                "/join",
                                "/findLoginId_view",
                                "/findPw_view",
                                "/checkPwCode_view",
                                "/emailSent_view",
                                "/withdrawalSuccess_view",
                                "/emailCheck",
                                "/authenticate",
                                "/find_loginId",
                                "/css/**", "/js/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
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
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // 로그인 없이 모두 허용
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
}