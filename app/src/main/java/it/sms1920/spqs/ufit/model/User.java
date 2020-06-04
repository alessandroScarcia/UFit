package it.sms1920.spqs.ufit.model;

import java.util.Date;


public class User {

    private String name;
    private String surname;
    private String gender;
    private Date dateBirth;
    private int bodyWeight;
    private int height; // express in cm

    public User(String name, String surname, String gender, Date dateBirth, int bodyWeight, int height) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.dateBirth = dateBirth;
        this.bodyWeight = bodyWeight;
        this.height = height;
    }

    public User() {

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

    public String getGender() {
        return gender;
    }

    public void setSex(String gender) {
        this.gender = gender;
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

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    //quando crea il profilo carico i dati sul realTime databse creando un'instanza di Auth per ottenere l'id dell'utente
}

