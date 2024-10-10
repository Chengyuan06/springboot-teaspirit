package com.teaspiritspringboot.teaspiritspringboot.model;

import org.springframework.beans.factory.annotation.Autowired;

import com.teaspiritspringboot.teaspiritspringboot.repository.ProductRepository;

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.Statement;
// import java.util.ArrayList;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table; 

import java.util.List;


@Entity //@Entity 注解：标记该类为一个 JPA 实体，表明它对应于数据库中的一个表。
@Inheritance(strategy = InheritanceType.JOINED) 
// inheritance strategy means each class in the hierarchy will have its table, and they will be joined using foreign keys
@Table( name = "product") // @Table(name = "product") 注解：指定了这个实体类映射的数据库表的名称是 product。
public class Product {
    @Id
    private int sku; // @Id est juste l'atonnation pour l'attribut sku. Pour indiquer sku est la clé primaire dans BDD
    private String name;
    private double price;
    private int quantity;
    private String image;
    private boolean is_deleted;

    /* Si la clé primaire est auto-incrément, il faut ajouter @ GenerateValue juste après @Id
     * @Id 和 @GeneratedValue(strategy = GenerationType.IDENTITY) 注解：标记 id 字段为一个自动递增的主键。
     * ID 字段通常是一个表的主键。@GeneratedValue 注解用于指定主键生成策略，这里使用了 IDENTITY 策略，表示数据库自动生成主键值。
     */

    @Autowired static ProductRepository productRespository;

    // public static List<Product> getAllDeleted(){
    //     return productRespository.getAllDeleted();
    // }


    //constructeurs:
    public Product(int sku, String name, double price, int quantity, String image){
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.is_deleted = false;

    }

    public Product(){}


   

    @Override
    public String toString() {
        return "Product [sku=" + sku + ", name=" + name + ", price=" + price + ", quantity=" + quantity + ", image="
                + image + ", is_deleted=" + is_deleted + "]";
    }

    public int getSku() {
        return sku;
    }

    public void setSku(int sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isIsDeleted() {
        return is_deleted;
    }

    public void setIsDeleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    

    // //méthodes: CRUD
    // // Read 1 : méthode pour récuperer tous les produits dans la BDD sans les afficher
    // public static ArrayList<Product> readProducts(){
    //     ArrayList<Product> allProducts = new ArrayList<>();
    //     try {
    //         Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/teaspirit","root","");
    //         Statement st = con.createStatement();
    //         String sql = "select * from `product`";
    //         ResultSet result = st.executeQuery(sql);
    //         while(result.next()){
    //             Product product = new Tea(result.getInt("sku"), result.getString("name"),result.getDouble("price"),result.getInt("quantity"),result.getString("image"), result.getBoolean("is_deleted"));
    //             allProducts.add(product);
    //         } 

    //     } catch (Exception e) {  
    //     }
    //     return allProducts;
    // }


    // public static void displayProducts(){
    //     for(Product product:readProducts()){
    //         System.out.println("["+product.getSku()+"]\t"+product.getName()+"\t"+product.getPrice()+"\t"+product.getQuantity()+"\t"+product.isIs_deleted());
    //     }

    // }
    // // Read 2: recupérer un produit selon un sku donnée par admin/client
    // public static Product readProduct(int sku){
    //      try {
    //         Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/teaspirit","root","");
    //         PreparedStatement st = con.prepareStatement("select * from `product` where sku = ?");
    //         st.setInt(1, sku);
    //         ResultSet result = st.executeQuery();
    //         result.next();
    //         return new Tea(sku,result.getString("name"),result.getDouble("price"),result.getInt("quantity"),result.getString("image"),result.getBoolean("is_deleted"));   
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     return null;

    // }

    // // Read 3: méthode abstract pour afficher les détails de tous les produits
    // public abstract void displayProductsDetails();

    // // Create: créer un nouveau produit et le sauvegarder dans la BDD:
    // public abstract void createProduct();

    // // Upadate: mis à jour d'un produit
    // public abstract void updateProduct(int sku);

    // // SoftDelete: "supprimer" un produit selon son sku en apparence, mais les données sont toutjours dans BDD:
    // /* en réalité c'est un request "update", 1 = true (produit supprimé), 0 = false (produit non supprimé);
    //  * Dans ma BDD, pour les clés étrangères, upadate est "cascade","update" sera appliqué au meme temps ddans les deux tables associées
    //  * donc dans les classes enfants, je n'ai pas besoins de réecrire cette méthode
    //  */
    // public static void softDeleteProduct(int sku){
    //     try {
    //         Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/teaspirit","root","");
    //         PreparedStatement statement = con.prepareStatement("update `product` set `is_deleted`= 1 where `sku` = ?");
    //         statement.setInt(1, sku);
    //         statement.executeUpdate();
    //         con.close();
            
    //     } catch (SQLException e) {
    //         System.out.println("Le produit n'existe pas");
    //         e.printStackTrace();
    //     }
    // }

    // // Récupérer la liste des produits qui sont sotf-deleted:
    // public static ArrayList<Product> deleteList(){
    //     ArrayList<Product> productsDeleted = new ArrayList<>();
    //     try {
    //         Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/teaspirit","root","");
    //         Statement statement = con.createStatement();
    //         String sql = "select * from `product` where `is_deleted` = 1";
    //         ResultSet result = statement.executeQuery(sql);
    //         while(result.next()){
    //             Product product = new Tea(result.getInt("sku"), result.getString("name"), result.getDouble("price"), result.getInt("quantity"), result.getString("image"),result.getBoolean("is_deleted"));
    //             productsDeleted.add(product);
    //         }
    //         return productsDeleted;
            
    //     } catch (SQLException e) {
    //         System.out.println("Le produit n'existe pas");
    //         e.printStackTrace();
    //     }
    //     return null;


    // }


   
}