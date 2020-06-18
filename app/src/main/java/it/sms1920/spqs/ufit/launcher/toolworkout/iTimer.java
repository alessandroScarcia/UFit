package it.sms1920.spqs.ufit.launcher.toolworkout;


import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.progressindicator.ProgressIndicator;

public interface iTimer {

    interface View{
        TextView getTextViewCountDown();

        Button getBtnStartStop();

        Button getBtnReset();

        ProgressIndicator getTimeProgressBar();

        void showToast(String message);

        Button getBtnIncrementSeconds();

        Button getBtnIncrementMinutes();

        Button getBtnDecrementSeconds();

        Button getBtnDecrementMinutes();
    }

    interface Presenter{

        void updateCountDownText();

//        void updateCountDown(int seconds, int minutes);

        void resetTimer();

        void initializeTimer();

        void startTimer();

        void startPause(boolean running);

        boolean isTimerRunning();

        void incrementMinutesTimer();

        void decrementMinutesTimer();

        void decrementSecondsTimer();

        void incrementSecondsTimer();
    }
}
