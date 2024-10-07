package com.teaspiritspringboot.teaspiritspringboot.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Categorie {
    private String nom;
    private int ID;

    
    public Categorie(int ID, String nom) {
        this.ID = ID;
        this.nom = nom;
    }
    // Polymorphisme: on peut avoir des constructors différents (voir dans boutique fonction créerCategoire)
    public Categorie(String nom){
        this.nom = nom;
    }

    
    // crud : read une categorie, read tous les categories, sauvegarder une categorie, upadate une categorie, suppr
    // read la liste des categories:
    public static ArrayList<Categorie> readAllCategorie(){
        ArrayList<Categorie> listeCategorie = new ArrayList<Categorie>();
            try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/theboutique","root","");
            Statement st = con.createStatement();
            String requetSql = "select * from categorie";
            ResultSet result = st.executeQuery(requetSql);
            while(result.next()){
                Categorie categorie = new Categorie(result.getInt("ID"), result.getString("nom"));
                listeCategorie.add(categorie);
            }
        con.close();  
        } catch (SQLException e) {      
            e.printStackTrace();
        }
        return listeCategorie;
    }

    // fonction pour afficher la liste de toutes les categories pour modifier, supprimer par la suite si besoins
    public static void afficheCategorieList(){
        for(Categorie categorie:readAllCategorie()){
            System.out.println("ID:["+categorie.ID+"]\t"+"nom: "+categorie.nom);
        }
    }
    // fonction pour vérifier si un CategorieID existe dans BDD, si existe, return true; sinon, false
    public static boolean checkCategorieID(int categorieID){
        ArrayList<Integer> categorieIDList = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/theboutique","root","");
            Statement st = con.createStatement();
            String sql = "select `categorieID` from `categorie`";
            ResultSet rsl = st.executeQuery(sql);
            while(rsl.next()){
                rsl.getInt("ID");
                categorieIDList.add(rsl.getInt("ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(categorieIDList.contains(categorieID)){
            return true;
        }else{
            return false;
        }

    }

    // read une Categorie(ID,nom), selon un categorieID
    public static Categorie categorieByID(int ID){
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/theboutique","root","");
            PreparedStatement statement = con.prepareStatement("select * from categorie where ID = ?");
            // pourquoi "?", parceque cette fonction est pour trouver l'ensemble d'info de Categorie(nom et Id) selon  un categorieID qui est dans l'argument de la fonction, et je ne sais pas encore
            // et "?" sera parametré via setInt(emplacement de ?, par quoi je veux remplacer "?")
            // une fois que "?" est remplacé, le statement est complet et concrète, il peut faire excuteQuery
            statement.setInt(1, ID); 
            // 1 est le l'emplacement de 1er?, sur cet emplacement, je veux remplacer ? par categorieID
            // S'il il y a 2ème request avec ? dedans, setInt(2, xx), 3ème setInt(3, wwwwww)
            // il faut autant de set() que le nombre de "?"
            // setInt(1,a), setString(2,b), setDouble(3,c) est en fonction de type de 2ème variable dans ( )
            // setInt(1, categorieID), ce categorieID est le categorieID dans l'argument de la fonction
            ResultSet result = statement.executeQuery();
            // executeQuery() : cette méthode permet d'exécuter une requête et renvoie un objet de type ResultSet qui contient les données issues de l'exécution de la requête
            result.next();
            //Déplacement du curseur de la position par défaut à la première ligne.
            return new Categorie(ID, result.getString("nom"));
        } catch (SQLException e) {
           
            e.printStackTrace();
        }  
        return null;// c'est une méthode avec un return, il est obligé de retourner quelque chose 
    }

    // n'est pas static, il est appelé par un objet de Categorie, voir dans la class Boutique, ligne 244
    public void saveCategorie(){
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/theboutique","root","");
            PreparedStatement statement = con.prepareStatement("insert into `categorie` (`ID`, `nom`) values (?,?)");
            // ID est primary key et Auto_Increment, je ne laisse pas utilisateur modifier
            // soit, je saute cette info dans le request sql ("insert into `categorie` (`nom`) values (?)")
            // soit, je laisse tout, avec setString(1, null)
            statement.setString(1,null); // je ne peux pas mettre setInt(1,null), car null n'est pas un Int
            statement.setString(2,this.nom);
            statement.executeUpdate();
            con.close();    
        } catch (SQLException e) {
            System.out.println("La categorie existe déjà");
            e.printStackTrace();
        }   

    }
    // modifier le nom d'une categorie dans BDD, ce n'et pas une fonction static, car elle sera solicitée par objet categorie
    // idee de cette fonction est de 1èrement récupérer une categorie dans BDD, et puis la modifier.Donc la fonction est appelée par un objet categorie qui est récupérée
    public void updateCategorie(){
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/theboutique","root","");
            PreparedStatement statement = con.prepareStatement("update `categorie` set `nom`= ? where `ID` = ?");
            statement.setString(1, this.nom); // this est l'objet qui va appeler cette fonction. Comment avoir cet objet? c'est le travail de la class Boutique, qui s'occupe de récupérer les infos des clients. Categorie est juste responsable de gérér toutes les manipulation de catégories, quand on solicite cette fonction, il a déja un objet Categorie devant lui. Il prend tout ce qu'il a recu, les filtres sont à Boutique 
            statement.setInt(2, this.ID);
            statement.executeUpdate(); //renvoie rien, car pas besoins, c'est des requests 'insert,delete,update'
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCategorie(){
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/theboutique","root","");
            PreparedStatement statement = con.prepareStatement("delete from `categorie` where `ID` = ?");
            statement.setInt(1, this.ID);
            statement.executeUpdate();
            con.close();
        } catch (SQLException e) {
          
            e.printStackTrace();
        }

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getCategorieID() {
        return ID;
    }

    public void setCategorieID(int categorieID) {
        this.ID = categorieID;
    }
    

    public String toString() {
        return "Categorie [ID=" + ID + ",nom=" + nom + "]";
    }

   
    
}
