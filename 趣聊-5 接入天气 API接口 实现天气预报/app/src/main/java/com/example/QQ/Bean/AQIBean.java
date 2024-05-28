package com.example.QQ.Bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AQIBean implements Serializable {

    @SerializedName("update_time")
    private String updateTime;

    @SerializedName("cityid")
    private String cityId;

    @SerializedName("city")
    private String city;

    @SerializedName("air")
    private String air;

    @SerializedName("air_level")
    private String airLevel;

    @SerializedName("air_tips")
    private String airTips;

    @SerializedName("pm25")
    private String pm25;

    @SerializedName("pm25_desc")
    private String pm25Desc;

    @SerializedName("pm10")
    private String pm10;

    @SerializedName("pm10_desc")
    private String pm10Desc;

    @SerializedName("o3")
    private String o3;

    @SerializedName("o3_desc")
    private String o3Desc;

    @SerializedName("no2")
    private String no2;

    @SerializedName("no2_desc")
    private String no2Desc;

    @SerializedName("so2")
    private String so2;

    @SerializedName("so2_desc")
    private String so2Desc;

    @SerializedName("co")
    private String co;

    @SerializedName("co_desc")
    private String coDesc;

    @SerializedName("kouzhao")
    private String kouzhao;

    @SerializedName("yundong")
    private String yundong;

    @SerializedName("waichu")
    private String waichu;

    @SerializedName("kaichuang")
    private String kaichuang;

    @SerializedName("jinghuaqi")
    private String jinghuaqi;

    // Getters and Setters
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAir() {
        return air;
    }

    public void setAir(String air) {
        this.air = air;
    }

    public String getAirLevel() {
        return airLevel;
    }

    public void setAirLevel(String airLevel) {
        this.airLevel = airLevel;
    }

    public String getAirTips() {
        return airTips;
    }

    public void setAirTips(String airTips) {
        this.airTips = airTips;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getPm25Desc() {
        return pm25Desc;
    }

    public void setPm25Desc(String pm25Desc) {
        this.pm25Desc = pm25Desc;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getPm10Desc() {
        return pm10Desc;
    }

    public void setPm10Desc(String pm10Desc) {
        this.pm10Desc = pm10Desc;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getO3Desc() {
        return o3Desc;
    }

    public void setO3Desc(String o3Desc) {
        this.o3Desc = o3Desc;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getNo2Desc() {
        return no2Desc;
    }

    public void setNo2Desc(String no2Desc) {
        this.no2Desc = no2Desc;
    }

    public String getSo2() {
        return so2;
    }

    public void setSo2(String so2) {
        this.so2 = so2;
    }

    public String getSo2Desc() {
        return so2Desc;
    }

    public void setSo2Desc(String so2Desc) {
        this.so2Desc = so2Desc;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getCoDesc() {
        return coDesc;
    }

    public void setCoDesc(String coDesc) {
        this.coDesc = coDesc;
    }

    public String getKouzhao() {
        return kouzhao;
    }

    public void setKouzhao(String kouzhao) {
        this.kouzhao = kouzhao;
    }

    public String getYundong() {
        return yundong;
    }

    public void setYundong(String yundong) {
        this.yundong = yundong;
    }

    public String getWaichu() {
        return waichu;
    }

    public void setWaichu(String waichu) {
        this.waichu = waichu;
    }

    public String getKaichuang() {
        return kaichuang;
    }

    public void setKaichuang(String kaichuang) {
        this.kaichuang = kaichuang;
    }

    public String getJinghuaqi() {
        return jinghuaqi;
    }

    public void setJinghuaqi(String jinghuaqi) {
        this.jinghuaqi = jinghuaqi;
    }

    @Override
    public String toString() {
        return "AQIBean{" +
                "updateTime='" + updateTime + '\'' +
                ", cityId='" + cityId + '\'' +
                ", city='" + city + '\'' +
                ", air='" + air + '\'' +
                ", airLevel='" + airLevel + '\'' +
                ", airTips='" + airTips + '\'' +
                ", pm25='" + pm25 + '\'' +
                ", pm25Desc='" + pm25Desc + '\'' +
                ", pm10='" + pm10 + '\'' +
                ", pm10Desc='" + pm10Desc + '\'' +
                ", o3='" + o3 + '\'' +
                ", o3Desc='" + o3Desc + '\'' +
                ", no2='" + no2 + '\'' +
                ", no2Desc='" + no2Desc + '\'' +
                ", so2='" + so2 + '\'' +
                ", so2Desc='" + so2Desc + '\'' +
                ", co='" + co + '\'' +
                ", coDesc='" + coDesc + '\'' +
                ", kouzhao='" + kouzhao + '\'' +
                ", yundong='" + yundong + '\'' +
                ", waichu='" + waichu + '\'' +
                ", kaichuang='" + kaichuang + '\'' +
                ", jinghuaqi='" + jinghuaqi + '\'' +
                '}';
    }
}
