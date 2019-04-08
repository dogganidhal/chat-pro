package io.github.dogganidhal.chatpro.ui.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dogganidhal.chatpro.R;
import io.github.dogganidhal.chatpro.model.Discussion;
import io.github.dogganidhal.chatpro.model.Message;
import io.github.dogganidhal.chatpro.ui.fragment.DiscussionsFragment.OnDiscussionClickListener;
import io.github.dogganidhal.chatpro.utils.DateUtils;

import java.util.List;
import java.util.Optional;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Discussion} and makes a call to the
 * specified {@link OnDiscussionClickListener}.
 */
public class DiscussionViewAdapter extends RecyclerView.Adapter<DiscussionViewAdapter.ViewHolder> {

  private final List<Discussion> mDiscussions;
  private final OnDiscussionClickListener mListener;

  public DiscussionViewAdapter(List<Discussion> items, OnDiscussionClickListener listener) {
    this.mDiscussions = items;
    this.mListener = listener;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.fragment_discussion, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    final Discussion discussion = this.mDiscussions.get(position);
    holder.setDiscussion(discussion);
    holder.setOnClickListener(view -> {
      if (mListener != null) {
        mListener.onDiscussionViewClicked(discussion);
      }
    });
  }

  @Override
  public int getItemCount() {
    return this.mDiscussions.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.discussion_member_initial_text_view)
    TextView mMemberInitialTextView;

    @BindView(R.id.discussion_last_message_author)
    TextView mDiscussionLastMessageAuthorTextView;

    @BindView(R.id.discussion_last_message_content)
    TextView mDiscussionLastMessageContentTextView;

    @BindView(R.id.discussion_last_message_date)
    TextView mDiscussionLastMessageDateTextView;

    private View mView;
    private View.OnClickListener mOnClickListener;

    void setOnClickListener(View.OnClickListener onClickListener) {
      this.mOnClickListener = onClickListener;
    }

    void setDiscussion(Discussion discussion) {
      // TODO: properly compute title and last message
      String discussionTitle = discussion.users.stream().findFirst().get().fullName;
      Optional<Message> lastMessage = discussion.messages
        .stream()
        .reduce((lhs, rhs) -> lhs.timestamp.compareTo(rhs.timestamp) > 0 ? lhs : rhs);

      this.mMemberInitialTextView.setText(discussion.users.stream().findFirst().get().fullName.substring(0, 1));
      this.mDiscussionLastMessageAuthorTextView.setText(discussionTitle);

      if (lastMessage.isPresent()) {
        this.mDiscussionLastMessageDateTextView.setText(DateUtils.formatDiscussionTimestamp(lastMessage.get().timestamp));
        this.mDiscussionLastMessageContentTextView.setText(lastMessage.get().content);
      }

    }

    ViewHolder(View view) {
      super(view);
      this.mView = view;
      ButterKnife.bind(this, this.mView);
      this.mView.setOnClickListener(this.mOnClickListener);
    }
  }
}
