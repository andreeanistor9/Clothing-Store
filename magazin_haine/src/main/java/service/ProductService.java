package service;

import products.*;

import javax.management.StringValueExp;
import java.sql.*;
import java.util.Scanner;

public class ProductService {
    Scanner scanner = new Scanner(System.in);

    public Product createProduct() {
        try {
            String jdbcURL = "jdbc:mysql://localhost:3306/new_schema";
            String username = "root";
            String password = "flamingoSQLpa55";
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Enter Product Information");
            System.out.println("Enter Product Name");
            String name = scanner.next();
            System.out.println("Enter Product Material");
            String material = scanner.next();
            System.out.println("Enter Product Color");
            String color = scanner.next();
            System.out.println("Enter Product Size");
            String size = scanner.next();
            System.out.println("Enter Product Price");
            double price = scanner.nextDouble();
            System.out.println("Enter Total Number of Items");
            int total_nr = scanner.nextInt();
            System.out.println("Product type(bottom, top, shoes):");
            String type = scanner.next();

            String query = "insert into product(name,material,color,size,price,type, total_nr) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            Product p = new Product();
            if (type.equals("bottom")) {
                p = new Bottom(name, material, color, size, price, total_nr);
            }
            if (type.equals("top")) {
                p = new Top(name, material, color, size, price, total_nr);
            }
            if (type.equals("shoes")) {
                p = new Shoes(name, material, color, size, price, total_nr);
            }
            ps.setString(1, p.getName());
            ps.setString(2, p.getMaterial());
            ps.setString(3, p.getColor());
            ps.setString(4, p.getSize());
            ps.setString(5, String.valueOf(p.getPrice()));
            ps.setString(6, type);
            ps.setString(7, String.valueOf(p.getTotal_nr()));
            ps.executeUpdate();
            return p;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void removeProduct(Shop shop, String productName) {
        try {

            String jdbcURL = "jdbc:mysql://localhost:3306/new_schema";
            String username = "root";
            String password = "flamingoSQLpa55";
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            Product p1;
            for (Product p : shop.getProd()) {
                if (p.getName().equalsIgnoreCase(productName)) {
                    p1 = p;
                    shop.getProd().remove(p1);

                    System.out.println("Product deleted!");

                    String query = "delete from product where name = ?";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setString(1, p1.getName());
                    ps.executeUpdate();
                    return;
                }
            }
            System.out.println("Product not found!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void updateProduct(Shop shop, String productName) {


        try {

            String jdbcURL = "jdbc:mysql://localhost:3306/new_schema";
            String username = "root";
            String password = "flamingoSQLpa55";
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            Product p1 = new Product();
            for (Product p : shop.getProd()) {
                if (p.getName().equalsIgnoreCase(productName)) {
                    p1 = p;
                    System.out.println(p1.getTotal_nr());
                    for (; ; ) {
                        System.out.println(" 1.Material");
                        System.out.println(" 2.Color");
                        System.out.println(" 3.Price");
                        System.out.println(" 4.Size");
                        System.out.println((" 5. Total Number of Items"));
                        System.out.println(" 0.Back");
                        int op = scanner.nextInt();
                        if (op == 1) {
                            String material = scanner.next();
                            p1.setMaterial(material);
                        } else if (op == 2) {
                            String color = scanner.next();
                            p1.setColor(color);
                        } else if (op == 3) {
                            double price = scanner.nextDouble();
                            p1.setPrice(price);
                        } else if (op == 4) {
                            String size = scanner.next();
                            p1.setSize(size);
                        } else if (op == 5) {
                            int total_nr = scanner.nextInt();
                            p1.setTotal_nr(total_nr);

                        } else if (op != 0) {
                            System.out.println("Invalid option");
                        } else {
                            break;
                        }
                    }
                    System.out.println(p1.getSize());
                    PreparedStatement ps3 = connection.prepareCall("select id_product from product where name = ?");
                    ps3.setString(1, productName.trim());
                    ResultSet resultSet3 = ps3.executeQuery();
                    resultSet3.next();
                    Integer idp = (Integer) resultSet3.getObject(1);
                    System.out.println(idp);

                    String query = "update product set material = ?, color = ?, size = ?, price = ?, total_nr = ? where id_product =?";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setString(1, p1.getMaterial());
                    ps.setString(2, p1.getColor());
                    ps.setString(3, p1.getSize());
                    ps.setString(4, String.valueOf(p1.getPrice()));
                    ps.setString(5, String.valueOf(p1.getTotal_nr()));
                    ps.setString(6, String.valueOf(idp));
                    ps.executeUpdate();
                    return;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("Product not found");
    }
}