package com.ecm2425.caweather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.font.NumericShaper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    private String formatDay(Integer time, String timeZone) {
        Date timeFormat = new java.util.Date(time*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEEE dd");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone(timeZone));
        String dayFormated = sdf.format(timeFormat);
        return dayFormated;
    }

    private String formatTimeDay(Integer time, String timeZone) {
        Date timeFormat = new java.util.Date(time*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm - EEEE dd");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone(timeZone));
        String timeDayFormated = sdf.format(timeFormat);
        return timeDayFormated;
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

    public String currentWeatherStr(){
        HashMap current = currentWeather();

        return " Temp: " + current.get("temp") + "°C"
                + "\n Description: " + current.get("description")
                + "\n Clouds: " + current.get("clouds") + "%"
                + "\n Humidity: " + current.get("humidity") + "%"
                + "\n Wind Speed: " + current.get("wind_speed") + "m/s"
                + "\n Wind Direction: " + current.get("wind_direction")
                + "\n Sunrise: " + current.get("sunrise")
                + "\n Sunset: " + current.get("sunset");
    }

    public ArrayList dailyWeather() {
        JSONArray allDailyWeather = null;
        try {
            allDailyWeather = weatherResultJSON.getJSONArray("daily");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList dailyWeatherResult = new ArrayList();
        JSONObject day = null;

        for (int i = 0; i < 8; i++) {
            HashMap<String, String> dayResult = new HashMap<String, String>();
            try {
                day = allDailyWeather.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            assert day != null;
            //JSONObject dayTemp = getInner(day, "temp");
            //JSONObject dayWeather = getInner(day, "weather");
            Integer unixTime = getInt(day, "dt");
            //Integer tempMin = getInt(dayTemp, "day");
            //Integer tempMax = getInt(dayTemp, "max");
            Integer pop = getInt(day, "pop");
            //String desc = getStr(dayWeather, "Main");

            Integer tempMin = 0;
            Integer tempMax = 0;
            String desc= "";
            try {
                tempMin = day.getJSONObject("temp").getInt("min");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                tempMax = day.getJSONObject("temp").getInt("max");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                desc = day.getJSONArray("weather").getJSONObject(0).getString("main");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            int rain = pop*100;

            String date = formatDay(unixTime, "GMT");

            dayResult.put("day", date);
            dayResult.put("tempMin", tempMin.toString());
            dayResult.put("tempMax", tempMax.toString());
            dayResult.put("rain", Integer.toString(rain));
            dayResult.put("desc", desc);
            dailyWeatherResult.add(dayResult);
        }
        return dailyWeatherResult;
    }

    public ArrayList hourlyWeather() {
        JSONArray allHourlyWeather = null;
        try {
            allHourlyWeather = weatherResultJSON.getJSONArray("hourly");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList hourlyWeatherResult = new ArrayList();

        for (int i = 0; i < 48; i++) {
            HashMap<String, String> hourResult = new HashMap<String, String>();
            JSONObject hour = null;
            try {
                hour = allHourlyWeather.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            assert hour != null;
            //JSONObject dayTemp = getInner(day, "temp");
            //JSONObject dayWeather = getInner(day, "weather");
            Integer unixTime = getInt(hour, "dt");
            //Integer tempMin = getInt(dayTemp, "day");
            //Integer tempMax = getInt(dayTemp, "max");
            Integer pop = getInt(hour, "pop");
            //String desc = getStr(dayWeather, "Main");
            Integer temp = getInt(hour, "temp");

            String desc= "";

            try {
                desc = hour.getJSONArray("weather").getJSONObject(0).getString("main");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            int rain = pop*100;

            String date = formatTimeDay(unixTime, "GMT");

            hourResult.put("day", date);
            hourResult.put("temp", temp.toString());
            hourResult.put("rain", Integer.toString(rain));
            hourResult.put("desc", desc);
            hourlyWeatherResult.add(hourResult);
        }
        return hourlyWeatherResult;
    }
}