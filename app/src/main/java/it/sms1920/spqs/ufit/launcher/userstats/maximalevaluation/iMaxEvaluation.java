package it.sms1920.spqs.ufit.launcher.userstats.maximalevaluation;


public interface iMaxEvaluation {
    interface View {

        void callNotifyDataSetChanged();

        void calculateWeight(int checkIdRadioButton, String weightValue);

        interface Item {
            void setReps(String reps);

            void setWeight(String weight);

            void setPosition(int position);

        }



    }

    interface Presenter {

        void onBindWeightItemListViewAtPosition(iMaxEvaluation.View.Item holder, int position);

        int getWeightCount();

        void loadSetList();

        void setRM(int weightValue);

        void calculateRM(int range, int weight);
    }
}
