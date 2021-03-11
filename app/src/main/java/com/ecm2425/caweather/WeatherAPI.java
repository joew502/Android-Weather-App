package com.ecm2425.caweather;

import android.net.Uri;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class WeatherAPI {

    final static String WEATHER_BASE_API = "https://api.openweathermap.org/data/2.5/onecall";
    final static String PARAM_LAT = "lat";
    final static String PARAM_LON = "lon";
    final static String PARAM_UNITS = "units";
    final static String PARAM_APIKEY = "appid";
    final static String PARAM_EXCLUDE = "exclude";
    final static String LAT = "50.7236";
    final static String LON = "-3.5275";
    final static String UNITS = "metric";
    final static String APIKEY = "6f82e0a5c4ae28f19b131e7bba6fdedf";
    final static String EXCLUDE = "exclude";


    public static URL buildURL () {
        Uri builtUri = Uri.parse(WEATHER_BASE_API).buildUpon()
                .appendQueryParameter(PARAM_LAT, LAT)
                .appendQueryParameter(PARAM_LON, LON)
                .appendQueryParameter(PARAM_UNITS, UNITS)
                .appendQueryParameter(PARAM_APIKEY, APIKEY)
                .appendQueryParameter(PARAM_EXCLUDE, EXCLUDE)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
