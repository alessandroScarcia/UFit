package it.sms1920.spqs.ufit.launcher.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import it.sms1920.spqs.ufit.model.util.StringUtils;
import it.sms1920.spqs.ufit.launcher.R;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ExerciseHolder> implements SearchListContract.View {

    //    Link to associated presenter
    private SearchListContract.Presenter presenter;

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

    private boolean selectable = false;
    private Set<String> selectedItems;
    private boolean selectionMode;

    private Manager viewManager;

    public SearchListAdapter(int layoutItemId) {
        presenter = new SearchListPresenter(this);
        this.layoutItemId = layoutItemId;
    }

    public SearchListAdapter(int layoutItemId, boolean selectable, Manager viewManager) {
        presenter = new SearchListPresenter(this);
        this.layoutItemId = layoutItemId;
        this.selectable = selectable;
        selectedItems = new HashSet<>();
        selectionMode = false;
        this.viewManager = viewManager;
    }

    public SearchListAdapter(int layoutItemId, View.OnClickListener clickListener, Manager viewManager) {
        this(layoutItemId);
        this.myClickListener = clickListener;
        this.viewManager = viewManager;
    }


    @NonNull
    @Override
    public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExerciseHolder(LayoutInflater.from(parent.getContext()).inflate(layoutItemId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseHolder holder, int position) {

        if (selectable) {
            presenter.onBindSelectableExerciseItemViewAtPosition(holder, position);
        } else {
            presenter.onBindExerciseItemViewAtPosition(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return presenter.getExerciseCount();
    }

    @Override
    public void callNotifyDataSetChanged() {
        viewManager.notifyNoItemFound(presenter.getExerciseCount() == 0);

        notifyDataSetChanged();
    }

    @Override
    public void initializeSelectedItemSet(ArrayList<String> exercisesId) {
        selectedItems = new HashSet<>(exercisesId);
    }

    @Override
    public ArrayList<String> getSelectedItems() {
        return new ArrayList<>(selectedItems);
    }


    public void onQueryTextChanged(final String keyword) {
        presenter.onQueryTextChanged(keyword);
    }

    public void setMyClickListener(View.OnClickListener clickListener) {
        this.myClickListener = clickListener;
    }

    public void setItemsSelection(ArrayList<String> exercisesId) {
        presenter.onItemsSelectionUpdateRequested(exercisesId);
    }

    /*
     * Inner class, used to extend RecyclerView's ViewHolder for correct item binding
     */
    public class ExerciseHolder extends RecyclerView.ViewHolder implements SearchListContract.View.Item {

        TextView name;
        TextView id;
        ImageView image;
        View lyt;
        CardView card;

        public ExerciseHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.lytEsercizio);
            lyt = itemView.findViewById(R.id.lytExerciseSelection);
            name = itemView.findViewById(R.id.txtExerciseName);
            image = itemView.findViewById(R.id.imgExercise);
            id = itemView.findViewById(R.id.txtExerciseId);
            itemView.setOnClickListener(myClickListener);


            if (selectable) {
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (!selectionMode) {
                            selectionMode = true;
                        }
                        viewManager.notifySelectionMode(true);
                        return false;
                    }
                });


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String exerciseId = id.getText().toString();
                        if (selectionMode) {

                            if (selectedItems.contains(exerciseId)) {
                                selectedItems.remove(exerciseId);
                                selectionMode = !selectedItems.isEmpty();
                                lyt.setVisibility(View.GONE);
                            } else {
                                selectedItems.add(exerciseId);
                                lyt.setVisibility(View.VISIBLE);
                            }
                        } else {
                            if (myClickListener != null) {
                                myClickListener.onClick(view);
                            }
                        }
                        viewManager.notifySelectionMode(selectionMode);
                    }
                });
            }

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

        @Override
        public void markSelected() {
            lyt.setVisibility(View.VISIBLE);
            selectionMode = true;
        }


    }

    public interface Manager {
        void notifySelectionMode(boolean activated);

        void notifyNoItemFound(boolean isEmpty);
    }
}