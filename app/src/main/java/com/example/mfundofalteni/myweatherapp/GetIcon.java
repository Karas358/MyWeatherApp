package com.example.mfundofalteni.myweatherapp;


import android.content.Context;

public class GetIcon {
    public int getWeatherIcon(String small_desc){
        Context context;

        switch (small_desc){
            case "scattered clouds":
                return R.drawable.clouds;
            case "few clouds":
                return R.drawable.cloud;
            case "broken clouds":
                return R.drawable.normal;
            case "sky is clear":
                return R.drawable.clear;
            case "snow":
                return R.drawable.snow;
            case "rain":
                return R.drawable.rain;
            case "shower rain":
                return R.drawable.lightrain;
            case "thunderstorm":
                return R.drawable.hail;
            case "mist":
                return R.drawable.mist;
            default:
                return R.drawable.umbrella;
        }
    }
}
