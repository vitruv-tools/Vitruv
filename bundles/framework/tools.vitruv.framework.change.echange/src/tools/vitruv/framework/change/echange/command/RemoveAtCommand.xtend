package tools.vitruv.framework.change.echange.command

import java.util.Collections
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.edit.command.RemoveCommand
import org.eclipse.emf.edit.domain.EditingDomain

/**
 * Command which is used to remove a entry of a EList at a specific index.
 * Only single objects are supported.
 */
public class RemoveAtCommand extends RemoveCommand {
	/**
	 * Index at which the value is removed in the list.
	 */
	var private int index;

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
	 * Returns the index at which the value will be removed.
	 * @return The index
	 */
	def public int getIndex() {
		return this.index;
	}

	override public void doExecute() {
		ownerList.remove(index);
	}

	override public boolean prepare() {
		var result = super.prepare() && 0 <= index && index < ownerList.size() && (collection.size() == 1);
		if (!result) {
			return false;
		}
		// Check if get(index) == object		
		return ownerList.get(index).equals(collection.get(0))
	}

	override void doUndo() {
		throw new UnsupportedOperationException();
	}

	override public void doRedo() {
		throw new UnsupportedOperationException();
	}
}
