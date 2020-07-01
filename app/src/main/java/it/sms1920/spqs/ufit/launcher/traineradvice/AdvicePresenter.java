package it.sms1920.spqs.ufit.launcher.traineradvice;

public class AdvicePresenter implements AdviceContract.Presenter, AdviceActivity.AdviceDialog.AdviceDialogListener {
    private final AdviceContract.View view;

    public AdvicePresenter(AdviceContract.View view) {
        this.view = view;
    }

    @Override
    public void onFabButtonClicked() {
        view.openDialog();
    }

    @Override
    public void onBackPressed() {
        view.endActivity();
    }

    @Override
    public void onPositiveButtonClicked(String title, String description) {
        view.addNewAdvice(title, description);
    }
}
