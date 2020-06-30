package it.sms1920.spqs.ufit.launcher.toolworkout;


import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.material.progressindicator.ProgressIndicator;

public interface TimerContract {

    interface View{
        void setMaxProgressBar(int max);

        void showToast(String message);


        void startCoundDownSound();

        void setProgressBar(int progressionProgressBar);

        void setTimeTextViewCountDown(String timeLeftFormatted);

        void setVisibleBtnStartStop();

        void startTimer(long timeLeftInMillis);

        void pauseTimer();

        void resumeCountDownPlayer(int value);

        void resetPlayerCountDown();

        void changeIconSoundSetting(boolean soundActive);

        void setVisibleBtnTime();
    }

    interface Presenter{
        void initProgressionProgressBar();
        
        void resetProgressionProgressBar();

        void updateCountDownText(long millisUntilFinished);

//        void updateCountDown(int seconds, int minutes);

        void resetTimer();

        void initializeTimer();
        
        void startPause(boolean running);

        boolean isTimerRunning();

        void incrementMinutesTimer();

        void decrementMinutesTimer();

        void decrementSecondsTimer();

        void incrementSecondsTimer();

        void finish();

        void start();

        void controllerSound();

        void saveIstance(Context context);

        void retriveIstance(Context context);
    }
}
