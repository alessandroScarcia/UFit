package it.sms1920.spqs.ufit.contract;

public interface PlansContract {
    interface View {
        void showTrainerPlans();

        void showPersonalPlans();
    }

    interface Presenter {
        void clickedTrainerPlansButton();

        void clickedPersonalPlansButton();
    }
}
