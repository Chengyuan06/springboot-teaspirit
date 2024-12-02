package com.teaspiritspringboot.teaspiritspringboot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "accessory")
@PrimaryKeyJoinColumn(name = "sku")
public class Accessory extends Product {

    private String size;
    private String material;
    private String care;
    private String category;


    public Accessory(String sku, String name, double price, int quantity, String image1, String image2) {
        super(sku, name, price, quantity, image1, image2);
    }


    public Accessory(String sku, String name, double price, int quantity, String image1, String image2, String size, String material, String care, String category){
        super(sku, name, price, quantity, image1, image2);
        this.size = size;
        this.material = material;
        this.care = care;
        this.category = category;
    }

    public Accessory(){}


    public String getSize() {
        return size;
    }


    public String getMaterial() {
        return material;
    }


    public String getCare() {
        return care;
    }


    public void setSize(String size) {
        this.size = size;
    }


    public void setMaterial(String material) {
        this.material = material;
    }


    public void setCare(String care) {
        this.care = care;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }


    @Override
    public String toString() {
        return "Accessory [size=" + size + ", material=" + material + ", care=" + care + ", category=" + category + "]";
    }


   
    
}
