package edu.kit.ipd.sdq.vitruvius.framework.transformationexecuter;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableHashMap;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableMap;
import java.util.Collections;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

@SuppressWarnings("all")
public abstract class EObjectMappingTransformation {
  protected ClaimableMap<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap;
  
  public EObjectMappingTransformation() {
    ClaimableHashMap<EStructuralFeature, EStructuralFeature> _claimableHashMap = new ClaimableHashMap<EStructuralFeature, EStructuralFeature>();
    this.featureCorrespondenceMap = _claimableHashMap;
  }
  
  protected CorrespondenceInstance correspondenceInstance;
  
  public abstract Class<?> getClassOfMappedEObject();
  
  public abstract EObject[] createEObject(final EObject eObject);
  
  public abstract EObject[] removeEObject(final EObject eObject);
  
  public abstract TransformationChangeResult createRootEObject(final EObject newRootEObject, final EObject[] newCorrespondingEObjects);
  
  public abstract TransformationChangeResult deleteRootEObject(final EObject oldRootEObject, final EObject[] oldCorrespondingEObjectsToDelete);
  
  public abstract TransformationChangeResult replaceRoot(final EObject oldRootEObject, final EObject newRootEObject, final EObject[] oldCorrespondingEObjectsToDelete, final EObject[] newCorrespondingEObjects);
  
  public abstract TransformationChangeResult createNonRootEObjectInList(final EObject affectedEObject, final EReference affectedReference, final EObject newValue, final int index, final EObject[] newCorrespondingEObjects);
  
  public abstract TransformationChangeResult deleteNonRootEObjectSingle(final EObject affectedEObject, final EReference affectedReference, final EObject oldValue, final EObject[] eObjectsToDelete);
  
  public abstract TransformationChangeResult createNonRootEObjectSingle(final EObject affectedEObject, final EReference affectedReference, final EObject newValue, final EObject[] newCorrespondingEObjects);
  
  public abstract TransformationChangeResult replaceNonRootEObjectInList(final EObject affectedEObject, final EReference affectedReference, final EObject oldValue, final EObject newValue, final int index, final EObject[] oldCorrespondingEObjectsToDelete, final EObject[] newCorrespondingEObjects);
  
  public abstract TransformationChangeResult deleteNonRootEObjectInList(final EObject affectedEObject, final EReference affectedReference, final EObject oldValue, final int index, final EObject[] oldCorrespondingEObjectsToDelete);
  
  public abstract TransformationChangeResult replaceNonRootEObjectSingle(final EObject affectedEObject, final EReference affectedReference, final EObject oldValue, final EObject newValue, final EObject[] oldCorrespondingEObjectsToDelete, final EObject[] newCorrespondingEObjects);
  
  public abstract TransformationChangeResult permuteContainmentEReferenceValues(final EObject affectedEObject, final EReference affectedReference, final EList<Integer> newIndexForElementAt);
  
  public abstract TransformationChangeResult insertNonContaimentEReference(final EObject affectedEObject, final EReference affectedReference, final EObject newValue, final int index);
  
  public abstract TransformationChangeResult updateSingleValuedNonContainmentEReference(final EObject affectedEObject, final EReference affectedReference, final EObject oldValue, final EObject newValue);
  
  public abstract TransformationChangeResult permuteNonContainmentEReferenceValues(final EObject affectedEObject, final EReference affectedReference, final EList<Integer> newIndexForElementAt);
  
  public abstract TransformationChangeResult replaceNonContainmentEReference(final EObject affectedEObject, final EReference affectedReference, final EObject oldValue, final EObject newValue, final int index);
  
  public abstract TransformationChangeResult removeNonContainmentEReference(final EObject affectedEObject, final EReference affectedReference, final EObject oldValue, final int index);
  
  public abstract TransformationChangeResult updateSingleValuedEAttribute(final EObject affectedEObject, final EAttribute affectedAttribute, final Object oldValue, final Object newValue);
  
  public abstract TransformationChangeResult removeEAttributeValue(final EObject affectedEObject, final EAttribute affectedAttribute, final Object oldValue, final int index);
  
  public abstract TransformationChangeResult insertEAttributeValue(final EObject affectedEObject, final EAttribute affectedAttribute, final Object newValue, final int index);
  
  public abstract TransformationChangeResult unsetEAttribute(final EObject affectedEObject, final EStructuralFeature affectedFeature, final Object oldValue);
  
  public abstract TransformationChangeResult unsetContainmentEReference(final EObject affectedEObject, final EReference affectedReference, final EObject oldValue, final EObject[] oldCorrespondingEObjectsToDelete);
  
  public abstract TransformationChangeResult unsetNonContainmentEReference(final EObject affectedEObject, final EReference affectedReference, final EObject oldValue);
  
  public abstract TransformationChangeResult replaceEAttributeValue(final EObject affectedEObject, final EAttribute affectedAttribute, final Object oldValue, final Object newValue, final int index);
  
  public abstract TransformationChangeResult permuteEAttributeValues(final EObject affectedEObject, final EAttribute affectedAttribute, final EList<Integer> newIndexForElementAt);
  
  public abstract void setCorrespondenceForFeatures();
  
  public void setCorrespondenceInstance(final CorrespondenceInstance correspondenceInstance) {
    this.correspondenceInstance = correspondenceInstance;
    boolean _notEquals = (!Objects.equal(null, correspondenceInstance));
    if (_notEquals) {
      this.setCorrespondenceForFeatures();
    }
  }
  
  protected List<EObject> toArray(final EObject eObject) {
    List<EObject> _xblockexpression = null;
    {
      boolean _equals = Objects.equal(null, eObject);
      if (_equals) {
        return null;
      }
      _xblockexpression = Collections.<EObject>unmodifiableList(Lists.<EObject>newArrayList(eObject));
    }
    return _xblockexpression;
  }
}
