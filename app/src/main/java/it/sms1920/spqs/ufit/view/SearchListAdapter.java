package it.sms1920.spqs.ufit.view;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import it.sms1920.spqs.ufit.contract.iSearchListAdapter;
import it.sms1920.spqs.ufit.presenter.SearchListAdapterPresenter;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ExerciseHolder> implements iSearchListAdapter.View {

    //    Link to associated presenter
    private iSearchListAdapter.Presenter presenter;

    /*
        Resource ID indicating layout to use in binding. Required at least something like below:
        Required: TextView with "txtExerciseName" as ID
                  ImageView with "imgExercise" as ID
    */
    private int layoutItemID;

    /*
        Listener to apply for each item "setOnClickListener" in list
     */
    private View.OnClickListener myClickListener;


    public SearchListAdapter(int layoutItemID) {
        presenter = new SearchListAdapterPresenter(this);
        this.layoutItemID = layoutItemID;
    }

    @NonNull
    @Override
    public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExerciseHolder(LayoutInflater.from(parent.getContext()).inflate(layoutItemID, parent, false));
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

    public void setMyClickListener(View.OnClickListener listener) {
        this.myClickListener = listener;
    }


    /*
     * Inner class, used to extend RecyclerView's ViewHolder for correct item binding
     */
    public class ExerciseHolder extends RecyclerView.ViewHolder implements iSearchListAdapter.View.Item {

        TextView name;
        ImageView image;

        public ExerciseHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtExerciseName);
            image = itemView.findViewById(R.id.imgExercise);
            itemView.setOnClickListener(myClickListener);
        }

        @Override
        public void setName(String name) {
            if (name == null || name.length() == 0) {
                name = "";
            } else if (name.length() == 1) {
                name = name.toUpperCase();
            } else {
                name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
            }
            this.name.setText(name);
        }

        @Override
        public void setImage(Image image) {
            //TODO image fetch
            this.image.setImageResource(R.drawable.img_exercise);
        }

        @Override
        public void setId(String id) {
            this.name.setHint(id);
        }
    }
}