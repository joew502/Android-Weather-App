package com.ecm2425.caweather;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class weatherResultParser {

    private JSONObject weatherResultJSON;

    public weatherResultParser(String weatherResult) {
        try {
            weatherResultJSON = new JSONObject(weatherResult);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONObject getCurrent(){
        JSONObject currentWeather = null;
        try {
            currentWeather = weatherResultJSON.getJSONObject("current");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return currentWeather;
    }

    private JSONObject getInner(JSONObject mainObj, String innerName){
        JSONObject weatherObj = null;
        try {
            weatherObj = mainObj.getJSONArray(innerName).getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weatherObj;
    }

    private String getStr(JSONObject mainObj, String strRequest) {
        String strResponse = null;
        try {
            strResponse = mainObj.getString(strRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return strResponse;
    }

    private Integer getInt(JSONObject mainObj, String intRequest) {
        int intResponse = 0;
        try {
            intResponse = mainObj.getInt(intRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return intResponse;
    }

    private String formatTime(Integer time, String timeZone) {
        Date timeFormat = new java.util.Date(time*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone(timeZone));
        String timeFormated = sdf.format(timeFormat);
        return timeFormated;
    }

    private HashMap currentWeather(){
        HashMap<String, String> weather = new HashMap<String, String>();

        JSONObject current = getCurrent();

        Integer temp = getInt(current, "temp");
        Integer sunrise = getInt(current, "sunrise");
        Integer sunset = getInt(current, "sunset");
        Integer clouds = getInt(current, "clouds");
        Integer humidity = getInt(current, "humidity");
        Integer windSpeed = getInt(current, "wind_speed");
        Integer windAngle = getInt(current, "wind_deg");

        String windDirection;
        if (22.5<windAngle && windAngle<=67.5) {
            windDirection = "NE";
        } else if(67.5<windAngle && windAngle<=112.5) {
            windDirection = "E";
        } else if(112.5<windAngle && windAngle<=157.5) {
            windDirection = "SE";
        } else if(157.5<windAngle && windAngle<=202.5) {
            windDirection = "S";
        } else if(202.5<windAngle && windAngle<=247.5) {
            windDirection = "SW";
        } else if(247.5<windAngle && windAngle<=292.5) {
            windDirection = "W";
        } else if(292.5<windAngle && windAngle<=337.5) {
            windDirection = "NW";
        } else {
            windDirection = "N";
        }

        String sunriseFormated = formatTime(sunrise, "GMT");
        String sunsetFormated = formatTime(sunset, "GMT");

        JSONObject currentWeather = getInner(current, "weather");
        String description = getStr(currentWeather, "description");

        weather.put("temp", temp.toString());
        weather.put("description", description);
        weather.put("clouds", clouds.toString());
        weather.put("humidity", humidity.toString());
        weather.put("wind_speed", windSpeed.toString());
        weather.put("wind_direction", windDirection);
        weather.put("sunrise", sunriseFormated);
        weather.put("sunset", sunsetFormated);

        return weather;
    }

    public String currentWeatherStr(String location){
        HashMap current = currentWeather();

        return " Current Weather in " + location + ":"
                + "\n Temp: " + current.get("temp") + "°C"
                + "\n Description: " + current.get("description")
                + "\n Clouds: " + current.get("clouds") + "%"
                + "\n Humidity: " + current.get("humidity") + "%"
                + "\n Wind Speed: " + current.get("wind_speed") + "kph"
                + "\n Wind Direction: " + current.get("wind_direction")
                + "\n Sunrise: " + current.get("sunrise")
                + "\n Sunset: " + current.get("sunset");
    }
}