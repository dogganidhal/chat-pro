package io.github.dogganidhal.chatpro.ui.activity;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dogganidhal.chatpro.R;
import io.github.dogganidhal.chatpro.ui.fragment.LoginFragment;
import io.github.dogganidhal.chatpro.ui.fragment.SignUpFragment;

public class AuthActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {

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
}
