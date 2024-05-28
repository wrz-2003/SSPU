package com.example.QQ.Bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OtherTipsBean implements Serializable {
    @SerializedName("title")
    private String title;

    @SerializedName("level")
    private String level;

    @SerializedName("desc")
    private String desc;

    public OtherTipsBean(String title, String level, String desc) {
        this.title = title;
        this.level = level;
        this.desc = desc;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "OtherTipsBean{" +
                "title='" + title + '\'' +
                ", level='" + level + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}

