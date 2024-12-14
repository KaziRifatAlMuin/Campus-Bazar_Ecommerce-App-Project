package com.rifat.campusbazar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MyCartViewModel extends ViewModel {
    private final CartRepository cartRepo;

    public MyCartViewModel() {
        cartRepo = CartRepository.getInstance();
    }

    // Get LiveData for cart items
    public LiveData<List<Product>> getCartItems() {
        return cartRepo.getCartItemsLiveData();
    }

    // Get LiveData for total price
    public LiveData<Integer> getTotalPrice() {
        return cartRepo.getTotalPriceLiveData();
    }

    // Remove an item from the cart
    public void removeItem(Product product) {
        cartRepo.removeItemFromCart(product);
    }
}
