package tools.vitruv.framework.vsum.persistence;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.aet;
import static tools.vitruv.framework.vsum.VirtualModelTestUtil.createTestModelResourceUri;
import static tools.vitruv.framework.vsum.VirtualModelTestUtil.recordChanges;

import allElementTypes.Root;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestProjectManager;
import tools.vitruv.framework.vsum.VirtualModelTestUtil;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.framework.vsum.persistence.DefaultModelPersistence;

/**
 * Pins the files the default (state-based) persistence writes for a small propagation: the
 * models, {@code models.models}, {@code uuid.uuid} and the correspondences.
 *
 * <p>The expected files were recorded with the persistence code before it was extracted into a
 * strategy (system property {@code vitruv.persistence.recordGolden} set to a folder records them
 * again). UUIDs are random and the project folder differs per run, so the comparison replaces both
 * with placeholders (a UUID by the path of its element), unifies line endings and sorts the lines
 * of {@code uuid.uuid}, whose order comes from a hash map. Everything else must match byte for
 * byte. {@code constraints.properties} is left out: the constraint store writes it, with a date.
 */
@ExtendWith(TestProjectManager.class)
class StateBasedModelPersistenceTest {
  private static final String GOLDEN_RESOURCE_FOLDER = "persistence/state-based/";
  private static final String UUIDS_FILE = "vsum/uuid.uuid";
  private static final Set<String> IGNORED_FILES = Set.of("vsum/constraints.properties");

  @Test
  @DisplayName("the state-based persistence writes the same files as before the extraction")
  void stateBasedWritesSameFilesAsBefore(@TestProject Path projectFolder) throws Exception {
    Path vsumFolder = projectFolder.resolve("vsum");
    String previousMode = System.setProperty(DefaultModelPersistence.PROPERTY, "state");
    InternalVirtualModel virtualModel;
    try {
      virtualModel =
          (InternalVirtualModel)
              VirtualModelTestUtil.createAndLoadTestVirtualModelWithConsistencyPreservation(
                  vsumFolder);
    } finally {
      if (previousMode == null) {
        System.clearProperty(DefaultModelPersistence.PROPERTY);
      } else {
        System.setProperty(DefaultModelPersistence.PROPERTY, previousMode);
      }
    }
    ResourceSet resourceSet = withGlobalFactories(new ResourceSetImpl());
    UuidResolver uuidResolver = UuidResolver.create(resourceSet);
    URI modelUri = createTestModelResourceUri(vsumFolder, "");
    Root[] root = new Root[1];
    virtualModel.propagateChange(
        recordChanges(
            resourceSet,
            uuidResolver,
            () -> {
              root[0] = aet.Root();
              root[0].setId("root");
              var nonRoot = aet.NonRoot();
              nonRoot.setId("nonRoot");
              root[0].setSingleValuedContainmentEReference(nonRoot);
              resourceSet.createResource(modelUri).getContents().add(root[0]);
            }));
    virtualModel.propagateChange(
        recordChanges(resourceSet, uuidResolver, () -> root[0].setSingleValuedEAttribute(42)));
    virtualModel.dispose();

    Map<String, String> actual = normalizedFiles(vsumFolder);
    String recordFolder = System.getProperty("vitruv.persistence.recordGolden");
    if (recordFolder != null) {
      record(Path.of(recordFolder), actual);
    }
    assertEquals(goldenFiles(), actual);
  }

  private static Map<String, String> normalizedFiles(Path vsumFolder) throws IOException {
    Map<String, String> rawFiles = new TreeMap<>();
    try (Stream<Path> files = Files.walk(vsumFolder)) {
      for (Path file : files.filter(Files::isRegularFile).toList()) {
        String relative = vsumFolder.relativize(file).toString().replace('\\', '/');
        if (IGNORED_FILES.contains(relative)) {
          continue;
        }
        rawFiles.put(relative, Files.readString(file, StandardCharsets.UTF_8));
      }
    }
    String folderUri = URI.createFileURI(vsumFolder.toString()).toString();
    Map<String, String> uuidPlaceholders = uuidPlaceholders(rawFiles.get(UUIDS_FILE), folderUri);
    Map<String, String> normalized = new TreeMap<>();
    rawFiles.forEach(
        (name, content) -> {
          String result = content.replace("\r\n", "\n").replace(folderUri, "${FOLDER}");
          for (var placeholder : uuidPlaceholders.entrySet()) {
            result = result.replace(placeholder.getKey(), placeholder.getValue());
          }
          if (UUIDS_FILE.equals(name)) {
            result = String.join("\n", result.lines().sorted().toList()) + "\n";
          }
          normalized.put(name, result);
        });
    return normalized;
  }

  /** Maps each stored UUID to a placeholder naming the element it identifies. */
  private static Map<String, String> uuidPlaceholders(String uuidsFile, String folderUri) {
    Map<String, String> placeholders = new HashMap<>();
    for (String line : uuidsFile.lines().toList()) {
      String uuid = line.substring(0, line.indexOf('|'));
      String id = line.substring(line.indexOf('|') + 1).replace(folderUri, "${FOLDER}");
      placeholders.put(uuid, "${UUID:" + id + "}");
    }
    return placeholders;
  }

  private static Map<String, String> goldenFiles() throws IOException {
    Map<String, String> golden = new TreeMap<>();
    ClassLoader loader = StateBasedModelPersistenceTest.class.getClassLoader();
    String index = readResource(loader, GOLDEN_RESOURCE_FOLDER + "files.txt");
    for (String name : index.lines().toList()) {
      golden.put(name, readResource(loader, GOLDEN_RESOURCE_FOLDER + name));
    }
    return golden;
  }

  private static String readResource(ClassLoader loader, String name) throws IOException {
    try (InputStream stream = loader.getResourceAsStream(name)) {
      if (stream == null) {
        throw new IOException("missing test resource " + name);
      }
      return new String(stream.readAllBytes(), StandardCharsets.UTF_8).replace("\r\n", "\n");
    }
  }

  private static void record(Path folder, Map<String, String> files) throws IOException {
    for (var file : files.entrySet()) {
      Path target = folder.resolve(file.getKey());
      Files.createDirectories(target.getParent());
      Files.writeString(target, file.getValue(), StandardCharsets.UTF_8);
    }
    Files.writeString(
        folder.resolve("files.txt"),
        String.join("\n", files.keySet()) + "\n",
        StandardCharsets.UTF_8);
  }
}
