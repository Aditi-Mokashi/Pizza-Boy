package com.example.virtualpizzaboy;

public class datamodel3{
    String name,qty;
    public datamodel3(){}

    public datamodel3(String name ,String qty) {
        this.name = name;
        this.qty = qty;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
