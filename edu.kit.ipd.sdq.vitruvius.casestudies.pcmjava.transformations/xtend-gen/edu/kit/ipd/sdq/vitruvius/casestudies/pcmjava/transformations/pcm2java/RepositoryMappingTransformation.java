package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java;

import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.EObjectMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.JaMoPPPCMNamespace;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence;
import java.util.Iterator;
import org.apache.log4j.Logger;
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
public class RepositoryMappingTransformation extends EObjectMappingTransformation {
  private final static Logger logger = Logger.getLogger(RepositoryMappingTransformation.class.getName());
  
  public Class<?> getClassOfMappedEObject() {
    return Repository.class;
  }
  
  public void setCorrespondenceForFeatures() {
    Repository _createRepository = RepositoryFactory.eINSTANCE.createRepository();
    EClass _eClass = _createRepository.eClass();
    EList<EAttribute> _eAllAttributes = _eClass.getEAllAttributes();
    final Function1<EAttribute, Boolean> _function = new Function1<EAttribute, Boolean>() {
      public Boolean apply(final EAttribute attribute) {
        String _name = attribute.getName();
        return Boolean.valueOf(_name.equalsIgnoreCase(JaMoPPPCMNamespace.PCM_ATTRIBUTE_ENTITY_NAME));
      }
    };
    Iterable<EAttribute> _filter = IterableExtensions.<EAttribute>filter(_eAllAttributes, _function);
    Iterator<EAttribute> _iterator = _filter.iterator();
    EAttribute repositoryNameAttribute = _iterator.next();
    org.emftext.language.java.containers.Package _createPackage = ContainersFactory.eINSTANCE.createPackage();
    EClass _eClass_1 = _createPackage.eClass();
    EList<EAttribute> _eAllAttributes_1 = _eClass_1.getEAllAttributes();
    final Function1<EAttribute, Boolean> _function_1 = new Function1<EAttribute, Boolean>() {
      public Boolean apply(final EAttribute attribute) {
        String _name = attribute.getName();
        return Boolean.valueOf(_name.equalsIgnoreCase(JaMoPPPCMNamespace.JAMOPP_ATTRIBUTE_NAME));
      }
    };
    Iterable<EAttribute> _filter_1 = IterableExtensions.<EAttribute>filter(_eAllAttributes_1, _function_1);
    Iterator<EAttribute> _iterator_1 = _filter_1.iterator();
    EAttribute packageNameAttribute = _iterator_1.next();
    this.featureCorrespondenceMap.put(repositoryNameAttribute, packageNameAttribute);
  }
  
  public EObject[] addEObject(final EObject eObject) {
    final Repository repository = ((Repository) eObject);
    final org.emftext.language.java.containers.Package jaMoPPPackage = ContainersFactory.eINSTANCE.createPackage();
    String _entityName = repository.getEntityName();
    jaMoPPPackage.setName(_entityName);
    EList<String> _namespaces = jaMoPPPackage.getNamespaces();
    String _name = jaMoPPPackage.getName();
    _namespaces.add(_name);
    final EObjectCorrespondence eObjectCorrespondence = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence();
    eObjectCorrespondence.setElementA(repository);
    eObjectCorrespondence.setElementB(jaMoPPPackage);
    this.correspondenceInstance.addSameTypeCorrespondence(eObjectCorrespondence);
    return this.toArray(jaMoPPPackage);
  }
  
  public EObject[] removeEObject(final EObject eObject) {
    final Repository repository = ((Repository) eObject);
    final org.emftext.language.java.containers.Package jaMoPPPackage = this.correspondenceInstance.<org.emftext.language.java.containers.Package>claimUniqueCorrespondingEObjectByType(repository, org.emftext.language.java.containers.Package.class);
    EcoreUtil.remove(jaMoPPPackage);
    this.correspondenceInstance.removeAllCorrespondences(repository);
    return null;
  }
  
  public EObject[] updateEAttribute(final EObject eObject, final EAttribute affectedAttribute, final Object newValue) {
    final Repository repository = ((Repository) eObject);
    final EStructuralFeature jaMoPPNameAttribute = this.featureCorrespondenceMap.claimValueForKey(affectedAttribute);
    final org.emftext.language.java.containers.Package jaMoPPPackage = this.correspondenceInstance.<org.emftext.language.java.containers.Package>claimUniqueCorrespondingEObjectByType(repository, org.emftext.language.java.containers.Package.class);
    jaMoPPPackage.eSet(jaMoPPNameAttribute, newValue);
    return this.toArray(jaMoPPPackage);
  }
  
  public EObject[] updateEReference(final EObject eObject, final EReference affectedEReference, final Object newValue) {
    return null;
  }
  
  public EObject[] updateEContainmentReference(final EObject eObject, final EReference afffectedEReference, final Object newValue) {
    final Repository repository = ((Repository) eObject);
    return null;
  }
}
