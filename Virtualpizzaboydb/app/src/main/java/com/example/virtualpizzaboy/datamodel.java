package com.example.virtualpizzaboy;

public class datamodel{
    String name,desc,price,purl,addtocart,qty;
    public datamodel(){}

    public datamodel(String name, String desc, String price, String purl,String addtocart,String qty) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.purl = purl;
        this.addtocart = addtocart;
        this.qty = qty;
    }
    public datamodel(String name,String qty){
        this.name = name;
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
    public String getAddtocart(){ return addtocart; }
    public void setAddtocart(String addtocart){  this.addtocart = addtocart; }
    public String getQty(){ return qty; }
    public void setQty(String qty){  this.qty = qty; }

}
