//TODO rinominare il nome di sto file in qualcosa di più azzeccato

package it.sms1920.spqs.ufit.presenter;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import it.sms1920.spqs.ufit.MainframeMVP;
import it.sms1920.spqs.ufit.R;
import it.sms1920.spqs.ufit.model.Exercise;
import it.sms1920.spqs.ufit.model.Search;
import it.sms1920.spqs.ufit.view.SearchActivity.myViewHolder;

public class SearchAdapter extends RecyclerView.Adapter<myViewHolder> {

    private List<Exercise> lstExercise;
    private SearchViewManager searchViewManager;
    Search model;

    public SearchAdapter(SearchViewManager searchViewManager) {
        model = new Search(this);
        this.lstExercise = new ArrayList<>();
        this.searchViewManager = searchViewManager;
    }

    public void publishData(Exercise exercise) {
        lstExercise.add(exercise);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return searchViewManager.createSearchViewItem(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        holder.bind(lstExercise.get(position), position);

    }

    @Override
    public int getItemCount() {
        return lstExercise.size();
    }


    public interface SearchViewItem {
        void bind(Exercise item, final int position);
    }

    public interface SearchViewManager {
        void showExercise(int image, String nome);

        myViewHolder createSearchViewItem(ViewGroup parent);
    }

    public void onClickExercise(int position) {
        searchViewManager.showExercise(R.drawable.profile_photo,
                lstExercise.get(position).getName());

    }


    public void search(String keyString) {
        lstExercise.clear();
        notifyDataSetChanged();
        model.askForResult(keyString);
    }

}
