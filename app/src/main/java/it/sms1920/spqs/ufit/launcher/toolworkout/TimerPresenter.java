package it.sms1920.spqs.ufit.launcher.toolworkout;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Locale;

public class TimerPresenter implements TimerContract.Presenter {
    private long initialTime;
    private final TimerContract.View view;
    public int progressionProgressBar = 0;
    private long timeLeftInMillis;

    private boolean timerRunning = false;

    private int minutes;
    private int seconds;

    private boolean soundActive = true;
    public TimerPresenter(TimerContract.View view) {
        this.view = view;
    }


    /**
     * Function used to calculate the milliseconds from minutes and seconds
     * @param minutes .
     * @param seconds .
     */
    public void setTime(int minutes, int seconds){
        timeLeftInMillis = (minutes * 1000) * 60 + seconds * 1000;
        initialTime = timeLeftInMillis;

    }

    /**
     * Calculate minutes and seconds from milliseconds
     * @param initialTimeMillis .
     */
    public void setTimeFromMillis(long initialTimeMillis){
        minutes = (int) (initialTimeMillis / 1000) / 60;
        try {
            seconds = (int) (initialTimeMillis / 1000) % 60;
        }catch (NumberFormatException e){
            seconds = 0;
        }
    }


    /**
     * Function that manage the start and pause event
     * @param running .
     */
    public void startPause(boolean running) {
        timerRunning = running;
        if(timerRunning){
            pauseTimer();
        }else {
            view.setMaxProgressBar(progressionProgressBar);

            //check if the user have setted the timer
            if(minutes == 0 && seconds == 0){
                view.showToast("You must set the timer first");
            }else{
                int initialSeconds = (int) ((initialTime / 1000) % 60);
                if(seconds <= 5 && initialSeconds >= 5) {
                    if(soundActive) {
                        view.resumeCountDownPlayer(seconds);
                    }
                }
                view.startTimer(timeLeftInMillis);
            }
        }
    }


//    /**
//     * Function useful if we have the seconds or minutes passed from another fragment
//     * @param seconds
//     * @param minutes
//     */
//    public void updateCountDown(int seconds, int minutes){
//        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
//        view.getTextViewCountDown().setText(timeLeftFormatted);
//        setProgressionProgressBar();
//    }


    /**
     * Function used to update the text of th TextView that show the  every seconds
     */
    public void updateCountDownText(long millisUntilFinished) {
        timeLeftInMillis = millisUntilFinished;
        setTimeFromMillis(timeLeftInMillis);

        startSoundCountDownEnd();

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        view.setTimeTextViewCountDown(timeLeftFormatted);

        decrementProgressionProgressBar();
    }

    private void startSoundCountDownEnd() {
        if(seconds == 5 && soundActive){
            view.startCoundDownSound();
//            view.showToast("avvio");
        }
    }


    /**
     * Function that manage the pause event
     */
    private void pauseTimer() {
        view.pauseTimer();
        timerRunning = false;
    }


    /**
     * Function used to manage the reset event
     */
    public void resetTimer() {
        setTimeFromMillis(initialTime);
        view.setVisibleBtnStartStop();

        //reset the time in the text view with the las value
        setTextViewTimer();

        view.setVisibleBtnTime();

        view.resetPlayerCountDown();

        //reset and initialize the progressbar
        resetProgressionProgressBar();
        initProgressionProgressBar();
    }

    @Override
    public void resetProgressionProgressBar(){
        progressionProgressBar = (int) (timeLeftInMillis / 1000);
    }

    /**
     * Function used to initialize the timer settings
     */
    @Override
    public void initializeTimer() {
        updateCountDownText(timeLeftInMillis);
        initProgressionProgressBar();
    }


    public boolean isTimerRunning() {
        return timerRunning;
    }

    @Override
    public void incrementMinutesTimer() {
        if (minutes < 99) {
            minutes++;
            setTextViewTimer();
        }
    }

    @Override
    public void decrementMinutesTimer() {
        if (minutes != 0){
            minutes--;
            setTextViewTimer();
        }
    }

    @Override
    public void decrementSecondsTimer() {
        if (seconds != 0) {
            seconds--;
            setTextViewTimer();
        }
    }

    @Override
    public void incrementSecondsTimer() {
        if (seconds < 59) {
            seconds++;
            setTextViewTimer();
        }else{
            minutes++;
            seconds = 0;
            setTextViewTimer();

        }
    }

    @Override
    public void finish() {
        timerRunning = false;
    }

    @Override
    public void start() {
        timerRunning = true;
    }

    @Override
    public void controllerSound() {
        soundActive = !soundActive;

        view.changeIconSoundSetting(soundActive);
    }

    @Override
    public void saveIstance(Context context) {
        SharedPreferences prefes = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefes.edit();
        editor.putLong("millisLeft", timeLeftInMillis);
        editor.putBoolean("timerRunning", timerRunning);
        editor.putBoolean("soundActive", soundActive);
        editor.putInt("progressionProgressBar", progressionProgressBar);

        editor.apply();
    }


    @Override
    public void retriveIstance(Context context) {
        SharedPreferences prefes = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);

        timeLeftInMillis = prefes.getLong("millisLeft", timeLeftInMillis);
        timerRunning = prefes.getBoolean("timerRunning", timerRunning);
        soundActive = prefes.getBoolean("soundActive", soundActive);
        progressionProgressBar = prefes.getInt("progressionProgressBar", progressionProgressBar);

        startPause(false);
    }


    /**
     * Initialize the textView of the timer
     */
    public void setTextViewTimer(){
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        view.setTimeTextViewCountDown(timeLeftFormatted);
        setTime(minutes,seconds);
    }


    /**
     * Initialize the progress bar
     */
    public void initProgressionProgressBar(){
        view.setMaxProgressBar(progressionProgressBar);
        view.setProgressBar(progressionProgressBar);
    }


    /**
     * Update the progress of the ProgressBar
     */
    public void decrementProgressionProgressBar(){
        progressionProgressBar--;
        view.setProgressBar(progressionProgressBar);
    }



}
