package com.example.QQ;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CountDownView extends androidx.appcompat.widget.AppCompatTextView {
    private final static int DURATION = 1000;
    private List<String> mData;
    private int index = -1;
    private AnimationSet mAnimationSet;
    private ScheduledExecutorService mScheduled;
    private CountDownListener mCountDownListener;
    private boolean isCountingDown = false;

    public interface CountDownListener {
        void finish();
    }

    public CountDownView(Context context) {
        this(context, null);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAnim();
    }

    private void initAnim() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        ScaleAnimation scaleAnim = new ScaleAnimation(1, 2, 1, 2, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mAnimationSet = new AnimationSet(true);
        mAnimationSet.addAnimation(alphaAnimation);
        mAnimationSet.addAnimation(scaleAnim);
        mAnimationSet.setDuration(DURATION);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (index < mData.size() - 1) {
                index++;
                setText(mData.get(index));
                startAnimation(mAnimationSet);
            } else {
                isCountingDown = false;
                if (mCountDownListener != null) {
                    if (mScheduled != null) {
                        mScheduled.shutdown();
                    }
                    mCountDownListener.finish();
                }
            }
        }
    };

    public void init() {
        index = -1;
        setText("");
        isCountingDown = true;
        if (mData != null && mData.size() > 0) {
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(1);
                }
            };
            mScheduled = Executors.newScheduledThreadPool(1);
            mScheduled.scheduleAtFixedRate(timerTask, 2000, DURATION, TimeUnit.MILLISECONDS);
        }
    }

    public void cancel() {
        if (mScheduled != null) {
            mScheduled.shutdown();
            isCountingDown = false;
        }
    }

    public boolean isCountingDown() {
        return isCountingDown;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mScheduled != null) {
            mScheduled.shutdown();
        }
    }

    public void setCountDown(int start, int end) {
        if (end > start) {
            if (mData == null)
                mData = new ArrayList<>();
            mData.clear();
            for (int i = start; i <= end; i++) {
                mData.add(String.valueOf(i));
            }
        }
    }

    public List<String> getData() {
        return mData;
    }

    public void setData(List<String> data) {
        mData = data;
    }

    public CountDownListener getCountDownListener() {
        return mCountDownListener;
    }

    public void setCountDownListener(CountDownListener countDownListener) {
        mCountDownListener = countDownListener;
    }
}
