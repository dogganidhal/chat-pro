package io.github.dogganidhal.chatpro.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.dogganidhal.chatpro.R;
import io.github.dogganidhal.chatpro.model.User;
import io.github.dogganidhal.chatpro.ui.adapter.ContactsViewAdapter;
import io.github.dogganidhal.chatpro.viewmodel.ContactsViewModel;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnContactClickListener}
 * interface.
 */
public class ContactsFragment extends Fragment {

  private ContactsViewModel mViewModel;
  private OnContactClickListener mListener;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public ContactsFragment() {
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
    this.mViewModel = ViewModelProviders.of(this).get(ContactsViewModel.class);

    // Set the adapter
    if (view instanceof RecyclerView) {
      Context context = view.getContext();
      RecyclerView recyclerView = (RecyclerView) view;
      ContactsViewAdapter adapter = new ContactsViewAdapter(mListener);
      recyclerView.setLayoutManager(new LinearLayoutManager(context));
      this.mViewModel.contacts.observe(this, users -> {
        adapter.setContacts(users);
        adapter.notifyDataSetChanged();
      });
      recyclerView.setAdapter(adapter);
    }
    return view;
  }

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    if (context instanceof OnContactClickListener) {
      mListener = (OnContactClickListener) context;
    } else {
      throw new RuntimeException(context.toString() + " must implement OnContactClickListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    this.mListener = null;
  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p/>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnContactClickListener {
    // TODO: Update argument type and name
    void onContactViewClicked(User contact);
  }
}
