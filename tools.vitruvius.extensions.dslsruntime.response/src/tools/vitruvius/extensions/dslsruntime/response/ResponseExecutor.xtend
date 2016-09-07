package tools.vitruvius.extensions.dslsruntime.response

import org.eclipse.emf.common.command.Command
import java.util.List
import tools.vitruvius.framework.change.echange.EChange
import tools.vitruvius.framework.correspondence.CorrespondenceModel

interface ResponseExecutor {
	public def List<Command> generateCommandsForEvent(EChange change, CorrespondenceModel correspondenceModel);
}