package com.lsukys.codechella.tradutor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import static com.lsukys.codechella.tradutor.config.DeepLConstantes.DEEPL_AUTH_PREFIX;

public class DeepLConfig {

    private static String deeplApiKey;

    public static String getDeeplApiKey() {
        return deeplApiKey;
    }

    @Value("${deepl.api-key}")
    public void setDeeplApiKey(String deeplApiKey) {
        DeepLConfig.deeplApiKey = DEEPL_AUTH_PREFIX + " " + deeplApiKey;
    }

}
