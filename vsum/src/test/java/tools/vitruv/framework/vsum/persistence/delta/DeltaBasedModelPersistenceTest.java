package tools.vitruv.framework.vsum.persistence.delta;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.aet;

import allElementTypes.AllElementTypesPackage;
import allElementTypes.NonRoot;
import allElementTypes.Root;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.composite.MetamodelDescriptor;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.interaction.UserInteractionFactory;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestProjectManager;
import tools.vitruv.framework.vsum.VirtualModelBuilder;
import tools.vitruv.framework.vsum.VirtualModelTestUtil;
import tools.vitruv.framework.vsum.VirtualModelTestUtil.RedundancyChangePropagationSpecification;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.framework.vsum.persistence.ModelPersistence;
import tools.vitruv.framework.vsum.persistence.StateBasedModelPersistence;

/** Delta-based persistence: log, replay, snapshots, damaged logs, adoption of state-based files. */
@ExtendWith(TestProjectManager.class)
class DeltaBasedModelPersistenceTest {
  private static final String MODEL = "root.allElementTypes";
  private static final String MODEL_COPY = "rootCopy.allElementTypes";
  private static final String LOG = "vsum/changes.log";

  /** A client that edits its own copy of the models and records its changes for a VSUM. */
  static final class Client {
    final ResourceSet resourceSet = withGlobalFactories(new ResourceSetImpl());
    final UuidResolver uuidResolver = UuidResolver.create(resourceSet);
    final Path folder;

    Client(Path folder) {
      this.folder = folder;
    }

    VitruviusChange<Uuid> record(Runnable edit) {
      return VirtualModelTestUtil.recordChanges(resourceSet, uuidResolver, edit);
    }

    Resource createResource(String name) {
      return resourceSet.createResource(URI.createFileURI(folder.resolve(name).toString()));
    }
  }

  /** Edits covering inserts, removes, list shifts, cross-resource references and root deletes. */
  static final class Scenario {
    final Client client;
    Root root;
    Root root2;
    NonRoot a;
    NonRoot b;
    NonRoot c;
    NonRoot x;
    Resource resource2;

    Scenario(Client client) {
      this.client = client;
    }

    List<Runnable> steps() {
      return List.of(
          () -> {
            root = aet.Root();
            root.setId("root");
            root.setSingleValuedContainmentEReference(nonRoot("n0"));
            client.createResource(MODEL).getContents().add(root);
          },
          () -> {
            a = nonRoot("a");
            b = nonRoot("b");
            c = nonRoot("c");
            root.getMultiValuedContainmentEReference().addAll(List.of(a, b, c));
          },
          () -> root.getMultiValuedContainmentEReference().remove(b),
          () -> root.getMultiValuedContainmentEReference().add(0, nonRoot("d")),
          () -> root.getMultiValuedContainmentEReference().move(0, c),
          () -> {
            root2 = aet.Root();
            root2.setId("root2");
            x = nonRoot("x");
            root2.getMultiValuedContainmentEReference().add(x);
            resource2 = client.createResource("root2.allElementTypes");
            resource2.getContents().add(root2);
          },
          () -> {
            root.setSingleValuedNonContainmentEReference(x);
            root.getMultiValuedNonContainmentEReference().addAll(List.of(a, x));
          },
          () -> {
            root.setSingleValuedEAttribute(7);
            root.getMultiValuedEAttribute().addAll(List.of(1, 2, 3));
            root.getMultiValuedEAttribute().remove(Integer.valueOf(2));
            a.setValue("value of a");
          },
          () -> {
            root.setSingleValuedNonContainmentEReference(null);
            root.getMultiValuedNonContainmentEReference().remove(x);
            resource2.getContents().remove(root2);
          },
          () -> EcoreUtil.delete(a, true));
    }

    static NonRoot nonRoot(String id) {
      NonRoot nonRoot = aet.NonRoot();
      nonRoot.setId(id);
      return nonRoot;
    }
  }

  static InternalVirtualModel vsum(Path folder, ModelPersistence persistence) throws IOException {
    return new VirtualModelBuilder()
        .withStorageFolder(folder)
        .withChangePropagationSpecification(
            new RedundancyChangePropagationSpecification(
                MetamodelDescriptor.with(AllElementTypesPackage.eNS_URI),
                MetamodelDescriptor.with(AllElementTypesPackage.eNS_URI)))
        .withUserInteractor(
            UserInteractionFactory.instance.createUserInteractor(
                UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null)))
        .withModelPersistence(persistence)
        .buildAndInitialize();
  }

  static DeltaBasedModelPersistence delta(int snapshotInterval) {
    return new DeltaBasedModelPersistence(
        DeltaBasedModelPersistence.Options.defaults().withSnapshotInterval(snapshotInterval));
  }

  static void runScenario(InternalVirtualModel vsum, Path folder) {
    Scenario scenario = new Scenario(new Client(folder));
    for (Runnable step : scenario.steps()) {
      vsum.propagateChange(scenario.client.record(step));
    }
  }

  /** Copies a storage folder while its VSUM is still open: what a crash would leave behind. */
  static void copyFolder(Path source, Path target) throws IOException {
    try (Stream<Path> files = Files.walk(source)) {
      for (Path file : files.toList()) {
        Path copy = target.resolve(source.relativize(file).toString());
        if (Files.isDirectory(file)) {
          Files.createDirectories(copy);
        } else {
          Files.copy(file, copy);
        }
      }
    }
  }

  /** The model files of a folder (top level only), line endings unified. */
  static Map<String, String> modelFiles(Path folder) throws IOException {
    Map<String, String> files = new TreeMap<>();
    try (Stream<Path> list = Files.list(folder)) {
      for (Path file : list.filter(it -> it.toString().endsWith(".allElementTypes")).toList()) {
        files.put(
            file.getFileName().toString(), Files.readString(file, UTF_8).replace("\r\n", "\n"));
      }
    }
    return files;
  }

  static Root rootOf(InternalVirtualModel vsum, Path folder, String model) {
    return (Root)
        vsum.getModelInstance(URI.createFileURI(folder.resolve(model).toString()))
            .getResource()
            .getContents()
            .get(0);
  }

  @Test
  @DisplayName("replaying the log gives the state that state-based persistence stores")
  void replayGivesStateBasedState(@TestProject Path project) throws Exception {
    Path stateFolder = project.resolve("state");
    InternalVirtualModel stateVsum = vsum(stateFolder, new StateBasedModelPersistence());
    runScenario(stateVsum, stateFolder);
    stateVsum.dispose();

    Path deltaFolder = project.resolve("delta");
    InternalVirtualModel deltaVsum = vsum(deltaFolder, delta(1000));
    runScenario(deltaVsum, deltaFolder);
    assertEquals(10, Files.readAllLines(deltaFolder.resolve(LOG)).size(), "one record each");
    deltaVsum.dispose(); // writes the snapshot

    Map<String, String> expected = modelFiles(stateFolder);
    assertFalse(expected.containsKey("root2.allElementTypes"), "the deleted root's model is gone");
    assertTrue(expected.containsKey("root2Copy.allElementTypes"), "consequential model");
    assertEquals(expected, modelFiles(deltaFolder), "snapshot equals state-based files");
    String expectedCorrespondences = correspondences(stateFolder);
    assertTrue(expectedCorrespondences.contains("rootCopy.allElementTypes"), "has correspondences");
    assertFalse(expectedCorrespondences.contains("root2"), "the deleted root's one is dropped");
    assertEquals(expectedCorrespondences, correspondences(deltaFolder), "snapshot correspondences");

    // Rebuild from the log alone: no snapshot, no model files, no correspondences.
    Path rebuiltFolder = project.resolve("rebuilt");
    copyFolder(deltaFolder, rebuiltFolder);
    deleteSnapshot(rebuiltFolder);
    DeltaBasedModelPersistence rebuiltPersistence = delta(1000);
    InternalVirtualModel rebuilt = vsum(rebuiltFolder, rebuiltPersistence);
    assertEquals(10, rebuiltPersistence.getLastRecord());
    assertEquals(0, rebuiltPersistence.getSnapshotRecord());
    rebuilt.dispose();
    assertEquals(expected, modelFiles(rebuiltFolder), "replay equals state-based files");
    assertEquals(
        Files.readString(deltaFolder.resolve("vsum/snapshot/uuid.uuid")),
        Files.readString(rebuiltFolder.resolve("vsum/snapshot/uuid.uuid")),
        "replay restores the same UUIDs");
    assertEquals(
        expectedCorrespondences, correspondences(rebuiltFolder), "replay restores correspondences");
  }

  static final String CORRESPONDENCES = "vsum/correspondences.correspondence";

  /** The correspondence file of a folder, line endings unified. */
  static String correspondences(Path folder) throws IOException {
    return Files.readString(folder.resolve(CORRESPONDENCES), UTF_8).replace("\r\n", "\n");
  }

  @Test
  @DisplayName("correspondence edits are logged and replayed, also between propagations")
  void correspondenceEditsAreReplayed(@TestProject Path project) throws Exception {
    Path folder = project.resolve("open");
    InternalVirtualModel open = vsum(folder, delta(1000));
    Client client = new Client(folder);
    Root root = aet.Root();
    open.propagateChange(
        client.record(
            () -> {
              root.setId("root");
              client.createResource(MODEL).getContents().add(root);
            }));
    Root vsumRoot = rootOf(open, folder, MODEL);
    Root vsumCopy = rootOf(open, folder, MODEL_COPY);
    // removes the reaction's correspondence (a null tag matches every tag)
    open.getCorrespondenceModel().removeCorrespondencesBetween(vsumRoot, vsumCopy, null);
    open.getCorrespondenceModel().addCorrespondenceBetween(vsumRoot, vsumCopy, "manual");
    open.getCorrespondenceModel().addCorrespondenceBetween(vsumCopy, vsumRoot, "reverse");
    open.propagateChange(client.record(() -> root.setSingleValuedEAttribute(1)));
    open.getCorrespondenceModel().removeCorrespondencesBetween(vsumCopy, vsumRoot, "reverse");
    open.propagateChange(client.record(() -> root.setSingleValuedEAttribute(2)));
    Path crashed = project.resolve("crashed");
    copyFolder(folder, crashed);
    open.dispose();

    List<ChangeLog.Entry> entries = new ChangeLog(crashed.resolve(LOG), false).read();
    assertEquals(3, entries.size());
    assertEquals(
        List.of("insert 0 null"), operations(entries.get(0)), "the reaction's correspondence");
    assertEquals(
        List.of("remove 0 -", "insert 0 manual", "insert 1 reverse"),
        operations(entries.get(1)),
        "edits between propagations go into the next record");
    assertEquals(List.of("remove 1 -"), operations(entries.get(2)));
    Map<String, Object> insert = entries.get(1).correspondenceOperations().get(1);
    assertEquals(
        "http://vitruv.tools/metamodels/change/correspondence/1.0#//ManualCorrespondence",
        insert.get(CorrespondenceLog.TYPE));
    assertTrue(
        ((List<?>) insert.get(CorrespondenceLog.LEFT))
            .get(0)
            .toString()
            .matches(Pattern.quote(MODEL) + "#[^|]+\\|u=.+"),
        insert.toString());
    assertFalse(Files.exists(crashed.resolve(CORRESPONDENCES)), "written with snapshots only");

    DeltaBasedModelPersistence persistence = delta(1000);
    InternalVirtualModel reopened = vsum(crashed, persistence);
    assertEquals(0, persistence.getSnapshotRecord());
    Root reopenedRoot = rootOf(reopened, crashed, MODEL);
    Root reopenedCopy = rootOf(reopened, crashed, MODEL_COPY);
    assertEquals(
        java.util.Set.of(reopenedCopy),
        reopened.getCorrespondenceModel().getCorrespondingEObjects(reopenedRoot, "manual"));
    assertEquals(
        java.util.Set.of(reopenedCopy),
        reopened.getCorrespondenceModel().getCorrespondingEObjects(reopenedRoot, null),
        "the removed correspondences stay removed");
    reopened.dispose();
  }

  /** The correspondence operations of a record as {@code "<op> <index> <tag>"}. */
  static List<String> operations(ChangeLog.Entry entry) {
    return entry.correspondenceOperations().stream()
        .map(
            it -> {
              Object tag = "-";
              if (it.get(CorrespondenceLog.ATTRIBUTES) instanceof Map<?, ?> attributes) {
                tag = attributes.get("tag");
              } else if (CorrespondenceLog.INSERT.equals(it.get(CorrespondenceLog.OP))) {
                tag = null;
              }
              return it.get(CorrespondenceLog.OP) + " " + it.get(CorrespondenceLog.INDEX) + " "
                  + tag;
            })
        .toList();
  }

  @Test
  @DisplayName("a record without correspondences fails the load and names its line")
  void recordWithoutCorrespondencesFails(@TestProject Path project) throws Exception {
    Path folder = project.resolve("open");
    InternalVirtualModel open = vsum(folder, delta(1000));
    runScenario(open, folder);
    Path broken = project.resolve("broken");
    copyFolder(folder, broken);
    open.dispose();
    List<String> lines = new ArrayList<>();
    for (String line : Files.readAllLines(broken.resolve(LOG), UTF_8)) {
      lines.add(line.replaceFirst(",\"correspondences\":(\\[.*\\]|\"[^\"]*\")}$", "}"));
    }
    assertTrue(lines.stream().noneMatch(it -> it.contains("correspondences")));
    Files.write(broken.resolve(LOG), lines, UTF_8);

    RuntimeException failure =
        assertThrows(RuntimeException.class, () -> vsum(broken, delta(1000)));
    assertTrue(failure.getMessage().contains("line 1"), failure.getMessage());
  }

  @ParameterizedTest(name = "{0}")
  @ValueSource(strings = {"ARCHIVE", "DELETE"})
  @DisplayName("a snapshot archives or deletes the records it contains, and reopening works")
  void logRetentionAfterSnapshot(String retention, @TestProject Path project) throws Exception {
    DeltaBasedModelPersistence.LogRetention logRetention =
        DeltaBasedModelPersistence.LogRetention.valueOf(retention);
    Path stateFolder = project.resolve("state");
    InternalVirtualModel stateVsum = vsum(stateFolder, new StateBasedModelPersistence());
    runScenario(stateVsum, stateFolder);
    stateVsum.dispose();

    Path folder = project.resolve("delta");
    DeltaBasedModelPersistence persistence =
        new DeltaBasedModelPersistence(
            DeltaBasedModelPersistence.Options.defaults()
                .withSnapshotInterval(3)
                .withLogRetention(logRetention));
    InternalVirtualModel open = vsum(folder, persistence);
    runScenario(open, folder);
    assertEquals(9, persistence.getSnapshotRecord());
    assertEquals(1, Files.readAllLines(folder.resolve(LOG)).size(), "only record 10 is left");
    open.dispose();
    assertFalse(Files.exists(folder.resolve(LOG)), "the closing snapshot contains record 10");
    Path archive = folder.resolve("vsum").resolve(DeltaBasedModelPersistence.ARCHIVE_FOLDER);
    if (logRetention == DeltaBasedModelPersistence.LogRetention.ARCHIVE) {
      int archived = 0;
      for (String name : List.of("changes-3.log", "changes-6.log", "changes-9.log",
          "changes-10.log")) {
        archived += Files.readAllLines(archive.resolve(name)).size();
      }
      assertEquals(10, archived, "every record is archived once");
    } else {
      assertFalse(Files.exists(archive));
    }

    DeltaBasedModelPersistence reopenedPersistence =
        new DeltaBasedModelPersistence(
            DeltaBasedModelPersistence.Options.defaults().withLogRetention(logRetention));
    InternalVirtualModel reopened = vsum(folder, reopenedPersistence);
    assertEquals(10, reopenedPersistence.getLastRecord());
    assertEquals(7, rootOf(reopened, folder, MODEL).getSingleValuedEAttribute());
    reopened.dispose();
    assertEquals(modelFiles(stateFolder), modelFiles(folder));
    assertEquals(correspondences(stateFolder), correspondences(folder));
  }

  static void deleteSnapshot(Path folder) throws IOException {
    Files.deleteIfExists(folder.resolve(CORRESPONDENCES));
    try (Stream<Path> files = Files.walk(folder.resolve("vsum/snapshot"))) {
      for (Path file : files.sorted(java.util.Comparator.reverseOrder()).toList()) {
        Files.delete(file);
      }
    }
    for (String model : modelFiles(folder).keySet()) {
      Files.delete(folder.resolve(model));
    }
  }

  @ParameterizedTest(name = "after {0} propagations")
  @ValueSource(ints = {2, 3, 4})
  @DisplayName("reopening after N-1, N and N+1 propagations (N = 3) without close")
  void reopenAroundTheSnapshotInterval(int propagations, @TestProject Path project)
      throws Exception {
    Path folder = project.resolve("open");
    InternalVirtualModel open = vsum(folder, delta(3));
    Client client = new Client(folder);
    Root root = aet.Root();
    open.propagateChange(
        client.record(
            () -> {
              root.setId("root");
              client.createResource(MODEL).getContents().add(root);
            }));
    for (int i = 2; i <= propagations; i++) {
      int value = i;
      open.propagateChange(client.record(() -> root.setSingleValuedEAttribute(value)));
    }
    Path crashed = project.resolve("crashed");
    copyFolder(folder, crashed);
    open.dispose();

    DeltaBasedModelPersistence persistence = delta(3);
    InternalVirtualModel reopened = vsum(crashed, persistence);
    assertEquals(propagations, persistence.getLastRecord());
    assertEquals(propagations >= 3 ? 3 : 0, persistence.getSnapshotRecord());
    assertEquals(propagations, rootOf(reopened, crashed, MODEL).getSingleValuedEAttribute());
    assertEquals("root", rootOf(reopened, crashed, MODEL_COPY).getId(), "consequential model");
    propagateNewRoot(reopened, crashed);
    assertEquals(propagations + 1, persistence.getLastRecord(), "numbering goes on");
    reopened.dispose();
  }

  /** Propagates a root in a new model, from a client that knows none of the existing elements. */
  static void propagateNewRoot(InternalVirtualModel vsum, Path folder) {
    Client client = new Client(folder);
    vsum.propagateChange(
        client.record(
            () -> {
              Root root = aet.Root();
              root.setId("other");
              client.createResource("other.allElementTypes").getContents().add(root);
            }));
  }

  @Test
  @DisplayName("a torn last record is skipped, kept aside and cut, and the log goes on")
  void tornLastRecordIsSkipped(@TestProject Path project) throws Exception {
    Path folder = project.resolve("open");
    InternalVirtualModel open = vsum(folder, delta(1000));
    Client client = new Client(folder);
    Root root = aet.Root();
    open.propagateChange(
        client.record(
            () -> {
              root.setId("root");
              client.createResource(MODEL).getContents().add(root);
            }));
    open.propagateChange(client.record(() -> root.setSingleValuedEAttribute(1)));
    Path crashed = project.resolve("crashed");
    copyFolder(folder, crashed);
    open.dispose();
    Path log = crashed.resolve(LOG);
    String complete = Files.readString(log, UTF_8);
    Files.writeString(log, complete + "{\"seq\":3,\"tx\":\"abc\",\"orig", UTF_8);

    DeltaBasedModelPersistence persistence = delta(1000);
    InternalVirtualModel reopened = vsum(crashed, persistence);
    assertEquals(2, persistence.getLastRecord());
    assertEquals(1, rootOf(reopened, crashed, MODEL).getSingleValuedEAttribute());
    assertEquals(complete, Files.readString(log, UTF_8), "torn bytes cut");
    try (Stream<Path> files = Files.list(crashed.resolve("vsum"))) {
      assertEquals(
          List.of("{\"seq\":3,\"tx\":\"abc\",\"orig"),
          files
              .filter(it -> it.getFileName().toString().startsWith("changes.log.torn-"))
              .map(DeltaBasedModelPersistenceTest::readUnchecked)
              .toList());
    }
    propagateNewRoot(reopened, crashed);
    assertEquals(3, persistence.getLastRecord());
    Path again = project.resolve("again");
    copyFolder(crashed, again);
    reopened.dispose();
    DeltaBasedModelPersistence againPersistence = delta(1000);
    vsum(again, againPersistence).dispose();
    assertEquals(3, againPersistence.getLastRecord(), "the record after the cut reads back");
  }

  static String readUnchecked(Path file) {
    try {
      return Files.readString(file, UTF_8);
    } catch (IOException e) {
      throw new java.io.UncheckedIOException(e);
    }
  }

  @Test
  @DisplayName("a corrupt line in the middle fails the load and names its line")
  void corruptMiddleLineFails(@TestProject Path project) throws Exception {
    Path folder = project.resolve("open");
    InternalVirtualModel open = vsum(folder, delta(1000));
    Client client = new Client(folder);
    Root root = aet.Root();
    open.propagateChange(
        client.record(
            () -> {
              root.setId("root");
              client.createResource(MODEL).getContents().add(root);
            }));
    for (int i = 1; i <= 2; i++) {
      int value = i;
      open.propagateChange(client.record(() -> root.setSingleValuedEAttribute(value)));
    }
    Path crashed = project.resolve("crashed");
    copyFolder(folder, crashed);
    open.dispose();
    Path log = crashed.resolve(LOG);
    List<String> lines = new ArrayList<>(Files.readAllLines(log, UTF_8));
    lines.set(1, lines.get(1).substring(0, lines.get(1).length() / 2));
    Files.write(log, lines, UTF_8);

    IllegalStateException failure =
        assertThrows(IllegalStateException.class, () -> vsum(crashed, delta(1000)));
    assertTrue(failure.getMessage().contains("line 2"), failure.getMessage());
  }

  @Test
  @DisplayName("compact writes a snapshot that leaves nothing to replay")
  void compactWritesSnapshot(@TestProject Path project) throws Exception {
    Path folder = project.resolve("open");
    DeltaBasedModelPersistence persistence = delta(1000);
    InternalVirtualModel open = vsum(folder, persistence);
    runScenario(open, folder);
    persistence.compact();
    assertEquals(10, persistence.getSnapshotRecord());
    Path crashed = project.resolve("crashed");
    copyFolder(folder, crashed);
    open.dispose();
    DeltaBasedModelPersistence reopenedPersistence = delta(1000);
    InternalVirtualModel reopened = vsum(crashed, reopenedPersistence);
    assertEquals(10, reopenedPersistence.getSnapshotRecord());
    assertEquals(10, reopenedPersistence.getLastRecord());
    assertEquals(7, rootOf(reopened, crashed, MODEL).getSingleValuedEAttribute());
    reopened.dispose();
    assertEquals(10, Files.readAllLines(crashed.resolve(LOG)).size(), "log never truncated");
  }

  @Test
  @DisplayName("state-based files without snapshot and log become the first snapshot")
  void stateBasedFilesAreAdopted(@TestProject Path project) throws Exception {
    Path folder = project.resolve("session");
    InternalVirtualModel stateVsum = vsum(folder, new StateBasedModelPersistence());
    runScenario(stateVsum, folder);
    stateVsum.dispose();
    final Map<String, String> expected = modelFiles(folder);

    DeltaBasedModelPersistence persistence = delta(1000);
    final InternalVirtualModel adopted = vsum(folder, persistence);
    assertTrue(Files.exists(folder.resolve("vsum/snapshot/snapshot.properties")));
    assertFalse(Files.exists(folder.resolve(LOG)));
    assertEquals(0, persistence.getSnapshotRecord());
    assertEquals(7, rootOf(adopted, folder, MODEL).getSingleValuedEAttribute());
    adopted.dispose();
    assertEquals(expected, modelFiles(folder));
  }
}
