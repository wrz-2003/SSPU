package com.example.QQ;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.QQ.domain.User;
import com.example.QQ.utils.UserUtil;


public class DeleteAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText usernameText;
    private EditText passwordText;
    private Button deleteAccountBtn;
    private TextView errorTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_account); // 布局文件名为 delete_account.xml

        deleteAccountBtn = findViewById(R.id.btn_delete_account);
        usernameText = findViewById(R.id.delete_username_input);
        passwordText = findViewById(R.id.delete_password_input);
        errorTip = findViewById(R.id.delete_error_tip);

        deleteAccountBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_delete_account) {
            String username = usernameText.getText().toString();
            String password = passwordText.getText().toString();
            User user = new User(username, password);

            if (UserUtil.login(user)) { // 验证用户
                if (UserUtil.deleteUser(username)) { // 删除用户
                    Toast.makeText(DeleteAccountActivity.this, "账号已注销", Toast.LENGTH_SHORT).show();
                    // 注销成功，跳转到登录界面
                    Intent intent = new Intent(DeleteAccountActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    errorTip.setText("注销失败，请重试");
                }
            } else {
                errorTip.setText("账号或密码错误，请重试");
            }
        }
    }

}


