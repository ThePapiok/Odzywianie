package com.Odzywianie.configs;

import com.Odzywianie.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig  {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/produkty/lista").permitAll()
                        .requestMatchers("/produkty/dodaj","/kategorie").hasRole("ADMIN")
                        .requestMatchers("/zapotrzebowanie","/konto").authenticated()
                        .requestMatchers("/login","/register").anonymous()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/produkty/lista")
                        .failureUrl("/login?error=true")
                        .loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true"))
                .csrf(AbstractHttpConfigurer::disable)
                .rememberMe(remember -> remember.userDetailsService(userDetailsService));


        return http.build();

    }


    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }


}