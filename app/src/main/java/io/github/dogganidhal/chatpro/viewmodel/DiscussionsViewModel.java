package io.github.dogganidhal.chatpro.viewmodel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.github.dogganidhal.chatpro.model.Discussion;
import io.github.dogganidhal.chatpro.model.DiscussionMember;
import io.github.dogganidhal.chatpro.model.DiscussionViewHolderModel;
import io.github.dogganidhal.chatpro.model.Message;
import io.github.dogganidhal.chatpro.utils.DateUtils;

public class DiscussionsViewModel extends ViewModel {

  private static String DISCUSSIONS_COLLECTION = "discussions";
  private static String DISCUSSION_MEMBERS_PATH = "members";
  private static String DISCUSSION_MEMBER_ID_PATH = "id";

  private FirebaseAuth mAuth = FirebaseAuth.getInstance();
  private FirebaseFirestore mFireStore = FirebaseFirestore.getInstance();

  public MutableLiveData<List<Discussion>> discussions = new MutableLiveData<>();

  DiscussionsViewModel() {
    super();
    this.mFireStore
      .collection(DISCUSSIONS_COLLECTION)
//      .whereEqualTo(FieldPath.of(DISCUSSION_MEMBERS_PATH, DISCUSSION_MEMBER_ID_PATH), this.mAuth.getCurrentUser().getUid())
      .addSnapshotListener((queryDocumentSnapshots, exception) -> {
        if (queryDocumentSnapshots != null) {
          List<Discussion> discussions = queryDocumentSnapshots.getDocuments()
            .stream()
            .map(snapshot -> {
              Discussion discussion = snapshot.toObject(Discussion.class);
              discussion.setId(snapshot.getId());
              return discussion;
            })
            .collect(Collectors.toList());
          this.discussions.postValue(discussions);
        }
      });
  }

  public List<DiscussionViewHolderModel> getDiscussionViewHolderModels() {
    if (this.discussions.getValue() == null) {
      return new ArrayList<>();
    }
    return this.discussions.getValue()
      .stream()
      .map(discussion -> new DiscussionViewHolderModel(
        discussion.getId(),
        this.buildConversationName(discussion.getMembers()),
        this.extractDiscussionTimestamp(discussion.getMessages()),
        this.buildConversationName(discussion.getMembers()).substring(0, 1),
        this.extractLastMessageContent(discussion.getMessages())
      ))
      .collect(Collectors.toList());
  }

  private String buildConversationName(@Nullable List<DiscussionMember> members) {
    if (members == null) {
      return " ";
    }
    return members
      .stream()
      .filter(member -> !member.getId().equals(this.mAuth.getCurrentUser().getUid()))
      .map(DiscussionMember::getFullName)
      .collect(Collectors.joining(", "));
  }

  private String extractDiscussionTimestamp(List<Message> messages) {
    Optional<Message> lastMessage = messages
      .stream()
      .min((lhs, rhs) -> lhs.getTimestamp().compareTo(rhs.getTimestamp()));
    return lastMessage.map(message -> DateUtils.formatDiscussionTimestamp(message.getTimestamp()))
      .orElse(null);
  }

  private String extractLastMessageContent(List<Message> messages) {
    Optional<Message> lastMessage = messages
      .stream()
      .min((lhs, rhs) -> lhs.getTimestamp().compareTo(rhs.getTimestamp()));
    return lastMessage.map(Message::getContent).orElse(null);
  }

}
