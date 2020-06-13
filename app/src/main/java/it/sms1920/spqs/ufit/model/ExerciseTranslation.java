package it.sms1920.spqs.ufit.model;

public class ExerciseTranslation {
    public static final String CHILD_NAME = "ExerciseTranslation";

    private String codLanguage;
    private String name;
    private String description;
    private int exerciseId;

    public String getName() {
        return name;
    }

    public String getCodLanguage() {
        return codLanguage;
    }

    public String getDescription() {
        return description;
    }

    public int getExerciseId() {
        return exerciseId;
    }
}
