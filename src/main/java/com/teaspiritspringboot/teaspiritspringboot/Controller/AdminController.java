package com.teaspiritspringboot.teaspiritspringboot.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.teaspiritspringboot.teaspiritspringboot.model.Tea;
import com.teaspiritspringboot.teaspiritspringboot.repository.TeaRepository;

@Controller
public class AdminController {
    @Autowired private TeaRepository teaRespository;
    
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

    @GetMapping("/allTea")
       public ResponseEntity<List<Tea>> getAllTea(){
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
