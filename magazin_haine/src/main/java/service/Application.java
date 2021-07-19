package service;

import users.Admin;
import users.User;
import users.others.Order;

import java.util.ArrayList;
import java.util.List;

public class Application {
    private static Application instance = null;
    private List<User> users;
    private List<Admin> admins;
    private List<Order> orders;
    private Shop shop;

    public Application() {

        this.users = new ArrayList<User>();
        this.admins = new ArrayList<Admin>();
        this.orders = new ArrayList<Order>();
        this.shop = new Shop();
        Admin admin = new Admin("admin", "Admin Admin", "admin@admin.ro", "admin", "0000000000", "Admin Adress", "0000 0000 0000 0000");
        addAdmin(admin);
    }

    public static Application App() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }

    public static Application getInstance() {
        return instance;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Admin> getAdmins() {
        return admins;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public static void setInstance(Application instance) {
        Application.instance = instance;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void addAdmin(Admin admin) {
        this.admins.add(admin);
        this.users.add(admin);
    }
        public void addShop (Shop shop){
            this.shop=shop;
    }

    public void addOrder(Order order){
        this.orders.add(order);
    }

}
