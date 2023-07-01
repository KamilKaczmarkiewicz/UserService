package com.kams.UserService.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
record AuthenticationResponse(
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("user_id")
        long userId
) {
}
