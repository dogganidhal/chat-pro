package io.github.dogganidhal.chatpro.ui.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dogganidhal.chatpro.R;
import io.github.dogganidhal.chatpro.ui.adapter.ChatAdapter;
import io.github.dogganidhal.chatpro.viewmodel.ChatViewModel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;

public class ChatActivity extends BaseActivity {

  private static final String DISCUSSION_ID_EXTRA_KEY = "discussionId";

  private ChatViewModel mViewModel;

  @BindView(R.id.chat_input_edit_text)
  TextInputEditText mMessageInputText;

  @BindView(R.id.chat_recycler_view)
  RecyclerView mRecyclerView;

  public static Intent getStartingIntentFromDiscussion(Context context, String discussionId) {
    Intent intent = new Intent(context, ChatActivity.class);
    intent.putExtra(DISCUSSION_ID_EXTRA_KEY, discussionId);
    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.activity_chat);

    ButterKnife.bind(this);

    String discussionId = this.getIntent().getStringExtra(DISCUSSION_ID_EXTRA_KEY);

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
  public void onBackPressed() {
    super.onBackPressed();
  }

  @OnClick(R.id.chat_attach_button)
  void onAttachButtonClicked() {

  }

  @OnClick(R.id.chat_send_button)
  void onSendButtonClicked() {

  }
}
