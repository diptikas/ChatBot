package com.diptika.chatbot.network.api;

import com.diptika.chatbot.network.response.ChatBotMsgResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Diptika Shukla on 21/03/19.
 */

public interface ChatBotApi {

    @GET("/api/chat")
    Single<ChatBotMsgResponse> getChatBotMessage(@Query("apiKey") String apiKey,
                                                 @Query("chatBotID") Integer chatBotID,
                                                 @Query("externalID") String externalID,
                                                 @Query("message") String message);
}
