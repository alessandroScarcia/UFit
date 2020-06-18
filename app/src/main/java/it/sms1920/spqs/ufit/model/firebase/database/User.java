package it.sms1920.spqs.ufit.model.firebase.database;

import androidx.annotation.NonNull;

public class User {

    public static String NAME_FIELD = "name";
    public static String SURNAME_FIELD = "surname";
    public static String GENDER_FIELD = "gender";
    public static String IMG_URL_FIELD = "urlImage";
    public static String BIRTH_DATE_FIELD = "birthDate";
    public static String HEIGHT_FIELD = "height";
    public static String WEIGHT_FIELD = "weight";

    public static String PATH_STORAGE_PIC = "PicsProfile/";
    public static String JPG = ".jpg";


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
    private String birthDate;
    private String urlImage;
    private int weight;
    private int height;

    public User(String name, String surname, Gender gender, String urlImageProfile, String birthDate, int weight, int height) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.urlImage = urlImageProfile;
        this.birthDate = birthDate;
        this.weight = weight;
        this.height = height;
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

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setDateBirth(String dateBirth) {
        this.birthDate = birthDate;
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

