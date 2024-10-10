package com.teaspiritspringboot.teaspiritspringboot.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.teaspiritspringboot.teaspiritspringboot.model.Tea;
import com.teaspiritspringboot.teaspiritspringboot.repository.ProductRepository;
import com.teaspiritspringboot.teaspiritspringboot.repository.TeaRespository;

import java.util.Optional;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;



@Controller
@RequestMapping("/products") //Base URL /products : Toutes les routes de ce contrôleur commenceront par /products
public class ProductController {
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


    @Autowired private ProductRepository productRepository;
    @GetMapping("/teaspirit")
        public String getHomePage(){
            // Tea p = new Tea(9999,"yyds",33.3,100,"dsfsff","type","profil","origin","bio","pikcing","period","temperature","timing","dose","pairing","benefits","plus");
            // productRepository.save(p);

            // productRepository.softDeleteById(2222);

            // System.out.println(productRepository.getAllDeleted());
            // Tea yes = teaRespository.findByNameContains("cha");
            // System.out.println(yes);

            // productRepository.updateProuctInfo(1111, "Grenen", 0, 0, "getHomePage()", 1001);

            // Tea tea = teaRespository.findBySku(1001);
            // tea.setBenefits("dslfkjslfdklf");
            return "home";
        }

    @PutMapping("update/{sku}") // "Put" est le request d'update pour HTTP
        public ResponseEntity<String> updateTea(@PathVariable int sku, @RequestBody Tea updateTea){
            Optional<Tea> existingTea = teaRespository.findById(sku);
            if(existingTea.isPresent()){
                Tea tea = existingTea.get();
                tea.setSku(updateTea.getSku());
                tea.setName(updateTea.getName());
                tea.setPrice(updateTea.getPrice());
                tea.setQuantity(updateTea.getQuantity());
                tea.setImage(updateTea.getImage());
                tea.setType(updateTea.getType());
                tea.setProfil(updateTea.getProfil());
                tea.setOrigin(updateTea.getOrigin());
                tea.setBio(updateTea.getBio());
                tea.setPicking(updateTea.getPicking());
                tea.setPeriod(updateTea.getPeriod());
                tea.setTemperature(updateTea.getTemperature());
                tea.setTiming(updateTea.getTiming());
                tea.setDose(updateTea.getDose());
                tea.setPairing(updateTea.getPairing());
                tea.setBenefits(updateTea.getBenefits());
                tea.setPlus(updateTea.getPlus());
                teaRespository.save(tea);
                return ResponseEntity.ok("Le produit est mis à jours avec succès");
                // return ResponseEntity.ok(tea);
                
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produit introuvable");
            }

        }

        /*
         * ResponseEntity est une classe de Spring qui représente la réponse HTTP envoyée au client (navigateur ou application) 
         * lorsque ce dernier fait une requête. 
         * Elle permet de contrôler à la fois le corps de la réponse et le statut HTTP (comme 200 OK, 404 Not Found, etc.).
         * Cette méthode ResponseEntity<String> renvoie un statut HTTP avec un message comme "succes" à la fin d'opération d'update
         * Cela me donne plus de contrôle sur la réponse
         */

         /*
          * @RequestBody est une annotation de Spring. Elle indique les données renvoie par utilisateurs seront trasformé en Object
          * en quel Objet? en objet qui suit l'annotation, dans mon cas c'est en type Product, et puis je donne un nom de variable
          * Grâce à @RequestBody, Spring convertira automatiquement ce JSON en un objet Product
          */

          /*
           * .isPresent() est une méthode de la classe Optional en Java. 
           * Optional est une classe qui représente un conteneur pouvant contenir ou non une valeur
           * Cela m'évite d'avoir à vérifier constamment si une valeur est null avant de l'utiliser.
           * Pourquoi utiliser Optional ? Cela permet d'éviter les erreurs NullPointerException et d'écrire un code plus sûr.
           * Plutôt que de vérifier si une valeur est null, 
           */
          /*
           * ResponseEntity.ok est une méthode statique dans la classe ResponseEntity de Spring. 
           * Elle permet de créer une réponse HTTP avec un statut 200 OK (ce qui signifie que la requête a été traitée avec succès) et, 
           * en option, un corps de réponse (le contenu que je veux renvoyer au client).
           */

    @GetMapping("/findAllTea")
       public ResponseEntity<List<Tea>> findAllTea(){
         return ResponseEntity.ok(teaRespository.findAll());
        }
        /*
        *  Si je n'ai pas besoin de renvoyer de données supplémentaires,
        *  je peux simplement utiliser ResponseEntity.ok().build() pour indiquer que tout s’est bien passé :
        *  je peux aussi renvoyer des données au client (comme un message ou un objet)
        */

    @PostMapping("/savetea")
        public ResponseEntity<String> saveTea(@RequestBody Tea savedTea){
            teaRespository.save(savedTea);
            return ResponseEntity.status(HttpStatus.CREATED).body("Le produit est bien sauvegardé dans la base de donnée");
        }
        /*
         * ResponseEntity est une manière de représenter une réponse HTTP entière, y compris le statut et le corps de la réponse.
         * status(HttpStatus.CREATED) fixe le statut HTTP à 201, qui signifie "Créé". C'est approprié après avoir ajouté une nouvelle entité. 
         */








}
       
    



    


        


    

