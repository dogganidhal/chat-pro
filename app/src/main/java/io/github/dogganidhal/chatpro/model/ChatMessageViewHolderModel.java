package io.github.dogganidhal.chatpro.model;

public class ChatMessageViewHolderModel {

  private String authorName;
  private String content;
  private Boolean currentUserIsAuthor;
  private String messageType;
  private String mediaUrl;

  public ChatMessageViewHolderModel(String authorName, String content, Boolean currentUserIsAuthor, String messageType, String mediaUrl) {
    this.authorName = authorName;
    this.content = content;
    this.currentUserIsAuthor = currentUserIsAuthor;
    this.messageType = messageType;
    this.mediaUrl = mediaUrl;
  }

  public String getAuthorName() {
    return authorName;
  }

  public String getContent() {
    return content;
  }

  public Boolean getCurrentUserIsAuthor() {
    return currentUserIsAuthor;
  }

  public String getMediaUrl() {
    return mediaUrl;
  }

  public String getMessageType() {
    return messageType;
  }
}
