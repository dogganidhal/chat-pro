package io.github.dogganidhal.chatpro.model;

import com.google.firebase.Timestamp;

public class Message {

  public static final String MESSAGE_TYPE_TEXT = "text";
  public static final String MESSAGE_TYPE_IMAGE = "image";
  public static final String MESSAGE_TYPE_VIDEO = "video";
  public static final String MESSAGE_TYPE_DOCUMENT = "document";

  private User author;
  private String content;
  private String discussionId;
  private String mediaUrl;
  private String messageType;
  private Timestamp timestamp;

  public Message() {

  }

  public Message(User author, String content, String discussionId, String mediaUrl, String messageType, Timestamp timestamp) {
    this.author = author;
    this.content = content;
    this.discussionId = discussionId;
    this.mediaUrl = mediaUrl;
    this.messageType = messageType;
    this.timestamp = timestamp;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
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
