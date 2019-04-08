package io.github.dogganidhal.chatpro.viewmodel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

  private FirebaseAuth mAuth = FirebaseAuth.getInstance();

  public Task<AuthResult> login(String email, String password) {
    return this.mAuth.signInWithEmailAndPassword(email, password);
  }

}
