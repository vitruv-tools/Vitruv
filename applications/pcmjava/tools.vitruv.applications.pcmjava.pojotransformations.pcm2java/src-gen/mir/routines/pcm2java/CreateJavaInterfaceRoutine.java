package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.classifiers.impl.ClassifiersFactoryImpl;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.emftext.language.java.modifiers.Public;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade effectFacade;
  
  private CreateJavaInterfaceRoutine.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className, final Interface javaInterface) {
      return javaInterface;
    }
    
    public void updateJavaInterfaceElement(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className, final Interface javaInterface) {
      javaInterface.setName(className);
      Public _createPublic = ModifiersFactory.eINSTANCE.createPublic();
      javaInterface.addModifier(_createPublic);
    }
    
    public EObject getElement2(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className, final Interface javaInterface) {
      return sourceElementMappedToClass;
    }
    
    public void callRoutine1(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className, final Interface javaInterface, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createCompilationUnit(sourceElementMappedToClass, javaInterface, containingPackage);
    }
  }
  
  public CreateJavaInterfaceRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2java.CreateJavaInterfaceRoutine.EffectUserExecution(getExecutionState(), this);
    this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    this.sourceElementMappedToClass = sourceElementMappedToClass;this.containingPackage = containingPackage;this.className = className;
  }
  
  private NamedElement sourceElementMappedToClass;
  
  private org.emftext.language.java.containers.Package containingPackage;
  
  private String className;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaInterfaceRoutine with input:");
    getLogger().debug("   NamedElement: " + this.sourceElementMappedToClass);
    getLogger().debug("   Package: " + this.containingPackage);
    getLogger().debug("   String: " + this.className);
    
    Interface javaInterface = ClassifiersFactoryImpl.eINSTANCE.createInterface();
    initializeCreateElementState(javaInterface);
    userExecution.updateJavaInterfaceElement(sourceElementMappedToClass, containingPackage, className, javaInterface);
    
    addCorrespondenceBetween(userExecution.getElement1(sourceElementMappedToClass, containingPackage, className, javaInterface), userExecution.getElement2(sourceElementMappedToClass, containingPackage, className, javaInterface), "");
    
    userExecution.callRoutine1(sourceElementMappedToClass, containingPackage, className, javaInterface, effectFacade);
    
    postprocessElementStates();
  }
}
