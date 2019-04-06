package io.github.dogganidhal.chatpro.view;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import io.github.dogganidhal.chatpro.R;

public class TitleView extends LinearLayout {

  private String title;
  private String subTitle;

  public TitleView(Context context) {
    super(context);
    this.title = null;
    this.subTitle = null;
    this.setup();
  }

  public TitleView(Context context, String title, String subTitle) {
    super(context);
    this.title = title;
    this.subTitle = subTitle;
    this.setup();
  }

  public TitleView(Context context, String title) {
    super(context);
    this.title = title;
    this.subTitle = null;
    this.setup();
  }

  private void setup() {
    this.setOrientation(LinearLayout.VERTICAL);
    this.setGravity(Gravity.CENTER_VERTICAL);
    if (this.title != null) {
      TextView titleTextView = new TextView(this.getContext());
      titleTextView.setText(this.title);
      titleTextView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
      titleTextView.setTextAppearance(R.style.RobotoMediumTitleText);
      this.addView(titleTextView);
    }
    if (this.subTitle != null) {
      TextView subTitleTextView = new TextView(this.getContext());
      subTitleTextView.setText(this.subTitle);
      subTitleTextView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
      this.addView(subTitleTextView);
    }
  }

}
