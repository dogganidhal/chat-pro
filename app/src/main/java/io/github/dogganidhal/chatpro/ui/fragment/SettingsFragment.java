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
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dogganidhal.chatpro.R;
import io.github.dogganidhal.chatpro.ui.activity.AuthActivity;
import io.github.dogganidhal.chatpro.viewmodel.SettingsViewModel;

public class SettingsFragment extends Fragment {

	private SettingsViewModel mViewModel;

	@BindView(R.id.connected_user_initial_text_view)
	TextView mUserInitialTextView;

	@BindView(R.id.connected_user_full_name_text_view)
	TextView mFullNameTextView;

	@BindView(R.id.connected_user_email_text_view)
	TextView mEmailTextView;

	@BindView(R.id.connected_user_phone_text_view)
	TextView mPhoneTextView;

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
		this.mViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
		this.mFullNameTextView.setText(this.mViewModel.getUserFullName());
		this.mEmailTextView.setText(this.mViewModel.getUserEmail());
		this.mPhoneTextView.setText(this.mViewModel.getUserPhone());
		this.mUserInitialTextView.setText(this.mViewModel.getUserInitial());
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
