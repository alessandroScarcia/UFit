package it.sms1920.spqs.ufit.launcher.assembliesreport;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import it.sms1920.spqs.ufit.model.firebase.auth.FirebaseAuthSingleton;
import it.sms1920.spqs.ufit.model.firebase.database.AssemblyReport;
import it.sms1920.spqs.ufit.model.firebase.database.FirebaseDbSingleton;

import static it.sms1920.spqs.ufit.launcher.assembliesreport.AssembliesReportContract.Presenter.CoordinatesFields.LATITUDE;

public class AssembliesReportPresenter implements AssembliesReportContract.Presenter, AssembliesReportContract.Presenter.DialogListener {
    private static final String TAG = AssembliesReportPresenter.class.getCanonicalName();

    private final AssembliesReportContract.View view;

    public AssembliesReportPresenter(AssembliesReportContract.View view) {
        this.view = view;
    }

    @Override
    public void onBackPressed() {
        view.endActivity();
    }

    @Override
    public void onSearchReportClicked(String gymAddress) {
        if (TextUtils.isEmpty(gymAddress)) {
            view.setSearchReportError(AddressError.ADDRESS_EMPTY);
        } else {
            final Map<String, Double> coordinates = view.reverseGeocoding(gymAddress);

            if (coordinates.isEmpty()) {
                return;
            }

            DatabaseReference assemblyReportRef =
                    FirebaseDbSingleton.getInstance().getReference(AssemblyReport.CHILD_NAME);

            assemblyReportRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d(TAG, "assemblyReportRef:onDataChange");
                    List<AssemblyReport> reportList = new ArrayList<>();

                    for (DataSnapshot child : snapshot.getChildren()) {
                        long diffInHours;
                        AssemblyReport report = child.getValue(AssemblyReport.class);
                        if (report != null) {
                            float d = view.getDistanceBetween(coordinates.get(LATITUDE.toString()),
                                    coordinates.get(LATITUDE.toString()),
                                    report.getLatitude(),
                                    report.getLongitude());

                            try {
                                SimpleDateFormat formatter = new SimpleDateFormat(AssemblyReport.DATETIME_FORMAT, Locale.getDefault());
                                Date reportDate = formatter.parse(report.getDatetime());

                                if (reportDate == null) {
                                    continue;
                                } else {
                                    long diffInMillis = Math.abs(System.currentTimeMillis() - reportDate.getTime());
                                    diffInHours = TimeUnit.HOURS.convert(diffInMillis, TimeUnit.MILLISECONDS);
                                }

                            } catch (ParseException e) {
                                continue;
                            }

                            if (d < 200 && diffInHours < 2) {
                                Log.d(TAG, "reportAdded");
                                reportList.add(report);
                            }
                        }
                    }

                    computeAssemblyState(reportList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w(TAG, "assemblyReportRef:onCancelled", error.toException());
                }
            });
        }
    }

    @Override
    public void onSendReportClicked() {
        boolean checkGpsPermission = view.checkGpsPermissions();

        Log.d(TAG, "onSendReportClicked: checkGpsPermission " + checkGpsPermission);
        if (!checkGpsPermission && view.checkGpsShowRequestPermissionRationale()) {
            view.showGpsAccessReasons();
        } else if (!checkGpsPermission) {
            view.askGpsPermission();
        }
    }

    private void computeAssemblyState(List<AssemblyReport> reportList) {
        if (reportList.isEmpty()) {
            view.showNoResultMsg();
        } else {
            int[] perceptionsCount = new int[4];
            int max = 0;
            int assemblyPerception = -1;

            for (AssemblyReport report : reportList) {
                perceptionsCount[report.getPerception()]++;
            }

            for (int i = 0; i < perceptionsCount.length; i++) {
                if (perceptionsCount[i] >= max) {
                    max = perceptionsCount[i];
                    assemblyPerception = i;
                }
            }

            view.showSearchResult(assemblyPerception);
        }
    }

    @Override
    public void onReverseGeocodingError() {
        view.showConnectionError();
    }

    @Override
    public void onNoAddressFound() {
        view.setSearchReportError(AddressError.ADDRESS_NOT_VALID);
    }

    @Override
    public void onPositiveButtonClicked() {
        view.askGpsPermission();
    }

    @Override
    public void onLocationReady() {
        int perception = view.getPerception();
        double latitude = view.getCurrentLatitude();
        double longitude = view.getCurrentLongitude();

        if (perception == -1) {
            view.setAssemblyPerceptionError(AssemblyPerceptionError.PERCEPTION_EMPTY);
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat(AssemblyReport.DATETIME_FORMAT, Locale.getDefault());
            String currentDate = formatter.format(new Date(System.currentTimeMillis()));

            FirebaseUser user = FirebaseAuthSingleton.getFirebaseAuth().getCurrentUser();
            if (user == null) {
                throw new IllegalStateException(TAG + "user reference is null. It should be at least logged as anonymous.");
            }

            DatabaseReference assemblyReportRef =
                    FirebaseDbSingleton
                            .getInstance()
                            .getReference(AssemblyReport.CHILD_NAME)
                            .child(user.getUid());

            assemblyReportRef.setValue(new AssemblyReport(latitude, longitude, perception, currentDate));

            view.showSendReportMsg();
        }
    }

    @Override
    public void gpsPermissionsDenied() {
        view.showGpsNotGrantedMsg();
    }

    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case 0:
                view.showSearchReports();
                break;
            case 1:
                view.showCreateReport();
                break;
            default:
                break;
        }
    }
}
