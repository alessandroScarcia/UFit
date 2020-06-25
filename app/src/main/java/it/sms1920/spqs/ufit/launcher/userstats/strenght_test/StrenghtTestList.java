package it.sms1920.spqs.ufit.launcher.userstats.strenght_test;


import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class StrenghtTestList implements iStrenghtTest.Presenter {
    //declaration of the different range
    private static final int RM_1 = 1;
    private static final int RANGE_2_3 = 2;
    private static final int RANGE_4_5 = 3;
    private static final int RANGE_6_7 = 4;
    private static final int RANGE_8_9 = 5;
    private static final int RANGE_10_11 = 6;
    private static final int RANGE_12_13 = 7;
    private static final int RANGE_14_15 = 8;
    private static final int RANGE_16_17 = 9;
    private static final int RANGE_18_19 = 10;
    private static final int RANGE_20 = 11;

    private static final int PERCENT_100 = 100;

    private static final int PERCENT_90 = 90;
    private static final int PERCENT_85  = 85;
    private static final int PERCENT_80  = 80;
    private static final int PERCENT_75  = 75;
    private static final int PERCENT_70  = 70;
    private static final int PERCENT_65  = 65;
    private static final int PERCENT_60  = 60;
    private static final int PERCENT_55  = 55;
    private static final int PERCENT_50  = 50;
    private static final int PERCENT_45  = 45;


    private static final String TAG = StrenghtTestList.class.getCanonicalName();
    private final iStrenghtTest.View view;

    private List<Set> setWeightList;
    private static int RM;

    public StrenghtTestList(iStrenghtTest.View view) {
        this.view = view;
        setWeightList= new ArrayList<>();
    }



    @Override
    public void onBindWeightItemListViewAtPosition(iStrenghtTest.View.Item holder, int position) {
        Set itemData = setWeightList.get(position);
        holder.setPosition(position);
        holder.setReps(itemData.getReps());
        holder.setWeight(itemData.getWeight());
    }

    @Override
    public int getWeightCount() {
        return setWeightList.size();
    }


    /**
     * Function used to add the different set into the list
     */
    @Override
    public void loadSetList() {
        Log.d(TAG, "loadAdvice");

        Set rmSet = new Set();
        rmSet.setReps("1");
        rmSet.setWeight(String.valueOf(RM));

        setWeightList.add(rmSet);
        setWeightList.add(weightPercent90());
        setWeightList.add(weightPercent85());
        setWeightList.add(weightPercent80());
        setWeightList.add(weightPercent75());
        setWeightList.add(weightPercent70());
        setWeightList.add(weightPercent65());
        setWeightList.add(weightPercent60());
        setWeightList.add(weightPercent55());
        setWeightList.add(weightPercent50());
        setWeightList.add(weightPercent45());

        view.callNotifyDataSetChanged();
    }


    public void setRM(int weightValue) {
        RM = weightValue;
    }


    public Set weightPercent90() {
        Set newSet = new Set();
        newSet.setReps("3-2");
        newSet.setWeight(String.valueOf(RM * PERCENT_90 /PERCENT_100));
        return newSet;
    }


    public Set weightPercent85() {
        Set newSet = new Set();
        newSet.setReps("5-4");
        newSet.setWeight(String.valueOf(RM * PERCENT_85 /PERCENT_100));
        return newSet;
    }


    public Set weightPercent80() {
        Set newSet = new Set();
        newSet.setReps("7-6");
        newSet.setWeight(String.valueOf(RM * PERCENT_80 /PERCENT_100));
        return newSet;
    }


    public Set weightPercent75() {
        Set newSet = new Set();
        newSet.setReps("9-8");
        newSet.setWeight(String.valueOf(RM * PERCENT_75 /PERCENT_100));
        return newSet;
    }


    public Set weightPercent70() {
        Set newSet = new Set();
        newSet.setReps("11-10");
        newSet.setWeight(String.valueOf(RM * PERCENT_70 / PERCENT_100));
        return newSet;
    }


    public Set weightPercent65() {
        Set newSet = new Set();
        newSet.setReps("13-12");
        newSet.setWeight(String.valueOf(RM * PERCENT_65 / PERCENT_100));
        return newSet;
    }


    public Set weightPercent60() {
        Set newSet = new Set();
        newSet.setReps("15-14");
        newSet.setWeight(String.valueOf(RM * PERCENT_60 /PERCENT_100));
        return newSet;
    }


    public Set weightPercent55() {
        Set newSet = new Set();
        newSet.setReps("17-16");
        newSet.setWeight(String.valueOf(RM * PERCENT_55 / PERCENT_100));
        return newSet;
    }


    public Set weightPercent50() {
        Set newSet = new Set();
        newSet.setReps("19-18");
        newSet.setWeight(String.valueOf(RM * PERCENT_50 / PERCENT_100));
        return newSet;
    }

    public Set weightPercent45() {
        Set newSet = new Set();
        newSet.setReps("20");
        newSet.setWeight(String.valueOf(RM * PERCENT_45 / PERCENT_100));
        return newSet;
    }

    /**
     * Function used to calculate the weight of 1 rep
     * @param range range of reps
     * @param weightValue weight value submitted
     */
    @Override
    public void calculateRM(int range, int weightValue) {
        switch (range){
            case RM_1:
                setRM(weightValue);
                break;
            case RANGE_2_3:
                setRM(weightValue * PERCENT_100 / PERCENT_90);
                break;
            case RANGE_4_5:
                setRM(weightValue * PERCENT_100 / PERCENT_85);
                break;
            case RANGE_6_7:
                setRM(weightValue * PERCENT_100 / PERCENT_80);
                break;
            case RANGE_8_9:
                setRM(weightValue * PERCENT_100 / PERCENT_75);
                break;
            case RANGE_10_11:
                setRM(weightValue * PERCENT_100 / PERCENT_70);
                break;
            case RANGE_12_13:
                setRM(weightValue * PERCENT_100 / PERCENT_65);
                break;
            case RANGE_14_15:
                setRM(weightValue * PERCENT_100 / PERCENT_60);
                break;
            case RANGE_16_17:
                setRM(weightValue * PERCENT_100 / PERCENT_55);
                break;
            case RANGE_18_19:
                setRM(weightValue * PERCENT_100 / PERCENT_50);
                break;
            case RANGE_20:
                setRM(weightValue * PERCENT_100 / PERCENT_45);
                break;
        }

        setWeightList.clear();

        loadSetList();
    }

}
