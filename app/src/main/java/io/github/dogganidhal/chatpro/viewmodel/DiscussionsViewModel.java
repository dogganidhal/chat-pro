package io.github.dogganidhal.chatpro.viewmodel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.github.dogganidhal.chatpro.model.Discussion;
import io.github.dogganidhal.chatpro.model.DiscussionViewHolderModel;
import io.github.dogganidhal.chatpro.model.User;
import io.github.dogganidhal.chatpro.utils.DateUtils;

public class DiscussionsViewModel extends ViewModel {

  private static final String MESSAGE_TYPE_TEXT = "text";
  private static final String MESSAGE_TYPE_IMAGE = "image";
  private static final String MESSAGE_TYPE_VIDEO = "video";
  private static final String MESSAGE_TYPE_DOCUMENT = "document";

  private static final String USERS_COLLECTION = "users";
  private static final String DISCUSSIONS_COLLECTION = "discussions";
  private static final String DISCUSSION_MEMBERS_PATH = "members";

  private FirebaseAuth mAuth = FirebaseAuth.getInstance();
  private FirebaseFirestore mFireStore = FirebaseFirestore.getInstance();

  public MutableLiveData<List<Discussion>> discussions = new MutableLiveData<>();

  DiscussionsViewModel() {
    super();
    FirebaseUser currentUser = this.mAuth.getCurrentUser();
    if (currentUser != null) {
      this.mFireStore
        .collection(DISCUSSIONS_COLLECTION)
        .whereEqualTo(FieldPath.of(DISCUSSION_MEMBERS_PATH, currentUser.getUid()), currentUser.getDisplayName())
        .addSnapshotListener((queryDocumentSnapshots, exception) -> {
          if (queryDocumentSnapshots != null) {
            List<Discussion> discussions = queryDocumentSnapshots.getDocuments()
              .stream()
              .map(snapshot -> {
                Discussion discussion = snapshot.toObject(Discussion.class);
                discussion.setId(snapshot.getId());
                return discussion;
              })
              .filter(discussion -> discussion.getLastMessage() != null)
              .sorted((lhs, rhs) -> -lhs.getLastMessage().getTimestamp().compareTo(rhs.getLastMessage().getTimestamp()))
              .collect(Collectors.toList());
            this.discussions.postValue(discussions);
          }
        });
    }
  }

  public List<DiscussionViewHolderModel> getDiscussionViewHolderModels() {
    if (this.discussions.getValue() == null) {
      return new ArrayList<>();
    }
    return this.discussions.getValue()
      .stream()
      .filter(discussion -> discussion.getLastMessage() != null)
      .map(discussion -> new DiscussionViewHolderModel(
        discussion.getId(),
        this.buildConversationName(discussion.getMembers()),
        DateUtils.formatDiscussionTimestamp(discussion.getLastMessage().getTimestamp()),
        this.buildConversationName(discussion.getMembers()).substring(0, 1),
        this.extractLastMessageContent(discussion)
      ))
      .collect(Collectors.toList());
  }

  public Task<List<User>> getContacts() {
    return this.mFireStore.collection(USERS_COLLECTION)
      .get()
      .continueWith(task -> {
        if (!task.isSuccessful() || task.getResult() == null) {
          throw new RuntimeException();
        }
        return task.getResult()
          .getDocuments()
          .stream()
          .map(document -> {
            User contact = document.toObject(User.class);
            contact.setId(document.getId());
            return contact;
          })
          .collect(Collectors.toList());
      });
  }

  public Task<DiscussionViewHolderModel> createGroupDiscussion(List<User> contacts) {
    Map<String, String> members = new HashMap<>();
    members.put(this.mAuth.getCurrentUser().getUid(), this.mAuth.getCurrentUser().getDisplayName());
    for(User contact: contacts) {
      members.put(contact.getId(), contact.getFullName());
    }
    Discussion discussion = new Discussion(members);
    return this.mFireStore.collection(DISCUSSIONS_COLLECTION)
      .add(discussion)
      .continueWith(task -> {
        if (!task.isSuccessful() || task.getResult() == null) {
          throw new RuntimeException();
        }
        discussion.setId(task.getResult().getId());
        return new DiscussionViewHolderModel(
          discussion.getId(),
          this.buildConversationName(discussion.getMembers()),
          null,
          this.buildConversationName(discussion.getMembers()).substring(0, 1),
          this.extractLastMessageContent(discussion)
        );
      });
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

  private String extractLastMessageContent(Discussion discussion) {
    if (discussion.getLastMessage() == null) return null;
    switch (discussion.getLastMessage().getMessageType()) {
      case MESSAGE_TYPE_TEXT:
        return discussion.getLastMessage().getContent().length() > 24 ?
          String.format("%s %s", discussion.getLastMessage().getContent().substring(0, 24), "...") :
          discussion.getLastMessage().getContent();
      case MESSAGE_TYPE_IMAGE:
        return "Une photo";
      case MESSAGE_TYPE_VIDEO:
        return "Une vidéo";
      case MESSAGE_TYPE_DOCUMENT:
          return "Un document";
    }
    return null;
  }
}
