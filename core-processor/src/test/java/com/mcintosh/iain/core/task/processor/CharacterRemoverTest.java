package com.mcintosh.iain.core.task.processor;

import static org.assertj.core.api.Assertions.assertThat;

import com.mcintosh.iain.core.task.enums.CaseMode;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CharacterRemoverTest {

  @Test
  void removesSpecifiedCharacters_caseSensitive() {
    String input = "banana";
    Set<Character> charsToRemove = Set.of('a');

    String result = CharacterRemover.execute(input, charsToRemove, CaseMode.SENSITIVE);

    assertThat(result).isEqualTo("bnn");
  }

  @Test
  void removesSpecifiedCharacters_caseInsensitive() {
    String input = "BaNaNa";
    Set<Character> charsToRemove = Set.of('a');

    String result = CharacterRemover.execute(input, charsToRemove, CaseMode.INSENSITIVE);

    // Original casing preserved, comparison is case-insensitive
    assertThat(result).isEqualTo("BNN");
  }

  @Test
  void removesCharactersInADifferentCaseModeIfCaseInsensitive() {
    String input = "BaNaNa";
    Set<Character> charsToRemove = Set.of('n');

    String result = CharacterRemover.execute(input, charsToRemove, CaseMode.INSENSITIVE);

    assertThat(result).isEqualTo("Baaa");
  }

  @Test
  void doesNotRemoveCharactersInADifferentCaseModeIfCaseSensitive() {
    String input = "BaNaNa";
    Set<Character> charsToRemove = Set.of('n');

    String result = CharacterRemover.execute(input, charsToRemove, CaseMode.SENSITIVE);

    assertThat(result).isEqualTo("BaNaNa");
  }

  @Test
  void preservesCharactersNotInRemovalSet() {
    String input = "hello";
    Set<Character> charsToRemove = Set.of('x', 'y', 'z');

    String result = CharacterRemover.execute(input, charsToRemove, CaseMode.INSENSITIVE);

    assertThat(result).isEqualTo("hello");
  }

  @Test
  void emptyInputReturnsEmptyString() {
    String result = CharacterRemover.execute(
        "",
        Set.of('a', 'e'),
        CaseMode.INSENSITIVE
    );

    assertThat(result).isEmpty();
  }

  @Test
  void removalSetEmptyReturnsOriginalString() {
    String input = "Hello World!";

    String result = CharacterRemover.execute(
        input,
        Set.of(),
        CaseMode.INSENSITIVE
    );

    assertThat(result).isEqualTo(input);
  }

}
