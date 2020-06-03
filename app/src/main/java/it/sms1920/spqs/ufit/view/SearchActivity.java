package it.sms1920.spqs.ufit.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.sms1920.spqs.ufit.contract.SearchContract;
import it.sms1920.spqs.ufit.model.Exercise;
import it.sms1920.spqs.ufit.presenter.SearchAdapter;


public class SearchActivity extends AppCompatActivity implements SearchContract.view {

    Activity mContext = this;
    SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mContext = this;

        final SearchView keyString = findViewById(R.id.search_edit_text);
        keyString.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                adapter.search(mContext, s.trim());

                return false;
            }
        });

        // Setting adapter to the recycler view for search result
        adapter = new SearchAdapter((SearchContract.view) mContext);

        RecyclerView rvSearchResult = findViewById(R.id.rvSearchResult);
        rvSearchResult.setAdapter(adapter);
        rvSearchResult.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override  // TODO aggiungere dinamicità in base all'esercizio
    public void showExercise(int image, String nome) {
        startActivity(new Intent(this, ExerciseActivity.class));
        this.overridePendingTransition(R.anim.enter_from_right, R.anim.idle);
    }

    @Override
    public myViewHolder createSearchViewItem(ViewGroup parent) {
        LayoutInflater inflater = android.view.LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.item_exercise, parent, false);

        return new myViewHolder(v);
    }

    /**
     * Recycler View Holder that bind a single exercise to a single recyclerView row
     */
    public class myViewHolder extends RecyclerView.ViewHolder implements itemHolder {

        ImageView image;
        TextView name;
        View itemView;

        myViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            image = itemView.findViewById(R.id.imgExercise);
            name = itemView.findViewById(R.id.txtExerciseName);
        }

        @Override
        public void bind(Exercise item, final int position) {
            this.image.setImageResource(R.drawable.esercizio);
            this.name.setText(item.getName());

            itemView.setOnClickListener(new ImageView.OnClickListener() {

                @Override
                public void onClick(View view) {
                    adapter.onClickExercise(position);
                }
            });
        }
    }

}
