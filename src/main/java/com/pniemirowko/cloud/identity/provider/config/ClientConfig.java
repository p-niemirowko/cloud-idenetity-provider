package com.pniemirowko.cloud.identity.provider.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class ClientConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    public RegisteredClientRepository registeredClientRepository() {

        RegisteredClient userClient = createUserClient();
        RegisteredClient bookingServiceClient = createBookingServiceClient();

        return new InMemoryRegisteredClientRepository(userClient, bookingServiceClient);
    }

    private RegisteredClient createUserClient() {
        return RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("postman-client")
                .clientSecret(passwordEncoder.encode("secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("https://oauth.pstmn.io/v1/callback")
                .scope("read")
                .build();
    }

    private RegisteredClient createBookingServiceClient() {
        return RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId("booking-service")
                .clientSecret(passwordEncoder.encode("booking-secret")) // na start
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("payment.create")
                .scope("property.read")
                .build();
    }
}
