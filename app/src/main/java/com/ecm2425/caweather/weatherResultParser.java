package com.ecm2425.caweather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class weatherResultParser {

    private JSONObject weatherResultJSON;

    public weatherResultParser(String weatherResult) {
        try {
            weatherResultJSON = new JSONObject(weatherResult);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONArray getWeather(){
        JSONArray weatherArray = null;
        try {
            weatherArray = weatherResultJSON.getJSONArray("weather");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weatherArray;
    }

    private JSONObject getMain(){
        JSONObject mainWeather = null;
        try {
            mainWeather = weatherResultJSON.getJSONObject("main");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainWeather;
    }

    //public String getDescription(){
    //    JSONArray weatherArray = getWeather();
    //    String description =
    //}

    public String getTemp() {
        JSONObject mainWeather = getMain();
        String temp = null;
        try {
            temp = mainWeather.getString("temp");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return temp;
    }
}
