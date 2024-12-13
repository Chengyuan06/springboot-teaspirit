package com.teaspiritspringboot.teaspiritspringboot.model;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity 
@Table(name = "selection")
@PrimaryKeyJoinColumn(name = "sku")
public class Selection extends Tea {


  //ici je n'ai pas besoins de constructeur ni getters/setters, car ils sont hérités de classe Tea
  //sauf que si j'ai besoins d'ajouter des nouveaus attributs

}
