package com.practice;

import com.practice.enums.Category;
import com.practice.models.Inventory;
import com.practice.models.Product;
import com.practice.models.VendingMachine;
import com.practice.paymentgateways.PaymentGateway;
import com.practice.paymentgateways.factory.PaymentGatewayFactory;
import com.practice.repositories.CartRepository;
import com.practice.repositories.InventoryRepository;
import com.practice.repositories.PaymentTransactionRepository;
import com.practice.repositories.ProductRepository;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Lynx's vending machine");

        Scanner scanner = new Scanner(System.in);
        ProductRepository productRepository = new ProductRepository();
        CartRepository cartRepository = new CartRepository();
        PaymentTransactionRepository paymentTransactionRepository = new PaymentTransactionRepository();
        InventoryRepository inventoryRepository = new InventoryRepository();
        PaymentGateway paymentGateway = PaymentGatewayFactory.getPaymentGateway("stripe");

        VendingMachine vendingMachine = new VendingMachine(
                cartRepository,
                inventoryRepository,
                paymentTransactionRepository,
                paymentGateway
        );

        generateInventory(productRepository, inventoryRepository, scanner);

        startVendingMachine(vendingMachine, scanner);

        System.out.println("Stopping Lynx's vending machine");
    }

    private static void startVendingMachine(VendingMachine vendingMachine, Scanner scanner) {
        /*
            1. Display list of available products
            2. Ask user to select the product
            3. Ask user to select the quantity of the product
            4. Add the product into cart and Ask whether user wants to proceed with payment or wants to select the next product.
                If user wants to select new product, go to pointer # 1
                Else, move to pointer # 5
            5. Create a payment object and save it into repository
            6. Delegate task to Payment gateway - Implement Adapter Design pattern
         */

        while(true) {

            try {

                System.out.println("Do you want to buy something? (y/n):");
                String wantToByPass = scanner.nextLine();
                boolean wishToProceed = false;

                if(!wantToByPass.equals("y")) {
                    continue;
                }

                while(!wishToProceed) {

                    try {

                        System.out.println("Available products:");
                        vendingMachine.displayAllAvailableProducts();

                        System.out.println("Please enter the product id:");
                        String productUniqueId = scanner.nextLine();

                        System.out.println("Please enter quantity of the product:");
                        int quantity = Integer.parseInt(scanner.nextLine());

                        vendingMachine.addProductsToCart(productUniqueId, quantity);

                    } catch(Exception e) {

                        System.out.println(e.getMessage());

                    }

                    vendingMachine.checkIfCartIsEmpty();

                    System.out.println("Would you like to proceed for payment? (y/n):");
                    wishToProceed = scanner.nextLine().equals("y");

                }

                vendingMachine.processPayment();
                vendingMachine.dispenseBoughtProducts();


            }catch (Exception ex) {

                System.out.println(ex.getMessage());

            }
        }
    }

    private static List<Product> generateProducts(Scanner scanner) {

        List<Product> products = new ArrayList<>();

        System.out.println("Total number of products you want to add: ");
        int numberOfProducts = scanner.nextInt();
        scanner.nextLine();

        for(int i = 0; i < numberOfProducts; i++){
            System.out.println("Enter product name:");
            String productName = scanner.nextLine(); // Use nextLine() to capture the entire line

            System.out.println("Select the Category:");
            getCategoryNames();

            String categorySelect = scanner.nextLine(); // Use nextLine() to capture the entire line
            try {
                Category category = Category.valueOf(categorySelect.toUpperCase());

                System.out.println("Enter product price:");
                double price;
                try {
                    price = scanner.nextDouble();
                    scanner.nextLine(); // Consume the newline after reading the number
                } catch (InputMismatchException e) {
                    System.out.println("Invalid price format. Please enter a numeric value.");
                    scanner.nextLine(); // Consume the invalid input
                    i--; // Decrement i to retry this product
                    continue;
                }

                String productUniqueId = generateProductUniqueId(productName);

                Product product = Product.builder()
                        .name(productName)
                        .uniqueId(productUniqueId)
                        .category(category)
                        .price(price)
                        .build();

                products.add(product);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid category. Please select from the available options.");
                i--; // Decrement i to retry this product
            }
        }

        return products;

    }

    private static void generateInventory(ProductRepository productRepository, InventoryRepository inventoryRepository, Scanner scanner) {

        List<Product> products = generateProducts(scanner);

        productRepository.saveAll(products);

        List<Inventory> inventories = products.stream().map(product -> {
            System.out.println("Enter quantity for product " + product.getName() + ":");
            int quantity = Integer.parseInt(scanner.nextLine());
            return Inventory.builder()
                    .quantity(quantity)
                    .product(product)
                    .isOutOfStock(quantity == 0)
                    .build();
        }).toList();

        inventoryRepository.saveAll(inventories);

        System.out.println("========= Done filling vending machine =========");
    }

    private static void getCategoryNames() {
        for(Category category : Category.values()) {

            System.out.println(category.name());

        }
    }

    private static String generateProductUniqueId(String productName) {

        StringBuilder sb = new StringBuilder();

        for(String subProduct : productName.split(" ")) {
            sb.append(subProduct);
        }

        return RandomStringUtils.secure().next(7, sb.toString().toLowerCase() + "0123456789");

    }

}