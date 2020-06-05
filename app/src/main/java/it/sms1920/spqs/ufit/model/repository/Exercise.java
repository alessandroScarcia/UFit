package it.sms1920.spqs.ufit.model.repository;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Exercise implements Serializable {

    @PrimaryKey
    private long exerciseId;
    private String image;
    private String videoUrl;
    @NonNull
    private String defLanguage;

    public Exercise() {
    }

    @Ignore
    public Exercise(String name, String image, String videoUrl) {
        this.image = image;
        this.videoUrl = videoUrl;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDefLanguage() {
        return defLanguage;
    }

    public void setDefLanguage(String defLanguage) {
        this.defLanguage = defLanguage;
    }
}
