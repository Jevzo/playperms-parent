package io.ic1101.playperms.library.utils;

import com.google.gson.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Document {

  private static final Gson gson =
      new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

  private final JsonObject jsonObject;

  public Document() {
    this.jsonObject = new JsonObject();
  }

  public Document(JsonObject jsonObject) {
    this.jsonObject = jsonObject;
  }

  public static Document read(File file) {
    try {
      BufferedReader bufferedReader =
          new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));

      JsonObject jsonObject = gson.fromJson(bufferedReader, JsonObject.class);

      return new Document(jsonObject);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  public static Document read(String input) {
    return new Document(gson.fromJson(input, JsonObject.class));
  }

  public Document appendString(String key, String value) {
    this.jsonObject.addProperty(key, value);
    return this;
  }

  public Document appendInt(String key, int value) {
    this.jsonObject.addProperty(key, value);
    return this;
  }

  public Document appendDouble(String key, double value) {
    this.jsonObject.addProperty(key, value);
    return this;
  }

  public Document appendFloat(String key, float value) {
    this.jsonObject.addProperty(key, value);
    return this;
  }

  public Document appendLong(String key, long value) {
    this.jsonObject.addProperty(key, value);
    return this;
  }

  public Document appendBoolean(String key, boolean value) {
    this.jsonObject.addProperty(key, value);
    return this;
  }

  public <T> Document appendList(String key, List<T> value) {
    this.jsonObject.add(key, gson.toJsonTree(value));
    return this;
  }

  public Document appendJsonElement(String key, JsonElement value) {
    this.jsonObject.add(key, value);
    return this;
  }

  public Document appendDocument(String key, Document value) {
    this.jsonObject.add(key, value.jsonObject);
    return this;
  }

  public <K, V> Document appendMap(String key, Map<K, V> value) {
    this.jsonObject.add(key, gson.toJsonTree(value));
    return this;
  }

  public Document removeEntry(String key) {
    this.jsonObject.remove(key);
    return this;
  }

  public String getStringValue(String key) {
    if (!this.jsonObject.has(key)) {
      return "null";
    }

    return new String(
        this.jsonObject.get(key).getAsString().getBytes(StandardCharsets.UTF_8),
        StandardCharsets.UTF_8);
  }

  public int getIntValue(String key) {
    if (!this.jsonObject.has(key)) {
      return -1;
    }

    return this.jsonObject.get(key).getAsInt();
  }

  public double getDoubleValue(String key) {
    if (!this.jsonObject.has(key)) {
      return -1.0;
    }

    return this.jsonObject.get(key).getAsDouble();
  }

  public float getFloatValue(String key) {
    if (!this.jsonObject.has(key)) {
      return -1.0F;
    }

    return this.jsonObject.get(key).getAsFloat();
  }

  public long getLongValue(String key) {
    if (!this.jsonObject.has(key)) {
      return -1;
    }

    return this.jsonObject.get(key).getAsLong();
  }

  public boolean getBooleanValue(String key) {
    if (!this.jsonObject.has(key)) {
      return false;
    }

    return this.jsonObject.get(key).getAsBoolean();
  }

  public JsonElement getJsonElementValue(String key) {
    if (!this.jsonObject.has(key)) {
      return new JsonArray();
    }

    return this.jsonObject.get(key);
  }

  public Document getDocument(String key) {
    if (!this.jsonObject.has(key)) {
      return new Document();
    }

    return new Document(this.jsonObject.get(key).getAsJsonObject());
  }

  public <K, V> Map<K, V> getMap(String key) {
    if (!this.jsonObject.has(key)) {
      return new HashMap<K, V>();
    }

    return gson.fromJson(this.jsonObject.get(key), Map.class);
  }

  public <T> List<T> getList(String key) {
    if (!this.jsonObject.has(key)) {
      return new ArrayList<T>();
    }

    return gson.fromJson(this.jsonObject.get(key), List.class);
  }

  public JsonObject getAsJsonObject() {
    return this.jsonObject;
  }

  public String getAsString() {
    return this.jsonObject.toString();
  }

  public void write(File file) {
    try {
      BufferedWriter bufferedWriter =
          new BufferedWriter(
              new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));

      bufferedWriter.write(gson.toJson(this.jsonObject));
      bufferedWriter.flush();
      bufferedWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
