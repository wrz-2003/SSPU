package com.example.QQ.Bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HourlyWeatherBean implements Serializable {

    @SerializedName("hours")
    private String hours;

    @SerializedName("wea")
    private String weather;

    @SerializedName("wea_img")
    private String weatherImg;

    @SerializedName("tem")
    private String temperature;

    @SerializedName("win")
    private String wind;

    @SerializedName("win_speed")
    private String windSpeed;

    @SerializedName("vis")
    private String visibility;

    @SerializedName("aqinum")
    private String airQualityNum;

    @SerializedName("aqi")
    private String airQuality;

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWeatherImg() {
        return weatherImg;
    }

    public void setWeatherImg(String weatherImg) {
        this.weatherImg = weatherImg;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getAirQualityNum() {
        return airQualityNum;
    }

    public void setAirQualityNum(String airQualityNum) {
        this.airQualityNum = airQualityNum;
    }

    public String getAirQuality() {
        return airQuality;
    }

    public void setAirQuality(String airQuality) {
        this.airQuality = airQuality;
    }

    @Override
    public String toString() {
        return "HourlyWeatherBean{" +
                "hours='" + hours + '\'' +
                ", weather='" + weather + '\'' +
                ", weatherImg='" + weatherImg + '\'' +
                ", temperature='" + temperature + '\'' +
                ", wind='" + wind + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                ", visibility='" + visibility + '\'' +
                ", airQualityNum='" + airQualityNum + '\'' +
                ", airQuality='" + airQuality + '\'' +
                '}';
    }
}
