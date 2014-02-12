package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.EObjectMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableHashMap;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableMap;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.CreateNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.CreateRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.DeleteNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.DeleteRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.UnsetEFeature;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.UpdateEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.UpdateEContainmentReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.UpdateEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.impl.EObjectChangeImpl;
import java.util.Arrays;
import java.util.Set;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.reflections.Reflections;
import org.reflections.Store;

@SuppressWarnings("all")
public class ChangeSynchronizer {
  private final static Logger logger = new Function0<Logger>() {
    public Logger apply() {
      String _simpleName = ChangeSynchronizer.class.getSimpleName();
      Logger _logger = Logger.getLogger(_simpleName);
      return _logger;
    }
  }.apply();
  
  private final ClaimableMap<Class<? extends Object>,EObjectMappingTransformation> mappingTransformations;
  
  public ChangeSynchronizer() {
    ClaimableHashMap<Class<? extends Object>,EObjectMappingTransformation> _claimableHashMap = new ClaimableHashMap<Class<?>, EObjectMappingTransformation>();
    this.mappingTransformations = _claimableHashMap;
    this.fillTransformationMap();
  }
  
  public Object synchronizeChange(final EChange change) {
    Object _syncChange = this.syncChange(change);
    return _syncChange;
  }
  
  protected Object _syncChange(final EChange change) {
    String _plus = ("No syncChang method found for change " + change);
    String _plus_1 = (_plus + ". Change not synchronized");
    ChangeSynchronizer.logger.error(_plus_1);
    return null;
  }
  
  protected Object _syncChange(final CreateRootEObject createRootEObject) {
    Class<? extends CreateRootEObject> _class = createRootEObject.getClass();
    EObjectMappingTransformation _claimValueForKey = this.mappingTransformations.claimValueForKey(_class);
    EObject _changedEObject = createRootEObject.getChangedEObject();
    EObject _addEObject = _claimValueForKey.addEObject(_changedEObject);
    return _addEObject;
  }
  
  protected Object _syncChange(final DeleteRootEObject deleteRootEObject) {
    Class<? extends DeleteRootEObject> _class = deleteRootEObject.getClass();
    EObjectMappingTransformation _claimValueForKey = this.mappingTransformations.claimValueForKey(_class);
    EObject _changedEObject = deleteRootEObject.getChangedEObject();
    EObject _removeEObject = _claimValueForKey.removeEObject(_changedEObject);
    return _removeEObject;
  }
  
  protected EObject _syncChange(final CreateNonRootEObject<? extends Object> createNonRootEObject) {
    EObject _xblockexpression = null;
    {
      EObject _changedEObject = createNonRootEObject.getChangedEObject();
      Class<? extends EObject> _class = _changedEObject.getClass();
      this.mappingTransformations.claimKeyIsMapped(_class);
      EObject _affectedEObject = createNonRootEObject.getAffectedEObject();
      Class<? extends EObject> _class_1 = _affectedEObject.getClass();
      this.mappingTransformations.claimKeyIsMapped(_class_1);
      EObject _changedEObject_1 = createNonRootEObject.getChangedEObject();
      Class<? extends EObject> _class_2 = _changedEObject_1.getClass();
      EObjectMappingTransformation _get = this.mappingTransformations.get(_class_2);
      EObject _changedEObject_2 = createNonRootEObject.getChangedEObject();
      _get.addEObject(_changedEObject_2);
      EObject _affectedEObject_1 = createNonRootEObject.getAffectedEObject();
      Class<? extends EObject> _class_3 = _affectedEObject_1.getClass();
      EObjectMappingTransformation _get_1 = this.mappingTransformations.get(_class_3);
      EObject _affectedEObject_2 = createNonRootEObject.getAffectedEObject();
      EReference _affectedFeature = createNonRootEObject.getAffectedFeature();
      EObject _newValue = createNonRootEObject.getNewValue();
      EObject _updateEReference = _get_1.updateEReference(_affectedEObject_2, _affectedFeature, _newValue);
      _xblockexpression = (_updateEReference);
    }
    return _xblockexpression;
  }
  
  protected Object _syncChange(final DeleteNonRootEObject<? extends Object> deleteNonRootEObject) {
    EObject _xblockexpression = null;
    {
      EObject _changedEObject = deleteNonRootEObject.getChangedEObject();
      Class<? extends EObject> _class = _changedEObject.getClass();
      this.mappingTransformations.claimKeyIsMapped(_class);
      EObject _affectedEObject = deleteNonRootEObject.getAffectedEObject();
      Class<? extends EObject> _class_1 = _affectedEObject.getClass();
      this.mappingTransformations.claimKeyIsMapped(_class_1);
      EObject _changedEObject_1 = deleteNonRootEObject.getChangedEObject();
      Class<? extends EObject> _class_2 = _changedEObject_1.getClass();
      EObjectMappingTransformation _get = this.mappingTransformations.get(_class_2);
      EObject _changedEObject_2 = deleteNonRootEObject.getChangedEObject();
      _get.addEObject(_changedEObject_2);
      EObject _affectedEObject_1 = deleteNonRootEObject.getAffectedEObject();
      Class<? extends EObject> _class_3 = _affectedEObject_1.getClass();
      EObjectMappingTransformation _get_1 = this.mappingTransformations.get(_class_3);
      EObject _affectedEObject_2 = deleteNonRootEObject.getAffectedEObject();
      EReference _affectedFeature = deleteNonRootEObject.getAffectedFeature();
      EObject _newValue = deleteNonRootEObject.getNewValue();
      EObject _updateEReference = _get_1.updateEReference(_affectedEObject_2, _affectedFeature, _newValue);
      _xblockexpression = (_updateEReference);
    }
    return _xblockexpression;
  }
  
  protected Object _syncChange(final UpdateEAttribute<? extends Object> updateEAttribute) {
    EObject _affectedEObject = updateEAttribute.getAffectedEObject();
    Class<? extends EObject> _class = _affectedEObject.getClass();
    EObjectMappingTransformation _claimValueForKey = this.mappingTransformations.claimValueForKey(_class);
    EObject _affectedEObject_1 = updateEAttribute.getAffectedEObject();
    EAttribute _affectedFeature = updateEAttribute.getAffectedFeature();
    Object _newValue = updateEAttribute.getNewValue();
    EObject _updateEAttribute = _claimValueForKey.updateEAttribute(_affectedEObject_1, _affectedFeature, _newValue);
    return _updateEAttribute;
  }
  
  protected Object _syncChange(final UpdateEReference<? extends Object> updateEReference) {
    EObject _affectedEObject = updateEReference.getAffectedEObject();
    Class<? extends EObject> _class = _affectedEObject.getClass();
    EObjectMappingTransformation _claimValueForKey = this.mappingTransformations.claimValueForKey(_class);
    EObject _affectedEObject_1 = updateEReference.getAffectedEObject();
    EReference _affectedFeature = updateEReference.getAffectedFeature();
    EObject _newValue = updateEReference.getNewValue();
    EObject _updateEReference = _claimValueForKey.updateEReference(_affectedEObject_1, _affectedFeature, _newValue);
    return _updateEReference;
  }
  
  protected Object _syncChange(final UpdateEContainmentReference<? extends Object> updateEContainmentReference) {
    EObject _affectedEObject = updateEContainmentReference.getAffectedEObject();
    Class<? extends EObject> _class = _affectedEObject.getClass();
    EObjectMappingTransformation _claimValueForKey = this.mappingTransformations.claimValueForKey(_class);
    EObject _affectedEObject_1 = updateEContainmentReference.getAffectedEObject();
    EReference _affectedFeature = updateEContainmentReference.getAffectedFeature();
    EObject _newValue = updateEContainmentReference.getNewValue();
    EObject _updateEContainmentReference = _claimValueForKey.updateEContainmentReference(_affectedEObject_1, _affectedFeature, _newValue);
    return _updateEContainmentReference;
  }
  
  protected Object _syncChange(final UnsetEFeature<? extends Object> unsetEFeature) {
    ChangeSynchronizer.logger.error("syncChange for UnsetEFeature<?> is not implemented yet...");
    return null;
  }
  
  private void fillTransformationMap() {
    try {
      Reflections _reflections = new Reflections();
      final Reflections reflections = _reflections;
      Store _store = reflections.getStore();
      String _name = EObjectMappingTransformation.class.getName();
      final Set<String> transformationClasses = _store.getSubTypesOf(_name);
      for (final String transClassName : transformationClasses) {
        {
          final Class<?> currentClass = Class.forName(transClassName);
          final Object currentInstance = currentClass.newInstance();
          if ((currentInstance instanceof EObjectMappingTransformation)) {
            final EObjectMappingTransformation currentMappingTransformation = ((EObjectMappingTransformation) currentInstance);
            Class<? extends Object> _classOfMappedEObject = currentMappingTransformation.getClassOfMappedEObject();
            this.mappingTransformations.putClaimingNullOrSameMapped(_classOfMappedEObject, currentMappingTransformation);
          } else {
            String _plus = ("current instance " + currentInstance);
            String _plus_1 = (_plus + " is not an instance of ");
            String _name_1 = EObjectChangeImpl.class.getName();
            String _plus_2 = (_plus_1 + _name_1);
            String _plus_3 = (_plus_2 + " This should not happen...");
            ChangeSynchronizer.logger.warn(_plus_3);
          }
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public Object syncChange(final EChange createNonRootEObject) {
    if (createNonRootEObject instanceof CreateNonRootEObject) {
      return _syncChange((CreateNonRootEObject<?>)createNonRootEObject);
    } else if (createNonRootEObject instanceof DeleteNonRootEObject) {
      return _syncChange((DeleteNonRootEObject<?>)createNonRootEObject);
    } else if (createNonRootEObject instanceof UpdateEContainmentReference) {
      return _syncChange((UpdateEContainmentReference<?>)createNonRootEObject);
    } else if (createNonRootEObject instanceof CreateRootEObject) {
      return _syncChange((CreateRootEObject)createNonRootEObject);
    } else if (createNonRootEObject instanceof DeleteRootEObject) {
      return _syncChange((DeleteRootEObject)createNonRootEObject);
    } else if (createNonRootEObject instanceof UpdateEAttribute) {
      return _syncChange((UpdateEAttribute<?>)createNonRootEObject);
    } else if (createNonRootEObject instanceof UpdateEReference) {
      return _syncChange((UpdateEReference<?>)createNonRootEObject);
    } else if (createNonRootEObject instanceof UnsetEFeature) {
      return _syncChange((UnsetEFeature<?>)createNonRootEObject);
    } else if (createNonRootEObject != null) {
      return _syncChange(createNonRootEObject);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(createNonRootEObject).toString());
    }
  }
}
