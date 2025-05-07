package com.practice;

import com.practice.enums.Category;
import com.practice.models.*;
import com.practice.paymentgateways.PaymentGateway;
import com.practice.paymentgateways.api.RazorpayApi;
import com.practice.paymentgateways.implementations.RazorpayPaymentGateway;
import com.practice.repositories.CartRepository;
import com.practice.repositories.InventoryRepository;
import com.practice.repositories.PaymentTransactionRepository;
import com.practice.repositories.ProductRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
public class VendingMachineTests {

    private PaymentTransactionRepository paymentTransactionRepository;
    private InventoryRepository inventoryRepository;
    private ProductRepository productRepository;
    private PaymentGateway paymentGateway;
    private VendingMachine vendingMachine;
    private RazorpayApi razorpayApi;
    private CartRepository cartRepository;

    @BeforeEach
    public void setUp() {

        razorpayApi = new RazorpayApi();
        paymentTransactionRepository = new PaymentTransactionRepository();
        inventoryRepository = new InventoryRepository();
        productRepository = new ProductRepository();
        paymentGateway = new RazorpayPaymentGateway(razorpayApi);
        cartRepository = new CartRepository();

        addInventoryToCart();

        vendingMachine = new VendingMachine(
                cartRepository,
                inventoryRepository,
                paymentTransactionRepository,
                paymentGateway
        );
    }

    private void addInventoryToCart() {

        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();

        Inventory inventory1 = new Inventory();
        Inventory inventory2 = new Inventory();
        Inventory inventory3 = new Inventory();

        product1.setName("Thumbs Up");
        product2.setName("Good Day");
        product3.setName("Aloo Bujeeya");

        product1.setCategory(Category.DRINK);
        product2.setCategory(Category.COOKIE);
        product3.setCategory(Category.NAMKEEN);

        product1.setUniqueId(getUniqueName(product1.getName()));
        product2.setUniqueId(getUniqueName(product2.getName()));
        product3.setUniqueId(getUniqueName(product3.getName()));

        product1.setPrice(10);
        product2.setPrice(20);
        product3.setPrice(30);

        productRepository.saveAll(product1, product2, product3);

        inventory1.setProduct(product1);
        inventory2.setProduct(product2);
        inventory3.setProduct(product3);

        inventory1.setQuantity(10);
        inventory2.setQuantity(10);
        inventory3.setQuantity(10);

        inventory1.setOutOfStock(false);
        inventory2.setOutOfStock(false);
        inventory3.setOutOfStock(false);

        inventoryRepository.saveAll(inventory1, inventory2, inventory3);

    }

    private String getUniqueName(String name) {

        return RandomStringUtils.secure().next(7, name+"1234567890");

    }

    @Test
    void testVendingMachineCartNetAmountAggregation() {

        Product product1 = productRepository.getAllProducts().get(0);
        Product product2 = productRepository.getAllProducts().get(1);
        Product product3 = productRepository.getAllProducts().get(2);

        int product1Quantity = 5;
        int product2Quantity = 10;
        int product3Quantity = 4;

        vendingMachine.addProductsToCart(product1.getUniqueId(), product1Quantity);
        vendingMachine.addProductsToCart(product2.getUniqueId(), product2Quantity);
        vendingMachine.addProductsToCart(product3.getUniqueId(), product3Quantity);

        assertDoesNotThrow(() -> vendingMachine.checkIfCartIsEmpty());

        Cart cart = cartRepository.findAll().get(0);
        double actualNetPrice = product1.getPrice() * product1Quantity + product2Quantity * product2.getPrice() + product3Quantity * product3.getPrice();

        assertEquals(cart.getNetPrice(), actualNetPrice);


    }

    @Test
    void testVendingMachineCheckIfPaymentTransactionCreated () {

        Product product1 = productRepository.getAllProducts().get(0);
        Product product2 = productRepository.getAllProducts().get(1);
        Product product3 = productRepository.getAllProducts().get(2);

        int product1Quantity = 5;
        int product2Quantity = 10;
        int product3Quantity = 4;

        vendingMachine.addProductsToCart(product1.getUniqueId(), product1Quantity);
        vendingMachine.addProductsToCart(product2.getUniqueId(), product2Quantity);
        vendingMachine.addProductsToCart(product3.getUniqueId(), product3Quantity);

        vendingMachine.processPayment();

        List<PaymentTransaction> transactions = paymentTransactionRepository.getAllPaymentTransactions();
        assertEquals(1, transactions.size());

    }

    @Test
    void testVendingMachineCheckIfCartIsEmpty() {

        Product product1 = productRepository.getAllProducts().get(0);
        Product product2 = productRepository.getAllProducts().get(1);
        Product product3 = productRepository.getAllProducts().get(2);

        int product1Quantity = 5;
        int product2Quantity = 10;
        int product3Quantity = 4;

        vendingMachine.addProductsToCart(product1.getUniqueId(), product1Quantity);
        vendingMachine.addProductsToCart(product2.getUniqueId(), product2Quantity);
        vendingMachine.addProductsToCart(product3.getUniqueId(), product3Quantity);

        vendingMachine.processPayment();

        assertThrows(RuntimeException.class, () -> vendingMachine.checkIfCartIsEmpty());

    }

    @Test
    void testVendingMachineCheckIfCartHasAllProducts() {

        Product product1 = productRepository.getAllProducts().get(0);
        Product product2 = productRepository.getAllProducts().get(1);
        Product product3 = productRepository.getAllProducts().get(2);

        int product1Quantity = 5;
        int product2Quantity = 10;
        int product3Quantity = 4;

        vendingMachine.addProductsToCart(product1.getUniqueId(), product1Quantity);
        vendingMachine.addProductsToCart(product2.getUniqueId(), product2Quantity);
        vendingMachine.addProductsToCart(product3.getUniqueId(), product3Quantity);

        Cart cart = cartRepository.findAll().get(0);

        assertEquals(cart.getCartProducts().size(), 3);
        assertTrue(cart.getCartProducts().stream().map(CartProduct::getProduct).toList().contains(product1));
        assertTrue(cart.getCartProducts().stream().map(CartProduct::getProduct).toList().contains(product2));
        assertTrue(cart.getCartProducts().stream().map(CartProduct::getProduct).toList().contains(product3));

    }

    @Test
    void testVendingMachineCheckIfExceptionThrownWhenInventoryIsEmpty() {

        Product product1 = productRepository.getAllProducts().get(0);
        Product product2 = productRepository.getAllProducts().get(1);
        Product product3 = productRepository.getAllProducts().get(2);


        vendingMachine.addProductsToCart(product1.getUniqueId(), 10);
        vendingMachine.addProductsToCart(product2.getUniqueId(), 10);
        vendingMachine.addProductsToCart(product3.getUniqueId(), 10);

        assertThrows(RuntimeException.class, () -> vendingMachine.addProductsToCart(product1.getUniqueId(), 1), "Inventory is empty");
        assertThrows(RuntimeException.class, () -> vendingMachine.addProductsToCart(product2.getUniqueId(), 1), "Inventory is empty");
        assertThrows(RuntimeException.class, () -> vendingMachine.addProductsToCart(product3.getUniqueId(), 1), "Inventory is empty");

    }

}
