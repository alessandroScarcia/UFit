package it.sms1920.spqs.ufit.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class Exercise implements Serializable {
    public static final String CHILD_NAME = "Exercise";

    @NonNull
    private String defLanguage;
    private String imageUrl;
    private String videoUrl;

    @NonNull
    public String getDefLanguage() {
        return defLanguage;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

}
