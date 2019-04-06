package io.github.dogganidhal.chatpro.utils;

import com.google.firebase.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class DateUtils {

  public static String formatDiscussionTimestamp(Timestamp timestamp) {

    Date date = timestamp.toDate();
    Date now = new Date();
    String dateFormat;
    long diff = now.getTime() - date.getTime();

    if (diff < 0) {
      throw new IllegalArgumentException("Can't format timestamp in the future");
    } else if (diff < 24 * 3600) {
      dateFormat = "HH:mm";
    } else if (diff < 7 * 24 * 3600) {
      dateFormat = "EEE";
    } else {
      dateFormat = "MMM d";
    }
    return new SimpleDateFormat(dateFormat, Locale.getDefault())
      .format(date);

  }

}
