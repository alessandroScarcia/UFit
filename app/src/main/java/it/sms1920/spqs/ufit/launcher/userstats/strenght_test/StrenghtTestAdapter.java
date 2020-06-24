package it.sms1920.spqs.ufit.launcher.userstats.strenght_test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import it.sms1920.spqs.ufit.launcher.R;

public class StrenghtTestAdapter extends RecyclerView.Adapter<StrenghtTestAdapter.StrenghtHolder> implements iStrenghtTest.View {


    private iStrenghtTest.Presenter presenter;

    private StrenghtTestFragment parentFragment;

    public StrenghtTestAdapter(StrenghtTestFragment parentFragment) {
        presenter = new StrenghtTestList(this);
        this.parentFragment = parentFragment;
    }

    @NonNull
    @Override
    public StrenghtHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StrenghtHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weight_reps, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull StrenghtHolder holder, int position) {
        presenter.onBindWeightItemListViewAtPosition(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getWeightCount();
    }

    @Override
    public void callNotifyDataSetChanged() {
        notifyDataSetChanged();
    }


    @Override
    public void calculateWeight(int checkIdRadioButton, String weightString) {

        if (checkIdRadioButton != 0 && weightString.matches("\\d+(?:\\.\\d+)?")){
            int weightValue = (int) Float.parseFloat(weightString);
            presenter.calculateRM(checkIdRadioButton, weightValue);
        }
    }



    public class StrenghtHolder extends RecyclerView.ViewHolder implements iStrenghtTest.View.Item {
        private TextView tvReps;
        private TextView tvWeight;
        private int position;


        public StrenghtHolder(@NonNull final View itemView) {
            super(itemView);
            tvReps = itemView.findViewById(R.id.tvReps);
            tvWeight = itemView.findViewById(R.id.tvWeight);


        }

        @Override
        public void setReps(String title) {
            tvReps.setText(title);
        }

        @Override
        public void setWeight(String description) {
            tvWeight.setText(description);
        }

        @Override
        public void setPosition(int position) {
            this.position = position;
        }


    }
}
