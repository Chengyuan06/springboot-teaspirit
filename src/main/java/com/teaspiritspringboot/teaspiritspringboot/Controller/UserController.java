package com.teaspiritspringboot.teaspiritspringboot.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.teaspiritspringboot.teaspiritspringboot.model.User;
import com.teaspiritspringboot.teaspiritspringboot.repository.UserRepository;

@Controller
@RequestMapping("/teaspirit/user") 
public class UserController {


    @Autowired UserRepository userRepository;
    @GetMapping("/login")
    public String login(){
        return "login";

    }

    @PostMapping("/account")
    public String account(@RequestParam String username, @RequestParam String password, Model model){
        Optional<User> userOptional = userRepository.findByUsernameAndPassword(username,password);
        System.out.println(userOptional);
        if(userOptional.isPresent()){ 
            User user = userOptional.get();
            model.addAttribute("user", user);
            return "account";
        }
        return "error";
      

    }
    
}
