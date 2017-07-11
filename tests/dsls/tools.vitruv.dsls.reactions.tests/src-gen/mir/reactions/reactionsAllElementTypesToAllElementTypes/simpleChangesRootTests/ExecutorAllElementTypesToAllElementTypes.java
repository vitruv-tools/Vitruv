package mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesRootTests;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.testutils.domains.AllElementTypesDomainProvider;

@SuppressWarnings("all")
public class ExecutorAllElementTypesToAllElementTypes extends AbstractReactionsExecutor {
  public ExecutorAllElementTypesToAllElementTypes() {
    super(new AllElementTypesDomainProvider().getDomain(), 
    	new AllElementTypesDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesRootTests.CreateRootTestReaction.getExpectedChangeType(), new mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesRootTests.CreateRootTestReaction());
    this.addReaction(mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesRootTests.DeleteRootTestReaction.getExpectedChangeType(), new mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesRootTests.DeleteRootTestReaction());
  }
}
