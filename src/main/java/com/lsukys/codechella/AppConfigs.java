package com.lsukys.codechella;

import com.lsukys.codechella.tradutor.config.DeepLConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfigs {


    @Bean
    public DeepLConfig deepLConfig() {
        return new DeepLConfig();
    }
}
