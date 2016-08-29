package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime

import org.eclipse.emf.common.command.Command
import java.util.List
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.EChange
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel

interface ResponseExecutor {
	public def List<Command> generateCommandsForEvent(EChange change, CorrespondenceModel correspondenceModel);
}