//TODO rinominare il nome di sto file in qualcosa di più azzeccato

package it.sms1920.spqs.ufit.presenter;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import it.sms1920.spqs.ufit.contract.SearchContract;
import it.sms1920.spqs.ufit.model.Exercise;
import it.sms1920.spqs.ufit.model.Search;
import it.sms1920.spqs.ufit.view.R;
import it.sms1920.spqs.ufit.view.SearchActivity.myViewHolder;

public class SearchAdapter extends RecyclerView.Adapter<myViewHolder> implements SearchContract.presenter {

    private List<Exercise> lstExercise;
    private SearchContract.view view;
    Search model;

    public SearchAdapter(SearchContract.view view) {
        model = new Search(this);
        this.lstExercise = new ArrayList<>();
        this.view = view;
    }

    public void publishData(Exercise exercise) {
        lstExercise.add(exercise);
        notifyDataSetChanged();
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
        view.showExercise(R.drawable.profile_photo,
                lstExercise.get(position).getName());

    }


    public void search(String keyString) {
        lstExercise.clear();
        notifyDataSetChanged();
        model.askForResult(keyString);
    }

}
