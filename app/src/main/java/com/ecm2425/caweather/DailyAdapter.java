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

    private ArrayList<HashMap<String, String>> dailyWeather;
    private int numberItems;
    private static int viewHolderCount;

    public DailyAdapter(ArrayList<HashMap<String, String>> dailyWeatherResult, int numberOfItems) {
        dailyWeather = dailyWeatherResult;
        numberItems = numberOfItems;
        viewHolderCount = 0;
    }

    @Override
    public dailyWeatherViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.daily_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        dailyWeatherViewHolder viewHolder = new dailyWeatherViewHolder(view);

        viewHolder.dayView.setText("Good");
        viewHolder.tempMaxView.setText("Good");
        viewHolder.tempMinView.setText("Good");
        viewHolder.rainView.setText("Good");
        viewHolder.descView.setText("Good");

        viewHolderCount++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull dailyWeatherViewHolder holder, int position) {

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
    }
}
