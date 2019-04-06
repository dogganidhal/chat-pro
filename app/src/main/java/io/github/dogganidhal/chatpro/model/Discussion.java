package io.github.dogganidhal.chatpro.model;

import java.util.List;

public class Discussion {

  public List<Message> messages;
  public List<User> users;

  public Discussion(List<Message> messages, List<User> users) {
    this.messages = messages;
    this.users = users;
  }

  public List<Message> getMessages() {
    return messages;
  }

  public void setMessages(List<Message> messages) {
    this.messages = messages;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }
}
