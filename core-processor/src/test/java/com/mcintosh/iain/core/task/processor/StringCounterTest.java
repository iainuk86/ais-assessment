package com.mcintosh.iain.core.task.processor;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.mcintosh.iain.core.task.enums.CaseMode;
import org.junit.jupiter.api.Test;

class StringCounterTest {

  @Test
  void execute_countsSingleOccurrence_caseInsensitive() {
    String input = "holy guacamole!";
    int result = StringCounter.execute(input, "MOLE!", CaseMode.INSENSITIVE);

    assertThat(result).isEqualTo(1);
  }

  @Test
  void execute_countsMultipleOccurrences_caseInsensitive() {
    String input = "holy guacamole! Mole";
    int result = StringCounter.execute(input, "mole", CaseMode.INSENSITIVE);

    assertThat(result).isEqualTo(2);
  }

  @Test
  void execute_countsMultipleOccurrences_caseSensitive() {
    String input = "holy guacamole! Mole";
    int result = StringCounter.execute(input, "mole", CaseMode.SENSITIVE);

    assertThat(result).isEqualTo(1);
  }

  @Test
  void execute_noOccurrencesReturnsZero() {
    String input = "holy guacamole! Mole";
    int result = StringCounter.execute(input, "MOLE!?", CaseMode.INSENSITIVE);

    assertThat(result).isZero();
  }

  @Test
  void execute_emptyInputReturnsZero() {
    int result = StringCounter.execute("", "any input", CaseMode.INSENSITIVE);
    assertThat(result).isZero();
  }

  @Test
  void execute_emptySearchValueReturnsZero() {
    int result = StringCounter.execute("any input", "", CaseMode.INSENSITIVE);
    assertThat(result).isZero();
  }

  @Test
  void execute_inputShorterThanSearchValueReturnsZero() {
    int result = StringCounter.execute("short", "longer string", CaseMode.INSENSITIVE);
    assertThat(result).isZero();
  }

  @Test
  void execute_partialMatchesNotCounted() {
    String input = "holy guacamole! Mol";
    int result = StringCounter.execute(input, "mole", CaseMode.INSENSITIVE);

    assertThat(result).isEqualTo(1);
  }
}
