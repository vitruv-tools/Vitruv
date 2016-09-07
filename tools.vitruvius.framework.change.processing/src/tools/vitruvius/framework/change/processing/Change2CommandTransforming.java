package tools.vitruvius.framework.change.processing;

import java.util.List;

import tools.vitruvius.framework.change.description.VitruviusChange;
import tools.vitruvius.framework.correspondence.CorrespondenceModel;
import tools.vitruvius.framework.userinteraction.UserInteracting;
import tools.vitruvius.framework.util.command.VitruviusRecordingCommand;
import tools.vitruvius.framework.util.datatypes.MetamodelPair;

public interface Change2CommandTransforming {
	String ExtensionPointID = "tools.vitruvius.framework.change.processing.change2commandtransforming";
	
	List<VitruviusRecordingCommand> transformChange2Commands(VitruviusChange change, CorrespondenceModel correspondenceModel);
    MetamodelPair getTransformableMetamodels();
    void setUserInteracting(UserInteracting userInteracting);
}
