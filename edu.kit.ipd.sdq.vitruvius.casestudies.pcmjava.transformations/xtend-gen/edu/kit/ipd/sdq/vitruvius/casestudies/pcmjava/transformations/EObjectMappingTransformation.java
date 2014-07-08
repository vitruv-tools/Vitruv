package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableHashMap;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableMap;
import java.util.Collections;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.xbase.lib.Conversions;

@SuppressWarnings("all")
public abstract class EObjectMappingTransformation {
  protected ClaimableMap<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap;
  
  public EObjectMappingTransformation() {
    ClaimableHashMap<EStructuralFeature, EStructuralFeature> _claimableHashMap = new ClaimableHashMap<EStructuralFeature, EStructuralFeature>();
    this.featureCorrespondenceMap = _claimableHashMap;
  }
  
  protected CorrespondenceInstance correspondenceInstance;
  
  public abstract Class<?> getClassOfMappedEObject();
  
  public abstract EObject[] addEObject(final EObject eObject);
  
  public abstract EObject[] removeEObject(final EObject eObject);
  
  public abstract EObject[] updateEAttribute(final EObject eObject, final EAttribute affectedAttribute, final Object newValue);
  
  public abstract EObject[] updateEReference(final EObject eObject, final EReference affectedEReference, final Object newValue);
  
  public abstract EObject[] updateEContainmentReference(final EObject eObject, final EReference afffectedEReference, final Object newValue);
  
  public abstract void setCorrespondenceForFeatures();
  
  public void setCorrespondenceInstance(final CorrespondenceInstance correspondenceInstance) {
    this.correspondenceInstance = correspondenceInstance;
    boolean _notEquals = (!Objects.equal(null, correspondenceInstance));
    if (_notEquals) {
      this.setCorrespondenceForFeatures();
    }
  }
  
  protected EObject[] toArray(final EObject eObject) {
    boolean _equals = Objects.equal(null, eObject);
    if (_equals) {
      return null;
    }
    return ((EObject[])Conversions.unwrapArray(Collections.<EObject>unmodifiableSet(Sets.<EObject>newHashSet(eObject)), EObject.class));
  }
}
