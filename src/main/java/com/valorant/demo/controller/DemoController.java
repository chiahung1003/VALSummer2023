package com.valorant.demo.controller;

import com.valorant.dataService.entity.Test;
import com.valorant.dataService.service.TestService;
import com.valorant.demo.from.DemoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KenLi
 * @date 2023-07-07
 */
@Controller
public class DemoController {

    private TestService testService;

    @Autowired
    public void setTestService(TestService testService) {
        this.testService = testService;
    }

//    @GetMapping(value = { "/", "/index" })
//    public String getDemoMessage(Model model) {
//        Test[] tests = testService.demo();
//        DemoForm demoForm = new DemoForm();
//        List<String> messages = new ArrayList<>();
//
//        for (Test test : tests) {
//            messages.add(test.getMessage());
//        }
//
//        demoForm.setMessages(messages);
//        model.addAttribute("demoForm", demoForm);
//        return "index";
//    }

}
