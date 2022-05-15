package io.ic1101.playperms.library.utils;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

public class TimeUtils {

  public static long parseTimeString(String time) throws NumberFormatException {
    if(time.equals("-1")) {
      return -1;
    }

    String[] timeSplit = time.split(" ");

    AtomicLong endResult = new AtomicLong(0);

    Arrays.stream(timeSplit).forEach(value -> {
      String lastChar = value.substring(value.length() - 1);
      int number = Integer.parseInt(value.substring(0, value.length() - 1));

      switch (lastChar) {
        case "Y" -> endResult.set(endResult.get() + (31557600000L * number));
        case "M" -> endResult.set(endResult.get() + (2629800000L * number));
        case "w" -> endResult.set(endResult.get() + (604800016L * number));
        case "d" -> endResult.set(endResult.get() + (86400000L * number));
        case "h" -> endResult.set(endResult.get() + (3600000L * number));
        case "m" -> endResult.set(endResult.get() + (60000L * number));
        case "s" -> endResult.set(endResult.get() + (1000L * number));
      }
    });

    return endResult.get();
  }

  public static String parseTimeMillis(long millis) {
    if(millis == -1) {
      return "permanent";
    }

    int seconds = Math.toIntExact(millis / 1000);

    int minutes = 0;
    int hours = 0;
    int days = 0;
    int weeks = 0;
    int months = 0;
    int years = 0;

    while (seconds >= 60) {
      seconds -= 60;
      minutes++;
    }

    while (minutes >= 60) {
      minutes -= 60;
      hours++;
    }

    while (hours >= 24) {
      hours -= 24;
      days++;
    }

    while (days >= 7) {
      days -= 7;
      weeks++;
    }

    while (weeks >= 4) {
      weeks -= 4;
      months++;
    }

    while (months >= 12) {
      months -= 12;
      years++;
    }

    StringBuilder stringBuilder = new StringBuilder();

    if (years > 0) {
      stringBuilder.append(years).append("Y").append(" ");
    }

    if (months > 0) {
      stringBuilder.append(months).append("M").append(" ");
    }

    if (weeks > 0) {
      stringBuilder.append(weeks).append("w").append(" ");
    }

    if (days > 0) {
      stringBuilder.append(days).append("d").append(" ");
    }

    if (hours > 0) {
      stringBuilder.append(hours).append("h").append(" ");
    }

    if (minutes > 0) {
      stringBuilder.append(minutes).append("m").append(" ");
    }

    if (seconds > 0) {
      stringBuilder.append(seconds).append("s").append(" ");
    }

    return stringBuilder.toString().trim();
  }
}
