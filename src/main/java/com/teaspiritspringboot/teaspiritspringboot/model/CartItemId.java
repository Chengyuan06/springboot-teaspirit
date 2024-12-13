package com.teaspiritspringboot.teaspiritspringboot.model;

import java.io.Serializable;
import java.util.Objects;


public class CartItemId implements Serializable {
    private Long cartId;
    private String productSku;

    // Constructeurs
    public CartItemId() {}

    public CartItemId(Long cartId, String productSku) {
        this.cartId = cartId;
        this.productSku = productSku;
    }

    // Getters et setters
    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    // equals() et hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemId that = (CartItemId) o;
        return Objects.equals(cartId, that.cartId) &&
               Objects.equals(productSku, that.productSku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, productSku);
    }
}


