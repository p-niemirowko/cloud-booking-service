package com.pniemirowko.cloud.booking.infrastructure.web.property;

import com.pniemirowko.cloud.booking.infrastructure.web.RemoteService;
import com.pniemirowko.cloud.booking.infrastructure.web.exception.RemoteServiceException;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Configuration
public class PropertyWebClientConfig {

    @Bean
    WebClient propertyWebClient(
            OAuth2AuthorizedClientManager manager,
            HttpClient httpClient) {

        var oauth = new ServletOAuth2AuthorizedClientExchangeFilterFunction(manager);
        oauth.setDefaultClientRegistrationId("booking-property-client");

        return WebClient.builder()
                .baseUrl("http://property-service") // todo dodac do zmiennych
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .apply(oauth.oauth2Configuration())
                .filter(errorMappingFilter())
                .filter(correlationFilter())
                .build();
    }

    private ExchangeFilterFunction errorMappingFilter() {
        return ExchangeFilterFunction.ofResponseProcessor(response -> {

            if (response.statusCode().is4xxClientError()) {
                int statusCode = response.statusCode().value();
                if (statusCode == 404) {
                    return Mono.error(new RemoteServiceException(
                            RemoteService.PROPERTY,
                            HttpStatus.NOT_FOUND,
                            "Property not found"));
                    // todo add others statues
                }
                return Mono.error(new RemoteServiceException(
                        RemoteService.PROPERTY,
                        HttpStatus.valueOf(statusCode),
                        "Property client error"));
            }

            if (response.statusCode().is5xxServerError()) {
                int statusCode = response.statusCode().value();
                return Mono.error(new RemoteServiceException(
                        RemoteService.PROPERTY,
                        HttpStatus.valueOf(statusCode),
                        "Property service unavailable"));
            }

            return Mono.just(response);
        });
    }

    private ExchangeFilterFunction correlationFilter() {
        return (request, next) -> {

            String correlationId = MDC.get("X-Correlation-Id");

            ClientRequest newRequest = ClientRequest.from(request)
                    .header("X-Correlation-Id", correlationId)
                    .build();

            return next.exchange(newRequest);
        };
    }


}
