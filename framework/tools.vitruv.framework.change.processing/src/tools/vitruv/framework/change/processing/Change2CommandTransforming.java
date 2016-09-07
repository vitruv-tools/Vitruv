package tools.vitruv.framework.change.processing;

import java.util.List;

import tools.vitruv.framework.change.description.VitruviusChange;
import tools.vitruv.framework.correspondence.CorrespondenceModel;
import tools.vitruv.framework.userinteraction.UserInteracting;
import tools.vitruv.framework.util.command.VitruviusRecordingCommand;
import tools.vitruv.framework.util.datatypes.MetamodelPair;

public interface Change2CommandTransforming {
	String ExtensionPointID = "tools.vitruv.framework.change.processing.change2commandtransforming";
	
	List<VitruviusRecordingCommand> transformChange2Commands(VitruviusChange change, CorrespondenceModel correspondenceModel);
    MetamodelPair getTransformableMetamodels();
    void setUserInteracting(UserInteracting userInteracting);
}
