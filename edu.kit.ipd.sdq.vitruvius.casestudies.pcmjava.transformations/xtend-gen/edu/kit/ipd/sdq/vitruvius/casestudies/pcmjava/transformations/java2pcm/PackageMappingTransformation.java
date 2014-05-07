package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm;

import de.uka.ipd.sdq.pcm.repository.OperationInterface;
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.EObjectMappingTransformation;
import java.util.Iterator;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.containers.ContainersFactory;

@SuppressWarnings("all")
public class PackageMappingTransformation extends EObjectMappingTransformation {
  public Class<? extends Object> getClassOfMappedEObject() {
    return org.emftext.language.java.containers.Package.class;
  }
  
  public EObject addEObject(final EObject eObject) {
    UnsupportedOperationException _unsupportedOperationException = new UnsupportedOperationException("TODO: auto-generated method stub");
    throw _unsupportedOperationException;
  }
  
  public EObject removeEObject(final EObject eObject) {
    final org.emftext.language.java.containers.Package jaMoPPPackage = ((org.emftext.language.java.containers.Package) eObject);
    final Set<EObject> correspondingObjects = this.correspondenceInstance.claimCorrespondingEObjects(jaMoPPPackage);
    for (final EObject correspondingObject : correspondingObjects) {
      {
        EcoreUtil.remove(correspondingObject);
        this.correspondenceInstance.removeAllCorrespondingInstances(correspondingObject);
      }
    }
    return null;
  }
  
  public EObject updateEAttribute(final EObject eObject, final EAttribute affectedAttribute, final Object newValue) {
    final org.emftext.language.java.containers.Package jaMoPPPackage = ((org.emftext.language.java.containers.Package) eObject);
    final Set<EObject> correspondingEObjects = this.correspondenceInstance.claimCorrespondingEObjects(jaMoPPPackage);
    final EStructuralFeature esf = this.featureCorrespondenceMap.claimValueForKey(affectedAttribute);
    for (final EObject correspondingEObject : correspondingEObjects) {
      correspondingEObject.eSet(esf, newValue);
    }
    Iterator<EObject> _iterator = correspondingEObjects.iterator();
    return _iterator.next();
  }
  
  public EObject updateEReference(final EObject eObject, final EReference affectedEReference, final Object newValue) {
    UnsupportedOperationException _unsupportedOperationException = new UnsupportedOperationException("TODO: auto-generated method stub");
    throw _unsupportedOperationException;
  }
  
  public EObject updateEContainmentReference(final EObject eObject, final EReference afffectedEReference, final Object newValue) {
    UnsupportedOperationException _unsupportedOperationException = new UnsupportedOperationException("TODO: auto-generated method stub");
    throw _unsupportedOperationException;
  }
  
  public void setCorrespondenceForFeatures() {
    org.emftext.language.java.containers.Package _createPackage = ContainersFactory.eINSTANCE.createPackage();
    EClass _eClass = _createPackage.eClass();
    EList<EAttribute> _eAllAttributes = _eClass.getEAllAttributes();
    final Function1<EAttribute,Boolean> _function = new Function1<EAttribute,Boolean>() {
      public Boolean apply(final EAttribute attribute) {
        String _name = attribute.getName();
        boolean _equalsIgnoreCase = _name.equalsIgnoreCase("name");
        return Boolean.valueOf(_equalsIgnoreCase);
      }
    };
    Iterable<EAttribute> _filter = IterableExtensions.<EAttribute>filter(_eAllAttributes, _function);
    Iterator<EAttribute> _iterator = _filter.iterator();
    EAttribute packageNameAttribute = _iterator.next();
    OperationInterface _createOperationInterface = RepositoryFactory.eINSTANCE.createOperationInterface();
    EClass _eClass_1 = _createOperationInterface.eClass();
    EList<EAttribute> _eAllAttributes_1 = _eClass_1.getEAllAttributes();
    final Function1<EAttribute,Boolean> _function_1 = new Function1<EAttribute,Boolean>() {
      public Boolean apply(final EAttribute attribute) {
        String _name = attribute.getName();
        boolean _equalsIgnoreCase = _name.equalsIgnoreCase("entityName");
        return Boolean.valueOf(_equalsIgnoreCase);
      }
    };
    Iterable<EAttribute> _filter_1 = IterableExtensions.<EAttribute>filter(_eAllAttributes_1, _function_1);
    Iterator<EAttribute> _iterator_1 = _filter_1.iterator();
    EAttribute opInterfaceNameAttribute = _iterator_1.next();
    this.featureCorrespondenceMap.put(packageNameAttribute, opInterfaceNameAttribute);
  }
}
