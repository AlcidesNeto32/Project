package application;

import entites.Product;
import exception.ProductNotFoundException;
import service.CardPayment;

import java.util.*;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Product> listProduct = new ArrayList<>();
        char confirm = '0';
        String name = null;
        CardPayment cardPayment = new CardPayment() {
        };
        boolean test_everything = false;
        UUID uuid = UUID.randomUUID();
        Map<UUID, Product> productMap = new HashMap<>();
        int remainder = 0;


        do {
            System.out.println("Select a option: ");
            System.out.println("1.Add product");
            System.out.println("2. Buy a product");
            System.out.println("3. Show all products");
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
                    String id = uuid.toString();
                    System.out.println("ID of product: " + id);
                    productMap.put(UUID.fromString(id), new Product(name, quantity, price));
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
                        do {
                            System.out.println("Quantity: ");
                            quantity_toBuy = sc.nextInt();
                            if (quantity_toBuy > product.getQuantity()) {
                                System.out.println("This quantity is higher than the amount of inventory!");
                                test_everything = true;
                            }
                        } while (test_everything);
                        test_everything = false;
                        double value = quantity_toBuy * product.getPrice();
                        System.out.println("The value is: $" + value);

                        System.out.println("What will be the payment method? ");
                        System.out.println("1.Money");
                        System.out.println("2.Card of Credit");
                        System.out.println("3. Card of Debit");
                        System.out.print("Choice: ");
                        int choice_payment = sc.nextInt();
                        switch (choice_payment) {
                            case 1:
                                do {
                                    System.out.println("Enter the amount of money to pay: ");
                                    double money_payment = sc.nextDouble();
                                    if (money_payment < value) {
                                        System.out.println("[ERROR]: the amount is less than value of payment");
                                        test_everything = true;
                                    } else {
                                        test_everything = false;
                                    }
                                } while (test_everything == true);
                                System.out.println("Payment done!");
                                product.setQuantity(product.getQuantity() - quantity_toBuy);
                                break;
                            case 2:
                                System.out.println("Attention! In this format there is an extra 3% charge because of the card machine rate");
                                System.out.println("How many parcel you wish to divide? ");
                                int parcels = sc.nextInt();
                                System.out.println("Value of parcel: $" + cardPayment.payment_card(parcels,value));
                                break;
                        }
                    } else {
                        throw new ProductNotFoundException();
                    }
                    break;
                case 3:
                    if (productMap.isEmpty()){
                        throw new ProductNotFoundException();
                    }
                    for (Product pr : listProduct){
                        int constant = 0;
                        System.out.println(pr);
                        for(UUID getId : productMap.keySet()){
                            if (constant < 1){
                                System.out.printf("Id: %s%n" , getId);
                                constant ++;
                            }
                        }
                    }
                    break;
                default:
                    System.out.println("This option does not exist! ");
            }
            System.out.println("Do you want continue? Y/N");
            confirm = sc.next().charAt(0);
        } while (confirm != 'N' || confirm != 'n');
    }
}
