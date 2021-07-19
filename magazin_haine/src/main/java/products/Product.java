package products;

import java.util.UUID;

public class Product {
    protected final String id = UUID.randomUUID().toString();
    protected String name;
    protected String material;
    protected String color;
    protected String size;
    protected double price;
    protected int total_nr;
    public Product() {
    }

    public Product(String name, String material, String color, String size, double price, int total_nr) {
        this.name = name;
        this.material = material;
        this.color = color;
        this.size = size;
        this.price = price;
        this.total_nr = total_nr;
    }

    @Override
    public String toString() {
        return "Name= "+name + ' ' +
                "Material= "+ material + ' '+
                "Color= " + color + ' ' +
                "Size= " + size + ' '+
                "Total number of items=" + total_nr +
                "Price= " + price + ' ' +
                "Total number= " + total_nr;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTotal_nr() {
        return total_nr;
    }

    public void setTotal_nr(int total_nr) {
        this.total_nr = total_nr;
    }
}
