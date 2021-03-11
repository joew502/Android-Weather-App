package com.ecm2425.caweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView weatherResultsTextView;
    final String MYPREFS = "WeatherPreferences";
    SharedPreferences mySharedPreferences;
    SharedPreferences.Editor myEditor;
    private String location;
    //Button exeterButton;
    //Button londonButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySharedPreferences = getSharedPreferences(MYPREFS, 0);
        myEditor = mySharedPreferences.edit();

        if (mySharedPreferences != null && mySharedPreferences.contains("location")) {
            applySavedPreferences();
        } else {
            Toast.makeText(getApplicationContext(),
                    "No Preferences found", Toast.LENGTH_LONG).show();
            location = "Exeter";
        }

        weatherResultsTextView = (TextView) findViewById(R.id.tv_weather_results_json);
        getWeatherQuery();

        final Button hourlyButton = (Button) findViewById(R.id.hButton);
        hourlyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = MainActivity.this;
                Class destinationActivity = HourlyActivity.class;
                Intent intent = new Intent (context, destinationActivity);
                startActivity(intent);
            }
        });

        final Button dailyButton = (Button) findViewById(R.id.dButton);
        dailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = MainActivity.this;
                Class destinationActivity = DailyActivity.class;
                Intent intent = new Intent (context, destinationActivity);
                startActivity(intent);
            }
        });

        final Button exeterButton = (Button) findViewById(R.id.action_exeter);
        exeterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myEditor.clear();
                myEditor.putString("location", "Exeter");
                myEditor.commit();
                applySavedPreferences();
            }
        });

        final Button londonButton = (Button) findViewById(R.id.action_london);
        londonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myEditor.clear();
                myEditor.putString("location", "London");
                myEditor.commit();
                applySavedPreferences();
            }
        });

        /*exeterButton = (Button) findViewById(R.id.action_exeter);
        exeterButton.setOnClickListener((View.OnClickListener) this);
        londonButton = (Button) findViewById(R.id.action_london);
        londonButton.setOnClickListener(this);*/

    }

    /*public void onClick(View v) {
        myEditor.clear();

        // what button has been clicked?
        if (v.getId() == exeterButton.getId()) {
            myEditor.putString("location", "Exeter");
        } else { // case londonButton
            myEditor.putString("location", "London");
        }
        myEditor.commit();
        applySavedPreferences();
    }*/

    private void applySavedPreferences() {
        String locationSP = mySharedPreferences.getString("location","Exeter");
        String msg = "location " + locationSP;
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        location = locationSP;
        getWeatherQuery();
    }

    private void getWeatherQuery() {
        URL weatherApiUrl = WeatherAPI.buildURL(location);
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
                String out = weatherResultsJSON.currentWeatherStr();
                weatherResultsTextView.setText(out);
            }
        }
    }

    public void onClickLocationExeter(View v) {
        myEditor.clear();
        myEditor.putString("location", "Exeter");
        myEditor.commit();
        applySavedPreferences();
    }

    public void onClickLocationLondon(View v) {
        myEditor.clear();
        myEditor.putString("location", "London");
        myEditor.commit();
        applySavedPreferences();
    }

    public void onClickMoreWeather(View v) {
        String urlAsString = "https://openweathermap.org/city/2649808";
        openWebPage(urlAsString);
    }

    private void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_exeter:
                // do something
                return true;
            case R.id.action_london:
                // do something
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}