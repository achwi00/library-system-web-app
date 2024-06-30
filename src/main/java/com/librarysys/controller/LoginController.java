package com.librarysys.controller;

import com.librarysys.service.LibUserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Random;

@RestController
@RequestMapping("/log-in")
public class LoginController
{
    @Autowired
    private LibUserService libUserService;

    @PostMapping("/submit")
    public ModelAndView log_in(@RequestParam("email") String email,
                         @RequestParam("password") String password,
                         RedirectAttributes redirectAttributes){
        System.out.println("in login controller");
        //check if the user is valid, generate the sessionKey, put it in the database
        if(libUserService.doesExist(email, password)){
            ObjectId userId = libUserService.findUser(email, password);
            System.out.println("found user of id: " + userId.toString());
            String sessionKey = generateSessionKey();
            System.out.println("generated sessionkey for user: " + sessionKey);
            libUserService.updateSessionKey(userId, sessionKey);
            redirectAttributes.addAttribute("sessionKey", sessionKey);
            ModelAndView modelAndView = new ModelAndView("redirect:/panel");
            return modelAndView;
        }
        return new ModelAndView("redirect:/log-in");
    }


    public String generateSessionKey(){
        boolean doesExist= false;
        String generatedString = "";
        if(!doesExist)
        {
            int leftLimit = 48; // numeral '0'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 20;
            Random random = new Random();

            generatedString = random.ints(leftLimit, rightLimit + 1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

        }
        if(libUserService.existsBySessionKey(generatedString)) doesExist=true;
        else{
            doesExist = false;
        }
        if(doesExist) generateSessionKey();
        System.out.println("String generated: " + generatedString);
        return generatedString;
    }


}
