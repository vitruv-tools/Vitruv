package mir.routines.pcm2java;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import tools.vitruvius.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaHelper;
import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.containers.impl.ContainersFactoryImpl;

@SuppressWarnings("all")
public class CreateJavaPackageEffect extends AbstractEffectRealization {
  public CreateJavaPackageEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag) {
    super(responseExecutionState, calledBy);
    				this.sourceElementMappedToPackage = sourceElementMappedToPackage;this.parentPackage = parentPackage;this.packageName = packageName;this.newTag = newTag;
  }
  
  private EObject sourceElementMappedToPackage;
  
  private org.emftext.language.java.containers.Package parentPackage;
  
  private String packageName;
  
  private String newTag;
  
  private EObject getElement0(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag, final org.emftext.language.java.containers.Package javaPackage) {
    return javaPackage;
  }
  
  private EObject getElement1(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag, final org.emftext.language.java.containers.Package javaPackage) {
    return sourceElementMappedToPackage;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaPackageEffect with input:");
    getLogger().debug("   EObject: " + this.sourceElementMappedToPackage);
    getLogger().debug("   Package: " + this.parentPackage);
    getLogger().debug("   String: " + this.packageName);
    getLogger().debug("   String: " + this.newTag);
    
    if (getCorrespondingElement(
    	getCorrepondenceSourcenull(sourceElementMappedToPackage, parentPackage, packageName, newTag), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	getRetrieveTag0(sourceElementMappedToPackage, parentPackage, packageName, newTag)) != null) {
    	return;
    }
    org.emftext.language.java.containers.Package javaPackage = ContainersFactoryImpl.eINSTANCE.createPackage();
    initializeCreateElementState(javaPackage);
    
    addCorrespondenceBetween(getElement0(sourceElementMappedToPackage, parentPackage, packageName, newTag, javaPackage), getElement1(sourceElementMappedToPackage, parentPackage, packageName, newTag, javaPackage), getTag0(sourceElementMappedToPackage, parentPackage, packageName, newTag, javaPackage));
    preprocessElementStates();
    new mir.routines.pcm2java.CreateJavaPackageEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	sourceElementMappedToPackage, parentPackage, packageName, newTag, javaPackage);
    postprocessElementStates();
  }
  
  private String getRetrieveTag0(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag) {
    return newTag;
  }
  
  private String getTag0(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag, final org.emftext.language.java.containers.Package javaPackage) {
    return newTag;
  }
  
  private EObject getCorrepondenceSourcenull(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag) {
    return sourceElementMappedToPackage;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag, final org.emftext.language.java.containers.Package javaPackage) {
      boolean _notEquals = (!Objects.equal(parentPackage, null));
      if (_notEquals) {
        EList<String> _namespaces = javaPackage.getNamespaces();
        EList<String> _namespaces_1 = parentPackage.getNamespaces();
        Iterables.<String>addAll(_namespaces, _namespaces_1);
        EList<String> _namespaces_2 = javaPackage.getNamespaces();
        String _name = parentPackage.getName();
        _namespaces_2.add(_name);
      }
      javaPackage.setName(packageName);
      String _buildJavaFilePath = Pcm2JavaHelper.buildJavaFilePath(javaPackage);
      this.persistProjectRelative(sourceElementMappedToPackage, javaPackage, _buildJavaFilePath);
    }
  }
}
