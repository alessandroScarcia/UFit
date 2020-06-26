package it.sms1920.spqs.ufit.launcher.userstats.testmaximalstrenght;

public class MaxStrenghtTestPresenter implements iMaxStrenghtTestFragment.Presenter {

    private iMaxStrenghtTestFragment.View view;

    public MaxStrenghtTestPresenter(iMaxStrenghtTestFragment.View view) {
        this.view = view;
    }

    @Override
    public int checkDataToAdapter(int checkIdRadioButton, String weightString) {
        //we check the validity of the input
        if (checkIdRadioButton != 0 && weightString.matches("\\d+(?:\\.\\d+)?")){
            //we round the value submitted and send to the presenter
            return (int) Float.parseFloat(weightString);
        }
        return -1;
    }

    @Override
    public void onSelectRepsClicked() {
            view.openDialog();
    }
}
