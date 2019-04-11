package io.github.dogganidhal.chatpro.viewmodel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.github.dogganidhal.chatpro.model.Discussion;
import io.github.dogganidhal.chatpro.model.DiscussionViewHolderModel;
import io.github.dogganidhal.chatpro.utils.DateUtils;

public class DiscussionsViewModel extends ViewModel {

  private static final String MESSAGE_TYPE_TEXT = "text";
  private static final String MESSAGE_TYPE_IMAGE = "image";
  private static final String MESSAGE_TYPE_VIDEO = "video";
  private static final String MESSAGE_TYPE_DOCUMENT = "document";

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
    switch (discussion.getLastMessage().getMessageType()) {
      case MESSAGE_TYPE_TEXT:
        return discussion.getLastMessage().getContent().length() > 24 ?
          String.format("%s %s", discussion.getLastMessage().getContent().substring(0, 24), "...") :
          discussion.getLastMessage().getContent();
      case MESSAGE_TYPE_IMAGE:
        return "Vous a envoyé une photo";
      case MESSAGE_TYPE_VIDEO:
        return "Vous a envoyé une vidéo";
      case MESSAGE_TYPE_DOCUMENT:
          return "Vous a envoyé un document";
    }
    return null;
  }

}
