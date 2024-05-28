package com.example.QQ;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class StateActivity extends AppCompatActivity {

    private TextView batteryInfo;
    private TextView networkInfo;

    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float batteryPct = level * 100 / (float)scale;

            batteryInfo.setText("电池电量: " + batteryPct + "%");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);

        batteryInfo = findViewById(R.id.battery_info);
        networkInfo = findViewById(R.id.network_info);

        this.registerReceiver(this.batteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        updateNetworkInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(batteryInfoReceiver);
    }

    private void updateNetworkInfo() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            this.networkInfo.setText("网络状态: 已连接");
        } else {
            this.networkInfo.setText("网络状态: 未连接");
        }
    }
}
