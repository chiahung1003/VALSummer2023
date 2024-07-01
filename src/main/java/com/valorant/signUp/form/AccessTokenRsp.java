package com.valorant.signUp.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author KenLi
 * @date 2023-08-04
 */
@Data
public class AccessTokenRsp {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private long expiresIn;
}
