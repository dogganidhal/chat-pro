package io.github.dogganidhal.chatpro.viewmodel;

import android.content.Intent;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

public class AuthViewModel extends ViewModel {

  private static String USERS_COLLECTION = "users";
  private static String USER_DOCUMENT_FULL_NAME_KEY = "fullName";
  private static String USER_DOCUMENT_EMAIL_KEY = "email";

  private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
  private FirebaseAuth mAuth = FirebaseAuth.getInstance();
  private CallbackManager mCallbackManager = CallbackManager.Factory.create();

  public Task<AuthResult> login(String email, String password) {
    return this.mAuth.signInWithEmailAndPassword(email, password);
  }

  public Task<AuthResult> signUp(String fullName, String email, String password) {
    return this.mAuth.createUserWithEmailAndPassword(email, password)
      .addOnSuccessListener(authResult -> {

        Map<String, String> userObject = new HashMap<>();
        userObject.put(USER_DOCUMENT_FULL_NAME_KEY, fullName);
        userObject.put(USER_DOCUMENT_EMAIL_KEY, email);

        authResult.getUser()
          .updateProfile(
            new UserProfileChangeRequest.Builder()
            .setDisplayName(fullName)
            .build()
          );

        this.mFirestore
          .collection(USERS_COLLECTION)
          .document(authResult.getUser().getUid())
          .set(userObject);

      });
  }

  public Task<AuthResult> loginWithGoogle(GoogleSignInAccount googleSignInAccount) {
    AuthCredential credential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
    return this.mAuth.signInWithCredential(credential)
      .addOnSuccessListener(authResult -> {

        FirebaseUser user = authResult.getUser();
        if (user != null) {
          Map<String, String> userObject = new HashMap<>();
          userObject.put(USER_DOCUMENT_FULL_NAME_KEY, user.getDisplayName());
          userObject.put(USER_DOCUMENT_EMAIL_KEY, user.getEmail());

          this.mFirestore
            .collection(USERS_COLLECTION)
            .document(user.getUid())
            .set(userObject);
        }

      });
  }

  public void setUpFacebookLoginButton(LoginButton facebookLoginButton, FacebookLoginCallback callback) {
    facebookLoginButton.registerCallback(this.mCallbackManager, new FacebookCallback<LoginResult>() {
      @Override
      public void onSuccess(LoginResult loginResult) {
        AuthCredential credential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
        mAuth.signInWithCredential(credential)
          .addOnSuccessListener(authResult -> {

            FirebaseUser user = authResult.getUser();
            if (user != null) {
              Map<String, String> userObject = new HashMap<>();
              userObject.put(USER_DOCUMENT_FULL_NAME_KEY, user.getDisplayName());
              userObject.put(USER_DOCUMENT_EMAIL_KEY, user.getEmail());

              mFirestore
                .collection(USERS_COLLECTION)
                .document(user.getUid())
                .set(userObject);
            }
            callback.onFacebookLoginSuccess(authResult);

          })
          .addOnFailureListener(callback::onFacebookLoginFailure);
      }

      @Override
      public void onCancel() { }

      @Override
      public void onError(FacebookException exception) {
        callback.onFacebookLoginFailure(exception);
      }
    });
  }

  public void loginWithFacebookActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    this.mCallbackManager.onActivityResult(requestCode, resultCode, data);
  }

  public interface FacebookLoginCallback {
    void onFacebookLoginSuccess(AuthResult authResult);
    void onFacebookLoginFailure(Exception exception);
  }

}
