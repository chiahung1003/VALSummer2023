package com.valorant.signUp.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.valorant.dataService.entity.SignUpInfo;
import com.valorant.dataService.service.SignUpInfoService;
import com.valorant.signUp.form.AccessTokenRsp;
import com.valorant.signUp.form.SignUpForm;
import com.valorant.signUp.form.UserProfile;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.utils.URIBuilder;
import org.jetbrains.annotations.NotNull;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.EncodingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @author KenLi
 * @date 2023-07-31
 */
@Controller
@Log4j2
public class SignUpController {

    private SignUpInfoService signUpInfoService;

    private RestTemplate restTemplate;

    @Value("${valorant.oauth.url}")
    private String oauthUrl;
    @Value("${valorant.client.id}")
    private String clientId;
    @Value("${valorant.basic.token}")
    private String basicToken;
    @Value("${valorant.redirect.uri}")
    private String redirectUri;


    @Autowired
    public void setSignUpInfoService(SignUpInfoService signUpInfoService) {
        this.signUpInfoService = signUpInfoService;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/redirect/login")
    public RedirectView redirect() throws URISyntaxException, MalformedURLException {
        log.debug("redirect to login page");
        return new RedirectView(toAuthPage());
    }

    @NotNull
    private String toAuthPage() throws URISyntaxException, MalformedURLException {
        URIBuilder uriBuilder = new URIBuilder(oauthUrl);
        uriBuilder.setPath("MemberOAuth/authPageLogin");

        uriBuilder.addParameter("response_type", "code");
        uriBuilder.addParameter("client_id", clientId);
        uriBuilder.addParameter("redirect_uri", redirectUri);
        uriBuilder.addParameter("noCache", "Y");
        uriBuilder.addParameter("userAgent", "W");
        URL url = uriBuilder.build().toURL();
        return url.toString();
    }


    @GetMapping("/auth/signUp/form")
    public String init(WebRequest webRequest, Model model, RedirectAttributes redirectAttributes) throws URISyntaxException, IOException, EncodingException {

        log.debug(webRequest.getParameter("code"));

        String code = webRequest.getParameter("code");

        URIBuilder uriBuilder = new URIBuilder(oauthUrl);
        uriBuilder.setPath("MemberOAuth/getAccessToken");

        uriBuilder.setParameter("grant_type", "authorization_code");
        uriBuilder.setParameter("redirect_uri", redirectUri);
        String url = uriBuilder.build().toString();
        url += "&code=" + URLEncoder.encode(code, "UTF-8");

        AccessTokenRsp accessTokenRsp = null;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        RequestBody formBody = new FormBody.Builder().build();
        OkHttpClient clientOk = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Basic " + basicToken)
                .post(formBody)
                .build();
        Call call = clientOk.newCall(request);
        try (Response responseOk = call.execute()) {
            if (responseOk.code() == HttpStatus.SC_UNAUTHORIZED) {
                return "redirect:" + toAuthPage();
            }
            if (responseOk.body() == null) {
                model.addAttribute("status", "loginError");
                return "party";
            } else {
                String responseStr = responseOk.body().string();
                accessTokenRsp = objectMapper.readValue(responseStr, AccessTokenRsp.class);
            }

        }

        if (accessTokenRsp == null) {
            model.addAttribute("status", "loginError");
            return "form";
        }

        String accessToken = accessTokenRsp.getAccessToken();
        String accessTokenType = accessTokenRsp.getTokenType();
        UserProfile userProfile = null;

        uriBuilder = new URIBuilder(oauthUrl);
        uriBuilder.setPath("MemberOAuth/getUserProfile");
        url = uriBuilder.build().toURL().toString();

        request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", accessTokenType + " " + accessToken)
                .post(formBody)
                .build();

        call = clientOk.newCall(request);
        try (Response responseOk = call.execute()) {
            if (!responseOk.isSuccessful()) {
                model.addAttribute("status", "loginError");
                return "form";
            }
            if (responseOk.body() == null) {
                model.addAttribute("status", "loginError");
                return "form";
            } else {
                String responseStr = responseOk.body().string();
                userProfile = objectMapper.readValue(responseStr, UserProfile.class);
            }
        }

        if (userProfile == null) {
            model.addAttribute("status", "loginError");
            return "form";
        }

        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setUid(userProfile.getUid());
        signUpForm.setName(userProfile.getName());
        signUpForm.setPhone(userProfile.getMobileNbr());
        signUpForm.setEmail(userProfile.getEmail());
        signUpForm.setCustId(userProfile.getCustId());
        signUpForm.setAddress(userProfile.getAddress());
        if (StringUtils.isNoneBlank(userProfile.getBirthday())) {
            if (userProfile.getBirthday().length() == 8) {
                signUpForm.setBirthday(userProfile.getBirthday().substring(0, 4) + "-" + userProfile.getBirthday().substring(4, 6) + "-" + userProfile.getBirthday().substring(6, 8));
            } else if (userProfile.getBirthday().contains("/")) {
                signUpForm.setBirthday(userProfile.getBirthday().replace("/", "-"));
            } else {
                signUpForm.setBirthday(userProfile.getBirthday());
            }
        }

        model.addAttribute("status", "init");
        model.addAttribute("signUpForm", signUpForm);
        return "form";
    }

    @PostMapping("/auth/signUp/form")
    @ResponseBody
    public String signUp(Model model, SignUpForm signUpForm) {

        String uid = ESAPI.encoder().decodeForHTML(signUpForm.getUid());

//        uid = signUpForm.getCustId();
        try {
            if (signUpInfoService.isExistById(uid)) {
                return "duplicate";
            }

            String name = ESAPI.encoder().decodeForHTML(signUpForm.getName());
            String phone = ESAPI.encoder().decodeForHTML(signUpForm.getPhone());
            String email = ESAPI.encoder().decodeForHTML(signUpForm.getEmail());
            String custId = ESAPI.encoder().decodeForHTML(signUpForm.getCustId());
            String birthday = ESAPI.encoder().decodeForHTML(signUpForm.getBirthday());
            String address = ESAPI.encoder().decodeForHTML(signUpForm.getAddress());
            String riotId = ESAPI.encoder().decodeForHTML(signUpForm.getRiotId());

            SignUpInfo signUpInfo = new SignUpInfo();
            signUpInfo.setUid(uid);
            signUpInfo.setName(name);
            signUpInfo.setPhone(phone);
            signUpInfo.setEmail(email);
            signUpInfo.setCustId(custId);
            signUpInfo.setBirthday(birthday);
            signUpInfo.setAddress(address);
            signUpInfo.setRiotId(riotId);

            signUpInfoService.addSignUpInfo(signUpInfo);
            model.addAttribute("status", "success");

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

        return "success";
    }
}
