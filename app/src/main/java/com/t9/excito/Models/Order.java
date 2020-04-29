package com.t9.excito.Models;

public class Order {
    private String productName;
    private String storeName;
    private String quantity;
    private String price;

    public Order() {
    }

    public Order(String storeName, String productName, String quantity, String price){
        storeName=this.storeName;
        productName=this.productName;
        quantity=this.quantity;
        price=this.price;
    }

    public String getProductName() {
        return productName;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}