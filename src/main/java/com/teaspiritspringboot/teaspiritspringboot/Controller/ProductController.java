package com.teaspiritspringboot.teaspiritspringboot.Controller;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.teaspiritspringboot.teaspiritspringboot.model.Accessory;
import com.teaspiritspringboot.teaspiritspringboot.model.Product;
import com.teaspiritspringboot.teaspiritspringboot.model.Selection;
import com.teaspiritspringboot.teaspiritspringboot.model.Tea;
import com.teaspiritspringboot.teaspiritspringboot.repository.AccessoryRepository;
import com.teaspiritspringboot.teaspiritspringboot.repository.ProductRepository;
import com.teaspiritspringboot.teaspiritspringboot.repository.SelectionRepository;
import com.teaspiritspringboot.teaspiritspringboot.repository.TeaRepository;


@Controller
@RequestMapping("/teaspirit") //Base URL /products : Toutes les routes de ce contrôleur commenceront par /products
public class ProductController {


    @Autowired SelectionRepository selectionRepository;
    @Autowired TeaRepository teaRepository;
    @Autowired AccessoryRepository accessoryRepository;

    @GetMapping()
    public String getHomePage(Model model){
        List<Selection> selections = selectionRepository.findAll();
        if (selections != null) {
            model.addAttribute("selections", selections);
            return "home";
        }
        return "noResult";
        
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
        
        
    }

     @GetMapping("/bysku") 
     public String getProduct(@RequestParam String sku, Model model) {
        if (sku != null) {
            if(sku.matches("^[Aa]\\d{4}$")){
                Accessory accessory = accessoryRepository.findBySku(sku);
                if(accessory != null){
                    model.addAttribute("accessory", accessory);
                    return "accessoryDetails";
                }
            }

            if(sku.matches("^[Tt]\\d{4}$")){
                Tea tea = teaRepository.findBySku(sku);
                if (tea != null) {
                model.addAttribute("tea", tea);
                return "teaDetails";
                }   
            }



            /*
            if(sku.matches("^[Cc]\\d{4}$")){
                Page<GiftSet> giftSet = giftSetRepository.findBySku(sku,pageable);
                if (!giftSet.isEmpty()) {
                model.addAttribute("giftSet", giftSet);
                return "giftSetDetails";
                }   
            }
             */
        } 
    
        return "noResult";
   
     }

    //Route pour renvoyer un product en cliquant sur son image
   
    // Route pour renvoyer un résultat de recherche, soit une liste d'un produit ou des produits
    // @Autowired ProductRepository productRepository;
    // @GetMapping("/search")
    // public String searchProduct(@RequestParam String query, Model model, Pageable pageable) {
    //     Page<Product> products;

    //     if (query.matches("^[A-Za-z]\\d{4}$")) {
    //         Product product = productRepository.findBySku(query);
    //         products = (product != null) ? ConvertProductToPage(product, pageable) : Page.empty();
    //     } else {
    //         products = productRepository.findByNameContains(query, pageable);
    //         if (products.isEmpty()) {
    //             products = Page.empty();
    //         }
    //     }

    //     model.addAttribute("products", products);
    //     return "productlist";
    // }

    @Autowired ProductRepository productRepository;
    @GetMapping("/search")
    public String searchProduct(@RequestParam String query, Model model, Pageable pageable) {
        Page<Product> products;
        if (query.matches("^[A-Za-z]\\d{4}$")) {
            products = productRepository.findBySku(query, pageable);
        } else {
            products = productRepository.findByNameContains(query, pageable);
        }
        if (products.isEmpty()) {
            List<Selection> selections = selectionRepository.findAll();
            if (selections != null) {
                model.addAttribute("selections", selections);
            }
            return "noResult";
        }

        model.addAttribute("products", products);
        model.addAttribute("query", query);
        return "productList";
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










}
