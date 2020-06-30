package it.sms1920.spqs.ufit.launcher.userstats.testmaximalstrenght;


public interface MaxStrenghtTestListContract {
    interface View {

        void callNotifyDataSetChanged();

        void calculateWeight(int checkIdRadioButton, int weightValue);

        interface Item {
            void setReps(String reps);

            void setWeight(String weight);

            void setPosition(int position);
        }
    }

    interface Presenter {

        void onBindWeightItemListViewAtPosition(MaxStrenghtTestListContract.View.Item holder, int position);

        int getWeightCount();

        void loadSetList();

        void setRM(int weightValue);

        void calculateRM(int range, int weight);
    }
}
