package io.github.dogganidhal.chatpro.ui.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dogganidhal.chatpro.R;
import io.github.dogganidhal.chatpro.ui.activity.MainActivity;
import io.github.dogganidhal.chatpro.viewmodel.LoginViewModel;

public class LoginFragment extends Fragment {

  private LoginViewModel mViewModel;

  @BindView(R.id.login_fragment_email_text_field)
  TextInputEditText mEmailField;

  @BindView(R.id.login_fragment_password_text_field)
  TextInputEditText mPasswordField;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.login_fragment, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    this.mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
  }

  @OnClick(R.id.login_fragment_login_button)
  void onLoginButtonClicked() {
    Editable email = this.mEmailField.getText();
    Editable password = this.mPasswordField.getText();
    if (email != null && password != null) {
      this.mViewModel.login(email.toString(), password.toString())
        .addOnSuccessListener(authResult -> {
          Intent intent = new Intent(getContext(), MainActivity.class);
          startActivity(intent);
        })
        .addOnFailureListener(exception -> {
          Toast.makeText(this.getContext(), exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        });
    } else {
      Toast.makeText(this.getContext(), "Email ou mot de passe incorrect", Toast.LENGTH_LONG).show();
    }
  }

}
