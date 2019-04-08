package io.github.dogganidhal.chatpro.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;


public class SplashActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Class launcherActivity;
    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
      launcherActivity = MainActivity.class;
    } else {
      launcherActivity = AuthActivity.class;
    }
    Intent intent = new Intent(this, launcherActivity);
    this.startActivity(intent);
  }
}
