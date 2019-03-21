package com.diptika.chatbot.ui.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.diptika.chatbot.R;
import com.diptika.chatbot.network.response.ChatBotMsgResponse;
import com.diptika.chatbot.ui.ChatBotContract;
import com.diptika.chatbot.ui.ChatBotInetractor;
import com.diptika.chatbot.ui.ChatBotPresenter;
import com.diptika.chatbot.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Diptika Shukla on 21/03/19.
 */

public class ChatBotActivity extends AppCompatActivity implements View.OnClickListener, ChatBotContract.View {
    public static final int VIEW_USER_MSG = 0;
    public static final int VIEW_CHATBOT_MSG = 1;

    private RecyclerView rvChat;
    private RelativeLayout rlEmpty;
    private LinearLayout llInput;
    private ImageView ivSend;
    private EditText etInputMsg;
    private ChatBotAdapter chatBotAdapter;
    private List<ChatBotMsgResponse> chatBotMsgResponseList;
    private ChatBotPresenter chatBotPresenter;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
    }

    /**
     * initialize view
     */
    private void initView() {
        rvChat = findViewById(R.id.rv_chat);
        rlEmpty = findViewById(R.id.layout_empty);
        llInput = findViewById(R.id.layout_input);
        ivSend = findViewById(R.id.iv_send);
        etInputMsg = findViewById(R.id.et_message);

        // set up adapter
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvChat.setLayoutManager(linearLayoutManager);
        chatBotMsgResponseList = new ArrayList<>();
        chatBotAdapter = new ChatBotAdapter(this, chatBotMsgResponseList);
        rvChat.setAdapter(chatBotAdapter);
        linearLayoutManager.scrollToPosition(chatBotMsgResponseList.size() - 1);

        //initializing presenter
        chatBotPresenter = new ChatBotPresenter(new ChatBotInetractor());
        chatBotPresenter.subscribeView(this);

        //set listener
        ivSend.setOnClickListener(this);

        //show empty layout
        showEmptyLayout();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_send:
                sendInputMessage();
                break;
            default:
                break;
        }
    }

    /**
     * Send input message to chatbot server
     */
    private void sendInputMessage() {
        String inputMessage = etInputMsg.getText().toString();
        if (!TextUtils.isEmpty(inputMessage)) {
            chatBotMsgResponseList.add(ChatBotPresenter.getInputMessage(inputMessage));
            notifyDataSetChanged();
            linearLayoutManager.scrollToPosition(chatBotMsgResponseList.size() - 1);
            etInputMsg.setText("");
            showEmptyLayout();
            fetchChatBotMsg(inputMessage);
        } else {
            Toast.makeText(this, getString(R.string.err_empty_msg), Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * fetch chatbot message from chatbot server
     * @param inputMessage
     */
    private void fetchChatBotMsg(String inputMessage) {
        if (NetworkUtils.isInternetAvailable(this)) {
            chatBotPresenter.getAllMessage(inputMessage);
        } else {
            Toast.makeText(this, getString(R.string.err_network), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void notifyDataSetChanged() {
        chatBotAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String errMsg) {
        Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Add all chatbot response message in recyclerview
     * @param chatBotMsgResponse
     */
    @Override
    public void showAllMessage(ChatBotMsgResponse chatBotMsgResponse) {
        showEmptyLayout();
        chatBotMsgResponseList.add(chatBotMsgResponse);
        notifyDataSetChanged();
        linearLayoutManager.scrollToPosition(chatBotMsgResponseList.size() - 1);
    }

    /**
     * show empty layout if no msg is available
     */
    private void showEmptyLayout(){
        rlEmpty.setVisibility(chatBotMsgResponseList.size() > 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        if (chatBotPresenter.wasSubscribed(this)) {
            chatBotPresenter.unsubscribeView(this);
        }
        super.onDestroy();
    }
}
