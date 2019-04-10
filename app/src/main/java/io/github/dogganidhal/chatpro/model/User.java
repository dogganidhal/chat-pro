package io.github.dogganidhal.chatpro.model;

public class User {

  private String id;
  private String email;
  private String fullName;

  public User() {

  }

  public User(String id, String fullName) {
    this.id = id;
    this.fullName = fullName;
  }

  public User(String id, String email, String fullName) {
    this.id = id;
    this.email = email;
    this.fullName = fullName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
