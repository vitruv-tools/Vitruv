package tools.vitruv.framework.vsum.persistence.delta;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.correspondence.Correspondence;
import tools.vitruv.change.correspondence.CorrespondencePackage;
import tools.vitruv.framework.vsum.persistence.ModelPersistenceContext;

/**
 * Records the changes of the correspondence model as operations on its list of correspondences,
 * one list per propagation, and replays them.
 *
 * <p>Reactions edit the correspondence model directly, outside the change recorder of the models.
 * So instead of recording EMF changes, each save compares the list of correspondences with the
 * list at the previous save, by object identity, and writes
 *
 * <pre>
 * {"op":"remove","index":3}
 * {"op":"insert","index":5,"type":"&lt;nsURI&gt;#//ManualCorrespondence",
 *  "attributes":{"tag":"t"},"left":[ID,...],"right":[ID,...]}
 * </pre>
 *
 * <p>Removals come first, with descending indices of the old list; insertions follow, with
 * ascending indices of the new list. A correspondence whose content changed in place counts as
 * removed and inserted. {@code ID} is a dual ID as in the model changes ({@link ElementIds}):
 * {@code <path>|u=<uuid>}, path only for read-only elements. {@code attributes} holds every set
 * attribute of the correspondence class as EMF literal (a list for many-valued ones) and is left
 * out when empty.
 *
 * <p>Before comparing, correspondences of removed elements are dropped, with the same rule as
 * {@code PersistableCorrespondenceModel.save()} applies, so the log holds what state-based
 * persistence would store.
 */
final class CorrespondenceLog {
  static final String OP = "op";
  static final String REMOVE = "remove";
  static final String INSERT = "insert";
  static final String INDEX = "index";
  static final String TYPE = "type";
  static final String ATTRIBUTES = "attributes";
  static final String LEFT = "left";
  static final String RIGHT = "right";

  private static final Logger LOGGER = LogManager.getLogger(CorrespondenceLog.class);

  private final ModelPersistenceContext context;
  private final ElementIds ids;
  private List<Correspondence> previous = List.of();
  private Map<Correspondence, Key> previousKeys = new IdentityHashMap<>();

  /** What makes two states of a correspondence equal for the log. */
  private record Key(EClass type, Map<String, Object> attributes, List<String> left,
      List<String> right) {}

  /**
   * Creates the log for the correspondence model of a context.
   *
   * @param context the virtual model's models and correspondence model
   * @param ids converts element IDs
   */
  CorrespondenceLog(ModelPersistenceContext context, ElementIds ids) {
    this.context = context;
    this.ids = ids;
  }

  /** Takes the current list as the state the next {@link #diff()} compares against. */
  void reset() {
    previous = List.copyOf(list());
    previousKeys = new IdentityHashMap<>();
    for (Correspondence correspondence : previous) {
      previousKeys.put(correspondence, key(correspondence));
    }
  }

  /**
   * Drops the correspondences of removed elements, compares the list with the one at the last
   * call (or {@link #reset()}) and takes the current list as the new reference.
   *
   * @return the operations that turn the previous list into the current one, empty if none
   */
  List<Map<String, Object>> diff() {
    context.getCorrespondenceModel().removeCorrespondencesForRemovedElements();
    List<Correspondence> current = List.copyOf(list());
    Map<Correspondence, Key> currentKeys = new IdentityHashMap<>();
    for (Correspondence correspondence : current) {
      currentKeys.put(correspondence, key(correspondence));
    }
    List<Correspondence> keptBefore = new ArrayList<>();
    for (Correspondence correspondence : previous) {
      if (Objects.equals(currentKeys.get(correspondence), previousKeys.get(correspondence))) {
        keptBefore.add(correspondence);
      }
    }
    List<Correspondence> keptAfter =
        current.stream()
            .filter(it -> Objects.equals(previousKeys.get(it), currentKeys.get(it)))
            .toList();
    // Kept correspondences keep their order, as the correspondence model only adds and removes.
    // Should they not, everything is removed and inserted again.
    boolean orderKept = keptBefore.equals(keptAfter);
    Set<Correspondence> kept = Collections.newSetFromMap(new IdentityHashMap<>());
    kept.addAll(keptAfter);
    List<Map<String, Object>> operations = new ArrayList<>();
    for (int i = previous.size() - 1; i >= 0; i--) {
      Correspondence correspondence = previous.get(i);
      if (!orderKept || !kept.contains(correspondence)) {
        Map<String, Object> remove = new LinkedHashMap<>();
        remove.put(OP, REMOVE);
        remove.put(INDEX, (long) i);
        operations.add(remove);
      }
    }
    for (int j = 0; j < current.size(); j++) {
      Correspondence correspondence = current.get(j);
      if (!orderKept || !kept.contains(correspondence)) {
        operations.add(insert(j, correspondence));
      }
    }
    previous = current;
    previousKeys = currentKeys;
    return operations;
  }

  /**
   * Applies logged operations to the correspondence model; the models must be in the state after
   * the propagation that logged them.
   *
   * @param operations the operations of one record
   */
  void apply(List<Map<String, Object>> operations) {
    List<Correspondence> list = list();
    for (Map<String, Object> operation : operations) {
      Object op = operation.get(OP);
      int index = Math.toIntExact((Long) operation.get(INDEX));
      if (REMOVE.equals(op)) {
        list.remove(index);
      } else if (INSERT.equals(op)) {
        list.add(index, create(operation));
      } else {
        throw new IllegalStateException("unknown correspondence operation " + op);
      }
    }
  }

  private List<Correspondence> list() {
    return context.getCorrespondenceModel().getCorrespondenceList();
  }

  private Key key(Correspondence correspondence) {
    return new Key(
        correspondence.eClass(),
        attributes(correspondence),
        correspondence.getLeftEObjects().stream().map(this::uuidOrUri).toList(),
        correspondence.getRightEObjects().stream().map(this::uuidOrUri).toList());
  }

  private String uuidOrUri(EObject element) {
    String uuid = uuid(element);
    return uuid != null ? uuid : EcoreUtil.getURI(element).toString();
  }

  private String uuid(EObject element) {
    try {
      return context.getUuidResolver().getUuid(element).getRawValue();
    } catch (IllegalStateException e) {
      return null; // not managed by the UUID resolver: identified by its path only
    }
  }

  private static Map<String, Object> attributes(Correspondence correspondence) {
    Map<String, Object> attributes = new LinkedHashMap<>();
    for (EAttribute attribute : correspondence.eClass().getEAllAttributes()) {
      if (!correspondence.eIsSet(attribute)) {
        continue;
      }
      Object value = correspondence.eGet(attribute);
      if (attribute.isMany()) {
        List<String> literals = new ArrayList<>();
        for (Object item : (List<?>) value) {
          literals.add(EcoreUtil.convertToString(attribute.getEAttributeType(), item));
        }
        attributes.put(attribute.getName(), literals);
      } else {
        attributes.put(
            attribute.getName(), EcoreUtil.convertToString(attribute.getEAttributeType(), value));
      }
    }
    return attributes;
  }

  private Map<String, Object> insert(int index, Correspondence correspondence) {
    for (EReference reference : correspondence.eClass().getEAllReferences()) {
      if (reference != CorrespondencePackage.Literals.CORRESPONDENCE__LEFT_EOBJECTS
          && reference != CorrespondencePackage.Literals.CORRESPONDENCE__RIGHT_EOBJECTS
          && correspondence.eIsSet(reference)) {
        LOGGER.warn(
            "Reference {} of correspondence class {} is not logged",
            reference.getName(),
            correspondence.eClass().getName());
      }
    }
    Map<String, Object> insert = new LinkedHashMap<>();
    insert.put(OP, INSERT);
    insert.put(INDEX, (long) index);
    insert.put(TYPE, EcoreUtil.getURI(correspondence.eClass()).toString());
    Map<String, Object> attributes = attributes(correspondence);
    if (!attributes.isEmpty()) {
      insert.put(ATTRIBUTES, attributes);
    }
    insert.put(LEFT, correspondence.getLeftEObjects().stream().map(this::id).toList());
    insert.put(RIGHT, correspondence.getRightEObjects().stream().map(this::id).toList());
    return insert;
  }

  private String id(EObject element) {
    String absolutePath = EcoreUtil.getURI(element).toString();
    String uuid = uuid(element);
    return ElementIds.encode(
        ids.relativize(absolutePath), absolutePath.equals(uuid) ? null : uuid);
  }

  @SuppressWarnings("unchecked")
  private Correspondence create(Map<String, Object> operation) {
    URI typeUri = URI.createURI((String) operation.get(TYPE));
    EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(typeUri.trimFragment().toString());
    checkState(ePackage != null, "correspondence package %s is not registered", typeUri);
    EObject type =
        ePackage.eResource() != null
            ? ePackage.eResource().getEObject(typeUri.fragment())
            : ePackage.getEClassifier(typeUri.fragment().replaceFirst("^//", ""));
    checkState(type instanceof EClass, "no correspondence class %s", typeUri);
    Correspondence correspondence = (Correspondence) EcoreUtil.create((EClass) type);
    Object attributes = operation.get(ATTRIBUTES);
    if (attributes instanceof Map<?, ?> map) {
      for (Map.Entry<?, ?> entry : map.entrySet()) {
        EAttribute attribute =
            (EAttribute) correspondence.eClass().getEStructuralFeature((String) entry.getKey());
        checkState(attribute != null, "%s has no attribute %s", typeUri, entry.getKey());
        if (attribute.isMany()) {
          List<Object> values = (List<Object>) correspondence.eGet(attribute);
          for (Object literal : (List<?>) entry.getValue()) {
            values.add(EcoreUtil.createFromString(attribute.getEAttributeType(), (String) literal));
          }
        } else {
          correspondence.eSet(
              attribute,
              EcoreUtil.createFromString(attribute.getEAttributeType(), (String) entry.getValue()));
        }
      }
    }
    for (Object id : (List<?>) operation.get(LEFT)) {
      correspondence.getLeftEObjects().add(resolve((String) id));
    }
    for (Object id : (List<?>) operation.get(RIGHT)) {
      correspondence.getRightEObjects().add(resolve((String) id));
    }
    return correspondence;
  }

  private EObject resolve(String id) {
    ElementIds.Ref ref = ElementIds.decode(id);
    if (ref.uuid() != null) {
      return context.getUuidResolver().getEObject(Uuid.of(ref.uuid()));
    }
    String path = ids.absolutize(ref.path());
    EObject element = context.getModelsResourceSet().getEObject(URI.createURI(path), true);
    checkState(element != null, "no element at %s", path);
    return element;
  }
}
