package com.example.QQ;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class PianoActivity extends AppCompatActivity {
    private CountDownView mCountDownView;
    private PianoTilesView mPianoTilesView;
    private RelativeLayout mMarkRela;
    private AlertScoreDialog mAlertScoreDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        initView();
    }

    private void initView() {
        mPianoTilesView = findViewById(R.id.pianoTilesView);
        mCountDownView = findViewById(R.id.countTextView);
        mMarkRela = findViewById(R.id.markRela);
        mCountDownView.setData(Arrays.asList("3", "2", "1", "开始"));
        mCountDownView.init();
        mCountDownView.setCountDownListener(new CountDownView.CountDownListener() {
            @Override
            public void finish() {
                mMarkRela.setVisibility(View.GONE);
                mPianoTilesView.setZOrderOnTop(true);
                mPianoTilesView.startGame();
            }
        });

        mAlertScoreDialog = new AlertScoreDialog.Builder(PianoActivity.this)
                .setFinishClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("DEMO", "点击点击");
                        finish();
                        mAlertScoreDialog.dismiss();
                    }
                })
                .setRestartClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAlertScoreDialog.dismiss();
                        mPianoTilesView.restart();
                        mMarkRela.setVisibility(View.VISIBLE);
                        mCountDownView.init();
                    }
                })
                .builder();

        mPianoTilesView.setGameListener(new PianoTilesView.GameListener() {
            @Override
            public void gameEnd(final String number) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("DEMO", "number == " + number);
                        if (mAlertScoreDialog != null) {
                            mPianoTilesView.setTouchable(false);
                            mAlertScoreDialog.setScore(number);
                            mAlertScoreDialog.show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mCountDownView.isCountingDown()) {
            // 如果倒计时正在进行，取消倒计时并返回上一个界面
            mCountDownView.cancel();
            mMarkRela.setVisibility(View.VISIBLE);
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPianoTilesView != null) {
            mPianoTilesView.stopGame();
        }
        if (mCountDownView != null) {
            mCountDownView.cancel();
        }
    }
}
