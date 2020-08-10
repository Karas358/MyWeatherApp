package com.example.mfundofalteni.myweatherapp;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ParseJSON {

    public List<WeatherModel> parseWeather(String json) throws Exception {
        GetMonth getMonth = new GetMonth();
        WeatherModel weatherModel;
        try {
            List<WeatherModel> weatherModelList = new ArrayList<>();

            JSONObject jsonObject = new JSONObject(json);
            JSONObject cityJSON  = jsonObject.getJSONObject("city");
            JSONArray weatherJSON = jsonObject.getJSONArray("list");
            for(int x = 0; x < weatherJSON.length() ;x++){
                weatherModel = new WeatherModel();
                JSONObject jsonWeatherOBJ = weatherJSON.getJSONObject(x);
                JSONObject jsonTempObj = jsonWeatherOBJ.getJSONObject("temp");
                JSONArray jsonWeatherArray = jsonWeatherOBJ.getJSONArray("weather");
                JSONObject jsonWeather = jsonWeatherArray.getJSONObject(0);
                String DT = Instant.ofEpochSecond(Long.parseLong(jsonWeatherOBJ.getString("dt"))).toString().substring(5,10);

                weatherModel.Location = cityJSON.getString("name");
                weatherModel.Date = getMonth.getMonthName(DT.substring(0,2)) + DT.substring(2,5);
                weatherModel.Sunrise = Instant.ofEpochSecond(Long.parseLong(jsonWeatherOBJ.getString("sunrise"))).toString().substring(11, 16);
                weatherModel.Sunset = Instant.ofEpochSecond(Long.parseLong(jsonWeatherOBJ.getString("sunset"))).toString().substring(11, 16);
                weatherModel.TempMin = jsonTempObj.getString("min") +" °C";
                weatherModel.TempMax = jsonTempObj.getString("max") +" °C";
                weatherModel.Humidity = jsonWeatherOBJ.getString("humidity") + " %";
                weatherModel.WindSpeed = jsonWeatherOBJ.getString("speed") + " \nkm/h";
                weatherModel.Description = jsonWeather.getString("description");
                weatherModel.MainDesc = jsonWeather.getString("main");
                weatherModelList.add(weatherModel);
            }
           return weatherModelList;

        } catch (Exception e) {
            throw new Exception();
        }
    }
}
