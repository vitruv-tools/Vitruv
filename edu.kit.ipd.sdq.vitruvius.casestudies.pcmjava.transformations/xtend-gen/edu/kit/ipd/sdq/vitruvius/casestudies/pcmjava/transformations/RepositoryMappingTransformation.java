package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations;

import de.uka.ipd.sdq.pcm.repository.Repository;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.EObjectMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.TransformationUtils;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emftext.language.java.containers.ContainersFactory;

@SuppressWarnings("all")
public class RepositoryMappingTransformation extends EObjectMappingTransformation {
  public Class<? extends Object> getClassOfMappedEObject() {
    return Repository.class;
  }
  
  public EObject addEObject(final EObject eObject) {
    final Repository repository = ((Repository) eObject);
    final org.emftext.language.java.containers.Package jaMoPPPackage = ContainersFactory.eINSTANCE.createPackage();
    String _entityName = repository.getEntityName();
    jaMoPPPackage.setName(_entityName);
    String _name = jaMoPPPackage.getName();
    String _plus = ("src/" + _name);
    final VURI vuri = VURI.getInstance(_plus);
    TransformationUtils.saveRootEObject(jaMoPPPackage, vuri);
    return jaMoPPPackage;
  }
  
  public EObject removeEObject(final EObject eObject) {
    final Repository repository = ((Repository) eObject);
    return null;
  }
  
  public EObject updateEAttribute(final EObject eObject, final EAttribute affectedAttribute, final Object newValue) {
    final Repository repository = ((Repository) eObject);
    return null;
  }
  
  public EObject updateEReference(final EObject eObject, final EReference affectedEReference, final EObject newValue) {
    final Repository repository = ((Repository) eObject);
    return null;
  }
  
  public EObject updateEContainmentReference(final EObject eObject, final EReference afffectedEReference, final EObject newValue) {
    final Repository repository = ((Repository) eObject);
    return null;
  }
}
