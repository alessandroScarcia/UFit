
//public class StatsFragment extends Fragment {
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        // Using the correct layout for this fragment
//        return inflater.inflate(R.layout.muscle_stats, container, false);
//    }
//}


package it.sms1920.spqs.ufit.launcher.userstats;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

import it.sms1920.spqs.ufit.launcher.R;

import static java.sql.Types.NULL;


public class StatsFragment extends Fragment implements iStatsFragment.View, PopupMenu.OnMenuItemClickListener{
    private static final String TAG = StatsFragment.class.getCanonicalName();
    private static iStatsFragment.Presenter presenter;
    private TabLayout tlStats;
    LinearLayout containerLayout;
    Context context;

    //declaration of all the text view inside the 2 different tab content
    private static TextView weight;
    private static TextView fat;
    private static TextView water;
    private static TextView muscle;
    private TextView weightDate;
    private TextView fatDate;
    private TextView waterDate;
    private TextView muscleDate;
    private static TextView arm;
    private TextView armDate;
    private static TextView chest;
    private TextView chestDate;
    private static TextView waist;
    private TextView waistDate;
    private static TextView tight;
    private TextView tightDate;
    private static TextView calve;
    private TextView calveDate;

    public ImageButton imcHelp;
    public ImageButton ffmiHelp;
    
    private TextView BMI;
    private TextView FFMI;

    private TextView tvBMIStatus;
    private TextView tvFFMIStatus;


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
        context = getActivity();
        presenter = new StatsPresenter(this,context);

        setViewsDataGeneralStats(view);

        //set options of tab menu
        tlStats = view.findViewById(R.id.tlStats);
        tlStats.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, String.valueOf(tab.getPosition()));
                presenter.onTabSelectedAtPosition(tab.getPosition());

                //if the activity show the content of the first tab the data are set
                if (tab.getPosition() == 0) {
                    setViewsDataGeneralStats(view);
                }else if(tab.getPosition() == 1){
                    setViewsDataMuscleStats(view);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    setViewsDataGeneralStats(view);
                }else if(tab.getPosition() == 1){
                    setViewsDataMuscleStats(view);
                }
            }
        });



        imcHelp = view.findViewById(R.id.imcHelp);

        imcHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), v);
                popup.getMenuInflater().inflate(R.menu.popup_imc, popup.getMenu());
                popup.show();
            }
        });

        ffmiHelp = view.findViewById(R.id.ffmiHelp);
        ffmiHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), v);
                popup.getMenuInflater().inflate(R.menu.popup_ffmi, popup.getMenu());
                popup.show();
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
        View child = getLayoutInflater().inflate(R.layout.muscle_measurment, null);
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

    public static void applyTexts(float value, String date, TextView textViewValue, TextView textViewDate) {
        textViewValue.setText(String.valueOf(value));
        textViewDate.setText(date);

        if (textViewValue == weight) {
//            Toast.makeText(context, presenter.getUserStats().getIdUserStats() + " " + value + " " + date, Toast.LENGTH_SHORT).show();
            presenter.updateWeight(value, date);
        } else if (textViewValue == fat) {
            presenter.updateFat(value, date);
        } else if (textViewValue == water) {
            presenter.updateWater(value, date);
        } else if (textViewValue == muscle) {
            presenter.updateMuscle(value, date);
        }else if(textViewValue == arm){
            presenter.updateArm(value, date);
        }else if(textViewValue == chest){
            presenter.updateChest(value, date);
        }else if(textViewValue == waist){
            presenter.updateWaist(value, date);
        }else if(textViewValue == tight){
            presenter.updateTight(value, date);
        }else if(textViewValue == calve){
            presenter.updateCalve(value, date);
        }
    }

    public void setViewsDataGeneralStats(View view) {
//      Toast.makeText(context, presenter.getUserStats().getIdUserStats() + " " + presenter.getUserStats().getWeight(), Toast.LENGTH_SHORT).show();

        weight = view.findViewById(R.id.weightInput);
        BMI = view.findViewById(R.id.bmiValue);
        FFMI = view.findViewById(R.id.ffmiValue);
        weightDate = view.findViewById(R.id.weightDateInput);
        tvBMIStatus =  view.findViewById(R.id.tvBMIStatus);
        tvFFMIStatus = view.findViewById(R.id.tvFFMIStatus);

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

        presenter.setGeneralStats();
    }


    public void setViewsDataMuscleStats(View view) {
//      Toast.makeText(context, presenter.getUserStats().getIdUserStats() + " " + presenter.getUserStats().getWeight(), Toast.LENGTH_SHORT).show();

        chest = view.findViewById(R.id.chestInput);
        chestDate = view.findViewById(R.id.chestDateInput);


        //text view of weight show the dialog box
        chest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we pass to the dialog box the text view to update at the end of the insertion
                openDialog(chest, chestDate);
            }
        });

        arm = view.findViewById(R.id.bicepsInput);
        armDate = view.findViewById(R.id.bicepsDateInput);
        arm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we pass to the dialog box the text view to update at the end of the insertion
                openDialog(arm, armDate);
            }
        });

        waist = view.findViewById(R.id.waistInput);
        waistDate = view.findViewById(R.id.waistDateInput);
        waist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we pass to the dialog box the text view to update at the end of the insertion
                openDialog(waist, waistDate);
            }
        });

        tight = view.findViewById(R.id.tightInput);
        tightDate = view.findViewById(R.id.tightDateInput);
        tight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we pass to the dialog box the text view to update at the end of the insertion
                openDialog(tight, tightDate);
            }
        });


        calve = view.findViewById(R.id.calveInput);
        calveDate = view.findViewById(R.id.calveDateInput);
        calve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we pass to the dialog box the text view to update at the end of the insertion
                openDialog(calve, calveDate);
            }
        });

        presenter.setBodyStats();
    }

    public void setWeight(String weightValue) {
        weight.setText(weightValue);
    }

    public void setFat(String fatValue) {
        fat.setText(fatValue);
    }

    public void setWater(String waterValue) {
        water.setText(waterValue);    }

    public void setMuscle(String muscleValue) {
        muscle.setText(muscleValue);
    }

    public void setWeightDate(String weightDateValue) {
        weightDate.setText(weightDateValue);
    }

    public void setFatDate(String fatDateValue) {
        fatDate.setText(fatDateValue);
    }

    public void setWaterDate(String waterDateValue) {
        waterDate.setText(waterDateValue);
    }

    public void setMuscleDate(String muscleDateValue) {
        muscleDate.setText(muscleDateValue);
    }

    public void setArm(String armValue) {
        arm.setText(armValue);
    }

    public void setArmDate(String armDateValue) {
        armDate.setText(armDateValue);
    }

    public void setChest(String chestValue) {
        chest.setText(chestValue);
    }

    public void setChestDate(String chestDateValue) {
        chestDate.setText(chestDateValue);
    }

    public  void setWaist(String waistValue) {
        waist.setText(waistValue);
    }

    public void setWaistDate(String waistDateValue) {
        waistDate.setText(waistDateValue);
    }

    public  void setTight(String tightValue) {
        tight.setText(tightValue);
    }

    public void setTightDate(String tightDateValue) {
        tightDate.setText(tightDateValue);
    }

    public  void setCalve(String calveValue) {
        calve.setText(calveValue);
    }

    public void setCalveDate(String calveDateValue) {
        calveDate.setText(calveDateValue);
    }

    public void setBMI(String BMIValue) {
        BMI.setText(BMIValue);
    }

    public void setBMIStatus(String strBMIStatus) {
        tvBMIStatus.setText(strBMIStatus);
    }

    @Override
    public void setFFMIStatus(String strFFMIStatus) {
        tvFFMIStatus.setText(strFFMIStatus);
    }

    public void setFFMI(String FFMIValue) {
        FFMI.setText(FFMIValue);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    public static class StatsDialog extends AppCompatDialogFragment {

        //        private ExampleDialogListener listener;
        private EditText statsValueEditText;
        private EditText statsDateEditText;
        private DatePickerDialog datePickerDialog;
        public float statValue;
        public String statDate;

        int year;
        int mounth;
        int dayOfMounth;
        Calendar calendar;

        public TextView textViewValue;
        public TextView textViewDate;


        /**
         * Construct od the Stats Dialog
         * @param textViewValue textView that will contain the value of the parameter setted
         * @param textViewDate textView that will contain the date of the parameter setted
         */
        public StatsDialog(TextView textViewValue, TextView textViewDate) {
            this.textViewValue = textViewValue;
            this.textViewDate = textViewDate;
        }

        /**
         * When the dialog is created the layout show 2 editText inside it. One of them use datePicker
         * @param savedInstanceState
         * @return builder.create()
         */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.layout_dialog, null);

            builder.setView(view)
                    .setTitle("Inserisci Dati")
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try {
                                statValue = Float.parseFloat(statsValueEditText.getText().toString());
                            } catch (NumberFormatException e) {
//                            Snackbar.make(getContext(), R.string.error_value_message, LENGTH_SHORT)
//                                    .show();
                                statValue = 0;
                            }
                            statDate = statsDateEditText.getText().toString();
                            applyTexts(statValue, statDate, textViewValue, textViewDate);
                        }
                    });

            statsValueEditText = view.findViewById(R.id.stats_value);

            //setting options of DatePicker
            statsDateEditText = view.findViewById(R.id.stats_date);
            statsDateEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //the instance of calendar is necessary to set current date inside the DatePicker
                    calendar = Calendar.getInstance();
                    year = calendar.get(Calendar.YEAR);
                    mounth = calendar.get(Calendar.MONTH);
                    dayOfMounth = calendar.get(Calendar.DAY_OF_MONTH);

                    datePickerDialog = new DatePickerDialog(getTargetFragment().getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    statsDateEditText.setText(dayOfMonth + "/" + month + "/" + year);
                                }
                            }, year, mounth, dayOfMounth);
                    datePickerDialog.show();
                }
            });

            return builder.create();

        }
    }




}
