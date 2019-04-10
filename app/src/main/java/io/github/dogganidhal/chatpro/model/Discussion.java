package io.github.dogganidhal.chatpro.model;

import java.util.List;
import java.util.Map;

public class Discussion {

  private String id;
  private Message lastMessage;
  private Map<String, String> members;

  public Discussion() {
  }

  public Discussion(String id, Message lastMessage, Map<String, String> members) {
    this.id = id;
    this.lastMessage = lastMessage;
    this.members = members;
  }

  public Discussion(Map<String, String> members) {
    this.members = members;
  }

  public Message getLastMessage() {
    return lastMessage;
  }

  public void setLastMessage(Message lastMessage) {
    this.lastMessage = lastMessage;
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
