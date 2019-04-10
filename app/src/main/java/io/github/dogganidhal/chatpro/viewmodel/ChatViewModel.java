package io.github.dogganidhal.chatpro.viewmodel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.stream.Collectors;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.github.dogganidhal.chatpro.model.ChatMessageViewHolderModel;
import io.github.dogganidhal.chatpro.model.Message;

public class ChatViewModel extends ViewModel {

  private static final String MESSAGES_COLLECTION = "messages";
  private static final String MESSAGES_DISCUSSION_ID_FIELD = "discussionId";

  private FirebaseAuth mAuth = FirebaseAuth.getInstance();
  private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

  public MutableLiveData<List<ChatMessageViewHolderModel>> messages = new MutableLiveData<>();

  public ChatViewModel(String discussionId) {
    super();
    this.mFirestore.collection(MESSAGES_COLLECTION)
      .whereEqualTo(MESSAGES_DISCUSSION_ID_FIELD, discussionId)
      .addSnapshotListener((queryDocumentSnapshots, exception) -> {

        if (queryDocumentSnapshots != null) {
          List<ChatMessageViewHolderModel> messages = queryDocumentSnapshots.getDocuments()
            .stream()
            .map(document -> {
              Message message = document.toObject(Message.class);
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

  }

}
