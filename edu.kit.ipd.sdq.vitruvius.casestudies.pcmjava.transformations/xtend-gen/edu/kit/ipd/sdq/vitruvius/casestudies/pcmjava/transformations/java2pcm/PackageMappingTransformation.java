package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm;

import com.google.common.base.Objects;
import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.repository.RepositoryComponent;
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.EObjectMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence;
import java.util.Iterator;
import java.util.Set;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class PackageMappingTransformation extends EObjectMappingTransformation {
  private final static Logger logger = Logger.getLogger(PackageMappingTransformation.class.getSimpleName());
  
  private boolean correspondenceRepositoryAlreadyExists;
  
  private Repository repository;
  
  public Class<?> getClassOfMappedEObject() {
    return org.emftext.language.java.containers.Package.class;
  }
  
  /**
   * override setCorrespondenceInstance:
   * Check whether there already exists a repository in the correspondences.
   * If yes set correspondenceRepositoryAlreadyExists to true otherwise to false.
   * If a repository exists we do not have to create a new one in addEObject
   */
  public void setCorrespondenceInstance(final CorrespondenceInstance correspondenceInstance) {
    super.setCorrespondenceInstance(correspondenceInstance);
    final Set<Repository> repositorys = correspondenceInstance.<Repository>getAllEObjectsInCorrespondencesWithType(Repository.class);
    boolean _or = false;
    boolean _equals = Objects.equal(null, repositorys);
    if (_equals) {
      _or = true;
    } else {
      int _size = repositorys.size();
      boolean _equals_1 = (0 == _size);
      _or = _equals_1;
    }
    if (_or) {
      this.correspondenceRepositoryAlreadyExists = false;
    } else {
      int _size_1 = repositorys.size();
      boolean _notEquals = (1 != _size_1);
      if (_notEquals) {
        PackageMappingTransformation.logger.warn(("more than one repositorys exists in correspondence model. Should not happen. " + repositorys));
      }
      Iterator<Repository> _iterator = repositorys.iterator();
      Repository _next = _iterator.next();
      this.repository = _next;
      this.correspondenceRepositoryAlreadyExists = true;
    }
  }
  
  /**
   * when a package is added there following possibilities exists:
   * i) it is a package corresponding to a basic component --> create PCM basic component
   * ii) it is a package corresponding to a composite component --> create PCM composite component
   * iii) it is a package corresponding to a system --> create PCM system
   * iv) it is the root package and the package where all interfaces and datatypes should be stored --> create PCM repository
   * v) none of the above --> do nothing
   * 
   * Case iv) occurs when no package and no repository exist yet--> c
   * 			an be determined automatically (see correspondenceRepositoryAlreadyExists)
   * Whether it is case i), ii) or iii) can not be decided automatically --> ask user
   * 
   * Since we do not have no interface to ask the user we currently do the following:
   * We assume a new package (which is not the root package) corresponds to a PCM basic component (case i)).
   * Packages that are not architectural relevant can not be created.
   * Packages that represent a system or a composite component have to be created using the PCM
   */
  public EObject[] addEObject(final EObject eObject) {
    final org.emftext.language.java.containers.Package jaMoPPPackage = ((org.emftext.language.java.containers.Package) eObject);
    if ((!this.correspondenceRepositoryAlreadyExists)) {
      Repository _createRepository = RepositoryFactory.eINSTANCE.createRepository();
      this.repository = _createRepository;
      String _name = jaMoPPPackage.getName();
      this.repository.setEntityName(_name);
      EObjectCorrespondence repo2Package = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence();
      repo2Package.setElementA(this.repository);
      repo2Package.setElementB(jaMoPPPackage);
      this.correspondenceInstance.addSameTypeCorrespondence(repo2Package);
      this.correspondenceRepositoryAlreadyExists = true;
      return this.toArray(this.repository);
    }
    BasicComponent basicComponent = RepositoryFactory.eINSTANCE.createBasicComponent();
    String _name_1 = jaMoPPPackage.getName();
    basicComponent.setEntityName(_name_1);
    basicComponent.setRepository__RepositoryComponent(this.repository);
    EList<RepositoryComponent> _components__Repository = this.repository.getComponents__Repository();
    _components__Repository.add(basicComponent);
    EObjectCorrespondence basicComponent2Package = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence();
    basicComponent2Package.setElementA(basicComponent);
    basicComponent2Package.setElementB(jaMoPPPackage);
    Correspondence _claimUniqueOrNullCorrespondenceForEObject = this.correspondenceInstance.claimUniqueOrNullCorrespondenceForEObject(this.repository);
    basicComponent2Package.setParent(_claimUniqueOrNullCorrespondenceForEObject);
    this.correspondenceInstance.addSameTypeCorrespondence(basicComponent2Package);
    return this.toArray(basicComponent);
  }
  
  /**
   * When a package is removed all classes in the packages are removed as well.
   * Hence, we remove all corresponding objects (which theoretically could be the whole repository if the main
   * package is removed.
   */
  public EObject[] removeEObject(final EObject eObject) {
    final org.emftext.language.java.containers.Package jaMoPPPackage = ((org.emftext.language.java.containers.Package) eObject);
    try {
      final Set<EObject> correspondingObjects = this.correspondenceInstance.claimCorrespondingEObjects(jaMoPPPackage);
      for (final EObject correspondingObject : correspondingObjects) {
        {
          EcoreUtil.remove(correspondingObject);
          this.correspondenceInstance.removeAllCorrespondences(correspondingObject);
        }
      }
    } catch (final Throwable _t) {
      if (_t instanceof RuntimeException) {
        final RuntimeException rte = (RuntimeException)_t;
        PackageMappingTransformation.logger.info(rte);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return null;
  }
  
  public EObject[] updateEAttribute(final EObject eObject, final EAttribute affectedAttribute, final Object newValue) {
    return null;
  }
  
  /**
   * not needed for package
   */
  public EObject[] updateEReference(final EObject eObject, final EReference affectedEReference, final Object newValue) {
    return null;
  }
  
  /**
   * not needed for package
   */
  public EObject[] updateEContainmentReference(final EObject eObject, final EReference afffectedEReference, final Object newValue) {
    return null;
  }
  
  public void setCorrespondenceForFeatures() {
  }
}
