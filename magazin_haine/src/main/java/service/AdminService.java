package service;

import products.Product;
import users.Admin;
import users.others.Order;

import java.sql.*;
import java.text.ParseException;
import java.util.Scanner;

public class AdminService {
    private final Scanner scanner = new Scanner(System.in);

    public void Main(Application app, Admin admin) throws ParseException {
        System.out.println("Admin logged in");
        System.out.println(admin);
        try{
        String jdbcURL = "jdbc:mysql://localhost:3306/new_schema";
        String username = "root";
        String password = "flamingoSQLpa55";
        Connection connection = DriverManager.getConnection(jdbcURL, username, password);
        for (; ; ) {
            System.out.println("Select an option");
            System.out.println("1. Show Admin menu");
            System.out.println("2. Show User menu");
            System.out.println("0. Exit");
            int option = scanner.nextInt();
            if (option == 0) {
                break;
            } else if (option == 2) {
                UserService userService = new UserService();
                userService.Main(app, admin);
            } else if (option != 1) {
                System.out.println("Invalid option");
            } else {
                for (; ; ) {
                    System.out.println("Select an option");
                    System.out.println("1. Display all users");
                    System.out.println("2. Display all orders");
                    System.out.println("3. Manage store");
                    System.out.println("4. Modify order status for a user");
                    System.out.println("0. Exit");
                    int option2 = scanner.nextInt();
                    if (option2 == 0) {
                        System.out.println("Exiting...");
                        break;
                    }
                    switch (option2) {
                        case 1:
                            System.out.println(app.getUsers());
                            scanner.nextLine();
                            scanner.nextLine();
                            break;
                        case 2:
                            System.out.println(app.getOrders());
                            scanner.nextLine();
                            scanner.nextLine();
                            break;
                        case 3:
                            manageStore(app);
                            break;
                        case 4:
                            System.out.println("Give user email:");
                            String email = scanner.next();

                            for (Order order : app.getOrders()) {
                                if (order.getUser().getEmail().equals(email)) {
                                    System.out.println(order);
                                    System.out.println("Do you want to modify this order N/Y?");
                                    String status = order.getStatus();
                                    String answer = scanner.next();
                                    if (answer.equals("Y")) {
                                        System.out.println("New status:");
                                        status = scanner.next();
                                        order.setStatus(status);
                                        PreparedStatement ps3 = connection.prepareCall("select id_user from users where email = ?");
                                        ps3.setString(1, email);
                                        ResultSet resultSet3 = ps3.executeQuery();
                                        resultSet3.next();
                                        Integer id = (Integer) resultSet3.getObject(1);
                                        System.out.println(id);
                                        String query = "update orders set status = ? where id_user =?";
                                        PreparedStatement ps = connection.prepareStatement(query);
                                        ps.setString(1, status);
                                        ps.setString(2, String.valueOf(id));

                                        ps.executeUpdate();


                                    }
                                }
                            }

                            break;
                        default:
                            System.out.println("Invalid option");
                            scanner.nextLine();
                            scanner.nextLine();
                            break;
                    }
                }
            }


        }
        }catch (SQLException ex) {
            ex.printStackTrace();}
    }

    private void manageStore(Application app) {
        ProductService ps = new ProductService();
        Shop shop = app.getShop();
        if (shop != null) {
            for (; ; ) {
                System.out.println("Choose option");
                System.out.println("1. View all products from the shop");
                System.out.println("2. Add product to the shop");
                System.out.println("3. Delete product from the shop");
                System.out.println("4. Update a product");
                System.out.println("0. Back");
                int option = scanner.nextInt();
                if(option == 0){
                    System.out.println("Exiting...");
                    break;
                }
                switch (option) {
                    case 1:
                        System.out.println(shop.getProd());
                        break;
                    case 2:
                        Product p = ps.createProduct();
                        shop.addProduct(p);
                        break;
                    case 3:
                        System.out.println("Enter product name");
                        String name1 = scanner.next();
                        ps.removeProduct(shop, name1);
                        break;
                    case 4:
                        System.out.println("Enter product name");
                        String name2 = scanner.next();
                        ps.updateProduct(shop, name2);
                        break;
                    default:
                        System.out.println("Invalid option");
                        scanner.nextLine();
                        scanner.nextLine();
                        break;
                }
            }
        }
    }
}
