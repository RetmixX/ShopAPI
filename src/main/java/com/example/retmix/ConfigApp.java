package com.example.retmix;

import com.example.retmix.utils.TokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigApp {

    @Bean
    public TokenUtil tokenUtil(){
        return new TokenUtil();
    }
}
