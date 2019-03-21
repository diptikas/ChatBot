package com.diptika.chatbot.ui;

import com.diptika.chatbot.network.common.BaseInteractor;
import com.diptika.chatbot.network.common.RxSingleObserverEvent;
import com.diptika.chatbot.network.api.RestApiObservables;
import com.diptika.chatbot.network.response.ChatBotMsgResponse;
/**
 * Created by Diptika Shukla on 21/03/19.
 */

public class ChatBotInetractor extends BaseInteractor implements ChatBotContract.ApiContract,
        RxSingleObserverEvent<ChatBotMsgResponse> {

    private RestApiObservables restApiObservables = new RestApiObservables();

    /**
     * Callback when api get success
     * @param chatBotMsgResponse
     */
    @Override
    public void onSuccess(ChatBotMsgResponse chatBotMsgResponse) {
        if (getPresenter() != null) {
            getPresenter().onAllMessageRetrieved(chatBotMsgResponse);
        }
    }

    /**
     * Callback when api get failure
     * @param throwable
     */
    @Override
    public void onError(Throwable throwable) {
        if (getPresenter() != null) {
            getPresenter().onError(throwable);
        }
    }

    /**
     * Fetch All Chatbot Message
     * @param message
     */
    @Override
    public void getAllMessage(String message) {
        restApiObservables.getChatBotMessage(message).subscribe(getSingleSubscriber(this));
    }

    private ChatBotContract.Presenter getPresenter() {
        return (ChatBotContract.Presenter) getPresenterContract();
    }
}

