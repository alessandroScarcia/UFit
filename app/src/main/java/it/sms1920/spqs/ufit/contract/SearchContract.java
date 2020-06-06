package it.sms1920.spqs.ufit.contract;

import android.view.ViewGroup;

import it.sms1920.spqs.ufit.model.Exercise;
import it.sms1920.spqs.ufit.view.SearchActivity;

public interface SearchContract {
    interface view {

        void showExercise(Exercise exercise);
        void back();
        SearchActivity.myViewHolder createSearchViewItem(ViewGroup parent);

        interface itemHolder {
            void bind(Exercise item, final int position);
        }
    }

    interface presenter {
        void onClickExercise(int position);
        void onBackPressed();

    }

}


