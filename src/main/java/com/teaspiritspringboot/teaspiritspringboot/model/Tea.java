package com.teaspiritspringboot.teaspiritspringboot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity 
@Table(name = "tea")
@PrimaryKeyJoinColumn(name = "sku")
public class Tea extends Product {
    private String type;
    private String profil;
    private String origin;
    private String bio;
    private String picking;
    private String period;
    private String temperature;
    private String timing;
    private String dose;
    private String pairing;
    private String benefits;
    private String plus;



    public Tea(String sku, String name, double price, int quantity, String image) {
        super(sku, name, price, quantity, image);
    }

    public Tea(String sku, String name, double price, int quantity, String image, String profil){
        super(sku, name, price, quantity, image);
        this.profil = profil;
    }


    public Tea(String sku, String name, double price,int quantity, String image, String type, String profil, String origin, String bio,
            String picking, String period, String temperature, String timing, String dose, String pairing,
            String benefits, String plus) {
        super(sku, name, price,quantity, image);
        this.type = type;
        this.profil = profil;
        this.origin = origin;
        this.bio = bio;
        this.picking = picking;
        this.period = period;
        this.temperature = temperature;
        this.timing = timing;
        this.dose = dose;
        this.pairing = pairing;
        this.benefits = benefits;
        this.plus = plus;
    }


    public Tea(String type, String profil, String origin, String bio, String picking, String period, String temperature,
            String timing, String dose, String pairing, String benefits, String plus) {
        this.type = type;
        this.profil = profil;
        this.origin = origin;
        this.bio = bio;
        this.picking = picking;
        this.period = period;
        this.temperature = temperature;
        this.timing = timing;
        this.dose = dose;
        this.pairing = pairing;
        this.benefits = benefits;
        this.plus = plus;
    }
    public Tea(){}


    public String getType() {
        return type;
    }
    @Override
    public String toString() {
        return "Tea [type=" + type + ", profil=" + profil + ", origin=" + origin + ", bio=" + bio + ", picking="
                + picking + ", period=" + period + ", temperature=" + temperature + ", timing=" + timing + ", dose="
                + dose + ", pairing=" + pairing + ", benefits=" + benefits + ", plus=" + plus + "]";
    }


    public void setType(String type) {
        this.type = type;
    }
    public String getProfil() {
        return profil;
    }
    public void setProfil(String profil) {
        this.profil = profil;
    }
    public String getOrigin() {
        return origin;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPicking() {
        return picking;
    }
    public void setPicking(String picking) {
        this.picking = picking;
    }

    public String getPeriod() {
        return period;
    }
    public void setPeriod(String period) {
        this.period = period;
    }
    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }
    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }
    public String getPairing() {
        return pairing;
    }
    public void setPairing(String pairing) {
        this.pairing = pairing;
    }
    public String getBenefits() {
        return nl2br(benefits);
    }
    public String nl2br(String text) {
        return text.replace("\n", "<br>");
    }
    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getPlus() {
        return plus;
    }
    public void setPlus(String plus) {
        this.plus = plus;
    }
    

   
    //maintenant il faut créer des fonctions CRUD:

    // @Override
    // public String toString() {
    //     return "Tea [type=" + type + ", profil=" + profil + ", origin=" + origin + ", bio=" + bio + ", picking="
    //             + picking + ", period=" + period + ", temperature=" + temperature + ", timing=" + timing + ", dose="
    //             + dose + ", pairing=" + pairing + ", benefits=" + benefits + ", plus=" + plus + "]";
    // }


    // Read 1 : méthode pour récuperer tous les thés dans la BDD sans les afficher
    // public static ArrayList<Tea> readAllTeas(){
    //     ArrayList<Tea> allTeas = new ArrayList<>();
    //         try {
    //             Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/teaspirit","root","");
    //             Statement st = con.createStatement();
    //             String sql = "select * from product right join tea on product.sku = tea.tea_sku";
    //             ResultSet result = st.executeQuery(sql);
    //             while(result.next()){
    //                 Tea tea = new Tea(result.getInt("tea_sku"), result.getString("name"),result.getDouble("price"),result.getInt("quantity"), result.getString("image"), result.getBoolean("is_deleted"), result.getString("type"),result.getString("profil"),result.getString("origin"),result.getString("bio"),result.getString("picking"),result.getString("period"),result.getString("temperature"),result.getString("timing"),result.getString("dose"),result.getString("pairing"),result.getString("benefits"),result.getString("plus"));
    //                 allTeas.add(tea);
    //             } 

    //         } catch (Exception e) {  
    //         }
    //         return allTeas;

    // }

    // Read 2 : méthode pour afficher tous les thés avec tous les détails pour chaque thé
    // public void displayProductsDetails(){
    //     for(Tea tea:readAllTeas()){
    //         System.out.println("["+ tea.getSku() + "]\n" + tea.getName() + "\n" + tea.getPrice() + "\n" + tea.getQuantity() + "\n" + tea.isIs_deleted() + "\n" + tea.getType()+ "\n" + tea.getProfil() + "\n" + tea.getOrigin() +"\n" + tea.getBio() + "\n" + tea.getPicking() + "\n" + tea.getPeriod() + "\n" + tea.getTemperature() + "\n" + tea.getTiming() + "\n" + tea.getDose() + "\n" + tea.getPairing() + "\n" + tea.getBenefits() + "\n" + tea.getPlus());
    //         System.out.println("==============================");
    //     }

    // }

    // Read 3 : méthode pour récuperer un objet "The" avec tous ses détails selon un sku rentré par admin/client
    /* une méthode statice peut être ihéritée, mais elle ne peut pas être overrided, elle peut être hidden.*/ 
    // public static Tea readTea(int paramSku){
    //     try {
    //         Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/teaspirit","root","");
    //         PreparedStatement st = con.prepareStatement("select * from product right join tea on product.sku = tea.tea_sku where sku = ?");
    //         st.setInt(1,paramSku);
    //         ResultSet result = st.executeQuery();
    //         if(result.next()){
    //             return new Tea(result.getInt("tea_sku"),result.getString("name"),result.getDouble("price"),result.getInt("quantity"),result.getString("image"), result.getBoolean("is_deleted"), result.getString("type"), result.getString("profil"), result.getString("origin"), result.getString("bio"), result.getString("picking"), result.getString("period"), result.getString("temperature"), result.getString("timing"), result.getString("dose"), result.getString("pairing"), result.getString("benefits"), result.getString("plus")); 

    //         };
            
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     return null;
    // }
    
    // // Create : créer un nouveau thé et le sauvegarder dans la BDD:
    // // quand on a nouveau produit, enregistre le dans la BDD. Ce n'est pas static, cette fonction sera appelée par un produit specifique
    // // L'attribut "is_deleted" a une valeure par défaut dans la BDD, je n'ai pas besoins d'inserer cet attribut
    // public void createProduct(){
    //     try {
    //         Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/teaspirit","root","");
    //         PreparedStatement statementProd = con.prepareStatement("insert into `product`(`sku`, `name`, `price`,`quantity`, `image`) values(?,?,?,?,?)");
    //             statementProd.setInt(1,this.getSku());
    //             statementProd.setString(2, this.getName());
    //             statementProd.setDouble(3, this.getPrice());
    //             statementProd.setInt(4,this.getQuantity());
    //             statementProd.setString(5,this.getImage());
    //             statementProd.executeUpdate();
    //         PreparedStatement statement = con.prepareStatement("insert into `tea`(`tea_sku`, `type`, `profil`, `origin`, `bio`, `picking`, `period`, `temperature`, `timing`, `dose`, `pairing`, `benefits`, `plus`) values (?,?,?,?,?,?,?,?,?,?,?,?,?");
    //             statement.setInt(1, this.getSku());
    //             statement.setString(2, this.getType());
    //             statement.setString(3, this.getProfil());
    //             statement.setString(4,this.getOrigin());
    //             statement.setString(5,this.getBio());
    //             statement.setString(6,this.getPicking());
    //             statement.setString(7, this.getPeriod());
    //             statement.setString(8,this.getTemperature());
    //             statement.setString(9,this.getTiming());
    //             statement.setString(10,this.getDose());
    //             statement.setString(11,this.getPairing());
    //             statement.setString(12,this.getBenefits());
    //             statement.setString(13,this.getPlus());
    //             statement.executeUpdate(); 
    //             con.close();      
    //     } catch (SQLException e) {
    //         System.out.println("Le produit existe déjà"); // dans quelle condition on n'arrive pas enregistrer un produit dans BDD? sku existe déja, parceque c'est la clé primary
    //         e.printStackTrace();
    //     }
    // }

    // // Upadate: mis à jour "thé" dans BDD:
    // // fonction permet de modifier un produit existant dans la BDD selon son Sku (sku,nom,prix, quantite, etc)
    // // j'ai besoins d'un argument dans la fonction pour retrouver le produit à update. comme sku est unique, donc je modifie le produit where le sku est ? pour assurer que je modifie qu'un seul produit chaque fois. Si je modifie le sku, il faut garder ancien sku pour retrouver le produit, et puis update. Si ce n'est pas le sku à modifier, il faut juste recupère son sku actuel
   
    // public void updateProduct(int sku){
    //     try {
    //         Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/teaspirit","root","");
    //         PreparedStatement statementProd = con. prepareStatement("update `product`set `tea_sku`=?, `name`=?, `price`=?, `quantity`=?, `image`= ?, where `sku`=?");
    //         statementProd.setInt(1,this.getSku());
    //         statementProd.setString(2,this.getName());
    //         statementProd.setDouble(3,this.getPrice());
    //         statementProd.setInt(4, this.getQuantity());
    //         statementProd.setString(5, this.getImage());
    //         statementProd.setInt(6,sku);
    //         statementProd.executeUpdate();
    //         PreparedStatement statement = con.prepareStatement("update `tea` set `type`=?, `profil`=?, `origin`=?, `bio`=?, `picking`=?, `period`=?, `temperature`=?, `timing`=?, `dose`=?, `pairing`=?, `benefits`=?, `plus`=? where `sku`=?");
    //             statement.setString(1, this.getType());
    //             statement.setString(2, this.getProfil());
    //             statement.setString(3,this.getOrigin());
    //             statement.setString(4,this.getBio());
    //             statement.setString(5,this.getPicking());
    //             statement.setString(6, this.getPeriod());
    //             statement.setString(7,this.getTemperature());
    //             statement.setString(8,this.getTiming());
    //             statement.setString(9,this.getDose());
    //             statement.setString(10,this.getPairing());
    //             statement.setString(11,this.getBenefits());
    //             statement.setString(12,this.getPlus());
    //             statement.setInt(13,this.getSku()); // le sku est déja modifié dans BDD avec le 1er request, et dans BDD, "Cascade" permet de mettre à jour la clé étrangère au meme temps dans les 2 tables associés lors d'une modification 
    //         statement.executeUpdate();
    //         con.close();

    //     } catch (Exception e) {
    //         System.out.println("erreru de mis à jour");
    //         e.printStackTrace();
    //     }
    // }

    // methode de softDelete et recuperer la liste de produits "supprimés" sont hérités de la classe Parent Product


    





}
