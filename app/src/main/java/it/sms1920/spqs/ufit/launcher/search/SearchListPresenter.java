package it.sms1920.spqs.ufit.launcher.search;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import it.sms1920.spqs.ufit.model.search.iSearchClient;
import it.sms1920.spqs.ufit.model.search.ExerciseDetailed;
import it.sms1920.spqs.ufit.model.search.SearchExercise;

/**
 * Presenter for SearchListAdapter View.
 * Implements iSearchClient to communicate with Model SearchExercise.
 */
public class SearchListPresenter implements SearchListContract.Presenter, iSearchClient {
    private static final String TAG = SearchListPresenter.class.getCanonicalName();

    private SearchListContract.View view;

    private SearchExercise searchExercise;
    private List<ExerciseDetailed> exerciseDetailedList = new ArrayList<>();

    public SearchListPresenter(SearchListContract.View view) {
        this.view = view;
        searchExercise = new SearchExercise(this);
        onQueryTextChanged("");
    }

    @Override
    public void onQueryTextChanged(final String keyword) {
        exerciseDetailedList.clear();
        searchExercise.getExerciseWithNameStarting(keyword);
    }

    @Override
    public void onBindSelectableExerciseItemViewAtPosition(SearchListContract.View.Item holder, int position) {
        ExerciseDetailed exercise = exerciseDetailedList.get(position);
        if (view.getSelectedItems().contains(exercise.getExerciseId())) {
            holder.markSelected();
        }
        holder.setName(exercise.getName());
        holder.setImage(exercise.getImageUrl());
        holder.setId(exercise.getExerciseId());
    }

    @Override
    public void onBindExerciseItemViewAtPosition(SearchListContract.View.Item holder, int position) {
        ExerciseDetailed exercise = exerciseDetailedList.get(position);
        holder.setName(exercise.getName());
        holder.setImage(exercise.getImageUrl());
        holder.setId(exercise.getExerciseId());
    }

    @Override
    public int getExerciseCount() {
        return exerciseDetailedList.size();
    }

    @Override
    public void onItemsSelectionUpdateRequested(ArrayList<String> exercisesId) {
        view.initializeSelectedItemSet(exercisesId);
        view.callNotifyDataSetChanged();
    }

    @Override
    public void notifyResultListReady() {
        exerciseDetailedList = searchExercise.getExerciseResultList();
        view.callNotifyDataSetChanged();
    }


}