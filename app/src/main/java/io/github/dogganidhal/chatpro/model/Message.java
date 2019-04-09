package io.github.dogganidhal.chatpro.model;

import com.google.firebase.Timestamp;

public class Message {

  private String authorId;
  private String content;
  private String discussionId;
  private String mediaUrl;
  private String messageType;
  private Timestamp timestamp;

  public Message() {

  }

  public Message(String authorId, String content, String discussionId, String mediaUrl, String messageType, Timestamp timestamp) {
    this.authorId = authorId;
    this.content = content;
    this.discussionId = discussionId;
    this.mediaUrl = mediaUrl;
    this.messageType = messageType;
    this.timestamp = timestamp;
  }

  public String getAuthorId() {
    return authorId;
  }

  public void setAuthorId(String authorId) {
    this.authorId = authorId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getDiscussionId() {
    return discussionId;
  }

  public void setDiscussionId(String discussionId) {
    this.discussionId = discussionId;
  }

  public String getMediaUrl() {
    return mediaUrl;
  }

  public void setMediaUrl(String mediaUrl) {
    this.mediaUrl = mediaUrl;
  }

  public String getMessageType() {
    return messageType;
  }

  public void setMessageType(String messageType) {
    this.messageType = messageType;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

}
