package tools.vitruv.framework.vsum.persistence.delta;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.loadOrCreateResource;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.correspondence.model.PersistableCorrespondenceModel;
import tools.vitruv.framework.vsum.persistence.ModelPersistenceContext;

/**
 * Snapshots of the delta-based persistence, in {@code vsum/snapshot/}: a copy of each model as XMI
 * in {@code models/}, {@code models.models} (the URI of each model, relative to the storage folder,
 * and its copy), {@code uuid.uuid} with relative URIs, {@code snapshot.properties} with {@code
 * seq}, the last log record the snapshot contains, and {@code correspondences.correspondence} at its
 * own URI.
 *
 * <p>Loading reads the copies, not the model files at the models' own URIs: those are exports,
 * written with each snapshot and, with {@link
 * DeltaBasedModelPersistence.ModelFiles#EVERY_PROPAGATION}, after every propagation, so they may be
 * newer than the snapshot.
 *
 * <p>A snapshot is written atomically: every file is first written next to its target with the
 * suffix {@value #TEMP_SUFFIX}; then a journal listing the moves is moved into place (the commit
 * point); then the files are moved over their targets, {@code snapshot.properties} last, and the
 * journal is deleted. A journal found when loading is completed first, so a crash leaves either
 * the old or the new snapshot.
 */
final class Snapshots {
  static final String FOLDER = "snapshot";
  static final String PROPERTIES = "snapshot.properties";
  static final String MODELS = "models.models";
  static final String UUIDS = "uuid.uuid";
  static final String JOURNAL = "journal";
  static final String TEMP_SUFFIX = ".snapshot-tmp";
  private static final String SEQ = "seq";
  private static final char UUID_SEPARATOR = '|';
  /** The folder in the snapshot folder that holds the snapshot's copies of the models. */
  static final String MODEL_COPIES = "models";
  /** Separates a model's URI from the path of its copy in {@value #MODELS}. */
  static final char COPY_SEPARATOR = '\t';

  private Snapshots() {}

  /**
   * Completes a snapshot whose journal was committed but not yet applied.
   *
   * @param folder the snapshot folder
   * @throws IOException if a move fails
   */
  static void recover(Path folder) throws IOException {
    if (Files.exists(folder.resolve(JOURNAL))) {
      applyJournal(folder);
    }
  }

  /**
   * Loads the latest snapshot into the context: the models, their UUIDs, and registers the models.
   *
   * @param context the models of the virtual model
   * @param ids converts relative URIs
   * @param folder the snapshot folder
   * @return the last log record the snapshot contains, or -1 if there is no snapshot
   * @throws IOException if a snapshot file cannot be read
   */
  static long read(ModelPersistenceContext context, ElementIds ids, Path folder)
      throws IOException {
    Path propertiesFile = folder.resolve(PROPERTIES);
    if (!Files.exists(propertiesFile)) {
      return -1;
    }
    Properties properties = new Properties();
    properties.load(new StringReader(Files.readString(propertiesFile, UTF_8)));
    final long seq = Long.parseLong(properties.getProperty(SEQ));
    List<URI> modelUris = new ArrayList<>();
    for (String line : Files.readAllLines(folder.resolve(MODELS), UTF_8)) {
      if (line.isBlank()) {
        continue;
      }
      int cut = line.indexOf(COPY_SEPARATOR);
      URI uri = URI.createURI(ids.absolutize(cut < 0 ? line : line.substring(0, cut)));
      modelUris.add(uri);
      if (cut < 0) {
        loadOrCreateResource(context.getModelsResourceSet(), uri);
      } else {
        // the snapshot's own copy: the file at the model's URI may be newer than the snapshot
        Resource resource = context.getModelsResourceSet().createResource(uri);
        try (InputStream input = Files.newInputStream(folder.resolve(line.substring(cut + 1)))) {
          resource.load(input, null);
        }
      }
    }
    Map<Uuid, HierarchicalId> uuidToId = new HashMap<>();
    for (String line : Files.readAllLines(folder.resolve(UUIDS), UTF_8)) {
      int cut = line.indexOf(UUID_SEPARATOR);
      if (cut > 0) {
        uuidToId.put(
            Uuid.of(line.substring(0, cut)),
            new HierarchicalId(ids.absolutize(line.substring(cut + 1))));
      }
    }
    context.getUuidResolver().loadFromMapping(uuidToId);
    modelUris.forEach(context::registerModel);
    return seq;
  }

  /**
   * Writes a snapshot of the current state, then deletes the empty models as state-based
   * persistence does.
   *
   * @param context the models of the virtual model
   * @param ids converts absolute URIs
   * @param folder the snapshot folder
   * @param seq the last log record the state contains
   * @throws IOException if a file cannot be written
   */
  static void write(
      ModelPersistenceContext context,
      ElementIds ids,
      Path folder,
      long seq)
      throws IOException {
    Files.createDirectories(folder.resolve(MODEL_COPIES));
    List<Path[]> moves = new ArrayList<>();
    List<Resource> saved = new ArrayList<>();
    List<String> modelLines = new ArrayList<>();
    Set<String> copies = new HashSet<>();
    for (Resource resource : List.copyOf(context.getModelsResourceSet().getResources())) {
      URI uri = resource.getURI();
      boolean modifiable = uri.isFile() || uri.isPlatform();
      if (!modifiable) {
        modelLines.add(ids.relativize(uri.toString())); // read-only: loaded from its URI
        continue;
      }
      if (resource.getContents().isEmpty()) {
        continue; // deleted below
      }
      ByteArrayOutputStream content = new ByteArrayOutputStream();
      resource.save(content, null);
      byte[] bytes = content.toByteArray();
      String copy = MODEL_COPIES + "/m" + copies.size() + ".xmi";
      copies.add(copy);
      modelLines.add(ids.relativize(uri.toString()) + COPY_SEPARATOR + copy);
      Path copyTarget = folder.resolve(copy);
      moves.add(new Path[] {writeTemp(copyTarget, bytes), copyTarget});
      // the model file at its own URI is an export of the snapshot's state
      if (uri.isFile()) {
        Path target = Path.of(uri.toFileString());
        if (target.getParent() != null) {
          Files.createDirectories(target.getParent());
        }
        moves.add(new Path[] {writeTemp(target, bytes), target});
        saved.add(resource);
      } else if (resource.isModified()) {
        resource.save(null); // not atomic: no file system path outside Eclipse
        saved.add(resource);
      }
    }
    PersistableCorrespondenceModel correspondenceModel = context.getCorrespondenceModel();
    URI correspondencesUri = context.getFileSystemLayout().getCorrespondencesURI();
    if (correspondencesUri != null && correspondencesUri.isFile()) {
      ByteArrayOutputStream content = new ByteArrayOutputStream();
      correspondenceModel.writeTo(content);
      Path target = Path.of(correspondencesUri.toFileString());
      Files.createDirectories(target.getParent());
      moves.add(new Path[] {writeTemp(target, content.toByteArray()), target});
    } else if (correspondencesUri != null) {
      correspondenceModel.save(); // not atomic: no file system path outside Eclipse
    }
    List<String> uuidLines = new ArrayList<>();
    for (Map.Entry<Uuid, HierarchicalId> entry :
        context.getUuidResolver().getUuidToIdMapping().entrySet()) {
      uuidLines.add(
          entry.getKey().getRawValue()
              + UUID_SEPARATOR
              + ids.relativize(entry.getValue().getId()));
    }
    uuidLines.sort(null);
    moves.add(lines(folder.resolve(UUIDS), uuidLines));
    moves.add(lines(folder.resolve(MODELS), modelLines));
    moves.add(lines(folder.resolve(PROPERTIES), List.of(SEQ + "=" + seq, "time=" + Instant.now())));

    List<String> journal = new ArrayList<>();
    for (Path[] move : moves) {
      journal.add(move[0].toAbsolutePath() + "\t" + move[1].toAbsolutePath());
    }
    Path journalFile = folder.resolve(JOURNAL);
    move(writeTemp(journalFile, (String.join("\n", journal) + "\n").getBytes(UTF_8)), journalFile);
    syncDirectory(folder); // commit point
    applyJournal(folder);
    Set<Path> directories = new HashSet<>();
    for (Path[] move : moves) {
      directories.add(move[1].toAbsolutePath().getParent());
    }
    directories.forEach(Snapshots::syncDirectory);
    deleteStaleCopies(folder.resolve(MODEL_COPIES), copies, folder);

    saved.forEach(resource -> resource.setModified(false));
    context.saveOrDeleteModels();
  }

  /** Deletes the model copies of earlier snapshots that the new one does not use. */
  private static void deleteStaleCopies(Path copiesFolder, Set<String> copies, Path folder)
      throws IOException {
    try (Stream<Path> files = Files.list(copiesFolder)) {
      for (Path file : files.toList()) {
        String name = folder.relativize(file).toString().replace('\\', '/');
        if (!copies.contains(name) && !name.endsWith(TEMP_SUFFIX)) {
          Files.deleteIfExists(file);
        }
      }
    }
  }

  private static Path[] lines(Path target, List<String> lines) throws IOException {
    StringBuilder content = new StringBuilder();
    lines.forEach(line -> content.append(line).append('\n'));
    return new Path[] {writeTemp(target, content.toString().getBytes(UTF_8)), target};
  }

  /** Writes the content next to the target and forces it to the storage device. */
  private static Path writeTemp(Path target, byte[] content) throws IOException {
    Path temp = target.resolveSibling(target.getFileName() + TEMP_SUFFIX);
    try (FileChannel channel =
        FileChannel.open(
            temp,
            StandardOpenOption.CREATE,
            StandardOpenOption.TRUNCATE_EXISTING,
            StandardOpenOption.WRITE)) {
      ByteBuffer buffer = ByteBuffer.wrap(content);
      while (buffer.hasRemaining()) {
        channel.write(buffer);
      }
      channel.force(true);
    }
    return temp;
  }

  /**
   * Forces the entries of a directory (moved or created files) to the storage device. Not every
   * platform can open a directory for this (Windows cannot); there the moves are as durable as the
   * file system makes them.
   */
  static void syncDirectory(Path directory) {
    try (FileChannel channel = FileChannel.open(directory, StandardOpenOption.READ)) {
      channel.force(true);
    } catch (IOException | UnsupportedOperationException e) {
      // not supported on this platform
    }
  }

  private static void applyJournal(Path folder) throws IOException {
    Path journalFile = folder.resolve(JOURNAL);
    for (String line : Files.readAllLines(journalFile, UTF_8)) {
      int tab = line.indexOf('\t');
      if (tab < 0) {
        continue;
      }
      Path temp = Path.of(line.substring(0, tab));
      if (Files.exists(temp)) { // already moved if the journal was interrupted before
        move(temp, Path.of(line.substring(tab + 1)));
      }
    }
    Files.delete(journalFile);
  }

  private static void move(Path source, Path target) throws IOException {
    try {
      Files.move(
          source, target, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
    } catch (AtomicMoveNotSupportedException e) {
      Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
    }
  }
}
