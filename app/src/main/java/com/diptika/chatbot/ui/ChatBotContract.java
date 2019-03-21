package com.diptika.chatbot.ui;

import com.diptika.chatbot.network.common.BasePresenterContract;
import com.diptika.chatbot.network.response.ChatBotMsgResponse;

import java.util.List;

/**
 * Created by Diptika Shukla on 21/03/19.
 */

public class ChatBotContract {

    public interface View {
        void notifyDataSetChanged();

        void onError(String errMsg);

        void showAllMessage(List<ChatBotMsgResponse> chatBotMsgResponse);
    }

    public interface Presenter extends BasePresenterContract {
        void getAllMessage(String message);

        void getAllMessageFromDb();

        void onAllMessageRetrieved(List<ChatBotMsgResponse> chatBotMsgResponse);
    }

    public interface ApiContract {
        void getAllMessage(String message);

        void getAllMessageFromDb();

        void storeMessageToDB(ChatBotMsgResponse chatBotMsgResponse);

    }
}
