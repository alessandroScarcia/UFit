package it.sms1920.spqs.ufit.launcher.search;


public interface SearchContract {
    interface View {
        void back();

        void notifyQueryTextChangedToAdapter(final String keyword);

        void showExercise(int position);
    }

    interface Presenter {
        void onBackPressed();

        void onQueryTextChanged(final String keyword);

        void onItemClicked(int position);
    }

}


