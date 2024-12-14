package com.rifat.campusbazar;

public class Product {
    private String name;
    private String price;
    private int imageResId;
    private String category;
    private String imageUrl; // New field

    // Existing constructor for local resources
    public Product(String name, String price, int imageResId, String category) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
        this.category = category;
        this.imageUrl = null; // default
    }

    // New constructor for network images
    public Product(String name, String price, String imageUrl, String category) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.imageResId = 0; // no local image resource
    }

    // Getters
    public String getName() { return name; }
    public String getPrice() { return price; }
    public int getImageResId() { return imageResId; }
    public String getCategory() { return category; }
    public String getImageUrl() { return imageUrl; }
}
