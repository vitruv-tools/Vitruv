package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm;

import com.google.common.base.Objects;
import de.uka.ipd.sdq.pcm.repository.OperationInterface;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.EObjectMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;

@SuppressWarnings("all")
public class InterfaceMappingTransformation extends EObjectMappingTransformation {
  private final static Logger logger = new Function0<Logger>() {
    public Logger apply() {
      String _name = InterfaceMappingTransformation.class.getName();
      Logger _logger = Logger.getLogger(_name);
      return _logger;
    }
  }.apply();
  
  public Class<? extends Object> getClassOfMappedEObject() {
    return Interface.class;
  }
  
  /**
   * Called when a Java-interface was added to the source code
   * Determines whether the interface is architecture relevant or not by
   * a) checking whether it is in the package that corresponds to the repository package
   * b) asking the developer (not yet implmented)
   */
  public EObject addEObject(final EObject eObject) {
    final Interface jaMoPPInterface = ((Interface) eObject);
    final List<String> namespace = jaMoPPInterface.getContainingPackageName();
    final org.emftext.language.java.containers.Package jaMoPPPackage = null;
    try {
      final Repository repo = this.correspondenceInstance.<Repository>claimCorrespondingEObjectByTypeIfUnique(jaMoPPPackage, Repository.class);
      OperationInterface opInterface = RepositoryFactory.eINSTANCE.createOperationInterface();
      String _name = jaMoPPInterface.getName();
      opInterface.setEntityName(_name);
      opInterface.setRepository__Interface(repo);
      EObjectCorrespondence interface2opInterfaceCorrespondence = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence();
      interface2opInterfaceCorrespondence.setElementA(jaMoPPInterface);
      interface2opInterfaceCorrespondence.setElementB(opInterface);
      this.correspondenceInstance.addSameTypeCorrespondence(interface2opInterfaceCorrespondence);
      EObjectCorrespondence compilationUnit2opInterfaceCorrespondence = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence();
      CompilationUnit _containingCompilationUnit = jaMoPPInterface.getContainingCompilationUnit();
      compilationUnit2opInterfaceCorrespondence.setElementA(_containingCompilationUnit);
      compilationUnit2opInterfaceCorrespondence.setElementB(opInterface);
      return opInterface;
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
  public EObject removeEObject(final EObject eObject) {
    final Interface jaMoPPInterface = ((Interface) eObject);
    final CompilationUnit jaMoPPCompilationUnit = jaMoPPInterface.getContainingCompilationUnit();
    EObject correspondingOpInterface = this.correspondenceInstance.getCorrespondeceForEObjectIfUnique(jaMoPPInterface);
    EObject correspondingOpInterface2CompilationUnit = this.correspondenceInstance.getCorrespondeceForEObjectIfUnique(jaMoPPCompilationUnit);
    boolean _and = false;
    boolean _equals = Objects.equal(null, correspondingOpInterface);
    if (!_equals) {
      _and = false;
    } else {
      boolean _equals_1 = Objects.equal(null, correspondingOpInterface2CompilationUnit);
      _and = (_equals && _equals_1);
    }
    if (_and) {
      return null;
    }
    boolean _notEquals = (!Objects.equal(correspondingOpInterface, correspondingOpInterface2CompilationUnit));
    if (_notEquals) {
      String _plus = ("corresponding interface " + correspondingOpInterface);
      String _plus_1 = (_plus + "is not the same interface as the interface corresponding to the compilation unit ");
      String _plus_2 = (_plus_1 + correspondingOpInterface2CompilationUnit);
      InterfaceMappingTransformation.logger.warn(_plus_2);
      return null;
    }
    EcoreUtil.remove(correspondingOpInterface);
    this.correspondenceInstance.removeAllCorrespondingInstances(jaMoPPInterface);
    return correspondingOpInterface;
  }
  
  public EObject updateEAttribute(final EObject eObject, final EAttribute affectedAttribute, final Object newValue) {
    final Interface jaMoPPInterface = ((Interface) eObject);
    OperationInterface opInterface = this.correspondenceInstance.<OperationInterface>claimCorrespondingEObjectByTypeIfUnique(jaMoPPInterface, OperationInterface.class);
    final EStructuralFeature attribute = this.featureCorrespondenceMap.claimValueForKey(affectedAttribute);
    opInterface.eSet(attribute, newValue);
    return opInterface;
  }
  
  public EObject updateEReference(final EObject eObject, final EReference affectedEReference, final Object newValue) {
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
  
  public EObject updateEContainmentReference(final EObject eObject, final EReference affectedEReference, final Object newValue) {
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
    final Function1<EAttribute,Boolean> _function = new Function1<EAttribute,Boolean>() {
      public Boolean apply(final EAttribute attribute) {
        String _name = attribute.getName();
        boolean _equalsIgnoreCase = _name.equalsIgnoreCase("name");
        return Boolean.valueOf(_equalsIgnoreCase);
      }
    };
    Iterable<EAttribute> _filter = IterableExtensions.<EAttribute>filter(_eAllAttributes, _function);
    Iterator<EAttribute> _iterator = _filter.iterator();
    EAttribute interfaceNameAttribute = _iterator.next();
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
    this.featureCorrespondenceMap.put(interfaceNameAttribute, opInterfaceNameAttribute);
  }
}
