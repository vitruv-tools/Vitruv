package tools.vitruv.framework.vsum.persistence.delta;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Minimal JSON for the change log, so that the framework needs no JSON library: objects are
 * {@link Map}s with insertion order, arrays {@link List}s, numbers {@link Long} (integral) or
 * {@link Double}, plus {@link String}, {@link Boolean} and {@code null}.
 */
final class Json {

  private Json() {}

  /**
   * Writes a value as compact JSON.
   *
   * @param value a map, list, string, number, boolean or null
   * @return the JSON text
   */
  static String write(Object value) {
    StringBuilder out = new StringBuilder();
    write(value, out);
    return out.toString();
  }

  private static void write(Object value, StringBuilder out) {
    if (value == null) {
      out.append("null");
    } else if (value instanceof String text) {
      writeString(text, out);
    } else if (value instanceof Boolean || value instanceof Long || value instanceof Integer) {
      out.append(value);
    } else if (value instanceof Number number) {
      double d = number.doubleValue();
      if (Double.isNaN(d) || Double.isInfinite(d)) {
        throw new IllegalArgumentException("JSON has no " + d);
      }
      out.append(d);
    } else if (value instanceof Map<?, ?> map) {
      out.append('{');
      boolean first = true;
      for (Map.Entry<?, ?> entry : map.entrySet()) {
        if (!first) {
          out.append(',');
        }
        first = false;
        writeString((String) entry.getKey(), out);
        out.append(':');
        write(entry.getValue(), out);
      }
      out.append('}');
    } else if (value instanceof List<?> list) {
      out.append('[');
      for (int i = 0; i < list.size(); i++) {
        if (i > 0) {
          out.append(',');
        }
        write(list.get(i), out);
      }
      out.append(']');
    } else {
      throw new IllegalArgumentException("not a JSON value: " + value.getClass().getName());
    }
  }

  private static void writeString(String text, StringBuilder out) {
    out.append('"');
    for (int i = 0; i < text.length(); i++) {
      char c = text.charAt(i);
      switch (c) {
        case '"' -> out.append("\\\"");
        case '\\' -> out.append("\\\\");
        case '\n' -> out.append("\\n");
        case '\r' -> out.append("\\r");
        case '\t' -> out.append("\\t");
        case '\b' -> out.append("\\b");
        case '\f' -> out.append("\\f");
        default -> {
          if (c < 0x20 || c == ' ' || c == ' ') {
            out.append(String.format("\\u%04x", (int) c));
          } else {
            out.append(c);
          }
        }
      }
    }
    out.append('"');
  }

  /**
   * Parses one JSON value that spans the whole text.
   *
   * @param text the JSON text
   * @return the value
   * @throws IllegalArgumentException if the text is not exactly one JSON value
   */
  static Object parse(String text) {
    Parser parser = new Parser(text);
    Object value = parser.value();
    parser.skipWhitespace();
    if (parser.pos != text.length()) {
      throw parser.error("trailing characters");
    }
    return value;
  }

  private static final class Parser {
    private final String text;
    private int pos;

    Parser(String text) {
      this.text = text;
    }

    Object value() {
      skipWhitespace();
      if (pos >= text.length()) {
        throw error("unexpected end");
      }
      char c = text.charAt(pos);
      return switch (c) {
        case '{' -> object();
        case '[' -> array();
        case '"' -> string();
        case 't' -> literal("true", Boolean.TRUE);
        case 'f' -> literal("false", Boolean.FALSE);
        case 'n' -> literal("null", null);
        default -> number();
      };
    }

    private Map<String, Object> object() {
      Map<String, Object> map = new LinkedHashMap<>();
      pos++;
      skipWhitespace();
      if (peek() == '}') {
        pos++;
        return map;
      }
      while (true) {
        skipWhitespace();
        if (peek() != '"') {
          throw error("expected a key");
        }
        String key = string();
        skipWhitespace();
        expect(':');
        map.put(key, value());
        skipWhitespace();
        char c = next();
        if (c == '}') {
          return map;
        }
        if (c != ',') {
          throw error("expected , or }");
        }
      }
    }

    private List<Object> array() {
      List<Object> list = new ArrayList<>();
      pos++;
      skipWhitespace();
      if (peek() == ']') {
        pos++;
        return list;
      }
      while (true) {
        list.add(value());
        skipWhitespace();
        char c = next();
        if (c == ']') {
          return list;
        }
        if (c != ',') {
          throw error("expected , or ]");
        }
      }
    }

    private String string() {
      expect('"');
      StringBuilder out = new StringBuilder();
      while (true) {
        char c = next();
        if (c == '"') {
          return out.toString();
        }
        if (c != '\\') {
          out.append(c);
          continue;
        }
        char escaped = next();
        switch (escaped) {
          case '"', '\\', '/' -> out.append(escaped);
          case 'n' -> out.append('\n');
          case 'r' -> out.append('\r');
          case 't' -> out.append('\t');
          case 'b' -> out.append('\b');
          case 'f' -> out.append('\f');
          case 'u' -> {
            if (pos + 4 > text.length()) {
              throw error("truncated \\u escape");
            }
            try {
              out.append((char) Integer.parseInt(text.substring(pos, pos + 4), 16));
            } catch (NumberFormatException e) {
              throw error("bad \\u escape");
            }
            pos += 4;
          }
          default -> throw error("bad escape \\" + escaped);
        }
      }
    }

    private Object literal(String word, Object value) {
      if (!text.startsWith(word, pos)) {
        throw error("expected " + word);
      }
      pos += word.length();
      return value;
    }

    private Object number() {
      int start = pos;
      boolean integral = true;
      while (pos < text.length()) {
        char c = text.charAt(pos);
        if (c == '.' || c == 'e' || c == 'E') {
          integral = false;
        } else if (!(c == '-' || c == '+' || (c >= '0' && c <= '9'))) {
          break;
        }
        pos++;
      }
      String number = text.substring(start, pos);
      try {
        return integral ? (Object) Long.parseLong(number) : (Object) Double.parseDouble(number);
      } catch (NumberFormatException e) {
        throw error("not a value: '" + number + "'");
      }
    }

    void skipWhitespace() {
      while (pos < text.length() && Character.isWhitespace(text.charAt(pos))) {
        pos++;
      }
    }

    private char peek() {
      if (pos >= text.length()) {
        throw error("unexpected end");
      }
      return text.charAt(pos);
    }

    private char next() {
      char c = peek();
      pos++;
      return c;
    }

    private void expect(char c) {
      if (next() != c) {
        pos--;
        throw error("expected " + c);
      }
    }

    IllegalArgumentException error(String message) {
      return new IllegalArgumentException("JSON at offset " + pos + ": " + message);
    }
  }
}
