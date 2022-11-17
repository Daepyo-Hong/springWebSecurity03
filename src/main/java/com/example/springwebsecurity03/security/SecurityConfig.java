package com.example.springwebsecurity03.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/css/**","/js/**").permitAll()
                        //.antMatchers(HttpMethod.PUT,"/boards/**").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE,"/boards/**").hasRole("USER")
                        .antMatchers(HttpMethod.POST,"/boards","/boards/**").hasRole("USER")
                        .antMatchers(HttpMethod.GET,"/boards/write").hasRole("USER")
                        .antMatchers("/","/boards/**").permitAll()
                        .anyRequest().authenticated()
                )
                //.csrf().disable()
                //.csrf(csrf->csrf.disable())
                .formLogin(login -> login       //로그인페이지 커스터마이징
                        .loginPage("/signin")
                        .usernameParameter("email")// username(디폴트) -> email
                        .passwordParameter("pass")// password(디폴트) -> pass
                        .loginProcessingUrl("/signin/proc")//form action
                        .failureUrl("/signin?myerror") //에러페이지
                        .permitAll())
                ;
        return http.build();
    }

}
