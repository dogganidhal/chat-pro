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
import io.github.dogganidhal.chatpro.model.User;
import io.github.dogganidhal.chatpro.ui.fragment.ContactsFragment;
import io.github.dogganidhal.chatpro.ui.fragment.ContactsFragment.OnContactClickListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link User} and makes a call to the
 * specified {@link ContactsFragment.OnContactClickListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ContactsViewAdapter extends RecyclerView.Adapter<ContactsViewAdapter.ViewHolder> {

  private final List<User> mContacts;
  private final OnContactClickListener mListener;

  public ContactsViewAdapter(List<User> items, ContactsFragment.OnContactClickListener listener) {
    mContacts = items;
    mListener = listener;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.fragment_contact, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
    holder.setContact(this.mContacts.get(position));
    holder.mView.setOnClickListener(v -> {
      if (null != mListener) {
        mListener.onContactViewClicked(holder.mContact);
      }
    });
  }

  @Override
  public int getItemCount() {
    return mContacts.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.contact_initial_text_view)
    TextView mContactInitialTextView;

    @BindView(R.id.contact_name_text_view)
    TextView mContactNameTextView;

    @BindView(R.id.user_status_view)
    View mContactStatusView;

    View mView;
    User mContact;

    void setContact(User contact) {
      this.mContact = contact;
      this.mContactNameTextView.setText(contact.getFullName());
      this.mContactInitialTextView.setText(contact.getFullName().substring(0, 1));
    }

    ViewHolder(View view) {
      super(view);
      this.mView = view;
      ButterKnife.bind(this, this.mView);
    }
  }
}
