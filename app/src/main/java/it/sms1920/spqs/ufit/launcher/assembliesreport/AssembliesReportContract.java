package it.sms1920.spqs.ufit.launcher.assembliesreport;

import java.util.Map;

public interface AssembliesReportContract {
    interface View {

        void endActivity();

        void setSearchReportError(Presenter.AddressError addressEmpty);

        void setAssemblyPerceptionError(Presenter.AssemblyPerceptionError perceptionError);

        Map<String, Double> reverseGeocoding(String gymAddress);

        void showConnectionError();

        void showNoResultMsg();

        float getDistanceBetween(Double startLatitude, Double startLongitude, Double endLatitude, Double endLongitude);

        void showSearchResult(int assemblyPerception);

        boolean checkGpsPermissions();

        boolean checkGpsShowRequestPermissionRationale();

        void showGpsAccessReasons();

        void askGpsPermission();

        void showGpsNotGrantedMsg();

        int getPerception();

        double getCurrentLatitude();

        double getCurrentLongitude();

        void showSendReportMsg();
    }

    interface Presenter {
        void onBackPressed();

        void onSearchReportClicked(String gymAddress);

        void onSendReportClicked();

        void onReverseGeocodingError();

        void onNoAddressFound();

        void onLocationReady();

        void gpsPermissionsDenied();

        enum AddressError {
            ADDRESS_EMPTY,
            ADDRESS_NOT_VALID
        }

        enum AssemblyPerceptionError {
            PERCEPTION_EMPTY
        }

        enum CoordinatesFields {
            LATITUDE,
            LONGITUDE
        }

        interface DialogListener {
            void onPositiveButtonClicked();
        }
    }
}
