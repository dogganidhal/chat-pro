package io.github.dogganidhal.chatpro.model;

import java.util.List;
import java.util.Map;

public class Discussion {

  private String id;
  private List<Message> messages;
  private Map<String, String> members;

  public List<Message> getMessages() {
    return messages;
  }

  public void setMessages(List<Message> messages) {
    this.messages = messages;
  }

  public Map<String, String> getMembers() {
    return members;
  }

  public void setMembers(Map<String, String> members) {
    this.members = members;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
