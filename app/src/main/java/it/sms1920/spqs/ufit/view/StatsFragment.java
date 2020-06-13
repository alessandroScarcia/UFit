
//public class StatsFragment extends Fragment {
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        // Using the correct layout for this fragment
//        return inflater.inflate(R.layout.muscle_stats, container, false);
//    }
//}


package it.sms1920.spqs.ufit.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import it.sms1920.spqs.ufit.contract.iStatsFragment;
import it.sms1920.spqs.ufit.presenter.StatsPresenter;

import static java.sql.Types.NULL;


public class StatsFragment extends Fragment implements iStatsFragment.View, StatsDialog.ExampleDialogListener {
    private static final String TAG = StatsFragment.class.getCanonicalName();
    private iStatsFragment.Presenter presenter;
    private TabLayout tlStats;
    LinearLayout containerLayout;
    Context context;

    private TextView weight;
    private TextView fat;
    private TextView water;
    private TextView muscle;
    private TextView weightDate;
    private TextView fatDate;
    private TextView waterDate;
    private TextView muscleDate;
    private TextView BMI;
    private TextView FFMI;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_stats, container, false);

        //setting content of frame layout
        containerLayout = view.findViewById(R.id.container_stats);
        View child = getLayoutInflater().inflate(R.layout.general_stats, null);
        containerLayout.addView(child);

        //set date of the room inside the text views
        setViewsData(view);

        //set options of tab menu
        tlStats = view.findViewById(R.id.tlStats);
        tlStats.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, String.valueOf(tab.getPosition()));
                presenter.onTabSelectedAtPostition(tab.getPosition());

                //if the activity show the content of the first tab the data are set
                if (tab.getPosition() == 0) {
                    setViewsData(view);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                setViewsData(view);
            }
        });


        return view;
    }


    /**
     * Function used to show the dialog box set by the the class of StatsDialog
     *
     * @param textViewValue
     * @param textViewDate
     */
    public void openDialog(TextView textViewValue, TextView textViewDate) {
        StatsDialog dialogBox = new StatsDialog(textViewValue, textViewDate);
        dialogBox.setTargetFragment(this, 1);
        dialogBox.show(getFragmentManager(), "example dialog");
    }

    /**
     * Function to set the content of frame layout to the panoramic of the general stats
     */
    @Override
    public void showGeneralStats() {
        containerLayout.removeAllViewsInLayout();
        View child = getLayoutInflater().inflate(R.layout.general_stats, null);
        containerLayout.addView(child);
    }

    /**
     * Function to set the content of frame layout to the panoramic of single muscle stats
     */
    @Override
    public void showBodyStats() {
        containerLayout.removeAllViewsInLayout();
        View child = getLayoutInflater().inflate(R.layout.muscle_stats, null);
        containerLayout.addView(child);
    }


    /**
     * Function to update the data inserted in dialog box
     *
     * @param value
     * @param date
     * @param textViewValue
     * @param textViewDate
     */
    @Override
    public void applyTexts(float value, String date, TextView textViewValue, TextView textViewDate) {
        textViewValue.setText(String.valueOf(value));
        textViewDate.setText(date);

        if (textViewValue == weight) {
//            Toast.makeText(context, StatsPresenter.userStats.getIdUserStats() + " " + value + " " + date, Toast.LENGTH_SHORT).show();
            presenter.updateWeight(StatsPresenter.userStats.getIdUserStats(), value, date);

            //with the weight is possible calculate the BMI
            setBMITextView();
        } else if (textViewValue == fat) {
            presenter.updateFat(StatsPresenter.userStats.getIdUserStats(), value, date);

            //with the percentage of fat we can calculate the FFMI
            setFFMITextView();

        } else if (textViewValue == water) {
            presenter.updateWater(StatsPresenter.userStats.getIdUserStats(), value, date);
        } else if (textViewValue == muscle) {
            presenter.updateMuscle(StatsPresenter.userStats.getIdUserStats(), value, date);
        }

    }

    private void setFFMITextView() {
        String weightControlValue = weight.getText().toString();

        //check if weight is a number to calculate the FFMI(if the text view contains a value)
        if (weightControlValue.matches("\\d+(?:\\.\\d+)?")) {
            float FFMIValue = presenter.calculateFFMI(Float.parseFloat(weight.getText().toString()),
                    Float.parseFloat(fat.getText().toString()));
            FFMI.setText(String.valueOf(FFMIValue));
        }
    }

    private void setBMITextView() {
        float BMIValue = presenter.calculateBMI(Float.parseFloat(weight.getText().toString()));
        BMI.setText(String.valueOf(BMIValue));
    }


    public void callRemoveRecordStats(int id) {
        presenter.deleteRecordStats(id);
    }


    public void setViewsData(View view) {
        presenter = new StatsPresenter(this);
        context = getActivity();

        //setting database for the session
        presenter.setDatabase(context);

        //get data from database
        presenter.getData();

//      Toast.makeText(context, StatsPresenter.userStats.getIdUserStats() + " " + StatsPresenter.userStats.getWeight(), Toast.LENGTH_SHORT).show();

        //this check is useful for the first insert
        if (!presenter.checkExistUserStats()) {
            presenter.addNewUserStats();
        }


        weight = view.findViewById(R.id.weightInput);
        BMI = view.findViewById(R.id.bmiValue);
        FFMI = view.findViewById(R.id.ffmiValue);
        weightDate = view.findViewById(R.id.weightDateInput);


        //text view of weight show the dialog box
        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we pass to the dialog box the text view to update at the end of the insertion
                openDialog(weight, weightDate);
            }
        });

        fat = view.findViewById(R.id.fatInput);
        fatDate = view.findViewById(R.id.fatDateInput);
        fat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we pass to the dialog box the text view to update at the end of the insertion
                openDialog(fat, fatDate);
            }
        });

        muscle = view.findViewById(R.id.musclesInput);
        muscleDate = view.findViewById(R.id.musclesDateInput);
        muscle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we pass to the dialog box the text view to update at the end of the insertion
                openDialog(muscle, muscleDate);
            }
        });

        water = view.findViewById(R.id.waterInput);
        waterDate = view.findViewById(R.id.waterDateInput);
        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we pass to the dialog box the text view to update at the end of the insertion
                openDialog(water, waterDate);
            }
        });

        if (StatsPresenter.userStats.getWeight() != NULL) {
            weight.setText(String.valueOf(StatsPresenter.userStats.getWeight()));
            weightDate.setText(String.valueOf(StatsPresenter.userStats.getDateWeightDetection()));
            setBMITextView();
        }

        if (StatsPresenter.userStats.getFat() != NULL) {
            fat.setText(String.valueOf(StatsPresenter.userStats.getFat()));
            fatDate.setText(String.valueOf(StatsPresenter.userStats.getDateFatDetection()));
            setFFMITextView();
        }

        if (StatsPresenter.userStats.getWater() != NULL) {
            water.setText(String.valueOf(StatsPresenter.userStats.getWater()));
            waterDate.setText(String.valueOf(StatsPresenter.userStats.getDateWaterDetection()));
        }

        if (StatsPresenter.userStats.getMuscle() != NULL) {
            muscle.setText(String.valueOf(StatsPresenter.userStats.getMuscle()));
            muscleDate.setText(String.valueOf(StatsPresenter.userStats.getDateMuscleDetection()));
        }
    }


}
