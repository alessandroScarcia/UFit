package it.sms1920.spqs.ufit.model;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Class that represent an instance of an Exercise with translated information.
 */
public class ExerciseDetailed implements Comparable<ExerciseDetailed> {
    private String exerciseId;
    private String name;
    private String description;
    private String imageUrl;
    private String videoUrl;
    private List<String> muscleList;

    public ExerciseDetailed() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public List<String> getMuscleList() {
        return muscleList;
    }

    public void setMuscleList(List<String> muscleList) {
        this.muscleList = muscleList;
    }

    @Override
    public int compareTo(ExerciseDetailed o) {
        if (this.exerciseId.equals(o.exerciseId)) {
            return 0;
        } else {
            return this.name.compareTo(o.name);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExerciseDetailed that = (ExerciseDetailed) o;

        return exerciseId.equals(that.exerciseId);
    }

    @Override
    public int hashCode() {
        int result = exerciseId.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (videoUrl != null ? videoUrl.hashCode() : 0);
        result = 31 * result + (muscleList != null ? muscleList.hashCode() : 0);
        return result;
    }

    @Override
    @NonNull
    public String toString() {
        return "ExerciseResult{" +
                "exerciseId='" + exerciseId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", muscleList=" + muscleList +
                '}';
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
