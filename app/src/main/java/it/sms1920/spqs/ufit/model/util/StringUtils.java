package it.sms1920.spqs.ufit.model.util;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.annotation.NonNull;

public class StringUtils {
    public static String capitalize(@NonNull String string) {
        if (string.isEmpty()) {
            return string;
        }

        if (string.length() == 1) {
            return string.toUpperCase();
        }

        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    public static boolean isEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
