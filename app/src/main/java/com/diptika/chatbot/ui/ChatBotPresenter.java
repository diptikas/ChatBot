package com.diptika.chatbot.ui;

import com.diptika.chatbot.network.common.BasePresenter;
import com.diptika.chatbot.network.response.ChatBotMsgResponse;
import com.diptika.chatbot.network.response.ChatMessageData;
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
     * @param message
     */
    @Override
    public void getAllMessage(String message) {
        if (getViewContract() != null) {
            chatBotInetractor.getAllMessage(message);
        }
    }

    /**
     * Callback when all chatbot messages fetched
     * @param chatBotMsgResponse
     */
    @Override
    public void onAllMessageRetrieved(ChatBotMsgResponse chatBotMsgResponse) {
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
     * Get User Input Message Body
     * @param msg
     * @return
     */
    public static ChatBotMsgResponse getInputMessage(String msg){
        ChatBotMsgResponse chatBotMsgResponse=new ChatBotMsgResponse();
        chatBotMsgResponse.setSender(ChatBotMsgResponse.SenderType.SENDER_USER);
        chatBotMsgResponse.setSuccess(1);
        chatBotMsgResponse.setErrorMsg("");
        ChatMessageData chatMessageData=new ChatMessageData();
        chatMessageData.setMessage(msg);
        chatBotMsgResponse.setMessage(chatMessageData);
        return chatBotMsgResponse;

    }

}

