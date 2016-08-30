package edu.kit.ipd.sdq.vitruvius.framework.change.processing;

import java.util.List;

import org.eclipse.emf.common.command.Command;

import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChange;
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel;
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.MetamodelPair;

public interface Change2CommandTransforming {
    List<Command> transformChange2Commands(VitruviusChange change, CorrespondenceModel correspondenceModel);
    MetamodelPair getTransformableMetamodels();
    void setUserInteracting(UserInteracting userInteracting);
}
