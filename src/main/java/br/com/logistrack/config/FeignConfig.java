package br.com.logistrack.config;


import feign.Logger;
import feign.codec.ErrorDecoder;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@RequiredArgsConstructor
public class FeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }


    @Bean
    public ErrorDecoder errorDecoder() {
        return new SimpleErrorDecode();
    }
}
