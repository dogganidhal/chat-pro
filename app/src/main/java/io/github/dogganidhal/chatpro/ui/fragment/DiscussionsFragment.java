package io.github.dogganidhal.chatpro.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dogganidhal.chatpro.R;
import io.github.dogganidhal.chatpro.model.DiscussionViewHolderModel;
import io.github.dogganidhal.chatpro.ui.adapter.DiscussionViewAdapter;
import io.github.dogganidhal.chatpro.viewmodel.DiscussionsViewModel;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnDiscussionClickListener}
 * interface.
 */
public class DiscussionsFragment extends Fragment {

  private DiscussionsViewModel mViewModel;
  private OnDiscussionClickListener mListener;

  @BindView(R.id.discussions_recycler_view)
  RecyclerView mRecyclerView;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public DiscussionsFragment() {
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_discussion_list, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    this.mViewModel = ViewModelProviders.of(this).get(DiscussionsViewModel.class);
    DiscussionViewAdapter adapter = new DiscussionViewAdapter(mListener);

    this.mViewModel.discussions.observe(this, discussions -> {
      adapter.setDiscussions(this.mViewModel.getDiscussionViewHolderModels());
      adapter.notifyDataSetChanged();
    });

    this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    this.mRecyclerView.setAdapter(adapter);
  }

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    if (context instanceof OnDiscussionClickListener) {
      mListener = (OnDiscussionClickListener) context;
    } else {
      throw new RuntimeException(context.toString()
              + " must implement OnContactClickListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  @OnClick(R.id.create_group_button)
  void onCreateGroupButtonClicked() {

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
  public interface OnDiscussionClickListener {
    // TODO: Update argument type and name
    void onDiscussionViewClicked(DiscussionViewHolderModel discussion);
  }
}
