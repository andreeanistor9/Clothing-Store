import com.mysql.cj.exceptions.DataReadException;
import products.Clothes;
import products.Product;
import service.Application;
import service.LogInService;
import service.Shop;
import users.Admin;
import users.User;

import users.others.Order;
import users.others.Pair;

import java.sql.*;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, ParseException, NullPointerException {


        String sql1 = "SELECT * FROM users";
        String jdbcURL = "jdbc:mysql://localhost:3306/new_schema";
        String username = "root";
        String password = "flamingoSQLpa55";
        Connection connection = DriverManager.getConnection(jdbcURL, username, password);
        Statement stmt = connection.createStatement();
        ResultSet rs1 = stmt.executeQuery(sql1);

        Application application = new Application();
        User user;
        Admin admin;

        while (rs1.next()) {
            String usernam = rs1.getObject(2).toString();
            String fullname = rs1.getObject(3).toString();
            String email = rs1.getObject(4).toString();
            String pass = rs1.getObject(5).toString();
            String pn = rs1.getObject(6).toString();
            String adr = rs1.getObject(7).toString();
            String card = rs1.getObject(8).toString();
            if (rs1.getObject(9).toString().equals("admin")) {
                admin = new Admin(usernam, fullname, email, pass, pn, adr, card);
                application.addAdmin(admin);
            } else if (rs1.getObject(9).toString().equals("user")) {
                user = new User(usernam, fullname, email, pass, pn, adr, card);
                application.addUser(user);
            }
        }


        Shop shop = new Shop("Levi's", "Aurel Vlaicu");
        application.addShop(shop);
        String sql2 = "SELECT * FROM product";
        ResultSet rs2 = stmt.executeQuery(sql2);
        Product product;
        while (rs2.next()) {
            String name = rs2.getObject(2).toString();
            String material = rs2.getObject(3).toString();
            String color = rs2.getObject(4).toString();
            String size = rs2.getObject(5).toString();
            double price = (double) rs2.getObject(6);
            String type = rs2.getObject(7).toString();
            int total_nr = (int) rs2.getObject(8);
            product = new Product(name, material, color, size, price, total_nr);
            shop.addProduct(product);
        }

        String sql_ = "SELECT * from ORDERS";
        ResultSet rs_ = stmt.executeQuery(sql_);
        while(rs_.next())
        {
            Integer id_order = (Integer)rs_.getObject(1);
            String status = rs_.getObject(2).toString();
            Integer id = (Integer) rs_.getObject(3);
            Timestamp date = rs_.getTimestamp(4);

            PreparedStatement ps3 = connection.prepareCall("select email from users where id_user = ?");
            ps3.setString(1, String.valueOf(id));
            ResultSet resultSet3 = ps3.executeQuery();
            resultSet3.next();
            String email = resultSet3.getObject(1).toString();
            System.out.println(email);

            for (User user1 : application.getUsers())
            {
                if(user1.getEmail().equals(email))
                {
                    Order order = new Order(user1);
                    user1.addOrder(order);
                    application.addOrder(order);
                    order.setStatus(status);
                    order.setCreateDate(date.toInstant().atZone(ZoneId.systemDefault() ).toLocalDateTime());

                    PreparedStatement ps4 = connection.prepareStatement("select id_product from order_items where id_order = ?");
                    ps4.setString(1,String.valueOf(id_order));
                    ResultSet res = ps4.executeQuery();
                    res.next();
                    Integer id_prod = (Integer) res.getObject(1);


                    PreparedStatement ps5 = connection.prepareStatement("SELECT name from product where id_product = ?");
                    ps5.setString(1,String.valueOf(id_prod));
                    ResultSet res1 = ps5.executeQuery();
                    //ArrayList<Product> produc = new ArrayList<>();

                    PreparedStatement ps6 = connection.prepareStatement("SELECT quantity from order_items where id_order = ?");
                    ps6.setString(1,String.valueOf(id_order));
                    ResultSet res2 = ps6.executeQuery();
                    ArrayList<Integer> o= new ArrayList<>();

                    ArrayList<Pair<Product,Integer>> pair= new ArrayList<>();
                    while(res1.next() && res2.next()) {
                        String name = res1.getObject(1).toString();
                        Integer quantity = (Integer) res2.getObject(1);
                        for(Product prod:shop.getProd())
                        {

                            if(prod.getName().equals(name)){
                                Pair p = new Pair(prod, quantity);
                                pair.add(p);
                            }
                        }
                    }
                    order.setProducts(pair);


                }
            }
        }


        LogInService logInService = new LogInService();
        logInService.Main(application);

    }
}