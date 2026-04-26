package org.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        var factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(3000); // 3s
        factory.setReadTimeout(3000);

        return new RestTemplate(factory);
    }
}
