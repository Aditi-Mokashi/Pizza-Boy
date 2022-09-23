package com.example.virtualpizzaboy;


public class AdminAdded {
    String name;
    String price;
    String desc;
    String purl;

    public AdminAdded(String purl,String name,String desc,String price){
        this.purl = purl;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }
    public String getName() {
        return name;
    }

    public String setName(String name) {
        this.name = name;
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String setDesc(String desc) {
        this.desc = desc;
        return desc;
    }

    public String getPrice() {
        return price;
    }

    public String setPrice(String price) {
        this.price = price;
        return price;
    }

    public String  getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
