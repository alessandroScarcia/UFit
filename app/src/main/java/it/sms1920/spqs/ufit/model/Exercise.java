package it.sms1920.spqs.ufit.model;


import java.util.ArrayList;

public class Exercise {

    private String description; // English exercise description
    private String descrizione; // Italian exercise description
    private String image;
    private String link;
    private ArrayList<String> muscles; // English muscles list
    private ArrayList<String> muscoli; // Italian muscles list
    private String name; // English name
    private String nome; // Italian name


    public Exercise(){

    }

    public Exercise(String description, String descrizione, String image, String link, ArrayList<String> muscles, ArrayList<String> muscoli, String name, String nome) {
        this.description = description;
        this.descrizione = descrizione;
        this.image = image;
        this.link = link;
        this.muscles = muscles;
        this.muscoli = muscoli;
        this.name = name;
        this.nome = nome;
    }

    public String getDescription() {
        return description;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getImage() {
        return image;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<String> getMuscoli() {
        return muscoli;
    }

    public ArrayList<String> getMuscles() {
        return muscles;
    }


    @Override
    public String toString() {
        return "Exercise{" +
                "description='" + description + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", image='" + image + '\'' +
                ", link='" + link + '\'' +
                ", muscles=" + muscles +
                ", muscoli=" + muscoli +
                ", name='" + name + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }
}
