package it.sms1920.spqs.ufit.launcher.workoutplan.create;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import it.sms1920.spqs.ufit.model.firebase.database.Exercise;
import it.sms1920.spqs.ufit.model.firebase.database.ExerciseSetItem;
import it.sms1920.spqs.ufit.model.search.ExerciseDetailed;
import it.sms1920.spqs.ufit.model.search.SearchExercise;
import it.sms1920.spqs.ufit.model.search.iSearchClient;

public class WorkoutExerciseListAdapterPresenter implements iWorkoutExerciseListAdapter.Presenter, iSearchClient {

    SearchExercise mSearch;
    iWorkoutExerciseListAdapter.View view;


    List<Esercizio> esercizios;


    public WorkoutExerciseListAdapterPresenter(iWorkoutExerciseListAdapter.View view) {
        this.view = view;

        esercizios = new ArrayList<>();

        mSearch = new SearchExercise(this);

        view.callNotifyDataSetChanged();

    }


    @Override
    public void notifyResultListReady() {
        esercizios.clear();
        for (ExerciseDetailed exercise : mSearch.getExerciseResultList()) {
            esercizios.add(new Esercizio(exercise.getExerciseId(), exercise.getName(), new ArrayList<ExerciseSetItem>()));
        }

        view.callNotifyDataSetChanged();
    }


    @Override
    public void onBindExerciseItemViewAtPosition(iWorkoutExerciseListAdapter.View.Item holder, int position) {
        Esercizio temp = esercizios.get(position);
        holder.setName(temp.getNome());
        holder.setId(temp.getId());
        for (ExerciseSetItem serie : temp.getLstSet()) {
            holder.addSerie(serie.getReps(), serie.getLoad());
        }
    }

    @Override
    public int getExerciseCount() {
        return esercizios.size();
    }

    @Override
    public void removeItemAt(int position) {
        esercizios.remove(position);
        view.callNotifyItemRemoved(position);
        view.callNotifyItemRangeChanged(position, esercizios.size());
        view.callNotifyDataSetChanged();
    }

    @Override
    public void onNewExercisesAdded(ArrayList<String> exercisesId) {
        mSearch.getExerciseByIdList(exercisesId);
    }

    @Override
    public ArrayList<String> onExercisesIdRequested() {
        ArrayList<String> list = new ArrayList<>();

        for (Esercizio exercise : esercizios) {
            list.add(exercise.getId());
        }
        return list;
    }


    static class Esercizio {
        String id;
        String nome;
        ArrayList<ExerciseSetItem> lstSet;

        public Esercizio(String id, String nome, ArrayList<ExerciseSetItem> lstSet) {
            this.id = id;
            this.nome = nome;
            this.lstSet = lstSet;
        }

        public ArrayList<ExerciseSetItem> getLstSet() {
            return lstSet;
        }

        public String getNome() {
            return nome;
        }

        public String getId() {
            return id;
        }
    }
}
