package it.sms1920.spqs.ufit.contract;

public interface iWorkoutPlans {
    interface View {

        void callNotifyDataSetChanged();

        interface Item {
            void setName(String name);

            void setPosition(int position);
        }
    }

    interface Presenter {
        void onClickedItem(int position);

        void onBindWorkoutPlanItemListViewAtPosition(iWorkoutPlans.View.Item holder, int position);

        int getWorkoutPlansCount();

        void onPersonalWorkoutPlansRequired();

        void onTrainerWorkoutPlansRequired();
    }
}
