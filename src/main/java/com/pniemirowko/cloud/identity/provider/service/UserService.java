package com.pniemirowko.cloud.identity.provider.service;

import com.pniemirowko.cloud.identity.provider.dto.SignupUserRequest;
import com.pniemirowko.cloud.identity.provider.dto.SignupUserResponse;
import com.pniemirowko.cloud.identity.provider.entity.AppUser;
import com.pniemirowko.cloud.identity.provider.repositroy.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Optional<SignupUserResponse> registerUser(SignupUserRequest request) {
        if (userRepository.findUserByUsername(request.getUsername()).isPresent()) {
            return Optional.empty();
        }

        AppUser appUser = AppUser.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of("USER"))
                .build();

        userRepository.save(appUser);

        SignupUserResponse response = SignupUserResponse.builder()
                .id(appUser.getId().toString())
                .username(appUser.getUsername())
                .build();
        return Optional.of(response);
    }
}
