package io.github.dogganidhal.chatpro.ui.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.LayoutInflaterFactory;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dogganidhal.chatpro.R;
import io.github.dogganidhal.chatpro.model.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DiscussionGroupDialog extends Dialog {

  private Activity mActivity;
  private List<User> mContacts;
  private List<User> mSelectedContacts = new ArrayList<>();
  private OnGroupMembersConfirmed mOnGroupMembersConfirmed;

  @BindView(R.id.group_member_search_text_input)
  AutoCompleteTextView mSearchContactTextInput;

  @BindView(R.id.group_members_chip_group)
  ChipGroup mChipGroup;

  public DiscussionGroupDialog(@NonNull Context context, Activity activity, List<User> contacts) {
    super(context);
    this.mActivity = activity;
    this.mContacts = contacts;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_discussion_group_dialog);
    ButterKnife.bind(this);
    ArrayAdapter<User> autocompleteAdapter = new ContactAdapter(this.mActivity, this.mContacts);
    this.mSearchContactTextInput.setAdapter(autocompleteAdapter);
    this.mSearchContactTextInput.setOnItemClickListener(this::onChipSelected);

  }

  @OnClick(R.id.group_members_confirm_group_button)
  void onConfirmButtonClicked() {
    if (this.mOnGroupMembersConfirmed != null) {
      this.mOnGroupMembersConfirmed.onSelectMembers(this.mSelectedContacts);
    }
    this.dismiss();
  }

  @OnClick(R.id.group_members_cancel_button)
  void onCancelButtonClicked() {
    this.dismiss();
  }

  public void setmOnGroupMembersConfirmed(OnGroupMembersConfirmed mOnGroupMembersConfirmed) {
    this.mOnGroupMembersConfirmed = mOnGroupMembersConfirmed;
  }

  private void onChipSelected(AdapterView<?> parent, View view, int position, long id) {
    User selectedUser = (User) parent.getItemAtPosition(position);
    Chip chip = new Chip(this.getContext());
    chip.setBackground(ContextCompat.getDrawable(this.getContext(), R.drawable.message_input_view_background));
    chip.setText(selectedUser.getFullName());
    chip.setChipIcon(ContextCompat.getDrawable(this.getContext(), R.drawable.ic_person_black_24dp));
    chip.setClickable(true);
    chip.setCheckable(false);
    chip.setCloseIconVisible(true);
    chip.setOnCloseIconClickListener(v -> {
      this.mContacts.remove(selectedUser);
      this.mChipGroup.removeView(v);
    });
    this.mChipGroup.addView(chip);
    this.mSelectedContacts.add(selectedUser);
    this.mSearchContactTextInput.getText().clear();
  }

  @FunctionalInterface
  public interface OnGroupMembersConfirmed {
    void onSelectMembers(List<User> members);
  }

  private class ContactAdapter extends ArrayAdapter<User> {

    private Context mContext;
    private List<User> mContacts;

    ContactAdapter(@NonNull Context context, @NonNull List<User> objects) {
      super(context, android.R.layout.simple_dropdown_item_1line, objects);
      this.mContext = context;
      this.mContacts = objects;
    }

    @NonNull
    @Override
    public Filter getFilter() {
      return new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
          FilterResults results = new FilterResults();
          results.values = mContacts
            .stream()
            .filter(contact -> contact.getFullName().toLowerCase().contains(constraint.toString().toLowerCase()))
            .collect(Collectors.toList());
          return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
          notifyDataSetChanged();
        }
      };
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

      if (convertView == null) {
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        convertView = inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
      }

      TextView textView = convertView.findViewById(android.R.id.text1);
      textView.setText(this.mContacts.get(position).getFullName());

      return convertView;
    }
  }

}
