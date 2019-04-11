package io.github.dogganidhal.chatpro.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dogganidhal.chatpro.R;
import io.github.dogganidhal.chatpro.utils.RoundTransform;


public class MessageView extends FrameLayout {

  public static final String MESSAGE_TYPE_TEXT = "text";
  public static final String MESSAGE_TYPE_VIDEO = "video";
  public static final String MESSAGE_TYPE_IMAGE = "image";

  public static final String MESSAGE_OWNER_CURRENT = "0";
  public static final String MESSAGE_OWNER_OTHER = "1";

  private String mAuthorName = null;
  private String mMessageContent = "";
  private String mMessageOwner = MESSAGE_OWNER_CURRENT;
  private String mMediaUrl = null;
  private String mMessageType = MESSAGE_TYPE_TEXT;

  @BindView(R.id.message_view_holder)
  LinearLayout mMessageContentHolderView;

  @BindView(R.id.message_bubble_view)
  LinearLayout mMessageBubbleView;

  @BindView(R.id.message_view_content_text_view)
  TextView mMessageContentTextView;

  @BindView(R.id.message_view_content_view)
  LinearLayout mContentView;

  @BindView(R.id.message_author_text_view)
  TextView mAuthorTextView;

  @BindView(R.id.message_view_image_view)
  ImageView mImageView;

  public MessageView(Context context) {
    super(context);
    this.init(context, null, 0);
  }

  public MessageView(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.init(context, attrs, 0);
  }

  public MessageView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    this.init(context, attrs, defStyle);
  }

  private void init(Context context, AttributeSet attrs, int defStyle) {
    // Load attributes
    final TypedArray attributes = this.getContext()
      .obtainStyledAttributes(attrs, R.styleable.MessageView, defStyle, 0);

    this.mMessageContent = attributes.getString(R.styleable.MessageView_messageContent);
    this.mAuthorName = attributes.getString(R.styleable.MessageView_messageAuthorName);
    if (attributes.getString(R.styleable.MessageView_owner) != null) {
      this.mMessageOwner = attributes.getString(R.styleable.MessageView_owner);
    }

    attributes.recycle();

    View contentView = LayoutInflater.from(context).inflate(R.layout.view_message, null);
    this.addView(contentView);

    ButterKnife.bind(this);

    this.resetViewContent();

  }

  private void resetViewContent() {

    int background = this.mMessageOwner.equals(MESSAGE_OWNER_CURRENT) ?
      R.drawable.current_user_message_view_background :
      R.drawable.message_input_view_background;
    this.mMessageContentHolderView.setBackground(ContextCompat.getDrawable(this.getContext(), background));
    int textColor = this.mMessageOwner.equals(MESSAGE_OWNER_CURRENT) ?
      R.color.white :
      R.color.textBlack;
    this.mMessageContentTextView.setTextColor(this.getResources().getColor(textColor));
    this.mMessageContentTextView.setText(this.mMessageContent);

    int contentViewGravity = this.mMessageOwner.equals(MESSAGE_OWNER_CURRENT) ?
      Gravity.END :
      Gravity.START;

    this.mContentView.setGravity(contentViewGravity);
    this.mMessageContentHolderView.setGravity(contentViewGravity);
    this.mMessageBubbleView.setGravity(contentViewGravity);

    if (this.mAuthorName != null && this.mMessageOwner.equals(MESSAGE_OWNER_OTHER)) {
      this.mAuthorTextView.setVisibility(View.VISIBLE);
      this.mAuthorTextView.setText(this.mAuthorName);
    } else {
      this.mAuthorTextView.setVisibility(View.GONE);
    }

    if (this.mMessageType.equals(MESSAGE_TYPE_IMAGE) && this.mMediaUrl != null) {
      this.mMessageContentTextView.setVisibility(View.GONE);
      this.mImageView.setVisibility(View.VISIBLE);
      this.mMessageContentHolderView.setPadding(0, 0, 0, 0);
      this.mMessageContentHolderView.invalidate();
//      this.mMessageContentHolderView.setBackgroundColor(Color.TRANSPARENT);
      Picasso.get().load(this.mMediaUrl).transform(new RoundTransform(16)).into(this.mImageView);
    }
    // TODO: Do the same with document and video

  }

  public void setMessageContent(String messageContent) {
    this.mMessageContent = messageContent;
    this.resetViewContent();
  }

  public void setMessageOwner(String messageOwner) {
    this.mMessageOwner = messageOwner;
    this.resetViewContent();
  }

  public void setAuthorName(String authorName) {
    this.mAuthorName = authorName;
    this.resetViewContent();
  }

  public void setMediaUrl(String mMediaUrl) {
    this.mMediaUrl = mMediaUrl;
    this.resetViewContent();
  }

  public void setMessageType(String messageType) {
    this.mMessageType = messageType;
    this.resetViewContent();
  }
}
