package io.github.dogganidhal.chatpro.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.github.dogganidhal.chatpro.model.ChatMessageViewHolderModel;
import io.github.dogganidhal.chatpro.ui.view.MessageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

  private List<ChatMessageViewHolderModel> mMessageList = new ArrayList<>();
  private PdfFileMessageClickListener mOnPdfMediaClickListener;

  @NonNull
  @Override
  public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    MessageView view = new MessageView(parent.getContext());
    RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    view.setLayoutParams(lp);
    return new ChatAdapter.ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.setMessage(this.mMessageList.get(position));
    holder.setOnPdfMediaClickListener(view -> {
      if (this.mOnPdfMediaClickListener != null) {
        this.mOnPdfMediaClickListener.OnPdfFileMessageClicked(this.mMessageList.get(position).getMediaUrl());
      }
    });
  }

  @Override
  public int getItemCount() {
    return this.mMessageList.size();
  }

  public void setMessageList(List<ChatMessageViewHolderModel> messageList) {
    this.mMessageList = messageList;
  }

  public void setOnPdfMediaClickListener(PdfFileMessageClickListener mOnPdfMediaClickListener) {
    this.mOnPdfMediaClickListener = mOnPdfMediaClickListener;
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    MessageView mMessageView;

    void setMessage(ChatMessageViewHolderModel message) {
      this.mMessageView.setMessageOwner(message.getCurrentUserIsAuthor() ? MessageView.MESSAGE_OWNER_CURRENT : MessageView.MESSAGE_OWNER_OTHER);
      this.mMessageView.setAuthorName(message.getAuthorName());
      this.mMessageView.setMessageContent(message.getContent());
      this.mMessageView.setMediaUrl(message.getMediaUrl());
      this.mMessageView.setMessageType(message.getMessageType());
    }

    ViewHolder(@NonNull MessageView itemView) {
      super(itemView);
      this.mMessageView = itemView;
    }

    public void setOnPdfMediaClickListener(View.OnClickListener mOnPdfMediaClickListener) {
      this.mMessageView.setOnPdfMediaClickListener(mOnPdfMediaClickListener);
    }
  }

  public interface PdfFileMessageClickListener {
    void OnPdfFileMessageClicked(String url);
  }
}
