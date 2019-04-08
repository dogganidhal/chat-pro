package io.github.dogganidhal.chatpro.ui.activity;

import android.annotation.SuppressLint;
import android.view.Gravity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import io.github.dogganidhal.chatpro.ui.view.TitleView;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

  protected void setActionBarTitle(String title, String subtitle) {
    ActionBar.LayoutParams params = new ActionBar.LayoutParams(
      ActionBar.LayoutParams.WRAP_CONTENT,
      ActionBar.LayoutParams.MATCH_PARENT,
      Gravity.CENTER
    );
    ActionBar actionBar = this.getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
      actionBar.setCustomView(new TitleView(this, title, subtitle), params);
    }
  }

}
