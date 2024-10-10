package com.teaspiritspringboot.teaspiritspringboot.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// import com.teaspiritspringboot.teaspiritspringboot.model.Product;
import com.teaspiritspringboot.teaspiritspringboot.model.Tea;
// import com.teaspiritspringboot.teaspiritspringboot.repository.ProductRepository;
import com.teaspiritspringboot.teaspiritspringboot.repository.TeaRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;



@Controller
public class MainController {
  @Autowired private TeaRespository teaRespository;
  @GetMapping("/tea/{sku}") // {sku} est une variable de chemin (path variable), ce qui signifie qu'une partie de l'URL (par exemple /tea/123) sera extraite et utilisée comme paramètre dans la méthode.

  //@PathVariable("sku") : Cela signifie que la valeur de {sku} dans l'URL (par exemple, 123 dans /tea/123) sera capturée et attribuée à la variable int sku.
  //@PathVariable("sku") signifie que l'URL contiendra une variable appelée sku, comme dans /tea/{sku}.
  // La valeur à cet emplacement dans l'URL (par exemple 123) est attribuée à la variable sku dans la méthode.

    public String getProductById(@PathVariable("sku") int sku, Model model) {
        Tea tea = teaRespository.findBySku(sku); // 获取产品详情
        if (tea != null) {
            model.addAttribute("tea", tea);
    //         // L'objet Model est utilisé pour passer des attributs (données) depuis le contrôleur vers la vue (page HTML générée). Il te permet d'ajouter des données à la requête pour qu'elles soient accessibles dans la page Thymeleaf.
    //         //Description : Cette ligne ajoute l'objet tea au modèle. Le modèle est un conteneur de données qui sera utilisé dans la vue (page Thymeleaf) pour afficher les informations du produit.
    //         // Fonction : En ajoutant cet attribut, la page Thymeleaf pourra accéder à l'objet tea via ${tea} pour afficher ses informations comme le nom, la description, etc.
            return "product";  // 返回产品详情页面
        } else {
            return "error";  // 如果没有找到产品，则返回错误页面
        }
    }

    // Pourquoi les guillemets ? : Les guillemets sont nécessaires ici pour indiquer que "tea" est une chaîne de caractères, et cette chaîne sera le nom de l'attribut sous lequel tu veux enregistrer l'objet tea.
    // Modèle et Vue : Cet attribut sera ensuite utilisé dans le modèle pour être accessible dans la vue (le fichier Thymeleaf). Par exemple, dans ton fichier HTML, tu pourras accéder à cet objet tea en utilisant la syntaxe ${tea} dans Thymeleaf.


    // @Autowired ProductRepository productRepository;

    @GetMapping("/teaspirit")
        public String getHomePage(){
            // Tea p = new Tea(9999,"yyds",33.3,100,"dsfsff","type","profil","origin","bio","pikcing","period","temperature","timing","dose","pairing","benefits","plus");
            // productRepository.save(p);

            // productRepository.softDeleteById(2222);

            // System.out.println(productRepository.getAllDeleted());
            // Tea yes = teaRespository.findByNameContains("cha");
            // System.out.println(yes);
            return "home";
        }


        

}
    

