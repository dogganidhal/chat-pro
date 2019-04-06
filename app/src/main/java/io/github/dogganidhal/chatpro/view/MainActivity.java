package io.github.dogganidhal.chatpro.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dogganidhal.chatpro.R;

import android.os.PersistableBundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

  private DiscussionsFragment mDiscussionFragment;
  private ContactsFragment mContactsFragment;
  private SettingsFragment mSettingsFragment;

  @BindView(R.id.navigation)
  BottomNavigationView mBottomNavigationView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    this.setupBottomNavigationView();
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    Fragment contentFragment = null;
    switch (item.getItemId()) {
      case R.id.navigation_home:
        contentFragment = this.getDiscussionFragment();
        break;
      case R.id.navigation_dashboard:
        contentFragment = this.getContactsFragment();
        break;
      case R.id.navigation_notifications:
        contentFragment = this.getSettingsFragment();
        break;
    }
    if (contentFragment != null) {
      this.getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.activity_main_content_fragment, contentFragment)
        .commit();
      return true;
    }
    return false;
  }

  private void setupBottomNavigationView() {
    this.getSupportFragmentManager()
      .beginTransaction()
      .replace(R.id.activity_main_content_fragment, this.getDiscussionFragment())
      .commit();
    this.mBottomNavigationView.setOnNavigationItemSelectedListener(this);
  }

  /*
   * Lazy fragment initialization
   */

  DiscussionsFragment getDiscussionFragment() {
    if (this.mDiscussionFragment == null) {
      this.mDiscussionFragment = new DiscussionsFragment();
    }
    return this.mDiscussionFragment;
  }

  ContactsFragment getContactsFragment() {
    if (this.mContactsFragment == null) {
      this.mContactsFragment = new ContactsFragment();
    }
    return this.mContactsFragment;
  }

  SettingsFragment getSettingsFragment() {
    if (this.mSettingsFragment == null) {
      this.mSettingsFragment = new SettingsFragment();
    }
    return this.mSettingsFragment;
  }

}
