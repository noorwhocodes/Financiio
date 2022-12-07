package com.ass2.project;

public class TransactionRowModel {
    String iconName, amount;


    public TransactionRowModel(String iconName, String amount) {
        this.iconName = iconName;
        this.amount = amount;
    }
    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
