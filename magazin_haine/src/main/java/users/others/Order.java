package users.others;

import products.Product;
import users.User;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;


public class Order {
    private final String id = UUID.randomUUID().toString();
    private String status = "created";//created,delivered,delivering,cancelled
    private User user;
    private LocalDateTime createDate;
    private ArrayList<Pair<Product,Integer>> products;//product, quantity

    public Order(String status, User user, LocalDateTime createDate, ArrayList<Pair<Product, Integer>> products) {
        this.status = status;
        this.user = user;
        this.createDate = createDate;
        this.products = products;
    }

    public Order(User user)
   {
       this.user = user;
       this.products = new ArrayList<Pair<Product, Integer>>();
       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd 'at' HH:mm:ss z");
       this.createDate = LocalDateTime.now();

   }

    public double getTotalPrice()
    {
        double total = 0;
        for(Pair<Product, Integer>p : products)
        {
            total = total + p.getSecond()*p.getFirst().getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        String prod = new String();
        prod += "[";
        for (Pair<Product, Integer> p : products) {
            prod += "(" + p.getFirst().getName() + " " + p.getSecond() + "),";
        }
        prod += "]";

        return "users.others.Order{" +
                "user = " + user.getFullName() +

                "status=" + status +
                ", products = " + prod +
                ", createdate = " + createDate +
                ", price= " + getTotalPrice() +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public ArrayList<Pair<Product, Integer>> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Pair<Product, Integer>> products) {
        this.products = products;
    }

    public void addProduct(Product product, int quantity)
    {
        Integer q = quantity;
        Pair<Product, Integer>p = new Pair<>(product, q);
        products.add(p);
        Collections.sort(products);
    }
}
