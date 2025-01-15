package com.triplem.momoim.api.auth.config;

import feign.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@EnableFeignClients
@Configuration
public class FeignConfig {

    @Bean
    public Client feignClient() {
        return new Client.Default(null, null);
    }
}