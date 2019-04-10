package io.github.dogganidhal.chatpro.ui.activity;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dogganidhal.chatpro.R;
import io.github.dogganidhal.chatpro.ui.adapter.ChatAdapter;
import io.github.dogganidhal.chatpro.ui.view.RecyclerViewSpacingDecoration;
import io.github.dogganidhal.chatpro.viewmodel.ChatViewModel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.MenuItem;

import com.google.android.material.textfield.TextInputEditText;

public class ChatActivity extends BaseActivity {

  private static final String DISCUSSION_ID_EXTRA_KEY = "discussionId";
  private static final String DISCUSSION_TITLE_EXTRA_KEY = "discussionTitle";

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
    this.mRecyclerView.addItemDecoration(new RecyclerViewSpacingDecoration(24, 1));
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

  @OnClick(R.id.chat_attach_button)
  void onAttachButtonClicked() {

  }

  @OnClick(R.id.chat_send_button)
  void onSendButtonClicked() {
    Editable message = this.mMessageInputText.getText();
    if (message != null) {
      this.mViewModel.sendMessage(message.toString());
      this.mMessageInputText.getText().clear();
    }
  }
}
