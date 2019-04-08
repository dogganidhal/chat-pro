package io.github.dogganidhal.chatpro.viewmodel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.ViewModel;

public class SignUpViewModel extends ViewModel {

  private static String USERS_COLLECTION = "users";
  private static String USER_DOCUMENT_FULL_NAME_KEY = "fullName";
  private static String USER_DOCUMENT_EMAIL_KEY = "email";

  private FirebaseAuth mAuth = FirebaseAuth.getInstance();
  private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

  public Task<AuthResult> signUp(String fullName, String email, String password) {
    return this.mAuth.createUserWithEmailAndPassword(email, password)
      .addOnSuccessListener(authResult -> {

        Map<String, Object> userObject = new HashMap<>();
        userObject.put(USER_DOCUMENT_FULL_NAME_KEY, fullName);
        userObject.put(USER_DOCUMENT_EMAIL_KEY, email);

        this.mFirestore
          .collection(USERS_COLLECTION)
          .add(userObject);

      });
  }

}
