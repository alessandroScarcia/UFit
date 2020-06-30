package it.sms1920.spqs.ufit.launcher.traineradvice;

public class AdvicePresenter implements AdviceContract.Presenter{
    private final AdviceContract.View view;

    public AdvicePresenter(AdviceContract.View view) {
        this.view = view;
    }

}
