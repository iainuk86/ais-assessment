package com.mcintosh.iain.core.task.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import com.mcintosh.iain.core.task.enums.CaseMode;
import org.junit.jupiter.api.Test;

class VowelRemoverTest {

  private final VowelRemover vowelRemover = new VowelRemover();

  @Test
  void execute_removesVowels_caseInsensitiveByDefault() {
    String input = "Hello World";

    String result = vowelRemover.execute(input);

    assertThat(result).isEqualTo("Hll Wrld");
  }

  @Test
  void execute_removesVowels_caseSensitive() {
    String input = "HellO World";

    String result = vowelRemover.execute(input, CaseMode.SENSITIVE);

    // Only lowercase vowels removed
    assertThat(result).isEqualTo("HllO Wrld");
  }

  @Test
  void execute_preservesOriginalCasing() {
    String input = "AeIoU";

    String result = vowelRemover.execute(input);

    assertThat(result).isEmpty();
  }

  @Test
  void execute_stringWithNoVowelsReturnsSameString() {
    String input = "rhythms";

    String result = vowelRemover.execute(input);

    assertThat(result).isEqualTo(input);
  }

  @Test
  void execute_emptyInputReturnsEmptyString() {
    String result = vowelRemover.execute("");

    assertThat(result).isEmpty();
  }
}
