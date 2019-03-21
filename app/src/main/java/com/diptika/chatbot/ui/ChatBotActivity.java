package com.diptika.chatbot.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.diptika.chatbot.R;

public class ChatBotActivity extends AppCompatActivity {
    public static final int VIEW_USER_MSG = 0;
    public static final int VIEW_CHATBOT_MSG = 1;

    private RecyclerView rvChat;
    private RelativeLayout rlEmpty;
    private LinearLayout llInput;
    private ImageView ivSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
    }

    private void initView() {
        rvChat=findViewById(R.id.rv_chat);
        rlEmpty=findViewById(R.id.layout_empty);
        llInput=findViewById(R.id.layout_input);
        ivSend=findViewById(R.id.iv_send);
    }
}
