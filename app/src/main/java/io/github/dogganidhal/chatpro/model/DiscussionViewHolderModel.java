package io.github.dogganidhal.chatpro.model;

public class DiscussionViewHolderModel {

  private String discussionId;
  private String discussionTitle;
  private String formattedDiscussionTimestamp;
  private String discussionMembersInitial;
  private String lastMessageContent;

  public DiscussionViewHolderModel(String discussionId, String discussionTitle, String formattedDiscussionTimestamp, String discussionMembersInitial, String lastMessageContent) {
    this.discussionId = discussionId;
    this.discussionTitle = discussionTitle;
    this.formattedDiscussionTimestamp = formattedDiscussionTimestamp;
    this.discussionMembersInitial = discussionMembersInitial;
    this.lastMessageContent = lastMessageContent;
  }

  public String getDiscussionTitle() {
    return discussionTitle;
  }

  public void setDiscussionTitle(String discussionTitle) {
    this.discussionTitle = discussionTitle;
  }

  public String getFormattedDiscussionTimestamp() {
    return formattedDiscussionTimestamp;
  }

  public void setFormattedDiscussionTimestamp(String formattedDiscussionTimestamp) {
    this.formattedDiscussionTimestamp = formattedDiscussionTimestamp;
  }

  public String getDiscussionMembersInitial() {
    return discussionMembersInitial;
  }

  public void setDiscussionMembersInitial(String discussionMembersInitial) {
    this.discussionMembersInitial = discussionMembersInitial;
  }

  public String getLastMessageContent() {
    return lastMessageContent;
  }

  public void setLastMessageContent(String lastMessageContent) {
    this.lastMessageContent = lastMessageContent;
  }

  public String getDiscussionId() {
    return discussionId;
  }

  public void setDiscussionId(String discussionId) {
    this.discussionId = discussionId;
  }
}
