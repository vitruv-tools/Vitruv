package mir.routines.pcm2java;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import tools.vitruv.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaHelper;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameJavaPackageEffect extends AbstractEffectRealization {
  private RoutinesFacade effectFacade;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final NamedElement sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String expectedTag, final org.emftext.language.java.containers.Package javaPackage) {
      return javaPackage;
    }
    
    public void update0Element(final NamedElement sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String expectedTag, final org.emftext.language.java.containers.Package javaPackage) {
      EList<String> _namespaces = javaPackage.getNamespaces();
      _namespaces.clear();
      boolean _notEquals = (!Objects.equal(parentPackage, null));
      if (_notEquals) {
        EList<String> _namespaces_1 = javaPackage.getNamespaces();
        EList<String> _namespaces_2 = parentPackage.getNamespaces();
        Iterables.<String>addAll(_namespaces_1, _namespaces_2);
        EList<String> _namespaces_3 = javaPackage.getNamespaces();
        String _name = parentPackage.getName();
        _namespaces_3.add(_name);
      }
      javaPackage.setName(packageName);
      String _buildJavaFilePath = Pcm2JavaHelper.buildJavaFilePath(javaPackage);
      this.persistProjectRelative(sourceElementMappedToPackage, javaPackage, _buildJavaFilePath);
    }
    
    public String getRetrieveTag1(final NamedElement sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String expectedTag) {
      return expectedTag;
    }
    
    public EObject getCorrepondenceSourceJavaPackage(final NamedElement sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String expectedTag) {
      return sourceElementMappedToPackage;
    }
  }
  
  private RenameJavaPackageEffect.EffectUserExecution userExecution;
  
  public RenameJavaPackageEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final NamedElement sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String expectedTag) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.pcm2java.RenameJavaPackageEffect.EffectUserExecution(getExecutionState(), this);
    				this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    				this.sourceElementMappedToPackage = sourceElementMappedToPackage;this.parentPackage = parentPackage;this.packageName = packageName;this.expectedTag = expectedTag;
  }
  
  private NamedElement sourceElementMappedToPackage;
  
  private org.emftext.language.java.containers.Package parentPackage;
  
  private String packageName;
  
  private String expectedTag;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameJavaPackageEffect with input:");
    getLogger().debug("   NamedElement: " + this.sourceElementMappedToPackage);
    getLogger().debug("   Package: " + this.parentPackage);
    getLogger().debug("   String: " + this.packageName);
    getLogger().debug("   String: " + this.expectedTag);
    
    org.emftext.language.java.containers.Package javaPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaPackage(sourceElementMappedToPackage, parentPackage, packageName, expectedTag), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(sourceElementMappedToPackage, parentPackage, packageName, expectedTag));
    if (javaPackage == null) {
    	return;
    }
    initializeRetrieveElementState(javaPackage);
    // val updatedElement userExecution.getElement1(sourceElementMappedToPackage, parentPackage, packageName, expectedTag, javaPackage);
    userExecution.update0Element(sourceElementMappedToPackage, parentPackage, packageName, expectedTag, javaPackage);
    
    preprocessElementStates();
    postprocessElementStates();
  }
}
