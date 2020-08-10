package com.example.mfundofalteni.myweatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ViewDayAdapter extends RecyclerView.Adapter<ViewDayAdapter.MyViewHolder> {

    private List<WeatherModel> weatherModelList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtDate, txtLocation, txtDescription, txtTempMin, txtTempMax, txtSunset, txtSunrise, txtWindSpeed, txtHumidity;
        public ImageView imgview;

        public MyViewHolder(View view) {
            super(view);
            txtDate = view.findViewById(R.id.txtDate);
            txtLocation = view.findViewById(R.id.txtLocation);
            txtDescription = view.findViewById(R.id.txtDescription);
            txtTempMin = view.findViewById(R.id.txtTempMin);
            txtTempMax = view.findViewById(R.id.txtTempMax);
            txtSunrise = view.findViewById(R.id.txtSunrise);
            txtSunset = view.findViewById(R.id.txtSunset);
            txtWindSpeed = view.findViewById(R.id.txtWindSpeed);
            txtHumidity = view.findViewById(R.id.txtHumidity);
            imgview = view.findViewById(R.id.imageView);
        }
    }
    public ViewDayAdapter(List<WeatherModel> weatherModels) {
        this.weatherModelList = weatherModels;
    }

    @NonNull
    @Override
    public ViewDayAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_day_layout, parent, false);
        return new ViewDayAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final WeatherModel weatherModel = weatherModelList.get(position);

        GetIcon getIcon = new GetIcon();
        holder.txtDate.setText(weatherModel.Date);
        holder.txtDescription.setText(weatherModel.Description);
        holder.txtLocation.setText(weatherModel.Location);
        holder.txtSunrise.setText(weatherModel.Sunrise);
        holder.txtSunset.setText(weatherModel.Sunset);
        holder.txtTempMin.setText(weatherModel.TempMin);
        holder.txtTempMax.setText(weatherModel.TempMax);
        holder.txtWindSpeed.setText(weatherModel.WindSpeed);
        holder.txtHumidity.setText(weatherModel.Humidity);
        holder.imgview.setImageResource(getIcon.getWeatherIcon(weatherModel.Description));
    }
    @Override
    public int getItemCount() {
        return weatherModelList.size();
    }
}