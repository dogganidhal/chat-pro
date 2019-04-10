package io.github.dogganidhal.chatpro.viewmodel;

import android.provider.DocumentsContract;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import io.github.dogganidhal.chatpro.model.Discussion;
import io.github.dogganidhal.chatpro.model.DiscussionViewHolderModel;
import io.github.dogganidhal.chatpro.model.User;
import io.github.dogganidhal.chatpro.utils.DateUtils;

public class MainViewModel extends ViewModel {

  private static final String DISCUSSIONS_COLLECTION = "discussions";
  private static String DISCUSSION_MEMBERS_PATH = "members";

  private FirebaseAuth mAuth = FirebaseAuth.getInstance();
  private FirebaseFirestore mFireStore = FirebaseFirestore.getInstance();

  public void createDiscussionIfNeeded(User contact, OnCreateDiscussionListener callback) {

    FirebaseUser currentUser = this.mAuth.getCurrentUser();

    if (currentUser != null) {
      this.mFireStore
        .collection(DISCUSSIONS_COLLECTION)
        .whereEqualTo(FieldPath.of(DISCUSSION_MEMBERS_PATH, currentUser.getUid()), currentUser.getDisplayName())
        .whereEqualTo(FieldPath.of(DISCUSSION_MEMBERS_PATH, contact.getId()), contact.getFullName())
        .get()
        .addOnSuccessListener(snapshot -> {

          if (snapshot != null) {
            if (snapshot.getDocuments().size() == 0) {
              // Discussion does not exist ~> Create it
              Map<String, String> members = new HashMap<>();
              members.put(currentUser.getUid(), currentUser.getDisplayName());
              members.put(contact.getId(), contact.getFullName());
              Discussion discussion = new Discussion(members);
              this.mFireStore.collection(DISCUSSIONS_COLLECTION)
                .add(discussion)
                .addOnSuccessListener(document -> {
                  if (document != null) {
                    discussion.setId(document.getId());
                    callback.onSuccess(this.mapToViewHolderModel(discussion));
                  }
                });

            } else {
              // Discussion exists ~> Return it
              Optional<DocumentSnapshot> document = snapshot.getDocuments().stream().findFirst();
              if (document.isPresent()) {
                Discussion discussion = document.get().toObject(Discussion.class);
                discussion.setId(document.get().getId());
                callback.onSuccess(this.mapToViewHolderModel(discussion));
              }
            }
          }

      });

    }

  }

  private DiscussionViewHolderModel mapToViewHolderModel(@Nullable  Discussion discussion) {
    if (discussion == null) return null;
    return new DiscussionViewHolderModel(
      discussion.getId(),
      this.buildConversationName(discussion.getMembers()),
      DateUtils.formatDiscussionTimestamp(discussion.getLastMessage() != null ? discussion.getLastMessage().getTimestamp() : Timestamp.now()),
      this.buildConversationName(discussion.getMembers()).substring(0, 1),
      discussion.getLastMessage() != null ? discussion.getLastMessage().getContent() : null
    );
  }

  private String buildConversationName(@Nullable Map<String, String> members) {
    if (members == null) {
      return " ";
    }
    return members
      .keySet()
      .stream()
      .filter(memberId -> !memberId.equals(this.mAuth.getCurrentUser().getUid()))
      .map(members::get)
      .collect(Collectors.joining(", "));
  }

  @FunctionalInterface
  public interface OnCreateDiscussionListener {
    void onSuccess(DiscussionViewHolderModel discussion);
  }

}
