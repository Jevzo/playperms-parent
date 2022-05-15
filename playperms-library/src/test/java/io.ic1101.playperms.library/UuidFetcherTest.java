package io.ic1101.playperms.library;

import io.ic1101.playperms.library.utils.UuidFetcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UuidFetcherTest {

  @Test
  public void testUuidFetcherReturnsValidUuid() {
    UUID testData = UUID.fromString("b903c5a8-7627-4f10-bf59-37a4f1170d9a");
    UUID fetchedUuid = UuidFetcher.getUuid("GetThatAlcohol");

    Assertions.assertEquals(testData, fetchedUuid, "The uuid's should be equal!");
  }

  @Test
  public void testUuidFetcherThrowsErrorOnInvalidRequest() {
    UUID uuid = UuidFetcher.getUuid("a");

    Assertions.assertNull(uuid, "UUID should be null because the name is invalid!");
  }
}
