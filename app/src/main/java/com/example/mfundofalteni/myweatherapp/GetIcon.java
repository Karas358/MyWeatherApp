package com.example.mfundofalteni.myweatherapp;

import android.util.Log;

public class GetIcon {
    public int getWeatherIcon(String small_desc){
        //Log.e("Check Desc", small_desc);
        switch (small_desc){
            case "scattered clouds":
                return R.drawable.clouds;
            case "few clouds":
                return R.drawable.cloud;
            case "broken clouds":
                return R.drawable.clouds;
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
                return R.drawable.normal;
        }
    }
}
