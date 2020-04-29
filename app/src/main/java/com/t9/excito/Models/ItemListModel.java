package com.t9.excito.Models;

import com.google.firebase.database.Exclude;

public class ItemListModel {
    private String itemName,documentId;
    private String itemPrice;

    public ItemListModel() {
        //public no-arg constructor needed
    }

    public ItemListModel(String itemName, String itemPrice) {
    this.itemName=itemName;
    this.itemPrice=itemPrice;

    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }
}
