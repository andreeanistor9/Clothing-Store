package service;

import products.Product;
import users.User;
import users.others.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderService {
    private static OrderService instance = null;

    public OrderService() {
    }

    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderService();
        }
        return instance;
    }

    private Scanner scanner = new Scanner(System.in);

    public List<Order> getActiveOrders(Application app) {
        List<Order> activeOrders = new ArrayList<>();
        for (Order order : app.getOrders()) {
            if (order.getStatus().equals("created")) {
                activeOrders.add(order);
            }
        }
        return activeOrders;
    }
    public void displayOrders(Application app, User user,boolean b){
        int count = 0;
        System.out.println(user.getOrders());
        for (Order order : user.getOrders()) {
            if (!b || order.getStatus().equals("created")) {
                System.out.println(order);
                count++;
            }
        }
        if (count == 0) {
            if (b) {
                System.out.println("No active orders");
            } else {
                System.out.println("No orders");
            }
        }
    }

    public void createOrder(Application app, User user) {
        try{
            String jdbcURL = "jdbc:mysql://localhost:3306/new_schema";
            String username1 = "root";
            String password1 = "flamingoSQLpa55";
            Connection connection = DriverManager.getConnection(jdbcURL, username1, password1);

            Order order = new Order(user);
            Shop shop = app.getShop();
            ///id user fk pt order
            PreparedStatement ps2 = connection.prepareCall("select id_user from users where email = ?");
            ps2.setString(1, user.getEmail().trim());
            ResultSet resultSet = ps2.executeQuery();
            resultSet.next();
            Integer id=(Integer)resultSet.getObject(1);
            //inserare order
            String query = "insert into orders(status, id_user, creation_date) values(?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, order.getStatus());
            ps.setString(2,String.valueOf(id));
            ps.setString(3, String.valueOf(order.getCreateDate()));
            ps.executeUpdate();

            ///pt a obtine ultimul id_order
            Statement stmt = connection.createStatement();
            ResultSet rs1 = stmt.executeQuery("SELECT id_order FROM orders order by id_order desc LIMIT 1");
            rs1.next();
            Integer id_ord=(Integer) rs1.getObject(1);
            System.out.println(id_ord);

            System.out.println("Order created");
            for (; ; ) {
                System.out.println("Select an option");
                System.out.println("1. Add an order");
                System.out.println("2. Finish order");
                System.out.println("0. Cancel Order");
                int option = scanner.nextInt();
                if (option == 0) {
                    return;
                }
                if (option == 2) {
                    System.out.println("Do you want to place the order in our shop?(Y/N)");
                    String message = scanner.next();
                    if(message.equals("Y"))
                        System.out.println("The order will arrive in our shop in 2 workdays!");
                    else {
                        System.out.println("The order will be delivered in 3-5 workdays!");
                    }
                    break;
                }
                if(option == 1) {
                    for (; ; ) {
                        System.out.println("Select option");
                        System.out.println("1. View all products from the shop");
                        System.out.println("2. Add product to your order");
                        System.out.println("3. Back");
                        System.out.println("0. Cancel order");
                        int addoption = scanner.nextInt();
                        if (addoption == 0) {
                            return;
                        } else if (addoption == 1) {
                            System.out.println(shop.getProd());
                        } else if (addoption == 3) {
                            break;
                        } else if (addoption != 2) {
                            System.out.println("Invalid option");
                        } else {
                            System.out.println("Enter product name");
                            String name = scanner.next();
                            Product product = null;
                            for (Product p : shop.getProd()) {
                                if (p.getName().equalsIgnoreCase(name)) {
                                    product = p;
                                }
                            }
                            if (product == null) {
                                System.out.println("This product doesn't exist");
                            } else {
                                if(product.getTotal_nr()==0) {System.out.println("Out of Stock! ");}
                                else {
                                    System.out.println("We have " + product.getTotal_nr() + " items");
                                    System.out.println("Enter quantity");
                                    int quantity = scanner.nextInt();
                                    if (quantity > product.getTotal_nr()) System.out.println("Quantity too high!");
                                    else {
                                        order.addProduct(product, quantity);
                                        product.setTotal_nr(product.getTotal_nr() - quantity);

                                        ///Pentru a obtine ID ul produsului

                                        PreparedStatement ps3 = connection.prepareCall("select id_product from product where name = ?");
                                        ps3.setString(1, product.getName().trim());
                                        ResultSet resultSet3 = ps3.executeQuery();
                                        resultSet3.next();
                                        Integer idp = (Integer) resultSet3.getObject(1);
                                        System.out.println(idp);

                                        String query1 = "update product set total_nr = ? where id_product =?";
                                        PreparedStatement ps1 = connection.prepareStatement(query1);
                                        ps1.setString(1, String.valueOf(product.getTotal_nr()));
                                        ps1.setString(2, String.valueOf(idp));
                                        ps1.executeUpdate();


                                        String query3 = "insert into order_items(id_order, id_product, quantity) values (?, ?, ?)";
                                        PreparedStatement ps4 = connection.prepareStatement(query3);
                                        ps4.setString(1, String.valueOf(id_ord));
                                        ps4.setString(2, String.valueOf(idp));
                                        ps4.setString(3, String.valueOf(quantity));
                                        ps4.executeUpdate();
                                        System.out.println("Added in the cart");
                                    }
                                }
                            }
                        }
                    }
                }
            }
            user.addOrder(order);
            app.addOrder(order);
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}