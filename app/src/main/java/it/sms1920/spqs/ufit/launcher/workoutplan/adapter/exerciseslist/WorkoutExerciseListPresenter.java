package it.sms1920.spqs.ufit.launcher.workoutplan.adapter.exerciseslist;

import java.util.ArrayList;
import java.util.List;

import it.sms1920.spqs.ufit.model.firebase.database.ExerciseSetDetails;
import it.sms1920.spqs.ufit.model.firebase.database.ExerciseSetItem;
import it.sms1920.spqs.ufit.model.search.ExerciseDetailed;
import it.sms1920.spqs.ufit.model.search.SearchExercise;
import it.sms1920.spqs.ufit.model.search.iSearchClient;

public class WorkoutExerciseListPresenter implements WorkoutExerciseListContract.Presenter, iSearchClient {

    WorkoutExerciseListContract.View view;

    List<ExerciseSetDetails> myExercises;
    SearchExercise mSearch;


    public WorkoutExerciseListPresenter(WorkoutExerciseListContract.View view) {
        this.view = view;

        myExercises = new ArrayList<>();
        mSearch = new SearchExercise(this);

        view.callNotifyDataSetChanged();
    }


    @Override
    public void notifyResultListReady() {
        myExercises.clear();
        // TODO handle old sets
        for (ExerciseDetailed exercise : mSearch.getExerciseResultList()) {
            myExercises.add(new ExerciseSetDetails(exercise.getExerciseId(), exercise.getName(), new ArrayList<ExerciseSetItem>()));
        }

        view.callNotifyDataSetChanged();
    }


    @Override
    public void onBindExerciseItemViewAtPosition(WorkoutExerciseListContract.View.Item holder, int position) {
        ExerciseSetDetails exercise = myExercises.get(position);
        holder.setName(exercise.getExerciseName());
        holder.setId(exercise.getExerciseId());
        holder.setExerciseSetsAdapter(position);

        holder.setExerciseSets(new ArrayList<ExerciseSetItem>());
        for (ExerciseSetItem exerciseSet : exercise.getExerciseSetItems()) {
            holder.addSerie(exerciseSet.getReps(), exerciseSet.getLoad());
        }
    }

    @Override
    public int getExerciseCount() {
        return myExercises.size();
    }

    @Override
    public void removeItemAt(int position) {
        myExercises.remove(position);
        view.callNotifyDataSetChanged();
    }

    @Override
    public void onNewExercisesAdded(ArrayList<String> exercisesId) {
        mSearch.getExerciseByIdList(exercisesId);
    }

    @Override
    public ArrayList<String> onExercisesIdRequested() {
        ArrayList<String> list = new ArrayList<>();

        for (ExerciseSetDetails exercise : myExercises) {
            list.add(exercise.getExerciseId());
        }
        return list;
    }

    @Override
    public List<ExerciseSetItem> onSetsListRequested(int position) {

        return myExercises.get(position).getExerciseSetItems();
    }



}
