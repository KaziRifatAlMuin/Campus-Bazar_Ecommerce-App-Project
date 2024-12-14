package com.rifat.campusbazar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class CartRepository {
    private static CartRepository instance;

    private final MutableLiveData<List<Product>> cartItemsLiveData = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Integer> totalPriceLiveData = new MutableLiveData<>(0);

    private CartRepository() {}

    public static synchronized CartRepository getInstance() {
        if (instance == null) {
            instance = new CartRepository();
        }
        return instance;
    }

    // Getter for LiveData of cart items
    public LiveData<List<Product>> getCartItemsLiveData() {
        return cartItemsLiveData;
    }

    // Getter for LiveData of total price
    public LiveData<Integer> getTotalPriceLiveData() {
        return totalPriceLiveData;
    }

    // Add item to the cart
    public void addItemToCart(Product product) {
        List<Product> currentCart = new ArrayList<>(cartItemsLiveData.getValue());
        currentCart.add(product);
        cartItemsLiveData.setValue(currentCart);
        recalculateTotalPrice(currentCart);
    }

    // Remove item from the cart
    public void removeItemFromCart(Product product) {
        List<Product> currentCart = new ArrayList<>(cartItemsLiveData.getValue());
        currentCart.remove(product);
        cartItemsLiveData.setValue(currentCart);
        recalculateTotalPrice(currentCart);
    }

    // Recalculate total price of items in the cart
    private void recalculateTotalPrice(List<Product> currentCart) {
        int total = 0;
        for (Product product : currentCart) {
            // Parse price to extract numeric value (removing "৳" and other characters)
            String numericPrice = product.getPrice().replaceAll("[^0-9]", "");
            if (!numericPrice.isEmpty()) {
                total += Integer.parseInt(numericPrice);
            }
        }
        totalPriceLiveData.setValue(total);
    }
}
