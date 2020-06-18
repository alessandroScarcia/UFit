package it.sms1920.spqs.ufit.launcher.workoutplan.showlist;

public interface iWorkoutPlans {
    interface View {

        void callNotifyDataSetChanged();

        void showWorkoutPlanDetail(String workoutPlanId);

        interface Item {
            void setName(String name);

            void setPosition(int position);
        }
    }

    interface Presenter {
        void onItemClicked(int position);

        void onBindWorkoutPlanItemListViewAtPosition(iWorkoutPlans.View.Item holder, int position);

        int getWorkoutPlansCount();

        void onPersonalWorkoutPlansRequired();

        void onTrainerWorkoutPlansRequired();
    }
}
