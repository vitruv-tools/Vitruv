package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime

import org.eclipse.emf.common.command.Command
import java.util.List
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange

interface ResponseExecutor {
	public def List<Command> generateCommandsForEvent(EChange change, CorrespondenceInstance<Correspondence> correspondenceInstance);
}