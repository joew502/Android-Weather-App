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

    public JSONObject getCurrent(){
        JSONObject currentWeather = null;
        try {
            currentWeather = weatherResultJSON.getJSONObject("current");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return currentWeather;
    }

    public JSONObject getWeather(JSONObject mainObj){
        JSONObject weatherObj = null;
        try {
            weatherObj = mainObj.getJSONArray("weather").getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weatherObj;
    }

    public String getTemp(JSONObject mainObj) {
        String temp = null;
        try {
            temp = mainObj.getString("temp");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public String getDescription(JSONObject weatherObj) {
        String description = null;
        try {
            description = weatherObj.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return description;
    }
}