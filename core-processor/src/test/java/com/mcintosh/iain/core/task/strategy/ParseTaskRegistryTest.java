package com.mcintosh.iain.core.task.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import com.mcintosh.iain.core.task.enums.ParseTaskType;
import org.junit.jupiter.api.Test;

public class ParseTaskRegistryTest {

  @Test
  void getStrategy_returnsVowelRemover_forRemoveVowels() {
    ParseTask strategy = ParseTaskRegistry.getStrategy(ParseTaskType.REMOVE_VOWELS);
    assertThat(strategy).isInstanceOf(VowelRemover.class);
  }

  @Test
  void getStrategy_returnsConsonantCounter_forCountConsonants() {
    ParseTask strategy = ParseTaskRegistry.getStrategy(ParseTaskType.COUNT_CONSONANTS);
    assertThat(strategy).isInstanceOf(ConsonantCounter.class);
  }

  @Test
  void getStrategy_returnsSlowBikeCounter_forCountSlowBike() {
    ParseTask strategy = ParseTaskRegistry.getStrategy(ParseTaskType.COUNT_SLOW_BIKE);
    assertThat(strategy).isInstanceOf(SlowBikeCounter.class);
  }
}
