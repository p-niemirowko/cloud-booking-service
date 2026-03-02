package com.pniemirowko.cloud.booking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient propertyWebClient(OAuth2AuthorizedClientManager manager) {

        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(manager);

        oauth.setDefaultClientRegistrationId("booking-property-client");

        return WebClient.builder()
                .apply(oauth.oauth2Configuration())
                .baseUrl("http://property-service")
                .build();
    }

    @Bean
    WebClient paymentWebClient(OAuth2AuthorizedClientManager manager) {

        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(manager);

        oauth.setDefaultClientRegistrationId("booking-payment-client");

        return WebClient.builder()
                .apply(oauth.oauth2Configuration())
                .baseUrl("http://payment-service")
                .build();
    }
}
