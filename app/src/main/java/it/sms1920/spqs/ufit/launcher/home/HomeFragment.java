package it.sms1920.spqs.ufit.launcher.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import it.sms1920.spqs.ufit.launcher.assembliesreport.AssembliesReportActivity;
import it.sms1920.spqs.ufit.launcher.R;
import it.sms1920.spqs.ufit.launcher.toolworkout.TimerFragment;
import it.sms1920.spqs.ufit.launcher.traineradvice.AdviceList;
import it.sms1920.spqs.ufit.launcher.traineradvice.iAdvice;

public class HomeFragment extends Fragment implements HomeContract.View, iAdvice.View {
    private static final String TAG = HomeFragment.class.getCanonicalName();

    private HomeContract.Presenter presenter;

    private Intent webViewIntent;
    private TextView tvTitleAdvice;
    private TextView tvDescriptionAdvice;
    private final String URL_WEBSITE = "http://www.quatematico.com/Ufit";

    private FloatingActionButton fabAssembliesReport;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new HomePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        iAdvice.Presenter advicePresenter = new AdviceList(this);
        tvTitleAdvice = view.findViewById(R.id.tvTitleRandomAdvice);
        tvDescriptionAdvice = view.findViewById(R.id.tvDescriptionRandomAdvice);
        advicePresenter.getRandomAdvice();


//   TODO     Button btnTimer = view.findViewById(R.id.btnTimerDialog);
//        btnTimer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                openDialog();
//            }
//        });

        Button btnWebsite = view.findViewById(R.id.btnWebsite);

        webViewIntent = new Intent(Intent.ACTION_VIEW);
        webViewIntent.setData(Uri.parse(URL_WEBSITE));
        btnWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(webViewIntent);
            }
        });

        fabAssembliesReport = view.findViewById(R.id.fabAssembliesReport);
        fabAssembliesReport.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onAssembliesReportClicked();
            }
        });

        // Using the correct layout for this fragment
        return view;
    }

    @Override
    public void callNotifyDataSetChanged() {
    }

    @Override
    public void addNewAdvice(String title, String description) {

    }

    public void setRandomAdvice(String title, String description) {
        tvTitleAdvice.setText(title);
        tvDescriptionAdvice.setText(description);
    }

    public void openDialog() {
        HomeFragment.TimerDialog dialogBox = new HomeFragment.TimerDialog();
        dialogBox.setTargetFragment(this, 1);
        dialogBox.show(getParentFragmentManager(), "example dialog");
    }

    @Override
    public void startAssembliesReportActivity() {
        startActivity(new Intent(getContext(), AssembliesReportActivity.class));
    }

    public static class TimerDialog extends DialogFragment {
        public TimerDialog() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.layout_dialog_timer, container, false);
            getChildFragmentManager().beginTransaction().add(R.id.frame_container_timer, new TimerFragment()).commit();
            return v;
        }

    }


}
