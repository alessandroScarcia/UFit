package it.sms1920.spqs.ufit;

public class StringUtils {
    public static String capitalize(String string) {
        if (string == null || string.isEmpty()) return string;

        if (string.length() == 1) return string.toUpperCase();

        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }
}
