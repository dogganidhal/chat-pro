package io.github.dogganidhal.chatpro.viewmodel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.stream.Collectors;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.github.dogganidhal.chatpro.model.User;

public class ContactsViewModel extends ViewModel {

  private static final String USERS_COLLECTION = "users";

  private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
  private FirebaseAuth mAuth = FirebaseAuth.getInstance();

  public MutableLiveData<List<User>> contacts = new MutableLiveData<>();

  ContactsViewModel() {
    this.mFirestore
      .collection(USERS_COLLECTION)
      .addSnapshotListener((queryDocumentSnapshots, exception) -> {
        if (queryDocumentSnapshots != null) {
          List<User> contacts = queryDocumentSnapshots
            .getDocuments()
            .stream()
            .map(document -> {
              User contact = document.toObject(User.class);
              contact.setId(document.getId());
              return contact;
            })
            .filter(contact -> !contact.getId().equals(this.mAuth.getCurrentUser().getUid()))
            .collect(Collectors.toList());
          this.contacts.postValue(contacts);
        }
      });
  }

}
