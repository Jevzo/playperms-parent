package io.ic1101.playperms.library;

import io.ic1101.playperms.library.utils.TimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtilsTest {

  @Test
  public void testParseTimeString() {
    long millis = TimeUtils.parseTimeString("30m 30d");

    Assertions.assertEquals(
        2593800000L, millis, "The parsed time should be equal to the test data");
  }

  @Test
  public void testParseTimeMillis() {
    String parsedMilliString = TimeUtils.parseTimeMillis(2593800000L);

    Assertions.assertEquals(
        "1M 2d 30m", parsedMilliString, "The millis should be converted to the right value!");
  }

  @Test
  public void testWrongFormatThrowsNumberFormatException() {
    Assertions.assertThrows(
        NumberFormatException.class,
        () -> TimeUtils.parseTimeString("30m30d"),
        "Expected NumberFormatException was not thrown");
  }

  @Test
  public void testCommandDateValidationPattern() {
    Pattern pattern = Pattern.compile("\\d+[YMwdhms]");

    Matcher valid = pattern.matcher("30d");
    Matcher invalid = pattern.matcher("10x");

    Assertions.assertTrue(valid.matches(), "The valid string should not be invalid");
    Assertions.assertFalse(invalid.matches(), "The invalid string should not be valid");
  }
}
