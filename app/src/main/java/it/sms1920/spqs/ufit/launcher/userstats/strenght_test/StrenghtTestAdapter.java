package it.sms1920.spqs.ufit.launcher.userstats.strenght_test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import it.sms1920.spqs.ufit.launcher.R;

public class StrenghtTestAdapter extends RecyclerView.Adapter<StrenghtTestAdapter.StrenghtHolder> implements iStrenghtTest.View {


    private iStrenghtTest.Presenter presenter;

//    private StrenghtTestFragment parentFragment;

    public StrenghtTestAdapter() {
        presenter = new StrenghtTestList(this);
//        this.parentFragment = parentFragment;
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


    /**
     * Function that communicate with the list to realize the different set and weight
     * @param checkIdRadioButton the id from the checkbox selected in the dialog box
     * @param weightString the weight inserted in the edit box
     */
    @Override
    public void calculateWeight(int checkIdRadioButton, String weightString) {
        //we check the validity of the input
        if (checkIdRadioButton != 0 && weightString.matches("\\d+(?:\\.\\d+)?")){
            //we round the value submitted and send to the presenter
            int weightValue = (int) Float.parseFloat(weightString);
            presenter.calculateRM(checkIdRadioButton, weightValue);
        }
    }


    public static class StrenghtHolder extends RecyclerView.ViewHolder implements iStrenghtTest.View.Item {
        private TextView tvReps;
        private TextView tvWeight;

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
        }


    }
}
