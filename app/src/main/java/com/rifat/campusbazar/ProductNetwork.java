package com.rifat.campusbazar;

public class ProductNetwork {
    private String name;
    private String price;
    private String imageUrl;
    private String category;

    public ProductNetwork(String name, String price, String imageUrl, String category) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public String getName() { return name; }
    public String getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public String getCategory() { return category; }
}
