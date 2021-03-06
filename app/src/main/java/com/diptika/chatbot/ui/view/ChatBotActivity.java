package com.diptika.chatbot.ui.view;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.diptika.chatbot.R;
import com.diptika.chatbot.database.RealmManager;
import com.diptika.chatbot.network.response.ChatBotMsgResponse;
import com.diptika.chatbot.ui.ChatBotContract;
import com.diptika.chatbot.ui.ChatBotInetractor;
import com.diptika.chatbot.ui.ChatBotPresenter;
import com.diptika.chatbot.utils.NetworkStateReceiver;
import com.diptika.chatbot.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diptika Shukla on 21/03/19.
 */

public class ChatBotActivity extends AppCompatActivity implements View.OnClickListener, ChatBotContract.View,
        NetworkStateReceiver.NetworkStateReceiverListener {
    public static final int VIEW_USER_MSG = 0;
    public static final int VIEW_CHATBOT_MSG = 1;

    private RecyclerView rvChat;
    private RelativeLayout rlEmpty;
    private ImageView ivSend;
    private EditText etInputMsg;
    private ChatBotAdapter chatBotAdapter;
    private List<ChatBotMsgResponse> chatBotMsgResponseList;
    private ChatBotPresenter chatBotPresenter;
    private LinearLayoutManager linearLayoutManager;
    private NetworkStateReceiver networkStateReceiver;

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
        ivSend = findViewById(R.id.iv_send);
        etInputMsg = findViewById(R.id.et_message);

        // set up adapter
        setUpAdapter();
        //init network state receiver
        initReceiver();
        //init realm.
        RealmManager.getInstance().init(ChatBotActivity.this);

        //initializing presenter
        chatBotPresenter = new ChatBotPresenter(new ChatBotInetractor());
        chatBotPresenter.subscribeView(this);

        //set listener
        ivSend.setOnClickListener(this);
        //get all stored message from db
        chatBotPresenter.getAllMessageFromDb();

    }

    private void initReceiver() {
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void setUpAdapter() {
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvChat.setLayoutManager(linearLayoutManager);
        chatBotMsgResponseList = new ArrayList<>();
        chatBotAdapter = new ChatBotAdapter(this, chatBotMsgResponseList);
        rvChat.setAdapter(chatBotAdapter);
        linearLayoutManager.scrollToPosition(chatBotMsgResponseList.size() - 1);
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
            fetchChatBotMsg(inputMessage);
            etInputMsg.setText("");
        } else {
            Toast.makeText(this, getString(R.string.err_empty_msg), Toast.LENGTH_SHORT).show();

        }

    }

    /**
     * fetch chatbot message from chatbot server
     *
     * @param inputMessage
     */
    private void fetchChatBotMsg(String inputMessage) {
        if (NetworkUtils.isInternetAvailable(this)) {
            chatBotPresenter.storeInputMessageDB(inputMessage, true);
            chatBotPresenter.getChatBotResponseMsg(inputMessage);
        } else {
            Toast.makeText(this, getString(R.string.err_network), Toast.LENGTH_SHORT).show();
            chatBotPresenter.storeInputMessageDB(inputMessage, false);
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
     *
     * @param chatBotMsgResponse
     */
    @Override
    public void showAllMessage(List<ChatBotMsgResponse> chatBotMsgResponse) {
        if (chatBotMsgResponse != null && chatBotMsgResponse.size() > 0) {
            rlEmpty.setVisibility(View.GONE);
            chatBotMsgResponseList.clear();
            chatBotMsgResponseList.addAll(chatBotMsgResponse);
            notifyDataSetChanged();
            linearLayoutManager.scrollToPosition(chatBotMsgResponseList.size() - 1);
        } else {
            rlEmpty.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Callback when network comes back
     */
    @Override
    public void networkAvailable() {
        chatBotPresenter.getAllUndeliveredMessage();
    }

    @Override
    protected void onDestroy() {
        if (chatBotPresenter.wasSubscribed(this)) {
            chatBotPresenter.unsubscribeView(this);
        }
        networkStateReceiver.removeListener(this);
        unregisterReceiver(networkStateReceiver);
        super.onDestroy();
    }


}
