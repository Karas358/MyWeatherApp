package com.example.mfundofalteni.myweatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewDayAdapter extends RecyclerView.Adapter<ViewDayAdapter.MyViewHolder> {

    private List<WeatherModel> weatherModelList;
    //private ViewDayAdapter.OnReflectionListener onReflectionListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtDate, txtLocation, txtDescription, txtTempMin, txtTempMax, txtSunset, txtSunrise, txtWindSpeed, txtHumidity;
        public ImageView imgview;
        //ViewDayAdapter.OnReflectionListener onReflectionListener;

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
            //this.onReflectionListener = onReflectionListener;
            //view.setOnClickListener(this);
        }

        /*@Override
        public void onClick(View v) {
            onReflectionListener.onReflectionClick(getAdapterPosition());
        }*/
    }
    public ViewDayAdapter(List<WeatherModel> weatherModels) {
        this.weatherModelList = weatherModels;
        //this.onReflectionListener = onReflectionListener;
    }

    @Override
    public ViewDayAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_day_layout, parent, false);
        //itemView.setOnClickListener(new MyOnClickListener());
        return new ViewDayAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final WeatherModel weatherModel = weatherModelList.get(position);

        GetIcon getIcon = new GetIcon();
        //final ReflectionRepository reflectionRepository = new ReflectionRepository(holder.itemView.getContext());
        //String today = "Today, " + weatherModel.DT;
        holder.txtDate.setText(weatherModel.DT);
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

    /*public interface OnReflectionListener{
        void onReflectionClick(int position);
    }*/
}