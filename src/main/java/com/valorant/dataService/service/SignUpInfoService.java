package com.valorant.dataService.service;

import com.valorant.dataService.entity.SignUpInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author KenLi
 * @date 2023-07-10
 */
@Service
public class SignUpInfoService {

    @Value("${valorant.event.data.url}")
    private String url;
    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean isExistById(String uid) {
        String endPoint = url + "v1/user/exist/" + uid;
        Boolean result = restTemplate.getForObject(endPoint, Boolean.class);
        return Boolean.TRUE.equals(result);
    }

    public void addSignUpInfo(SignUpInfo signUpInfo) {
        String endPoint = url + "v1/user/signUp";
        restTemplate.postForObject(endPoint, signUpInfo, SignUpInfo.class);
    }
}
