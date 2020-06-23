package it.sms1920.spqs.ufit.launcher.toolworkout;


import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
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
    private MediaPlayer countDownPlayer;
    private CountDownTimer countDownTimer;
    private TextView textViewCountDown;
    private Button btnStartStop;
    private Button btnReset;
    private ProgressIndicator timeProgressBar;
    private Button btnIncrementSeconds;
    private Button btnIncrementMinutes;
    private Button btnDecrementSeconds;
    private Button btnDecrementMinutes;
    private View view;

    private Button btnCountDownSound;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_timer, container, false);

        presenter = new TimerPresenter(this);
        countDownPlayer = MediaPlayer.create(view.getContext(), R.raw.countdown_sound);


        timeProgressBar= view.findViewById(R.id.progressIndicator);
        textViewCountDown = view.findViewById(R.id.timerValue);
        btnStartStop = view.findViewById(R.id.startPause);
        btnIncrementMinutes = view.findViewById(R.id.incrementMinutes);
        btnDecrementMinutes = view.findViewById(R.id.decrementMinutes);
        btnDecrementSeconds = view.findViewById(R.id.decrementSeconds);
        btnIncrementSeconds = view.findViewById(R.id.incrementSeconds);
        btnCountDownSound = view.findViewById(R.id.btnCountDownSound);
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



        btnCountDownSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.controllerSound();
            }
        });

        presenter.initializeTimer();

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        presenter.retriveIstance(this.getActivity());
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.saveIstance(this.getActivity());
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }



    public void setMaxProgressBar(int max){
        timeProgressBar.setMax(max);
    }

    @Override
    public void startCoundDownSound() {
        countDownPlayer.start();
    }

    @Override
    public void setProgressBar(int progressionProgressBar) {
        timeProgressBar.setProgress(progressionProgressBar);
    }

    @Override
    public void setTimeTextViewCountDown(String timeLeftFormatted) {
        textViewCountDown.setText(timeLeftFormatted);
    }

    @Override
    public void setVisibleBtnStartStop() {
        btnReset.setVisibility(View.INVISIBLE);
        btnStartStop.setVisibility(View.VISIBLE);
    }


    /**
     * Function that manage the start event of the timer
     */
    public void startTimer(long timeLeftInMillis) {
        //set the countDown and the relatives methods
         countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                presenter.updateCountDownText(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                presenter.finish();
                btnStartStop.setText("Start");
                btnStartStop.setVisibility(View.INVISIBLE);
                btnReset.setVisibility(View.VISIBLE);
                setVisibleBtnTime();

            }
        }.start();
        presenter.start();
        btnStartStop.setText("pause");

        //set the buttons of the timer settings invisible
        btnReset.setVisibility(View.INVISIBLE);

        btnDecrementMinutes.setVisibility(View.INVISIBLE);
        btnDecrementSeconds.setVisibility(View.INVISIBLE);
        btnIncrementSeconds.setVisibility(View.INVISIBLE);
        btnIncrementMinutes.setVisibility(View.INVISIBLE);

        //set the progress bar
        presenter.resetProgressionProgressBar();
        presenter.initProgressionProgressBar();
    }

    @Override
    public void pauseTimer() {
        btnStartStop.setText("Start");
        btnReset.setVisibility(View.VISIBLE);
        countDownPlayer.pause();
        countDownTimer.cancel();
    }

    @Override
    public void resumeCountDownPlayer(int value) {
        int currentMilliSecond = 5000 - value * 1000;
        countDownPlayer.seekTo(currentMilliSecond);
        countDownPlayer.start();
    }

    @Override
    public void resetPlayerCountDown() {
        countDownPlayer.seekTo(0);
    }


    @Override
    public void changeIconSoundSetting(boolean soundActive) {
        if(soundActive){
            btnCountDownSound.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_volume_up_24, 0, 0, 0);
        }else{
            btnCountDownSound.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_volume_off_24, 0, 0, 0);
        }
    }


    /**
     * Set visible the buttons settings of the timer
     */
    @Override
    public void setVisibleBtnTime(){
        btnDecrementMinutes.setVisibility(View.VISIBLE);
        btnDecrementSeconds.setVisibility(View.VISIBLE);
        btnIncrementSeconds.setVisibility(View.VISIBLE);
        btnIncrementMinutes.setVisibility(View.VISIBLE);
    }
}
