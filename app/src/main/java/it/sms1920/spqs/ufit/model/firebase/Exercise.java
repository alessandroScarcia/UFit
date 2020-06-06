package it.sms1920.spqs.ufit.model.firebase;

import java.io.Serializable;
import java.util.List;

public class Exercise implements Serializable {
    public int exerciseId;
    public String defLanguage;
    public String image;
    public String videoUrl;
    public List<Integer> muscleList;

    public Exercise() {
    }
}
