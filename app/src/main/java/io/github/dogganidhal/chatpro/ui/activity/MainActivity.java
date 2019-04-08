package io.github.dogganidhal.chatpro.ui.activity;

import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dogganidhal.chatpro.R;
import io.github.dogganidhal.chatpro.model.Discussion;
import io.github.dogganidhal.chatpro.model.User;
import io.github.dogganidhal.chatpro.ui.fragment.ContactsFragment;
import io.github.dogganidhal.chatpro.ui.fragment.DiscussionsFragment;
import io.github.dogganidhal.chatpro.ui.fragment.SettingsFragment;

import android.view.MenuItem;

public class MainActivity extends BaseActivity
  implements BottomNavigationView.OnNavigationItemSelectedListener,
  DiscussionsFragment.OnDiscussionClickListener,
    ContactsFragment.OnContactClickListener {

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
    this.setup();
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    Resources resource = this.getResources();
    Fragment contentFragment = null;
    switch (item.getItemId()) {
      case R.id.navigation_discussions:
        contentFragment = this.getDiscussionFragment();
        this.setActionBarTitle(resource.getString(R.string.title_discussions), null);
        break;
      case R.id.navigation_contacts:
        contentFragment = this.getContactsFragment();
        this.setActionBarTitle(resource.getString(R.string.title_contacts), null);
        break;
      case R.id.navigation_settings:
        contentFragment = this.getSettingsFragment();
        this.setActionBarTitle(resource.getString(R.string.title_settings), null);
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

  private void setup() {
    this.setActionBarTitle(this.getResources().getString(R.string.title_discussions), null);
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

  @Override
  public void onDiscussionViewClicked(Discussion discussion) {
    System.out.println(discussion);
  }

  @Override
  public void onContactViewClicked(User contact) {
    System.out.println(contact);
  }
}
