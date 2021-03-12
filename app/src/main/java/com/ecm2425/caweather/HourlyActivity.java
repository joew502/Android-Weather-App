package com.ecm2425.caweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class HourlyActivity extends AppCompatActivity {

    private static final int NUM_LIST_ITEMS = 48;
    private HourlyAdapter hourlyAdapter;
    private RecyclerView hourlyWeatherList;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly);

        hourlyWeatherList = (RecyclerView) findViewById(R.id.hourlyWeatherList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        hourlyWeatherList.setLayoutManager(layoutManager);
        hourlyWeatherList.setHasFixedSize(true);

        //String weatherResults = MainActivity.getMasterWeatherResults();
        //weatherResultParser weatherResultsJSON = new weatherResultParser(weatherResults);
        //ArrayList hourlyWeatherResult = weatherResultsJSON.dailyWeather();
        ArrayList hourlyWeatherResult = null;

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(hourlyWeatherList.getContext(),
                layoutManager.getOrientation());
        hourlyWeatherList.addItemDecoration(dividerItemDecoration);

        hourlyAdapter = new HourlyAdapter(hourlyWeatherResult, NUM_LIST_ITEMS);
        hourlyWeatherList.setAdapter(hourlyAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.back, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                Context context = HourlyActivity.this;
                Class destinationActivity = MainActivity.class;
                Intent intent = new Intent (context, destinationActivity);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}