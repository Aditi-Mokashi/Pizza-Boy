package com.example.virtualpizzaboy;

public class User
{
    String name, phoneNumber, emailAddress, password, userType, id;

    public User() {}

    public User(String id, String email, String phone, String password, String userType)
    {
        this.id = id;
        this.emailAddress = email;
        this.phoneNumber = phone;
        this.password = password;
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String password) {
        this.userType = userType;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }
}