package org.kurgu.moviemanagamentsystemv2.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsServiceDAO userDetailsService;

    @Bean
    public NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf->csrf.disable());
        http.authorizeHttpRequests(auth->auth
                        .requestMatchers("/login", "/logout").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/user").hasRole("ADMIN")
                        .requestMatchers("/category/add").hasRole("ADMIN")
                        .requestMatchers("/category/update/").hasRole("ADMIN")
                        .requestMatchers("/category/delete/").hasRole("ADMIN")
                        .requestMatchers("/movies/add/").hasRole("ADMIN")
                        .requestMatchers("/movies/update/").hasRole("ADMIN")
                        .requestMatchers("/movies/delete/").hasRole("ADMIN")
                        .requestMatchers("classification/classificatecategory/").hasRole("ADMIN")
                        .requestMatchers("/classification/classificatemovie/").hasRole("ADMIN")
                        .requestMatchers("/classification/update/").hasRole("ADMIN")
                        .requestMatchers("classification/delete/").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(login->login.loginPage("/login").defaultSuccessUrl("/",true))
                .logout(logout->logout.logoutUrl("/logout").logoutSuccessUrl("/")
                        .clearAuthentication(true).deleteCookies("JSESSIONID").invalidateHttpSession(true))
                .exceptionHandling(exception->exception.accessDeniedPage("/access-denied"));

        return http.build();
    }
}
