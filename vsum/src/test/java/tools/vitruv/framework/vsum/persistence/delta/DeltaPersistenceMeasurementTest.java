package tools.vitruv.framework.vsum.persistence.delta;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.aet;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestProjectManager;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.framework.vsum.persistence.ModelPersistence;
import tools.vitruv.framework.vsum.persistence.StateBasedModelPersistence;

/**
 * Bytes written per propagation, state-based against delta-based, on a growing model: the first
 * propagation inserts a root (the specification copies it), each further one adds an element with
 * two attributes, every fifth also changes an attribute of the root. Prints a table and writes it
 * to {@code target/d2-measurement.txt}.
 *
 * <p>Counted per propagation: the appended bytes of the log; the whole size of a file the
 * strategy rewrites every time ({@code uuid.uuid}, {@code models.models}, correspondences); the
 * whole size of any other file whose content changed (models, snapshot files).
 */
@ExtendWith(TestProjectManager.class)
class DeltaPersistenceMeasurementTest {
  private static final int PROPAGATIONS = 300;
  private static final Set<String> ALWAYS_REWRITTEN =
      Set.of("vsum/uuid.uuid", "vsum/models.models", "vsum/correspondences.correspondence");

  record Result(long bytes, long logBytes, long millis, long modelBytes) {}

  @Test
  @DisplayName("bytes per propagation: delta log against state-based files")
  void measureBytesPerPropagation(@TestProject Path project) throws Exception {
    Result state = run(project.resolve("state"), new StateBasedModelPersistence());
    final Result delta =
        run(
            project.resolve("delta"),
            new DeltaBasedModelPersistence(
                DeltaBasedModelPersistence.Options.defaults().withSyncLog(true)));
    final Result deltaNoSync =
        run(
            project.resolve("delta-nosync"),
            new DeltaBasedModelPersistence(
                DeltaBasedModelPersistence.Options.defaults().withSyncLog(false)));
    StringBuilder table = new StringBuilder();
    table.append(String.format("propagations: %d, final model file: %d bytes%n", PROPAGATIONS,
        state.modelBytes()));
    table.append("strategy | bytes written | per propagation | log per propagation | ms total\n");
    line(table, "state-based", state);
    line(table, "delta (sync, snapshot/50)", delta);
    line(table, "delta (no sync, snapshot/50)", deltaNoSync);
    System.out.print(table);
    Files.createDirectories(Path.of("target"));
    Files.writeString(Path.of("target", "d2-measurement.txt"), table, UTF_8);
    assertTrue(delta.bytes() < state.bytes(), table.toString());
  }

  private static void line(StringBuilder table, String name, Result result) {
    table.append(
        String.format(
            "%s | %d | %d | %d | %d%n",
            name,
            result.bytes(),
            result.bytes() / PROPAGATIONS,
            result.logBytes() / PROPAGATIONS,
            result.millis()));
  }

  private static Result run(Path folder, ModelPersistence persistence) throws Exception {
    InternalVirtualModel vsum = DeltaBasedModelPersistenceTest.vsum(folder, persistence);
    DeltaBasedModelPersistenceTest.Client client = new DeltaBasedModelPersistenceTest.Client(
        folder);
    Root root = aet.Root();
    Map<Path, String> hashes = hashes(folder);
    long bytes = 0;
    long logBytes = 0;
    long nanos = 0;
    for (int i = 1; i <= PROPAGATIONS; i++) {
      int n = i;
      Runnable edit =
          n == 1
              ? () -> {
                root.setId("root");
                client.createResource("root.allElementTypes").getContents().add(root);
              }
              : () -> {
                NonRoot element = aet.NonRoot();
                element.setId("element" + n);
                element.setValue("value of element " + n);
                root.getMultiValuedContainmentEReference().add(element);
                if (n % 5 == 0) {
                  root.setSingleValuedEAttribute(n);
                }
              };
      var change = client.record(edit);
      long start = System.nanoTime();
      vsum.propagateChange(change);
      nanos += System.nanoTime() - start;
      Map<Path, String> after = hashes(folder);
      for (Map.Entry<Path, String> file : after.entrySet()) {
        String name = folder.relativize(file.getKey()).toString().replace('\\', '/');
        long size = Files.size(file.getKey());
        if (name.equals("vsum/changes.log")) {
          long appended = size - (hashes.containsKey(file.getKey())
              ? Long.parseLong(hashes.get(file.getKey()).split(":")[0]) : 0);
          bytes += appended;
          logBytes += appended;
        } else if (ALWAYS_REWRITTEN.contains(name)
            || !file.getValue().equals(hashes.get(file.getKey()))) {
          bytes += size;
        }
      }
      hashes = after;
    }
    vsum.dispose();
    long modelBytes = Files.size(folder.resolve("root.allElementTypes"));
    return new Result(bytes, logBytes, nanos / 1_000_000, modelBytes);
  }

  /** Size and SHA-256 of every file below the folder, except constraint files. */
  private static Map<Path, String> hashes(Path folder) throws IOException {
    Map<Path, String> result = new HashMap<>();
    if (!Files.exists(folder)) {
      return result;
    }
    try (Stream<Path> files = Files.walk(folder)) {
      for (Path file : files.filter(Files::isRegularFile).toList()) {
        if (file.getFileName().toString().startsWith("constraints")) {
          continue;
        }
        byte[] content = Files.readAllBytes(file);
        result.put(file, content.length + ":" + sha256(content));
      }
    }
    return result;
  }

  private static String sha256(byte[] content) {
    try {
      return java.util.HexFormat.of().formatHex(
          MessageDigest.getInstance("SHA-256").digest(content));
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
  }
}
