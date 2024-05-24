package com.example.QQ;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class Index extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.message_id, R.id.friends_id, R.id.news_id,R.id.mine_id)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.popumenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_addFriend:
                Intent intent = new Intent(Index.this, AddFriendPage.class);
                startActivity(intent);
                return true;
            case R.id.menu_help:
                Intent stateIntent = new Intent(this, StateActivity.class);
                startActivity(stateIntent);
                return true;
            case R.id.menu_delete_account:
                // 处理注销账号菜单的点击事件
                Intent deleteAccountIntent = new Intent(this, DeleteAccountActivity.class);
                startActivity(deleteAccountIntent);
                return true;
            case R.id.menu_music_player:
                // 启动音乐播放器
                Intent musicPlayerIntent = new Intent(this, MusicPlayerActivity.class);
                startActivity(musicPlayerIntent);
                return true;
            case R.id.menu_Online_shopping:
                Intent webViewIntent = new Intent(this, WebViewActivity.class);
                startActivity(webViewIntent);
                return true;
            case R.id.menu_Piano_Tiles:
                Intent pianoIntent = new Intent(this, PianoActivity.class);
                startActivity(pianoIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }






}