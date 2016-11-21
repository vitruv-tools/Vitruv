package mir.reactions.reactionsUMLToJava.umlToJava;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorUMLToJava extends AbstractReactionsExecutor {
  public ExecutorUMLToJava(final UserInteracting userInteracting) {
    super(userInteracting, new tools.vitruv.framework.util.datatypes.MetamodelPair(org.eclipse.uml2.uml.internal.impl.UMLPackageImpl.eNS_URI, org.emftext.language.java.impl.JavaPackageImpl.eNS_URI));
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.CreatedUmlClassReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.CreatedUmlClassReaction(userInteracting));
  }
}
