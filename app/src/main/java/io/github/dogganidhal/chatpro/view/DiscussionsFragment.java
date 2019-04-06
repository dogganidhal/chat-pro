package io.github.dogganidhal.chatpro.view;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import io.github.dogganidhal.chatpro.viewmodel.DiscussionsViewModel;
import io.github.dogganidhal.chatpro.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DiscussionsFragment extends Fragment {

	private DiscussionsViewModel mViewModel;

	public static DiscussionsFragment newInstance() {
		return new DiscussionsFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
													 @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.discussions_fragment, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mViewModel = ViewModelProviders.of(this).get(DiscussionsViewModel.class);
		// TODO: Use the ViewModel
	}

}
