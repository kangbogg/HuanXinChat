package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hyphenate.chat.EMClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ec_edit_chat_id;
    private Button ec_btn_start_chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        ec_edit_chat_id =  findViewById(R.id.ec_edit_chat_id);
        ec_btn_start_chat =  findViewById(R.id.ec_btn_start_chat);

        ec_btn_start_chat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ec_btn_start_chat:
                String chatId = ec_edit_chat_id.getText().toString().trim();
                String currUsername = EMClient.getInstance().getCurrentUser();
                if (chatId.equals(currUsername)) {
                    Toast.makeText(MainActivity.this, "不能和自己聊天", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 跳转到聊天界面，开始聊天
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                intent.putExtra("ec_chat_id", chatId);
                startActivity(intent);
                break;
        }
    }


}
