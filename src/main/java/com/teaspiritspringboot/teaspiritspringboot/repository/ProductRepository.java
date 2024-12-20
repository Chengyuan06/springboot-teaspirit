package com.teaspiritspringboot.teaspiritspringboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.teaspiritspringboot.teaspiritspringboot.model.Product;


/*
 * @Repository 注解用于标记一个类，使其成为 Spring 应用中的数据访问层（Data Access Layer，DAL）组件。
 * 通过 @Repository 注解，可以将数据访问的逻辑封装在这个类中，使得业务逻辑层可以通过接口来调用数据访问方法，从而提高代码的分层和解耦性。
 * JpaRepository 是 Spring Data JPA 提供的一个接口，它提供了基本的 CRUD（创建、读取、更新、删除）操作。
 * 
 * ProductRepository 可以用于管理 Product 类型的数据。Product 是一个类，代表了数据库中的 product 表，而Integer 是Proudct类中主键的类型。
 * extends JpaRepository<Product, Integer> 意味着 ProductRepository 继承了 JpaRepository 的所有方法，同时可以自定义查询方法。
 */
public interface ProductRepository extends JpaRepository<Product, String> {

    Page<Product> findBySku(String sku, Pageable pageable);
    Optional<Product> findBySku(String sku);
    Page<Product> findByNameContains(String name,Pageable pageable);

    
    // une méthode fournie par JPA est findById() qui retrouve une donnée par la clé primaire, cette méthode renvoie un résulat de <Optional>
    //donc je veux une méthode qui est nommé à une autre façon pas comme findById() et qui retrouve une donnée par la clé primaire, 
    // il me faut que je crée cette méthode dans repository, mais je n'ai pas besoins coder les requests 

    
    @Transactional
    @Modifying //自定义的更新或删除操作时，需要添加 `@Modifying` 注解来告知 Spring Data JPA 这不是一个普通的查询操作，而是一个会修改数据库数据的操作。如果没有这个注解，可能会导致运行时错误或操作无法正常执行。
    @Query("UPDATE Product e SET e.is_deleted = true WHERE e.sku = :sku")
    int softDeleteById(@Param("sku") String sku); 
    // `:sku` 中的冒号是用于标识这是一个参数占位符。
    /*
    通过在 `@Query` 注解中的 SQL 语句中使用，并在方法定义中使用 `@Param("sku")` 注解将方法参数与占位符进行绑定
    使得在执行查询或更新操作时能够动态地传入具体的值。这种方式增强了代码的灵活性和可复用性。
    */
    /*
     *  int softDeleteById(@Param("sku") int sku); il renvoie int pour savoir combien de linge a été modifié dans BDD
     *  c'est une bonne pratique pour savoir si le request est bien programmé. on peut rester en void 
     * 通常@Modifying查询返回一个int，表示受影响的行数。
     */

     @Transactional
     @Modifying
     @Query("UPDATE Product p SET p.sku = :newSku, p.name = :newName, p.price = :newPrice, p.quantity = :newQuantity, p.image1= :newImage1, p.image2 = :newImage2 WHERE p.sku = :sku")
     int updateProuctInfo(@Param("newSku") String newSku, @Param("newName") String newName, @Param("newPrice") double newPrice, @Param("newQuantity") int newQuantity, @Param("newImage1") String newImage1,@Param("newImage2") String newImage2,@Param("sku") String sku);



    @Query("SELECT p FROM Product p WHERE p.is_deleted = true")
    List<Product> getAllDeleted();
    // SELECT p: C'est la partie de sélection de la requête, où p est un alias pour l'entité Product. 
    // Je peux choisir n'importe quelle lettre ou nom comme alias, mais ici, on a choisi p pour Product.
    // FROM Product p: Cela indique qu'on veut récupérer les objets Product depuis l'entité Product, 
    // en utilisant p comme alias pour s'y référer dans le reste de la requête.
    /*
     * 在JPQL中，你需要选择的是实体对象，而不是数据库中的表记录。相应地，字段名应当使用实体类中的字段名称，而不是数据库列的名称。
     * 具体来说，你应该选择Product实体对象，并使用实体类的属性名称，而不是数据库中的is_deleted列名。
     */

}


