package com.playdata.eungae.config;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfig {

    @Value("${api.key}")
    private String apiKey;

    @Value("${api.secret-key}")
    private String apiSecretKey;

    @Value("${domain}")
    private String domain;

    @Bean
    public DefaultMessageService defaultMessageService() {
        return NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, domain);
    }

}
