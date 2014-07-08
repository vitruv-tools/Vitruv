package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm;

import com.google.common.base.Objects;
import de.uka.ipd.sdq.pcm.repository.OperationInterface;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.repository.RepositoryComponent;
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.EObjectMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.JaMoPPPCMUtils;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence;
import java.util.Iterator;
import java.util.Set;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;

/**
 * Maps a JaMoPP interface to a PCM interface
 * Triggered when a CUD operation on JaMoPP interface is detected.
 */
@SuppressWarnings("all")
public class InterfaceMappingTransformation extends EObjectMappingTransformation {
  private final static Logger logger = Logger.getLogger(InterfaceMappingTransformation.class.getName());
  
  public Class<?> getClassOfMappedEObject() {
    return Interface.class;
  }
  
  /**
   * Called when a Java-interface was added to the source code
   * Determines whether the interface is architecture relevant or not by
   * a) checking whether it is in the package that corresponds to the repository package
   * b) asking the developer (not yet implemented)
   */
  public EObject[] addEObject(final EObject eObject) {
    final Interface jaMoPPInterface = ((Interface) eObject);
    final org.emftext.language.java.containers.Package jaMoPPPackage = JaMoPPPCMUtils.getContainingPackageFromCorrespondenceInstance(jaMoPPInterface, this.correspondenceInstance);
    try {
      final Set<EObject> pcmArtefacts = this.correspondenceInstance.getAllCorrespondingEObjects(jaMoPPPackage);
      boolean _or = false;
      boolean _equals = Objects.equal(null, pcmArtefacts);
      if (_equals) {
        _or = true;
      } else {
        int _size = pcmArtefacts.size();
        boolean _equals_1 = (0 == _size);
        _or = _equals_1;
      }
      if (_or) {
        return null;
      }
      Repository repo = null;
      final Function1<EObject, Boolean> _function = new Function1<EObject, Boolean>() {
        public Boolean apply(final EObject pcmArt) {
          return Boolean.valueOf((pcmArt instanceof Repository));
        }
      };
      Iterable<EObject> _filter = IterableExtensions.<EObject>filter(pcmArtefacts, _function);
      int _size_1 = IterableExtensions.size(_filter);
      boolean _lessThan = (0 < _size_1);
      if (_lessThan) {
        final Function1<EObject, Boolean> _function_1 = new Function1<EObject, Boolean>() {
          public Boolean apply(final EObject pcmArt) {
            return Boolean.valueOf((pcmArt instanceof Repository));
          }
        };
        Iterable<EObject> _filter_1 = IterableExtensions.<EObject>filter(pcmArtefacts, _function_1);
        Iterator<EObject> _iterator = _filter_1.iterator();
        EObject _next = _iterator.next();
        repo = ((Repository) _next);
      } else {
        boolean _or_1 = false;
        final Function1<EObject, Boolean> _function_2 = new Function1<EObject, Boolean>() {
          public Boolean apply(final EObject pcmArt) {
            return Boolean.valueOf((pcmArt instanceof RepositoryComponent));
          }
        };
        Iterable<EObject> _filter_2 = IterableExtensions.<EObject>filter(pcmArtefacts, _function_2);
        int _size_2 = IterableExtensions.size(_filter_2);
        boolean _lessThan_1 = (0 < _size_2);
        if (_lessThan_1) {
          _or_1 = true;
        } else {
          final Function1<EObject, Boolean> _function_3 = new Function1<EObject, Boolean>() {
            public Boolean apply(final EObject pcmArt) {
              return Boolean.valueOf((pcmArt instanceof de.uka.ipd.sdq.pcm.system.System));
            }
          };
          Iterable<EObject> _filter_3 = IterableExtensions.<EObject>filter(pcmArtefacts, _function_3);
          int _size_3 = IterableExtensions.size(_filter_3);
          boolean _lessThan_2 = (0 < _size_3);
          _or_1 = _lessThan_2;
        }
        if (_or_1) {
          InterfaceMappingTransformation.logger.info("pcmArtefact is instanceof RepositoryComponent. Assuming that the interface is not architectural relevant");
          return null;
        } else {
          InterfaceMappingTransformation.logger.warn(("pcmArtefact is not the repository or a component or a system. Should not happen. PCMArtefact: " + pcmArtefacts));
          return null;
        }
      }
      OperationInterface opInterface = RepositoryFactory.eINSTANCE.createOperationInterface();
      String _name = jaMoPPInterface.getName();
      opInterface.setEntityName(_name);
      opInterface.setRepository__Interface(repo);
      EList<de.uka.ipd.sdq.pcm.repository.Interface> _interfaces__Repository = repo.getInterfaces__Repository();
      _interfaces__Repository.add(opInterface);
      final Set<Correspondence> parentCorrespondences = this.correspondenceInstance.getAllCorrespondences(repo);
      Correspondence parentCorrespondence = null;
      boolean _notEquals = (!Objects.equal(null, parentCorrespondences));
      if (_notEquals) {
        Iterator<Correspondence> _iterator_1 = parentCorrespondences.iterator();
        Correspondence _next_1 = _iterator_1.next();
        parentCorrespondence = _next_1;
      }
      EObjectCorrespondence interface2opInterfaceCorrespondence = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence();
      interface2opInterfaceCorrespondence.setElementA(opInterface);
      interface2opInterfaceCorrespondence.setElementB(jaMoPPInterface);
      interface2opInterfaceCorrespondence.setParent(parentCorrespondence);
      EObjectCorrespondence compilationUnit2opInterfaceCorrespondence = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence();
      compilationUnit2opInterfaceCorrespondence.setElementA(opInterface);
      CompilationUnit _containingCompilationUnit = jaMoPPInterface.getContainingCompilationUnit();
      compilationUnit2opInterfaceCorrespondence.setElementB(_containingCompilationUnit);
      compilationUnit2opInterfaceCorrespondence.setParent(parentCorrespondence);
      EList<Correspondence> _dependentCorrespondences = interface2opInterfaceCorrespondence.getDependentCorrespondences();
      _dependentCorrespondences.add(compilationUnit2opInterfaceCorrespondence);
      EList<Correspondence> _dependentCorrespondences_1 = compilationUnit2opInterfaceCorrespondence.getDependentCorrespondences();
      _dependentCorrespondences_1.add(interface2opInterfaceCorrespondence);
      this.correspondenceInstance.addSameTypeCorrespondence(compilationUnit2opInterfaceCorrespondence);
      this.correspondenceInstance.addSameTypeCorrespondence(interface2opInterfaceCorrespondence);
      return this.toArray(opInterface);
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception e = (Exception)_t;
        InterfaceMappingTransformation.logger.info(e);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return null;
  }
  
  /**
   * Called when a Java-interface was removed. Also removes the corresponding PCM Interface (if there is one)
   * Does not ask the developer whether the PCM interface should be removed also.
   */
  public EObject[] removeEObject(final EObject eObject) {
    final Interface jaMoPPInterface = ((Interface) eObject);
    final CompilationUnit jaMoPPCompilationUnit = jaMoPPInterface.getContainingCompilationUnit();
    try {
      EObject correspondingOpInterface = this.correspondenceInstance.<OperationInterface>claimUniqueCorrespondingEObjectByType(jaMoPPInterface, OperationInterface.class);
      EObject correspondingOpInterface2CompilationUnit = this.correspondenceInstance.<OperationInterface>claimUniqueCorrespondingEObjectByType(jaMoPPCompilationUnit, OperationInterface.class);
      boolean _and = false;
      boolean _equals = Objects.equal(null, correspondingOpInterface);
      if (!_equals) {
        _and = false;
      } else {
        boolean _equals_1 = Objects.equal(null, correspondingOpInterface2CompilationUnit);
        _and = _equals_1;
      }
      if (_and) {
        return null;
      }
      boolean _notEquals = (!Objects.equal(correspondingOpInterface, correspondingOpInterface2CompilationUnit));
      if (_notEquals) {
        InterfaceMappingTransformation.logger.warn(((("corresponding interface " + correspondingOpInterface) + "is not the same interface as the interface corresponding to the compilation unit ") + correspondingOpInterface2CompilationUnit));
        return null;
      }
      EcoreUtil.remove(correspondingOpInterface);
      this.correspondenceInstance.removeAllCorrespondences(jaMoPPInterface);
    } catch (final Throwable _t) {
      if (_t instanceof RuntimeException) {
        final RuntimeException rte = (RuntimeException)_t;
        InterfaceMappingTransformation.logger.info(rte);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return null;
  }
  
  public EObject[] updateEAttribute(final EObject eObject, final EAttribute affectedAttribute, final Object newValue) {
    final Interface jaMoPPInterface = ((Interface) eObject);
    EObject ret = null;
    try {
      final EStructuralFeature attribute = this.featureCorrespondenceMap.claimValueForKey(affectedAttribute);
      Set<OperationInterface> opInterfaces = this.correspondenceInstance.<OperationInterface>claimCorrespondingEObjectsByType(jaMoPPInterface, OperationInterface.class);
      for (final OperationInterface opInterface : opInterfaces) {
        {
          opInterface.eSet(attribute, newValue);
          ret = opInterface;
        }
      }
    } catch (final Throwable _t) {
      if (_t instanceof RuntimeException) {
        final RuntimeException rt = (RuntimeException)_t;
        InterfaceMappingTransformation.logger.trace(rt);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return this.toArray(ret);
  }
  
  public EObject[] updateEReference(final EObject eObject, final EReference affectedEReference, final Object newValue) {
    Class<? extends InterfaceMappingTransformation> _class = this.getClass();
    String _simpleName = _class.getSimpleName();
    String _plus = ("updateEReference called in class " + _simpleName);
    String _plus_1 = (_plus + " with parameters: EObject");
    String _plus_2 = (_plus_1 + eObject);
    String _plus_3 = (_plus_2 + " AffectedERefernece");
    String _plus_4 = (_plus_3 + affectedEReference);
    String _plus_5 = (_plus_4 + " newValue=");
    String _plus_6 = (_plus_5 + newValue);
    String _plus_7 = (_plus_6 + ". Should not happen...");
    InterfaceMappingTransformation.logger.warn(_plus_7);
    return null;
  }
  
  public EObject[] updateEContainmentReference(final EObject eObject, final EReference affectedEReference, final Object newValue) {
    Class<? extends InterfaceMappingTransformation> _class = this.getClass();
    String _simpleName = _class.getSimpleName();
    String _plus = ("updateEContainmentReference called in class " + _simpleName);
    String _plus_1 = (_plus + " with parameters: EObject");
    String _plus_2 = (_plus_1 + eObject);
    String _plus_3 = (_plus_2 + " affectedEReference");
    String _plus_4 = (_plus_3 + affectedEReference);
    String _plus_5 = (_plus_4 + " newValue=");
    String _plus_6 = (_plus_5 + newValue);
    String _plus_7 = (_plus_6 + ". Should not happen...");
    InterfaceMappingTransformation.logger.warn(_plus_7);
    return null;
  }
  
  public void setCorrespondenceForFeatures() {
    Interface _createInterface = ClassifiersFactory.eINSTANCE.createInterface();
    EClass _eClass = _createInterface.eClass();
    EList<EAttribute> _eAllAttributes = _eClass.getEAllAttributes();
    final Function1<EAttribute, Boolean> _function = new Function1<EAttribute, Boolean>() {
      public Boolean apply(final EAttribute attribute) {
        String _name = attribute.getName();
        return Boolean.valueOf(_name.equalsIgnoreCase("name"));
      }
    };
    Iterable<EAttribute> _filter = IterableExtensions.<EAttribute>filter(_eAllAttributes, _function);
    Iterator<EAttribute> _iterator = _filter.iterator();
    EAttribute interfaceNameAttribute = _iterator.next();
    OperationInterface _createOperationInterface = RepositoryFactory.eINSTANCE.createOperationInterface();
    EClass _eClass_1 = _createOperationInterface.eClass();
    EList<EAttribute> _eAllAttributes_1 = _eClass_1.getEAllAttributes();
    final Function1<EAttribute, Boolean> _function_1 = new Function1<EAttribute, Boolean>() {
      public Boolean apply(final EAttribute attribute) {
        String _name = attribute.getName();
        return Boolean.valueOf(_name.equalsIgnoreCase("entityName"));
      }
    };
    Iterable<EAttribute> _filter_1 = IterableExtensions.<EAttribute>filter(_eAllAttributes_1, _function_1);
    Iterator<EAttribute> _iterator_1 = _filter_1.iterator();
    EAttribute opInterfaceNameAttribute = _iterator_1.next();
    this.featureCorrespondenceMap.put(interfaceNameAttribute, opInterfaceNameAttribute);
  }
}
