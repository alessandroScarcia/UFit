package it.sms1920.spqs.ufit.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "description")
    private String description; // English exercise description

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "link")
    private String link;

    @ColumnInfo(name = "muscles")
    private int muscles; // English muscles list

    @ColumnInfo(name = "name")
    private String name; // English name


    public Exercise(){

    }

    public Exercise(String name, String description, String image, String link, int muscles) {
        this.description = description;
        this.image = image;
        this.link = link;
        this.muscles = muscles;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getMuscles() {
        return muscles;
    }

    public void setMuscles(int muscles) {
        this.muscles = muscles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
