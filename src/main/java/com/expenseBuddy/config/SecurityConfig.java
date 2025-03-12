package com.expenseBuddy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http

                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/auth/signup-view", "/auth/signup-success",
                                "/auth/login-view").permitAll() // Public pages
                        .requestMatchers("/css/**", "/images/**").permitAll() //allow access to static resources (CSS, images)
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login-view")
                        .loginProcessingUrl("/auth/login-view")
                        .defaultSuccessUrl("/user/dashboard")
                        .defaultSuccessUrl("/admin/dashboard")
                        .failureUrl("/auth/login-view?error=true")   //Redirect to login with error flag

                        .successHandler((request, response, authentication) -> {
                            String role = authentication.getAuthorities().stream()
                                    .map(authority ->authority.getAuthority())
                                    .findFirst()
                                    .orElse("");
                            if("ROLE_ADMIN".equals(role)){
                                response.sendRedirect("/admin/dashboard-view");
                            }else if ("ROLE_USER".equals(role)){
                                response.sendRedirect("/user/dashboard-view");
                            }
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );
        return http.build();

    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //for secure password storage (encrypting password)
    }

}
