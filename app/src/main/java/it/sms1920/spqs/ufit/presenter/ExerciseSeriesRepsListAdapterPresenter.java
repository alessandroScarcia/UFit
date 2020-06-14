package it.sms1920.spqs.ufit.presenter;

import java.util.ArrayList;
import java.util.List;

import it.sms1920.spqs.ufit.contract.iExerciseSeriesRepsListAdapter;

public class ExerciseSeriesRepsListAdapterPresenter implements iExerciseSeriesRepsListAdapter.Presenter {


    iExerciseSeriesRepsListAdapter.View view;

    private class Vector3s {
        String x;
        String y;
        String z;

        public Vector3s(String x, String y, String z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    List<Vector3s> list;

    public ExerciseSeriesRepsListAdapterPresenter(iExerciseSeriesRepsListAdapter.View view) {
        this.view = view;
        list = new ArrayList<>();
    }

    @Override
    public void onBindItemViewAtPosition(iExerciseSeriesRepsListAdapter.View.Item holder, int position) {
        holder.setSerie(list.get(position).x);
        holder.setReps(list.get(position).y);
        holder.setLoad(list.get(position).z);
    }

    @Override
    public int getSeriesCount() {
        return list.size();
    }

    @Override
    public void onSerieAdded(int reps, float loads) {
        list.add(new Vector3s(
                String.valueOf(getSeriesCount()),
                String.valueOf(reps),
                String.valueOf(loads)));
    }

    @Override
    public void setSeriesList(ArrayList<Integer> reps, ArrayList<Float> loads) {
        list.clear();
        for (int i = 0; i < reps.size(); i++) {
            list.add(new Vector3s(
                    String.valueOf(i + 1),
                    String.valueOf(reps.get(i)),
                    String.valueOf(loads.get(i))
            ));
        }
        view.callNotifyDatasetChanged();
    }


}
