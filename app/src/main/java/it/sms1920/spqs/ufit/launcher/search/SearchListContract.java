package it.sms1920.spqs.ufit.launcher.search;

import java.util.ArrayList;

public interface SearchListContract {

    interface View {

        void callNotifyDataSetChanged();
        void initializeSelectedItemSet(ArrayList<String> exercisesId);
        ArrayList<String> getSelectedItems();

        interface Item {
            void setName(String name);

            void setImage(String imageUrl);

            void setId(String id);

            void markSelected();

        }
    }

    interface Presenter {
        void onQueryTextChanged(final String keyword);
        void onBindExerciseItemViewAtPosition(View.Item holder, int position);
        void onBindSelectableExerciseItemViewAtPosition(View.Item holder, int position);
        int getExerciseCount();
        void onItemsSelectionUpdateRequested(ArrayList<String> exercisesId);
    }

}
