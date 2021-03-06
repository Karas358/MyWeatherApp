package com.example.mfundofalteni.myweatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<WeatherModel> weatherModelList;
    private OnDayClickListener onDayClickListener;

    public static class  MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtDate, txtMin, txtMax, txtWind;
        public ImageView imgView;
        OnDayClickListener onDayClickListener;

        public MyViewHolder(View view, OnDayClickListener onDayClickListener) {
            super(view);
            txtDate = view.findViewById(R.id.txtDate);
            txtMin =  view.findViewById(R.id.txtMin);
            txtMax =  view.findViewById(R.id.txtMax);
            txtWind =  view.findViewById(R.id.txtWindSpeed);
            imgView = view.findViewById(R.id.imgWeather);
            this.onDayClickListener = onDayClickListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onDayClickListener.onDayClick(getAdapterPosition());
        }
    }
    public RecyclerAdapter(List<WeatherModel> weatherModels, OnDayClickListener onDayClickListener) {
        this.weatherModelList = weatherModels;
        this.onDayClickListener = onDayClickListener;
    }

    @NonNull
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
        holder.txtDate.setText(weatherModel.Date);
        holder.txtMin.setText(weatherModel.TempMin);
        holder.txtMax.setText(weatherModel.TempMax);
        holder.txtWind.setText(weatherModel.WindSpeed);
        holder.imgView.setImageResource(getIcon.getWeatherIcon(weatherModel.Description));
    }

    @Override
    public int getItemCount() {
        return weatherModelList.size();
    }

    public interface OnDayClickListener{
        void onDayClick(int position);
    }
}
