package ru.diasoft.nixson.util;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RestAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange serverWebExchange, AuthenticationException e) {
        serverWebExchange.getResponse().getHeaders().add("WWW-Authenticate","FormBased");
        serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return Mono.empty();
    }
}
