package com.ecm2425.caweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.hourlyWeatherViewHolder> {
    private ArrayList hourlyWeather;
    private int numberItems;
    private static int viewHolderCount;

    public HourlyAdapter(ArrayList hourlyWeatherResult, int numberOfItems) {
        hourlyWeather = hourlyWeatherResult;
        numberItems = numberOfItems;
        viewHolderCount = 0;
    }

    @Override
    public hourlyWeatherViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.hourly_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        hourlyWeatherViewHolder viewHolder = new hourlyWeatherViewHolder(view);

        HashMap<String, String> hour = (HashMap<String, String>) hourlyWeather.get(viewHolderCount);

        viewHolder.dayView.setText(hour.get("day"));
        viewHolder.tempView.setText(hour.get("temp") + "°C");
        viewHolder.rainView.setText("Chance of Rain: " + hour.get("rain") + "%");
        viewHolder.descView.setText(hour.get("desc"));

        viewHolderCount++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull hourlyWeatherViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return numberItems;
    }

    class hourlyWeatherViewHolder extends RecyclerView.ViewHolder {

        TextView dayView;
        TextView tempView;
        TextView rainView;
        TextView descView;

        public hourlyWeatherViewHolder(View itemView) {
            super(itemView);

            dayView = (TextView) itemView.findViewById(R.id.dayView);
            tempView = (TextView) itemView.findViewById(R.id.tempView);
            rainView = (TextView) itemView.findViewById(R.id.rainView);
            descView = (TextView) itemView.findViewById(R.id.descView);
        }
    }
}
