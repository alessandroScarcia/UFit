package it.sms1920.spqs.ufit.launcher.userstats.strenght_test;


public interface iStrenghtTest {
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
        

        void onBindWeightItemListViewAtPosition(iStrenghtTest.View.Item holder, int position);

        int getWeightCount();

        void addNewWeightItem(String reps, String weight);


        void loadSetList();

        void setRM(int weightValue);

        void calculateRM(int range, int weight);
    }
}
