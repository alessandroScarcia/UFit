package it.sms1920.spqs.ufit.presenter;

import java.util.ArrayList;
import java.util.List;

import it.sms1920.spqs.ufit.contract.iExerciseSeriesRepsListAdapter;
import it.sms1920.spqs.ufit.model.ExerciseSetItem;

public class ExerciseSeriesRepsListAdapterPresenter implements iExerciseSeriesRepsListAdapter.Presenter {

    private iExerciseSeriesRepsListAdapter.View view;
    private List<ExerciseSetItem> list;

    public ExerciseSeriesRepsListAdapterPresenter(iExerciseSeriesRepsListAdapter.View view) {
        this.view = view;
        list = new ArrayList<>();
    }

    @Override
    public void onBindItemViewAtPosition(iExerciseSeriesRepsListAdapter.View.Item holder, int position) {
        holder.setReps(String.valueOf(list.get(position).getReps()));
        holder.setLoad(String.valueOf(list.get(position).getLoad()));
    }

    @Override
    public int getSeriesCount() {
        return list.size();
    }

    @Override
    public void onSerieAdded(int reps, float loads) {
        list.add(new ExerciseSetItem(reps, loads));
    }

    @Override
    public void setSeriesList(ArrayList<ExerciseSetItem> seriesList) {
        list.clear();
        list.addAll(seriesList);
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
        for (ExerciseSetItem serie : list) {
            reps.add(serie.getReps());
        }
        return reps;
    }

    @Override
    public ArrayList<Float> getLoads() {
        ArrayList<Float> loads = new ArrayList<>();
        for (ExerciseSetItem serie : list) {
            loads.add(serie.getLoad());
        }
        return loads;
    }
}
