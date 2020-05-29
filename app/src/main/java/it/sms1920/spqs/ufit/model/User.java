package it.sms1920.spqs.ufit.model;

public class User {
    private String name;
    private String surname;
    private String sex;
    private int bodyWeight;
    private int height; // express in cm
    private String email;
    private String password;

    public User(String name, String surname, String sex, int bodyWeight, int height, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.bodyWeight = bodyWeight;
        this.height = height;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getBodyWeight() {
        return bodyWeight;
    }

    public void setBodyWeight(int bodyWeight) {
        this.bodyWeight = bodyWeight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
