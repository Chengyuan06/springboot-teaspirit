package com.teaspiritspringboot.teaspiritspringboot.Controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.teaspiritspringboot.teaspiritspringboot.model.Cart;
import com.teaspiritspringboot.teaspiritspringboot.model.CartItem;
import com.teaspiritspringboot.teaspiritspringboot.model.CartItemId;
import com.teaspiritspringboot.teaspiritspringboot.model.Product;
import com.teaspiritspringboot.teaspiritspringboot.model.User;
import com.teaspiritspringboot.teaspiritspringboot.repository.CartItemRepository;
import com.teaspiritspringboot.teaspiritspringboot.repository.CartRepository;
import com.teaspiritspringboot.teaspiritspringboot.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;


@Controller
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    // Récupérer ou créer un panier
    private Cart getOrCreateCart(User user, HttpSession session) {
        String sessionId = (String) session.getAttribute("sessionId");

        if (user != null) {
            return cartRepository.findByUser(user)
                    .orElseGet(() -> createCartForUser(user));
        } else {
            if (sessionId == null) {
                sessionId = UUID.randomUUID().toString();
                session.setAttribute("sessionId", sessionId);
            }
            final String finalSessionId = sessionId;
            return cartRepository.findBySessionId(finalSessionId)
                    .orElseGet(() -> createCartForSession(finalSessionId));
        }
    }

    private Cart createCartForUser(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    private Cart createCartForSession(String sessionId) {
        Cart cart = new Cart();
        cart.setSessionId(sessionId);
        return cartRepository.save(cart);
    }

    // Afficher le panier
    // @GetMapping("/cart")
    // public String viewCart(@SessionAttribute(name = "user", required = false) User user, HttpSession session, Model model) {
    //     Cart cart = getOrCreateCart(user, session);
    //     addCartToModel(cart, model);
    //     return "cartView";
    // }

    @GetMapping("/cart")
    public String viewCart(@SessionAttribute(name = "user", required = false) User user, HttpSession session, Model model) {
    Cart cart = getOrCreateCart(user, session);
    List<CartItem> items = cart.getItems();
    
    // Calculer le total
    double total = items.stream()
                         .mapToDouble(item -> item.getProductQuantity() * item.getProduct().getPrice())
                         .sum();
    BigDecimal roundedTotal = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP);
    
    model.addAttribute("items", items);
    model.addAttribute("total", roundedTotal); // Ajouter le total au modèle
    return "cartView";
    }

    // Ajouter un produit au panier
    @PostMapping("/cart/add")
    @Transactional
    public String addToCart(@SessionAttribute(name = "user", required = false) User user, HttpSession session,
                            @RequestParam("sku") String sku, @RequestParam("quantity") int quantity, Model model) {
        Cart cart = getOrCreateCart(user, session);
        Product product = findProductBySku(sku);
    
        // Vérifier si la quantité demandée est disponible en stock
        if (product.getQuantity() < quantity) {
            model.addAttribute("error", "La quantité demandée dépasse le stock disponible.");
            addCartToModel(cart, model); // Recharger les données du panier dans le modèle
            return "cart"; // Recharger la page du panier avec un message d'erreur
        }
    
        // Vérifier si l'article existe déjà dans le panier
        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getSku().equals(sku))
                .findFirst()
                .orElse(null);
    
        if (existingItem != null) {
            // Vérifier si la quantité totale (existante + demandée) dépasse le stock disponible
            int totalQuantity = existingItem.getProductQuantity() + quantity;
            if (product.getQuantity() < totalQuantity) {
                model.addAttribute("error", "La quantité totale demandée dépasse le stock disponible.");
                addCartToModel(cart, model);
                return "cart"; // Recharger la page du panier avec un message d'erreur
            }
    
            // Mettre à jour la quantité si tout est valide
            existingItem.setProductQuantity(totalQuantity);
            cartItemRepository.save(existingItem);
        } else {
            // Ajouter un nouvel article au panier
            CartItem cartItem = createCartItem(cart, product, quantity);
            cartItemRepository.save(cartItem);
            cart.getItems().add(cartItem);
        }
    
        // Sauvegarder le panier
        cartRepository.save(cart);
        addCartToModel(cart, model);
    
        // Rediriger vers la page du panier
        return "redirect:/cart";
    }
    
    
    // Supprimer un produit du panier
    @PostMapping("/cart/remove")
    @Transactional
    public String removeFromCart(@SessionAttribute(name = "user", required = false) User user, HttpSession session,
                                 @RequestParam("cartItemId") CartItemId cartItemId, Model model) {
        Cart cart = getOrCreateCart(user, session);
        CartItem cartItem = findCartItemById(cartItemId);

        cart.getItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
        cartRepository.save(cart);

        addCartToModel(cart, model);
        return "cartView";
    }

    // Mettre à jour la quantité d'un produit dans le panier
    @PostMapping("/cart/update")
    @Transactional
    public String updateCartItem(@SessionAttribute(name = "user", required = false) User user, HttpSession session,
                                 @RequestParam("cartItemId") CartItemId cartItemId, @RequestParam("quantity") int quantity, Model model) {
        Cart cart = getOrCreateCart(user, session);
        CartItem cartItem = findCartItemById(cartItemId);

        cartItem.setProductQuantity(quantity);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);

        addCartToModel(cart, model);
        return "cartView";
    }

    // Méthodes privées
    private void addCartToModel(Cart cart, Model model) {
        model.addAttribute("cart", cart);
        model.addAttribute("items", cart.getItems());
    }

    private Product findProductBySku(String sku) {
        return productRepository.findBySku(sku)
                .orElseThrow(() -> new IllegalArgumentException("Produit non trouvé"));
    }

    private CartItem createCartItem(Cart cart, Product product, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setProductQuantity(quantity);
        return cartItem;
    }

    private CartItem findCartItemById(CartItemId cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("Item non trouvé"));
    }
}

