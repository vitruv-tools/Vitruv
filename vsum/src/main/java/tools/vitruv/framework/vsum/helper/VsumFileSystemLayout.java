package tools.vitruv.framework.vsum.helper;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.createFileURI;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.createDirectories;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Path;
import org.eclipse.emf.common.util.URI;

/** Helper class to manage the file system layout of a Vitruv project with VSUM support. */
public class VsumFileSystemLayout {
  private static final String CORRESPONDENCES_FILE = "correspondences.correspondence";
  private static final String UUIDS_FILE = "uuid.uuid";
  private static final String MODELS_FILE = "models.models";
  private static final String VSUM_FOLDER_NAME = "vsum";
  private static final String CONSISTENCY_METADATA_FOLDER_NAME = "consistencymetadata";

  private final Path vsumProjectFolder;
  private boolean prepared = false;

  /**
   * Creates a new file system layout for the given Vitruv project folder.
   *
   * @param vsumProjectFolder the Vitruv project folder
   */
  public VsumFileSystemLayout(Path vsumProjectFolder) {
    this.vsumProjectFolder = vsumProjectFolder;
  }

  /**
   * Prepares the file system layout by creating the necessary folders.
   *
   * @throws IOException if folder creation fails
   */
  public void prepare() throws IOException {
    createDirectories(getVsumFolder());
    createDirectories(getConsistencyMetadataFolder());
    prepared = true;
  }

  private Path getMetadataFilePath(String... metadataKey) {
    checkArgument(
        metadataKey != null && metadataKey.length > 0, "The key must have at least one part!");
    checkArgument(
        metadataKey[metadataKey.length - 1].contains("."),
        "metadataKey is missing a file extension!");

    Path result = Path.of("");
    for (String keyPart : metadataKey) {
      checkArgument(keyPart != null, "A key part must not be null!");
      // URL-encoding the string makes it safe for being a file part,
      // except for the cases '', '.' and '..'
      // we thus use _ as an escape character
      // This also ensures that the resulting path is always located
      // within the metadata folder.
      String preparedKeyPart;
      assert keyPart != null;
      preparedKeyPart =
          switch (keyPart) {
            case "." -> "_.";
            case ".." -> "_._.";
            case "" -> "_";
            default -> keyPart.replaceAll("_", "__");
          };
      result = result.resolve(URLEncoder.encode(preparedKeyPart, UTF_8));
    }
    return result;
  }

  /**
   * Gets the {@link URI} of a model that stores metadata.
   *
   * @param metadataKey The key uniquely identifying the metadata model. The different parts of the
   *     key can be used to convey some sort of hierarchy in the metadata. The key may contain
   *     arbitrary characters. The last key part contains the metadata model's file name and
   *     extension.
   * @return the URI of the specified metadata model
   */
  public URI getConsistencyMetadataModelURI(String... metadataKey) {
    checkPrepared();
    Path metadataPath = getConsistencyMetadataFolder().resolve(getMetadataFilePath(metadataKey));
    return createFileURI(metadataPath.toFile());
  }

  /**
   * Gets the URI of the correspondences file.
   *
   * @return the URI of the correspondences file
   */
  public URI getCorrespondencesURI() {
    checkPrepared();
    return createFileURI(getVsumFolder().resolve(CORRESPONDENCES_FILE).toFile());
  }

  /**
   * Gets the URI of the UUIDs file.
   *
   * @return the URI of the UUIDs file
   */
  public URI getUuidsURI() {
    checkPrepared();
    return createFileURI(getVsumFolder().resolve(UUIDS_FILE).toFile());
  }

  /**
   * Gets the path of the models names file.
   *
   * @return the path of the models names file
   */
  public Path getModelsNamesFilesPath() {
    checkPrepared();
    return getVsumFolder().resolve(MODELS_FILE);
  }

  /**
   * Gets the Vitruv project folder.
   *
   * @return the Vitruv project folder
   */
  public Path getVsumProjectFolder() {
    return this.vsumProjectFolder;
  }

  private Path getVsumFolder() {
    return vsumProjectFolder.resolve(VSUM_FOLDER_NAME);
  }

  private Path getConsistencyMetadataFolder() {
    return vsumProjectFolder.resolve(CONSISTENCY_METADATA_FOLDER_NAME);
  }

  @Override
  public String toString() {
    return "@" + vsumProjectFolder;
  }

  private void checkPrepared() {
    checkState(prepared, "The file system layout has not been loaded yet!");
  }
}
