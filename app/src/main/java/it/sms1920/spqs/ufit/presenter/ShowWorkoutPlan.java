package it.sms1920.spqs.ufit.presenter;

import it.sms1920.spqs.ufit.contract.iShowWorkoutPlan;

public class ShowWorkoutPlan implements iShowWorkoutPlan.Presenter {
    private iShowWorkoutPlan.View view;

    public ShowWorkoutPlan(iShowWorkoutPlan.View view) {
        this.view = view;
    }


}
