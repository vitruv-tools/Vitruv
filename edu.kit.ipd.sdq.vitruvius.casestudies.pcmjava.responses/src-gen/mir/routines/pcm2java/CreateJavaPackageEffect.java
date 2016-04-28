package mir.routines.pcm2java;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.pcm2java.Pcm2JavaHelper;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.containers.impl.ContainersFactoryImpl;

@SuppressWarnings("all")
public class CreateJavaPackageEffect extends AbstractEffectRealization {
  public CreateJavaPackageEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private EObject sourceElementMappedToPackage;
  
  private org.emftext.language.java.containers.Package parentPackage;
  
  private String packageName;
  
  private String newTag;
  
  private boolean isSourceElementMappedToPackageSet;
  
  private boolean isParentPackageSet;
  
  private boolean isPackageNameSet;
  
  private boolean isNewTagSet;
  
  public void setSourceElementMappedToPackage(final EObject sourceElementMappedToPackage) {
    this.sourceElementMappedToPackage = sourceElementMappedToPackage;
    this.isSourceElementMappedToPackageSet = true;
  }
  
  public void setParentPackage(final org.emftext.language.java.containers.Package parentPackage) {
    this.parentPackage = parentPackage;
    this.isParentPackageSet = true;
  }
  
  public void setPackageName(final String packageName) {
    this.packageName = packageName;
    this.isPackageNameSet = true;
  }
  
  public void setNewTag(final String newTag) {
    this.newTag = newTag;
    this.isNewTagSet = true;
  }
  
  private EObject getElement0(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag, final org.emftext.language.java.containers.Package javaPackage) {
    return javaPackage;
  }
  
  private EObject getElement1(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag, final org.emftext.language.java.containers.Package javaPackage) {
    return sourceElementMappedToPackage;
  }
  
  public boolean allParametersSet() {
    return isSourceElementMappedToPackageSet&&isParentPackageSet&&isPackageNameSet&&isNewTagSet;
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
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine CreateJavaPackageEffect with input:");
    getLogger().debug("   EObject: " + this.sourceElementMappedToPackage);
    getLogger().debug("   Package: " + this.parentPackage);
    getLogger().debug("   String: " + this.packageName);
    getLogger().debug("   String: " + this.newTag);
    
    initializeRetrieveElementState(
    	() -> getCorrepondenceSourcenull(sourceElementMappedToPackage, parentPackage, packageName, newTag), // correspondence source supplier
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	() -> getRetrieveTag0(sourceElementMappedToPackage, parentPackage, packageName, newTag), // tag supplier
    	org.emftext.language.java.containers.Package.class,
    	false, false, true);
    if (isAborted()) {
    	return;
    }
    org.emftext.language.java.containers.Package javaPackage = ContainersFactoryImpl.eINSTANCE.createPackage();
    initializeCreateElementState(javaPackage);
    
    addCorrespondenceBetween(getElement0(sourceElementMappedToPackage, parentPackage, packageName, newTag, javaPackage), getElement1(sourceElementMappedToPackage, parentPackage, packageName, newTag, javaPackage), getTag0(sourceElementMappedToPackage, parentPackage, packageName, newTag, javaPackage));
    preProcessElements();
    new mir.routines.pcm2java.CreateJavaPackageEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	sourceElementMappedToPackage, parentPackage, packageName, newTag, javaPackage);
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
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
