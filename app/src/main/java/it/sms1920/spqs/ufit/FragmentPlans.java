package it.sms1920.spqs.ufit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentPlans extends Fragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Using the correct layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_plans, container, false);
        Button btn = myView.findViewById(R.id.btn);
        btn.setOnClickListener(this);

        return myView;
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent( getContext(), ActivityExercise.class));

        getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.idle);
    }
}
