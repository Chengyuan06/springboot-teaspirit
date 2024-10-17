package com.teaspiritspringboot.teaspiritspringboot.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.teaspiritspringboot.teaspiritspringboot.model.Product;
import com.teaspiritspringboot.teaspiritspringboot.model.Tea;
import com.teaspiritspringboot.teaspiritspringboot.repository.ProductRepository;
import com.teaspiritspringboot.teaspiritspringboot.repository.TeaRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;





@Controller
@RequestMapping("/products") //Base URL /products : Toutes les routes de ce contrôleur commenceront par /products
public class ClientController {
  
  
  


  @Autowired private ProductRepository productRepository;
  @GetMapping("/bysku") 
  public String getProduct(@RequestParam String sku, Model model, Pageable pageable) {
      if (sku != null) {
          Product product = productRepository.findBySku(sku);
          if (product != null) {
              model.addAttribute("product", product);
              return "product";
          }
      }
      // Gérer le cas où le produit n'est pas trouvé ou le SKU est manquant
      return "error"; // ou une autre vue
  }
   
  Page<Product> ConvertProductToPage(Product product, Pageable pageable){
    List<Product> productList;
    if (product == null) {
        productList = Collections.emptyList();
    } else {
        productList = List.of(product);
    }
  // Retourner une instance de PageImpl avec la liste de produits, le pageable, et la taille de la liste
    return new PageImpl<>(productList, pageable,productList.size());


}

  @PostMapping("/search")
  public String searchProduct(@RequestParam String query, Model model, Pageable pageable){
    if (query.matches("^[A-Za-z]\\d{4}$")) {   //regular expression
        System.out.println(query);// Vérifie si c'est un SKU
        Product product = productRepository.findBySku(query);
        if (product != null) {
            model.addAttribute("products", ConvertProductToPage(product,pageable));
            return "tealist";
        }
    }else{
        Page<Product> products = productRepository.findByNameContains(query,pageable);
        if(products != null){
            model.addAttribute("products", products);
            return "tealist";

        }
    
    }
    return "error";
  }
  /* Regular expression: regex
   * ^ : Indique le début de la chaîne. Cela signifie que l'expression doit commencer dès le début de la chaîne.
   *[A-Za-z] : Correspond à une seule lettre, majuscule ou minuscule.A-Z : Toutes les lettres majuscules. a-z : Toutes les lettres minuscules.
   * \\d{4} : Correspond exactement à quatre chiffres. \\d : Correspond à un chiffre (0-9).
   * {4} : Indique que la séquence doit se répéter exactement quatre fois.
   * $ : Indique la fin de la chaîne. Cela signifie que l'expression doit se terminer ici.
   * 
   */

 

    
        //@PathVariable("sku") : Cela signifie que la valeur de {sku} dans l'URL (par exemple, 123 dans /tea/123) sera capturée et attribuée à la variable int sku.
        //@PathVariable("sku") signifie que l'URL contiendra une variable appelée sku, comme dans /tea/{sku}.
        // La valeur à cet emplacement dans l'URL (par exemple 123) est attribuée à la variable sku dans la méthode.
       
        // L'objet Model est utilisé pour passer des attributs (données) depuis le contrôleur vers la vue (page HTML générée). Il te permet d'ajouter des données à la requête pour qu'elles soient accessibles dans la page Thymeleaf.
        // Cette ligne ajoute l'objet tea au modèle. Le modèle est un conteneur de données qui sera utilisé dans la vue (page Thymeleaf) pour afficher les informations du produit.
        // En ajoutant cet attribut, la page Thymeleaf pourra accéder à l'objet tea via ${tea} pour afficher ses informations comme le nom, la description, etc.
        
    // Pourquoi les guillemets ? : Les guillemets sont nécessaires ici pour indiquer que "tea" est une chaîne de caractères, et cette chaîne sera le nom de l'attribut sous lequel tu veux enregistrer l'objet tea.
    // Modèle et Vue : Cet attribut sera ensuite utilisé dans le modèle pour être accessible dans la vue (le fichier Thymeleaf). Par exemple, dans ton fichier HTML, tu pourras accéder à cet objet tea en utilisant la syntaxe ${tea} dans Thymeleaf


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

            // List<Tea> greenTea = teaRespository.findByType("thé vert");
            // System.out.println(greenTea);
         
            return "home";
        }

    @Autowired private TeaRepository teaRepository;
    @GetMapping("/alltea")
        public String geAllProducts(Model model, Pageable pageable){
            Page<Product> products = productRepository.findAll(pageable);
            model.addAttribute("products", products); // dans HTML, chaque fois quand il y a 'teas' dans th:, il passe l'objet teas
            return "tealist";
        }

    // @GetMapping("/{type}")
    // public String getTheVert(@PathVariable("type") String type, Model model, Pageable pageable) {
    //     String decodedType = UriUtils.decode(type, StandardCharsets.UTF_8);
    //     String lowerCaseDecodedType = decodedType.toLowerCase();
    //     Page<Tea> teas = teaRespository.findByType(lowerCaseDecodedType, pageable);
    //     model.addAttribute("teas", teas);
    //     return "tealist";
    // }

    @GetMapping("/tea/filter")
    public String getTheVert(@RequestParam String type, Model model, Pageable pageable) {
        Page<Tea> teas = teaRepository.findByType(type, pageable);
        model.addAttribute("products", teas);
        model.addAttribute("type", type);
        return "tealist";
    }

    /*@RequestParam: 
     * But: Récupérer les paramètres de la requête HTTP, comme les valeurs dans l'URL, pour les utiliser dans la méthode de contrôleur.
     * @RequestParam("type") String type, cela signifie que j'attends un paramètre nommé type dans l'URL, comme /products/tea/filter?type=thé%20vert.
     * les paramètres dans URL est après ?
     */

     /*@ParamVariable
      * But: pour exraitre des valeurs de l'URL elle-même
      * Exemple d'URL: /products/123
      *
      * On peut combiner les deux selon besoins
      */

     /*
      * l'objet type Page est une structure complexe qui comprend les éléments de la page actuelle et les infos de pagination
      *Numéro de page : La page actuelle (getNumber()).
                Total de pages : Le nombre total de pages (getTotalPages()).
                Nombre total d'éléments : Le nombre total d'éléments (getTotalElements()).
                Taille de la page : Le nombre d'éléments par page (getSize()).
                Indicateurs de navigation : Méthodes comme hasNext(), hasPrevious(), etc.
     */

    /*
     * mode.addAttribute est pour passer des données du contrôleur à Thymeleaf pour les afficher dans une vue. 
     * donc,j'ajoute les données que j'aurai besons par Thymeleaf dans HTML, comme l'objet tea, ou la valeur de type
     * ici, la valeur de l'attribut type est nommé en "type", sera appélé dans le lien de pagination
     * model.addAttribute("type", type);permets au template Thymeleaf d'accéder à la valeur du type dans les liens de pagination. 
     * Cela garantit que, lorsque tu navigues entre les pages de résultats, le filtre par type de thé est maintenu.
     */
       
    

}

    


        


    

