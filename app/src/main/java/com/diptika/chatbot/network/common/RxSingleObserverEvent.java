package com.diptika.chatbot.network.common;

/**
 * Created by Diptika Shukla on 21/03/19.
 */

public interface RxSingleObserverEvent<T> extends RxErrorEvent {
    void onSuccess(T var1);
}


