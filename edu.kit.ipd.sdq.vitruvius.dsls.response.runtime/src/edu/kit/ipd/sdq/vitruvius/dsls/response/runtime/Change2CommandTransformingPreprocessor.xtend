package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change
import org.eclipse.emf.common.command.Command
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting

interface Change2CommandTransformingPreprocessor {
	public def boolean doesProcess(Change change);
	public def Iterable<Command> processChange(Change change, UserInteracting userInteracting, Blackboard blackboard);
}