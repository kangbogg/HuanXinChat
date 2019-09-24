package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener, EMMessageListener {

    private Button ec_btn_send;
    private EditText ec_edit_message_input;
    private RelativeLayout ec_layout_input;
    private TextView ec_text_content;
    private String mChatId;
    private SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // 获取当前会话的username(如果是群聊就是群id)
        mChatId = getIntent().getStringExtra("ec_chat_id");
        initView();
    }

    @SuppressLint("SimpleDateFormat")
    private void initView() {
        ec_btn_send = findViewById(R.id.ec_btn_send);
        ec_edit_message_input = findViewById(R.id.ec_edit_message_input);
        ec_layout_input = findViewById(R.id.ec_layout_input);
        ec_text_content = findViewById(R.id.ec_text_content);

        ec_btn_send.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ec_btn_send:
                String input = ec_edit_message_input.getText().toString().trim();
                //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
                EMMessage message = EMMessage.createTxtSendMessage(input, mChatId);
                // 将新的消息内容和时间加入到下边
                ec_text_content.setText(ec_text_content.getText() + "\n" + input + " -> " + getTime(message.getMsgTime()));
                EMClient.getInstance().chatManager().sendMessage(message);
                break;
        }
    }

    private String getTime(long millisecond) {
        if (sdf == null) {
            sdf = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
        }
        return sdf.format(new Date(millisecond));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 添加消息监听
        EMClient.getInstance().chatManager().addMessageListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 移除消息监听
        EMClient.getInstance().chatManager().removeMessageListener(this);
    }

    /***
     * 消息监听
     * @param messages
     */
    @Override
    public void onMessageReceived(List<EMMessage> messages) {
        //收到消息
        // 循环遍历当前收到的消息
        for (EMMessage message : messages) {
            if (message.getFrom().equals(mChatId)) {
                //刷新UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        EMTextMessageBody body = (EMTextMessageBody) message.getBody();
                        // 将新的消息内容和时间加入到下边
                        ec_text_content.setText(ec_text_content.getText() + "\n" + body.getMessage() + " <- " + getTime(message.getMsgTime()));
                    }
                });

            } else {
            }
        }
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {
        //收到透传消息
    }

    @Override
    public void onMessageRead(List<EMMessage> messages) {
        //收到已读回执
    }

    @Override
    public void onMessageDelivered(List<EMMessage> message) {
        //收到已送达回执
    }

    @Override
    public void onMessageRecalled(List<EMMessage> messages) {
        //消息被撤回
    }

    @Override
    public void onMessageChanged(EMMessage message, Object change) {
        //消息状态变动
    }
}
