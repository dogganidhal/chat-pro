package io.github.dogganidhal.chatpro.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.dogganidhal.chatpro.R;

public class MessageView extends FrameLayout {

  static final String MESSAGE_OWNER_CURRENT = "0";
  static final String MESSAGE_OWNER_OTHER = "1";

  private String mAuthorName = null;
  private String mMessageContent = "";
  private String mMessageTimeStamp = "";
  private String mMessageOwner = MESSAGE_OWNER_CURRENT;

  private Unbinder mUnBinder;

  @BindView(R.id.message_view_holder)
  View mMessageContentHolderView;

  @BindView(R.id.message_view_content_text_view)
  TextView mMessageContentTextView;

  @BindView(R.id.message_view_content_view)
  LinearLayout mContentView;

  @BindView(R.id.message_author_text_view)
  TextView mAuthorTextView;

  public MessageView(Context context) {
    super(context);
    init(context, null, 0);
  }

  public MessageView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs, 0);
  }

  public MessageView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init(context, attrs, defStyle);
  }

  private void init(Context context, AttributeSet attrs, int defStyle) {
    // Load attributes
    final TypedArray attributes = this.getContext()
      .obtainStyledAttributes(attrs, R.styleable.MessageView, defStyle, 0);

    this.mMessageContent = attributes.getString(R.styleable.MessageView_messageContent);
    this.mMessageTimeStamp = attributes.getString(R.styleable.MessageView_messageTimestamp);
    this.mMessageOwner = attributes.getString(R.styleable.MessageView_owner);
    this.mAuthorName = attributes.getString(R.styleable.MessageView_messageAuthorName);

    attributes.recycle();

    View contentView = LayoutInflater.from(context).inflate(R.layout.view_message, null);
    this.addView(contentView);

    this.mUnBinder = ButterKnife.bind(this);

    int background = this.mMessageOwner.equals(MESSAGE_OWNER_CURRENT) ?
      R.drawable.current_user_message_view_background :
      R.drawable.message_input_view_background;
    this.mMessageContentHolderView.setBackground(ContextCompat.getDrawable(context, background));
    int textColor = this.mMessageOwner.equals(MESSAGE_OWNER_CURRENT) ?
      R.color.white :
      R.color.textBlack;
    this.mMessageContentTextView.setTextColor(this.getResources().getColor(textColor));
    this.mMessageContentTextView.setText(this.mMessageContent);

    int gravity = this.mMessageOwner.equals(MESSAGE_OWNER_CURRENT) ?
      Gravity.END :
      Gravity.START;

    this.mContentView.setGravity(gravity);

    if (this.mAuthorName != null && this.mMessageOwner.equals(MESSAGE_OWNER_OTHER)) {
      this.mAuthorTextView.setText(this.mAuthorName);
    } else {
      this.mAuthorTextView.setVisibility(View.GONE);
    }

  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    this.mUnBinder.unbind();
  }

  public String getMessageContent() {
    return mMessageContent;
  }

  public void setMessageContent(String messageContent) {
    this.mMessageContent = messageContent;
  }

  public String getMessageTimeStamp() {
    return mMessageTimeStamp;
  }

  public void setMessageTimeStamp(String messageTimeStamp) {
    this.mMessageTimeStamp = messageTimeStamp;
  }

  public String getMessageOwner() {
    return mMessageOwner;
  }

  public void setMessageOwner(String messageOwner) {
    this.mMessageOwner = messageOwner;
  }

  public String getAuthorName() {
    return mAuthorName;
  }

  public void setAuthorName(String authorName) {
    this.mAuthorName = authorName;
  }
}
