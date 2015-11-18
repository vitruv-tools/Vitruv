package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend.lib.annotations.Delegate;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public abstract class AbstractDelegatingCorrespondenceInstanceDecorator<D extends Object> implements CorrespondenceInstanceDecorator {
  @Delegate
  protected CorrespondenceInstanceDecorator correspondenceInstance;
  
  private final Class<D> decoratorObjectType;
  
  public AbstractDelegatingCorrespondenceInstanceDecorator(final CorrespondenceInstanceDecorator correspondenceInstance, final Class<D> decoratorObjectType) {
    this.correspondenceInstance = correspondenceInstance;
    this.decoratorObjectType = decoratorObjectType;
  }
  
  protected abstract String getDecoratorFileExtPrefix();
  
  protected abstract D getDecoratorObject();
  
  protected abstract void initializeFromDecoratorObject(final D object);
  
  protected abstract void initializeWithoutDecoratorObject();
  
  @Override
  public Correspondence createAndAddCorrespondence(final List<EObject> as, final List<EObject> bs) {
    return this.correspondenceInstance.createAndAddCorrespondence(as, bs);
  }
  
  @Override
  public Map<String, Object> getFileExtPrefix2ObjectMapForSave() {
    Map<String, Object> fileExtPrefix2ObjectMap = this.correspondenceInstance.getFileExtPrefix2ObjectMapForSave();
    String _decoratorFileExtPrefix = this.getDecoratorFileExtPrefix();
    D _decoratorObject = this.getDecoratorObject();
    fileExtPrefix2ObjectMap.put(_decoratorFileExtPrefix, _decoratorObject);
    return fileExtPrefix2ObjectMap;
  }
  
  @Override
  public Set<String> getFileExtPrefixesForObjectsToLoad() {
    Set<String> fileExtPrefixes = this.correspondenceInstance.getFileExtPrefixesForObjectsToLoad();
    String _decoratorFileExtPrefix = this.getDecoratorFileExtPrefix();
    fileExtPrefixes.add(_decoratorFileExtPrefix);
    return fileExtPrefixes;
  }
  
  @Override
  public void initialize(final Map<String, Object> fileExtPrefix2ObjectMap) {
    String _decoratorFileExtPrefix = this.getDecoratorFileExtPrefix();
    Object object = fileExtPrefix2ObjectMap.get(_decoratorFileExtPrefix);
    boolean _isInstance = this.decoratorObjectType.isInstance(object);
    if (_isInstance) {
      D _cast = this.decoratorObjectType.cast(object);
      this.initializeFromDecoratorObject(_cast);
    } else {
      if ((object == null)) {
        this.initializeWithoutDecoratorObject();
      } else {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("Cannot initialize decorator \'");
        _builder.append(this, "");
        _builder.append("\' with the decorator object \'");
        _builder.append(object, "");
        _builder.append("\' because it is not an instance of \'");
        _builder.append(this.decoratorObjectType, "");
        _builder.append("\'!");
        throw new RuntimeException(_builder.toString());
      }
    }
  }
  
  @Override
  public <T extends CorrespondenceInstanceDecorator> T getFirstCorrespondenceInstanceDecoratorOfTypeInChain(final Class<T> type) {
    boolean _isInstance = type.isInstance(this);
    if (_isInstance) {
      return type.cast(this);
    } else {
      if ((this.correspondenceInstance == null)) {
        return null;
      } else {
        return this.correspondenceInstance.<T>getFirstCorrespondenceInstanceDecoratorOfTypeInChain(type);
      }
    }
  }
  
  public boolean changedAfterLastSave() {
    return this.correspondenceInstance.changedAfterLastSave();
  }
  
  public Resource getResource() {
    return this.correspondenceInstance.getResource();
  }
  
  public VURI getURI() {
    return this.correspondenceInstance.getURI();
  }
  
  public void resetChangedAfterLastSave() {
    this.correspondenceInstance.resetChangedAfterLastSave();
  }
  
  public void addCorrespondence(final Correspondence correspondence) {
    this.correspondenceInstance.addCorrespondence(correspondence);
  }
  
  public TUID calculateTUIDFromEObject(final EObject eObject) {
    return this.correspondenceInstance.calculateTUIDFromEObject(eObject);
  }
  
  public List<TUID> calculateTUIDsFromEObjects(final List<EObject> eObjects) {
    return this.correspondenceInstance.calculateTUIDsFromEObjects(eObjects);
  }
  
  public Correspondence claimUniqueCorrespondence(final List<EObject> aEObjects, final List<EObject> bEObjects) {
    return this.correspondenceInstance.claimUniqueCorrespondence(aEObjects, bEObjects);
  }
  
  public Set<Correspondence> getCorrespondences(final List<EObject> eObjects) {
    return this.correspondenceInstance.getCorrespondences(eObjects);
  }
  
  public Set<Correspondence> getCorrespondencesForTUIDs(final List<TUID> tuids) {
    return this.correspondenceInstance.getCorrespondencesForTUIDs(tuids);
  }
  
  public Set<List<EObject>> getCorrespondingEObjects(final List<EObject> eObjects) {
    return this.correspondenceInstance.getCorrespondingEObjects(eObjects);
  }
  
  public Mapping getMapping() {
    return this.correspondenceInstance.getMapping();
  }
  
  public boolean hasCorrespondences(final List<EObject> eObject) {
    return this.correspondenceInstance.hasCorrespondences(eObject);
  }
  
  public boolean hasCorrespondences() {
    return this.correspondenceInstance.hasCorrespondences();
  }
  
  public Set<Correspondence> removeCorrespondencesOfEObjectAndChildrenOnBothSides(final EObject eObject) {
    return this.correspondenceInstance.removeCorrespondencesOfEObjectAndChildrenOnBothSides(eObject);
  }
  
  public Set<Correspondence> removeCorrespondencesOfEObjectAndChildrenOnBothSides(final TUID tuid) {
    return this.correspondenceInstance.removeCorrespondencesOfEObjectAndChildrenOnBothSides(tuid);
  }
  
  public Set<Correspondence> removeCorrespondencesOfEObjectsAndChildrenOnBothSides(final Correspondence correspondence) {
    return this.correspondenceInstance.removeCorrespondencesOfEObjectsAndChildrenOnBothSides(correspondence);
  }
  
  public EObject resolveEObjectFromRootAndFullTUID(final EObject root, final String tuidString) {
    return this.correspondenceInstance.resolveEObjectFromRootAndFullTUID(root, tuidString);
  }
  
  public EObject resolveEObjectFromTUID(final TUID tuid) {
    return this.correspondenceInstance.resolveEObjectFromTUID(tuid);
  }
  
  public List<EObject> resolveEObjectsFromTUIDs(final List<TUID> tuids) {
    return this.correspondenceInstance.resolveEObjectsFromTUIDs(tuids);
  }
  
  public Set<List<EObject>> resolveEObjectsSetsFromTUIDsSets(final Set<List<TUID>> tuidLists) {
    return this.correspondenceInstance.resolveEObjectsSetsFromTUIDsSets(tuidLists);
  }
  
  public void updateTUID(final EObject oldEObject, final EObject newEObject) {
    this.correspondenceInstance.updateTUID(oldEObject, newEObject);
  }
  
  public void updateTUID(final TUID oldTUID, final EObject newEObject) {
    this.correspondenceInstance.updateTUID(oldTUID, newEObject);
  }
  
  public void updateTUID(final TUID oldTUID, final TUID newTUID) {
    this.correspondenceInstance.updateTUID(oldTUID, newTUID);
  }
}
