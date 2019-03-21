package com.diptika.chatbot.network.response;

/**
 * Created by Diptika Shukla on 21/03/19.
 */

public class ChatMessageData {
    private String chatBotName;
    private int chatBotID;
    private String message;
    private String emotion;

    public String getChatBotName() {
        return chatBotName;
    }

    public void setChatBotName(String chatBotName) {
        this.chatBotName = chatBotName;
    }

    public int getChatBotID() {
        return chatBotID;
    }

    public void setChatBotID(int chatBotID) {
        this.chatBotID = chatBotID;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
