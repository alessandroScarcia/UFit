package it.sms1920.spqs.ufit.model;

import java.io.Serializable;
import java.util.List;

public class Exercise implements Serializable {
    public static final String CHILD_NAME = "Exercise";

    private String defLanguage;
    private String image;
    private String videoUrl;
    private List<Integer> muscleList;

    public String getDefLanguage() {
        return defLanguage;
    }

    public String getImage() {
        return image;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public List<Integer> getMuscleList() {
        return muscleList;
    }

}
