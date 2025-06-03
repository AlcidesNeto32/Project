package application;

import entites.Product;
import exception.ProductNotFoundException;
import service.Payments;

import java.util.*;

public class Program {
    public static void main(String[] args) {
        Random rd = new Random();
        Scanner sc = new Scanner(System.in);
        List<Product> listProduct = new ArrayList<>();
        char confirm = '0';
        String name = null;
        boolean test_everything = false;
        Map<Integer, Product> productMap = new HashMap<>();
        Payments payments = new Payments();

        do {
            System.out.println("Select a option: ");
            System.out.println("1.Add product");
            System.out.println("2.Buy a product");
            System.out.println("3.Show all products");
            System.out.println("4.Search for product");
            System.out.println("5.Delete product");
            System.out.println("Choice: ");
            int select = sc.nextInt();
            sc.nextLine();
            switch (select) {
                case 1:
                    System.out.println("Product name: ");
                    name = sc.nextLine();
                    System.out.println("Quantity: ");
                    int quantity = sc.nextInt();
                    System.out.println("Product price: ");
                    double price = sc.nextDouble();
                    int id = rd.nextInt(0,100000);
                    System.out.println("ID of product: " + id);
                    productMap.put(id, new Product(name, quantity, price, id));
                    listProduct.add(new Product(name, quantity, price,id));
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
                        do {
                            System.out.println("Quantity: ");
                            quantity_toBuy = sc.nextInt();
                            if (quantity_toBuy > product.getQuantity()) {
                                System.out.println("This quantity is higher than the amount of inventory!");
                                test_everything = true;
                            } else {
                                test_everything = false;
                            }
                        } while (test_everything);
                        double value = quantity_toBuy * product.getPrice();
                        System.out.println("The value is: $" + value);

                        System.out.println("What will be the payment method? ");
                        System.out.println("1.Money");
                        System.out.println("2.Card of Credit");
                        System.out.println("3.Card of Debit");
                        System.out.print("Choice: ");
                        int choice_payment = sc.nextInt();
                        switch (choice_payment) {
                            case 1:
                                do {
                                    System.out.println("Enter the amount of money to pay: ");
                                    double money_payment = sc.nextDouble();
                                    test_everything = payments.money_payment(money_payment, value);
                                    if (test_everything){
                                        System.out.println("[ERROR]: check again the money quantity!");
                                        System.out.println();
                                    }
                                } while (test_everything);

                                System.out.println("Payment done!");
                                product.setQuantity(product.getQuantity() - quantity_toBuy);
                                break;
                            case 2:
                                int parcels;
                                System.out.println("We divided up to 10x");
                                System.out.println("Attention! In this format there is an extra 3% charge because of the card machine rate");
                                // need change in value when card payment is chosen value * 1.03!!
                                do {
                                    System.out.println("How many parcel you wish to divide? ");
                                    parcels = sc.nextInt();
                                } while (parcels > 10);
                                System.out.print("Value of parcel: $" + String.format("%.2f%n", payments.payment_creditCard(parcels, value)));
                                System.out.println("Total value: $" + String.format("%.2f%n", payments.payment_creditCard(parcels, value) * parcels));
                                product.setQuantity(product.getQuantity() - quantity_toBuy);
                                break;
                            case 3:
                                System.out.println("Attention! In this format there is an extra 3% charge because of the card machine rate");
                                // need change in value when card payment is chosen value * 1.03!!
                                System.out.println("Enter the amount of money to pay: ");
                                double money_payment = sc.nextDouble();
                                money_payment *= 1.03;
                                System.out.println("Value with tax: " + money_payment);
                                do {
                                    test_everything = payments.payment_debitCard(money_payment, value);
                                    if (test_everything){
                                        System.out.println("[ERROR]: check again the money quantity!");
                                        System.out.println();
                                    }
                                } while (test_everything);
                                System.out.println("Payment done!");
                                product.setQuantity(product.getQuantity() - quantity_toBuy);
                                break;
                            default:
                                System.out.println("[ERROR] this option does not exist!");
                        }
                    } else {
                        throw new ProductNotFoundException();
                    }
                    break;
                case 3:
                    if (productMap.isEmpty()) {
                        throw new ProductNotFoundException();
                    }
                    for (Product pr : listProduct) {
                        System.out.println(pr);
                    }
                    break;
                case 4:
                    System.out.println();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("This option does not exist! ");
            }
            System.out.println("Do you want continue? Y/N");
            confirm = sc.next().charAt(0);
        } while (confirm != 'N' || confirm != 'n');
    }
}
