package mir.responses.responsesJavaTo5_1.parserIntegrationResponse;

import org.emftext.language.java.JavaPackage;
import org.palladiosimulator.pcm.PcmPackage;

import tools.vitruv.extensions.dslsruntime.response.AbstractResponseExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;
import tools.vitruv.framework.util.datatypes.MetamodelPair;

@SuppressWarnings("all")
public class ExecutorJavaTo5_1 extends AbstractResponseExecutor {
  private final MetamodelPair metamodelPair;
	
  public ExecutorJavaTo5_1(final UserInteracting userInteracting) {
    super(userInteracting);
	this.metamodelPair = new MetamodelPair(JavaPackage.eNS_URI, PcmPackage.eNS_URI);
  }
  
  @Override
  public MetamodelPair getMetamodelPair() {
    return metamodelPair;
  }
  
  protected void setup() {
    this.addResponse(mir.responses.responsesJavaTo5_1.parserIntegrationResponse.ChangeFieldModifierEventParserResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.parserIntegrationResponse.ChangeFieldModifierEventParserResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.parserIntegrationResponse.RemoveFieldModifierEventParserResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.parserIntegrationResponse.RemoveFieldModifierEventParserResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.parserIntegrationResponse.AddMethodEventParserResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.parserIntegrationResponse.AddMethodEventParserResponse(userInteracting));
  }
}
