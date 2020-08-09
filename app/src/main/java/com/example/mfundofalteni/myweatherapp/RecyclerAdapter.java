package com.example.mfundofalteni.myweatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class RecyclerAdaper extends RecyclerView.Adapter<RecyclerAdaper.MyViewHolder> {

    private List<WeatherModel> weatherModelList;
    private OnDayClickListener onDayClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView DT, txtMin, txtMax, txtWind;
        public ImageView imgview;
        OnDayClickListener onDayClickListener;

        public MyViewHolder(View view, OnDayClickListener onDayClickListener) {
            super(view);
            DT = view.findViewById(R.id.txtDate);
            txtMin =  view.findViewById(R.id.txtMin);
            txtMax =  view.findViewById(R.id.txtMax);
            txtWind =  view.findViewById(R.id.txtWindSpeed);
            imgview = view.findViewById(R.id.imgWeather);
            this.onDayClickListener = onDayClickListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onDayClickListener.onDayClick(getAdapterPosition());
        }
    }
    public RecyclerAdaper(List<WeatherModel> weatherModels, OnDayClickListener onDayClickListener) {
        this.weatherModelList = weatherModels;
        this.onDayClickListener = onDayClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_layout, parent, false);
        return new MyViewHolder(itemView, onDayClickListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final WeatherModel weatherModel = weatherModelList.get(position);

        GetIcon getIcon = new GetIcon();
        holder.DT.setText(weatherModel.DT);
        holder.txtMin.setText(weatherModel.TempMin);
        holder.txtMax.setText(weatherModel.TempMax);
        holder.txtWind.setText(weatherModel.WindSpeed);
        holder.imgview.setImageResource(getIcon.getWeatherIcon(weatherModel.Description));
    }

    @Override
    public int getItemCount() {
        return weatherModelList.size();
    }

    public interface OnDayClickListener{
        void onDayClick(int position);
    }
}
