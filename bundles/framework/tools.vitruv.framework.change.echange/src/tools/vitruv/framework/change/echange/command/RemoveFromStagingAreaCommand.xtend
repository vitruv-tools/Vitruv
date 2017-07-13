package tools.vitruv.framework.change.echange.command

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.resolve.StagingArea
import org.eclipse.emf.edit.command.AbstractOverrideableCommand
import org.eclipse.emf.edit.domain.EditingDomain

/**
 * Command which removes the first object from a staging area.
 */
class RemoveFromStagingAreaCommand extends AbstractOverrideableCommand {
	private StagingArea stagingArea
	private EObject object
	
	/**
	 * Constructor for the {@link RemoveFromStagingAreaCommand}.
	 * @param domain		The editing domain.
	 * @param stagingArea	The staging area from which the object will be removed.
	 * @param object		The object which is removed from the staging area. 
	 * 						Must be the first object of the staging area.
	 */
	public new(EditingDomain domain, StagingArea stagingArea, EObject object) {
		super(domain)
		this.stagingArea = stagingArea
		this.object = object
	}
	
	override doExecute() {
		stagingArea.poll
	}
	
	override doRedo() {
		throw new UnsupportedOperationException
	}
	
	override doUndo() {
		throw new UnsupportedOperationException
	}
	
	override public boolean prepare() {
		return stagingArea !== null && object !== null
			&& !stagingArea.empty && stagingArea.peek == object
	}
}