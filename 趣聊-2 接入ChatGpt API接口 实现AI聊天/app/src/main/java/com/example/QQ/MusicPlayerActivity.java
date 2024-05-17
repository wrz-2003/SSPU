package com.example.QQ;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class MusicPlayerActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private static final int REQUEST_READ_STORAGE_PERMISSION = 1001;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> musicPaths = new ArrayList<>();
    private SeekBar seekBar;
    private TextView currentTimeTextView;
    private TextView totalTimeTextView;
    private Handler handler = new Handler();
    private boolean isUserSeeking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        ListView musicListView = findViewById(R.id.music_list_view);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        musicListView.setAdapter(adapter);
        musicListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedMusicPath = musicPaths.get(position);
            playMusic(selectedMusicPath);
        });

        Button playButton = findViewById(R.id.play_button);
        playButton.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                updateSeekBar();
            } else if (!musicPaths.isEmpty()) {
                mediaPlayer.start();                 //playMusic(musicPaths.get(0)); // 或使用保存的最后一个选择的索引
                updateSeekBar();
            }
        });

        Button pauseButton = findViewById(R.id.pause_button);
        pauseButton.setOnClickListener(v -> pauseMusic());

        seekBar = findViewById(R.id.seekBar);
        currentTimeTextView = findViewById(R.id.currentTime);
        totalTimeTextView = findViewById(R.id.totalTime);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    currentTimeTextView.setText(formatTime(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isUserSeeking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isUserSeeking = false;
                mediaPlayer.seekTo(seekBar.getProgress());
                updateSeekBar();
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_READ_STORAGE_PERMISSION);
        } else {
            loadMusic();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadMusic();
            } else {
                Toast.makeText(this, "需要存储权限来加载音乐", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadMusic() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor == null) {
                Log.e("MusicPlayerActivity", "查询失败，Cursor 为空");
                return;
            }
            if (!cursor.moveToFirst()) {
                Log.e("MusicPlayerActivity", "没有找到音乐文件");
                return;
            }
            int titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int dataColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            do {
                String title = cursor.getString(titleColumn);
                String path = cursor.getString(dataColumn);
                adapter.add(title);
                musicPaths.add(path);
            } while (cursor.moveToNext());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MusicPlayerActivity", "检索音乐文件时发生错误", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void playMusic(String path) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(mp -> {
                mp.start();
                seekBar.setMax(mp.getDuration());
                totalTimeTextView.setText(formatTime(mp.getDuration()));
                updateSeekBar();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pauseMusic() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    private void updateSeekBar() {
        if (!isUserSeeking && mediaPlayer.isPlaying()) {
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            currentTimeTextView.setText(formatTime(mediaPlayer.getCurrentPosition()));
            handler.postDelayed(this::updateSeekBar, 1000);
        }
    }

    private String formatTime(int millis) {
        int seconds = millis / 1000;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format(Locale.getDefault(), "%d:%02d", minutes, seconds);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }
        handler.removeCallbacksAndMessages(null);
    }
}
