package io.github.dogganidhal.chatpro.model.dummy;

import android.annotation.TargetApi;
import android.os.Build;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.github.dogganidhal.chatpro.model.Discussion;
import io.github.dogganidhal.chatpro.model.Message;
import io.github.dogganidhal.chatpro.model.User;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyDiscussions {

  /**
   * An array of sample (dummy) items.
   */
  public static final List<Discussion> ITEMS = new ArrayList<>();

  private static final int COUNT = 4;

  static {
    // Add some sample items.
    for (int i = 1; i <= COUNT; i++) {
      addItem(createDummyItem());
    }
  }

  private static void addItem(Discussion item) {
    ITEMS.add(item);
  }

  private static Discussion createDummyItem() {
    return new Discussion(Arrays.asList(
      new Message(
        "f1b58bc1-f646-4ca7-84b1-6486fa93abb2",
        "Lorem Ipsum",
        "c3b8245d-21db-4bf7-a15a-ffced3aaaa6f",
        null,
        null,
        new Timestamp(new Date())
      ),
      new Message(
        "f1b58bc1-f646-4ca7-84b1-6486fa93abb2",
        "Hello World",
        "c3b8245d-21db-4bf7-a15a-ffced3aaaa6f",
        null,
        null,
         new Timestamp(new Date())
      ),
      new Message(
        "f1b58bc1-f646-4ca7-84b1-6486fa93abb2",
        "Lorem Ipsum, Hello World",
        "c3b8245d-21db-4bf7-a15a-ffced3aaaa6f",
        null,
        null,
         new Timestamp(new Date())
      )
    ), Arrays.asList(
      new User(
        "romannion0@tinypic.com",
        "Randal O'Mannion"
      ),
      new User(
        "pbilofsky1@usda.gov",
        "Pedro Bilofsky"
      )
    ));
  }
}
