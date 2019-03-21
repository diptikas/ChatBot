package com.diptika.chatbot.network.response;

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
