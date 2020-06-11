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

    private iSearchListAdapter.Presenter presenter;
    private SearchActivity activity;

    public SearchListAdapter(SearchActivity activity) {
        presenter = new SearchListAdapterPresenter(this);
        this.activity = activity;
    }

    @NonNull
    @Override
    public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExerciseHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise, parent, false));
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


    // TODO che maialata
    @Override
    public void showExercise(int exerciseId, String exerciseName) {
        activity.startExerciseActivity(exerciseId, exerciseName);
    }

    public class ExerciseHolder extends RecyclerView.ViewHolder implements iSearchListAdapter.View.Item {

        TextView name;
        ImageView image;
        int position;

        public ExerciseHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.txtExerciseName);
            image=itemView.findViewById(R.id.imgExercise);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.onClickedExerciseHolder(position);
                }
            });
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
            //TODO
            this.image.setImageResource(R.drawable.img_exercise);
        }

        @Override
        public void setPosition(int position) {
            this.position = position;
        }
    }
}