package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime

import org.eclipse.emf.common.command.Command
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChange

interface Change2CommandTransformingPreprocessor {
	public def boolean doesProcess(VitruviusChange change);
	public def Iterable<Command> processChange(VitruviusChange change, UserInteracting userInteracting, Blackboard blackboard);
}