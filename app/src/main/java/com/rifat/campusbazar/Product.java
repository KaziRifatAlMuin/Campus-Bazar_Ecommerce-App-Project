package com.rifat.campusbazar;

public class Product {
    private String name;
    private String price;
    private int imageResId;
    private String category;

    public Product(String name, String price, int imageResId, String category) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
        this.category = category;
    }

    // Getters
    public String getName() { return name; }
    public String getPrice() { return price; }
    public int getImageResId() { return imageResId; }
    public String getCategory() { return category; }
}
