package it.sms1920.spqs.ufit.launcher.workoutplan.adapter.workoutslist;

public interface WorkoutPlansListContract {
    interface View {

        void onItemRemoved(int position);
        void callNotifyDataSetChanged();

        void showWorkoutPlanDetail(String workoutPlanId);

        interface Item {
            void setName(String name);

            void setPosition(int position);
        }
    }

    interface Presenter {
        void onItemClicked(int position);

        void onBindWorkoutPlanItemListViewAtPosition(WorkoutPlansListContract.View.Item holder, int position);

        int getWorkoutPlansCount();

        void removeItemAt(int position);
        void onPersonalWorkoutPlansRequired();

        void onTrainerWorkoutPlansRequired();
    }
}
