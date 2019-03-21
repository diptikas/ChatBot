package com.diptika.chatbot.ui;

import com.diptika.chatbot.network.common.BasePresenterContract;
import com.diptika.chatbot.network.response.ChatBotMsgResponse;
/**
 * Created by Diptika Shukla on 21/03/19.
 */

public class ChatBotContract {

    public interface View {
        void notifyDataSetChanged();
        void onError(String errMsg);
        void showAllMessage(ChatBotMsgResponse chatBotMsgResponse);
    }

    public interface Presenter extends BasePresenterContract {
        void getAllMessage(String message);
        void onAllMessageRetrieved(ChatBotMsgResponse chatBotMsgResponse);
    }

    public interface ApiContract {
        void getAllMessage(String message);

    }
}
