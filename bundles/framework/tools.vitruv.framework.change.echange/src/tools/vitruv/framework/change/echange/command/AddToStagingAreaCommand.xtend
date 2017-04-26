package tools.vitruv.framework.change.echange.command

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.edit.command.AbstractOverrideableCommand
import org.eclipse.emf.edit.domain.EditingDomain
import tools.vitruv.framework.change.echange.resolve.StagingArea

/**
 * Command which inserts a new value to a staging area.
 */
class AddToStagingAreaCommand extends AbstractOverrideableCommand {
	private StagingArea stagingArea
	private EObject object
	
	/**
	 * Constructor of {@link AddToStagingAreaCommand}
	 * @param domain 		Editing domain of the command.
	 * @param stagingArea 	The staging area to which the object is added.
	 * @param object		The object which is added to the staging area.
	 * 						Will be added at the back of the staging area.
	 */
	public new(EditingDomain domain, StagingArea stagingArea, EObject object) {
		super(domain)
		this.stagingArea = stagingArea
		this.object = object
	}
	
	override doExecute() {
		stagingArea.add(object)
	}
	
	override doRedo() {
		throw new UnsupportedOperationException()
	}
	
	override doUndo() {
		throw new UnsupportedOperationException()
	}

	override public boolean prepare() {
		return stagingArea !== null && object !== null
	}	
}