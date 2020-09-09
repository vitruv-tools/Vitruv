package tools.vitruv.extensions.dslsruntime.reactions

import org.eclipse.emf.common.command.Command
import java.util.List
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.correspondence.CorrespondenceModel

interface ReactionsExecutor {
	def List<Command> generateCommandsForEvent(EChange change, CorrespondenceModel correspondenceModel);
}