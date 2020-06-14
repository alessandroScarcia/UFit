package it.sms1920.spqs.ufit.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.sms1920.spqs.ufit.contract.iWorkoutExerciseListAdapter;

public class WorkoutExerciseListAdapterPresenter implements iWorkoutExerciseListAdapter.Presenter {


    class Esercizio {
        String nome;
        ArrayList<Integer> reps;
        ArrayList<Float> loads;

        public Esercizio(String nome, ArrayList<Integer> reps, ArrayList<Float> loads) {
            this.nome = nome;
            this.reps = reps;
            this.loads = loads;
        }

        public String getNome() {
            return nome;
        }

        public ArrayList<Integer> getReps() {
            return reps;
        }

        public ArrayList<Float> getLoads() {
            return loads;
        }
    }

    iWorkoutExerciseListAdapter.View view;


    List<Esercizio> esercizios;


    public WorkoutExerciseListAdapterPresenter(iWorkoutExerciseListAdapter.View view) {
        this.view = view;

        ArrayList<Integer> reps = new ArrayList<>();
        ArrayList<Float> loads = new ArrayList<>();

        reps.add(2);
        loads.add(2f);

        esercizios = new ArrayList<>();

        esercizios.add(new Esercizio("pippo", reps, loads));
        esercizios.add(new Esercizio("pippo", reps, loads));
        esercizios.add(new Esercizio("pippo", reps, loads));

        view.callNotifyDataSetChanged();

    }

    @Override
    public void onBindExerciseItemViewAtPosition(iWorkoutExerciseListAdapter.View.Item holder, int position) {
        Esercizio temp = esercizios.get(position);
        holder.setName(temp.getNome());
        for (int i = 0; i < temp.getReps().size(); i++) {

            holder.addSerie(temp.getReps().get(i), temp.getLoads().get(i));
        }
    }

    @Override
    public int getExerciseCount() {
        return esercizios.size();
    }

    @Override
    public void onNewExerciseAdded(String exerciseId, ArrayList<Integer> reps, ArrayList<Float> loads) {
        esercizios.add(new Esercizio(exerciseId, reps, loads));
        view.callNotifyDataSetChanged();
    }
}
