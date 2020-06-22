package it.sms1920.spqs.ufit.launcher.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

import it.sms1920.spqs.ufit.launcher.R;
import it.sms1920.spqs.ufit.launcher.toolworkout.TimerFragment;
import it.sms1920.spqs.ufit.launcher.traineradvice.AdviceAdapter;
import it.sms1920.spqs.ufit.launcher.traineradvice.AdviceFragment;
import it.sms1920.spqs.ufit.launcher.traineradvice.AdviceList;
import it.sms1920.spqs.ufit.launcher.traineradvice.AdvicePresenter;
import it.sms1920.spqs.ufit.launcher.traineradvice.iAdvice;
import it.sms1920.spqs.ufit.launcher.userprofile.show.ProfileFragment;
import it.sms1920.spqs.ufit.launcher.userstats.StatsFragment;
import it.sms1920.spqs.ufit.model.firebase.database.Advice;


public class HomeFragment extends Fragment implements iAdvice.View {
    private static final String TAG = AdviceList.class.getCanonicalName();
    Button btnWebsite;
    Intent webViewIntent;
    private iAdvice.Presenter presenter;
    TextView tvTitleAdvice;
    TextView tvDescriptionAdvice;
    Button btnTimer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        presenter = new AdviceList(this);
        tvTitleAdvice = view.findViewById(R.id.tvTitleRandomAdvice);
        tvDescriptionAdvice = view.findViewById(R.id.tvDescriptionRandomAdvice);
        presenter.getRandomAdvice();

        btnTimer = view.findViewById(R.id.btnTimerDialog);
        btnTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialog();
            }
        });

        btnWebsite = view.findViewById(R.id.btnWebsite);
        webViewIntent = new Intent(getActivity(), WebViewActivity.class);
        btnWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(webViewIntent);
            }
        });

        // Using the correct layout for this fragment
        return view;
    }

    @Override
    public void callNotifyDataSetChanged() {
    }

    @Override
    public void addNewAdvice(String title, String description) {

    }

    public void setRandomAdvice(String title, String description){
        tvTitleAdvice.setText(title);
        tvDescriptionAdvice.setText(description);
    }

    public void openDialog() {
        HomeFragment.StatsDialog dialogBox = new HomeFragment.StatsDialog();
        dialogBox.setTargetFragment(this, 1);
        dialogBox.show(getFragmentManager(), "example dialog");
    }

    public static class StatsDialog extends DialogFragment {
        public StatsDialog() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.layout_dialog_timer, container, false);
            getChildFragmentManager().beginTransaction().add(R.id.frame_container_timer, new TimerFragment()).commit();
            return v;
        }

    }


}
