package it.sms1920.spqs.ufit.presenter;

import java.util.ArrayList;
import java.util.List;

import it.sms1920.spqs.ufit.contract.iWorkoutExerciseListAdapter;
import it.sms1920.spqs.ufit.model.ExerciseSetItem;

public class WorkoutExerciseListAdapterPresenter implements iWorkoutExerciseListAdapter.Presenter {


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

    iWorkoutExerciseListAdapter.View view;


    List<Esercizio> esercizios;


    public WorkoutExerciseListAdapterPresenter(iWorkoutExerciseListAdapter.View view) {
        this.view = view;

        esercizios = new ArrayList<>();

        view.callNotifyDataSetChanged();

    }

    @Override
    public void onBindExerciseItemViewAtPosition(iWorkoutExerciseListAdapter.View.Item holder, int position) {
        Esercizio temp = esercizios.get(position);
        holder.setName(temp.getNome());
        holder.setId(temp.getId());
        for (int i = 0; i < temp.getLstSet().size(); i++) {

            holder.addSerie(temp.getLstSet().get(i).getReps(), temp.getLstSet().get(i).getLoad());
        }
    }

    @Override
    public int getExerciseCount() {
        return esercizios.size();
    }

    @Override
    public void onNewExerciseAdded(String exerciseId, String exerciseName, ArrayList<Integer> reps, ArrayList<Float> loads) {
        ArrayList<ExerciseSetItem> lstSet = new ArrayList<>();

        for (int i = 0; i < reps.size(); i++)
            lstSet.add(new ExerciseSetItem(reps.get(i), loads.get(i)));

        esercizios.add(new Esercizio(exerciseId, exerciseName, lstSet));
        view.callNotifyDataSetChanged();
    }
}
