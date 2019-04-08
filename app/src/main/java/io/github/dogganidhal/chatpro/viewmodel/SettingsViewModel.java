package io.github.dogganidhal.chatpro.viewmodel;

import com.google.firebase.auth.FirebaseAuth;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {

  private FirebaseAuth mAuth = FirebaseAuth.getInstance();

  public void logout() {
    this.mAuth.signOut();
  }

}
