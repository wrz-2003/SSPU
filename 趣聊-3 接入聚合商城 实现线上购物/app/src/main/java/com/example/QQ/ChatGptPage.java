package com.example.QQ;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ChatGptPage extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView welcomeTextView;
    EditText messageEditText;
    ImageButton sendButton;
    List<Message> messageList;
    MessageAdapter messageAdapter;
    DatabaseHelper databaseHelper;

    public static final MediaType JSON = MediaType.get("application/json");

    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        databaseHelper = new DatabaseHelper(this);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        welcomeTextView = findViewById(R.id.welcome_text);
        messageEditText = findViewById(R.id.message_edit_text);
        sendButton = findViewById(R.id.send_btn);

        loadMessages(); // Load existing messages from database

        sendButton.setOnClickListener(v -> {
            String message = messageEditText.getText().toString().trim();
            if (!message.isEmpty()) {
                addToChat(message, Message.SEND_BY_ME);
                messageEditText.setText("");
                callAPI(message);
            }
            welcomeTextView.setVisibility(View.GONE);
        });
    }

    private void addToChat(String message, String sender) {
        ContentValues values = new ContentValues();
        values.put("message", message);
        values.put("sender", sender);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.insert("messages", null, values);
        runOnUiThread(() -> {
            messageList.add(new Message(message, sender));
            messageAdapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
        });
    }

    private void loadMessages() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query("messages", new String[]{"message", "sender"}, null, null, null, null, "_id ASC");
        if (cursor.moveToFirst()) {
            do {
                String message = cursor.getString(cursor.getColumnIndex("message"));
                String sender = cursor.getString(cursor.getColumnIndex("sender"));
                messageList.add(new Message(message, sender));
            } while (cursor.moveToNext());
        }
        cursor.close();
        messageAdapter.notifyDataSetChanged();
    }


    void addResponse(String response) {
        messageList.remove(messageList.size() - 1);
        addToChat(response, Message.SEND_BY_BOT);
    }

    void callAPI(String question) {
        //okhttp
        messageList.add(new Message("正在回复~~", Message.SEND_BY_BOT));

        JSONObject jsonBody = new JSONObject();

        try {
            // 创建messages数组，包含用户角色和问题内容
            JSONArray messagesArray = new JSONArray();
            JSONObject systemMessage = new JSONObject();
            systemMessage.put("role", "system");
            systemMessage.put("content", "You are ChatGPT, a large language model trained by OpenAI. Follow the user's instructions carefully. Respond using markdown.");
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", question);
            messagesArray.put(systemMessage);
            messagesArray.put(userMessage);
            // 构建完整的JSON请求体
            jsonBody.put("model", "gpt-3.5-turbo"); // 模型选择
            jsonBody.put("messages", messagesArray);

        }catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
        Request request = new Request.Builder()
                .url("https://openkey.cloud/v1/chat/completions") // 服务器地址
                .header("Authorization", "Bearer sk-RpHSBHOkt5tdkWIr762f6fD9676a4513BfF3096d0fB37f2b") // 填写你的key
                .header("Content-Type", "application/json")
                .post(body)
                .build();

        client.newBuilder().readTimeout(50000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("响应失败" + e.getMessage());
                Log.d("TAG", "onFailure: ------" + e.getMessage());
            }
//
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.isSuccessful()) {
                    JSONObject jsonObject = null;
                    String responseBody = response.body().string();
                    try {
                        // 解析服务器响应的JSON数据
                        jsonObject = new JSONObject(responseBody);
                        Log.d("TAG", "onResponse: ---------" + responseBody);
                        // 从JSON中提取需要的信息
                        JSONArray choicesArray = jsonObject.getJSONArray("choices");
                        JSONObject firstChoice = choicesArray.getJSONObject(0);
                        JSONObject message = firstChoice.getJSONObject("message");
                        String result = message.getString("content");

                        // 将提取的结果添加到聊天列表中
                        addResponse(result.trim());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    } finally {
                        response.close();
                    }

                } else {
                    addResponse("响应失败" + response.body().string());
                    Log.d("TAG", "onResponse: ---------" + response.body().toString());
                }
            }
        });
    }

}