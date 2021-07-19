package users;

import users.others.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    protected final String id = UUID.randomUUID().toString();
    protected String username;
    protected String fullName;
    protected String email;
    protected String password;
    protected String phoneNumber;
    protected String address;
    protected String cardNumber;
    protected List<Order> orders;

    public User(){
        this.orders = new ArrayList<>();
    }

    public User(String username, String fullName, String email, String password, String phoneNumber, String address, String cardNumber) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.cardNumber = cardNumber;
        orders = new ArrayList<Order>();
    }

    @Override
    public String toString() {
        return "users.User{" +
                "username='" + username + '\'' +
                ", fullname='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phonenumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order){
        System.out.println(this.orders);
        System.out.println(order);
        this.orders.add(order);
    }
}
