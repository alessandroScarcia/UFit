package it.sms1920.spqs.ufit.model;

import android.content.Context;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public class PicassoSingleton {
    private static Picasso built;

    public static void setPicassoInstance(Context context) {
        if (built == null) {
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.downloader(new OkHttp3Downloader(context, Integer.MAX_VALUE));
            built = builder.build();
            built.setIndicatorsEnabled(false);
            built.setLoggingEnabled(true);
            Picasso.setSingletonInstance(built);
        }
    }


}
