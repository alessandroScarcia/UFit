package it.sms1920.spqs.ufit.contract;

import android.view.ViewGroup;

import it.sms1920.spqs.ufit.model.firebase.Exercise;
import it.sms1920.spqs.ufit.view.SearchActivity;

public interface SearchContract {
    interface View {

        void showExercise(Exercise exercise);

        SearchActivity.myViewHolder createSearchViewItem(ViewGroup parent);

        interface itemHolder {
            void bind(Exercise item, final int position);
        }
    }

    interface Presenter {
        void onClickExercise(int position);

    }

}


