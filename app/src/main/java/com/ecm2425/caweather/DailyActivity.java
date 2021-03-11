package com.ecm2425.caweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

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
        dailyAdapter = new DailyAdapter(NUM_LIST_ITEMS);
        dailyWeatherList.setAdapter(dailyAdapter);
    }
}