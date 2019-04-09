package io.github.dogganidhal.chatpro.viewmodel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {

  private FirebaseAuth mAuth = FirebaseAuth.getInstance();

  public void logout() {
    this.mAuth.signOut();
  }

  public String getUserPhone() {
    FirebaseUser currentUser = this.mAuth.getCurrentUser();
    if (currentUser != null) {
      String phone = currentUser.getPhoneNumber();
      return phone != null && phone.length() > 0 ? phone : "Non renseigné";
    }
    return "Non renseigné";
  }

  public String getUserFullName() {
    FirebaseUser currentUser = this.mAuth.getCurrentUser();
    if (currentUser != null) {
      return currentUser.getDisplayName();
    }
    return "";
  }

  public String getUserEmail() {
    FirebaseUser currentUser = this.mAuth.getCurrentUser();
    if (currentUser != null) {
      return currentUser.getEmail();
    }
    return "";
  }

  public String getUserInitial() {
    FirebaseUser currentUser = this.mAuth.getCurrentUser();
    if (currentUser != null) {
      String displayName = currentUser.getDisplayName();
      if (displayName != null && displayName.length() > 0) {
        return displayName.substring(0, 1);
      }
    }
    return "?";
  }

}
