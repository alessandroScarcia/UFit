package it.sms1920.spqs.ufit.launcher.userstats.strenght_test;


import android.util.Log;
import java.util.ArrayList;
import java.util.List;





public class StrenghtTestList implements iStrenghtTest.Presenter {
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
        newSet.setWeight(String.valueOf(RM * 90 /100));
        return newSet;
    }


    public Set weightPercent85() {
        Set newSet = new Set();
        newSet.setReps("5-4");
        newSet.setWeight(String.valueOf(RM * 85 /100));
        return newSet;
    }


    public Set weightPercent80() {
        Set newSet = new Set();
        newSet.setReps("7-6");
        newSet.setWeight(String.valueOf(RM * 80 /100));
        return newSet;
    }


    public Set weightPercent75() {
        Set newSet = new Set();
        newSet.setReps("9-8");
        newSet.setWeight(String.valueOf(RM * 75 /100));
        return newSet;
    }


    public Set weightPercent70() {
        Set newSet = new Set();
        newSet.setReps("11-10");
        newSet.setWeight(String.valueOf(RM * 70 /100));
        return newSet;
    }


    public Set weightPercent65() {
        Set newSet = new Set();
        newSet.setReps("13-12");
        newSet.setWeight(String.valueOf(RM * 65 /100));
        return newSet;
    }


    public Set weightPercent60() {
        Set newSet = new Set();
        newSet.setReps("15-14");
        newSet.setWeight(String.valueOf(RM * 60 /100));
        return newSet;
    }


    public Set weightPercent55() {
        Set newSet = new Set();
        newSet.setReps("17-16");
        newSet.setWeight(String.valueOf(RM * 55 /100));
        return newSet;
    }


    public Set weightPercent50() {
        Set newSet = new Set();
        newSet.setReps("19-18");
        newSet.setWeight(String.valueOf(RM * 50 /100));
        return newSet;
    }

    public Set weightPercent45() {
        Set newSet = new Set();
        newSet.setReps("20");
        newSet.setWeight(String.valueOf(RM * 45 /100));
        return newSet;
    }

    @Override
    public void calculateRM(int range, int weightValue) {
        switch (range){
            case RM_1:
                setRM(weightValue);
                break;
            case RANGE_2_3:
                setRM(weightValue * 100 / 90);
                break;
            case RANGE_4_5:
                setRM(weightValue * 100 / 85);
                break;
            case RANGE_6_7:
                setRM(weightValue * 100 / 80);
                break;
            case RANGE_8_9:
                setRM(weightValue * 100 / 75);
                break;
            case RANGE_10_11:
                setRM(weightValue * 100 / 70);
                break;
            case RANGE_12_13:
                setRM(weightValue * 100 / 65);
                break;
            case RANGE_14_15:
                setRM(weightValue * 100 / 60);
                break;
            case RANGE_16_17:
                setRM(weightValue * 100 / 55);
                break;
            case RANGE_18_19:
                setRM(weightValue * 100 / 50);
                break;
            case RANGE_20:
                setRM(weightValue * 100 / 45);
                break;
        }

        setWeightList.clear();

        loadSetList();
    }

}
