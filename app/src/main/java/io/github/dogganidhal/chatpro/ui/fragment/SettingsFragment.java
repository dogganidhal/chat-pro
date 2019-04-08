package io.github.dogganidhal.chatpro.ui.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dogganidhal.chatpro.R;
import io.github.dogganidhal.chatpro.ui.activity.AuthActivity;
import io.github.dogganidhal.chatpro.viewmodel.SettingsViewModel;

public class SettingsFragment extends Fragment {

	private SettingsViewModel mViewModel;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
													 @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_settings, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
		// TODO: Use the ViewModel
	}

	@OnClick(R.id.connected_user_disconnect_button)
	void onLogoutClicked() {
		this.mViewModel.logout();
    Intent intent = new Intent(this.getContext(), AuthActivity.class);
    Activity activity = this.getActivity();
    if (activity != null) {
      activity.startActivity(intent);
    }
	}

}
