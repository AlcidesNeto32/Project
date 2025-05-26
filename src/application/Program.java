package application;

import entites.Product;
import exception.ProductNotExistException;
import service.CardPayment;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        List<Product> listProduct = new ArrayList<>();
        char confirm = '0';
        String name = null;

        do {
            System.out.println("Select a option: ");
            System.out.println("1.Add product");
            System.out.println("2. Buy a product");
            System.out.println("3. Show all products");
            System.out.println("Choice: ");
            int select = sc.nextInt();
            switch (select) {
                case 1:
                    System.out.println("Product name: ");
                    name = sc.next();
                    System.out.println("Quantity: ");
                    int quantity = sc.nextInt();
                    System.out.println("Product price: ");
                    double price = sc.nextDouble();
                    listProduct.add(new Product(name, quantity, price));
                    break;
                case 2:
                    if (listProduct.isEmpty()) {
                        System.out.println("Not product added");
                        break;
                    }
                    System.out.println("Name of product: ");
                    name = sc.next();
                    Product product = null;
                    for (Product p : listProduct) {
                        if (p.getName().equalsIgnoreCase(name)) {
                            product = p;
                            break;
                        }
                    }
                    if (product != null) {
                        int quantity_toBuy = 0;
                        boolean test_quantity = false;
                        do {
                            System.out.println("Quantity: ");
                            quantity_toBuy = sc.nextInt();
                            if (quantity_toBuy > product.getQuantity()) {
                                System.out.println("This quantity is higher than the amount of inventory!");
                                test_quantity = true;
                            }
                        } while (test_quantity);
                        double value = quantity_toBuy * product.getPrice();
                        System.out.println("The value is: " + value);
                        CardPayment.installments(value,4);

                    } else {
                        throw new ProductNotExistException();
                    }
                    break;
            }
            System.out.println("Do you want continue? Y/N");
            confirm = sc.next().charAt(0);
        } while (confirm != 'N' || confirm != 'n');
    }
}
