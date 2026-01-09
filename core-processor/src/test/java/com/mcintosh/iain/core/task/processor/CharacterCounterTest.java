package com.mcintosh.iain.core.task.processor;

import static org.assertj.core.api.Assertions.assertThat;

import com.mcintosh.iain.core.task.enums.CaseMode;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CharacterCounterTest {

  @Test
  void count_letters_only_caseInsensitive() {
    String input = "AbCabc";
    Set<Character> chars = Set.of('a', 'b', 'c');

    Map<Character, Integer> result = CharacterCounter.count(input, chars, CaseMode.INSENSITIVE);

    assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.of(
        'a', 2,
        'b', 2,
        'c', 2
    ));
  }

  @Test
  void count_letters_only_caseSensitive() {
    String input = "AbCabc";
    Set<Character> chars = Set.of('a', 'b', 'c', 'A', 'B', 'C');

    Map<Character, Integer> result = CharacterCounter.count(input, chars, CaseMode.SENSITIVE);

    assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.of(
        'A', 1,
        'B', 0,
        'C', 1,
        'a', 1,
        'b', 2,
        'c', 1
    ));
  }

  @Test
  void count_digits() {
    String input = "1122334455";
    Set<Character> digits = Set.of('1', '2', '3', '8');

    Map<Character, Integer> result = CharacterCounter.count(input, digits, CaseMode.INSENSITIVE);

    assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.of(
        '1', 2,
        '2', 2,
        '3', 2,
        '8', 0
    ));
  }

  @Test
  void count_symbols() {
    String input = "!@!@#$%";
    Set<Character> symbols = Set.of('!', '@', '$');

    Map<Character, Integer> result = CharacterCounter.count(input, symbols, CaseMode.INSENSITIVE);

    assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.of(
        '!', 2,
        '@', 2,
        '$', 1
    ));
  }

  @Test
  void count_emptyInput_returnsZeroCountForProvidedValues() {
    Map<Character, Integer> result =
        CharacterCounter.count("", Set.of('a', 'b'), CaseMode.INSENSITIVE);

    assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.of(
        'a', 0,
        'b', 0
    ));
  }

  @Test
  void count_emptySet_returnsEmptyMap() {
    Map<Character, Integer> result =
        CharacterCounter.count("abc", Set.of(), CaseMode.INSENSITIVE);

    assertThat(result).isEmpty();
  }

  @Test
  void count_noMatches_returnsEmptyMap() {
    Map<Character, Integer> result =
        CharacterCounter.count("xyz", Set.of('a', 'b', 'c'), CaseMode.INSENSITIVE);

    assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.of(
        'a', 0,
        'b', 0,
        'c', 0
    ));
  }

  @Test
  void count_repeatedCharacters() {
    Map<Character, Integer> result =
        CharacterCounter.count("aaabbbccc", Set.of('a', 'b'), CaseMode.INSENSITIVE);

    assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.of(
        'a', 3,
        'b', 3
    ));
  }

  @Test
  void count_mixedCaseSet_caseInsensitive() {
    String input = "aAbBcC";
    Set<Character> chars = Set.of('a', 'B', 'c');

    Map<Character, Integer> result =
        CharacterCounter.count(input, chars, CaseMode.INSENSITIVE);

    assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.of(
        'a', 2,
        'b', 2,
        'c', 2
    ));
  }

  @Test
  void count_mixedCaseSet_caseSensitive() {
    String input = "aAbBcC";
    Set<Character> chars = Set.of('a', 'B', 'c');

    Map<Character, Integer> result = CharacterCounter.count(input, chars, CaseMode.SENSITIVE);

    assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.of(
        'a', 1,
        'B', 1,
        'c', 1
    ));
  }
}
