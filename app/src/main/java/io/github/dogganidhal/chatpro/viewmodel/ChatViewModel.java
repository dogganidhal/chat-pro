package io.github.dogganidhal.chatpro.viewmodel;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.os.storage.StorageVolume;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.github.dogganidhal.chatpro.model.ChatMessageViewHolderModel;
import io.github.dogganidhal.chatpro.model.Message;
import io.github.dogganidhal.chatpro.model.User;

public class ChatViewModel extends ViewModel {

  private static final String MESSAGES_COLLECTION = "messages";
  private static final String DISCUSSIONS_COLLECTION = "discussions";

  private static final String MESSAGES_DISCUSSION_ID_FIELD = "discussionId";
  private static final String MESSAGES_TIMESTAMP_FIELD = "timestamp";

  private static final String DISCUSSION_LAST_MESSAGE_FIELD = "lastMessage";

  private static final String STORAGE_IMAGES_BASE_PATH = "images/";
  private static final String STORAGE_VIDEOS_BASE_PATH = "videos/";
  private static final String STORAGE_DOCUMENTS_BASE_PATH = "documents/";

  private String mDiscussionId;
  private FirebaseAuth mAuth = FirebaseAuth.getInstance();
  private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
  private FirebaseStorage mStorage = FirebaseStorage.getInstance();

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
              String authorName = null, content = null, mediaUrl = null, messageType = "text";
              if (message != null && message.getAuthor() != null && currentUser != null) {
                authorName = message.getAuthor().getFullName();
                content = message.getContent();
                isMessageAuthor = message.getAuthor().getId().equals(currentUser.getUid());
                mediaUrl = message.getMediaUrl();
                messageType = message.getMessageType();
              }
              return new ChatMessageViewHolderModel(authorName, content, isMessageAuthor, messageType, mediaUrl);
            })
            .collect(Collectors.toList());
          this.messages.postValue(messages);
        }

      });
  }

  public void sendTextMessage(String message) {
    if (message == null || message.length() == 0) return;
    FirebaseUser currentUser = this.mAuth.getCurrentUser();
    if (currentUser == null) return;
    User author = new User(currentUser.getUid(), currentUser.getDisplayName());
    Message messageObject = new Message(
      author,
      message.trim(),
      this.mDiscussionId,
      null,
      Message.MESSAGE_TYPE_TEXT,
      Timestamp.now()
    );
    this.sendMessage(messageObject);
  }

  public void sendImageMessage(Uri imageUri) {
    String uuid = UUID.randomUUID().toString();
    StorageReference reference = this.mStorage.getReference(String.format("%s/%s", STORAGE_IMAGES_BASE_PATH, uuid));
    reference.putFile(imageUri)
      .continueWithTask(task -> reference.getDownloadUrl())
      .addOnSuccessListener(uri -> {
        FirebaseUser currentUser = this.mAuth.getCurrentUser();
        if (currentUser == null) return;
        User author = new User(currentUser.getUid(), currentUser.getDisplayName());
        this.sendMessage(new Message(
          author,
          null,
          this.mDiscussionId,
          uri.toString(),
          Message.MESSAGE_TYPE_IMAGE,
          Timestamp.now()
        ));
      });
  }

  public void sendVideoMessage(Uri videoUri) {
    String uuid = UUID.randomUUID().toString();
    StorageReference reference = this.mStorage.getReference(String.format("%s/%s", STORAGE_VIDEOS_BASE_PATH, uuid));
    reference.putFile(videoUri)
      .continueWithTask(task -> reference.getDownloadUrl())
      .addOnSuccessListener(uri -> {
        FirebaseUser currentUser = this.mAuth.getCurrentUser();
        if (currentUser == null) return;
        User author = new User(currentUser.getUid(), currentUser.getDisplayName());
        this.sendMessage(new Message(
          author,
          null,
          this.mDiscussionId,
          uri.toString(),
          Message.MESSAGE_TYPE_VIDEO,
          Timestamp.now()
        ));
      });
  }

  public void sendDocumentMessage(Uri documentUri) {
    String uuid = UUID.randomUUID().toString();
    StorageReference reference = this.mStorage.getReference(String.format("%s/%s", STORAGE_DOCUMENTS_BASE_PATH, uuid));
    reference.putFile(documentUri)
      .continueWithTask(task -> reference.getDownloadUrl())
      .addOnSuccessListener(uri -> {
        FirebaseUser currentUser = this.mAuth.getCurrentUser();
        if (currentUser == null) return;
        User author = new User(currentUser.getUid(), currentUser.getDisplayName());
        this.sendMessage(new Message(
          author,
          null,
          this.mDiscussionId,
          uri.toString(),
          Message.MESSAGE_TYPE_DOCUMENT,
          Timestamp.now()
        ));
      });
  }

  public void sendPhotoMessage(Bitmap bitmap) {
    String uuid = UUID.randomUUID().toString();
    StorageReference reference = this.mStorage.getReference(String.format("%s/%s", STORAGE_IMAGES_BASE_PATH, uuid));
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
    byte[] data = baos.toByteArray();

    reference.putBytes(data)
      .continueWithTask(task -> reference.getDownloadUrl())
      .addOnSuccessListener(uri -> {
        FirebaseUser currentUser = this.mAuth.getCurrentUser();
        if (currentUser == null) return;
        User author = new User(currentUser.getUid(), currentUser.getDisplayName());
        this.sendMessage(new Message(
          author,
          null,
          this.mDiscussionId,
          uri.toString(),
          Message.MESSAGE_TYPE_IMAGE,
          Timestamp.now()
        ));
      });
  }

  private void sendMessage(Message message) {
    this.mFirestore.collection(MESSAGES_COLLECTION)
      .add(message);
    this.mFirestore.collection(DISCUSSIONS_COLLECTION)
      .document(this.mDiscussionId)
      .update(DISCUSSION_LAST_MESSAGE_FIELD, message);
  }

}
