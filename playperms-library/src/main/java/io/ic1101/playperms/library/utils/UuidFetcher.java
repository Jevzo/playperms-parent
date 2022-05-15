package io.ic1101.playperms.library.utils;

import io.ic1101.playperms.library.cache.SynchronizedCache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.UUID;

public class UuidFetcher {

  private static final OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

  private static final SynchronizedCache<String, UUID> uuidCache = new SynchronizedCache<>();

  public static UUID getUuid(String playerName) {
    if (uuidCache.contains(playerName)) {
      return uuidCache.get(playerName);
    }

    Request request =
        new Request.Builder()
            .url("https://api.mojang.com/users/profiles/minecraft/" + playerName)
            .method("GET", null)
            .build();

    try {
      Response response = okHttpClient.newCall(request).execute();

      if (response.body() == null) {
        return null;
      }

      String body = response.body().string();

      if (body.equals("")) {
        return null;
      }

      Document document = Document.read(body);
      String validUuid = transform(document.getStringValue("id"));

      UUID uuid = UUID.fromString(validUuid);

      uuidCache.add(playerName, uuid);

      return uuid;
    } catch (IOException e) {
      return null;
    }
  }

  private static String transform(String uuid) {
    StringBuilder stringBuilder = new StringBuilder(uuid);

    stringBuilder.insert(8, '-').insert(13, '-').insert(18, '-').insert(23, '-');

    return stringBuilder.toString();
  }
}
