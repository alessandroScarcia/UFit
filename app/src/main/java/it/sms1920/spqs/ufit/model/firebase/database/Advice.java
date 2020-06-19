package it.sms1920.spqs.ufit.model.firebase.database;

public class Advice {

    public static String TITLE_FIELD = "title";
    public static String DESCRIPTION_FIELD = "description";
    public static String AUTHOR_FIELD = "author";
    public static String ID_FIELD = "adviceId";

    private String title;
    private String description;
    private String author;
    private String adviceId;


    public Advice() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAdviceId() {
        return adviceId;
    }

    public void setAdviceId(String adviceId) {
        this.adviceId = adviceId;
    }
}
