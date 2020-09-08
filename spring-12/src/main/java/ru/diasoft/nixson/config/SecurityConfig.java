package ru.diasoft.nixson.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Autowired
    private ServerAuthenticationEntryPoint serverAuthenticationEntryPoint;

    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(
            ServerHttpSecurity http) {
        return http.csrf().disable()
                .authorizeExchange()
                .pathMatchers("/").permitAll()
                .pathMatchers("/api").hasAnyRole("ADMIN")
                .pathMatchers("/js/**").permitAll()
                .pathMatchers("/css/**").permitAll()
                .pathMatchers("/html/**").permitAll()
                .pathMatchers("/*.ico").permitAll()
                .anyExchange().authenticated()
                .and()
                .formLogin().disable()
                .logout().disable()
                .httpBasic()
                .and()
                .exceptionHandling().authenticationEntryPoint(serverAuthenticationEntryPoint)
                .and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(13);
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User
                .withUsername("user")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN")
                .build();
        return new MapReactiveUserDetailsService(user);
    }
}
