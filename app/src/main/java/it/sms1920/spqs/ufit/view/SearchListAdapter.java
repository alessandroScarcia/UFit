package it.sms1920.spqs.ufit.view;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import it.sms1920.spqs.ufit.contract.iSearchListAdapter;
import it.sms1920.spqs.ufit.presenter.SearchListAdapterPresenter;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ExerciseHolder> implements iSearchListAdapter.View {

    //    Link to associated presenter
    private iSearchListAdapter.Presenter presenter;

    /*
        Resource ID indicating layout to use in binding. Required at least something like below:
        Required: TextView with "txtExerciseName" as ID
                  TextView with "txtExerciseId" as ID
                  ImageView with "imgExercise" as ID
    */
    private int layoutItemId;

    /*
        Listener to apply for each item "setOnClickListener" in list
     */
    private View.OnClickListener myClickListener;


    public SearchListAdapter(int layoutItemId) {
        presenter = new SearchListAdapterPresenter(this);
        this.layoutItemId = layoutItemId;
    }

    public SearchListAdapter(int layoutItemId, View.OnClickListener clickListener) {
        this(layoutItemId);
        this.myClickListener = clickListener;
    }

    @NonNull
    @Override
    public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExerciseHolder(LayoutInflater.from(parent.getContext()).inflate(layoutItemId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseHolder holder, int position) {
        presenter.onBindExerciseItemViewAtPosition(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getExerciseCount();
    }

    @Override
    public void callNotifyDataSetChanged() {
        notifyDataSetChanged();
    }

    public void onQueryTextChanged(final String keyword) {
        presenter.onQueryTextChanged(keyword);
    }

    public void setMyClickListener(View.OnClickListener clickListener) {
        this.myClickListener = clickListener;
    }


    /*
     * Inner class, used to extend RecyclerView's ViewHolder for correct item binding
     */
    public class ExerciseHolder extends RecyclerView.ViewHolder implements iSearchListAdapter.View.Item {

        TextView name;
        TextView id;
        ImageView image;

        public ExerciseHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtExerciseName);
            image = itemView.findViewById(R.id.imgExercise);
            id = itemView.findViewById(R.id.txtExerciseId);
            itemView.setOnClickListener(myClickListener);
        }

        @Override
        public void setName(String name) {
            this.name.setText(StringUtils.capitalize(name));
        }

        @Override
        public void setImage(String imageUrl) {
            Picasso.get().load(imageUrl).into(image);
        }

        @Override
        public void setId(String id) {
            this.id.setText(id);
        }
    }
}