package edu.kit.ipd.sdq.vitruvius.framework.change.processing;

import java.util.List;

import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChange;
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel;
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.util.command.VitruviusRecordingCommand;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.MetamodelPair;

public interface Change2CommandTransforming {
	String ExtensionPointID = "edu.kit.ipd.sdq.vitruvius.framework.contracts.change2commandtransforming";
	
	List<VitruviusRecordingCommand> transformChange2Commands(VitruviusChange change, CorrespondenceModel correspondenceModel);
    MetamodelPair getTransformableMetamodels();
    void setUserInteracting(UserInteracting userInteracting);
}
