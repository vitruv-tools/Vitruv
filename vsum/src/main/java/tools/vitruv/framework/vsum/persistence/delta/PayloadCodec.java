package tools.vitruv.framework.vsum.persistence.delta;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * The Deltachain's per-payload codec: a payload is either the JSON itself, or {@link
 * #DEFLATE_DICT_V1} followed by a raw DEFLATE-9 stream of it, compressed against the frozen
 * dictionary {@value #DICTIONARY_V1_RESOURCE}. The dictionary is a byte-for-byte copy of the
 * Deltachain's, pinned by SHA-256 in the tests.
 */
final class PayloadCodec {

  /** First byte of a raw-DEFLATE payload compressed against dictionary v1. */
  static final byte DEFLATE_DICT_V1 = 0x01;

  static final String DICTIONARY_V1_RESOURCE = "payload-dict-v1.txt";

  /** Upper bound of a decompressed payload, against a DEFLATE bomb in a log. */
  static final int MAX_JSON_BYTES = 64 * 1024 * 1024;

  private static final byte[] DICTIONARY_V1 = load(DICTIONARY_V1_RESOURCE);

  private PayloadCodec() {}

  /**
   * Compresses JSON against dictionary v1.
   *
   * @param json the JSON bytes
   * @return the compressed payload, or {@code json} itself if compression does not shrink it
   */
  static byte[] encode(byte[] json) {
    Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION, true);
    try {
      deflater.setDictionary(DICTIONARY_V1);
      deflater.setInput(json);
      deflater.finish();
      ByteArrayOutputStream out = new ByteArrayOutputStream(json.length / 8 + 64);
      out.write(DEFLATE_DICT_V1);
      byte[] buf = new byte[8192];
      while (!deflater.finished()) {
        out.write(buf, 0, deflater.deflate(buf));
        if (out.size() >= json.length) {
          return json;
        }
      }
      return out.toByteArray();
    } finally {
      deflater.end();
    }
  }

  /**
   * Returns the JSON of a payload in either form.
   *
   * @param payload the payload
   * @return the JSON bytes
   * @throws IOException if the payload is corrupt, truncated, inflates beyond {@value
   *     #MAX_JSON_BYTES} bytes, or starts with an unknown codec byte
   */
  static byte[] toJson(byte[] payload) throws IOException {
    if (payload.length == 0 || payload[0] == '{' || payload[0] == '[') {
      return payload;
    }
    if (payload[0] != DEFLATE_DICT_V1) {
      throw new IOException(
          "unknown payload codec byte 0x" + Integer.toHexString(payload[0] & 0xff));
    }
    Inflater inflater = new Inflater(true);
    try {
      inflater.setDictionary(DICTIONARY_V1);
      inflater.setInput(payload, 1, payload.length - 1);
      ByteArrayOutputStream out =
          new ByteArrayOutputStream((int) Math.min(MAX_JSON_BYTES, payload.length * 8L));
      byte[] buf = new byte[8192];
      while (!inflater.finished()) {
        int n = inflater.inflate(buf);
        if (n == 0 && (inflater.needsInput() || inflater.needsDictionary())) {
          throw new IOException("truncated DEFLATE payload");
        }
        if (out.size() + n > MAX_JSON_BYTES) {
          throw new IOException("DEFLATE payload exceeds " + MAX_JSON_BYTES + " bytes");
        }
        out.write(buf, 0, n);
      }
      return out.toByteArray();
    } catch (DataFormatException e) {
      throw new IOException("corrupt DEFLATE payload", e);
    } finally {
      inflater.end();
    }
  }

  static byte[] dictionaryV1() {
    return DICTIONARY_V1.clone();
  }

  private static byte[] load(String resource) {
    try (InputStream in = PayloadCodec.class.getResourceAsStream(resource)) {
      if (in == null) {
        throw new IllegalStateException("missing resource " + resource);
      }
      return in.readAllBytes();
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
