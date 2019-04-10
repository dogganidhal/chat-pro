package io.github.dogganidhal.chatpro.ui.adapter;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.github.dogganidhal.chatpro.model.ChatMessageViewHolderModel;
import io.github.dogganidhal.chatpro.ui.view.MessageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

  private List<ChatMessageViewHolderModel> mMessageList = new ArrayList<>();

  @NonNull
  @Override
  public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    MessageView view = new MessageView(parent.getContext());
    return new ChatAdapter.ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.setMessage(this.mMessageList.get(position));
  }

  @Override
  public int getItemCount() {
    return this.mMessageList.size();
  }

  public void setMessageList(List<ChatMessageViewHolderModel> messageList) {
    this.mMessageList = messageList;
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    MessageView mMessageView;

    void setMessage(ChatMessageViewHolderModel message) {
      this.mMessageView.setMessageOwner(message.getCurrentUserIsAuthor() ? MessageView.MESSAGE_OWNER_CURRENT : MessageView.MESSAGE_OWNER_OTHER);
      this.mMessageView.setAuthorName(message.getAuthorName());
      this.mMessageView.setMessageContent(message.getContent());
    }

    ViewHolder(@NonNull MessageView itemView) {
      super(itemView);
      this.mMessageView = itemView;
    }

  }
}
