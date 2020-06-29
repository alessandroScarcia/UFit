package it.sms1920.spqs.ufit.launcher.assembliesreport;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.sms1920.spqs.ufit.launcher.R;

import static it.sms1920.spqs.ufit.launcher.assembliesreport.AssembliesReportContract.Presenter.CoordinatesFields.LATITUDE;
import static it.sms1920.spqs.ufit.launcher.assembliesreport.AssembliesReportContract.Presenter.CoordinatesFields.LONGITUDE;

public class AssembliesReportActivity extends AppCompatActivity implements AssembliesReportContract.View {
    private static final String TAG = AssembliesReportActivity.class.getCanonicalName();
    private static final int RC_GPS_PERMISSION = 1727;
    private AssembliesReportContract.Presenter presenter;
    private FusedLocationProviderClient fusedLocationClient;
    private double currentLatitude;
    private double currentLongitude;

    private TabLayout tlAssembliesReport;

    private LinearLayout llSearchReports;
    private LinearLayout llCreateReport;

    private TextInputLayout etlGymAddress;
    private TextInputEditText etGymAddress;

    private RadioGroup rgAssemblyPerception;

    private Button btnSearchReports;
    private Button btnSendReport;

    private TextView tvAssembliesReportDesc;
    private TextView tvAssemblyReportCreateMsg;

    private ArrayAdapter<CharSequence> assemblyPerceptionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assemblies_report);

        // setup toolbar
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.assemblies_report));
        toolbar.setTitleTextColor(getColor(R.color.colorWhite));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBackPressed();
            }
        });

        // setup views references
        tlAssembliesReport = findViewById(R.id.tlAssembliesReport);
        llSearchReports = findViewById(R.id.llSearchReports);
        llCreateReport = findViewById(R.id.llCreateReport);
        etlGymAddress = findViewById(R.id.etlGymAddress);
        etGymAddress = findViewById(R.id.etGymAddress);
        rgAssemblyPerception = findViewById(R.id.rgAssemblyPerception);
        btnSearchReports = findViewById(R.id.btnSearchReports);
        btnSendReport = findViewById(R.id.btnSendReport);
        tvAssembliesReportDesc = findViewById(R.id.tvAssembliesReportDesc);
        tvAssemblyReportCreateMsg = findViewById(R.id.tvAssemblyReportCreateMsg);

        tlAssembliesReport.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                presenter.onTabSelected(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        btnSearchReports.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etGymAddress.getText() != null) {
                    presenter.onSearchReportClicked(etGymAddress.getText().toString());
                }
            }
        });

        btnSendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSendReportClicked();
            }
        });


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        presenter = new AssembliesReportPresenter(this);
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    public void endActivity() {
        finish();
    }

    @Override
    public void setSearchReportError(AssembliesReportContract.Presenter.AddressError addressEmpty) {
        switch (addressEmpty) {
            case ADDRESS_EMPTY:
                etlGymAddress.setError(getString(R.string.assemblies_report_address_empty));
                break;
            case ADDRESS_NOT_VALID:
                etlGymAddress.setError(getString(R.string.assemblies_report_address_not_valid));
                break;
            default:
                break;
        }

        tvAssembliesReportDesc.setText(getString(R.string.assemblies_report_desc));
    }

    @Override
    public void setAssemblyPerceptionError(AssembliesReportContract.Presenter.AssemblyPerceptionError perceptionError) {
        if (perceptionError == AssembliesReportContract.Presenter.AssemblyPerceptionError.PERCEPTION_EMPTY) {
            btnSearchReports.setError(getString(R.string.assemblies_report_perception_empty));
        }
    }

    @Override
    public Map<String, Double> reverseGeocoding(String gymAddress) {
        Map<String, Double> coordinates = new HashMap<>();

        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addressList = geocoder.getFromLocationName(gymAddress, 1);
            if (addressList.size() != 1) {
                throw new IllegalArgumentException();
            } else {
                Address address = addressList.get(0);
                Log.d(TAG, address.toString());
                double latitude = address.getLatitude();
                double longitude = address.getLongitude();

                coordinates.put(LATITUDE.toString(), latitude);
                coordinates.put(LONGITUDE.toString(), longitude);

            }
        } catch (IOException e) {
            presenter.onReverseGeocodingError();
            return new HashMap<>();
        } catch (IllegalArgumentException e) {
            presenter.onNoAddressFound();
            return new HashMap<>();
        }

        return coordinates;
    }

    @Override
    public void showConnectionError() {
        tvAssembliesReportDesc.setText(getString(R.string.assemblies_report_connection_error));
    }

    @Override
    public void showNoResultMsg() {
        tvAssembliesReportDesc.setText(getString(R.string.assemblies_report_no_result));
    }

    @Override
    public float getDistanceBetween(Double startLatitude, Double startLongitude, Double endLatitude, Double endLongitude) {
        float pk = (float) (180.f / Math.PI);

        float a1 = (float) (startLatitude / pk);
        float a2 = (float) (startLongitude / pk);
        float b1 = (float) (startLatitude / pk);
        float b2 = (float) (startLongitude / pk);

        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);

        return (float) (6366000 * tt);
    }

    @Override
    public void showSearchResult(int assemblyPerception) {
        int stringId;
        switch (assemblyPerception) {
            case 1:
                stringId = R.string.assembly_perception_slightly_crowded;
                break;
            case 2:
                stringId = R.string.assembly_perception_crowded;
                break;
            case 3:
                stringId = R.string.assembly_perception_really_crowded;
                break;
            default:
                stringId = R.string.assembly_perception_not_crowded;
                break;
        }

        tvAssembliesReportDesc.setText(getString(R.string.assemblies_report_search_result, getString(stringId)));
    }

    @Override
    public boolean checkGpsPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful() && task.getResult() != null) {
                        Location location = task.getResult();
                        currentLatitude = location.getLatitude();
                        currentLongitude = location.getLongitude();
                        presenter.onLocationReady();
                    }
                }
            });

            return true;
        }

        return false;
    }

    @Override
    public boolean checkGpsShowRequestPermissionRationale() {
        return shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @Override
    public void showGpsAccessReasons() {
        GpsAccessReasonsDialog gpsAccessReasonsDialog = new GpsAccessReasonsDialog((AssembliesReportContract.Presenter.DialogListener) presenter);
        gpsAccessReasonsDialog.show(getSupportFragmentManager(), null);
    }

    @Override
    public void askGpsPermission() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, RC_GPS_PERMISSION);
    }

    @Override
    public void showGpsNotGrantedMsg() {
        tvAssemblyReportCreateMsg.setText(getString(R.string.assemblies_report_gps_access_reasons));
        tvAssemblyReportCreateMsg.setVisibility(View.VISIBLE);
    }

    @Override
    public int getPerception() {
        int perception = -1;
        int selectedRadioButtonId = rgAssemblyPerception.getCheckedRadioButtonId();

        if (selectedRadioButtonId == R.id.rbNotCrowded) {
            perception = 0;
        } else if (selectedRadioButtonId == R.id.rbSlightlyCrowded) {
            perception = 1;
        } else if (selectedRadioButtonId == R.id.rbCrowded) {
            perception = 2;
        } else if (selectedRadioButtonId == R.id.rbReallyCrowded) {
            perception = 3;
        }

        return perception;
    }

    @Override
    public double getCurrentLatitude() {
        return currentLatitude;
    }

    @Override
    public double getCurrentLongitude() {
        return currentLongitude;
    }

    @Override
    public void showSendReportMsg() {
        tvAssemblyReportCreateMsg.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSearchReports() {
        if (llSearchReports.getVisibility() != View.VISIBLE) {
            llSearchReports.setVisibility(View.VISIBLE);
            llCreateReport.setVisibility(View.GONE);
        }
    }

    @Override
    public void showCreateReport() {
        if (llCreateReport.getVisibility() != View.VISIBLE) {
            llCreateReport.setVisibility(View.VISIBLE);
            llSearchReports.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RC_GPS_PERMISSION) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.onSendReportClicked();
            } else {
                presenter.gpsPermissionsDenied();
            }
        }
    }

    public static class GpsAccessReasonsDialog extends DialogFragment {
        private static final String TAG = GpsAccessReasonsDialog.class.getCanonicalName();

        private AssembliesReportContract.Presenter.DialogListener listener;

        public GpsAccessReasonsDialog(AssembliesReportContract.Presenter.DialogListener listener) {
            this.listener = listener;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.assemblies_report_gps_access_reasons)
                    .setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listener.onPositiveButtonClicked();
                        }
                    })
                    .setNegativeButton(R.string.dialog_negative_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            return builder.create();
        }
    }
}