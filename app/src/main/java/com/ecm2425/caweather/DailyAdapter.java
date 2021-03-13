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

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.dailyWeatherViewHolder> {

    private ArrayList dailyWeather;
    private int numberItems;

    public DailyAdapter(ArrayList dailyWeatherResult, int numberOfItems) {
        dailyWeather = dailyWeatherResult;
        numberItems = numberOfItems;
    }

    @Override
    public dailyWeatherViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.daily_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        dailyWeatherViewHolder viewHolder = new dailyWeatherViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull dailyWeatherViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return numberItems;
    }

    class dailyWeatherViewHolder extends RecyclerView.ViewHolder {

        TextView dayView;
        TextView tempMaxView;
        TextView tempMinView;
        TextView rainView;
        TextView descView;

        public dailyWeatherViewHolder(View itemView) {
            super(itemView);

            dayView = (TextView) itemView.findViewById(R.id.dayView);
            tempMaxView = (TextView) itemView.findViewById(R.id.tempMaxView);
            tempMinView = (TextView) itemView.findViewById(R.id.tempMinView);
            rainView = (TextView) itemView.findViewById(R.id.rainView);
            descView = (TextView) itemView.findViewById(R.id.descView);
        }

        void bind(int listIndex) {
            HashMap<String, String> day = (HashMap<String, String>) dailyWeather.get(listIndex);

            dayView.setText(day.get("day"));
            tempMaxView.setText("Min: " + day.get("tempMin") + "°C");
            tempMinView.setText("Max: " + day.get("tempMax") + "°C");
            rainView.setText("Chance of Rain: " + day.get("rain") + "%");
            descView.setText(day.get("desc"));
        }
    }
}
