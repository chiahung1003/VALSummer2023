package com.valorant.dataService.service;

import com.valorant.dataService.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * @author KenLi
 * @date 2023-07-10
 */
@Service
public class TestService {

    @Value("${valorant.event.data.url}")
    private String url;
    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Test> demo() {
        String demoDataUrl = url + "v1/demo";
        ResponseEntity<Test[]> response = restTemplate.getForEntity(demoDataUrl, Test[].class);
        if (response.hasBody()) {
            Test[] tests = response.getBody();
            assert tests != null;
            return Arrays.asList(tests);
        }
        return null;
    }
}
