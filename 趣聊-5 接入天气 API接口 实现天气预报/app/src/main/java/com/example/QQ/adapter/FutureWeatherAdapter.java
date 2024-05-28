package com.example.QQ.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.QQ.Bean.HourlyWeatherBean;
import com.example.QQ.R;

import java.util.List;

public class FutureWeatherAdapter extends RecyclerView.Adapter<FutureWeatherAdapter.WeatherViewHolder> {

    private Context mContext;
    private List<HourlyWeatherBean> mWeatherBeans;

    public FutureWeatherAdapter(Context mContext, List<HourlyWeatherBean> mWeatherBeans) {
        this.mContext = mContext;
        this.mWeatherBeans = mWeatherBeans;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.weather_item_layout, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        HourlyWeatherBean weatherBean = mWeatherBeans.get(position);
        holder.tvWeather.setText(weatherBean.getWeather());
        holder.tvDate.setText(weatherBean.getHours());
        holder.tvTem.setText(weatherBean.getTemperature());
        holder.tvAir.setText("空气：" + weatherBean.getAirQuality() + " " + weatherBean.getAirQualityNum());
        holder.tvWin.setText(weatherBean.getWind() + " " + weatherBean.getWindSpeed());
        holder.ivWeather.setImageResource(getImageResource(weatherBean.getWeatherImg()));
    }

    @Override
    public int getItemCount() {
        if (mWeatherBeans == null) return 0;
        return mWeatherBeans.size();
    }

    static class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView tvWeather, tvTem, tvWin, tvAir, tvDate;
        ImageView ivWeather;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWeather = itemView.findViewById(R.id.tv_weather);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvTem = itemView.findViewById(R.id.tv_tem);
            tvWin = itemView.findViewById(R.id.tv_win);
            tvAir = itemView.findViewById(R.id.tv_air);
            ivWeather = itemView.findViewById(R.id.iv_weather);
        }
    }

    private int getImageResource(String weaStr) {
        int result = 0;
        switch (weaStr) {
            case "qing":
                result = R.drawable.biz_plugin_weather_qing;
                break;
            case "yin":
                result = R.drawable.biz_plugin_weather_yin;
                break;
            case "yu":
                result = R.drawable.biz_plugin_weather_dayu;
                break;
            case "yun":
                result = R.drawable.biz_plugin_weather_duoyun;
                break;
            case "bingbao":
                result = R.drawable.biz_plugin_weather_leizhenyubingbao;
                break;
            case "shachen":
                result = R.drawable.biz_plugin_weather_shachenbao;
                break;
            case "lei":
                result = R.drawable.biz_plugin_weather_leizhenyu;
                break;
            case "xue":
                result = R.drawable.biz_plugin_weather_daxue;
                break;
            default:
                result = R.drawable.biz_plugin_weather_qing;
                break;
        }
        return result;
    }
}
