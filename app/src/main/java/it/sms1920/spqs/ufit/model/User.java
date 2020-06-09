package it.sms1920.spqs.ufit.model;

import android.icu.util.LocaleData;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;

public class User {

    public enum Gender {

        MALE, FEMALE, NOT_SPECIFIED;

        @NonNull
        @Override
        public String toString() {
            return super.toString().substring(0, 1).toUpperCase()
                    + super.toString().substring(1).toLowerCase();
        }
    }

    public enum HeightUnit {CM, IN;

        @NonNull
        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    public enum WeightUnit {KG, LB;

        @NonNull
        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    private String linkImgProfile;
    private String name;
    private String surname;
    private Gender gender;
    private String dateBirth;
    private int bodyWeightKg;
    private double bodyWeightLbs;
    private HeightUnit heightUnit;
    private WeightUnit weightUnit;
    private int heightCm;
    private double heightIn;


    public User(String linkImgProfile, String name, String surname, Gender gender, String dateBirth,
                int bodyWeightKg, double bodyWeightLbs, HeightUnit heightUnit, WeightUnit weightUnit,
                int heightCm, double heightIn) {
        this.linkImgProfile = linkImgProfile;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.dateBirth = dateBirth;
        this.bodyWeightKg = bodyWeightKg;
        this.bodyWeightLbs = bodyWeightLbs;
        this.heightUnit = heightUnit;
        this.weightUnit = weightUnit;
        this.heightCm = heightCm;
        this.heightIn = heightIn;
    }


    //viene settato così perchè viene inizializzato all'interno del realtime database
    public User(){
        this.linkImgProfile = "";
        this.name = "";
        this.surname = "";
        this.gender = Gender.NOT_SPECIFIED;
        this.bodyWeightKg = 0;
        this.bodyWeightLbs = 0;
        this.heightUnit = HeightUnit.CM;
        this.weightUnit = WeightUnit.KG;
        this.heightCm = 0;
        this.heightIn = 0;
        this.dateBirth= "";
    }
    public HeightUnit getHeightUnit() {
        return heightUnit;
    }

    public void setHeightUnit(HeightUnit heightUnit) {
        this.heightUnit = heightUnit;
    }

    public WeightUnit getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(WeightUnit weightUnit) {
        this.weightUnit = weightUnit;
    }

    public int getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(int heightCm) {
        this.heightCm = heightCm;
    }

    public double getHeightIn() {
        return heightIn;
    }

    public void setHeightIn(double heightIn) {
        this.heightIn = heightIn;
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


    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public int getBodyWeightKg() {
        return bodyWeightKg;
    }

    public void setBodyWeightKg(int bodyWeightKg) {
        this.bodyWeightKg = bodyWeightKg;
    }

    public double getBodyWeightLbs() {
        return bodyWeightLbs;
    }

    public void setBodyWeightLbs(double bodyWeightLbs) {
        this.bodyWeightLbs = bodyWeightLbs;
    }


}

