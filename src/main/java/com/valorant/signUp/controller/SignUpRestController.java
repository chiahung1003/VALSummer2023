package com.valorant.signUp.controller;

import org.springframework.web.bind.annotation.RestController;

/**
 * @author KenLi
 * @date 2023-08-04
 */
@RestController
public class SignUpRestController {

//    private SignUpInfoService signUpInfoService;
//
//    @Autowired
//    public void setSignUpInfoService(SignUpInfoService signUpInfoService) {
//        this.signUpInfoService = signUpInfoService;
//    }
//
//    @PostMapping("/auth/signUp")
//    public String signUp(Model model, SignUpForm signUpForm) {
//
//        String uid = ESAPI.encoder().decodeForHTML(signUpForm.getUid());
//
//        uid = signUpForm.getCustId();
//        try {
//            if (signUpInfoService.isExistById(uid)) {
//                model.addAttribute("status", "error");
//                model.addAttribute("errorMessage", "系統錯誤請重新報名");
//                return "form";
//            }
//
//            String name = ESAPI.encoder().decodeForHTML(signUpForm.getName());
//            String phone = ESAPI.encoder().decodeForHTML(signUpForm.getPhone());
//            String email = ESAPI.encoder().decodeForHTML(signUpForm.getEmail());
//            String custId = ESAPI.encoder().decodeForHTML(signUpForm.getCustId());
//            String birthday = ESAPI.encoder().decodeForHTML(signUpForm.getBirthday());
//            String address = ESAPI.encoder().decodeForHTML(signUpForm.getAddress());
//            String riotId = ESAPI.encoder().decodeForHTML(signUpForm.getRiotId());
//
//            SignUpInfo signUpInfo = new SignUpInfo();
//            signUpInfo.setUid(uid);
//            signUpInfo.setName(name);
//            signUpInfo.setPhone(phone);
//            signUpInfo.setEmail(email);
//            signUpInfo.setCustId(custId);
//            signUpInfo.setBirthday(birthday);
//            signUpInfo.setAddress(address);
//            signUpInfo.setRiotId(riotId);
//
//            signUpInfoService.addSignUpInfo(signUpInfo);
//            model.addAttribute("status", "success");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            model.addAttribute("status", "error");
//            model.addAttribute("errorMessage", "系統錯誤請重新報名");
//        }
//
//        return "success";
//    }
}
