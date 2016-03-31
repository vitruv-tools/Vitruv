package mir.effects.pcm2java;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.pcm2java.Pcm2JavaHelper;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import java.io.IOException;
import mir.effects.pcm2java.EffectsFacade;
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
  
  private String getTagJavaPackage(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag) {
    return newTag;
  }
  
  public boolean allParametersSet() {
    return isSourceElementMappedToPackageSet&&isParentPackageSet&&isPackageNameSet&&isNewTagSet;
  }
  
  private EObject getCorrepondenceSourceJavaPackage(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag) {
    return sourceElementMappedToPackage;
  }
  
  private String getModelPath(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag, final org.emftext.language.java.containers.Package javaPackage) {
    String _buildJavaFilePath = Pcm2JavaHelper.buildJavaFilePath(javaPackage);
    return _buildJavaFilePath;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect CreateJavaPackageEffect with input:");
    getLogger().debug("   EObject: " + this.sourceElementMappedToPackage);
    getLogger().debug("   Package: " + this.parentPackage);
    getLogger().debug("   String: " + this.packageName);
    getLogger().debug("   String: " + this.newTag);
    
    org.emftext.language.java.containers.Package javaPackage = initializeCreateElementState(
    	() -> getCorrepondenceSourceJavaPackage(sourceElementMappedToPackage, parentPackage, packageName, newTag), // correspondence source supplier
    	() -> ContainersFactoryImpl.eINSTANCE.createPackage(), // element creation supplier
    	() -> getTagJavaPackage(sourceElementMappedToPackage, parentPackage, packageName, newTag), // tag supplier
    	org.emftext.language.java.containers.Package.class);
    setPersistenceInformation(javaPackage, () -> getModelPath(sourceElementMappedToPackage, parentPackage, packageName, newTag, javaPackage), false);
    if (isAborted()) return;
    preProcessElements();
    new mir.effects.pcm2java.CreateJavaPackageEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	sourceElementMappedToPackage, parentPackage, packageName, newTag, javaPackage);
    postProcessElements();
  }
  
  private static class EffectUserExecution {
    private Blackboard blackboard;
    
    private UserInteracting userInteracting;
    
    private TransformationResult transformationResult;
    
    @Extension
    private EffectsFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      this.blackboard = responseExecutionState.getBlackboard();
      this.userInteracting = responseExecutionState.getUserInteracting();
      this.transformationResult = responseExecutionState.getTransformationResult();
      this.effectFacade = new EffectsFacade(responseExecutionState, calledBy);
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
    }
  }
}
