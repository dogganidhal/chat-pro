package io.github.dogganidhal.chatpro.ui.activity;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dogganidhal.chatpro.R;
import io.github.dogganidhal.chatpro.ui.adapter.ChatAdapter;
import io.github.dogganidhal.chatpro.viewmodel.ChatViewModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.MenuItem;

import com.cocosw.bottomsheet.BottomSheet;
import com.google.android.material.textfield.TextInputEditText;

public class ChatActivity extends BaseActivity {

  private static final String DISCUSSION_ID_EXTRA_KEY = "discussionId";
  private static final String DISCUSSION_TITLE_EXTRA_KEY = "discussionTitle";
  private static final int IMAGE_PICKER_REQUEST_CODE = 0xFF;
  private static final int VIDEO_PICKER_REQUEST_CODE = 0xFE;
  private static final int DOCUMENT_PICKER_REQUEST_CODE = 0xFD;
  private static final int CAMERA_PICKER_REQUEST_CODE = 0xFC;

  private ChatViewModel mViewModel;

  @BindView(R.id.chat_input_edit_text)
  TextInputEditText mMessageInputText;

  @BindView(R.id.chat_recycler_view)
  RecyclerView mRecyclerView;

  public static Intent getStartingIntentFromDiscussion(Context context, String discussionId, String discussionTitle) {
    Intent intent = new Intent(context, ChatActivity.class);
    intent.putExtra(DISCUSSION_ID_EXTRA_KEY, discussionId);
    intent.putExtra(DISCUSSION_TITLE_EXTRA_KEY, discussionTitle);
    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.activity_chat);

    ButterKnife.bind(this);

    String discussionId = this.getIntent().getStringExtra(DISCUSSION_ID_EXTRA_KEY);
    String discussionTitle = this.getIntent().getStringExtra(DISCUSSION_TITLE_EXTRA_KEY);

    this.setActionBarTitle(discussionTitle, "En ligne");
    this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    this.mViewModel = new ChatViewModel(discussionId);
    ChatAdapter adapter = new ChatAdapter();

    this.mViewModel.messages.observe(this, messages -> {
      adapter.setMessageList(messages);
      adapter.notifyDataSetChanged();
    });

    LinearLayoutManager manager = new LinearLayoutManager(this);
    manager.setReverseLayout(true);
    this.mRecyclerView.setLayoutManager(manager);
    this.mRecyclerView.setAdapter(adapter);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        // API 5+ solution
        this.onBackPressed();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
    super.onActivityResult(requestCode, resultCode, intent);
    if (resultCode != Activity.RESULT_OK || intent == null) return;
    switch (requestCode) {
      case IMAGE_PICKER_REQUEST_CODE:
        Uri imageUri = intent.getData();
        this.mViewModel.sendImageMessage(imageUri);
        break;
      case CAMERA_PICKER_REQUEST_CODE:
        Uri photoUri = intent.getData();
        this.mViewModel.sendImageMessage(photoUri);
        break;
      case DOCUMENT_PICKER_REQUEST_CODE:
        Uri documentUri = intent.getData();
        this.mViewModel.sendDocumentMessage(documentUri);
        break;
      case VIDEO_PICKER_REQUEST_CODE:
        Uri videoUri = intent.getData();
        this.mViewModel.sendVideoMessage(videoUri);
        break;
    }
  }

  @OnClick(R.id.chat_attach_button)
  void onAttachButtonClicked() {
    new BottomSheet.Builder(this)
      .sheet(R.menu.menu_attachment_picker)
      .listener((dialog, which) -> {
        Intent intent = null;
        int requestCode = IMAGE_PICKER_REQUEST_CODE;
        switch (which) {
          case R.id.attachment_menu_gallery:
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            requestCode = IMAGE_PICKER_REQUEST_CODE;
            break;
          case R.id.attachment_menu_camera:
            // TODO: Open the camera
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            requestCode = CAMERA_PICKER_REQUEST_CODE;
            break;
          case R.id.attachment_menu_document:
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("application/pdf");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            requestCode = DOCUMENT_PICKER_REQUEST_CODE;
            break;
          case R.id.attachment_menu_video:
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("video/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            requestCode = VIDEO_PICKER_REQUEST_CODE;
            break;
        }
        this.startActivityForResult(intent, requestCode);
      })
      .show();
  }

  @OnClick(R.id.chat_send_button)
  void onSendButtonClicked() {
    Editable message = this.mMessageInputText.getText();
    if (message != null) {
      this.mViewModel.sendTextMessage(message.toString());
      this.mMessageInputText.getText().clear();
    }
  }
}
