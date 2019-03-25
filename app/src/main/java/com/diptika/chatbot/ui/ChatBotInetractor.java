package com.diptika.chatbot.ui;

import com.diptika.chatbot.database.RealmManager;
import com.diptika.chatbot.network.api.RestApiObservables;
import com.diptika.chatbot.network.common.BaseInteractor;
import com.diptika.chatbot.network.common.RxSingleObserverEvent;
import com.diptika.chatbot.network.response.ChatBotMsgResponse;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Diptika Shukla on 21/03/19.
 */

public class ChatBotInetractor extends BaseInteractor implements ChatBotContract.ApiContract,
        RxSingleObserverEvent<ChatBotMsgResponse> {

    private RestApiObservables restApiObservables = new RestApiObservables();
    private RealmResults<ChatBotMsgResponse> chatMessageDataRealmResults;
    private List<ChatBotMsgResponse> chatMessageDataArrayList = new ArrayList<>();

    private final RealmChangeListener<RealmResults<ChatBotMsgResponse>> changeListener = chatMessageDataList -> {
        try (Realm realmInstance = RealmManager.getInstance().getRealm()) {
            chatMessageDataArrayList.clear();
            for (ChatBotMsgResponse chatBotMsgResponse : chatMessageDataList) {
                if (chatBotMsgResponse.isValid()) {
                    ChatBotMsgResponse chatMessageData1 = realmInstance.copyFromRealm(chatBotMsgResponse);
                    chatMessageDataArrayList.add(chatMessageData1);

                }
            }

            if (getPresenter() != null) {
                getPresenter().onAllMessageRetrieved(chatMessageDataArrayList);
            }
        }
    };

    /**
     * Callback when api get success
     *
     * @param chatBotMsgResponse
     */
    @Override
    public void onSuccess(ChatBotMsgResponse chatBotMsgResponse) {
        chatBotMsgResponse.setMessageDelivered(true);
        storeMessageToDB(chatBotMsgResponse);
    }

    /**
     * Callback when api get failure
     *
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
     *
     * @param message
     */
    @Override
    public void getChatBotResponseMsg(String message) {
        restApiObservables.getChatBotMessage(message).subscribe(getSingleSubscriber(this));
    }

    /**
     * Fetch All Chatbot Message from realm db
     */

    @Override
    public void getAllMessageFromDb() {
        Realm realmInstance = RealmManager.getInstance().getRealm();
        chatMessageDataRealmResults = realmInstance.where(ChatBotMsgResponse.class)
                .findAllAsync();
        chatMessageDataRealmResults.addChangeListener(changeListener);
    }

    /**
     * Store all message into realm db
     *
     * @param chatBotMsgResponse
     */

    @Override
    public void storeMessageToDB(ChatBotMsgResponse chatBotMsgResponse) {
        try (Realm realmInstance = RealmManager.getInstance().getRealm()) {
            realmInstance.executeTransactionAsync((Realm realm) -> {
                        realm.insertOrUpdate(chatBotMsgResponse);
                    }
            );
        }

        getAllMessageFromDb();
    }

    @Override
    public void getAllUndeliveredMessage() {
        List<ChatBotMsgResponse> undeliveredMsgList = new ArrayList<>();
        Realm realmInstance = RealmManager.getInstance().getRealm();
        chatMessageDataRealmResults = realmInstance.where(ChatBotMsgResponse.class)
                .findAll();
        realmInstance.beginTransaction();
        for (int i = 0; i < chatMessageDataRealmResults.size(); i++) {
            if (!chatMessageDataRealmResults.get(i).isMessageDelivered()) {
                undeliveredMsgList.add(chatMessageDataRealmResults.get(i));
                chatMessageDataRealmResults.get(i).setMessageDelivered(true);
            }
        }
        realmInstance.commitTransaction();

        if (getPresenter() != null) {
            getPresenter().onAllUndeliveredMessageFetched(undeliveredMsgList);
        }

    }

    private ChatBotContract.Presenter getPresenter() {
        return (ChatBotContract.Presenter) getPresenterContract();
    }
}

