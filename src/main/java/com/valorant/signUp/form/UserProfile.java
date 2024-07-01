package com.valorant.signUp.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author KenLi
 * @date 2023-08-04
 */
@Data
public class UserProfile {

    @JsonProperty("loginuid")
    private String loginUid;

    private String uid;

    @JsonProperty("login_type")
    private String loginType;

    @JsonProperty("mobilenbr")
    private String mobileNbr = "";

    private String name = "";

    private String nickname;

    private String picture;

    @JsonProperty("subscr_id")
    private String subscrId;

    @JsonProperty("service_status")
    private String serviceStatus;

    @JsonProperty("need_link")
    private String needLink;

    private List<LinkOpenId> linkOpenIdList;

    @JsonProperty("signup_date")
    private String signupDate;

    private String email = "";

    @JsonProperty("caller_id")
    private String callerId;

    @JsonProperty("pay_method")
    private String payMethod;

    private String status;

    @JsonProperty("user_kind")
    private String userKind;

    private String op;

    @JsonProperty("cust_birth_day")
    private String custBirthday = "";

    @JsonProperty("cust_id")
    private String custId;

    @JsonProperty("package_cde")
    private String packageCde;

    @JsonProperty("user_group")
    private String userGroup;

    @JsonProperty("personal_indic")
    private String personalIndic;

    @JsonProperty("crdt_scr")
    private String crdtScr;

    private String birthday = "";

    @JsonProperty("address_type")
    private String addressType;

    private String address = "";
}
