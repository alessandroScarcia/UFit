package it.sms1920.spqs.ufit.launcher.workoutplan.create;

public interface iSearchForWorkout {
    interface View {
        void back();
        void notifyQueryTextChangedToAdapter(final String keyword);
    }

    interface Presenter {
        void onBackPressed();
        void onQueryTextChanged(final String keyword);
    }
}
