package com.example.QQ;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.QQ.Bean.DayWeatherBean;
import com.example.QQ.Bean.OtherTipsBean;
import com.example.QQ.adapter.TipsAdapter;

import java.util.ArrayList;
import java.util.List;

public class TipsActivity extends AppCompatActivity {

    private RecyclerView rlvTips;
    private TipsAdapter mTipsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        rlvTips = findViewById(R.id.rlv_tips);

        // 在 TipsActivity 中
        Intent intent = getIntent();
        DayWeatherBean weatherBean = (DayWeatherBean) intent.getSerializableExtra("tips");
        if (weatherBean == null) {
            Log.e("TipsActivity", "今日天气数据为空");
            return;
        }

        Log.d("TipsActivity", "接收到的weatherBean: " + weatherBean.toString());

// 创建新的 OtherTipsBean 列表以包含所有额外的提示信息
        List<OtherTipsBean> tips = new ArrayList<>();
        tips.add(new OtherTipsBean("PM2.5", weatherBean.getPm25Desc(), "浓度：" + weatherBean.getPm25()));
        tips.add(new OtherTipsBean("PM10", weatherBean.getPm10Desc(), "浓度：" + weatherBean.getPm10()));
        tips.add(new OtherTipsBean("O3", weatherBean.getO3Desc(), "浓度：" + weatherBean.getO3()));
        tips.add(new OtherTipsBean("NO2", weatherBean.getNo2Desc(), "浓度：" + weatherBean.getNo2()));
        tips.add(new OtherTipsBean("SO2", weatherBean.getSo2Desc(), "浓度：" + weatherBean.getSo2()));
        tips.add(new OtherTipsBean("CO", weatherBean.getCoDesc(), "浓度：" + weatherBean.getCo()));
        tips.add(new OtherTipsBean("口罩建议", "", weatherBean.getKouzhao()));
        tips.add(new OtherTipsBean("运动建议", "", weatherBean.getYundong()));
        tips.add(new OtherTipsBean("外出建议", "", weatherBean.getWaichu()));
        tips.add(new OtherTipsBean("开窗建议", "", weatherBean.getKaichuang()));
        tips.add(new OtherTipsBean("净化器建议", "", weatherBean.getJinghuaqi()));

        mTipsAdapter = new TipsAdapter(this, tips);

        rlvTips.setAdapter(mTipsAdapter);
        rlvTips.setLayoutManager(new LinearLayoutManager(this));

    }

}
