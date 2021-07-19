package service;

import users.Admin;
import users.User;


import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.util.Scanner;
import java.sql.*;

public class LogInService {
    private Scanner scanner = new Scanner(System.in);

    public void Main(Application app) throws ParseException {

        int option;

        for (; ; ) {
//            System.out.print("\033[H\033[2J");
//            System.out.flush();
            System.out.println("Select an option");
            System.out.println("1. Login");
            System.out.println("2. Sign up");
            System.out.println("0. Exit App");
            option = scanner.nextInt();

            switch (option) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    for (; ; ) {
                        System.out.println("Username");
                        String username = scanner.next();
                        System.out.println("Password");
                        String password = scanner.next();
                        User user = login(username, password, app);
                        if (user == null) {
                            System.out.println("Invalid credentials");
                            System.out.println("1. Try again");
                            System.out.println("2. Back");
                            int option2 = scanner.nextInt();
                            if (option2 == 2) {
                                break;
                            } else if (option2 != 1) {
                                System.out.println("Invalid optin - going back...");
                                break;
                            }

                        } else if (user instanceof Admin) {
//                            System.out.println("Logged in as Admin");
                            //admin service
                            AdminService adminService = new AdminService();
                            adminService.Main(app, (Admin) user);
                            break;
                        } else {
//                            System.out.println("Logged in as Normal User");
                            UserService userService = new UserService();
                            userService.Main(app, user);
                            break;
                        }
                    }
                    break;
                case 2:
                    int option2;
                    System.out.println("1. Sign up as a normal user");
                    System.out.println("0. Back");
                    option2 = scanner.nextInt();
                    if (option2 == 1) {
                        signup(option2, app);
                    }
                    else{
                        break;
                    }

            }
        }
    }

    private User login(String username, String password, Application app) {
        for (User user : app.getUsers()) {
//            System.out.println(user.getUsername()+" "+username + " - "+ user.getPassword() + " " + password);
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private void signup(int type, Application app) {

        try {
            String jdbcURL = "jdbc:mysql://localhost:3306/new_schema";
            String username1 = "root";
            String password1 = "flamingoSQLpa55";
            Connection connection = DriverManager.getConnection(jdbcURL, username1, password1);
            System.out.println("Username");
            String username = scanner.next();
            System.out.println("Password");
            String password = scanner.next();
            System.out.println("Full Name");
            String fullname = scanner.next();
            String temp = scanner.next();
            fullname = fullname + " " + temp;
            System.out.println("Email");
            String email = scanner.next();
            System.out.println("Phone number");
            String phonenumber = scanner.next();
            System.out.println("Address");
            String address = scanner.next();
            System.out.println("Card Number");
            String cardnumber = scanner.next();


            User user = new User(username, fullname, email, password, phonenumber, address, cardnumber);
            app.addUser(user);

            String query = "insert into users(username,fullname,email,password,phone_number,address,card_number,type_user) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getPhoneNumber());
            ps.setString(6, user.getAddress());
            ps.setString(7, user.getCardNumber());
            ps.setString(8, "user");
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}