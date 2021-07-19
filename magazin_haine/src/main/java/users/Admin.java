package users;

import users.others.Order;

import java.util.List;

public class Admin extends User{

    public Admin(String username, String fullName, String email, String password, String phoneNumber, String address, String cardNumber) {
        super(username, fullName, email, password, phoneNumber, address, cardNumber);
    }

    @Override
    public String toString() {
        return "users.Admin{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", fullname='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phonenumber='" + phoneNumber + '\'' +
                ", adress='" + address + '\'' +
                ", orders=" + orders +
                '}';
    }
}
