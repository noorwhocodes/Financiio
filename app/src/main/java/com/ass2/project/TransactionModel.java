package com.ass2.project;

import android.graphics.Bitmap;

public class TransactionModel {
    String category, amount, description, time, image;
    static int itemID=0;

    public TransactionModel(String category, String amount, String description) {
        this.category = category;
        this.amount = amount;
        this.description = description;
        itemID+=1;
    }

    public TransactionModel(String category, String amount, String description, String time) {
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.time = time;
        itemID+=1;
    }

    public TransactionModel(String category, String amount, String description, String time, String image) {
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.time = time;
        this.image = image;
        itemID+=1;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() { return  image; }

    public void setImage(String image) {
        this.image = image;
    }
}
