package tools.vitruv.change.atomic;

import static com.google.common.base.Preconditions.checkArgument;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import tools.vitruv.change.atomic.eobject.CreateEObject;
import tools.vitruv.change.atomic.eobject.DeleteEObject;
import tools.vitruv.change.atomic.eobject.EObjectAddedEChange;
import tools.vitruv.change.atomic.eobject.EObjectExistenceEChange;
import tools.vitruv.change.atomic.eobject.EObjectSubtractedEChange;
import tools.vitruv.change.atomic.eobject.EobjectFactory;
import tools.vitruv.change.atomic.feature.FeatureEChange;
import tools.vitruv.change.atomic.feature.FeatureFactory;
import tools.vitruv.change.atomic.feature.UnsetFeature;
import tools.vitruv.change.atomic.feature.attribute.AttributeFactory;
import tools.vitruv.change.atomic.feature.attribute.InsertEAttributeValue;
import tools.vitruv.change.atomic.feature.attribute.RemoveEAttributeValue;
import tools.vitruv.change.atomic.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruv.change.atomic.feature.reference.InsertEReference;
import tools.vitruv.change.atomic.feature.reference.ReferenceFactory;
import tools.vitruv.change.atomic.feature.reference.RemoveEReference;
import tools.vitruv.change.atomic.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruv.change.atomic.root.InsertRootEObject;
import tools.vitruv.change.atomic.root.RemoveRootEObject;
import tools.vitruv.change.atomic.root.RootEChange;
import tools.vitruv.change.atomic.root.RootFactory;

/**
 * Factory singleton class for elements of change models.
 * Infers types (i.e. metaclasses and feature types) from parameters where possible.
 * 
 * <p>Can be used by any transformation that creates change models.
 */
public class TypeInferringAtomicEChangeFactory {
  private static TypeInferringAtomicEChangeFactory instance;

  /**
   * Empty singleton constructor.
   */
  protected TypeInferringAtomicEChangeFactory() {
  }

  /**
   * Get the singleton instance of the factory.
   *
   * @return The singleton instance.
   */
  public static TypeInferringAtomicEChangeFactory getInstance() {
    if (instance == null) {
      instance = new TypeInferringAtomicEChangeFactory();
    }
    return instance;
  }

  /** 
   * Sets the attributes of a RootEChange.
   *
   * @param change The RootEChange which attributes are to be set.
   * @param resource The affected resource of the change.
   * @param uri the URI of the resource. May differ from URI of the resource if it has changed.
   * @param index The affected index of the resource.
   */
  protected void setRootChangeFeatures(
      RootEChange<?> change, Resource resource, URI uri, int index) {
    change.setUri(uri != null ? uri.toString() : "");
    change.setResource(resource);
    change.setIndex(index);
  }

  /**
   * Sets the attributes of a FeatureEChange.
   *
   * @param change The FeatureEChange which attributes are to be set.
   * @param affectedEObject The affected EObject of the change.
   * @param affectedFeature The affected feature of the change.
   */
  protected <A, F extends EStructuralFeature> void setFeatureChangeFeatures(
      FeatureEChange<A, F> change, A affectedEObject, F affectedFeature) {
    change.setAffectedElement(affectedEObject);
    change.setAffectedFeature(affectedFeature);
  }

  /** 
   * Sets the new value of a EObjectAddedEChange.
   *
   * @param change The EObjectAddedEChange which new value is to be set.
   * @param newValue The new value of the change.
   */
  protected <T> void setNewValue(EObjectAddedEChange<T> change, T newValue) {
    change.setNewValue(newValue);
  }

  /**
   * Sets the old value of the EObjectSubtractedEChange.
   *
   * @param change The EObjectSubtractedEChange which old value is to be set.
   * @param oldValue The old value of the change.
   */
  protected <T> void setOldValue(EObjectSubtractedEChange<T> change, T oldValue) {
    change.setOldValue(oldValue);
  }

  /**
   * Sets the affected EObject of a EObjectExistenceEChange.
   *
   * @param change The EObjectExistenceEChange which affected EObject is to be set.
   * @param affectedEObject The affected EObject.
   */
  protected <A extends EObject> void setEObjectExistenceChange(
      EObjectExistenceEChange<A> change, A affectedEObject) {
    change.setAffectedElement(affectedEObject);
    change.setAffectedEObjectType(affectedEObject.eClass());
    change.setIdAttributeValue(TypeInferringAtomicEChangeFactory.getID(affectedEObject));
  }

  /**
   * Creates a new {@link InsertRootEObject} EChange.
   *
   * @param newValue The new root EObject.
   * @param resource The resource which the new root object is placed in.
   * @param index The index of the resource which the new root object is placed in.
   * @return The created InsertRootEObject EChange.
   */
  public <T> InsertRootEObject<T> createInsertRootChange(T newValue, Resource resource, int index) {
    final InsertRootEObject<T> c = RootFactory.eINSTANCE.createInsertRootEObject();
    setNewValue(c, newValue);
    setRootChangeFeatures(c, resource, resource.getURI(), index);
    return c;
  }

  /**
   * Creates a new {@link RemoveRootEObject} EChange.
   *
   * @param oldValue The root EObject which is removed.
   * @param resource The resource which the root object is removed from.
   * @param index The index of the resource which the root object is removed from.
   * @param oldUri The old URI of the resource. 
   *      May differ from the current resource URI if it has been changed.
   * @return The created RemoveRootEObject EChange.
   */
  public <T> RemoveRootEObject<T> createRemoveRootChange(
      T oldValue, Resource resource, URI oldUri, int index) {
    final RemoveRootEObject<T> c = RootFactory.eINSTANCE.createRemoveRootEObject();
    setOldValue(c, oldValue);
    setRootChangeFeatures(c, resource, oldUri, index);
    return c;
  }

  /**
   * Creates a new {@link RemoveRootEObject} EChange.
   *
   * @param oldValue The root EObject which is removed.
   * @param resource The resource which the root object is removed from.
   * @param index The index of the resource which the root object is removed from.
   * @return The created RemoveRootEObject EChange.
   */
  public <T extends EObject> RemoveRootEObject<T> createRemoveRootChange(
      T oldValue, Resource resource, int index) {
    return createRemoveRootChange(oldValue, resource, resource.getURI(), index);
  }

  /**
   * Creates a new {@link InsertEAttributeValue} EChange.
   *
   * @param affectedEObject The affected EObject of the change.
   * @param affectedAttribute The affected EAttribute of the change.
   * @param newValue The inserted value.
   * @param index The index at which the new value is inserted in the attribute.
   * @return The created InsertEAttributeValue EChange.
   */
  public <S, T> InsertEAttributeValue<S, T> createInsertAttributeChange(S affectedEObject,
      EAttribute affectedAttribute, int index, T newValue) {
    final InsertEAttributeValue<S, T> c = AttributeFactory.eINSTANCE.createInsertEAttributeValue();
    setFeatureChangeFeatures(c, affectedEObject, affectedAttribute);
    c.setNewValue(newValue);
    c.setIndex(index);
    return c;
  }

  /** 
   * Creates a new {@link ReplaceSingleValuedEAttribute} EChange.
   *
   * @param affectedEObject The affected EObject of the change.
   * @param affectedAttribute The affected EAttribute of the change.
   * @param oldValue The replaced value.
   * @param newValue The new value.
   * @return The created ReplaceSingleValuedEAttribute EChange.
   */
  public <S, T> ReplaceSingleValuedEAttribute<S, T> createReplaceSingleAttributeChange(
      S affectedEObject, EAttribute affectedAttribute, T oldValue, T newValue) {
    final ReplaceSingleValuedEAttribute<S, T> c = 
        AttributeFactory.eINSTANCE.createReplaceSingleValuedEAttribute();
    setFeatureChangeFeatures(c, affectedEObject, affectedAttribute);
    c.setOldValue(oldValue);
    c.setNewValue(newValue);
    return c;
  }

  /**
   * Creates a new {@link RemoveEAttributeValue} EChange.
   *
   * @param affectedEObject The affected EObject of the change.
   * @param affectedAttribute The affected EAttribute of the change.
   * @param oldValue The removed value.
   * @param index The index at which the old value is removed from.
   * @return The created RemoveEAttributeValue EChange.
   */
  public <S, T> RemoveEAttributeValue<S, T> createRemoveAttributeChange(S affectedEObject,
      EAttribute affectedAttribute, int index, T oldValue) {
    final RemoveEAttributeValue<S, T> c = AttributeFactory.eINSTANCE.createRemoveEAttributeValue();
    setFeatureChangeFeatures(c, affectedEObject, affectedAttribute);
    c.setOldValue(oldValue);
    c.setIndex(index);
    return c;
  }

  /**
   * Creates a new {@link InsertEReference} EChange.
   *
   * @param affectedEObject The affected EObject of the change.
   * @param affectedReference The affected EReference of the change.
   * @param newValue The inserted value.
   * @param index The index at which the new value is inserted in the reference.
   * @return The created InsertEReference EChange.
   */
  public <T> InsertEReference<T> createInsertReferenceChange(T affectedEObject,
      EReference affectedReference, T newValue, int index) {
    final InsertEReference<T> c = ReferenceFactory.eINSTANCE.createInsertEReference();
    setFeatureChangeFeatures(c, affectedEObject, affectedReference);
    setNewValue(c, newValue);
    c.setIndex(index);
    return c;
  }

  /**
   * Creates a new {@link ReplaceSingleValuedEReference} EChange.
   *
   * @param affectedEObject The affected EObject of the change.
   * @param affectedReference The affected EReference of the change.
   * @param oldValue The value which is replaced.
   * @param newValue The value which replaces the old one.
   * @return The created ReplaceSingleValuedEReference EChange.
   */
  public <T> ReplaceSingleValuedEReference<T> createReplaceSingleReferenceChange(
      T affectedEObject, EReference affectedReference, T oldValue, T newValue) {
    final ReplaceSingleValuedEReference<T> c = 
        ReferenceFactory.eINSTANCE.createReplaceSingleValuedEReference();
    setFeatureChangeFeatures(c, affectedEObject, affectedReference);
    setOldValue(c, oldValue);
    setNewValue(c, newValue);
    return c;
  }

  /**
   * Creates a new {@link RemoveEReference} EChange.
   *
   * @param affectedEObject The affected EObject of the change.
   * @param affectedReference The affected EReference of the change.
   * @param oldValue The value which is removed.
   * @param index The index at which the old value is removed from.
   * @return The created RemoveEReference EChange.
   */
  public <T> RemoveEReference<T> createRemoveReferenceChange(T affectedEObject,
      EReference affectedReference, T oldValue, int index) {
    final RemoveEReference<T> c = ReferenceFactory.eINSTANCE.createRemoveEReference();
    setFeatureChangeFeatures(c, affectedEObject, affectedReference);
    setOldValue(c, oldValue);
    c.setIndex(index);
    return c;
  }

  /**
   * Creates a new {@link CreateEObject} EChange.
   *
   * @param affectedEObject The created EObject.
   * @return The created CreateEObject EChange.
   */
  public <A extends EObject> CreateEObject<A> createCreateEObjectChange(A affectedEObject) {
    checkAffectedEObjectExists(affectedEObject);
    final CreateEObject<A> c = EobjectFactory.eINSTANCE.createCreateEObject();
    setEObjectExistenceChange(c, affectedEObject);
    return c;
  }

  private <A> void checkAffectedEObjectExists(A affectedEObject) {
    checkArgument(affectedEObject != null, "affected object must not be null");
  }

  /**
   * Creates a new {@link DeleteEObject} EChange.
   *
   * @param affectedEObject The deleted EObject.
   * @return The created DeleteEObject EChange.
   */
  public <A extends EObject> DeleteEObject<A> createDeleteEObjectChange(A affectedEObject) {
    checkAffectedEObjectExists(affectedEObject);
    final DeleteEObject<A> c = EobjectFactory.eINSTANCE.createDeleteEObject();
    setEObjectExistenceChange(c, affectedEObject);
    return c;
  }

  /**
   * Creates a new {@link UnsetFeature} EChange.
   *
   * @param affectedEObject The EObject of which the feature was unset.
   * @param affectedFeature The feature that was unset.
   * @return The created UnsetFeature EChange.
   */
  public <A extends EObject, F extends EStructuralFeature> UnsetFeature<A, F> 
      createUnsetFeatureChange(
      A affectedEObject, F affectedFeature) {
    checkAffectedEObjectExists(affectedEObject);
    final UnsetFeature<A, F> c = FeatureFactory.eINSTANCE.createUnsetFeature();
    setFeatureChangeFeatures(c, affectedEObject, affectedFeature);
    return c;
  }
  
  /**
   * Return the value of the ID attribute of the given {@link EObject}, according to
   * {@link EcoreUtil#getID(EObject) EcoreUtil}.
   * If the object has no ID attribute or if is marked as <code>derived</code>, 
   * <code>null</code> will be returned.
   *
   * @see EcoreUtil#getID(EObject)
   * @param eObject
   *      The object to get the ID attribute value from
   * @return The ID attribute value of the given {@link EObject} or <code>null</code> 
   *      if it has no ID attribute or if it is marked as <code>derived</code>.
   */
  private static String getID(EObject eObject) {
    final var idAttribute = eObject.eClass().getEIDAttribute();
    if (idAttribute != null && !idAttribute.isDerived()) {
      return EcoreUtil.getID(eObject);
    }
    return null;
  }
}
