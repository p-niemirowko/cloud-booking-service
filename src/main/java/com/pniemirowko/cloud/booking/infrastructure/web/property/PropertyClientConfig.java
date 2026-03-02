package com.pniemirowko.cloud.booking.infrastructure.web.property;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class PropertyClientConfig {

    @Bean
    PropertyHttpClient propertyHttpClient(WebClient propertyWebClient) {

        var factory = HttpServiceProxyFactory
                .builder()
                .exchangeAdapter(WebClientAdapter.create(propertyWebClient))
                .build();

        return factory.createClient(PropertyHttpClient.class);
    }
}
