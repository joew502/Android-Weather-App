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

public class DailyActivity extends AppCompatActivity {

    private static final int NUM_LIST_ITEMS = 8;
    private DailyAdapter dailyAdapter;
    private RecyclerView dailyWeatherList;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        dailyWeatherList = (RecyclerView) findViewById(R.id.dailyWeatherList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        dailyWeatherList.setLayoutManager(layoutManager);
        dailyWeatherList.setHasFixedSize(true);

        String weatherResults = MainActivity.getMasterWeatherResults();
        weatherResultParser weatherResultsJSON = new weatherResultParser(weatherResults);
        ArrayList dailyWeatherResult = weatherResultsJSON.dailyWeather();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(dailyWeatherList.getContext(),
                layoutManager.getOrientation());
        dailyWeatherList.addItemDecoration(dividerItemDecoration);

        dailyAdapter = new DailyAdapter(dailyWeatherResult, NUM_LIST_ITEMS);
        dailyWeatherList.setAdapter(dailyAdapter);
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
                Context context = DailyActivity.this;
                Class destinationActivity = MainActivity.class;
                Intent intent = new Intent (context, destinationActivity);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}