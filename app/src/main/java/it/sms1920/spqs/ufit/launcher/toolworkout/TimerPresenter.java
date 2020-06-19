package it.sms1920.spqs.ufit.launcher.toolworkout;



import android.os.CountDownTimer;
import android.view.View;
import java.util.Locale;


public class TimerPresenter implements iTimer.Presenter {
    private long initialTime;
    private final iTimer.View view;
    public int progressionProgressBar = 0;
    private long timeLeftInMillis;
    private CountDownTimer countDownTimer;
    private boolean timerRunning = false;

    private int minutes;
    private int seconds;


    public TimerPresenter(iTimer.View view) {
        this.view = view;
    }


    /**
     * Function used to calculate the milliseconds from minutes and seconds
     * @param minutes
     * @param seconds
     */
    public void setTime(int minutes, int seconds){
        timeLeftInMillis = (minutes * 1000) * 60 + seconds * 1000;
        initialTime = timeLeftInMillis;

    }

    /**
     * Calculate minutes and seconds from milliseconds
     * @param initialTimeMillis
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
     * @param running
     */
    public void startPause(boolean running) {
        timerRunning = running;
        if(timerRunning){
            pauseTimer();
        }else {
            view.getTimeProgressBar().setMax(progressionProgressBar);

            //check if the user have setted the timer
            if(minutes == 0 && seconds == 0){
                view.showToast("You must set the timer first");
            }else{
                startTimer();
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
    public void updateCountDownText() {
        setTimeFromMillis(timeLeftInMillis);

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        view.getTextViewCountDown().setText(timeLeftFormatted);

        setProgressionProgressBar();
    }


    /**
     * Function that manage the pause event
     */
    private void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        view.getBtnStartStop().setText("Start");
        view.getBtnReset().setVisibility(View.VISIBLE);
    }


    /**
     * Function used to manage the reset event
     */
    public void resetTimer() {
        setTimeFromMillis(initialTime);
        view.getBtnReset().setVisibility(View.INVISIBLE);
        view.getBtnStartStop().setVisibility(View.VISIBLE);
        setVisibleBtnTime();

        //reset the time in the text view with the las value
        setTextViewTimer();

        //reset and initializwe the progressbar
        resetProgressionProgressBar();
        initProgressionProgressBar();
    }


    public void resetProgressionProgressBar(){
        progressionProgressBar = (int) (timeLeftInMillis / 1000);
    }

    /**
     * Function used to initialize the timer settings
     */
    @Override
    public void initializeTimer() {
        updateCountDownText();
        initProgressionProgressBar();
    }

    /**
     * Function that manage the start event of the timer
     */
    public void startTimer() {
        //set the countDown and the relatives methods
        countDownTimer =  new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                view.getBtnStartStop().setText("Start");
                view.getBtnStartStop().setVisibility(View.INVISIBLE);
                view.getBtnReset().setVisibility(View.VISIBLE);
                setVisibleBtnTime();

            }
        }.start();
        timerRunning = true;
        view.getBtnStartStop().setText("pause");

        //set the buttons of the timer settings invisible
        view.getBtnReset().setVisibility(View.INVISIBLE);
        view.getBtnDecrementMinutes().setVisibility(View.INVISIBLE);
        view.getBtnDecrementSeconds().setVisibility(View.INVISIBLE);
        view.getBtnIncrementSeconds().setVisibility(View.INVISIBLE);
        view.getBtnIncrementMinutes().setVisibility(View.INVISIBLE);

        //set the progress bar
        resetProgressionProgressBar();
        initProgressionProgressBar();
    }

    public boolean isTimerRunning() {
        return timerRunning;
    }

    @Override
    public void incrementMinutesTimer() {
        minutes++;
        setTextViewTimer();
    }

    @Override
    public void decrementMinutesTimer() {
        minutes--;
        setTextViewTimer();
    }

    @Override
    public void decrementSecondsTimer() {
        seconds--;
        setTextViewTimer();
    }

    @Override
    public void incrementSecondsTimer() {
        seconds++;
        setTextViewTimer();
    }


    /**
     * Initialize the textView of the timer
     */
    public void setTextViewTimer(){
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        view.getTextViewCountDown().setText(timeLeftFormatted);
        setTime(minutes,seconds);
    }


    /**
     * Initialize the progress bar
     */
    public void initProgressionProgressBar(){
        view.getTimeProgressBar().setMax(progressionProgressBar);
        view.getTimeProgressBar().setProgress(progressionProgressBar);
    }


    /**
     * Update the progress of the ProgressBar
     */
    public void setProgressionProgressBar(){
        progressionProgressBar--;
        view.getTimeProgressBar().setProgress(progressionProgressBar);
    }


    /**
     * Set visible the buttons settings of the timer
     */
    public void setVisibleBtnTime(){
        view.getBtnDecrementMinutes().setVisibility(View.VISIBLE);
        view.getBtnDecrementSeconds().setVisibility(View.VISIBLE);
        view.getBtnIncrementSeconds().setVisibility(View.VISIBLE);
        view.getBtnIncrementMinutes().setVisibility(View.VISIBLE);
    }
}
