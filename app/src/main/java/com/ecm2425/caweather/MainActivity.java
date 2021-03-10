package com.ecm2425.caweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView weatherResultsTextView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherResultsTextView = (TextView) findViewById(R.id.tv_weather_results_json);
        getWeatherQuery();
    }

    private void getWeatherQuery() {
        URL weatherApiUrl = WeatherAPI.buildURL();
        // Create a new GetWeatherTask and call its execute method, passing in the url to query
        new GetWeatherTask().execute(weatherApiUrl);
    }

    public class GetWeatherTask extends AsyncTask<URL, Void, String> {

        // Override the doInBackground method to perform the query. Return the results.
        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String weatherResults = null;
            try {
                weatherResults = WeatherAPI.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return weatherResults;
        }

        // Override onPostExecute to display the results in the TextView
        @Override
        protected void onPostExecute(String weatherResults) {
            if (weatherResults != null && !weatherResults.equals("")) {
                weatherResultParser weatherResultsJSON = new weatherResultParser(weatherResults);
                JSONObject current = weatherResultsJSON.getCurrent();
                String temp = weatherResultsJSON.getTemp(current);
                JSONObject currentWeather = weatherResultsJSON.getWeather(current);
                String description = weatherResultsJSON.getDescription(currentWeather);
                String out = "Temp: " + temp + " Description: " + description;
                weatherResultsTextView.setText(out);
            }
        }
    }
}