package com.diptika.chatbot.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diptika.chatbot.R;
import com.diptika.chatbot.network.response.ChatBotMsgResponse;
import com.diptika.chatbot.network.response.ChatMessageData;
import com.diptika.chatbot.network.response.SenderType;

import java.util.List;

/**
 * Created by Diptika Shukla on 21/03/19.
 */

public class ChatBotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ChatBotMsgResponse> chatBotMsgResponseList;

    public ChatBotAdapter(Context context, List<ChatBotMsgResponse> chatBotMsgResponseList) {
        this.context = context;
        this.chatBotMsgResponseList = chatBotMsgResponseList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chatbot_msg, null);
        return new ChatBotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((ChatBotViewHolder) viewHolder).bindData(chatBotMsgResponseList.get(position));
    }

    @Override
    public int getItemCount() {
        return chatBotMsgResponseList != null ? chatBotMsgResponseList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return chatBotMsgResponseList.get(position).getSender().equals(
                SenderType.SENDER_USER.getValue()) ?
                ChatBotActivity.VIEW_USER_MSG : ChatBotActivity.VIEW_CHATBOT_MSG;
    }


    public static class ChatBotViewHolder extends RecyclerView.ViewHolder {
        public View view;
        private TextView tvChatBotMsg;
        private TextView tvUserMsg;
        private TextView tvChatBotName;
        private RelativeLayout rlChatBotMsg;
        private RelativeLayout rlUserMsg;


        public ChatBotViewHolder(View itemView) {
            super(itemView);
            tvChatBotMsg = itemView.findViewById(R.id.tv_bot_msg);
            tvUserMsg = itemView.findViewById(R.id.tv_user_msg);
            rlChatBotMsg = itemView.findViewById(R.id.layout_bot_msg);
            rlUserMsg = itemView.findViewById(R.id.layout_user_msg);
            tvChatBotName = itemView.findViewById(R.id.tv_bot_name);
        }

        public void bindData(ChatBotMsgResponse chatBotMsgResponse) {
            SenderType senderType = SenderType.getType(chatBotMsgResponse.getSender());
            switch (senderType) {
                case SENDER_CHATBOT:
                    showChatBotView(chatBotMsgResponse.getMessage());
                    break;
                case SENDER_USER:
                    showUserView(chatBotMsgResponse.getMessage());
                    break;
            }
        }

        /**
         * Show chatbot view
         *
         * @param message
         */
        private void showChatBotView(ChatMessageData message) {
            if (message != null) {
                rlChatBotMsg.setVisibility(View.VISIBLE);
                rlUserMsg.setVisibility(View.GONE);
                tvChatBotMsg.setText(!TextUtils.isEmpty(message.getMessage()) ? message.getMessage() : "");
                tvChatBotName.setText(!TextUtils.isEmpty(message.getChatBotName()) ? message.getChatBotName() : "");
            }
        }

        /**
         * Show User input view
         *
         * @param message
         */
        private void showUserView(ChatMessageData message) {
            if (message != null) {
                rlChatBotMsg.setVisibility(View.GONE);
                rlUserMsg.setVisibility(View.VISIBLE);
                tvUserMsg.setText(message.getMessage());
            }
        }
    }
}
