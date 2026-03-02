package com.pniemirowko.cloud.identity.provider.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.pniemirowko.cloud.identity.provider.entity.AppUser;
import com.pniemirowko.cloud.identity.provider.repositroy.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration
public class TokenConfig {

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(
            UserRepository userRepository) {

        return context -> {
            if (AuthorizationGrantType.AUTHORIZATION_CODE.equals(
                    context.getAuthorizationGrantType())) {

                Authentication authentication = context.getPrincipal();
                String username = authentication.getName();
                AppUser user = userRepository.findUserByUsername(username)
                        .orElseThrow(); // todo exception handler
                context.getClaims().claim("userId", user.getId());
                context.getClaims().claim("type", TokenType.CLIENT.name());
            }

            if (AuthorizationGrantType.CLIENT_CREDENTIALS.equals(
                    context.getAuthorizationGrantType())) {

                context.getClaims().claim("type", TokenType.SERVICE.name());
                context.getClaims().claim("service_name",
                        context.getRegisteredClient().getClientId());
            }
        };
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (selector, context) -> selector.select(jwkSet);
    }

    private static RSAKey generateRsa() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    private static KeyPair generateRsaKey() {
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }
}
