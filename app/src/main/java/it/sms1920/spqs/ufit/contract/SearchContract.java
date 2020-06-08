package it.sms1920.spqs.ufit.contract;

import android.view.ViewGroup;

import it.sms1920.spqs.ufit.model.Exercise;
import it.sms1920.spqs.ufit.view.SearchActivity;

public interface SearchContract {
    interface View {
        void back();
        void notifyQueryTextChangedToAdapter(String query);
    }

    interface Presenter {
        void onBackPressed();
        void onQueryTextChanged(String query);
    }

}


