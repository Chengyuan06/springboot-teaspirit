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
import com.teaspiritspringboot.teaspiritspringboot.utils.PasswordUtil;
import com.teaspiritspringboot.teaspiritspringboot.utils.Validator;
import com.teaspiritspringboot.teaspiritspringboot.repository.UserRepository;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/teaspirit/user") 
public class UserController {


    @Autowired UserRepository userRepository;
    @Autowired  private HttpSession httpSession;


    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password) {
    // Logique d'authentification
    return "redirect:/teaspirit/user/account";
}

    // route pour aller au page de se connecter et s'enregistrer
    @GetMapping("/login")
    public String login(){
        if (httpSession.getAttribute("loggedInUser") != null) {
            return "redirect:/teaspirit/user/account";
        }
        return "login";
    }

    // se connecter un user et puis aller au page compte user
    @PostMapping("/account")
    public String account(@RequestParam String identifier,
                          @RequestParam String password,
                          Model model){
        Optional<User> userOptional = userRepository.findByIdentifier(identifier);
        if(userOptional.isPresent()){ 
            User user = userOptional.get();
            if(PasswordUtil.checkPassword(password, user.getHashedPassword())){
                 // Stocker l'utilisateur dans la session via Spring Session
                httpSession.setAttribute("loggedInUser", user);
                model.addAttribute("user", user);
                return "account";
            }else {
                model.addAttribute("errorMessage", "Votre identifiant ou mot de passe est incorrect. Veuillez réessayer.");
                return "login";  
            }
           
        } else {
            model.addAttribute("errorMessage", "Vous n'êtes pas enregistré. Veillez vous enregistrez"); 
            return "login";  
        }
    }
     
    // Cliquer sur icon user, si user est connecté, affiche page bienvenu user; sinon, aller au page se connecter
    @GetMapping("/account")
    public String account(Model model) {
        // Vérifier si l'utilisateur est connecté
        User loggedInUser = (User) httpSession.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Rediriger vers la page de connexion si non connecté
            return "redirect:/teaspirit/user/login";
        }
        // Ajouter l'utilisateur au modèle pour l'affichage
        model.addAttribute("user", loggedInUser);
        return "account";
    }

    // Création d'user 
    @PostMapping("/signup")
    public String signup(@RequestParam String email, 
                        @RequestParam String password, 
                        @RequestParam(value = "customer", defaultValue = "customer") String role, 
                        Model model){
        if (!Validator.isValidEmail(email)) {
            model.addAttribute("errorMessage", "Email invalide");
            return "login"; // Retourner vers la vue d'inscription
        }
        String hashedPassword = PasswordUtil.hashPassword(password);
        User user = new User(email,hashedPassword,role);
        userRepository.save(user);
        model.addAttribute("user", user);
        httpSession.setAttribute("loggedInUser", user);
        return "account";
    }

    // Se déconnecter la session et redirige vesr page de se connecter
    @GetMapping("/logout")
    public String logout() {
        // Invalider la session via Spring Session
        httpSession.invalidate();
        return "redirect:/teaspirit/user/login";
        }

    // S'afficher des infos d'user connecté
    @GetMapping("/profil")
    public String profile(Model model){

        User loggedInUser = (User) httpSession.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Rediriger vers la page de connexion si non connecté
            return "redirect:/teaspirit/user/login";
        }
        // Ajouter l'utilisateur au modèle pour l'affichage
        model.addAttribute("loggedInUser", loggedInUser);
        return "accountProfil";
    }


    @PostMapping("/update-profil")
    public String updateProfil(@RequestParam String email,
                               @RequestParam String firstname,
                               @RequestParam String lastname,
                               @RequestParam String username,
                               @RequestParam(required = false) String currentPassword,
                               @RequestParam(required = false) String newPassword,
                               @RequestParam(required = false) String confirmNewPassword,
                               Model model) {

        User loggedInUser = (User) httpSession.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Rediriger vers la page de connexion si non connecté
            return "redirect:/teaspirit/user/login";
        }
        // Ajouter l'utilisateur au modèle pour l'affichage
        model.addAttribute("loggedInUser", loggedInUser);
        
        // Validation des champs obligatoires
        if (!Validator.isValidEmail(email) ||!Validator.isValidName(firstname) ||!Validator.isValidName(lastname) || !Validator.isValidName(username) ) {
            model.addAttribute("errorMessage", "Veuillez vérifier tous les champs obligatoires");
            return "accountProfil";
        }
    
        // Récupérez l'utilisateur existant à partir du dépôt
        User user = userRepository.findById(loggedInUser.getId()).get();
        if (user == null) {
            model.addAttribute("errorMessage", "Utilisateur introuvable.");
            return "login";
        }

        // Vérifiez si le nouvel email est déjà utilisé
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent() && !existingUser.get().getId().equals(user.getId())) {
            model.addAttribute("errorMessage", "Cet email est déjà utilisé par un autre utilisateur.");
            return "accountProfil";
        }
    
        // Mettre à jour les informations utilisateur
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setUsername(username);
        user.setEmail(email);
    
        // Gestion du mot de passe
        if (newPassword != null && !newPassword.isEmpty()) {

            // étape 1 vérifier si le mdp actuel est correct
            if (currentPassword == null || !PasswordUtil.checkPassword(currentPassword, user.getHashedPassword())) {
                model.addAttribute("errorMessage", "Mot de passe actuel incorrect.");
                return "accountProfil";
            }

            // étatpe 2 vérifier nouveau mdp
            if (!newPassword.equals(confirmNewPassword)) {
                model.addAttribute("errorMessage", "Le nouveau mot de passe et sa confirmation ne correspondent pas.");
                return "accountProfil";
            }
            //
            user.setPassword(PasswordUtil.hashPassword(newPassword));
      
        }
    
        // Sauvegarder les modifications
        userRepository.save(user);
        // Mettre à jour l'objet utilisateur dans la session
        httpSession.setAttribute("loggedInUser", user);
        model.addAttribute("loggedInUser", user);
        model.addAttribute("successMessage", "Profil mis à jour avec succès");
        return "accountProfil";
    }
    
    




}
    



