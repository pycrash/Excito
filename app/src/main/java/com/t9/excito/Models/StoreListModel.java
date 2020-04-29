package com.t9.excito.Models;

import com.google.firebase.database.Exclude;

public class StoreListModel {
    private String storeName,documentId;
    private String area,type;
    private String phoneNumber;
    private String email;

    public StoreListModel() {
        //public no-arg constructor needed
    }

    public StoreListModel(String storeName, String phoneNumber, String type, String area, String email) {
        this.storeName=storeName;
        this.phoneNumber=phoneNumber;
        this.type=type;
        this.area=area;
        this.email=email;
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }
    @Exclude
    public String getEmail() {
        return email;
    }
    @Exclude
    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getArea() {
        return area;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getType() {
        return type;
    }
}
