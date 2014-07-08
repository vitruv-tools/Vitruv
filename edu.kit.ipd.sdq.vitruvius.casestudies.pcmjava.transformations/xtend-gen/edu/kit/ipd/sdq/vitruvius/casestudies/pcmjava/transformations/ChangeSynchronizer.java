package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.EObjectMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.ClassMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.InterfaceMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.PackageMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.BasicComponentMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.OperationInterfaceMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.RepositoryMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableHashMap;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableMap;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UnsetEFeature;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEContainmentReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEReference;
import java.util.Arrays;
import java.util.Collection;
import org.apache.log4j.Logger;
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
    this.fillTransformationMap();
  }
  
  public void setCorrespondenceInstance(final CorrespondenceInstance correspondenceInstance) {
    this.correspondenceInstance = correspondenceInstance;
    Collection<EObjectMappingTransformation> _values = this.mappingTransformations.values();
    for (final EObjectMappingTransformation mappingTransformation : _values) {
      mappingTransformation.setCorrespondenceInstance(correspondenceInstance);
    }
  }
  
  public EObject[] synchronizeChange(final EChange change) {
    return this.syncChange(change);
  }
  
  private EObject[] _syncChange(final EChange change) {
    ChangeSynchronizer.logger.error((("No syncChang method found for change " + change) + ". Change not synchronized"));
    return null;
  }
  
  private EObject[] _syncChange(final CreateRootEObject createRootEObject) {
    EObject _changedEObject = createRootEObject.getChangedEObject();
    Class<? extends EObject> _class = _changedEObject.getClass();
    EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
    EObject _changedEObject_1 = createRootEObject.getChangedEObject();
    return _claimForMappedClassOrImplementingInterface.addEObject(_changedEObject_1);
  }
  
  private EObject[] _syncChange(final DeleteRootEObject deleteRootEObject) {
    EObject _changedEObject = deleteRootEObject.getChangedEObject();
    Class<? extends EObject> _class = _changedEObject.getClass();
    EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
    EObject _changedEObject_1 = deleteRootEObject.getChangedEObject();
    return _claimForMappedClassOrImplementingInterface.removeEObject(_changedEObject_1);
  }
  
  private EObject[] _syncChange(final CreateNonRootEObject<?> createNonRootEObject) {
    EObject _changedEObject = createNonRootEObject.getChangedEObject();
    Class<? extends EObject> _class = _changedEObject.getClass();
    this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
    EObject _affectedEObject = createNonRootEObject.getAffectedEObject();
    Class<? extends EObject> _class_1 = _affectedEObject.getClass();
    this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_1);
    EObject _changedEObject_1 = createNonRootEObject.getChangedEObject();
    Class<? extends EObject> _class_2 = _changedEObject_1.getClass();
    EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_2);
    EObject _changedEObject_2 = createNonRootEObject.getChangedEObject();
    final EObject[] newNonRootObject = _claimForMappedClassOrImplementingInterface.addEObject(_changedEObject_2);
    EObject _affectedEObject_1 = createNonRootEObject.getAffectedEObject();
    Class<? extends EObject> _class_3 = _affectedEObject_1.getClass();
    EObjectMappingTransformation _claimForMappedClassOrImplementingInterface_1 = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_3);
    EObject _affectedEObject_2 = createNonRootEObject.getAffectedEObject();
    EReference _affectedFeature = createNonRootEObject.getAffectedFeature();
    Object _newValue = createNonRootEObject.getNewValue();
    _claimForMappedClassOrImplementingInterface_1.updateEReference(_affectedEObject_2, _affectedFeature, _newValue);
    return newNonRootObject;
  }
  
  private EObject[] _syncChange(final DeleteNonRootEObject<?> deleteNonRootEObject) {
    EObject[] _xblockexpression = null;
    {
      EObject _changedEObject = deleteNonRootEObject.getChangedEObject();
      Class<? extends EObject> _class = _changedEObject.getClass();
      this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
      EObject _affectedEObject = deleteNonRootEObject.getAffectedEObject();
      Class<? extends EObject> _class_1 = _affectedEObject.getClass();
      this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_1);
      EObject _changedEObject_1 = deleteNonRootEObject.getChangedEObject();
      Class<? extends EObject> _class_2 = _changedEObject_1.getClass();
      EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_2);
      EObject _changedEObject_2 = deleteNonRootEObject.getChangedEObject();
      _claimForMappedClassOrImplementingInterface.removeEObject(_changedEObject_2);
      EObject _affectedEObject_1 = deleteNonRootEObject.getAffectedEObject();
      Class<? extends EObject> _class_3 = _affectedEObject_1.getClass();
      EObjectMappingTransformation _claimForMappedClassOrImplementingInterface_1 = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class_3);
      EObject _affectedEObject_2 = deleteNonRootEObject.getAffectedEObject();
      EReference _affectedFeature = deleteNonRootEObject.getAffectedFeature();
      Object _newValue = deleteNonRootEObject.getNewValue();
      _xblockexpression = _claimForMappedClassOrImplementingInterface_1.updateEReference(_affectedEObject_2, _affectedFeature, _newValue);
    }
    return _xblockexpression;
  }
  
  private EObject[] _syncChange(final UpdateEAttribute<?> updateEAttribute) {
    EObject _affectedEObject = updateEAttribute.getAffectedEObject();
    Class<? extends EObject> _class = _affectedEObject.getClass();
    EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
    EObject _affectedEObject_1 = updateEAttribute.getAffectedEObject();
    EAttribute _affectedFeature = updateEAttribute.getAffectedFeature();
    Object _newValue = updateEAttribute.getNewValue();
    return _claimForMappedClassOrImplementingInterface.updateEAttribute(_affectedEObject_1, _affectedFeature, _newValue);
  }
  
  private EObject[] _syncChange(final UpdateEReference<?> updateEReference) {
    EObject _affectedEObject = updateEReference.getAffectedEObject();
    Class<? extends EObject> _class = _affectedEObject.getClass();
    EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
    EObject _affectedEObject_1 = updateEReference.getAffectedEObject();
    EReference _affectedFeature = updateEReference.getAffectedFeature();
    Object _newValue = updateEReference.getNewValue();
    return _claimForMappedClassOrImplementingInterface.updateEReference(_affectedEObject_1, _affectedFeature, _newValue);
  }
  
  private EObject[] _syncChange(final UpdateEContainmentReference<?> updateEContainmentReference) {
    EObject _affectedEObject = updateEContainmentReference.getAffectedEObject();
    Class<? extends EObject> _class = _affectedEObject.getClass();
    EObjectMappingTransformation _claimForMappedClassOrImplementingInterface = this.claimForMappedClassOrImplementingInterface(this.mappingTransformations, _class);
    EObject _affectedEObject_1 = updateEContainmentReference.getAffectedEObject();
    EReference _affectedFeature = updateEContainmentReference.getAffectedFeature();
    Object _newValue = updateEContainmentReference.getNewValue();
    return _claimForMappedClassOrImplementingInterface.updateEContainmentReference(_affectedEObject_1, _affectedFeature, _newValue);
  }
  
  private EObject[] _syncChange(final UnsetEFeature<?> unsetEFeature) {
    ChangeSynchronizer.logger.error("syncChange for UnsetEFeature<?> is not implemented yet...");
    return null;
  }
  
  private void fillTransformationMap() {
    RepositoryMappingTransformation _repositoryMappingTransformation = new RepositoryMappingTransformation();
    this.addMapping(_repositoryMappingTransformation);
    OperationInterfaceMappingTransformation _operationInterfaceMappingTransformation = new OperationInterfaceMappingTransformation();
    this.addMapping(_operationInterfaceMappingTransformation);
    BasicComponentMappingTransformation _basicComponentMappingTransformation = new BasicComponentMappingTransformation();
    this.addMapping(_basicComponentMappingTransformation);
    InterfaceMappingTransformation _interfaceMappingTransformation = new InterfaceMappingTransformation();
    this.addMapping(_interfaceMappingTransformation);
    ClassMappingTransformation _classMappingTransformation = new ClassMappingTransformation();
    this.addMapping(_classMappingTransformation);
    PackageMappingTransformation _packageMappingTransformation = new PackageMappingTransformation();
    this.addMapping(_packageMappingTransformation);
  }
  
  private void addMapping(final EObjectMappingTransformation transformation) {
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
  
  private EObject[] syncChange(final EChange createNonRootEObject) {
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
