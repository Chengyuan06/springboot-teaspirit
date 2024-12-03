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
    public String account(@RequestParam String identifier,
                          @RequestParam String password,
                          Model model){
        Optional<User> userOptional = userRepository.findByIdentifierAndPassword(identifier,password);
        if(userOptional.isPresent()){ 
            User user = userOptional.get();
            model.addAttribute("user", user);
            return "account";
        } else {
            model.addAttribute("errorMessage", "Votre identifiant ou mot de passe est incorrect. Veuillez réessayer."); 
            return "login";  
        }
    }
       
    @PostMapping("/signup")
    public String signup(@RequestParam String email, 
                        @RequestParam String password, 
                        @RequestParam(value = "customer", defaultValue = "customer") String role, 
                        Model model){
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            model.addAttribute("errorMessage", "Email et mot de passe sont obligatoires.");
            return "login"; // Retourner vers la vue d'inscription
        }
        User user = new User(email,password,role);
        userRepository.save(user);
        model.addAttribute("user", user);
        return "account";
    }

}
    



