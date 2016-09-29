package tools.vitruv.framework.change.processing;

import tools.vitruv.framework.change.description.TransactionalChange;
import tools.vitruv.framework.correspondence.CorrespondenceModel;
import tools.vitruv.framework.userinteraction.UserInteracting;
import tools.vitruv.framework.util.command.TransformationResult;
import tools.vitruv.framework.util.datatypes.MetamodelPair;

public interface Change2CommandTransforming {
	String ExtensionPointID = "tools.vitruv.framework.change.processing.change2commandtransforming";
	
	TransformationResult transformChange2Commands(TransactionalChange change, CorrespondenceModel correspondenceModel);
    MetamodelPair getTransformableMetamodels();
    void setUserInteracting(UserInteracting userInteracting);
}
