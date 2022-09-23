package com.example.virtualpizzaboy;

public class datamodel2{
    String name,crust,price,qty,size;
    public datamodel2(){}

    public datamodel2(String name ,String crust, String price, String qty) {
        this.name = name;
        this.crust = crust;
        this.price = price;
        this.qty = qty;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCrust() {
        return crust;
    }

    public void setCrust(String desc) {
        this.crust = crust;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

}
