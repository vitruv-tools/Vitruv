package tools.vitruv.framework.vsum.persistence.delta;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.atomic.resolve.AtomicEChangeResolverHelper;
import tools.vitruv.change.atomic.root.RootEChange;
import tools.vitruv.change.atomic.uuid.Uuid;

/**
 * Dual element IDs in the Deltachain encoding: each ID slot of a logged change is a {@link
 * HierarchicalId} whose string is {@code <path>|u=<uuid>}. The path is the positional ID Vitruvius
 * assigns ({@code root.allElementTypes#//@nonRoot.0}, {@code cache:/0} for a detached element) with
 * file URIs relative to the storage folder; the UUID is the element's Vitruvius UUID. A consumer
 * that only needs the path cuts at the first {@code |}. The Deltachain's optional fields ({@code
 * |a=} anchor, {@code |c=} class hints, {@code |w=} predecessor) are not written and are ignored
 * when read.
 *
 * <p>An element whose UUID equals its absolute path (read-only elements, such as metamodel types,
 * whose Vitruvius UUID is their URI) is written with its path only.
 */
final class ElementIds {
  static final String UUID_FIELD = "u=";
  private static final String SEPARATOR = "|";

  private final URI base;

  /**
   * Creates the IDs for a storage folder.
   *
   * @param storageFolder the folder that relative URIs are relative to
   */
  ElementIds(Path storageFolder) {
    this.base = URI.createFileURI(storageFolder.toAbsolutePath().normalize() + "/");
  }

  /** The content of one ID: its path (never null) and its UUID (null if it has none). */
  record Ref(String path, String uuid) {}

  /**
   * Encodes an ID.
   *
   * @param path the path, already relative
   * @param uuid the raw UUID, or null
   * @return the ID string
   */
  static String encode(String path, String uuid) {
    return uuid == null ? path : path + SEPARATOR + UUID_FIELD + uuid;
  }

  /**
   * Decodes an ID in either form; a string without {@code |u=} is a positional ID.
   *
   * @param id the ID string
   * @return its path and UUID
   */
  static Ref decode(String id) {
    int cut = id.indexOf(SEPARATOR);
    if (cut < 0) {
      return new Ref(id, null);
    }
    String uuid = null;
    for (String field : id.substring(cut + 1).split("\\|")) {
      if (field.startsWith(UUID_FIELD)) {
        uuid = field.substring(UUID_FIELD.length());
      }
    }
    return new Ref(id.substring(0, cut), uuid);
  }

  /**
   * Makes the URI part of a URI or of a URI with fragment relative to the storage folder, if it is
   * a file URI. Anything else stays as it is.
   *
   * @param uriWithFragment an absolute URI, possibly with fragment
   * @return the relative form
   */
  String relativize(String uriWithFragment) {
    if (uriWithFragment.startsWith(HierarchicalId.CACHE_PREFIX)) {
      return uriWithFragment;
    }
    URI uri = URI.createURI(uriWithFragment);
    if (!uri.isFile() || uri.isRelative()) {
      return uriWithFragment;
    }
    return uri.deresolve(base, true, true, false).toString();
  }

  /**
   * Resolves a URI written by {@link #relativize} against the storage folder.
   *
   * @param uriWithFragment a relative or absolute URI, possibly with fragment
   * @return the absolute form
   */
  String absolutize(String uriWithFragment) {
    if (uriWithFragment.startsWith(HierarchicalId.CACHE_PREFIX)) {
      return uriWithFragment;
    }
    URI uri = URI.createURI(uriWithFragment);
    return uri.isRelative() ? uri.resolve(base).toString() : uriWithFragment;
  }

  /**
   * Combines the same change with hierarchical IDs and with UUIDs into one change with dual IDs.
   * Both must come from the same change with model elements, so that their ID slots correspond.
   *
   * @param withPaths the change with hierarchical IDs (absolute URIs)
   * @param withUuids the change with UUIDs
   * @return the change with dual IDs and relative URIs
   */
  EChange<HierarchicalId> zip(EChange<HierarchicalId> withPaths, EChange<Uuid> withUuids) {
    List<Uuid> uuids = new ArrayList<>();
    AtomicEChangeResolverHelper.resolveChange(
        withUuids,
        uuid -> {
          uuids.add(uuid);
          return uuid;
        },
        UnaryOperator.identity());
    Iterator<Uuid> uuidIterator = uuids.iterator();
    EChange<HierarchicalId> result =
        AtomicEChangeResolverHelper.resolveChange(
            withPaths,
            hid -> {
              if (!uuidIterator.hasNext()) {
                throw new IllegalStateException("change has more IDs than UUIDs: " + withPaths);
              }
              String absolutePath = hid.getId();
              String uuid = uuidIterator.next().getRawValue();
              return new HierarchicalId(
                  encode(relativize(absolutePath), uuid.equals(absolutePath) ? null : uuid));
            },
            UnaryOperator.identity());
    if (uuidIterator.hasNext()) {
      throw new IllegalStateException("change has more UUIDs than IDs: " + withPaths);
    }
    if (result instanceof RootEChange<?> root && root.getUri() != null) {
      root.setUri(relativize(root.getUri()));
    }
    return result;
  }

  /**
   * Converts a logged change to a change with UUIDs that the UUID resolver can resolve and apply.
   *
   * @param logged the logged change with dual IDs and relative URIs
   * @param byPath gives the UUID of the element at an absolute path, for IDs without UUID
   * @return the change with UUIDs and absolute URIs
   */
  EChange<Uuid> toUuids(EChange<HierarchicalId> logged, Function<String, Uuid> byPath) {
    EChange<Uuid> result =
        AtomicEChangeResolverHelper.resolveChange(
            logged,
            hid -> {
              Ref ref = decode(hid.getId());
              return ref.uuid() != null
                  ? Uuid.of(ref.uuid())
                  : byPath.apply(absolutize(ref.path()));
            },
            UnaryOperator.identity());
    if (result instanceof RootEChange<Uuid> root && root.getUri() != null) {
      String uri = absolutize(root.getUri());
      root.setUri(uri);
      root.setResource(new ResourceImpl(URI.createURI(uri)));
    }
    return result;
  }
}
