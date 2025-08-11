package com.ama.don.common.config;

import com.ama.don.admin.service.userManage.CustomUserDetailsService;
import com.ama.don.admin.service.userActivityLog.LoginSuccessHandler;
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
    private final LoginSuccessHandler loginSuccessHandler; // 로그인 성공 로그

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
                                "/findPw",
                                "/checkPwCode",
                                "/resetPw_view",
                                "/resetPw",
                                "/checkDuplicateId",
                                "/checkDuplicateNickname",
                                "/successJoin_view",
                                "/css/**", "/js/**"
                        ).permitAll()
//                        .requestMatchers("/seller/**").hasRole("SELLER") // 200(판매자) 이상 권한이 필요한 페이지
//                        .requestMatchers("/admin/**").hasRole("ADMIN") // 300(관리자) 이상 권한이 필요한 페이지
//                        .requestMatchers("/superAdmin/**").hasRole("SUPER_ADMIN") // 400(최고운영자) 권한이 필요한 페이지
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
                        .successHandler(loginSuccessHandler)
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