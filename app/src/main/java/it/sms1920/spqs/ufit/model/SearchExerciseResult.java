package it.sms1920.spqs.ufit.model;

public class SearchExerciseResult implements Comparable<SearchExerciseResult> {
    private int exerciseId;
    private String name;
    private String imageUrl;

    public SearchExerciseResult(int exerciseId, String name, String imageUrl) {
        this.exerciseId = exerciseId;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchExerciseResult that = (SearchExerciseResult) o;

        return exerciseId == that.exerciseId;
    }

    @Override
    public int hashCode() {
        return exerciseId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
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

    @Override
    public int compareTo(SearchExerciseResult o) {
        if (this.exerciseId == o.exerciseId) {
            return 0;
        } else {
            return this.name.compareTo(o.name);
        }
    }
}
