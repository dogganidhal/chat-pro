package io.github.dogganidhal.chatpro.model;

public class ChatMessageViewHolderModel {

  private String authorName;
  private String content;
  private Boolean currentUserIsAuthor;

  public ChatMessageViewHolderModel(String authorName, String content, Boolean currentUserIsAuthor) {
    this.authorName = authorName;
    this.content = content;
    this.currentUserIsAuthor = currentUserIsAuthor;
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
}
