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
import io.github.dogganidhal.chatpro.viewmodel.SignUpViewModel;

public class SignUpFragment extends Fragment {

  private SignUpViewModel mViewModel;


  @BindView(R.id.sign_up_fragment_full_name_text_field)
  TextInputEditText mFullNameField;

  @BindView(R.id.sign_up_fragment_email_text_field)
  TextInputEditText mEmailField;

  @BindView(R.id.sign_up_fragment_password_text_field)
  TextInputEditText mPasswordField;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.sign_up_fragment, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
  }

  @OnClick(R.id.sign_up_fragment_sign_up_button)
  void onSignUpClicked() {
    Editable fullName = this.mFullNameField.getText();
    Editable email = this.mEmailField.getText();
    Editable password = this.mPasswordField.getText();
    if (fullName !=  null && email != null && password != null) {
      this.mViewModel.signUp(fullName.toString(), email.toString(), password.toString())
        .addOnSuccessListener(authResult ->  {
          Intent intent = new Intent(getContext(), MainActivity.class);
          startActivity(intent);
        })
        .addOnFailureListener(exception -> {
          Toast.makeText(this.getContext(), exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        });
    } else {
      Toast.makeText(this.getContext(), "Tous les champs sont obligatoires", Toast.LENGTH_LONG).show();
    }
  }

}
