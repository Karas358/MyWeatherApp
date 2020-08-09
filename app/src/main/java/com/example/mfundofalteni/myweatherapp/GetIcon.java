package com.example.mfundofalteni.myweatherapp;

public class GetIcon {
    public int getWeatherIcon(String small_desc){
        switch (small_desc){
            case "Clouds":
                return R.drawable.clouds;
            case "Clear":
                return R.drawable.clear;
            case "Snowy":
                return R.drawable.snow;
            case "Light Rain":
                return R.drawable.lightrain;
            case "Hail":
                return R.drawable.hail;
            default:
                return R.drawable.normal;
        }
    }
}
