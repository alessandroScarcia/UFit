package it.sms1920.spqs.ufit.presenter;

import java.util.ArrayList;
import java.util.List;

import it.sms1920.spqs.ufit.contract.iExerciseSeriesRepsListAdapter;
import it.sms1920.spqs.ufit.model.ExerciseSetItem;

public class ExerciseSeriesRepsListAdapterPresenter implements iExerciseSeriesRepsListAdapter.Presenter {


    iExerciseSeriesRepsListAdapter.View view;

    private class Vector2s {
        String x; // Reps
        String y; // Loads

        public Vector2s(String x, String y) {
            this.x = x;
            this.y = y;
        }
    }

    List<Vector2s> list;

    public ExerciseSeriesRepsListAdapterPresenter(iExerciseSeriesRepsListAdapter.View view) {
        this.view = view;
        list = new ArrayList<>();
    }

    @Override
    public void onBindItemViewAtPosition(iExerciseSeriesRepsListAdapter.View.Item holder, int position) {
        holder.setReps(list.get(position).x);
        holder.setLoad(list.get(position).y);
    }

    @Override
    public int getSeriesCount() {
        return list.size();
    }

    @Override
    public void onSerieAdded(int reps, float loads) {
        list.add(new Vector2s(
                String.valueOf(reps),
                String.valueOf(loads)));
    }

    @Override
    public void setSeriesList(ArrayList<ExerciseSetItem> seriesList) {
        list.clear();
        for (int i = 0; i < seriesList.size(); i++) {
            list.add(new Vector2s(
                    String.valueOf(seriesList.get(i).getReps()),
                    String.valueOf(seriesList.get(i).getLoad())
            ));
        }
        view.callNotifyDatasetChanged();
    }

    @Override
    public void removeItemAt(int position) {
            list.remove(position);
            view.callNotifyItemRemoved(position);
            view.callNotifyItemRangeChanged(position, list.size());
            view.callNotifyDatasetChanged();
    }

    @Override
    public ArrayList<Integer> getReps() {
        ArrayList<Integer> reps = new ArrayList<>();
        for (Vector2s serie : list) {
            reps.add(Integer.valueOf(serie.x /* reps */));
        }
        return reps;
    }

    @Override
    public ArrayList<Float> getLoads() {
        ArrayList<Float> loads = new ArrayList<>();
        for (Vector2s serie : list) {
            loads.add(Float.valueOf(serie.y /* loads */));
        }
        return loads;
    }


}
