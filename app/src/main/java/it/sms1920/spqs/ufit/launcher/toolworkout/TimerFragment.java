package it.sms1920.spqs.ufit.launcher.toolworkout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.progressindicator.ProgressIndicator;

import it.sms1920.spqs.ufit.launcher.R;



public class TimerFragment extends Fragment implements iTimer.View{
    
    private static final String TAG = TimerFragment.class.getCanonicalName();
    private iTimer.Presenter presenter;
    
    
    private TextView textViewCountDown;
    private Button btnStartStop;
    private Button btnReset;
    private ProgressIndicator timeProgressBar;
    private Button btnIncrementSeconds;
    private Button btnIncrementMinutes;
    private Button btnDecrementSeconds;
    private Button btnDecrementMinutes;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_timer, container, false);

        presenter = new TimerPresenter(this);

        timeProgressBar= view.findViewById(R.id.progressIndicator);
        textViewCountDown = view.findViewById(R.id.timerValue);
        btnStartStop = view.findViewById(R.id.startPause);
        btnIncrementMinutes = view.findViewById(R.id.incrementMinutes);
        btnDecrementMinutes = view.findViewById(R.id.decrementMinutes);
        btnDecrementSeconds = view.findViewById(R.id.decrementSeconds);
        btnIncrementSeconds = view.findViewById(R.id.incrementSeconds);
        btnReset = view.findViewById(R.id.reset);


        btnStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.startPause(presenter.isTimerRunning());
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              presenter.resetTimer();
            }
        });


        btnIncrementMinutes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                presenter.incrementMinutesTimer();
            }
        });

        btnDecrementMinutes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                presenter.decrementMinutesTimer();
            }
        });

        btnDecrementSeconds.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                presenter.decrementSecondsTimer();
            }
        });

        btnIncrementSeconds.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                presenter.incrementSecondsTimer();
            }
        });



        presenter.initializeTimer();


        return view;
    }


    public TextView getTextViewCountDown(){
        return textViewCountDown;
    }

    public Button getBtnStartStop() {
        return btnStartStop;
    }

    public Button getBtnReset() {
        return btnReset;
    }

    public ProgressIndicator getTimeProgressBar(){
        return timeProgressBar;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


    public Button getBtnIncrementSeconds() {
        return btnIncrementSeconds;
    }

    public Button getBtnIncrementMinutes() {
        return btnIncrementMinutes;
    }

    public Button getBtnDecrementSeconds() {
        return btnDecrementSeconds;
    }

    public Button getBtnDecrementMinutes() {
        return btnDecrementMinutes;
    }
}
