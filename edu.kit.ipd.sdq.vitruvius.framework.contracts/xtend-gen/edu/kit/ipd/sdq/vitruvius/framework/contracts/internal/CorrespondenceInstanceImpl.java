package edu.kit.ipd.sdq.vitruvius.framework.contracts.internal;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableHashMap;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Triple;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class CorrespondenceInstanceImpl extends ModelInstance implements CorrespondenceInstanceDecorator {
  private final static Logger logger = Logger.getLogger(CorrespondenceInstanceImpl.class.getSimpleName());
  
  private final Mapping mapping;
  
  private final ModelProviding modelProviding;
  
  private final Correspondences correspondences;
  
  private final ClaimableMap<TUID, Set<List<TUID>>> tuid2tuidListsMap;
  
  protected final ClaimableMap<List<TUID>, Set<Correspondence>> tuid2CorrespondencesMap;
  
  private boolean changedAfterLastSave = false;
  
  private final Map<String, String> saveCorrespondenceOptions;
  
  public CorrespondenceInstanceImpl(final Mapping mapping, final ModelProviding modelProviding, final VURI correspondencesVURI, final Resource correspondencesResource) {
    super(correspondencesVURI, correspondencesResource);
    this.mapping = mapping;
    this.modelProviding = modelProviding;
    ClaimableHashMap<TUID, Set<List<TUID>>> _claimableHashMap = new ClaimableHashMap<TUID, Set<List<TUID>>>();
    this.tuid2tuidListsMap = _claimableHashMap;
    ClaimableHashMap<List<TUID>, Set<Correspondence>> _claimableHashMap_1 = new ClaimableHashMap<List<TUID>, Set<Correspondence>>();
    this.tuid2CorrespondencesMap = _claimableHashMap_1;
    HashMap<String, String> _hashMap = new HashMap<String, String>();
    this.saveCorrespondenceOptions = _hashMap;
    String _optionProcessDanglingHref = VitruviusConstants.getOptionProcessDanglingHref();
    String _optionProcessDanglingHrefDiscard = VitruviusConstants.getOptionProcessDanglingHrefDiscard();
    this.saveCorrespondenceOptions.put(_optionProcessDanglingHref, _optionProcessDanglingHrefDiscard);
    Correspondences _loadAndRegisterCorrespondences = this.loadAndRegisterCorrespondences(correspondencesResource);
    this.correspondences = _loadAndRegisterCorrespondences;
  }
  
  @Override
  public void addCorrespondence(final Correspondence correspondence) {
    this.addCorrespondenceToModel(correspondence);
    this.registerCorrespondence(correspondence);
    this.setChangeAfterLastSaveFlag();
  }
  
  private void registerCorrespondence(final Correspondence correspondence) {
    this.registerTUIDLists(correspondence);
    this.registerCorrespondenceForTUIDs(correspondence);
  }
  
  private void registerTUIDLists(final Correspondence correspondence) {
    EList<TUID> _aTUIDs = correspondence.getATUIDs();
    this.registerTUIDList(_aTUIDs);
    EList<TUID> _bTUIDs = correspondence.getBTUIDs();
    this.registerTUIDList(_bTUIDs);
  }
  
  private void registerTUIDList(final List<TUID> tuidList) {
    for (final TUID tuid : tuidList) {
      {
        Set<List<TUID>> tuidLists = this.tuid2tuidListsMap.get(tuid);
        boolean _equals = Objects.equal(tuidLists, null);
        if (_equals) {
          HashSet<List<TUID>> _hashSet = new HashSet<List<TUID>>();
          tuidLists = _hashSet;
          this.tuid2tuidListsMap.put(tuid, tuidLists);
        }
        tuidLists.add(tuidList);
      }
    }
  }
  
  private void addCorrespondenceToModel(final Correspondence correspondence) {
    EList<Correspondence> correspondenceListForAddition = this.correspondences.getCorrespondences();
    correspondenceListForAddition.add(correspondence);
  }
  
  @Override
  public TUID calculateTUIDFromEObject(final EObject eObject) {
    Metamodel metamodel = null;
    Metamodel _metamodelA = this.mapping.getMetamodelA();
    List<EObject> _list = CollectionBridge.<EObject>toList(eObject);
    boolean _hasMetaclassInstances = _metamodelA.hasMetaclassInstances(_list);
    if (_hasMetaclassInstances) {
      Metamodel _metamodelA_1 = this.mapping.getMetamodelA();
      metamodel = _metamodelA_1;
    }
    Metamodel _metamodelB = this.mapping.getMetamodelB();
    List<EObject> _list_1 = CollectionBridge.<EObject>toList(eObject);
    boolean _hasMetaclassInstances_1 = _metamodelB.hasMetaclassInstances(_list_1);
    if (_hasMetaclassInstances_1) {
      Metamodel _metamodelB_1 = this.mapping.getMetamodelB();
      metamodel = _metamodelB_1;
    }
    if ((metamodel == null)) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("EObject: \'");
      _builder.append(eObject, "");
      _builder.append("\' is neither an instance of MM1 nor an instance of MM2. ");
      String _string = _builder.toString();
      CorrespondenceInstanceImpl.logger.warn(_string);
      return null;
    } else {
      String _calculateTUIDFromEObject = metamodel.calculateTUIDFromEObject(eObject);
      return TUID.getInstance(_calculateTUIDFromEObject);
    }
  }
  
  @Override
  public List<TUID> calculateTUIDsFromEObjects(final List<EObject> eObjects) {
    final Function1<EObject, TUID> _function = (EObject it) -> {
      return this.calculateTUIDFromEObject(it);
    };
    return ListExtensions.<EObject, TUID>map(eObjects, _function);
  }
  
  @Override
  public boolean changedAfterLastSave() {
    return this.changedAfterLastSave;
  }
  
  @Override
  public Correspondence claimUniqueCorrespondence(final List<EObject> aEObjects, final List<EObject> bEObjects) {
    final Set<Correspondence> correspondences = this.getCorrespondences(aEObjects);
    for (final Correspondence correspondence : correspondences) {
      {
        final EList<EObject> correspondingBs = correspondence.getBs();
        boolean _and = false;
        boolean _notEquals = (!Objects.equal(correspondingBs, null));
        if (!_notEquals) {
          _and = false;
        } else {
          boolean _equals = correspondingBs.equals(bEObjects);
          _and = _equals;
        }
        if (_and) {
          return correspondence;
        }
      }
    }
    throw new RuntimeException((((("No correspondence for \'" + aEObjects) + "\' and \'") + bEObjects) + "\' was found!"));
  }
  
  @Override
  public Correspondence createAndAddCorrespondence(final List<EObject> eObjects1, final List<EObject> eObjects2) {
    Correspondence correspondence = CorrespondenceFactory.eINSTANCE.createCorrespondence();
    this.setCorrespondenceFeatures(correspondence, eObjects1, eObjects2);
    this.addCorrespondence(correspondence);
    return correspondence;
  }
  
  @Override
  public Set<Correspondence> getCorrespondences(final List<EObject> eObjects) {
    List<TUID> tuids = this.calculateTUIDsFromEObjects(eObjects);
    return this.getCorrespondencesForTUIDs(tuids);
  }
  
  @Override
  public Set<Correspondence> getCorrespondencesForTUIDs(final List<TUID> tuids) {
    Set<Correspondence> correspondences = this.tuid2CorrespondencesMap.get(tuids);
    if ((correspondences == null)) {
      HashSet<Correspondence> _hashSet = new HashSet<Correspondence>();
      correspondences = _hashSet;
      this.tuid2CorrespondencesMap.put(tuids, correspondences);
      this.registerTUIDList(tuids);
    }
    return correspondences;
  }
  
  @Override
  public Set<List<EObject>> getCorrespondingEObjects(final List<EObject> eObjects) {
    List<TUID> tuids = this.calculateTUIDsFromEObjects(eObjects);
    Set<List<TUID>> correspondingTUIDLists = this.getCorrespondingTUIDs(tuids);
    return this.resolveEObjectsSetsFromTUIDsSets(correspondingTUIDLists);
  }
  
  private Set<List<TUID>> getCorrespondingTUIDs(final List<TUID> tuids) {
    Set<Correspondence> allCorrespondences = this.getCorrespondencesForTUIDs(tuids);
    int _size = allCorrespondences.size();
    Set<List<TUID>> correspondingTUIDLists = new HashSet<List<TUID>>(_size);
    for (final Correspondence correspondence : allCorrespondences) {
      {
        List<TUID> aTUIDs = correspondence.getATUIDs();
        List<TUID> bTUIDs = correspondence.getBTUIDs();
        boolean _or = false;
        boolean _or_1 = false;
        if (((aTUIDs == null) || (bTUIDs == null))) {
          _or_1 = true;
        } else {
          int _size_1 = aTUIDs.size();
          boolean _equals = (_size_1 == 0);
          _or_1 = _equals;
        }
        if (_or_1) {
          _or = true;
        } else {
          int _size_2 = bTUIDs.size();
          boolean _equals_1 = (_size_2 == 0);
          _or = _equals_1;
        }
        if (_or) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("The correspondence \'");
          _builder.append(correspondence, "");
          _builder.append("\' links to an empty TUID \'");
          _builder.append(aTUIDs, "");
          _builder.append("\' or \'");
          _builder.append(bTUIDs, "");
          _builder.append("\'!");
          String _string = _builder.toString();
          throw new IllegalStateException(_string);
        }
        boolean _equals_2 = aTUIDs.equals(tuids);
        if (_equals_2) {
          correspondingTUIDLists.add(bTUIDs);
        } else {
          correspondingTUIDLists.add(aTUIDs);
        }
      }
    }
    return correspondingTUIDLists;
  }
  
  @Override
  public Map<String, Object> getFileExtPrefix2ObjectMapForSave() {
    try {
      Resource _resource = this.getResource();
      EcoreResourceBridge.saveResource(_resource, this.saveCorrespondenceOptions);
    } catch (final Throwable _t) {
      if (_t instanceof IOException) {
        final IOException e = (IOException)_t;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("Could not save correspondence instance \'");
        _builder.append(this, "");
        _builder.append("\' using the resource \'");
        Resource _resource_1 = this.getResource();
        _builder.append(_resource_1, "");
        _builder.append("\' and the options \'");
        _builder.append(this.saveCorrespondenceOptions, "");
        _builder.append("\': ");
        _builder.append(e, "");
        String _string = _builder.toString();
        throw new RuntimeException(_string);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return new HashMap<String, Object>();
  }
  
  @Override
  public Set<String> getFileExtPrefixesForObjectsToLoad() {
    return new HashSet<String>();
  }
  
  @Override
  public <T extends CorrespondenceInstanceDecorator> T getFirstCorrespondenceInstanceDecoratorOfTypeInChain(final Class<T> type) {
    boolean _isInstance = type.isInstance(this);
    if (_isInstance) {
      return type.cast(this);
    }
    return null;
  }
  
  @Override
  public Mapping getMapping() {
    return this.mapping;
  }
  
  private Metamodel getMetamodelHavingTUID(final String tuidString) {
    Metamodel metamodel = null;
    Metamodel metamodelA = this.mapping.getMetamodelA();
    boolean _hasTUID = metamodelA.hasTUID(tuidString);
    if (_hasTUID) {
      metamodel = metamodelA;
    }
    Metamodel metamodelB = this.mapping.getMetamodelB();
    boolean _hasTUID_1 = metamodelB.hasTUID(tuidString);
    if (_hasTUID_1) {
      metamodel = metamodelB;
    }
    if ((metamodel == null)) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("The TUID \'");
      _builder.append(tuidString, "");
      _builder.append("\' is neither valid for ");
      _builder.append(metamodelA, "");
      _builder.append(" nor ");
      _builder.append(metamodelB, "");
      String _string = _builder.toString();
      throw new IllegalArgumentException(_string);
    }
    return metamodel;
  }
  
  @Override
  public boolean hasCorrespondences() {
    Collection<Set<Correspondence>> _values = this.tuid2CorrespondencesMap.values();
    for (final Set<Correspondence> correspondences : _values) {
      boolean _isEmpty = correspondences.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        return true;
      }
    }
    return false;
  }
  
  @Override
  public boolean hasCorrespondences(final List<EObject> eObjects) {
    List<TUID> tuids = this.calculateTUIDsFromEObjects(eObjects);
    Set<Correspondence> correspondences = this.tuid2CorrespondencesMap.get(tuids);
    boolean _and = false;
    boolean _notEquals = (!Objects.equal(correspondences, null));
    if (!_notEquals) {
      _and = false;
    } else {
      int _size = correspondences.size();
      boolean _greaterThan = (_size > 0);
      _and = _greaterThan;
    }
    return _and;
  }
  
  @Override
  public void initialize(final Map<String, Object> fileExtPrefix2ObjectMap) {
  }
  
  private Correspondences loadAndRegisterCorrespondences(final Resource correspondencesResource) {
    try {
      correspondencesResource.load(this.saveCorrespondenceOptions);
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception e = (Exception)_t;
        CorrespondenceInstanceImpl.logger.trace(
          "Could not load correspondence resource - creating new correspondence instance resource.");
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    Resource _resource = this.getResource();
    EObject correspondences = EcoreResourceBridge.getResourceContentRootIfUnique(_resource);
    if ((correspondences == null)) {
      Correspondences _createCorrespondences = CorrespondenceFactory.eINSTANCE.createCorrespondences();
      correspondences = _createCorrespondences;
      EList<EObject> _contents = correspondencesResource.getContents();
      _contents.add(correspondences);
    } else {
      if ((correspondences instanceof Correspondences)) {
        this.registerLoadedCorrespondences(((Correspondences) correspondences));
      } else {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("The unique root object \'");
        _builder.append(correspondences, "");
        _builder.append("\' of the correspondence model \'");
        VURI _uRI = this.getURI();
        _builder.append(_uRI, "");
        _builder.append("\' is not correctly typed!");
        String _string = _builder.toString();
        throw new RuntimeException(_string);
      }
    }
    return ((Correspondences) correspondences);
  }
  
  private void markNeighborAndChildrenCorrespondences(final Correspondence correspondence, final Set<Correspondence> markedCorrespondences) {
    boolean _add = markedCorrespondences.add(correspondence);
    if (_add) {
      TUID elementATUID = correspondence.getElementATUID();
      this.markNeighborAndChildrenCorrespondences(elementATUID, markedCorrespondences);
      TUID elementBTUID = correspondence.getElementBTUID();
      this.markNeighborAndChildrenCorrespondences(elementBTUID, markedCorrespondences);
    }
  }
  
  private void markNeighborAndChildrenCorrespondences(final TUID tuid, final Set<Correspondence> markedCorrespondences) {
    EObject eObject = this.resolveEObjectFromTUID(tuid);
    this.markNeighborAndChildrenCorrespondencesRecursively(eObject, markedCorrespondences);
  }
  
  private void markNeighborAndChildrenCorrespondencesRecursively(final EObject eObject, final Set<Correspondence> markedCorrespondences) {
    List<EObject> _list = CollectionBridge.<EObject>toList(eObject);
    Set<Correspondence> allCorrespondences = this.getCorrespondences(_list);
    for (final Correspondence correspondence : allCorrespondences) {
      this.markNeighborAndChildrenCorrespondences(correspondence, markedCorrespondences);
    }
    List<EObject> children = eObject.eContents();
    for (final EObject child : children) {
      this.markNeighborAndChildrenCorrespondencesRecursively(child, markedCorrespondences);
    }
  }
  
  private void registerCorrespondenceForTUIDs(final Correspondence correspondence) {
    EList<TUID> _aTUIDs = correspondence.getATUIDs();
    final Set<Correspondence> correspondencesForAs = this.getCorrespondencesForTUIDs(_aTUIDs);
    correspondencesForAs.add(correspondence);
    EList<TUID> _bTUIDs = correspondence.getBTUIDs();
    final Set<Correspondence> correspondencesForBs = this.getCorrespondencesForTUIDs(_bTUIDs);
    correspondencesForBs.add(correspondence);
  }
  
  private void registerLoadedCorrespondences(final Correspondences correspondences) {
    EList<Correspondence> _correspondences = correspondences.getCorrespondences();
    for (final Correspondence correspondence : _correspondences) {
      this.registerCorrespondence(correspondence);
    }
  }
  
  private void removeCorrespondenceFromMaps(final Correspondence markedCorrespondence) {
    List<TUID> aTUIDs = markedCorrespondence.getATUIDs();
    List<TUID> bTUIDs = markedCorrespondence.getBTUIDs();
    this.removeTUID2TUIDListsEntries(aTUIDs);
    this.removeTUID2TUIDListsEntries(bTUIDs);
    this.tuid2CorrespondencesMap.remove(aTUIDs);
    this.tuid2CorrespondencesMap.remove(bTUIDs);
  }
  
  private void removeTUID2TUIDListsEntries(final List<TUID> tuids) {
    for (final TUID tuid : tuids) {
      {
        final Set<List<TUID>> tuidLists = this.tuid2tuidListsMap.get(tuid);
        tuidLists.remove(tuids);
      }
    }
  }
  
  @Override
  public Set<Correspondence> removeCorrespondencesOfEObjectsAndChildrenOnBothSides(final Correspondence correspondence) {
    if ((correspondence == null)) {
      return Collections.<Correspondence>emptySet();
    }
    Set<Correspondence> markedCorrespondences = new HashSet<Correspondence>();
    this.markNeighborAndChildrenCorrespondences(correspondence, markedCorrespondences);
    for (final Correspondence markedCorrespondence : markedCorrespondences) {
      {
        this.removeCorrespondenceFromMaps(markedCorrespondence);
        EcoreUtil.remove(markedCorrespondence);
        this.setChangeAfterLastSaveFlag();
      }
    }
    return markedCorrespondences;
  }
  
  @Override
  public Set<Correspondence> removeCorrespondencesOfEObjectAndChildrenOnBothSides(final EObject eObject) {
    List<EObject> _list = CollectionBridge.<EObject>toList(eObject);
    List<TUID> _calculateTUIDsFromEObjects = this.calculateTUIDsFromEObjects(_list);
    final TUID tuid = CollectionBridge.<TUID>claimOne(_calculateTUIDsFromEObjects);
    return this.removeCorrespondencesOfEObjectAndChildrenOnBothSides(tuid);
  }
  
  @Override
  public Set<Correspondence> removeCorrespondencesOfEObjectAndChildrenOnBothSides(final TUID tuid) {
    List<TUID> _list = CollectionBridge.<TUID>toList(tuid);
    Set<Correspondence> directCorrespondences = this.getCorrespondencesForTUIDs(_list);
    Set<Correspondence> directAndChildrenCorrespondences = new HashSet<Correspondence>();
    for (final Correspondence correspondence : directCorrespondences) {
      Set<Correspondence> _removeCorrespondencesOfEObjectsAndChildrenOnBothSides = this.removeCorrespondencesOfEObjectsAndChildrenOnBothSides(correspondence);
      directAndChildrenCorrespondences.addAll(_removeCorrespondencesOfEObjectsAndChildrenOnBothSides);
    }
    return directAndChildrenCorrespondences;
  }
  
  /**
   * (non-Javadoc)
   * 
   * @see CorrespondenceInstance#
   * resetChangedAfterLastSave()
   */
  @Override
  public void resetChangedAfterLastSave() {
    this.changedAfterLastSave = false;
  }
  
  @Override
  public EObject resolveEObjectFromRootAndFullTUID(final EObject root, final String tuidString) {
    Metamodel _metamodelHavingTUID = this.getMetamodelHavingTUID(tuidString);
    return _metamodelHavingTUID.resolveEObjectFromRootAndFullTUID(root, tuidString);
  }
  
  @Override
  public List<EObject> resolveEObjectsFromTUIDs(final List<TUID> tuids) {
    final Function1<TUID, EObject> _function = (TUID it) -> {
      return this.resolveEObjectFromTUID(it);
    };
    return ListExtensions.<TUID, EObject>map(tuids, _function);
  }
  
  @Override
  public Set<List<EObject>> resolveEObjectsSetsFromTUIDsSets(final Set<List<TUID>> tuidLists) {
    final Function1<List<TUID>, List<EObject>> _function = (List<TUID> it) -> {
      return this.resolveEObjectsFromTUIDs(it);
    };
    Iterable<List<EObject>> _map = IterableExtensions.<List<TUID>, List<EObject>>map(tuidLists, _function);
    return IterableExtensions.<List<EObject>>toSet(_map);
  }
  
  @Override
  public EObject resolveEObjectFromTUID(final TUID tuid) {
    String tuidString = tuid.toString();
    Metamodel metamodel = this.getMetamodelHavingTUID(tuidString);
    VURI vuri = metamodel.getModelVURIContainingIdentifiedEObject(tuidString);
    EObject rootEObject = null;
    ModelInstance modelInstance = null;
    if ((vuri != null)) {
      ModelInstance _andLoadModelInstanceOriginal = this.modelProviding.getAndLoadModelInstanceOriginal(vuri);
      modelInstance = _andLoadModelInstanceOriginal;
      EObject _firstRootEObject = modelInstance.getFirstRootEObject();
      rootEObject = _firstRootEObject;
    }
    EObject resolvedEobject = null;
    try {
      EObject _resolveEObjectFromRootAndFullTUID = metamodel.resolveEObjectFromRootAndFullTUID(rootEObject, tuidString);
      resolvedEobject = _resolveEObjectFromRootAndFullTUID;
    } catch (final Throwable _t) {
      if (_t instanceof IllegalArgumentException) {
        final IllegalArgumentException iae = (IllegalArgumentException)_t;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    if (((null == resolvedEobject) && (modelInstance != null))) {
      modelInstance.load(null, true);
      EObject _uniqueRootEObject = modelInstance.getUniqueRootEObject();
      rootEObject = _uniqueRootEObject;
      EObject _resolveEObjectFromRootAndFullTUID_1 = metamodel.resolveEObjectFromRootAndFullTUID(rootEObject, tuidString);
      resolvedEobject = _resolveEObjectFromRootAndFullTUID_1;
      if ((null == resolvedEobject)) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("Could not resolve TUID ");
        _builder.append(tuidString, "");
        _builder.append(" in eObject ");
        _builder.append(rootEObject, "");
        String _string = _builder.toString();
        throw new RuntimeException(_string);
      }
    }
    boolean _and = false;
    if (!(null != resolvedEobject)) {
      _and = false;
    } else {
      boolean _eIsProxy = resolvedEobject.eIsProxy();
      _and = _eIsProxy;
    }
    if (_and) {
      Resource _resource = this.getResource();
      EcoreUtil.resolve(resolvedEobject, _resource);
    }
    return resolvedEobject;
  }
  
  private void setChangeAfterLastSaveFlag() {
    this.changedAfterLastSave = true;
  }
  
  private void setCorrespondenceFeatures(final Correspondence correspondence, final List<EObject> eObjects1, final List<EObject> eObjects2) {
    List<EObject> aEObjects = eObjects1;
    List<EObject> bEObjects = eObjects2;
    Metamodel _metamodelA = this.mapping.getMetamodelA();
    boolean _hasMetaclassInstances = _metamodelA.hasMetaclassInstances(bEObjects);
    if (_hasMetaclassInstances) {
      List<EObject> tmp = aEObjects;
      aEObjects = bEObjects;
      bEObjects = tmp;
    }
    List<TUID> aTUIDs = this.calculateTUIDsFromEObjects(aEObjects);
    EList<TUID> _aTUIDs = correspondence.getATUIDs();
    _aTUIDs.addAll(aTUIDs);
    List<TUID> bTUIDs = this.calculateTUIDsFromEObjects(bEObjects);
    EList<TUID> _bTUIDs = correspondence.getBTUIDs();
    _bTUIDs.addAll(bTUIDs);
  }
  
  @Override
  public void updateTUID(final EObject oldEObject, final EObject newEObject) {
    List<EObject> _list = CollectionBridge.<EObject>toList(oldEObject);
    List<TUID> _calculateTUIDsFromEObjects = this.calculateTUIDsFromEObjects(_list);
    TUID oldTUID = CollectionBridge.<TUID>claimOne(_calculateTUIDsFromEObjects);
    this.updateTUID(oldTUID, newEObject);
  }
  
  @Override
  public void updateTUID(final TUID oldTUID, final EObject newEObject) {
    List<EObject> _list = CollectionBridge.<EObject>toList(newEObject);
    List<TUID> _calculateTUIDsFromEObjects = this.calculateTUIDsFromEObjects(_list);
    TUID newTUID = CollectionBridge.<TUID>claimOne(_calculateTUIDsFromEObjects);
    this.updateTUID(oldTUID, newTUID);
  }
  
  @Override
  public void updateTUID(final TUID oldTUID, final TUID newTUID) {
    boolean _xifexpression = false;
    if ((oldTUID != null)) {
      _xifexpression = oldTUID.equals(newTUID);
    } else {
      _xifexpression = (newTUID == null);
    }
    boolean sameTUID = _xifexpression;
    if ((sameTUID || (oldTUID == null))) {
      return;
    }
    String oldTUIDString = oldTUID.toString();
    final Function1<TUID, Triple<TUID, String, Map<List<TUID>, Set<Correspondence>>>> _function = (TUID oldCurrentTUID) -> {
      final HashMap<List<TUID>, Set<Correspondence>> oldTUIDList2Correspondences = new HashMap<List<TUID>, Set<Correspondence>>();
      final Set<List<TUID>> oldTUIDLists = this.tuid2tuidListsMap.remove(oldCurrentTUID);
      for (final List<TUID> oldTUIDList : oldTUIDLists) {
        {
          final Set<Correspondence> correspondencesForOldTUIDList = this.tuid2CorrespondencesMap.remove(oldTUIDList);
          oldTUIDList2Correspondences.put(oldTUIDList, correspondencesForOldTUIDList);
        }
      }
      String _string = oldCurrentTUID.toString();
      return new Triple<TUID, String, Map<List<TUID>, Set<Correspondence>>>(oldCurrentTUID, _string, oldTUIDList2Correspondences);
    };
    TUID.BeforeHashCodeUpdateLambda before = ((TUID.BeforeHashCodeUpdateLambda) new TUID.BeforeHashCodeUpdateLambda() {
        public Triple<TUID, String, Map<List<TUID>, Set<Correspondence>>> performPreAction(TUID oldTUID) {
          return _function.apply(oldTUID);
        }
    });
    final Function1<Triple<TUID, String, Map<List<TUID>, Set<Correspondence>>>, Set<List<TUID>>> _function_1 = (Triple<TUID, String, Map<List<TUID>, Set<Correspondence>>> removedMapEntry) -> {
      Set<List<TUID>> _xblockexpression = null;
      {
        final TUID oldCurrentTUID = removedMapEntry.getFirst();
        final String oldCurrentTUIDString = removedMapEntry.getSecond();
        final Map<List<TUID>, Set<Correspondence>> oldTUIDList2Correspondences = removedMapEntry.getThird();
        final Set<Map.Entry<List<TUID>, Set<Correspondence>>> oldTUIDList2CorrespondencesEntries = oldTUIDList2Correspondences.entrySet();
        final HashSet<List<TUID>> newSetOfoldTUIDLists = new HashSet<List<TUID>>();
        for (final Map.Entry<List<TUID>, Set<Correspondence>> oldTUIDList2CorrespondencesEntry : oldTUIDList2CorrespondencesEntries) {
          {
            final List<TUID> oldTUIDList = oldTUIDList2CorrespondencesEntry.getKey();
            final Set<Correspondence> correspondences = oldTUIDList2CorrespondencesEntry.getValue();
            int indexOfOldTUID = (-1);
            for (final TUID tuid : oldTUIDList) {
              boolean _equals = false;
              if (oldCurrentTUIDString!=null) {
                String _string = tuid.toString();
                _equals=oldCurrentTUIDString.equals(_string);
              }
              if (_equals) {
                int _indexOf = oldTUIDList.indexOf(tuid);
                indexOfOldTUID = _indexOf;
              }
            }
            if ((indexOfOldTUID == (-1))) {
              throw new RuntimeException(((("No TUID in the List \'" + oldTUIDList) + "\' is equal to \'") + oldCurrentTUIDString));
            } else {
              oldTUIDList.set(indexOfOldTUID, oldCurrentTUID);
            }
            this.tuid2CorrespondencesMap.put(oldTUIDList, correspondences);
            for (final Correspondence correspondence : correspondences) {
              {
                EList<TUID> _aTUIDs = correspondence.getATUIDs();
                final TUID replacedATUID = CollectionBridge.<TUID>replaceFirstStringEqualElement(_aTUIDs, oldCurrentTUIDString, oldCurrentTUID);
                EList<TUID> _bTUIDs = correspondence.getBTUIDs();
                final TUID replacedBTUID = CollectionBridge.<TUID>replaceFirstStringEqualElement(_bTUIDs, oldCurrentTUIDString, oldCurrentTUID);
                boolean _and = false;
                boolean _equals_1 = Objects.equal(replacedATUID, null);
                if (!_equals_1) {
                  _and = false;
                } else {
                  boolean _equals_2 = Objects.equal(replacedBTUID, null);
                  _and = _equals_2;
                }
                if (_and) {
                  StringConcatenation _builder = new StringConcatenation();
                  _builder.append("None of the corresponding elements in \'");
                  _builder.append(correspondence, "");
                  _builder.append("\' has a TUID equal to \'");
                  _builder.append(oldCurrentTUIDString, "");
                  _builder.append("\'!");
                  throw new RuntimeException(_builder.toString());
                } else {
                  boolean _and_1 = false;
                  boolean _notEquals = (!Objects.equal(replacedATUID, null));
                  if (!_notEquals) {
                    _and_1 = false;
                  } else {
                    boolean _notEquals_1 = (!Objects.equal(replacedBTUID, null));
                    _and_1 = _notEquals_1;
                  }
                  if (_and_1) {
                    StringConcatenation _builder_1 = new StringConcatenation();
                    _builder_1.append("At least an a element and a b element of the correspondence \'");
                    _builder_1.append(correspondence, "");
                    _builder_1.append("\' have \'");
                    _builder_1.append(oldCurrentTUID, "");
                    _builder_1.append("\'!");
                    throw new RuntimeException(_builder_1.toString());
                  }
                }
                boolean _and_2 = false;
                if (!(oldCurrentTUIDString != null)) {
                  _and_2 = false;
                } else {
                  TUID _elementATUID = correspondence.getElementATUID();
                  String _string_1 = _elementATUID.toString();
                  boolean _equals_3 = oldCurrentTUIDString.equals(_string_1);
                  _and_2 = _equals_3;
                }
                if (_and_2) {
                  correspondence.setElementATUID(oldCurrentTUID);
                } else {
                  boolean _and_3 = false;
                  if (!(oldCurrentTUIDString != null)) {
                    _and_3 = false;
                  } else {
                    TUID _elementBTUID = correspondence.getElementBTUID();
                    String _string_2 = _elementBTUID.toString();
                    boolean _equals_4 = oldCurrentTUIDString.equals(_string_2);
                    _and_3 = _equals_4;
                  }
                  if (_and_3) {
                    correspondence.setElementBTUID(oldCurrentTUID);
                  } else {
                    boolean _or = false;
                    if ((oldCurrentTUID == null)) {
                      _or = true;
                    } else {
                      boolean _and_4 = false;
                      TUID _elementATUID_1 = correspondence.getElementATUID();
                      boolean _equals_5 = oldCurrentTUID.equals(_elementATUID_1);
                      boolean _not = (!_equals_5);
                      if (!_not) {
                        _and_4 = false;
                      } else {
                        TUID _elementBTUID_1 = correspondence.getElementBTUID();
                        boolean _equals_6 = oldCurrentTUID.equals(_elementBTUID_1);
                        boolean _not_1 = (!_equals_6);
                        _and_4 = _not_1;
                      }
                      _or = _and_4;
                    }
                    if (_or) {
                    }
                  }
                }
              }
            }
          }
        }
        _xblockexpression = this.tuid2tuidListsMap.put(oldCurrentTUID, newSetOfoldTUIDLists);
      }
      return _xblockexpression;
    };
    TUID.AfterHashCodeUpdateLambda after = ((TUID.AfterHashCodeUpdateLambda) new TUID.AfterHashCodeUpdateLambda() {
        public void performPostAction(Triple<TUID, String, Map<List<TUID>, Set<Correspondence>>> removedMapEntries) {
          _function_1.apply(removedMapEntries);
        }
    });
    oldTUID.renameSegments(newTUID, before, after);
    Metamodel metamodel = this.getMetamodelHavingTUID(oldTUIDString);
    metamodel.removeIfRootAndCached(oldTUIDString);
  }
}
