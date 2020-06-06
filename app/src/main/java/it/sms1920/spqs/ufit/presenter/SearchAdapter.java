//TODO rinominare il nome di sto file in qualcosa di più azzeccato

package it.sms1920.spqs.ufit.presenter;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import it.sms1920.spqs.ufit.contract.SearchContract;
import it.sms1920.spqs.ufit.model.firebase.Exercise;
import it.sms1920.spqs.ufit.view.SearchActivity.myViewHolder;

public class SearchAdapter extends RecyclerView.Adapter<myViewHolder> implements SearchContract.Presenter {

    private List<Exercise> lstExercise = new ArrayList<>();
    private SearchContract.View view;

    public SearchAdapter(SearchContract.View view) {
        this.view = view;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return view.createSearchViewItem(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.bind(lstExercise.get(position), position);
    }

    @Override
    public int getItemCount() {
        return lstExercise.size();
    }

    @Override
    public void onClickExercise(int position) {
        view.showExercise(lstExercise.get(position));
    }

    public void search(String keyString) {
        lstExercise.clear();
        notifyDataSetChanged();
        //lstExercise = new ArrayList<>(model.askForResult(keyString));
        notifyDataSetChanged();
    }

}
