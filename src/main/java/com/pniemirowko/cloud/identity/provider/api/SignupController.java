package com.pniemirowko.cloud.identity.provider.api;

import com.pniemirowko.cloud.identity.provider.dto.ErrorHttpResponse;
import com.pniemirowko.cloud.identity.provider.dto.SignupUserRequest;
import com.pniemirowko.cloud.identity.provider.dto.SignupUserResponse;
import com.pniemirowko.cloud.identity.provider.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SignupController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestBody SignupUserRequest request) {
        Optional<SignupUserResponse> response = userService.registerUser(request);

        if (response.isEmpty()) {
            ErrorHttpResponse errorResponse = ErrorHttpResponse.builder()
                    .path("/auth/signup")
                    .status(HttpStatus.BAD_REQUEST)
                    .message("User already exist")
                    .build();
            return ResponseEntity.badRequest().body(errorResponse);
        }

        return ResponseEntity.status(201).body(response.get());
    }
}
