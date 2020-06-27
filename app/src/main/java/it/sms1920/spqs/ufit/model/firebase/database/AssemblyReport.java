package it.sms1920.spqs.ufit.model.firebase.database;

public class AssemblyReport {
    public static final String CHILD_NAME = "AssemblyReport";
    public static final String FIELD_LATITUDE = "latitude";
    public static final String FIELD_LONGITUDE = "longitude";
    public static final String FIELD_PERCEPTION = "perception";
    public static final String FIELD_DATETIME = "datetime";
    public static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm";

    private Double latitude;
    private Double longitude;
    private Integer perception;
    private String datetime;

    public AssemblyReport() {

    }

    public AssemblyReport(double latitude, double longitude, int perception, String datetime) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.perception = perception;
        this.datetime = datetime;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getPerception() {
        return perception;
    }

    public void setPerception(Integer perception) {
        this.perception = perception;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
