package com.example.QQ;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.QQ.Bean.DayWeatherBean;
import com.example.QQ.Bean.HourlyWeatherBean;
import com.example.QQ.Bean.OtherTipsBean;
import com.example.QQ.Bean.WeatherBean;
import com.example.QQ.adapter.FutureWeatherAdapter;
import com.example.QQ.utils.NetUtil;
import com.google.gson.Gson;

import java.util.List;

public class WeatherStationActivity extends AppCompatActivity {
    private AppCompatSpinner mSpinner;
    private ArrayAdapter<String> mSpAdapter;
    private String[] mCities;
    private TextView tvWeather, tvTem, tvTemLowHigh, tvWin, tvAir;
    private ImageView ivWeather;
    private RecyclerView rlvFutureWeather;

    private FutureWeatherAdapter mWeatherAdapter;
    private DayWeatherBean todayWeather;

    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                String weather = (String) msg.obj;
                Log.d("WeatherStationActivity", "---主线程接收到了天气数据---" + weather);

                // GSON 解析
                Gson gson = new Gson();
                WeatherBean weatherBean = gson.fromJson(weather, WeatherBean.class);
                if (weatherBean != null) {
                    Log.d("WeatherStationActivity", "---解析后的weatherBean---" + weatherBean.toString());

                    // 将解析到的数据进行界面呈现
                    updateUiOfWeather(weatherBean);
                    Toast.makeText(WeatherStationActivity.this, "更新天气~", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("WeatherStationActivity", "解析后的weatherBean为null");
                }
            }
        }
    };

    private void updateUiOfWeather(WeatherBean weatherBean) {
        if (weatherBean == null) {
            Log.e("WeatherStationActivity", "WeatherBean is null");
            return;
        }

        tvTem.setText(weatherBean.getTemperature());
        tvWeather.setText(weatherBean.getWeather() + " " + weatherBean.getDate() + " " + weatherBean.getWeek());
        tvTemLowHigh.setText(weatherBean.getTempMin() + " ~ " + weatherBean.getTempMax());
        tvWin.setText(weatherBean.getWind() + " " + weatherBean.getWindSpeed());
        tvAir.setText("空气等级: " + weatherBean.getAirQuality() + " " + weatherBean.getAirLevel() + "\n\n" + weatherBean.getAirTips());
        ivWeather.setImageResource(getImageResource(weatherBean.getWeatherImg()));

        List<HourlyWeatherBean> hourlyWeathers = weatherBean.getHourlyWeather();
        if (hourlyWeathers == null || hourlyWeathers.isEmpty()) {
            Log.e("WeatherStationActivity", "HourlyWeatherBeans list is null or empty");
            return;
        }

        todayWeather = new DayWeatherBean();
        todayWeather.setTem(weatherBean.getTemperature());
        todayWeather.setWea(weatherBean.getWeather());
        todayWeather.setDate(weatherBean.getDate());
        todayWeather.setTem1(weatherBean.getTempMax());
        todayWeather.setTem2(weatherBean.getTempMin());
        todayWeather.setWin(new String[]{weatherBean.getWind()});
        todayWeather.setWinSpeed(weatherBean.getWindSpeed());
        todayWeather.setAir(weatherBean.getAirQuality());
        todayWeather.setAirLevel(weatherBean.getAirLevel());
        todayWeather.setAirTips(weatherBean.getAirTips());
        todayWeather.setWeaImg(weatherBean.getWeatherImg());

        if (weatherBean.getAqi() != null) {
            todayWeather.setPm25(weatherBean.getAqi().getPm25());
            todayWeather.setPm25Desc(weatherBean.getAqi().getPm25Desc());
            todayWeather.setPm10(weatherBean.getAqi().getPm10());
            todayWeather.setPm10Desc(weatherBean.getAqi().getPm10Desc());
            todayWeather.setO3(weatherBean.getAqi().getO3());
            todayWeather.setO3Desc(weatherBean.getAqi().getO3Desc());
            todayWeather.setNo2(weatherBean.getAqi().getNo2());
            todayWeather.setNo2Desc(weatherBean.getAqi().getNo2Desc());
            todayWeather.setSo2(weatherBean.getAqi().getSo2());
            todayWeather.setSo2Desc(weatherBean.getAqi().getSo2Desc());
            todayWeather.setCo(weatherBean.getAqi().getCo());
            todayWeather.setCoDesc(weatherBean.getAqi().getCoDesc());
            todayWeather.setKouzhao(weatherBean.getAqi().getKouzhao());
            todayWeather.setYundong(weatherBean.getAqi().getYundong());
            todayWeather.setWaichu(weatherBean.getAqi().getWaichu());
            todayWeather.setKaichuang(weatherBean.getAqi().getKaichuang());
            todayWeather.setJinghuaqi(weatherBean.getAqi().getJinghuaqi());
        }

        mWeatherAdapter = new FutureWeatherAdapter(this, hourlyWeathers);
        rlvFutureWeather.setAdapter(mWeatherAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rlvFutureWeather.setLayoutManager(layoutManager);

        Log.d("WeatherStationActivity", "UI更新成功");
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        initView();
    }

    private void initView() {
        mSpinner = findViewById(R.id.sp_city);
        mCities = getResources().getStringArray(R.array.cities);
        mSpAdapter = new ArrayAdapter<>(this, R.layout.sp_item_layout, mCities);
        mSpinner.setAdapter(mSpAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectCity = mCities[i];
                getWeatherOfCity(selectCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tvWeather = findViewById(R.id.tv_weather);
        tvTem = findViewById(R.id.tv_tem);
        tvTemLowHigh = findViewById(R.id.tv_tem_low_high);
        tvWin = findViewById(R.id.tv_win);
        tvAir = findViewById(R.id.tv_air);
        ivWeather = findViewById(R.id.iv_weather);
        rlvFutureWeather = findViewById(R.id.rlv_future_weather);

        // 在 MainActivity 中
        tvAir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (todayWeather != null) {
                    Log.d("MainActivity", "----todayWeather为：---" + todayWeather);
                    if (todayWeather.getTipsBean() != null) {
                        for (OtherTipsBean tip : todayWeather.getTipsBean()) {
                            Log.d("MainActivity", "----Tip:---" + tip.toString());
                        }
                    }
                    Intent intent = new Intent(WeatherStationActivity.this, TipsActivity.class);
                    intent.putExtra("tips", todayWeather);
                    startActivity(intent);
                } else {
                    Toast.makeText(WeatherStationActivity.this, "今日天气数据为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getWeatherOfCity(String selectCity) {
        NetUtil.getWeatherOfCity(selectCity, new NetUtil.NetworkCallback() {
            @Override
            public void onSuccess(String result) {
                if (result != null && !result.isEmpty()) {
                    Log.d("MainActivity", "获取天气数据成功：" + result);

                    Message message = Message.obtain();
                    message.what = 0;
                    message.obj = result;
                    mHandler.sendMessage(message);
                } else {
                    runOnUiThread(() -> Toast.makeText(WeatherStationActivity.this, "Failed to fetch weather data: Result is empty", Toast.LENGTH_LONG).show());
                    Log.e("MainActivity", "获取天气数据为空");
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(() -> Toast.makeText(WeatherStationActivity.this, "Failed to fetch weather data: " + e.getMessage(), Toast.LENGTH_LONG).show());
                Log.e("MainActivity", "获取天气数据失败：" + e.getMessage());
            }
        });
    }



}
