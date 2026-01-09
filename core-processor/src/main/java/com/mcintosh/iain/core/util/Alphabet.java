package com.mcintosh.iain.core.util;

import java.util.Set;

/**
 * Utility class providing predefined sets of characters for text processing.
 */
public final class Alphabet {

  private Alphabet() {
    throw new UnsupportedOperationException("Class not instantiable");
  }

  private static final Set<Character> VOWELS =
      Set.of('a','e','i','o','u');

  private static final Set<Character> CONSONANTS =
      Set.of('b','c','d','f','g','h','j','k','l','m','n','p','q','r','s','t','v','w','x','y','z');

  public static Set<Character> getVowels() {
    return VOWELS;
  }

  public static Set<Character> getConsonants() {
    return CONSONANTS;
  }
}
