package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/***
 * 用户名 ：001 密码 001
 * 用户名 ：002 密码 002
 * 用户名 ：003 密码 003
 * 用户名 ：004 密码 004
 * 用户名 ：005 密码 005
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ec_edit_username;
    private EditText ec_edit_password;
    private Button ec_btn_sign_up;
    private Button ec_btn_sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        ec_edit_username =  findViewById(R.id.ec_edit_username);
        ec_edit_password =  findViewById(R.id.ec_edit_password);
        ec_btn_sign_up =  findViewById(R.id.ec_btn_sign_up);
        ec_btn_sign_in =  findViewById(R.id.ec_btn_sign_in);

        ec_btn_sign_up.setOnClickListener(this);
        ec_btn_sign_in.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ec_btn_sign_up:

                break;
            case R.id.ec_btn_sign_in:
                singIn();
                break;
        }
    }

    private void singIn(){


        String username = ec_edit_username.getText().toString().trim();
        String password = ec_edit_password.getText().toString().trim();
        EMClient.getInstance().login(username,password,new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");
                // 登录成功跳转界面
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！");
            }
        });



    }
}
