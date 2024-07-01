package com.valorant.registerProgress.controller;

import com.valorant.dataService.entity.RegisterProgress;
import com.valorant.dataService.service.RegisterProgressService;
import com.valorant.registerProgress.form.RegisterProgressForm;
import org.owasp.esapi.ESAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author KenLi
 * @date 2023-07-25
 */
@Controller
public class RegisterProgressController {

    private RegisterProgressService registerProgressService;

    @Autowired
    public void setRegisterProgressService(RegisterProgressService registerProgressService) {
        this.registerProgressService = registerProgressService;
    }

    @GetMapping("/battle")
    public String getRegisterProgress(Model model) {
        List<RegisterProgress> registerProgressList = registerProgressService.getRegisterProgress();

        RegisterProgressForm registerProgressForm = new RegisterProgressForm();

        Map<String, String> areaMap = registerProgressList.stream().collect(Collectors.toMap(RegisterProgress::getArea, RegisterProgress::getCount, (k1, k2) -> k1));
        String taipeiCount = areaMap.get("taipei");
        String taichungCount = areaMap.get("taichung");
        String kaohsiungCount = areaMap.get("kaohsiung");

        double taipeiPercentD = (Double.parseDouble(taipeiCount) / 300) * 100;
        double taichungPercentD = (Double.parseDouble(taichungCount) / 300) * 100;
        double kaohsiungPercentD = (Double.parseDouble(kaohsiungCount) / 300) * 100;

        int taipeiPercent = (int) taipeiPercentD;
        int taichungPercent = (int) taichungPercentD;
        int kaohsiungPercent = (int) kaohsiungPercentD;

        registerProgressForm.setTaipeiCount(ESAPI.encoder().encodeForHTML(taipeiCount));
        registerProgressForm.setTaichungCount(ESAPI.encoder().encodeForHTML(taichungCount));
        registerProgressForm.setKaohsiungCount(ESAPI.encoder().encodeForHTML(kaohsiungCount));
        registerProgressForm.setTaipeiPercent(ESAPI.encoder().encodeForHTML(String.valueOf(taipeiPercent)));
        registerProgressForm.setTaichungPercent(ESAPI.encoder().encodeForHTML(String.valueOf(taichungPercent)));
        registerProgressForm.setKaohsiungPercent(ESAPI.encoder().encodeForHTML(String.valueOf(kaohsiungPercent)));

        model.addAttribute("registerProgressForm", registerProgressForm);

        return "battle";
    }

}
