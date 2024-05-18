package org.kurgu.moviemanagamentsystemv2.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsServiceDAO userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf->csrf.disable());
        http.authorizeHttpRequests(auth->auth
                        .requestMatchers("/login", "/logout").permitAll()
                        .requestMatchers("/").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/user").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(login->login.loginPage("/login").defaultSuccessUrl("/",true))
                .logout(logout->logout.logoutUrl("/logout").logoutSuccessUrl("/login")
                        .clearAuthentication(true).deleteCookies("JSESSIONID").invalidateHttpSession(true))
                .exceptionHandling(exception->exception.accessDeniedPage("/access-denied"));

        return http.build();
    }
}
