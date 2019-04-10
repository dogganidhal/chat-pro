package io.github.dogganidhal.chatpro.viewmodel;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.github.dogganidhal.chatpro.model.ChatMessageViewHolderModel;
import io.github.dogganidhal.chatpro.model.Message;
import io.github.dogganidhal.chatpro.model.User;

public class ChatViewModel extends ViewModel {

  private static final String MESSAGES_COLLECTION = "messages";
  private static final String DISCUSSIONS_COLLECTION = "discussions";

  private static final String MESSAGE_TYPE_TEXT = "text";

  private static final String MESSAGES_DISCUSSION_ID_FIELD = "discussionId";
  private static final String MESSAGES_TIMESTAMP_FIELD = "timestamp";

  private static final String DISCUSSION_LAST_MESSAGE_FIELD = "lastMessage";

  private String mDiscussionId;
  private FirebaseAuth mAuth = FirebaseAuth.getInstance();
  private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

  public MutableLiveData<List<ChatMessageViewHolderModel>> messages = new MutableLiveData<>();

  public ChatViewModel(String discussionId) {
    super();
    this.mDiscussionId = discussionId;
    this.mFirestore.collection(MESSAGES_COLLECTION)
      .whereEqualTo(MESSAGES_DISCUSSION_ID_FIELD, this.mDiscussionId)
      .addSnapshotListener((queryDocumentSnapshots, exception) -> {

        if (queryDocumentSnapshots != null) {
          List<ChatMessageViewHolderModel> messages = queryDocumentSnapshots.getDocuments()
            .stream()
            .map(document -> document.toObject(Message.class))
            .sorted((lhs, rhs) -> -lhs.getTimestamp().compareTo(rhs.getTimestamp()))
            .map(message -> {
              FirebaseUser currentUser = this.mAuth.getCurrentUser();
              Boolean isMessageAuthor = false;
              String authorName = null, content = null;
              if (message != null && message.getAuthor() != null && currentUser != null) {
                authorName = message.getAuthor().getFullName();
                content = message.getContent();
                isMessageAuthor = message.getAuthor().getId().equals(currentUser.getUid());
              }
              return new ChatMessageViewHolderModel(authorName, content, isMessageAuthor);
            })
            .collect(Collectors.toList());
          this.messages.postValue(messages);
        }

      });
  }

  public void sendMessage(String message) {
    if (message == null || message.length() == 0) return;
    FirebaseUser currentUser = this.mAuth.getCurrentUser();
    if (currentUser == null) return;
    User author = new User(currentUser.getUid(), currentUser.getDisplayName());
    Message messageObject = new Message(
      author,
      message.trim(),
      this.mDiscussionId,
      null,
      MESSAGE_TYPE_TEXT,
      Timestamp.now()
    );
    this.mFirestore.collection(MESSAGES_COLLECTION)
      .add(messageObject);
    this.mFirestore.collection(DISCUSSIONS_COLLECTION)
      .document(this.mDiscussionId)
      .update(DISCUSSION_LAST_MESSAGE_FIELD, messageObject);
  }

}
