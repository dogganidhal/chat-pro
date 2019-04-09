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
import io.github.dogganidhal.chatpro.model.DiscussionViewHolderModel;
import io.github.dogganidhal.chatpro.model.Message;
import io.github.dogganidhal.chatpro.ui.fragment.DiscussionsFragment.OnDiscussionClickListener;
import io.github.dogganidhal.chatpro.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Discussion} and makes a call to the
 * specified {@link OnDiscussionClickListener}.
 */
public class DiscussionViewAdapter extends RecyclerView.Adapter<DiscussionViewAdapter.ViewHolder> {

  private List<DiscussionViewHolderModel> mDiscussions = new ArrayList<>();
  private final OnDiscussionClickListener mListener;

  public DiscussionViewAdapter(OnDiscussionClickListener listener) {
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
    DiscussionViewHolderModel discussion = this.mDiscussions.get(position);
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

  public void setDiscussions(List<DiscussionViewHolderModel> discussions) {
    this.mDiscussions = discussions;
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

    void setOnClickListener(View.OnClickListener onClickListener) {
      this.mView.setOnClickListener(onClickListener);
    }

    void setDiscussion(DiscussionViewHolderModel discussionViewHolderModel) {

      this.mMemberInitialTextView.setText(discussionViewHolderModel.getDiscussionMembersInitial());
      this.mDiscussionLastMessageAuthorTextView.setText(discussionViewHolderModel.getDiscussionTitle());
      this.mDiscussionLastMessageDateTextView.setText(discussionViewHolderModel.getFormattedDiscussionTimestamp());
      this.mDiscussionLastMessageContentTextView.setText(discussionViewHolderModel.getLastMessageContent());

    }

    ViewHolder(View view) {
      super(view);
      this.mView = view;
      ButterKnife.bind(this, this.mView);
    }
  }
}
