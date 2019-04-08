package io.github.dogganidhal.chatpro.ui.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dogganidhal.chatpro.R;
import io.github.dogganidhal.chatpro.ui.activity.MainActivity;
import io.github.dogganidhal.chatpro.viewmodel.AuthViewModel;

public class SignUpFragment extends Fragment implements AuthViewModel.FacebookLoginCallback {

  public final static int GOOGLE_SIGN_UP_RESULT_CODE = 0xFE;

  private AuthViewModel mViewModel;

  @BindView(R.id.sign_up_fragment_full_name_text_field)
  TextInputEditText mFullNameField;

  @BindView(R.id.sign_up_fragment_email_text_field)
  TextInputEditText mEmailField;

  @BindView(R.id.sign_up_fragment_password_text_field)
  TextInputEditText mPasswordField;

  @BindView(R.id.sign_up_fragment_facebook_login)
  LoginButton mFacebookLoginButton;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.sign_up_fragment, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    this.mViewModel = ViewModelProviders.of(this.getActivity()).get(AuthViewModel.class);
    this.mFacebookLoginButton.setReadPermissions("email", "public_profile");
    this.mViewModel.setUpFacebookLoginButton(this.mFacebookLoginButton, this);
  }

  @OnClick(R.id.sign_up_fragment_sign_up_button)
  void onSignUpClicked() {
    Editable fullName = this.mFullNameField.getText();
    Editable email = this.mEmailField.getText();
    Editable password = this.mPasswordField.getText();
    if (fullName !=  null && fullName.length() > 0 && email != null && email.length() > 0 &&
      password != null && password.length() > 0) {
      this.mViewModel.signUp(fullName.toString(), email.toString(), password.toString())
        .addOnSuccessListener(authResult ->  {
          Intent intent = new Intent(getContext(), MainActivity.class);
          startActivity(intent);
        })
        .addOnFailureListener(exception -> {
          Toast.makeText(this.getContext(), exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        });
    } else {
      Toast.makeText(this.getContext(), "Tous les champs sont obligatoires", Toast.LENGTH_LONG).show();
    }
  }

  @OnClick(R.id.sign_up_fragment_google_login)
  void onGoogleSignUpClicked() {
    GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestIdToken("50125738530-ni5i154koa18ik1ou5uiahljkjtbct1f.apps.googleusercontent.com")
      .requestEmail()
      .requestProfile()
      .build();

    Context context = this.getContext();
    Activity activity = this.getActivity();
    if (context != null && activity != null) {
      GoogleSignInClient client = GoogleSignIn.getClient(context, options);
      activity.startActivityForResult(client.getSignInIntent(), GOOGLE_SIGN_UP_RESULT_CODE);
    }
  }

  @Override
  public void onFacebookLoginSuccess(AuthResult authResult) {
    Intent intent = new Intent(this.getContext(), MainActivity.class);
    Activity activity = this.getActivity();
    if (activity != null) {
      activity.startActivity(intent);
    }
  }

  @Override
  public void onFacebookLoginFailure(Exception exception) {
    Toast.makeText(this.getContext(), exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
  }
}
