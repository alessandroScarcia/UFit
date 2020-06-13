package it.sms1920.spqs.ufit.contract;

public interface iStats {
    interface View {

        void callNotifyDataSetChanged();

        interface Item {
            void setName(String name);

            void setPosition(int position);
        }
    }

    interface Presenter {
        void onClickAddMeasure();

        void onClickedItem(int position);

        void onBindWorkoutPlanItemListViewAtPosition(iStats.View.Item holder, int position);


        void onGeneralStatsRequired();

        void onBodyPartsStatsRequired();
    }
}
