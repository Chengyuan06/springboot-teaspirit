package com.teaspiritspringboot.teaspiritspringboot.model;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(CartItemId.class)
public class CartItem {

    @Id
    @Column(name = "cartId", nullable = false)
    private Long cartId;

    @Id
    @Column(name = "productSku", nullable = false)
    private String productSku;

    @Column(name = "productQuantity", nullable = false)
    private int productQuantity;

    @ManyToOne
    @JoinColumn(name = "cartId", referencedColumnName = "cartId", insertable = false, updatable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "productSku", referencedColumnName = "sku", insertable = false, updatable = false)
    private Product product;

   

    // Constructeurs
    public CartItem() {}

    
    public CartItem(Long cartId, String productSku, int productQuantity) {
        this.cartId = cartId;
        this.productSku = productSku;
        this.productQuantity = productQuantity;
    }

      // Méthode utilitaire pour récupérer le nom du produit
      public String getProductName() {
        return product != null ? product.getName() : null;
    }

    // Méthode utilitaire pour récupérer le prix du produit
    public double getProductPrice() {
        return product != null ? product.getPrice() : 0.0;
    }


    public Long getCartId() {
        return cartId;
    }



    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }



    public String getProductSku() {
        return productSku;
    }



    public Cart getCart() {
        return cart;
    }


    public Product getProduct() {
        return product;
    }


    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }



    public int getProductQuantity() {
        return productQuantity;
    }



    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
        this.cartId = cart.getId(); 
    }

    public void setProduct(Product product) {
        this.product = product;
        this.productSku = product.getSku(); 
    }






    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cartId == null) ? 0 : cartId.hashCode());
        result = prime * result + ((productSku == null) ? 0 : productSku.hashCode());
        result = prime * result + productQuantity;
        return result;
    }





    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CartItem other = (CartItem) obj;
        if (cartId == null) {
            if (other.cartId != null)
                return false;
        } else if (!cartId.equals(other.cartId))
            return false;
        if (productSku == null) {
            if (other.productSku != null)
                return false;
        } else if (!productSku.equals(other.productSku))
            return false;
        if (productQuantity != other.productQuantity)
            return false;
        return true;
    }

    

    

}