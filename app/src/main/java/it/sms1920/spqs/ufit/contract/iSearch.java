package it.sms1920.spqs.ufit.contract;

import androidx.recyclerview.widget.RecyclerView;

public interface iSearch {
    interface View {
        void back();

        void notifyQueryTextChangedToAdapter(String query);

        void showExercise(int position);
    }

    interface Presenter {
        void onBackPressed();

        void onQueryTextChanged(String query);

        void onItemClicked(int position);
    }

}


