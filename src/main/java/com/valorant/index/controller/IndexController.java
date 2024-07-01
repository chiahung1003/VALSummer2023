package com.valorant.index.controller;

import com.valorant.signUp.form.SignUpForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author KenLi
 * @date 2023-07-07
 */
@Controller
public class IndexController {

    @Value("${valorant.event.start.date}")
    private String eventStartDate;

    @GetMapping(value = { "" })
    public String toIndex(Model model) {
        return "index";
    }

    @GetMapping("/summer")
    public String toSummer(Model model) {
        return "summer";
    }

    @GetMapping("/party")
    public String toParty(Model model) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Taipei"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime partyDate = LocalDateTime.parse(eventStartDate, formatter);

        if (now.equals(partyDate) || now.isAfter(partyDate)) {
            model.addAttribute("signUpStatus", true);
        } else {
            model.addAttribute("signUpStatus", false);
        }
        return "party";
    }

    @GetMapping("/form")
    public String toForm(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        return "form";
    }
}
