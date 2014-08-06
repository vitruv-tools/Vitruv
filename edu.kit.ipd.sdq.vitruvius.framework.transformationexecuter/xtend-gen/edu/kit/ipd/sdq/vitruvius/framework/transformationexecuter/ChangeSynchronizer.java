package edu.kit.ipd.sdq.vitruvius.framework.transformationexecuter;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableHashMap;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableMap;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEFeature;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetNonContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.InsertEAttributeValue;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.PermuteEAttributeValues;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.RemoveEAttributeValue;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.ReplaceEAttributeValue;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.InsertNonContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.PermuteNonContainmentEReferenceValues;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.RemoveNonContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.ReplaceNonContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateSingleValuedNonContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectSingle;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectSingle;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.PermuteContainmentEReferenceValues;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ReplaceNonRootEObjectInList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ReplaceNonRootEObjectSingle;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.DeleteRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ReplaceRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.transformationexecuter.EObjectMappingTransformation;
import java.util.Arrays;
import java.util.Collection;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

@SuppressWarnings("all")
public class ChangeSynchronizer {
  private final static Logger logger = Logger.getLogger(ChangeSynchronizer.class.getSimpleName());
  
  private final ClaimableMap<Class<?>, EObjectMappingTransformation> mappingTransformations;
  
  private CorrespondenceInstance correspondenceInstance;
  
  public ChangeSynchronizer() {
    ClaimableHashMap<Class<?>, EObjectMappingTransformation> _claimableHashMap = new ClaimableHashMap<Class<?>, EObjectMappingTransformation>();
    this.mappingTransformations = _claimableHashMap;
  }
  
  public void setCorrespondenceInstance(final CorrespondenceInstance correspondenceInstance) {
    this.correspondenceInstance = correspondenceInstance;
    Collection<EObjectMappingTransformation> _values = this.mappingTransformations.values();
    for (final EObjectMappingTransformation mappingTransformation : _values) {
      mappingTransformation.setCorrespondenceInstance(correspondenceInstance);
    }
  }
  
  public TransformationChangeResult synchronizeChange(final EChange change) {
    final TransformationChangeResult transformationChangeResult = this.syncChange(change);
    this.updateTUID(change);
    return transformationChangeResult;
  }
  
  private void updateTUID(final EChange change) {
    if ((change instanceof EFeatureChange<?>)) {
      final EFeatureChange<?> eFeatureChange = ((EFeatureChange<?>) change);
      EObject _oldAffectedEObject = eFeatureChange.getOldAffectedEObject();
      EObject _newAffectedEObject = eFeatureChange.getNewAffectedEObject();
      this.correspondenceInstance.update(_oldAffectedEObject, _newAffectedEObject);
    }
  }
  
  private TransformationChangeResult _syncChange(final EChange change) {
    ChangeSynchronizer.logger.error((("No syncChang method found for change " + change) + ". Change not synchronized"));
    return null;
  }
  
  private TransformationChangeResult _syncChange(final CreateRootEObject<?> createRootEObject) {
    TransformationChangeResult _xblockexpression = null;
    {
      EObject _newValue = createRootEObject.getNewValue();
      Class<? extends EObject> _class = _newValue.getClass();
      EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
      EObject _newValue_1 = createRootEObject.getNewValue();
      final EObject[] createdObjects = _claimForMappedClassOrImplementingInterface.createEObject(_newValue_1);
      EObject _newValue_2 = createRootEObject.getNewValue();
      Class<? extends EObject> _class_1 = _newValue_2.getClass();
      EObjectMappingTransformation _claimForMappedClassOrImplementingInterface_1 = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_1);
      EObject _newValue_3 = createRootEObject.getNewValue();
      _xblockexpression = _claimForMappedClassOrImplementingInterface_1.createRootEObject(_newValue_3, createdObjects);
    }
    return _xblockexpression;
  }
  
  private TransformationChangeResult _syncChange(final DeleteRootEObject<?> deleteRootEObject) {
    TransformationChangeResult _xblockexpression = null;
    {
      EObject _oldValue = deleteRootEObject.getOldValue();
      Class<? extends EObject> _class = _oldValue.getClass();
      EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
      EObject _oldValue_1 = deleteRootEObject.getOldValue();
      final EObject[] removedEObjects = _claimForMappedClassOrImplementingInterface.removeEObject(_oldValue_1);
      EObject _oldValue_2 = deleteRootEObject.getOldValue();
      Class<? extends EObject> _class_1 = _oldValue_2.getClass();
      EObjectMappingTransformation _claimForMappedClassOrImplementingInterface_1 = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_1);
      EObject _oldValue_3 = deleteRootEObject.getOldValue();
      _xblockexpression = _claimForMappedClassOrImplementingInterface_1.deleteRootEObject(_oldValue_3, removedEObjects);
    }
    return _xblockexpression;
  }
  
  private TransformationChangeResult _syncChange(final ReplaceRootEObject<?> replaceRootEObject) {
    TransformationChangeResult _xblockexpression = null;
    {
      EObject _newValue = replaceRootEObject.getNewValue();
      Class<? extends EObject> _class = _newValue.getClass();
      EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
      EObject _newValue_1 = replaceRootEObject.getNewValue();
      final EObject[] createdObjects = _claimForMappedClassOrImplementingInterface.createEObject(_newValue_1);
      EObject _oldValue = replaceRootEObject.getOldValue();
      Class<? extends EObject> _class_1 = _oldValue.getClass();
      EObjectMappingTransformation _claimForMappedClassOrImplementingInterface_1 = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_1);
      EObject _oldValue_1 = replaceRootEObject.getOldValue();
      final EObject[] removedEObjects = _claimForMappedClassOrImplementingInterface_1.removeEObject(_oldValue_1);
      EObject _newValue_2 = replaceRootEObject.getNewValue();
      Class<? extends EObject> _class_2 = _newValue_2.getClass();
      EObjectMappingTransformation _claimForMappedClassOrImplementingInterface_2 = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_2);
      EObject _oldValue_2 = replaceRootEObject.getOldValue();
      EObject _newValue_3 = replaceRootEObject.getNewValue();
      _xblockexpression = _claimForMappedClassOrImplementingInterface_2.replaceRoot(_oldValue_2, _newValue_3, removedEObjects, createdObjects);
    }
    return _xblockexpression;
  }
  
  private TransformationChangeResult _syncChange(final CreateNonRootEObjectInList<?> createNonRootEObjectInList) {
    TransformationChangeResult _xblockexpression = null;
    {
      EObject _newValue = createNonRootEObjectInList.getNewValue();
      Class<? extends EObject> _class = _newValue.getClass();
      this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
      EObject _oldAffectedEObject = createNonRootEObjectInList.getOldAffectedEObject();
      Class<? extends EObject> _class_1 = _oldAffectedEObject.getClass();
      this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_1);
      EObject _newValue_1 = createNonRootEObjectInList.getNewValue();
      Class<? extends EObject> _class_2 = _newValue_1.getClass();
      EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_2);
      EObject _newValue_2 = createNonRootEObjectInList.getNewValue();
      final EObject[] createdEObjects = _claimForMappedClassOrImplementingInterface.createEObject(_newValue_2);
      EObject _oldAffectedEObject_1 = createNonRootEObjectInList.getOldAffectedEObject();
      Class<? extends EObject> _class_3 = _oldAffectedEObject_1.getClass();
      EObjectMappingTransformation _claimForMappedClassOrImplementingInterface_1 = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_3);
      EObject _oldAffectedEObject_2 = createNonRootEObjectInList.getOldAffectedEObject();
      EReference _affectedFeature = createNonRootEObjectInList.getAffectedFeature();
      EObject _newValue_3 = createNonRootEObjectInList.getNewValue();
      int _index = createNonRootEObjectInList.getIndex();
      _xblockexpression = _claimForMappedClassOrImplementingInterface_1.createNonRootEObjectInList(_oldAffectedEObject_2, _affectedFeature, _newValue_3, _index, createdEObjects);
    }
    return _xblockexpression;
  }
  
  private TransformationChangeResult _syncChange(final DeleteNonRootEObjectInList<?> deleteNonRootEObjectInList) {
    TransformationChangeResult _xblockexpression = null;
    {
      EObject _oldValue = deleteNonRootEObjectInList.getOldValue();
      Class<? extends EObject> _class = _oldValue.getClass();
      this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
      EObject _oldAffectedEObject = deleteNonRootEObjectInList.getOldAffectedEObject();
      Class<? extends EObject> _class_1 = _oldAffectedEObject.getClass();
      this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_1);
      EObject _oldValue_1 = deleteNonRootEObjectInList.getOldValue();
      Class<? extends EObject> _class_2 = _oldValue_1.getClass();
      EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_2);
      EObject _oldValue_2 = deleteNonRootEObjectInList.getOldValue();
      final EObject[] eObjectsToDelete = _claimForMappedClassOrImplementingInterface.removeEObject(_oldValue_2);
      EObject _oldAffectedEObject_1 = deleteNonRootEObjectInList.getOldAffectedEObject();
      Class<? extends EObject> _class_3 = _oldAffectedEObject_1.getClass();
      EObjectMappingTransformation _claimForMappedClassOrImplementingInterface_1 = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_3);
      EObject _oldAffectedEObject_2 = deleteNonRootEObjectInList.getOldAffectedEObject();
      EReference _affectedFeature = deleteNonRootEObjectInList.getAffectedFeature();
      EObject _oldValue_3 = deleteNonRootEObjectInList.getOldValue();
      int _index = deleteNonRootEObjectInList.getIndex();
      _xblockexpression = _claimForMappedClassOrImplementingInterface_1.deleteNonRootEObjectInList(_oldAffectedEObject_2, _affectedFeature, _oldValue_3, _index, eObjectsToDelete);
    }
    return _xblockexpression;
  }
  
  private TransformationChangeResult _syncChange(final ReplaceNonRootEObjectInList<?> replaceNonRootEObjectInList) {
    TransformationChangeResult _xblockexpression = null;
    {
      EObject _newValue = replaceNonRootEObjectInList.getNewValue();
      Class<? extends EObject> _class = _newValue.getClass();
      this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
      EObject _oldValue = replaceNonRootEObjectInList.getOldValue();
      Class<? extends EObject> _class_1 = _oldValue.getClass();
      this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_1);
      EObject _oldAffectedEObject = replaceNonRootEObjectInList.getOldAffectedEObject();
      Class<? extends EObject> _class_2 = _oldAffectedEObject.getClass();
      this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_2);
      EObject _oldValue_1 = replaceNonRootEObjectInList.getOldValue();
      Class<? extends EObject> _class_3 = _oldValue_1.getClass();
      EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_3);
      EObject _oldAffectedEObject_1 = replaceNonRootEObjectInList.getOldAffectedEObject();
      final EObject[] eObjectsToDelete = _claimForMappedClassOrImplementingInterface.removeEObject(_oldAffectedEObject_1);
      EObject _newValue_1 = replaceNonRootEObjectInList.getNewValue();
      Class<? extends EObject> _class_4 = _newValue_1.getClass();
      EObjectMappingTransformation _claimForMappedClassOrImplementingInterface_1 = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_4);
      EObject _oldAffectedEObject_2 = replaceNonRootEObjectInList.getOldAffectedEObject();
      final EObject[] createdEObjects = _claimForMappedClassOrImplementingInterface_1.createEObject(_oldAffectedEObject_2);
      EObject _newValue_2 = replaceNonRootEObjectInList.getNewValue();
      Class<? extends EObject> _class_5 = _newValue_2.getClass();
      EObjectMappingTransformation _claimForMappedClassOrImplementingInterface_2 = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_5);
      EObject _oldAffectedEObject_3 = replaceNonRootEObjectInList.getOldAffectedEObject();
      EReference _affectedFeature = replaceNonRootEObjectInList.getAffectedFeature();
      EObject _oldValue_2 = replaceNonRootEObjectInList.getOldValue();
      EObject _newValue_3 = replaceNonRootEObjectInList.getNewValue();
      int _index = replaceNonRootEObjectInList.getIndex();
      _xblockexpression = _claimForMappedClassOrImplementingInterface_2.replaceNonRootEObjectInList(_oldAffectedEObject_3, _affectedFeature, _oldValue_2, _newValue_3, _index, eObjectsToDelete, createdEObjects);
    }
    return _xblockexpression;
  }
  
  private TransformationChangeResult _syncChange(final CreateNonRootEObjectSingle<?> createNonRootEObjectSingle) {
    TransformationChangeResult _xblockexpression = null;
    {
      EObject _newValue = createNonRootEObjectSingle.getNewValue();
      Class<? extends EObject> _class = _newValue.getClass();
      this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
      EObject _oldAffectedEObject = createNonRootEObjectSingle.getOldAffectedEObject();
      Class<? extends EObject> _class_1 = _oldAffectedEObject.getClass();
      this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_1);
      EObject _newValue_1 = createNonRootEObjectSingle.getNewValue();
      Class<? extends EObject> _class_2 = _newValue_1.getClass();
      EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_2);
      EObject _newValue_2 = createNonRootEObjectSingle.getNewValue();
      final EObject[] createdEObjects = _claimForMappedClassOrImplementingInterface.createEObject(_newValue_2);
      EObject _oldAffectedEObject_1 = createNonRootEObjectSingle.getOldAffectedEObject();
      Class<? extends EObject> _class_3 = _oldAffectedEObject_1.getClass();
      EObjectMappingTransformation _claimForMappedClassOrImplementingInterface_1 = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_3);
      EObject _oldAffectedEObject_2 = createNonRootEObjectSingle.getOldAffectedEObject();
      EReference _affectedFeature = createNonRootEObjectSingle.getAffectedFeature();
      EObject _newValue_3 = createNonRootEObjectSingle.getNewValue();
      _xblockexpression = _claimForMappedClassOrImplementingInterface_1.createNonRootEObjectSingle(_oldAffectedEObject_2, _affectedFeature, _newValue_3, createdEObjects);
    }
    return _xblockexpression;
  }
  
  private TransformationChangeResult _syncChange(final DeleteNonRootEObjectSingle<?> deleteNonRootEObjectSingle) {
    TransformationChangeResult _xblockexpression = null;
    {
      EObject _oldValue = deleteNonRootEObjectSingle.getOldValue();
      Class<? extends EObject> _class = _oldValue.getClass();
      this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
      EObject _oldAffectedEObject = deleteNonRootEObjectSingle.getOldAffectedEObject();
      Class<? extends EObject> _class_1 = _oldAffectedEObject.getClass();
      this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_1);
      EObject _oldValue_1 = deleteNonRootEObjectSingle.getOldValue();
      Class<? extends EObject> _class_2 = _oldValue_1.getClass();
      EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_2);
      EObject _oldValue_2 = deleteNonRootEObjectSingle.getOldValue();
      final EObject[] eObjectsToDelete = _claimForMappedClassOrImplementingInterface.removeEObject(_oldValue_2);
      EObject _oldAffectedEObject_1 = deleteNonRootEObjectSingle.getOldAffectedEObject();
      Class<? extends EObject> _class_3 = _oldAffectedEObject_1.getClass();
      EObjectMappingTransformation _claimForMappedClassOrImplementingInterface_1 = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_3);
      EObject _oldAffectedEObject_2 = deleteNonRootEObjectSingle.getOldAffectedEObject();
      EReference _affectedFeature = deleteNonRootEObjectSingle.getAffectedFeature();
      EObject _oldValue_3 = deleteNonRootEObjectSingle.getOldValue();
      _xblockexpression = _claimForMappedClassOrImplementingInterface_1.deleteNonRootEObjectSingle(_oldAffectedEObject_2, _affectedFeature, _oldValue_3, eObjectsToDelete);
    }
    return _xblockexpression;
  }
  
  private TransformationChangeResult _syncChange(final ReplaceNonRootEObjectSingle<?> replaceNonRootEObjectSingle) {
    TransformationChangeResult _xblockexpression = null;
    {
      EObject _newValue = replaceNonRootEObjectSingle.getNewValue();
      Class<? extends EObject> _class = _newValue.getClass();
      this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
      EObject _oldValue = replaceNonRootEObjectSingle.getOldValue();
      Class<? extends EObject> _class_1 = _oldValue.getClass();
      this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_1);
      EObject _oldAffectedEObject = replaceNonRootEObjectSingle.getOldAffectedEObject();
      Class<? extends EObject> _class_2 = _oldAffectedEObject.getClass();
      this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_2);
      EObject _oldValue_1 = replaceNonRootEObjectSingle.getOldValue();
      Class<? extends EObject> _class_3 = _oldValue_1.getClass();
      EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_3);
      EObject _oldAffectedEObject_1 = replaceNonRootEObjectSingle.getOldAffectedEObject();
      final EObject[] createdEObjects = _claimForMappedClassOrImplementingInterface.removeEObject(_oldAffectedEObject_1);
      EObject _newValue_1 = replaceNonRootEObjectSingle.getNewValue();
      Class<? extends EObject> _class_4 = _newValue_1.getClass();
      EObjectMappingTransformation _claimForMappedClassOrImplementingInterface_1 = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_4);
      EObject _oldAffectedEObject_2 = replaceNonRootEObjectSingle.getOldAffectedEObject();
      final EObject[] eObjectsToDelete = _claimForMappedClassOrImplementingInterface_1.createEObject(_oldAffectedEObject_2);
      EObject _newValue_2 = replaceNonRootEObjectSingle.getNewValue();
      Class<? extends EObject> _class_5 = _newValue_2.getClass();
      EObjectMappingTransformation _claimForMappedClassOrImplementingInterface_2 = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_5);
      EObject _oldAffectedEObject_3 = replaceNonRootEObjectSingle.getOldAffectedEObject();
      EReference _affectedFeature = replaceNonRootEObjectSingle.getAffectedFeature();
      EObject _oldValue_2 = replaceNonRootEObjectSingle.getOldValue();
      EObject _newValue_3 = replaceNonRootEObjectSingle.getNewValue();
      _xblockexpression = _claimForMappedClassOrImplementingInterface_2.replaceNonRootEObjectSingle(_oldAffectedEObject_3, _affectedFeature, _oldValue_2, _newValue_3, eObjectsToDelete, createdEObjects);
    }
    return _xblockexpression;
  }
  
  private TransformationChangeResult _syncChange(final PermuteContainmentEReferenceValues<?> permuteContainmentEReferenceValues) {
    EObject _oldAffectedEObject = permuteContainmentEReferenceValues.getOldAffectedEObject();
    Class<? extends EObject> _class = _oldAffectedEObject.getClass();
    EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
    EObject _oldAffectedEObject_1 = permuteContainmentEReferenceValues.getOldAffectedEObject();
    EReference _affectedFeature = permuteContainmentEReferenceValues.getAffectedFeature();
    EList<Integer> _newIndexForElementAt = permuteContainmentEReferenceValues.getNewIndexForElementAt();
    return _claimForMappedClassOrImplementingInterface.permuteContainmentEReferenceValues(_oldAffectedEObject_1, _affectedFeature, _newIndexForElementAt);
  }
  
  private TransformationChangeResult _syncChange(final InsertNonContainmentEReference<?> insertNonContaimentEReference) {
    EObject _oldAffectedEObject = insertNonContaimentEReference.getOldAffectedEObject();
    Class<? extends EObject> _class = _oldAffectedEObject.getClass();
    EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
    EObject _oldAffectedEObject_1 = insertNonContaimentEReference.getOldAffectedEObject();
    EReference _affectedFeature = insertNonContaimentEReference.getAffectedFeature();
    EObject _newValue = insertNonContaimentEReference.getNewValue();
    int _index = insertNonContaimentEReference.getIndex();
    return _claimForMappedClassOrImplementingInterface.insertNonContaimentEReference(_oldAffectedEObject_1, _affectedFeature, _newValue, _index);
  }
  
  private TransformationChangeResult _syncChange(final RemoveNonContainmentEReference<?> removeNonContainmentEReference) {
    EObject _oldAffectedEObject = removeNonContainmentEReference.getOldAffectedEObject();
    Class<? extends EObject> _class = _oldAffectedEObject.getClass();
    EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
    EObject _oldAffectedEObject_1 = removeNonContainmentEReference.getOldAffectedEObject();
    EReference _affectedFeature = removeNonContainmentEReference.getAffectedFeature();
    EObject _oldValue = removeNonContainmentEReference.getOldValue();
    int _index = removeNonContainmentEReference.getIndex();
    return _claimForMappedClassOrImplementingInterface.removeNonContainmentEReference(_oldAffectedEObject_1, _affectedFeature, _oldValue, _index);
  }
  
  private TransformationChangeResult _syncChange(final ReplaceNonContainmentEReference<?> replaceNonContainmentEReference) {
    EObject _oldAffectedEObject = replaceNonContainmentEReference.getOldAffectedEObject();
    Class<? extends EObject> _class = _oldAffectedEObject.getClass();
    EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
    EObject _oldAffectedEObject_1 = replaceNonContainmentEReference.getOldAffectedEObject();
    EReference _affectedFeature = replaceNonContainmentEReference.getAffectedFeature();
    EObject _oldValue = replaceNonContainmentEReference.getOldValue();
    EObject _newValue = replaceNonContainmentEReference.getNewValue();
    int _index = replaceNonContainmentEReference.getIndex();
    return _claimForMappedClassOrImplementingInterface.replaceNonContainmentEReference(_oldAffectedEObject_1, _affectedFeature, _oldValue, _newValue, _index);
  }
  
  private TransformationChangeResult _syncChange(final PermuteNonContainmentEReferenceValues<?> permuteNonContainmentEReferenceValues) {
    EObject _oldAffectedEObject = permuteNonContainmentEReferenceValues.getOldAffectedEObject();
    Class<? extends EObject> _class = _oldAffectedEObject.getClass();
    EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
    EObject _oldAffectedEObject_1 = permuteNonContainmentEReferenceValues.getOldAffectedEObject();
    EReference _affectedFeature = permuteNonContainmentEReferenceValues.getAffectedFeature();
    EList<Integer> _newIndexForElementAt = permuteNonContainmentEReferenceValues.getNewIndexForElementAt();
    return _claimForMappedClassOrImplementingInterface.permuteNonContainmentEReferenceValues(_oldAffectedEObject_1, _affectedFeature, _newIndexForElementAt);
  }
  
  private TransformationChangeResult _syncChange(final UpdateSingleValuedNonContainmentEReference<?> updateSingleValuedNonContainmentEReference) {
    EObject _oldAffectedEObject = updateSingleValuedNonContainmentEReference.getOldAffectedEObject();
    Class<? extends EObject> _class = _oldAffectedEObject.getClass();
    EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
    EObject _oldAffectedEObject_1 = updateSingleValuedNonContainmentEReference.getOldAffectedEObject();
    EReference _affectedFeature = updateSingleValuedNonContainmentEReference.getAffectedFeature();
    EObject _oldValue = updateSingleValuedNonContainmentEReference.getOldValue();
    EObject _newValue = updateSingleValuedNonContainmentEReference.getNewValue();
    return _claimForMappedClassOrImplementingInterface.updateSingleValuedNonContainmentEReference(_oldAffectedEObject_1, _affectedFeature, _oldValue, _newValue);
  }
  
  private TransformationChangeResult _syncChange(final UpdateSingleValuedEAttribute<?> updateSingleValuedEAttribute) {
    EObject _oldAffectedEObject = updateSingleValuedEAttribute.getOldAffectedEObject();
    Class<? extends EObject> _class = _oldAffectedEObject.getClass();
    EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
    EObject _oldAffectedEObject_1 = updateSingleValuedEAttribute.getOldAffectedEObject();
    EAttribute _affectedFeature = updateSingleValuedEAttribute.getAffectedFeature();
    Object _oldValue = updateSingleValuedEAttribute.getOldValue();
    Object _newValue = updateSingleValuedEAttribute.getNewValue();
    return _claimForMappedClassOrImplementingInterface.updateSingleValuedEAttribute(_oldAffectedEObject_1, _affectedFeature, _oldValue, _newValue);
  }
  
  private TransformationChangeResult _syncChange(final InsertEAttributeValue<?> insertEAttributeValue) {
    EObject _oldAffectedEObject = insertEAttributeValue.getOldAffectedEObject();
    Class<? extends EObject> _class = _oldAffectedEObject.getClass();
    EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
    EObject _oldAffectedEObject_1 = insertEAttributeValue.getOldAffectedEObject();
    EAttribute _affectedFeature = insertEAttributeValue.getAffectedFeature();
    Object _newValue = insertEAttributeValue.getNewValue();
    int _index = insertEAttributeValue.getIndex();
    return _claimForMappedClassOrImplementingInterface.insertEAttributeValue(_oldAffectedEObject_1, _affectedFeature, _newValue, _index);
  }
  
  private TransformationChangeResult _syncChange(final RemoveEAttributeValue<?> removeEAttributeValue) {
    EObject _oldAffectedEObject = removeEAttributeValue.getOldAffectedEObject();
    Class<? extends EObject> _class = _oldAffectedEObject.getClass();
    EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
    EObject _oldAffectedEObject_1 = removeEAttributeValue.getOldAffectedEObject();
    EAttribute _affectedFeature = removeEAttributeValue.getAffectedFeature();
    Object _oldValue = removeEAttributeValue.getOldValue();
    int _index = removeEAttributeValue.getIndex();
    return _claimForMappedClassOrImplementingInterface.removeEAttributeValue(_oldAffectedEObject_1, _affectedFeature, _oldValue, _index);
  }
  
  private TransformationChangeResult _syncChange(final ReplaceEAttributeValue<?> replaceEAttributeValue) {
    EObject _oldAffectedEObject = replaceEAttributeValue.getOldAffectedEObject();
    Class<? extends EObject> _class = _oldAffectedEObject.getClass();
    EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
    EObject _oldAffectedEObject_1 = replaceEAttributeValue.getOldAffectedEObject();
    EAttribute _affectedFeature = replaceEAttributeValue.getAffectedFeature();
    Object _oldValue = replaceEAttributeValue.getOldValue();
    Object _newValue = replaceEAttributeValue.getNewValue();
    int _index = replaceEAttributeValue.getIndex();
    return _claimForMappedClassOrImplementingInterface.replaceEAttributeValue(_oldAffectedEObject_1, _affectedFeature, _oldValue, _newValue, _index);
  }
  
  private TransformationChangeResult _syncChange(final PermuteEAttributeValues<?> permuteEAttributeValues) {
    EObject _oldAffectedEObject = permuteEAttributeValues.getOldAffectedEObject();
    Class<? extends EObject> _class = _oldAffectedEObject.getClass();
    EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
    EObject _oldAffectedEObject_1 = permuteEAttributeValues.getOldAffectedEObject();
    EAttribute _affectedFeature = permuteEAttributeValues.getAffectedFeature();
    EList<Integer> _newIndexForElementAt = permuteEAttributeValues.getNewIndexForElementAt();
    return _claimForMappedClassOrImplementingInterface.permuteEAttributeValues(_oldAffectedEObject_1, _affectedFeature, _newIndexForElementAt);
  }
  
  private TransformationChangeResult _syncChange(final UnsetEAttribute<?> unsetEAttribute) {
    EObject _oldAffectedEObject = unsetEAttribute.getOldAffectedEObject();
    Class<? extends EObject> _class = _oldAffectedEObject.getClass();
    EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
    EObject _oldAffectedEObject_1 = unsetEAttribute.getOldAffectedEObject();
    EAttribute _affectedFeature = unsetEAttribute.getAffectedFeature();
    Object _oldValue = unsetEAttribute.getOldValue();
    return _claimForMappedClassOrImplementingInterface.unsetEAttribute(_oldAffectedEObject_1, _affectedFeature, _oldValue);
  }
  
  private TransformationChangeResult _syncChange(final UnsetNonContainmentEReference<?> unsetNonContainmentEReference) {
    EObject _oldAffectedEObject = unsetNonContainmentEReference.getOldAffectedEObject();
    Class<? extends EObject> _class = _oldAffectedEObject.getClass();
    EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
    EObject _oldAffectedEObject_1 = unsetNonContainmentEReference.getOldAffectedEObject();
    EReference _affectedFeature = unsetNonContainmentEReference.getAffectedFeature();
    EObject _oldValue = unsetNonContainmentEReference.getOldValue();
    return _claimForMappedClassOrImplementingInterface.unsetNonContainmentEReference(_oldAffectedEObject_1, _affectedFeature, _oldValue);
  }
  
  private TransformationChangeResult _syncChange(final UnsetContainmentEReference<?> unsetContainmentEReference) {
    TransformationChangeResult _xblockexpression = null;
    {
      EObject _oldValue = unsetContainmentEReference.getOldValue();
      Class<? extends EObject> _class = _oldValue.getClass();
      EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
      EObject _oldValue_1 = unsetContainmentEReference.getOldValue();
      final EObject[] eObjectsToDelete = _claimForMappedClassOrImplementingInterface.removeEObject(_oldValue_1);
      EObject _oldAffectedEObject = unsetContainmentEReference.getOldAffectedEObject();
      Class<? extends EObject> _class_1 = _oldAffectedEObject.getClass();
      EObjectMappingTransformation _claimForMappedClassOrImplementingInterface_1 = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_1);
      EObject _oldAffectedEObject_1 = unsetContainmentEReference.getOldAffectedEObject();
      EReference _affectedFeature = unsetContainmentEReference.getAffectedFeature();
      EObject _oldValue_2 = unsetContainmentEReference.getOldValue();
      _xblockexpression = _claimForMappedClassOrImplementingInterface_1.unsetContainmentEReference(_oldAffectedEObject_1, _affectedFeature, _oldValue_2, eObjectsToDelete);
    }
    return _xblockexpression;
  }
  
  private TransformationChangeResult _syncChange(final UnsetEFeature<?> unsetEFeature) {
    ChangeSynchronizer.logger.error("syncChange for UnsetEFeature<?> is not implemented yet...");
    return null;
  }
  
  public void addMapping(final EObjectMappingTransformation transformation) {
    boolean _notEquals = (!Objects.equal(null, this.correspondenceInstance));
    if (_notEquals) {
      transformation.setCorrespondenceInstance(this.correspondenceInstance);
    }
    Class<?> _classOfMappedEObject = transformation.getClassOfMappedEObject();
    this.mappingTransformations.putClaimingNullOrSameMapped(_classOfMappedEObject, transformation);
  }
  
  private EObjectMappingTransformation claimForMappedClassOrImplementingInterface(final ClaimableMap<Class<?>, EObjectMappingTransformation> transformations, final Class<?> concreteInstance) {
    Class<?>[] _interfaces = concreteInstance.getInterfaces();
    for (final Class<?> concreteIf : _interfaces) {
      boolean _containsKey = transformations.containsKey(concreteIf);
      if (_containsKey) {
        return transformations.get(concreteIf);
      }
    }
    return transformations.claimValueForKey(concreteInstance);
  }
  
  private TransformationChangeResult syncChange(final EChange createNonRootEObjectSingle) {
    if (createNonRootEObjectSingle instanceof CreateNonRootEObjectSingle) {
      return _syncChange((CreateNonRootEObjectSingle<?>)createNonRootEObjectSingle);
    } else if (createNonRootEObjectSingle instanceof DeleteNonRootEObjectSingle) {
      return _syncChange((DeleteNonRootEObjectSingle<?>)createNonRootEObjectSingle);
    } else if (createNonRootEObjectSingle instanceof ReplaceNonRootEObjectSingle) {
      return _syncChange((ReplaceNonRootEObjectSingle<?>)createNonRootEObjectSingle);
    } else if (createNonRootEObjectSingle instanceof UnsetContainmentEReference) {
      return _syncChange((UnsetContainmentEReference<?>)createNonRootEObjectSingle);
    } else if (createNonRootEObjectSingle instanceof UnsetNonContainmentEReference) {
      return _syncChange((UnsetNonContainmentEReference<?>)createNonRootEObjectSingle);
    } else if (createNonRootEObjectSingle instanceof InsertEAttributeValue) {
      return _syncChange((InsertEAttributeValue<?>)createNonRootEObjectSingle);
    } else if (createNonRootEObjectSingle instanceof RemoveEAttributeValue) {
      return _syncChange((RemoveEAttributeValue<?>)createNonRootEObjectSingle);
    } else if (createNonRootEObjectSingle instanceof ReplaceEAttributeValue) {
      return _syncChange((ReplaceEAttributeValue<?>)createNonRootEObjectSingle);
    } else if (createNonRootEObjectSingle instanceof InsertNonContainmentEReference) {
      return _syncChange((InsertNonContainmentEReference<?>)createNonRootEObjectSingle);
    } else if (createNonRootEObjectSingle instanceof PermuteNonContainmentEReferenceValues) {
      return _syncChange((PermuteNonContainmentEReferenceValues<?>)createNonRootEObjectSingle);
    } else if (createNonRootEObjectSingle instanceof RemoveNonContainmentEReference) {
      return _syncChange((RemoveNonContainmentEReference<?>)createNonRootEObjectSingle);
    } else if (createNonRootEObjectSingle instanceof ReplaceNonContainmentEReference) {
      return _syncChange((ReplaceNonContainmentEReference<?>)createNonRootEObjectSingle);
    } else if (createNonRootEObjectSingle instanceof UpdateSingleValuedNonContainmentEReference) {
      return _syncChange((UpdateSingleValuedNonContainmentEReference<?>)createNonRootEObjectSingle);
    } else if (createNonRootEObjectSingle instanceof CreateNonRootEObjectInList) {
      return _syncChange((CreateNonRootEObjectInList<?>)createNonRootEObjectSingle);
    } else if (createNonRootEObjectSingle instanceof DeleteNonRootEObjectInList) {
      return _syncChange((DeleteNonRootEObjectInList<?>)createNonRootEObjectSingle);
    } else if (createNonRootEObjectSingle instanceof PermuteContainmentEReferenceValues) {
      return _syncChange((PermuteContainmentEReferenceValues<?>)createNonRootEObjectSingle);
    } else if (createNonRootEObjectSingle instanceof ReplaceNonRootEObjectInList) {
      return _syncChange((ReplaceNonRootEObjectInList<?>)createNonRootEObjectSingle);
    } else if (createNonRootEObjectSingle instanceof UnsetEAttribute) {
      return _syncChange((UnsetEAttribute<?>)createNonRootEObjectSingle);
    } else if (createNonRootEObjectSingle instanceof PermuteEAttributeValues) {
      return _syncChange((PermuteEAttributeValues<?>)createNonRootEObjectSingle);
    } else if (createNonRootEObjectSingle instanceof UpdateSingleValuedEAttribute) {
      return _syncChange((UpdateSingleValuedEAttribute<?>)createNonRootEObjectSingle);
    } else if (createNonRootEObjectSingle instanceof CreateRootEObject) {
      return _syncChange((CreateRootEObject<?>)createNonRootEObjectSingle);
    } else if (createNonRootEObjectSingle instanceof DeleteRootEObject) {
      return _syncChange((DeleteRootEObject<?>)createNonRootEObjectSingle);
    } else if (createNonRootEObjectSingle instanceof ReplaceRootEObject) {
      return _syncChange((ReplaceRootEObject<?>)createNonRootEObjectSingle);
    } else if (createNonRootEObjectSingle instanceof UnsetEFeature) {
      return _syncChange((UnsetEFeature<?>)createNonRootEObjectSingle);
    } else if (createNonRootEObjectSingle != null) {
      return _syncChange(createNonRootEObjectSingle);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(createNonRootEObjectSingle).toString());
    }
  }
}
