package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

@SuppressWarnings("all")
public abstract class EObjectMappingTransformation {
  public abstract Class<? extends Object> getClassOfMappedEObject();
  
  public abstract EObject addEObject(final EObject eObject);
  
  public abstract EObject removeEObject(final EObject eObject);
  
  public abstract EObject updateEAttribute(final EObject eObject, final EAttribute affectedAttribute, final Object newValue);
  
  public abstract EObject updateEReference(final EObject eObject, final EReference affectedEReference, final EObject newValue);
  
  public abstract EObject updateEContainmentReference(final EObject eObject, final EReference afffectedEReference, final EObject newValue);
}
