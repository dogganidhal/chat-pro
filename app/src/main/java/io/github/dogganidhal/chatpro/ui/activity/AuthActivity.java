package io.github.dogganidhal.chatpro.ui.activity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.material.tabs.TabLayout;

import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dogganidhal.chatpro.R;
import io.github.dogganidhal.chatpro.ui.fragment.LoginFragment;
import io.github.dogganidhal.chatpro.ui.fragment.SignUpFragment;
import io.github.dogganidhal.chatpro.viewmodel.AuthViewModel;

public class AuthActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {

  private AuthViewModel mViewModel;
  private LoginFragment mLoginFragment;
  private SignUpFragment mSignUpFragment;

  @BindView(R.id.auth_tab_layout)
  TabLayout mTabLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_auth);
    ButterKnife.bind(this);
    this.setActionBarTitle("Authentification", null);
    this.mTabLayout.addOnTabSelectedListener(this);
    this.mViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);
    this.getSupportFragmentManager()
      .beginTransaction()
      .replace(R.id.activity_auth_content_fragment, this.getLoginFragment())
      .commit();
  }

  @Override
  public void onTabSelected(TabLayout.Tab tab) {
    Fragment contentFragment;
    switch (tab.getPosition()) {
      case 0:
        contentFragment = this.getLoginFragment();
        break;
      default:
        contentFragment =  this.getSignUpFragment();
        break;
    }
    this.getSupportFragmentManager()
      .beginTransaction()
      .replace(R.id.activity_auth_content_fragment, contentFragment)
      .commit();
  }

  @Override
  public void onTabUnselected(TabLayout.Tab tab) {

  }

  @Override
  public void onTabReselected(TabLayout.Tab tab) {

  }

  @NonNull
  private LoginFragment getLoginFragment() {
    if (this.mLoginFragment == null) {
      this.mLoginFragment = new LoginFragment();
    }
    return this.mLoginFragment;
  }

  @NonNull
  private SignUpFragment getSignUpFragment() {
    if (this.mSignUpFragment == null) {
      this.mSignUpFragment = new SignUpFragment();
    }
    return this.mSignUpFragment;
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case LoginFragment.GOOGLE_LOGIN_RESULT_CODE:
      case SignUpFragment.GOOGLE_SIGN_UP_RESULT_CODE:
        GoogleSignIn.getSignedInAccountFromIntent(data)
          .addOnSuccessListener(googleSignInAccount -> mViewModel.loginWithGoogle(googleSignInAccount)
            .addOnSuccessListener(authResult -> {
              Intent intent = new Intent(this, MainActivity.class);
              this.startActivity(intent);
            })
            .addOnFailureListener(exception -> {
              Toast.makeText(this, exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }))
          .addOnFailureListener(exception -> {
            Toast.makeText(this, exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
          });
        break;
      default:
        this.mViewModel.loginWithFacebookActivityResult(requestCode, resultCode, data);
        break;
    }

  }
}
