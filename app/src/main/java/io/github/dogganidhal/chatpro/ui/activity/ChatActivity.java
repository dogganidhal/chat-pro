package io.github.dogganidhal.chatpro.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dogganidhal.chatpro.R;
import io.github.dogganidhal.chatpro.model.ChatMessageViewHolderModel;
import io.github.dogganidhal.chatpro.ui.adapter.ChatAdapter;
import io.github.dogganidhal.chatpro.viewmodel.ChatViewModel;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class ChatActivity extends AppCompatActivity {

  private ChatViewModel mViewModel;

  @BindView(R.id.chat_input_edit_text)
  TextInputEditText mMessageInputText;

  @BindView(R.id.chat_recycler_view)
  RecyclerView mRecyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.activity_chat);

    ButterKnife.bind(this);

    this.mViewModel = new ChatViewModel("lpqWUkD3Ngv2qj0fKqru");
    ChatAdapter adapter = new ChatAdapter();

    this.mViewModel.messages.observe(this, messages -> {
      adapter.setMessageList(messages);
      adapter.notifyDataSetChanged();
    });

    this.mRecyclerView.setAdapter(adapter);
  }

  @OnClick(R.id.chat_attach_button)
  void onAttachButtonClicked() {

  }

  @OnClick(R.id.chat_send_button)
  void onSendButtonClicked() {

  }
}
