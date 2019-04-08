package io.github.dogganidhal.chatpro.ui.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.dogganidhal.chatpro.R;
import io.github.dogganidhal.chatpro.viewmodel.SignUpViewModel;

public class SignUpFragment extends Fragment {

  private SignUpViewModel mViewModel;

  public static SignUpFragment newInstance() {
    return new SignUpFragment();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.sign_up_fragment, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
    // TODO: Use the ViewModel
  }

}
