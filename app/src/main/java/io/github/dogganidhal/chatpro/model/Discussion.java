package io.github.dogganidhal.chatpro.model;

import java.util.List;

public class Discussion {

  private String id;
  private List<Message> messages;
  private List<DiscussionMember> members;

  public List<Message> getMessages() {
    return messages;
  }

  public void setMessages(List<Message> messages) {
    this.messages = messages;
  }

  public List<DiscussionMember> getMembers() {
    return members;
  }

  public void setMembers(List<DiscussionMember> members) {
    this.members = members;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
