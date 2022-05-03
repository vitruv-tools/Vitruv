package tools.vitruv.framework.change.echange.command

import java.util.Collections
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.edit.command.RemoveCommand
import org.eclipse.emf.edit.domain.EditingDomain
import tools.vitruv.framework.change.echange.EChangeUtil

/**
 * Command which is used to remove a entry of a EList at a specific index.
 * Only single objects are supported.
 */
class RemoveAtCommand extends RemoveCommand {
	/**
	 * Unresolved index as initialized. This may also contain meta indeces as the {@code EChangeUtil.LAST_POSITION_INDEX}.
	 */
	var int index;

	/**
	 * Constructor for a RemoveAtCommand, which removes an entry of an EList feature at a specific index.
	 * @param editingDomain The used editing domain.
	 * @param owner The owning EObject of the EList.
	 * @param feature The feature of the owner which is an EList.
	 * @param value The value which will be removed.
	 * @param index Index at which the value is removed in the EList.
	 */
	new(EditingDomain editingDomain, EObject owner, EStructuralFeature feature, Object value, int index) {
		super(editingDomain, owner, feature, Collections.singleton(value));
		this.index = index;
	}

	/**
	 * Constructor for a RemoveATCommand, which removes an entry of a EList at a specific index.
	 * @param editingDomain The used editing domain.
	 * @param ownerList The edited EList.
	 * @param value The value which will be removed.
	 * @param index The Index at which the value is removed in the EList.
	 */
	new(EditingDomain editingDomain, EList<?> ownerList, Object value, int index) {
		super(editingDomain, ownerList, Collections.singleton(value));
		this.index = index;
	}

	/**
	 * Returns the unresolved index, which may contain meta indeces as the {@code EChangeUtil.LAST_POSITION_INDEX}.
	 * @return The unresolved index
	 */
	def int getUnresolvedIndex() {
		return this.index;
	}
	
	/**
	 * Returns the resolved index at which the value will be removed.
	 * @return The resolved index
	 */
	def int getResolvedIndex() {
		return if (index == EChangeUtil.LAST_POSITION_INDEX) ownerList.size - 1 else index
	}

	override void doExecute() {
		ownerList.remove(resolvedIndex)
	}

	override boolean prepare() {
		var result = super.prepare() && collection.size() == 1 && 0 <= resolvedIndex && resolvedIndex < ownerList.size()
			
		if (!result) {
			return false;
		}
		// Check if get(index) == object		
		return ownerList.get(resolvedIndex).equals(collection.get(0))
	}

	override void doUndo() {
		throw new UnsupportedOperationException();
	}

	override void doRedo() {
		throw new UnsupportedOperationException();
	}
}