package service;

import products.Product;
import users.User;
import users.others.Order;

import java.util.Scanner;

public class UserService {

    private static UserService instance = null;

    UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public OrderService orderService = OrderService.getInstance();

    Scanner scanner = new Scanner(System.in);

    public void Main(Application app, User user) {
        System.out.println("\nUser logged in");
        System.out.println(user);
        for (; ; ) {
            System.out.println("Choose an option");
            System.out.println("1. Create order");
            System.out.println("2. Display current orders");
            System.out.println("3. Display all orders");
            System.out.println("4. Account settings");
            System.out.println("0. EXIT");

            int option = scanner.nextInt();
            if (option == 0) {
                System.out.println("Exiting...");
                break;
            }
            switch (option) {
                case 1:
                    orderService.createOrder(app, user);
                    break;
                case 2:
                    orderService.displayOrders(app, user, true);
                    break;
                case 3:
                    orderService.displayOrders(app, user, false);
                    break;
                case 4:
                    accountSettings(app, user);
                    break;
                default:
                    break;
            }
        }
    }
    private void accountSettings(Application app, User user) {
        for (; ; ) {
            System.out.println("Choose option");
            System.out.println("1. Show info");
            System.out.println("0. Back");
            int option3 = scanner.nextInt();
            switch (option3) {
                case 0:
                    return;
                case 1:
                    System.out.println(user);
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}
