package com.diptika.chatbot.network.api;

import com.diptika.chatbot.network.response.ChatBotMsgResponse;

import io.reactivex.Single;

public class RestApiObservables extends BaseObservable {
    private ChatBotApi chatBotApi = RetrofitProvider.getInstance().getRestApi();
    private final String apiKey="6nt5d1nJHkqbkphe";
    private final Integer chatBotID=63906;
    //This can be created when user signup in onboarding process so hardcoding here
    // as onboarding is not part of problem statement
    private final String externalID="user";


    public Single<ChatBotMsgResponse> getChatBotMessage(String message) {
        return chatBotApi
                .getChatBotMessage(apiKey,chatBotID,externalID,message)
                .compose(this.<ChatBotMsgResponse>applyCommonSchedulersSingle());
    }

}
