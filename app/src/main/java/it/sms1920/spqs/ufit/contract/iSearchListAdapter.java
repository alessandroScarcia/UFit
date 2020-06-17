package it.sms1920.spqs.ufit.contract;

import android.media.Image;

public interface iSearchListAdapter {

    interface View {

        void callNotifyDataSetChanged();

        interface Item {
            void setName(String name);

            void setImage(String imageUrl);

            void setId(String id);
        }
    }

    interface Presenter {
        void onQueryTextChanged(final String keyword);
        void onBindExerciseItemViewAtPosition(View.Item holder, int position);
        int getExerciseCount();
    }

}
