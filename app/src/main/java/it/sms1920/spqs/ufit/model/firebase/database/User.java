package it.sms1920.spqs.ufit.model.firebase.database;

import androidx.annotation.NonNull;

import it.sms1920.spqs.ufit.model.util.StringUtils;

public class User {

    public static final String CHILD_NAME = "User";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_SURNAME = "surname";
    public static final String FIELD_GENDER = "gender";
    public static final String FIELD_IMAGE_URL = "imageUrl";
    public static final String FIELD_BIRTH_DATE = "birthDate";
    public static final String FIELD_ROLE = "role";

    public static final String PATH_STORAGE_PIC = "PicsProfile/";
    public static final String JPG = ".jpg";

    private String name;
    private String surname;
    private Integer gender;
    private String birthDate;
    private String imageUrl;
    private Boolean role;

    public User() {
    }

    public User(String name, String surname, Integer gender, String urlImageProfile, String birthDate, Boolean role) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.imageUrl = urlImageProfile;
        this.birthDate = birthDate;
        this.role = role;
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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setDateBirth(String birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getRole() { return role; }

    public void setRole(Boolean role) { this.role = role; }
}

