package io.ic1101.playperms.library.cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SynchronizedCache<K, V> {

  private final Map<K, V> cache = new HashMap<>();

  public void add(K key, V value) {
    synchronized (this.cache) {
      this.cache.put(key, value);
    }
  }

  public void remove(K key) {
    synchronized (this.cache) {
      this.cache.remove(key);
    }
  }

  public void replace(K key, V value) {
    synchronized (this.cache) {
      this.cache.replace(key, value);
    }
  }

  public V get(K key) {
    synchronized (this.cache) {
      return this.cache.get(key);
    }
  }

  public boolean contains(K key) {
    synchronized (this.cache) {
      return this.cache.containsKey(key);
    }
  }

  public V getOrDefault(K key, V defaultValue) {
    synchronized (this.cache) {
      V value = this.get(key);

      if (value != null) {
        return value;
      }

      return defaultValue;
    }
  }

  public Map<K, V> getCache() {
    synchronized (this.cache) {
      return this.cache;
    }
  }

  public Collection<V> getCacheValues() {
    synchronized (this.cache) {
      return this.cache.values();
    }
  }
}
