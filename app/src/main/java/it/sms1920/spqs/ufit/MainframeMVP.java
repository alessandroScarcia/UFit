package it.sms1920.spqs.ufit;

import android.view.ViewGroup;

import it.sms1920.spqs.ufit.model.Exercise;
import it.sms1920.spqs.ufit.view.SearchActivity;


/**
 * Model-view-presenter interface, contains interfaces for View, Model and Presenter
 */
public interface MainframeMVP {

    /**
     * Contains interfaces for the Views
     */
    interface view {

        /**
         * Contains view's interfaces for search
         */
        interface search {
            void showExercise(int image, String nome);

            SearchActivity.myViewHolder createSearchViewItem(ViewGroup parent);

            interface recyclerViewAdapter {
                void bind(Exercise item, final int position);
            }
        }
    }


    /**
     * Contains interfaces for the Presenters
     */
    interface presenter {
        interface search {
            void onClickExercise(int position);
        }

    }


    /**
     * Contains interfaces for the Models
     */
    interface model {

    }


}
