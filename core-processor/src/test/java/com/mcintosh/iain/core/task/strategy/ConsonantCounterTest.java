package com.mcintosh.iain.core.task.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import com.mcintosh.iain.core.task.enums.CaseMode;
import com.mcintosh.iain.core.util.Alphabet;
import org.junit.jupiter.api.Test;

class ConsonantCounterTest {

  private final ConsonantCounter counter = new ConsonantCounter();

  @Test
  void execute_countsConsonants_caseInsensitive() {
    String input = "Hello World!";
    String json = counter.execute(input);

    // All consonants in Alphabet.CONSONANTS should appear, with zero for missing ones
    for (char c : Alphabet.getConsonants()) {
      if ("HllWrld".toLowerCase().indexOf(Character.toLowerCase(c)) >= 0) {
        assertThat(json).contains("\"" + Character.toLowerCase(c) + "\":");
      } else {
        assertThat(json).contains("\"" + Character.toLowerCase(c) + "\": 0");
      }
    }

    // Check specific counts
    assertThat(json).contains("\"h\": 1", "\"l\": 3", "\"w\": 1", "\"r\": 1", "\"d\": 1");
  }

  @Test
  void execute_countsConsonants_caseSensitive() {
    String input = "Hello World!";
    String json = counter.execute(input, CaseMode.SENSITIVE);

    // All consonants in Alphabet.CONSONANTS should appear, case-sensitive, missing chars = 0
    for (char c : Alphabet.getConsonants()) {
      assertThat(json).contains("\"" + c + "\":");
    }

    // Check specific counts
    assertThat(json).contains("\"h\": 0", "\"l\": 3", "\"w\": 0", "\"r\": 1", "\"d\": 1");
  }

  @Test
  void execute_emptyInput_returnsZeroForAllConsonants() {
    String json = counter.execute("");

    for (char c : Alphabet.getConsonants()) {
      assertThat(json).contains("\"" + c + "\": 0");
    }

    assertThat(json).doesNotContain(":null");
  }

  @Test
  void execute_inputWithNoConsonants_returnsZeroForAll() {
    String input = "aeiou 123 !@#";
    String json = counter.execute(input);

    for (char c : Alphabet.getConsonants()) {
      assertThat(json).contains("\"" + c + "\": 0");
    }
  }

  @Test
  void execute_repeatedConsonants_countsCorrectly() {
    String input = "bbbcccddd";
    String json = counter.execute(input);

    assertThat(json).contains("\"b\": 3", "\"c\": 3", "\"d\": 3");
  }

  @Test
  void execute_mixedCase_countsCaseInsensitiveCorrectly() {
    String input = "bBcCdD";
    String json = counter.execute(input);

    assertThat(json).contains("\"b\": 2", "\"c\": 2", "\"d\": 2");
  }

  @Test
  void execute_mixedCase_countsCaseSensitiveCorrectly() {
    String input = "bBcCdD";
    String json = counter.execute(input, CaseMode.SENSITIVE);

    assertThat(json)
        .contains("\"b\": 1", "\"c\": 1", "\"d\": 1")
        .doesNotContain("\"B\"", "\"C\"", "\"D\"");
  }
}
