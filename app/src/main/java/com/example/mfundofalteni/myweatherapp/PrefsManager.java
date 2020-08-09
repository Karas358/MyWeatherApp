package com.example.mfundofalteni.myweatherapp;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;


    public PrefsManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences("DT" , PRIVATE_MODE);
        editor = pref.edit();
    }

    public void set_DT(String DT) {
        editor.putString("DT" , DT);
        editor.commit();
    }

    public String get_DT() {
        String res = pref.getString("DT" , "DT");
        return res;
    }

    public void set_Location(String location) {
        editor.putString("Location" , location);
        editor.commit();
    }

    public String get_Location() {
        String res = pref.getString("Location" , "Location");
        return res;
    }
    public void set_Sunrise(String Sunrise) {
        editor.putString("Sunrise" , Sunrise);
        editor.commit();
    }

    public String get_Sunrise() {
        String res = pref.getString("Sunrise" , "Sunrise");
        return res;
    }

    public void set_Sunset(String Sunset) {
        editor.putString("Sunset" , Sunset);
        editor.commit();
    }

    public String get_Sunset() {
        String res = pref.getString("Sunset" , "Sunset");
        return res;
    }
    public void set_Min(String Min) {
        editor.putString("Min" , Min);
        editor.commit();
    }

    public String get_Min() {
        String res = pref.getString("Min" , "Min");
        return res;
    }

    public void set_Max(String Max) {
        editor.putString("Max" , Max);
        editor.commit();
    }

    public String get_Max() {
        String res = pref.getString("Max" , "Max");
        return res;
    }
    public void set_Windspeed(String Windspeed) {
        editor.putString("Windspeed" , Windspeed);
        editor.commit();
    }

    public String get_Windspeed() {
        String res = pref.getString("Windspeed" , "Windspeed");
        return res;
    }

    public void set_Humidity(String Humidity) {
        editor.putString("Humidity" , Humidity);
        editor.commit();
    }

    public String get_Humidity() {
        String res = pref.getString("Humidity" , "Humidity");
        return res;
    }
    public void set_Description(String Description) {
        editor.putString("Description" , Description);
        editor.commit();
    }

    public String get_Description() {
        String res = pref.getString("Description" , "Description");
        return res;
    }

    public void set_MainDesc(String MainDesc) {
        editor.putString("MainDesc" , MainDesc);
        editor.commit();
    }

    public String get_MainDesc() {
        String res = pref.getString("MainDesc" , "MainDesc");
        return res;
    }

    public WeatherModel getAllPrefs(){
        WeatherModel weatherModel = new WeatherModel();
        weatherModel.DT = get_DT();
        weatherModel.Location = get_Location();
        weatherModel.Sunrise = get_Sunrise();
        weatherModel.Sunset = get_Sunset();
        weatherModel.TempMin = get_Min();
        weatherModel.TempMax = get_Max();
        weatherModel.Humidity = get_Humidity();
        weatherModel.WindSpeed = get_Windspeed();
        weatherModel.Description = get_Description();
        weatherModel.MainDesc = get_MainDesc();
        return weatherModel;
    }

    public void populateAllPrefs(WeatherModel weatherModel){
        set_DT(weatherModel.DT);
        set_Location(weatherModel.Location);
        set_Sunrise(weatherModel.Sunrise);
        set_Sunset(weatherModel.Sunset);
        set_Min(weatherModel.TempMin);
        set_Max(weatherModel.TempMax);
        set_Description(weatherModel.Description);
        set_MainDesc(weatherModel.MainDesc);
        set_Windspeed(weatherModel.WindSpeed);
        set_Humidity(weatherModel.Humidity);
    }
}