package com.mcintosh.iain.core.task.enums;

/**
 * Represents whether the file processing should take character casing into account.
 * Enum Strategy pattern used for cleaner code and to make it easy to extend.
 */
public enum CaseMode {
  SENSITIVE {
    @Override
    public char normalise(char c) { return c; }
  },
  INSENSITIVE {
    @Override
    public char normalise(char c) { return Character.toLowerCase(c); }
  };

  public abstract char normalise(char c);
}
