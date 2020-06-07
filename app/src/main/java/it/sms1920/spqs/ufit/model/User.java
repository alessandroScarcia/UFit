package it.sms1920.spqs.ufit.model;

import java.util.Date;

public class User {
    enum Gender {MALE, FEMALE, NOT_SPECIFIED};
    enum HeightUnit{CM, IN};
    enum WeightUnit{KG, LB};

    private String linkImgProfile;
    private String name;
    private String surname;
    private Gender gender;
    private Date dateBirth;
    private int bodyWeight;
    private HeightUnit heightUnit;
    private WeightUnit weightUnit;
    private int height; // express in cm

    public User(String linkImgProfile, String name, String surname, Gender gender, Date dateBirth,
                int bodyWeight, HeightUnit heightUnit, WeightUnit weightUnit, int height) {
        this.linkImgProfile = linkImgProfile;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.dateBirth = dateBirth;
        this.bodyWeight = bodyWeight;
        this.heightUnit = heightUnit;
        this.weightUnit = weightUnit;
        this.height = height;
    }

    public User() {
        this.name = "";
        this.surname = "";
        this.bodyWeight = 0;
        this.height = 0;
    }

    public String getLinkImgProfile() {
        return linkImgProfile;
    }

    public void setLinkImgProfile(String linkImgProfile) {
        this.linkImgProfile = linkImgProfile;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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


    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    //quando crea il profilo carico i dati sul realTime databse creando un'instanza di Auth per ottenere l'id dell'utente
}

