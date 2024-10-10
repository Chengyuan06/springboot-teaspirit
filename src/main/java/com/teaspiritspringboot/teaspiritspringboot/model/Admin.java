// package com.teaspiritspringboot.teaspiritspringboot.metier;

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.Statement;
// import java.util.ArrayList;

// public class Admin {
//     String email;
//     String nom;
//     String prenom;
//     String mot_de_passe;

//      // fonction vérifier si un sku donné par admin existe dans BDD
//     public static boolean skuCheck(int sku){
//             ArrayList<Integer> skuList = new ArrayList<>();
//             try{
//                 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/teaspirit","root","");
//                 Statement st = con.createStatement();
//                 String sql = "select `sku` from `produits`";
//                 ResultSet result = st.executeQuery(sql);
//                 while(result.next()){
//                         skuList.add(result.getInt("sku"));
//                     }
//                 con.close();
//                 if(skuList.contains(sku)){
//                     // System.out.println("Le sku existe déjà");
//                     return true;
//                 }else{
//                     // System.out.println("Le sku n'existe pas");
//                     return false;
//                 }
//                 }catch(SQLException e){
//                     e.printStackTrace();
//                 }return false;
//         }

   



// }
