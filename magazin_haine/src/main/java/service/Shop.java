package service;

import products.Product;

import java.util.ArrayList;

public class Shop {
    private String name;
    private String address;
    private ArrayList<Product> prod;

    public Shop()
    {
        prod = new ArrayList<Product>();
    }

    public Shop(String name, String address) {
        this.name = name;
        this.address = address;
        prod = new ArrayList<Product>();
    }

    @Override
    public String toString()
    {
        return "service.Shop{" +' ' +
                "name= " + name + " ," +
                 "adress="  + address + " ,"+
                 '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<Product> getProd() {
        return prod;
    }

    public void setProd(ArrayList<Product> prod) {
        this.prod = prod;
    }

    public void addProduct(Product product)
    {
        this.prod.add(product);
    }
}
