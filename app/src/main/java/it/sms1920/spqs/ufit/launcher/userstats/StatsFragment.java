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
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;


import it.sms1920.spqs.ufit.launcher.R;




public class StatsFragment extends Fragment implements StatsContract.View, PopupMenu.OnMenuItemClickListener {
    private static final String TAG = StatsFragment.class.getCanonicalName();
    private static StatsContract.Presenter presenter;
    private TabLayout tlStats;
    LinearLayout containerLayout;
    Context context;

    private LinearLayout cardWeight;
    private LinearLayout cardFat;
    private LinearLayout cardWater;
    private LinearLayout cardMuscle;
    private LinearLayout cardHeight;

    private CardView cardArm;
    private CardView cardChest;
    private CardView cardWaist;
    private CardView cardTight;
    private CardView cardCalve;

    //declaration of all the text view inside the 2 different tab content
    @SuppressLint("StaticFieldLeak")
    private static TextView weight;
    @SuppressLint("StaticFieldLeak")
    private static TextView fat;
    @SuppressLint("StaticFieldLeak")
    private static TextView water;
    @SuppressLint("StaticFieldLeak")
    private static TextView muscle;
    private TextView weightDate;
    private TextView fatDate;
    private TextView waterDate;
    private TextView muscleDate;
    @SuppressLint("StaticFieldLeak")
    private static TextView arm;
    private TextView armDate;
    @SuppressLint("StaticFieldLeak")
    private static TextView chest;
    private TextView chestDate;
    @SuppressLint("StaticFieldLeak")
    private static TextView waist;
    private TextView waistDate;
    @SuppressLint("StaticFieldLeak")
    private static TextView tight;
    private TextView tightDate;
    @SuppressLint("StaticFieldLeak")
    private static TextView calve;
    private TextView calveDate;

    private TextView heightDate;
    private static TextView height;

    public MaterialButton imcHelp;
    public MaterialButton ffmiHelp;

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
        getLayoutInflater().inflate(R.layout.fragment_stats_general, containerLayout);

        context = getActivity();
        presenter = new StatsPresenter(this, context);

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
                } else if (tab.getPosition() == 1) {
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
                } else if (tab.getPosition() == 1) {
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

        Toast.makeText(context, R.string.click_to_insert_values, Toast.LENGTH_LONG).show();

        return view;
    }


    /**
     * Function used to show the dialog box set by the the class of StatsDialog
     *
     * @param textViewValue textView that call the dialog and will receive the value
     * @param textViewDate  textView that call the dialog and will receive the date
     */
    public void openDialog(TextView textViewValue, TextView textViewDate) {
        StatsDialog dialogBox = new StatsDialog(textViewValue, textViewDate);
        dialogBox.setTargetFragment(this, 1);
        if (getFragmentManager() != null) {
            dialogBox.show(getFragmentManager(), "example dialog");
        }
    }

    /**
     * Function to set the content of frame layout to the panoramic of the general stats
     */
    @Override
    public void showGeneralStats() {
        containerLayout.removeAllViewsInLayout();
        getLayoutInflater().inflate(R.layout.fragment_stats_general, containerLayout);

    }

    /**
     * Function to set the content of frame layout to the panoramic of single muscle stats
     */
    @Override
    public void showBodyStats() {
        containerLayout.removeAllViewsInLayout();
        getLayoutInflater().inflate(R.layout.fragment_stats_muscles, containerLayout);

    }


    /**
     * Function to update the data inserted in dialog box
     *
     * @param value         value from dialog
     * @param date          date from dialog
     * @param textViewValue textView to edit with the value
     * @param textViewDate  textView to edit with the date
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
        } else if (textViewValue == arm) {
            presenter.updateArm(value, date);
        } else if (textViewValue == chest) {
            presenter.updateChest(value, date);
        } else if (textViewValue == waist) {
            presenter.updateWaist(value, date);
        } else if (textViewValue == tight) {
            presenter.updateTight(value, date);
        } else if (textViewValue == calve) {
            presenter.updateCalve(value, date);
        }else if (textViewValue == height) {
            presenter.updateHeight(value, date);
        }
    }

    public void setViewsDataGeneralStats(View view) {
//      Toast.makeText(context, presenter.getUserStats().getIdUserStats() + " " + presenter.getUserStats().getWeight(), Toast.LENGTH_SHORT).show();

        BMI = view.findViewById(R.id.bmiValue);
        FFMI = view.findViewById(R.id.ffmiValue);
        tvBMIStatus = view.findViewById(R.id.tvBMIStatus);
        tvFFMIStatus = view.findViewById(R.id.tvFFMIStatus);


        weight = view.findViewById(R.id.weightInput);
        weightDate = view.findViewById(R.id.weightDateInput);
        cardWeight = view.findViewById(R.id.cardWeight);

        cardWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we pass to the dialog box the text view to update at the end of the insertion
                openDialog(weight, weightDate);
            }
        });


        fat = view.findViewById(R.id.fatInput);
        fatDate = view.findViewById(R.id.fatDateInput);
        cardFat = view.findViewById(R.id.cardFat);
        cardFat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we pass to the dialog box the text view to update at the end of the insertion
                openDialog(fat, fatDate);
            }
        });

        muscle = view.findViewById(R.id.musclesInput);
        muscleDate = view.findViewById(R.id.musclesDateInput);
        cardMuscle = view.findViewById(R.id.cardMuscle);
        cardMuscle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we pass to the dialog box the text view to update at the end of the insertion
                openDialog(muscle, muscleDate);
            }
        });

        water = view.findViewById(R.id.waterInput);
        waterDate = view.findViewById(R.id.waterDateInput);
        cardWater = view.findViewById(R.id.cardWater);
        cardWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we pass to the dialog box the text view to update at the end of the insertion
                openDialog(water, waterDate);
            }
        });


        height = view.findViewById(R.id.heightInput);
        heightDate = view.findViewById(R.id.heightDateInput);
        cardHeight = view.findViewById(R.id.cardHeight);
        cardHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we pass to the dialog box the text view to update at the end of the insertion
                openDialog(height, heightDate);
            }
        });

        presenter.setGeneralStats();
    }


    public void setViewsDataMuscleStats(View view) {
//      Toast.makeText(context, presenter.getUserStats().getIdUserStats() + " " + presenter.getUserStats().getWeight(), Toast.LENGTH_SHORT).show();

        chest = view.findViewById(R.id.chestInput);
        chestDate = view.findViewById(R.id.chestDateInput);
        cardChest = view.findViewById(R.id.cardChest);

        //text view of weight show the dialog box
        cardChest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we pass to the dialog box the text view to update at the end of the insertion
                openDialog(chest, chestDate);
            }
        });

        arm = view.findViewById(R.id.bicepsInput);
        armDate = view.findViewById(R.id.bicepsDateInput);
        cardArm = view.findViewById(R.id.cardArm);
        cardArm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we pass to the dialog box the text view to update at the end of the insertion
                openDialog(arm, armDate);
            }
        });

        waist = view.findViewById(R.id.waistInput);
        waistDate = view.findViewById(R.id.waistDateInput);
        cardWaist = view.findViewById(R.id.cardWaist);
        cardWaist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we pass to the dialog box the text view to update at the end of the insertion
                openDialog(waist, waistDate);
            }
        });

        tight = view.findViewById(R.id.tightInput);
        tightDate = view.findViewById(R.id.tightDateInput);
        cardTight = view.findViewById(R.id.cardTight);
        cardTight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we pass to the dialog box the text view to update at the end of the insertion
                openDialog(tight, tightDate);
            }
        });


        calve = view.findViewById(R.id.calveInput);
        calveDate = view.findViewById(R.id.calveDateInput);
        cardCalve = view.findViewById(R.id.cardCalve);
        cardCalve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we pass to the dialog box the text view to update at the end of the insertion
                openDialog(calve, calveDate);
            }
        });

        presenter.setBodyStats();
    }

    public void setHeight(String heightValue){
        height.setText(heightValue);
    }

    public void setHeightDate(String heightDateValue){
        heightDate.setText(heightDateValue);
    }

    public void setWeight(String weightValue) {
        weight.setText(weightValue);
    }

    public void setFat(String fatValue) {
        fat.setText(fatValue);
    }

    public void setWater(String waterValue) {
        water.setText(waterValue);
    }

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

    public void setWaist(String waistValue) {
        waist.setText(waistValue);
    }

    public void setWaistDate(String waistDateValue) {
        waistDate.setText(waistDateValue);
    }

    public void setTight(String tightValue) {
        tight.setText(tightValue);
    }

    public void setTightDate(String tightDateValue) {
        tightDate.setText(tightDateValue);
    }

    public void setCalve(String calveValue) {
        calve.setText(calveValue);
    }

    public void setCalveDate(String calveDateValue) {
        calveDate.setText(calveDateValue);
    }

    public void setBMI(String BMIValue) {
        BMI.setText(BMIValue);
    }

    public void setBMIStatus(float BMIValue) {
        String strBMIStatus;
        if (BMIValue < 16.5) {
            strBMIStatus = getString(R.string.severe_low_weight);
        } else if (BMIValue >= 16.5 && BMIValue <= 18.4) {
            strBMIStatus = getString(R.string.low_weight);
        } else if (BMIValue >= 18.5 && BMIValue <= 24.9) {
            strBMIStatus = getString(R.string.normal);
        } else if (BMIValue >= 25 && BMIValue <= 30) {
            strBMIStatus = getString(R.string.overweight);
        } else if (BMIValue >= 30.1 && BMIValue <= 34.9) {
            strBMIStatus = getString(R.string.obesity_first_level);
        } else if (BMIValue >= 35 && BMIValue <= 40) {
            strBMIStatus = getString(R.string.obesity_second_level);
        } else {
            strBMIStatus = getString(R.string.obesity_third_level);
        }
        tvBMIStatus.setText(strBMIStatus);
    }

    @Override
    public void setFFMIStatus(float FFMIValue) {
        String strFFMIStatus;
        if (FFMIValue < 17) {
            strFFMIStatus = getString(R.string.ffmi_below_average);
        } else if (FFMIValue >= 17 && FFMIValue <= 19) {
            strFFMIStatus = getString(R.string.ffmi_average);
        } else if (FFMIValue >= 19 && FFMIValue < 22) {
            strFFMIStatus = getString(R.string.ffmi_above_average);
        } else if (FFMIValue == 22) {
            strFFMIStatus = getString(R.string.ffmi_excellent);
        } else if (FFMIValue > 22 && FFMIValue <= 25) {
            strFFMIStatus = getString(R.string.ffmi_superior);
        } else if (FFMIValue >= 25 && FFMIValue <= 27) {
            strFFMIStatus = getString(R.string.ffmi_sospicius);
        } else {
            strFFMIStatus = getString(R.string.ffmi_not_natural);
        }
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
         *
         * @param textViewValue textView that will contain the value of the parameter setted
         * @param textViewDate  textView that will contain the date of the parameter setted
         */
        public StatsDialog(TextView textViewValue, TextView textViewDate) {
            this.textViewValue = textViewValue;
            this.textViewDate = textViewDate;
        }

        /**
         * When the dialog is created the layout show 2 editText inside it. One of them use datePicker
         *
         * @param savedInstanceState istance of dialog
         * @return builder.create()
         */
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.fragment_insert_stats_value_dialog, null);

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
                    if (getTargetFragment() != null && getTargetFragment().getContext() != null) {
                        datePickerDialog = new DatePickerDialog(getTargetFragment().getContext(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        month++;
                                        statsDateEditText.setText(dayOfMonth + "/" + month + "/" + year);
                                    }
                                }, year, mounth, dayOfMounth);
                        datePickerDialog.show();
                    }

                }
            });

            return builder.create();

        }
    }

}