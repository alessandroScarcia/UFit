package it.sms1920.spqs.ufit;

import android.view.ViewGroup;

import it.sms1920.spqs.ufit.model.Exercise;
import it.sms1920.spqs.ufit.view.SearchActivity;

public interface MainframeMVP {

    interface view {

        void showExercise(int image, String nome);

        SearchActivity.myViewHolder createSearchViewItem(ViewGroup parent);

        void bind(Exercise item, final int position);

    }

    interface presenter {
        public void onClickExercise(int position);
    }


}
