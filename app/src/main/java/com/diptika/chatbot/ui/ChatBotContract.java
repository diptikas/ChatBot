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
        void getChatBotResponseMsg(String message);

        void getAllMessageFromDb();

        void getAllUndeliveredMessage();

        void onAllMessageRetrieved(List<ChatBotMsgResponse> chatBotMsgResponse);

        void onAllUndeliveredMessageFetched(List<ChatBotMsgResponse> undeliveredMsgList);
    }

    public interface ApiContract {
        void getChatBotResponseMsg(String message);

        void getAllMessageFromDb();

        void storeMessageToDB(ChatBotMsgResponse chatBotMsgResponse);

        void getAllUndeliveredMessage();

    }
}
