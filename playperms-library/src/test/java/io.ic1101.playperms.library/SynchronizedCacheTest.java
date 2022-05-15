package io.ic1101.playperms.library;

import io.ic1101.playperms.library.cache.SynchronizedCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SynchronizedCacheTest {

  @Test
  public void testCacheCanContainData() {
    SynchronizedCache<String, String> testCache = new SynchronizedCache<>();
    testCache.add("foo", "bar");

    Assertions.assertEquals(
        "bar", testCache.get("foo"), "The value must be the same as the given value");
  }

  @Test
  public void testCacheGetOrDefault() {
    SynchronizedCache<String, String> testCache = new SynchronizedCache<>();

    Assertions.assertEquals(
        "bar",
        testCache.getOrDefault("abc", "bar"),
        "Cache must return the default value if no key value is found");
  }

  @Test
  public void testCacheRemove() {
    SynchronizedCache<String, String> testCache = new SynchronizedCache<>();
    testCache.add("foo", "bar");

    testCache.remove("foo");

    Assertions.assertNull(testCache.get("foo"), "Cache should not contain already removed values.");
  }

  @Test
  public void testCacheReplace() {
    SynchronizedCache<String, String> testCache = new SynchronizedCache<>();
    testCache.add("foo", "bar");

    testCache.replace("foo", "foobar");

    Assertions.assertEquals(
        "foobar", testCache.get("foo"), "Cache should contain the replaced value");
  }

  @Test
  public void testCacheContains() {
    SynchronizedCache<String, String> testCache = new SynchronizedCache<>();
    testCache.add("foo", "bar");

    Assertions.assertTrue(testCache.contains("foo"), "Cache should contain the given key value");
  }
}
