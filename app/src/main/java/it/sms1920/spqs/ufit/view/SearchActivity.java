package it.sms1920.spqs.ufit.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.sms1920.spqs.ufit.contract.SearchContract;
import it.sms1920.spqs.ufit.model.Exercise;
import it.sms1920.spqs.ufit.presenter.SearchAdapter;


public class SearchActivity extends AppCompatActivity implements SearchContract.View {

    Activity mContext = this;
    SearchAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mContext = this;

        TextInputEditText txtSearchField = findViewById(R.id.txtSearchField);
        TextInputLayout txtSearchFieldLayout = findViewById(R.id.txtSearchFieldLayout);

        txtSearchFieldLayout.setStartIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.onBackPressed();
            }
        });

        txtSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.search(charSequence.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        // Setting adapter to the recycler view for search result
        adapter = new SearchAdapter((SearchContract.View) mContext);
        adapter.search("");

        RecyclerView rvSearchResult = findViewById(R.id.rvSearchResult);
        rvSearchResult.setAdapter(adapter);
        rvSearchResult.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    public void onBackPressed() {
        adapter.onBackPressed();
    }

    @Override
    public void showExercise(Exercise exercise) {
        // TODO aggiungere dinamicità in base all'esercizio
        Intent intent = new Intent(this, ExerciseActivity.class);
        intent.putExtra("Exercise", exercise);
        startActivity(intent);
        this.overridePendingTransition(R.anim.enter_from_right, R.anim.idle);
    }

    @Override
    public void back() {
        finish();
        overridePendingTransition(R.anim.idle, R.anim.exit_to_right);
    }

    @Override
    public myViewHolder createSearchViewItem(ViewGroup parent) {
        LayoutInflater inflater = android.view.LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.item_exercise, parent, false);

        return new myViewHolder(v);
    }

    /**
     * Recycler View Holder binds a single exercise to a single recyclerView row
     */
    public class myViewHolder extends RecyclerView.ViewHolder implements SearchContract.View.itemHolder {

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
            this.image.setImageResource(R.drawable.img_exercise);
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
