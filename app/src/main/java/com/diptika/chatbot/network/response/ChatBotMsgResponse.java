package com.diptika.chatbot.network.response;

/**
 * Created by Diptika Shukla on 21/03/19.
 */

public class ChatBotMsgResponse {
    SenderType sender = SenderType.SENDER_CHATBOT;
    private ChatMessageData message;
    private String errorMsg;
    private int success;

    public SenderType getSender() {
        return sender;
    }

    public void setSender(SenderType sender) {
        this.sender = sender;
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

    public enum SenderType {
        SENDER_USER(0), SENDER_CHATBOT(1);

        private final int value;

        SenderType(int value) {
            this.value = value;
        }

        public static SenderType getType(int val) {
            return SenderType.values()[val];
        }

        public int getValue() {
            return value;
        }
    }


}
