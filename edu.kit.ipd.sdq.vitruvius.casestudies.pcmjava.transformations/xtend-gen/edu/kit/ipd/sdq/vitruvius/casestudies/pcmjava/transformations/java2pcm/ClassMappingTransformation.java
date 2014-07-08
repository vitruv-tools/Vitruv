package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm;

import com.google.common.base.Objects;
import de.uka.ipd.sdq.pcm.core.entity.InterfaceProvidingRequiringEntity;
import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import de.uka.ipd.sdq.pcm.repository.RepositoryComponent;
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.EObjectMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.JaMoPPPCMNamespace;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.JaMoPPPCMUtils;
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
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier;
import org.emftext.language.java.modifiers.Public;

/**
 * Maps a JaMoPP class to a PCM Components or System.
 * Triggered when a CUD operation on JaMoPP class is detected.
 */
@SuppressWarnings("all")
public class ClassMappingTransformation extends EObjectMappingTransformation {
  private final static Logger logger = Logger.getLogger(ClassMappingTransformation.class.getSimpleName());
  
  public Class<?> getClassOfMappedEObject() {
    return org.emftext.language.java.classifiers.Class.class;
  }
  
  /**
   * if a class is add in java we have to check whether this is a class that implements a component or a system
   * This is checked as follows:
   * The class does not represents the implementing class when:
   * 		i) the class is not public, or
   * 		ii) the component or system corresponding to the package of the class already has an implementing class
   * The class represents the implementing class, when
   * 		iii) the class is public, and
   * 		iv) the component or system corresponding to the package of the class does not has an implementing class yet, and
   * 		v) a) the class name contians the name of the package,or
   * 		v) b) the user says it is the implementing class
   */
  public EObject[] addEObject(final EObject eObject) {
    final org.emftext.language.java.classifiers.Class jaMoPPClass = ((org.emftext.language.java.classifiers.Class) eObject);
    EList<AnnotationInstanceOrModifier> _annotationsAndModifiers = jaMoPPClass.getAnnotationsAndModifiers();
    final Function1<AnnotationInstanceOrModifier, Boolean> _function = new Function1<AnnotationInstanceOrModifier, Boolean>() {
      public Boolean apply(final AnnotationInstanceOrModifier aam) {
        return Boolean.valueOf((aam instanceof Public));
      }
    };
    Iterable<AnnotationInstanceOrModifier> _filter = IterableExtensions.<AnnotationInstanceOrModifier>filter(_annotationsAndModifiers, _function);
    final int hasPublicAnnotation = IterableExtensions.size(_filter);
    if ((0 == hasPublicAnnotation)) {
      return null;
    }
    final org.emftext.language.java.containers.Package jaMoPPPackage = JaMoPPPCMUtils.getContainingPackageFromCorrespondenceInstance(jaMoPPClass, this.correspondenceInstance);
    boolean _equals = Objects.equal(null, jaMoPPPackage);
    if (_equals) {
      ClassMappingTransformation.logger.info(("jaMoPPPackage is null for class" + jaMoPPClass));
      return null;
    }
    final InterfaceProvidingRequiringEntity pcmComponentOrSystem = this.correspondenceInstance.<InterfaceProvidingRequiringEntity>claimUniqueCorrespondingEObjectByType(jaMoPPPackage, InterfaceProvidingRequiringEntity.class);
    final Set<Correspondence> correspondencesForPCMCompOrSystem = this.correspondenceInstance.getAllCorrespondences(pcmComponentOrSystem);
    boolean _equals_1 = Objects.equal(null, correspondencesForPCMCompOrSystem);
    if (_equals_1) {
      return null;
    }
    final Function1<Correspondence, Boolean> _function_1 = new Function1<Correspondence, Boolean>() {
      public Boolean apply(final Correspondence correspondence) {
        return Boolean.valueOf((correspondence instanceof org.emftext.language.java.classifiers.Class));
      }
    };
    Iterable<Correspondence> _filter_1 = IterableExtensions.<Correspondence>filter(correspondencesForPCMCompOrSystem, _function_1);
    final int hasClassCorrespondence = IterableExtensions.size(_filter_1);
    if ((0 < hasClassCorrespondence)) {
      return null;
    }
    boolean isCorrespondingClass = false;
    String _name = jaMoPPClass.getName();
    String _entityName = pcmComponentOrSystem.getEntityName();
    boolean _contains = _name.contains(_entityName);
    if (_contains) {
      isCorrespondingClass = true;
    }
    if (isCorrespondingClass) {
      final Set<Correspondence> parentCorrespondences = this.correspondenceInstance.getAllCorrespondences(pcmComponentOrSystem);
      Correspondence parentCorrespondence = null;
      boolean _notEquals = (!Objects.equal(null, parentCorrespondences));
      if (_notEquals) {
        Iterator<Correspondence> _iterator = parentCorrespondences.iterator();
        Correspondence _next = _iterator.next();
        parentCorrespondence = _next;
      }
      final EObjectCorrespondence class2Component = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence();
      class2Component.setElementA(pcmComponentOrSystem);
      class2Component.setElementB(jaMoPPClass);
      class2Component.setParent(parentCorrespondence);
      final EObjectCorrespondence compilationUnit2Component = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence();
      compilationUnit2Component.setElementA(pcmComponentOrSystem);
      CompilationUnit _containingCompilationUnit = jaMoPPClass.getContainingCompilationUnit();
      compilationUnit2Component.setElementB(_containingCompilationUnit);
      compilationUnit2Component.setParent(parentCorrespondence);
      EList<Correspondence> _dependentCorrespondences = compilationUnit2Component.getDependentCorrespondences();
      _dependentCorrespondences.add(class2Component);
      EList<Correspondence> _dependentCorrespondences_1 = class2Component.getDependentCorrespondences();
      _dependentCorrespondences_1.add(compilationUnit2Component);
      this.correspondenceInstance.addSameTypeCorrespondence(class2Component);
      this.correspondenceInstance.addSameTypeCorrespondence(compilationUnit2Component);
    }
    return this.toArray(pcmComponentOrSystem);
  }
  
  /**
   * Remove class:
   * Check if class has corresponding elements and
   * Remove CorrespondingInstance for class and compilation unit
   * Also removes basicComponent on PCM.
   * If the class is not the only class in the package ask the user whether to remove the component
   * and package and all classes
   */
  public EObject[] removeEObject(final EObject eObject) {
    final org.emftext.language.java.classifiers.Class jaMoPPClass = ((org.emftext.language.java.classifiers.Class) eObject);
    final Set<Correspondence> correspondences = this.correspondenceInstance.getAllCorrespondences(eObject);
    boolean _and = false;
    boolean _notEquals = (!Objects.equal(null, correspondences));
    if (!_notEquals) {
      _and = false;
    } else {
      int _size = correspondences.size();
      boolean _lessThan = (0 < _size);
      _and = _lessThan;
    }
    if (_and) {
      CompilationUnit _containingCompilationUnit = jaMoPPClass.getContainingCompilationUnit();
      final EList<ConcreteClassifier> classifiersInSamePackage = _containingCompilationUnit.getClassifiersInSamePackage();
      boolean _and_1 = false;
      boolean _notEquals_1 = (!Objects.equal(null, classifiersInSamePackage));
      if (!_notEquals_1) {
        _and_1 = false;
      } else {
        int _size_1 = classifiersInSamePackage.size();
        boolean _lessThan_1 = (1 < _size_1);
        _and_1 = _lessThan_1;
      }
      if (_and_1) {
        boolean removeAllClassifiers = false;
        if (removeAllClassifiers) {
          final Procedure1<ConcreteClassifier> _function = new Procedure1<ConcreteClassifier>() {
            public void apply(final ConcreteClassifier classifier) {
              CompilationUnit _containingCompilationUnit = classifier.getContainingCompilationUnit();
              EcoreUtil.remove(_containingCompilationUnit);
            }
          };
          IterableExtensions.<ConcreteClassifier>forEach(classifiersInSamePackage, _function);
        }
        CompilationUnit _containingCompilationUnit_1 = jaMoPPClass.getContainingCompilationUnit();
        EcoreUtil.remove(_containingCompilationUnit_1);
        final Procedure1<Correspondence> _function_1 = new Procedure1<Correspondence>() {
          public void apply(final Correspondence correspondingObj) {
            EcoreUtil.remove(correspondingObj);
          }
        };
        IterableExtensions.<Correspondence>forEach(correspondences, _function_1);
      }
      this.correspondenceInstance.removeAllCorrespondences(jaMoPPClass);
    }
    return null;
  }
  
  /**
   * if the class is renamed rename the corresponding objects on PCM side
   */
  public EObject[] updateEAttribute(final EObject eObject, final EAttribute affectedAttribute, final Object newValue) {
    final EStructuralFeature affectedPCMFeature = this.featureCorrespondenceMap.claimValueForKey(affectedAttribute);
    EObject ret = null;
    try {
      Set<RepositoryComponent> correspondingPCMObjects = this.correspondenceInstance.<RepositoryComponent>claimCorrespondingEObjectsByType(eObject, RepositoryComponent.class);
      for (final RepositoryComponent correspondingPCMObject : correspondingPCMObjects) {
        {
          correspondingPCMObject.eSet(affectedPCMFeature, newValue);
          ret = correspondingPCMObject;
        }
      }
    } catch (final Throwable _t) {
      if (_t instanceof RuntimeException) {
        final RuntimeException rt = (RuntimeException)_t;
        ClassMappingTransformation.logger.trace(rt);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return this.toArray(ret);
  }
  
  /**
   * currently not needed (?) for Class
   */
  public EObject[] updateEReference(final EObject eObject, final EReference affectedEReference, final Object newValue) {
    return null;
  }
  
  /**
   * currently not needed (?) for Class
   */
  public EObject[] updateEContainmentReference(final EObject eObject, final EReference afffectedEReference, final Object newValue) {
    return null;
  }
  
  /**
   * sets the name correspondece for JaMoPP-class names and PCM-entityName Attribut
   */
  public void setCorrespondenceForFeatures() {
    org.emftext.language.java.classifiers.Class _createClass = ClassifiersFactory.eINSTANCE.createClass();
    final EAttribute classNameAttribute = JaMoPPPCMUtils.getAttributeByNameFromEObject(JaMoPPPCMNamespace.JAMOPP_ATTRIBUTE_NAME, _createClass);
    BasicComponent _createBasicComponent = RepositoryFactory.eINSTANCE.createBasicComponent();
    final EAttribute componentNameAttribute = JaMoPPPCMUtils.getAttributeByNameFromEObject(JaMoPPPCMNamespace.PCM_ATTRIBUTE_ENTITY_NAME, _createBasicComponent);
    this.featureCorrespondenceMap.put(classNameAttribute, componentNameAttribute);
  }
}
