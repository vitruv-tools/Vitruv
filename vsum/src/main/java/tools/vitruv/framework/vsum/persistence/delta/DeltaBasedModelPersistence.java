package tools.vitruv.framework.vsum.persistence.delta;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.loadOrCreateResource;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.composite.description.CompositeChange;
import tools.vitruv.change.composite.description.TransactionalChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeFactory;
import tools.vitruv.change.composite.description.VitruviusChangeResolver;
import tools.vitruv.change.composite.description.VitruviusChangeResolverFactory;
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout;
import tools.vitruv.framework.vsum.persistence.ModelPersistence;
import tools.vitruv.framework.vsum.persistence.ModelPersistenceContext;

/**
 * Stores the models as a log of their changes plus snapshots, chosen with {@link
 * tools.vitruv.framework.vsum.VirtualModelBuilder#withDeltaBasedPersistence}.
 *
 * <ul>
 *   <li>Each propagation appends one record to {@code vsum/changes.log} ({@link ChangeLog}): the
 *       propagated change and the consequential changes in application order, every element ID as
 *       path plus UUID ({@link ElementIds}), URIs relative to the storage folder.
 *   <li>A snapshot ({@link Snapshots}) is written every {@link Options#snapshotInterval()}
 *       propagations, on {@link #close}, and on {@link #compact()}. The log is never truncated.
 *   <li>Loading reads the latest snapshot and replays the log records after it, resolving elements
 *       by UUID.
 *   <li>The changes of the correspondence model go into the same record ({@link
 *       CorrespondenceLog}); {@code correspondences.correspondence} is written with each snapshot
 *       only. Records of logs written before (without correspondences) are replayed with the
 *       correspondence file as the state after the last record, and a snapshot is written right
 *       away.
 * </ul>
 *
 * <p>A folder with state-based files ({@code models.models}) but neither snapshot nor log is
 * adopted: its state becomes the first snapshot.
 *
 * <p>An instance keeps the state of one virtual model; use a new one per virtual model.
 */
public class DeltaBasedModelPersistence implements ModelPersistence {
  private static final Logger LOGGER = LogManager.getLogger(DeltaBasedModelPersistence.class);

  /**
   * Options of the delta-based persistence.
   *
   * @param snapshotInterval a snapshot is written after this many propagations
   * @param syncLog whether each log record is forced to the storage device before the propagation
   *     returns
   */
  public record Options(int snapshotInterval, boolean syncLog) {
    /** The default interval between snapshots. */
    public static final int DEFAULT_SNAPSHOT_INTERVAL = 50;

    /**
     * Validates the options.
     *
     * @param snapshotInterval a snapshot is written after this many propagations
     * @param syncLog whether each log record is forced to the storage device
     */
    public Options {
      checkArgument(snapshotInterval > 0, "snapshot interval must be positive");
    }

    /**
     * Returns the default options: a snapshot every 50 propagations, log records forced to disk.
     *
     * @return the default options
     */
    public static Options defaults() {
      return new Options(DEFAULT_SNAPSHOT_INTERVAL, true);
    }

    /**
     * Returns these options with another snapshot interval.
     *
     * @param interval a snapshot is written after this many propagations
     * @return the changed options
     */
    public Options withSnapshotInterval(int interval) {
      return new Options(interval, syncLog);
    }
  }

  private final Options options;

  private ModelPersistenceContext context;
  private ElementIds ids;
  private ChangeLog log;
  private CorrespondenceLog correspondences;
  private Path snapshotFolder;
  private long lastSeq;
  private long snapshotSeq;
  private PendingRecord pending;
  private boolean compactRequested;
  private long appendedBytes;

  /** Creates the persistence with {@link Options#defaults()}. */
  public DeltaBasedModelPersistence() {
    this(Options.defaults());
  }

  /**
   * Creates the persistence.
   *
   * @param options the options
   */
  public DeltaBasedModelPersistence(Options options) {
    this.options = options;
  }

  /** The changes of the running propagation, as JSON, in application order. */
  private static final class PendingRecord {
    final List<List<Map<String, Object>>> original = new ArrayList<>();
    final List<List<Map<String, Object>>> consequential = new ArrayList<>();
  }

  @Override
  public synchronized void load(ModelPersistenceContext context) {
    checkState(this.context == null, "a delta-based persistence serves one virtual model only");
    VsumFileSystemLayout layout = context.getFileSystemLayout();
    Path vsumFolder = Path.of(layout.getUuidsURI().toFileString()).getParent();
    ids = new ElementIds(layout.getVsumProjectFolder());
    log = new ChangeLog(vsumFolder.resolve(ChangeLog.FILE_NAME), options.syncLog());
    snapshotFolder = vsumFolder.resolve(Snapshots.FOLDER);
    correspondences = new CorrespondenceLog(context, ids);
    try {
      Snapshots.recover(snapshotFolder);
      snapshotSeq = Snapshots.read(context, ids, snapshotFolder);
      List<ChangeLog.Entry> entries = log.read();
      boolean adopt = false;
      boolean legacy = false;
      if (snapshotSeq < 0) {
        snapshotSeq = 0;
        if (Files.exists(layout.getModelsNamesFilesPath())) {
          checkState(
              entries.isEmpty(),
              "%s holds state-based files and a change log, but no snapshot; open it with the"
                  + " persistence that wrote it",
              vsumFolder);
          loadStateBasedModels(context, layout);
          adopt = true;
        }
      }
      List<ChangeLog.Entry> tail = entries.stream().filter(it -> it.seq() > snapshotSeq).toList();
      if (!tail.isEmpty() && tail.stream().noneMatch(ChangeLog.Entry::hasCorrespondences)) {
        legacy = true; // the correspondence file holds the state after the last record
      } else {
        tail.stream()
            .filter(it -> !it.hasCorrespondences())
            .findFirst()
            .ifPresent(
                it -> {
                  throw new IllegalStateException(
                      log.file() + " line " + it.line() + ": record " + it.seq()
                          + " has no correspondences, but other records after the snapshot do");
                });
        // the correspondence file holds the snapshot's state; hrefs are positional in it
        context.loadCorrespondences();
      }
      Set<URI> registered = new HashSet<>();
      context.getModelsResourceSet().getResources().forEach(it -> registered.add(it.getURI()));
      lastSeq = replay(context, entries);
      for (Resource resource : List.copyOf(context.getModelsResourceSet().getResources())) {
        URI uri = resource.getURI();
        if ((uri.isFile() || uri.isPlatform()) && !registered.contains(uri)) {
          context.registerModel(uri);
        }
      }
      if (legacy) {
        context.loadCorrespondences();
      }
      correspondences.reset();
      this.context = context;
      if (adopt) {
        LOGGER.info("Adopting the state-based files in {} as first snapshot", vsumFolder);
        writeSnapshot();
      } else if (legacy) {
        LOGGER.info("{} has records without correspondences; writing a snapshot", log.file());
        writeSnapshot();
      }
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private static void loadStateBasedModels(
      ModelPersistenceContext context, VsumFileSystemLayout layout) throws IOException {
    List<URI> modelUris =
        Files.readAllLines(layout.getModelsNamesFilesPath()).stream()
            .map(URI::createURI)
            .toList();
    modelUris.forEach(uri -> loadOrCreateResource(context.getModelsResourceSet(), uri));
    context.getUuidResolver().loadFromUri(layout.getUuidsURI());
    modelUris.forEach(context::registerModel);
  }

  /** Replays the records after the snapshot; returns the last record's number. */
  private long replay(ModelPersistenceContext context, List<ChangeLog.Entry> entries) {
    VitruviusChangeResolver<Uuid> resolver =
        VitruviusChangeResolverFactory.forUuids(context.getUuidResolver());
    long last = snapshotSeq;
    long previous = 0;
    int replayed = 0;
    for (ChangeLog.Entry entry : entries) {
      if (entry.seq() <= previous) {
        throw new IllegalStateException(
            log.file() + " line " + entry.line() + ": record " + entry.seq() + " out of order");
      }
      previous = entry.seq();
      if (entry.seq() <= snapshotSeq) {
        continue; // contained in the snapshot
      }
      if (entry.seq() != last + 1) {
        throw new IllegalStateException(
            log.file()
                + " line "
                + entry.line()
                + ": record "
                + entry.seq()
                + " follows "
                + last
                + "; records are missing");
      }
      try {
        for (List<Map<String, Object>> group : entry.originalGroups()) {
          apply(context, resolver, group);
        }
        for (List<Map<String, Object>> group : entry.consequentialGroups()) {
          apply(context, resolver, group);
        }
        if (entry.hasCorrespondences()) {
          correspondences.apply(entry.correspondenceOperations());
        }
      } catch (RuntimeException e) {
        throw new IllegalStateException(
            log.file() + " line " + entry.line() + ": cannot replay record " + entry.seq() + ": "
                + e.getMessage(),
            e);
      }
      last = entry.seq();
      replayed++;
    }
    if (previous < snapshotSeq) {
      LOGGER.warn(
          "{} ends at record {}, the snapshot holds {}; continuing from the snapshot",
          log.file(),
          previous,
          snapshotSeq);
    }
    LOGGER.debug("Replayed {} records after snapshot {}", replayed, snapshotSeq);
    return last;
  }

  private void apply(
      ModelPersistenceContext context,
      VitruviusChangeResolver<Uuid> resolver,
      List<Map<String, Object>> group) {
    List<EChange<Uuid>> changes = new ArrayList<>(group.size());
    for (Map<String, Object> json : group) {
      changes.add(ids.toUuids(EChangeJson.fromJson(json), path -> uuidAt(context, path)));
    }
    resolver.resolveAndApply(VitruviusChangeFactory.getInstance().createTransactionalChange(
        changes));
  }

  /** The UUID of the element at a path, for an ID without UUID (e.g. a metamodel type). */
  private static Uuid uuidAt(ModelPersistenceContext context, String path) {
    checkState(
        !path.startsWith(HierarchicalId.CACHE_PREFIX),
        "detached element %s has no UUID",
        path);
    EObject element = context.getModelsResourceSet().getEObject(URI.createURI(path), true);
    checkState(element != null, "no element at %s", path);
    return context.getUuidResolver().getUuid(element);
  }

  @Override
  public synchronized void changeApplied(
      ModelPersistenceContext context,
      VitruviusChange<Uuid> change,
      VitruviusChange<EObject> appliedChange) {
    pending = new PendingRecord();
    pending.original.addAll(toJson(context, change, appliedChange));
  }

  @Override
  public synchronized void changeRecorded(
      ModelPersistenceContext context,
      TransactionalChange<EObject> recordedChange,
      VitruviusChange<Uuid> changeWithIds) {
    if (pending == null) {
      pending = new PendingRecord();
    }
    pending.consequential.addAll(toJson(context, changeWithIds, recordedChange));
  }

  /**
   * Converts a change to its transactional changes as JSON with dual IDs. The paths are assigned
   * by applying the change backward and forward on the models, which are in the state right after
   * the change.
   */
  private List<List<Map<String, Object>>> toJson(
      ModelPersistenceContext context,
      VitruviusChange<Uuid> withUuids,
      VitruviusChange<EObject> withElements) {
    VitruviusChange<HierarchicalId> withPaths =
        VitruviusChangeResolverFactory.forHierarchicalIds(context.getModelsResourceSet())
            .assignIds(withElements);
    List<List<EChange<Uuid>>> uuidGroups = transactionalChanges(withUuids);
    List<List<EChange<HierarchicalId>>> pathGroups = transactionalChanges(withPaths);
    checkState(
        uuidGroups.size() == pathGroups.size(),
        "change with UUIDs and with paths differ in structure");
    List<List<Map<String, Object>>> groups = new ArrayList<>(uuidGroups.size());
    for (int g = 0; g < uuidGroups.size(); g++) {
      List<EChange<Uuid>> uuidChanges = uuidGroups.get(g);
      List<EChange<HierarchicalId>> pathChanges = pathGroups.get(g);
      checkState(
          uuidChanges.size() == pathChanges.size(),
          "change with UUIDs and with paths differ in length");
      List<Map<String, Object>> group = new ArrayList<>(uuidChanges.size());
      for (int i = 0; i < uuidChanges.size(); i++) {
        group.add(EChangeJson.toJson(ids.zip(pathChanges.get(i), uuidChanges.get(i))));
      }
      groups.add(group);
    }
    return groups;
  }

  /** The non-empty transactional changes of a change, as lists of changes, in order. */
  private static <T> List<List<EChange<T>>> transactionalChanges(VitruviusChange<T> change) {
    List<List<EChange<T>>> result = new ArrayList<>();
    collect(change, result);
    return result;
  }

  private static <T> void collect(VitruviusChange<T> change, List<List<EChange<T>>> result) {
    if (change instanceof TransactionalChange<T> transactional) {
      if (!transactional.getEChanges().isEmpty()) {
        result.add(List.copyOf(transactional.getEChanges()));
      }
    } else if (change instanceof CompositeChange<T, ?> composite) {
      for (VitruviusChange<T> inner : composite.getChanges()) {
        collect(inner, result);
      }
    } else {
      throw new IllegalStateException(
          "unknown change type " + change.getClass().getSimpleName());
    }
  }

  @Override
  public synchronized void save(ModelPersistenceContext context) {
    try {
      if (pending != null) {
        long seq = lastSeq + 1;
        appendedBytes +=
            log.append(
                seq,
                UUID.randomUUID().toString(),
                Instant.now().toString(),
                pending.original,
                pending.consequential,
                correspondences.diff());
        lastSeq = seq;
        pending = null;
      }
      if (compactRequested || lastSeq - snapshotSeq >= options.snapshotInterval()) {
        writeSnapshot();
      }
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  @Override
  public synchronized void close(ModelPersistenceContext context) {
    try {
      if (this.context != null && lastSeq > snapshotSeq) {
        writeSnapshot();
      }
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    } finally {
      this.context = null;
    }
  }

  /**
   * Writes a snapshot of the current state now, so that the next load replays nothing. If a
   * propagation is running, the snapshot is written when it is saved.
   *
   * @throws IllegalStateException if the virtual model is not loaded or already closed
   */
  public synchronized void compact() {
    checkState(context != null, "the virtual model is not loaded or already closed");
    if (pending != null) {
      compactRequested = true;
      return;
    }
    try {
      writeSnapshot();
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  /**
   * Returns the number of the last record in the log, 0 if there is none.
   *
   * @return the last propagation number
   */
  public synchronized long getLastRecord() {
    return lastSeq;
  }

  /**
   * Returns the number of the last record the latest snapshot contains.
   *
   * @return the propagation number of the latest snapshot
   */
  public synchronized long getSnapshotRecord() {
    return snapshotSeq;
  }

  /**
   * Returns the bytes this instance appended to the log, for measurements.
   *
   * @return the appended bytes
   */
  public synchronized long getAppendedBytes() {
    return appendedBytes;
  }

  private void writeSnapshot() throws IOException {
    // Outside a propagation the correspondences may have changed without a record; the snapshot
    // stores them (pruned) all the same, as state-based persistence does on its next save.
    correspondences.diff();
    Snapshots.write(context, ids, snapshotFolder, lastSeq, correspondences.resource());
    snapshotSeq = lastSeq;
    compactRequested = false;
    LOGGER.debug("Wrote snapshot at record {}", lastSeq);
  }
}
