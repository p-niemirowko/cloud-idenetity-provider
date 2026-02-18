package com.pniemirowko.cloud.identity.provider.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignupUserResponse {
    String id;
    String username;
}
