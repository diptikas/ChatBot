package com.diptika.chatbot.ui;

import com.diptika.chatbot.network.common.BasePresenter;
import com.diptika.chatbot.network.response.ChatBotMsgResponse;
import com.diptika.chatbot.network.response.ChatMessageData;
import com.diptika.chatbot.network.response.SenderType;

import java.util.List;

/**
 * Created by Diptika Shukla on 21/03/19.
 */

public class ChatBotPresenter extends BasePresenter<ChatBotContract.View> implements
        ChatBotContract.Presenter {

    private ChatBotInetractor chatBotInetractor;

    public ChatBotPresenter(ChatBotInetractor chatBotInetractor) {
        this.chatBotInetractor = chatBotInetractor;
    }


    @Override
    public void subscribeInteractor() {
        subscribeInteractor(chatBotInetractor, this);
    }

    /**
     * Call api for getting chatbot message
     *
     * @param message
     */
    @Override
    public void getAllMessage(String message) {
        if (getViewContract() != null) {
            chatBotInetractor.getAllMessage(message);
        }
    }

    @Override
    public void getAllMessageFromDb() {
        if (getViewContract() != null) {
            chatBotInetractor.getAllMessageFromDb();
        }
    }

    @Override
    public void onAllMessageRetrieved(List<ChatBotMsgResponse> chatBotMsgResponse) {
        if (getViewContract() != null) {
            getViewContract().showAllMessage(chatBotMsgResponse);
        }
    }


    @Override
    public void onError(Throwable throwable) {
        if (getViewContract() != null) {
            getViewContract().onError(throwable.getMessage());
        }
    }

    /**
     * Store User Input Message into DB
     *
     * @param msg
     * @return
     */
    public void storeInputMessageDB(String msg) {
        ChatBotMsgResponse chatBotMsgResponse = new ChatBotMsgResponse();
        chatBotMsgResponse.setSender(SenderType.SENDER_USER.getValue());
        chatBotMsgResponse.setSuccess(1);
        chatBotMsgResponse.setErrorMsg("");
        ChatMessageData chatMessageData = new ChatMessageData();
        chatMessageData.setMessage(msg);
        chatBotMsgResponse.setMessage(chatMessageData);
        chatBotInetractor.storeMessageToDB(chatBotMsgResponse);

    }

}

