package com.valorant.signUp.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author KenLi
 * @date 2023-08-04
 */
@Data
public class LinkOpenId {
    private String nickname;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("member_id")
    private String memberId;
    @JsonProperty("member_type")
    private String memberType;
    private String uuid;
    @JsonProperty("u_id")
    private String uId;
}
