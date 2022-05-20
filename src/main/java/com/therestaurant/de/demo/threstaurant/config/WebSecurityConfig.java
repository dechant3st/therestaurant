package com.therestaurant.de.demo.threstaurant.config;

import com.therestaurant.de.demo.threstaurant.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    private final UserService userService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
            .authorizeRequests()
            .antMatchers("/carts", "/checkout", "/charge").hasAnyAuthority("CUSTOMER")
            .antMatchers("/admin/**").hasAnyAuthority("ADMIN")
            .antMatchers("/register").permitAll()
            .anyRequest().permitAll()
        .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/login-redirect")
            .permitAll()
        .and()
            .logout().permitAll()
        .and()
            .exceptionHandling().accessDeniedPage("/403")
        ;
    }
}
