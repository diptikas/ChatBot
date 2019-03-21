package com.diptika.chatbot.network.response;

import io.realm.RealmObject;

/**
 * Created by Diptika Shukla on 21/03/19.
 */

public class ChatBotMsgResponse extends RealmObject {
    private Integer senderType = SenderType.SENDER_CHATBOT.getValue();
    private ChatMessageData message;
    private String errorMsg;
    private int success;

    public Integer getSender() {
        return senderType;
    }

    public void setSender(Integer sender) {
        this.senderType = sender;
    }

    public ChatMessageData getMessage() {
        return message;
    }

    public void setMessage(ChatMessageData message) {
        this.message = message;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

}
