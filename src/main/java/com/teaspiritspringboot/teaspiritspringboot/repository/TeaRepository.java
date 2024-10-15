package com.teaspiritspringboot.teaspiritspringboot.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.teaspiritspringboot.teaspiritspringboot.model.Tea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface TeaRepository extends JpaRepository<Tea,String>{
  
    Page<Tea> findByNameContains(String name, Pageable pageable);
    Page<Tea> findByType(String type, Pageable pageable);
    

//     Optional<Tea> tea = teaRepository.findById(sku);
// if (tea.isPresent()) {
//     return tea.get();  // Récupère l'objet Tea si trouvé
// } else {
//     // Gérer le cas où le produit n'est pas trouvé
// }


    // @Transactional
    // @Modifying
    // @Query("UPDATE Tea t SET t.sku = :newSku, t.name = :newName WHERE t.sku = :sku")
    // int updateTeaSkuAndName(@Param("newSku") int newSku, @Param("newName") String newName, @Param("sku") int sku);
    /*
     * la méthode ci-dessus ne fonctionne pas, 
     * parce que la clé sku dans la table Tea est une clé étrangère qui relie la clé primaire de la table Product,
     * si je modifie tout d'abord le sku dans la table Tea, et le nouveau sku n'existe pas dans Product,il ne respecte pas la restriction de clé primaire
     * il faut d'abord modifier sku dans Product
     */


    

    
}
