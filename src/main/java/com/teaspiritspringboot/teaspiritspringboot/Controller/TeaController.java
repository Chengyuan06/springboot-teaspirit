package com.teaspiritspringboot.teaspiritspringboot.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.teaspiritspringboot.teaspiritspringboot.model.Product;
import com.teaspiritspringboot.teaspiritspringboot.model.Tea;
import com.teaspiritspringboot.teaspiritspringboot.repository.ProductRepository;
import com.teaspiritspringboot.teaspiritspringboot.repository.TeaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;





@Controller
@RequestMapping("/teaspirit/tea") //Base URL /products : Toutes les routes de ce contrôleur commenceront par /products
public class TeaController {
  

    @Autowired private TeaRepository teaRepository;
    @GetMapping()
        public String geAllTeas(Model model, Pageable pageable){
            Page<Tea> teas = teaRepository.findAll(pageable);
            if(!teas.isEmpty()){
                model.addAttribute("teas", teas); 
            } else {
                model.addAttribute("teas", null); 
            }
            return "teaList";
        }

    // @GetMapping("/{type}")
    // public String getTheVert(@PathVariable("type") String type, Model model, Pageable pageable) {
    //     String decodedType = UriUtils.decode(type, StandardCharsets.UTF_8);
    //     String lowerCaseDecodedType = decodedType.toLowerCase();
    //     Page<Tea> teas = teaRespository.findByType(lowerCaseDecodedType, pageable);
    //     model.addAttribute("teas", teas);
    //     return "tealist";
    // }

    @GetMapping("/bytype")
    public String getTheVert(@RequestParam String type, Model model, Pageable pageable) {
        Page<Tea> teas = teaRepository.findByType(type, pageable);
        if(!teas.isEmpty()){
            model.addAttribute("teas", teas); // dans HTML, chaque fois quand il y a 'products' dans th:, il passe l'objet teas
            model.addAttribute("type", type);
            return "teaList";
        }
        return "error";
      
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

    


        


    

