package com.mcintosh.iain.core.task.strategy;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.mcintosh.iain.core.task.enums.CaseMode;
import org.junit.jupiter.api.Test;

class SlowBikeCounterTest {

  private final SlowBikeCounter counter = new SlowBikeCounter();

  @Test
  void execute_countsOccurrences_caseInsensitiveByDefault() {
    String input = "slow bike Slow BikeSLOW BIKE";
    String result = counter.execute(input);

    assertThat(result).isEqualTo("3");
  }

  @Test
  void execute_countsOccurrences_withCaseModeSensitive() {
    String input = "slow bike Slow Bike slow bike";
    String result = counter.execute(input, CaseMode.SENSITIVE);

    assertThat(result).isEqualTo("2");
  }

  @Test
  void execute_noOccurrences_returnsZero() {
    String input = "fast car";
    String result = counter.execute(input);

    assertThat(result).isEqualTo("0");
  }

  @Test
  void execute_emptyInput_returnsZero() {
    String result = counter.execute("");
    assertThat(result).isEqualTo("0");
  }

  @Test
  void execute_partialMatchesNotCounted() {
    String input = "slow bik slow bike";
    String result = counter.execute(input);

    assertThat(result).isEqualTo("1");
  }

}
