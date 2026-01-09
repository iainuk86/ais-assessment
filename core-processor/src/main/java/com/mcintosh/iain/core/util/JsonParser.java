package com.mcintosh.iain.core.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Utility class providing a singleton instance for JSON serialization and deserialization.
 * <p>
 * Use {@link #instance()} to access the shared instance.
 * <p>
 * Example usage:
 * <pre>{@code
 * // Serialisation
 * Map<String, Integer> data = Map.of("a", 1, "b", 2);
 * String json = JsonParser.instance().toJson(data);
 *
 * // Deserialisation
 * Map<?, ?> parsed = JsonParser.instance().fromJson(json, Map.class);
 * }</pre>
 */
public final class JsonParser {

  private JsonParser() {
    throw new UnsupportedOperationException("Class not instantiable");
  }

  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  /**
   * Returns the shared instance for JSON processing.
   *
   * @return the singleton GSON object
   */
  public static Gson instance() {
    return GSON;
  }
}
