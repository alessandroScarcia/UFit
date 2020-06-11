package it.sms1920.spqs.ufit.model;

import androidx.annotation.NonNull;

public class User {
    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public enum Gender {

        MALE, FEMALE, NOT_SPECIFIED;

        @NonNull
        @Override
        public String toString() {
            return super.toString().substring(0, 1).toUpperCase()
                    + super.toString().substring(1).toLowerCase();
        }
    }

    private String name;
    private String surname;
    private Gender gender;
    private String dateBirth;
    private String urlImage;
    private int weight;
    private int height;

    public User(String name, String surname, Gender gender, String urlImageProfile, String dateBirth, int weight, int height) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.urlImage = urlImageProfile;
        this.dateBirth = dateBirth;
        this.weight = weight;
        this.height = height;
    }

    public User() {
        this.urlImage = "";
        this.name = "";
        this.surname = "";
        this.gender = Gender.NOT_SPECIFIED;
        this.dateBirth = "";
        this.weight = 0;
        this.height = 0;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }





}

