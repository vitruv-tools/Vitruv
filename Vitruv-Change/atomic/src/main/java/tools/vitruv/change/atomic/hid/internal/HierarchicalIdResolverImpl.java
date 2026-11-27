package tools.vitruv.change.atomic.hid.internal;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.getOrCreateResource;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil;
import java.util.PriorityQueue;
import java.util.Queue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.atomic.hid.ObjectResolutionUtil;

/**
 * {@link HierarchicalIdResolverImpl} implements the {@link HierarchicalIdResolver} interface,
 * based on a bidirectional mapping between {@link EObject}s and {@link HierarchicalId}s.
 */
public class HierarchicalIdResolverImpl implements HierarchicalIdResolver {
  private static Logger logger = LogManager.getLogger(HierarchicalIdResolverImpl.class);
  
  private final ResourceSet resourceSet;
  private final BiMap<EObject, HierarchicalId> eObjectToId = HashBiMap.create();
  private final CacheIdsRepository cacheIds = new CacheIdsRepository();

  /**
   * Instantiates an ID resolver with the given {@link ResourceSet} for resolving objects.
   *
   * @param resourceSet -
   *     the {@link ResourceSet} to load model elements from, may not be {@code null}
   * @throws IllegalArgumentException if given {@link ResourceSet} is {@code null}
   */
  public HierarchicalIdResolverImpl(ResourceSet resourceSet) {
    checkArgument(resourceSet != null, "Resource set may not be null");
    this.resourceSet = resourceSet;
  }
  
  @Override
  public void endTransaction() {
    cleanupRemovedElements();
    checkState(cacheIds.isNoneMissing(), 
        "there are still elements in cache although a transaction has been closed");
  }
  
  private void cleanupRemovedElements() {
    for (final var iterator = eObjectToId.keySet().iterator(); iterator.hasNext();) {
      final var object = iterator.next();
      if (object.eResource() == null || object.eResource().getResourceSet() == null) {
        var id = eObjectToId.get(object);
        if (id.isCache()) {
          cacheIds.push(id);
        }
        iterator.remove();
      }
    }
  }

  @Override
  public Resource getResource(URI uri) {
    return getOrCreateResource(resourceSet, uri);
  }

  @Override
  public HierarchicalId getAndUpdateId(EObject eObject) {
    return (eObject.eResource() != null) 
        ? registerObjectInResource(eObject)
        : getOrRegisterCachedObject(eObject);
  }
  
  private HierarchicalId registerObjectInResource(EObject eObject) {
    var id = new HierarchicalId(eObject.eResource().getURI().appendFragment(
        ObjectResolutionUtil.getHierarchicUriFragment(eObject)).toString());
    register(id, eObject);
    return id;
  }
  
  private HierarchicalId getOrRegisterCachedObject(EObject eObject) {
    final HierarchicalId storedId = eObjectToId.get(eObject);
    if (storedId != null && storedId.isCache()) {
      return storedId;
    } else {
      final HierarchicalId id = cacheIds.peek();
      register(id, eObject);
      return id;
    }
  }

  @Override
  public EObject getEObject(HierarchicalId id) {
    final EObject eObject = getEObjectOrNullFor(id);
    checkState(eObject != null, "no EObject could be found for ID: %s", id);
    return eObject;
  }

  private EObject getEObjectOrNullFor(HierarchicalId id) {
    final URI uri = URI.createURI(id.getId());
    EObject result = getEObjectIfReadonlyUri(uri);
    if (result == null) {
      result = getStoredEObject(uri);
    }
    if (result == null) {
      result = getAndRegisterNonStoredEObject(uri);
    }
    return result;
  }
 
  private EObject getEObjectIfReadonlyUri(URI uri) {
    if (isReadOnly(uri) && uri.hasFragment()) {
      return resourceSet.getEObject(uri, true);
    }
    return null;
  }
    
  private EObject getStoredEObject(URI uri) {
    return eObjectToId.inverse().get(new HierarchicalId(uri.toString()));
  }
  
  private EObject getAndRegisterNonStoredEObject(URI uri) {
    final var candidate = resourceSet.getEObject(uri, false);
    if (candidate != null) {
      getAndUpdateId(candidate);
    }
    return candidate;
  }
    
  private void register(HierarchicalId id, EObject eObject) {
    checkState(eObject != null, "object must not be null");
    if (logger.isTraceEnabled()) {
      logger.trace("Adding ID %s for EObject: %s", id, eObject); 
    }

    final var oldObject = eObjectToId.inverse().get(id);
    final var oldId = eObjectToId.get(eObject);
    if (oldObject != null && oldObject != eObject) {
      eObjectToId.remove(oldObject);
    }
    if (oldId != null && oldId != id) {
      eObjectToId.inverse().remove(oldId);
    }
    eObjectToId.put(eObject, id);
    if (oldId != null && oldId.isCache()) {
      cacheIds.push(oldId);
    }
    if (id.isCache()) {
      final var entry = cacheIds.pop();
      checkState(id.equals(entry), "expected cache ID was %s but actually gave %s", id, entry);
    }
  }

  @Override
  public boolean hasEObject(HierarchicalId id) {
    return getEObjectOrNullFor(id) != null;
  }

  private static boolean isReadOnly(URI uri) {
    return uri != null && (URIUtil.isPathmap(uri) || uri.isArchive());
  }
    
  /**
   * The cache IDs repository provides cache IDs with values starting from 0, 
   * always providing the one with the lowest index first. It ensures that
   * the same sequence of taking and pushing entries always gives the same values.
   */
  static class CacheIdsRepository {
    private final Queue<HierarchicalId> entries = new PriorityQueue<>();
    private int maxValue;
    
    HierarchicalId pop() {
      if (entries.isEmpty()) {
        push(new HierarchicalId(HierarchicalId.CACHE_PREFIX + maxValue++));
      }
      return entries.poll();
    }
    
    HierarchicalId peek() {
      if (entries.isEmpty()) {
        return new HierarchicalId(HierarchicalId.CACHE_PREFIX + maxValue);
      }
      return entries.peek();
    }
    
    void push(HierarchicalId value) {
      checkState(value.isCache(), "%s is a not a cache ID", value);
      entries.add(value);
    }
    
    boolean isNoneMissing() {
      return entries.size() == maxValue;
    }
  }
}
