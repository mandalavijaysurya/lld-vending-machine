package com.practice.models;

import com.practice.enums.PaymentStatus;
import com.practice.exceptions.InventoryNotFoundException;
import com.practice.paymentgateways.PaymentGateway;
import com.practice.repositories.CartRepository;
import com.practice.repositories.InventoryRepository;
import com.practice.repositories.PaymentTransactionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

public class VendingMachine {

    private final CartRepository cartRepository;
    private final InventoryRepository inventoryRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final PaymentGateway paymentGateway;
    private Cart cart;

    public VendingMachine(
            CartRepository cartRepository,
            InventoryRepository inventoryRepository,
            PaymentTransactionRepository paymentTransactionRepository,
            PaymentGateway paymentGateway
    ) {
        this.inventoryRepository = inventoryRepository;
        this.cartRepository = cartRepository;
        this.paymentTransactionRepository = paymentTransactionRepository;
        this.paymentGateway = paymentGateway;
        this.cart = null;
    }

    public void checkIfCartIsEmpty() {
        if(cart == null || cart.getCartProducts().isEmpty()){
            throw new RuntimeException("Cart is empty");
        }
    }

    public void addProductsToCart(String productUniqueId, int quantity) {

        if(this.cart == null) {
            this.cart = new Cart();
        }

        CartProduct cartProduct = getCartProductFromInventory(productUniqueId, quantity);
        addCartProductToCartAndAggregateTotalPrice(cartProduct, this.cart);

        cartRepository.save(this.cart);

    }

    public void displayAllAvailableProducts() {

        List<Inventory> inventories = inventoryRepository.getAllInventories().stream()
                .filter(inventory -> !inventory.isOutOfStock()).toList();

        if(inventories.isEmpty()) {
            throw new RuntimeException("No inventory available");
        }

        inventories.stream()
                .map(Inventory::getProduct)
                .forEach(product -> System.out.println(product.getUniqueId() + ": " + product.getName()));

    }

    private CartProduct getCartProductFromInventory(String productUniqueId, int quantity) {

        Inventory inventory = inventoryRepository.findByProductUniqueId(productUniqueId).orElseThrow(() -> new InventoryNotFoundException("Product you specified in not available"));

        if(inventory.isOutOfStock()) {
            throw new RuntimeException("Product " + productUniqueId + " is out of stock");
        }

        if(inventory.getQuantity() < quantity) {
            throw new RuntimeException("We only have " + inventory.getQuantity() + " " + productUniqueId);
        }

        inventory.setQuantity(quantity - inventory.getQuantity());

        if(inventory.getQuantity() == 0) {
            inventory.setOutOfStock(true);
        }

        inventoryRepository.save(inventory);

        return CartProduct.builder()
                .product(inventory.getProduct())
                .quantity(quantity)
                .build();

    }

    private void addCartProductToCartAndAggregateTotalPrice(CartProduct cartProduct, Cart cart) {

        double price = cartProduct.getProduct().getPrice() * cartProduct.getQuantity();
        List<CartProduct> cartProductList = cart.getCartProducts();

        this.cart.setNetPrice(cart.getNetPrice() + price);

        if(cartProductList == null) {

            cartProductList = new ArrayList<>();
            cart.setCartProducts(cartProductList);

        }

        cartProductList.add(cartProduct);

    }

    public void processPayment() {

        PaymentTransaction paymentTransaction = createPaymentTransaction(this.cart);
        processPaymentTransaction(paymentTransaction);
        cartRepository.delete(this.cart.getId());

        if(!paymentTransaction.getPaymentStatus().equals(PaymentStatus.SUCCESS)) {
            throw new RuntimeException("Issue with the payment transaction, Payment failed!");
        }

    }

    private PaymentTransaction createPaymentTransaction(Cart cart) {

        LocalDateTime now = LocalDateTime.now();

        PaymentTransaction transaction = PaymentTransaction.builder()
                .createdAt(now)
                .updatedAt(now)
                .paymentStatus(PaymentStatus.CREATED)
                .paymentAmount(cart.getNetPrice())
                .build();

        paymentTransactionRepository.save(transaction);

        return transaction;

    }

    private void processPaymentTransaction(PaymentTransaction paymentTransaction) {

        paymentTransaction = paymentGateway.performPayment(paymentTransaction);
        paymentTransactionRepository.save(paymentTransaction);

        if(paymentTransaction.getPaymentStatus() == PaymentStatus.FAILED) {
            throw new RuntimeException("something went wrong, please try again later");
        }

        System.out.println("Thanks for shopping!");

    }

    public void dispenseBoughtProducts() {

        System.out.println("Bought products:");
        this.cart.getCartProducts().stream().map(CartProduct::getProduct).forEach(System.out::println);

        this.cart = null;

    }
}
