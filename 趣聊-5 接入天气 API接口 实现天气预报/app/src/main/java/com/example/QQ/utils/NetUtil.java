package com.example.QQ.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetUtil {
    private static final String TAG = NetUtil.class.getSimpleName();
    public static final String URL_WEATHER_WITH_FUTURE = "http://v1.yiketianqi.com/api?unescape=1&version=v63&appid=26184152&appsecret=J5uM2g6I";

    // 封装网络请求的方法，确保它不在主线程中执行
    public static void getWeatherOfCity(String city, NetworkCallback callback) {
        new Thread(() -> {
            String weatherUrl = URL_WEATHER_WITH_FUTURE + "&city=" + city;
            Log.d(TAG, "---weatherUrl---" + weatherUrl);
            try {
                String weatherResult = doGet(weatherUrl);
                Log.d(TAG, "---weatherResult---" + weatherResult);
                callback.onSuccess(weatherResult);
            } catch (Exception e) {
                Log.e(TAG, "Error fetching weather data", e);
                callback.onError(e);
            }
        }).start();
    }

    // 执行HTTP GET请求并处理响应
    public static String doGet(String urlStr) throws IOException {
        String result = "";
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);

        // 使用try-with-resources语句自动管理资源
        try (InputStream inputStream = connection.getInputStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            result = stringBuilder.toString();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    // 网络回调接口，用于异步处理网络请求结果
    public interface NetworkCallback {
        void onSuccess(String result);
        void onError(Exception e);
    }
}
