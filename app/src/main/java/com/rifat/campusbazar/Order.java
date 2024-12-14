package com.rifat.campusbazar;

import java.util.List;
import java.util.Map;

public class Order {
    private String userId;
    private List<Map<String, Object>> items;
    private int totalPrice;
    private String timestamp;

    public Order() {
        // Empty constructor required for Firestore
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Map<String, Object>> getItems() {
        return items;
    }

    public void setItems(List<Map<String, Object>> items) {
        this.items = items;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
