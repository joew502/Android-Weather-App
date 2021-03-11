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
    //final static String LAT = "50.7236";
    //final static String LON = "-3.5275";
    final static String UNITS = "metric";
    final static String APIKEY = "6f82e0a5c4ae28f19b131e7bba6fdedf";
    final static String EXCLUDE = "exclude";


    public static URL buildURL (String location) {
        String[] coords = getCoords(location);

        Uri builtUri = Uri.parse(WEATHER_BASE_API).buildUpon()
                .appendQueryParameter(PARAM_LAT, coords[0])
                .appendQueryParameter(PARAM_LON, coords[1])
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

    private static String[] getCoords(String location) {
        String lat;
        String lon;
        switch (location){
            case "London":
                lat = "51.5085";
                lon = "-0.1257";
                break;
            default:
                lat = "50.7236";
                lon = "-3.5275";
        }
        String[] coords = {lat, lon};
        return coords;
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
