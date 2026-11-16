package tools.vitruv.framework.vsum.persistence.delta;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The change log {@code vsum/changes.log}: one JSON record per propagation and line, appended,
 * never rewritten. A record reads
 *
 * <pre>{"seq":7,"tx":"&lt;uuid&gt;","time":"&lt;ISO-8601&gt;","original":P,"consequential":P,
 *   "correspondences":C}</pre>
 *
 * <p>{@code seq} counts the propagations of the virtual model from 1. {@code original} holds the
 * propagated change, {@code consequential} the changes recorded while propagating it, both in
 * application order. Each {@code P} is a list of transactional changes, each a list of changes in
 * the Deltachain schema ({@link EChangeJson}), either as that JSON array or, if it is smaller so,
 * as the Base64 string of its {@link PayloadCodec} form ({@code 0x01} + DEFLATE against dictionary
 * v1).
 *
 * <p>{@code C} lists the changes of the correspondence model in that propagation as operations on
 * its list of correspondences ({@link CorrespondenceLog}), stored like {@code P}.
 *
 * <p>Reading tolerates a torn last line (the process died while appending): it is moved to a side
 * file {@code changes.log.torn-<millis>} and cut from the log, so that the next record starts on a
 * fresh line. Any other line that cannot be read fails the load with its line number.
 */
final class ChangeLog {
  static final String FILE_NAME = "changes.log";
  static final String SEQ = "seq";
  static final String TX = "tx";
  static final String TIME = "time";
  static final String ORIGINAL = "original";
  static final String CONSEQUENTIAL = "consequential";
  static final String CORRESPONDENCES = "correspondences";

  private static final Logger LOGGER = LogManager.getLogger(ChangeLog.class);

  private final Path file;
  private final boolean sync;

  /**
   * Creates the log at a file.
   *
   * @param file the log file
   * @param sync whether every append is forced to the storage device
   */
  ChangeLog(Path file, boolean sync) {
    this.file = file;
    this.sync = sync;
  }

  Path file() {
    return file;
  }

  /**
   * One record as stored; its payloads are decoded on demand.
   *
   * @param line the line number in the log, from 1
   * @param seq the propagation number
   * @param tx the transaction ID
   * @param time the time of the propagation
   * @param original the stored payload of the propagated change
   * @param consequential the stored payload of the consequential changes
   * @param correspondences the stored payload of the correspondence changes
   */
  record Entry(
      int line,
      long seq,
      String tx,
      String time,
      Object original,
      Object consequential,
      Object correspondences) {
    List<List<Map<String, Object>>> originalGroups() {
      return decode(original, line);
    }

    List<List<Map<String, Object>>> consequentialGroups() {
      return decode(consequential, line);
    }

    List<Map<String, Object>> correspondenceOperations() {
      return decodeList(correspondences, line);
    }
  }

  /**
   * Reads every record. A torn last line is reported, kept in a side file and cut from the log.
   *
   * @return the records in file order; empty if there is no log
   * @throws IOException if the log cannot be read or cut
   * @throws IllegalStateException if a line other than the last one cannot be read
   */
  List<Entry> read() throws IOException {
    List<Entry> entries = new ArrayList<>();
    if (!Files.exists(file)) {
      return entries;
    }
    byte[] bytes = Files.readAllBytes(file);
    List<int[]> lines = new ArrayList<>(); // {start, end} without the line break
    int start = 0;
    for (int i = 0; i < bytes.length; i++) {
      if (bytes[i] == '\n') {
        lines.add(new int[] {start, i});
        start = i + 1;
      }
    }
    if (start < bytes.length) {
      lines.add(new int[] {start, bytes.length});
    }
    int last = lines.size() - 1;
    while (last >= 0 && isBlank(bytes, lines.get(last))) {
      last--;
    }
    for (int i = 0; i <= last; i++) {
      int[] range = lines.get(i);
      if (isBlank(bytes, range)) {
        continue;
      }
      String text = new String(bytes, range[0], range[1] - range[0], UTF_8);
      try {
        entries.add(parse(text, i + 1));
      } catch (IllegalArgumentException | ClassCastException e) {
        if (i < last) {
          throw new IllegalStateException(
              file + " line " + (i + 1) + " is corrupt: " + e.getMessage(), e);
        }
        cutTornLine(bytes, range[0], i + 1, e);
      } catch (IllegalStateException e) {
        throw new IllegalStateException(file + " " + e.getMessage(), e);
      }
    }
    return entries;
  }

  private static boolean isBlank(byte[] bytes, int[] range) {
    for (int i = range[0]; i < range[1]; i++) {
      if (!Character.isWhitespace(bytes[i])) {
        return false;
      }
    }
    return true;
  }

  private void cutTornLine(byte[] bytes, int lineStart, int lineNumber, RuntimeException cause)
      throws IOException {
    Path side = file.resolveSibling(FILE_NAME + ".torn-" + System.currentTimeMillis());
    Files.write(side, Arrays.copyOfRange(bytes, lineStart, bytes.length));
    try (FileChannel channel = FileChannel.open(file, StandardOpenOption.WRITE)) {
      channel.truncate(lineStart);
    }
    LOGGER.warn(
        "{} line {} is torn ({}); skipped it and moved its {} bytes to {}",
        file,
        lineNumber,
        cause.getMessage(),
        bytes.length - lineStart,
        side);
  }

  private static Entry parse(String text, int line) {
    if (!(Json.parse(text) instanceof Map<?, ?> json)) {
      throw new IllegalArgumentException("not a JSON object");
    }
    Object seq = json.get(SEQ);
    Object original = json.get(ORIGINAL);
    Object consequential = json.get(CONSEQUENTIAL);
    Object correspondences = json.get(CORRESPONDENCES);
    if (!(seq instanceof Long number)
        || original == null
        || consequential == null
        || correspondences == null) {
      // a complete JSON object with missing fields is not torn: fail instead of cutting it
      throw new IllegalStateException(
          "line " + line + ": record without seq, original, consequential or correspondences");
    }
    return new Entry(
        line,
        number,
        (String) json.get(TX),
        (String) json.get(TIME),
        original,
        consequential,
        correspondences);
  }

  /**
   * Appends one record as a line.
   *
   * @param seq the propagation number
   * @param tx the transaction ID
   * @param time the time of the propagation
   * @param original the propagated change as transactional changes in JSON
   * @param consequential the consequential changes as transactional changes in JSON
   * @param correspondences the changes of the correspondence model as operations in JSON
   * @return the number of bytes appended
   * @throws IOException if the record cannot be written
   */
  int append(
      long seq,
      String tx,
      String time,
      List<List<Map<String, Object>>> original,
      List<List<Map<String, Object>>> consequential,
      List<Map<String, Object>> correspondences)
      throws IOException {
    Map<String, Object> record = new LinkedHashMap<>();
    record.put(SEQ, seq);
    record.put(TX, tx);
    record.put(TIME, time);
    record.put(ORIGINAL, encode(original));
    record.put(CONSEQUENTIAL, encode(consequential));
    record.put(CORRESPONDENCES, encode(correspondences));
    byte[] line = (Json.write(record) + "\n").getBytes(UTF_8);
    try (FileChannel channel =
        FileChannel.open(
            file,
            StandardOpenOption.CREATE,
            StandardOpenOption.READ,
            StandardOpenOption.WRITE)) {
      long size = channel.size();
      ByteBuffer buffer = ByteBuffer.wrap(line);
      if (size > 0) {
        ByteBuffer lastByte = ByteBuffer.allocate(1);
        channel.read(lastByte, size - 1);
        if (lastByte.get(0) != '\n') {
          buffer = ByteBuffer.allocate(line.length + 1).put((byte) '\n').put(line).flip();
        }
      }
      channel.position(size);
      while (buffer.hasRemaining()) {
        channel.write(buffer);
      }
      if (sync) {
        channel.force(false);
      }
      return (int) (channel.position() - size);
    }
  }

  /**
   * Forces the records appended so far to the storage device, also those appended without {@code
   * sync}.
   *
   * @throws IOException if forcing fails
   */
  void force() throws IOException {
    if (Files.exists(file)) {
      try (FileChannel channel = FileChannel.open(file, StandardOpenOption.WRITE)) {
        channel.force(false);
      }
    }
  }

  /**
   * Moves the log into an archive folder as {@code changes-<lastSeq>.log}, so that the next record
   * starts a new log. Called right after a snapshot that contains every record of the log.
   *
   * @param archiveFolder the folder to move the log to
   * @param lastSeq the number of the last record in the log
   * @throws IOException if moving fails
   */
  void archive(Path archiveFolder, long lastSeq) throws IOException {
    if (Files.exists(file)) {
      Files.createDirectories(archiveFolder);
      Files.move(
          file,
          archiveFolder.resolve("changes-" + lastSeq + ".log"),
          StandardCopyOption.ATOMIC_MOVE);
    }
  }

  /**
   * Deletes the log. Called right after a snapshot that contains every record of the log.
   *
   * @throws IOException if deleting fails
   */
  void delete() throws IOException {
    Files.deleteIfExists(file);
  }

  /**
   * Encodes a list for a record (transactional changes or correspondence operations): the JSON
   * array, or the Base64 string of its compressed form if that is shorter.
   */
  static Object encode(List<?> groups) {
    byte[] json = Json.write(groups).getBytes(UTF_8);
    byte[] payload = PayloadCodec.encode(json);
    if (payload == json) {
      return groups;
    }
    String base64 = Base64.getEncoder().encodeToString(payload);
    return base64.length() < json.length ? base64 : groups;
  }

  /** Decodes correspondence operations stored by {@link #encode}. */
  @SuppressWarnings("unchecked")
  static List<Map<String, Object>> decodeList(Object stored, int line) {
    Object operations = stored instanceof String base64 ? inflate(base64, line) : stored;
    if (!(operations instanceof List<?> list) || !list.stream().allMatch(Map.class::isInstance)) {
      throw new IllegalStateException(
          "changes.log line " + line + ": correspondences are not a list of operations");
    }
    return (List<Map<String, Object>>) list;
  }

  private static Object inflate(String base64, int line) {
    try {
      return Json.parse(
          new String(PayloadCodec.toJson(Base64.getDecoder().decode(base64)), UTF_8));
    } catch (IOException | IllegalArgumentException e) {
      throw new IllegalStateException("changes.log line " + line + ": " + e.getMessage(), e);
    }
  }

  @SuppressWarnings("unchecked")
  static List<List<Map<String, Object>>> decode(Object stored, int line) {
    Object groups = stored instanceof String base64 ? inflate(base64, line) : stored;
    if (!(groups instanceof List<?> list)
        || !list.stream().allMatch(group -> group instanceof List<?> changes
            && changes.stream().allMatch(Map.class::isInstance))) {
      throw new IllegalStateException(
          "changes.log line " + line + ": changes are not a list of change lists");
    }
    return (List<List<Map<String, Object>>>) list;
  }
}
