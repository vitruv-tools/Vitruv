package tools.vitruv.framework.util.command;

import java.util.Collections;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * Command which is used to remove entries of a EList at a specific index.
 */
public class RemoveAtCommand extends RemoveCommand {
	// TODO Stefan: Tests
	/**
	 * Creates a new RemoveAtCommand.
	 * @param editing Domain The used editing domain.
	 * @param owner The owning EObject of the List.
	 * @param feature The feature of the owner which is an EList.
	 * @param value The value which will be removed.
	 * @param index Index at which the value is removed in the EList.
	 * @return The RemoveAtCommand which removes entries of a EList at a specific index.
	 */
	public static Command create(final EditingDomain editingDomain, final EObject owner, final EStructuralFeature feature, final Object value, final int index) {
		return new RemoveAtCommand(editingDomain, owner, feature, value, index);
	}
	
	/**
	 * Creates a new RemoveAtCommand.
	 * @param editing Domain The used editing domain.
	 * @param ownerList The editied EList.
	 * @param value The value which will be removed.
	 * @param index Index at which the value is removed in the EList.
	 * @return The RemoveAtCommand which removes entries of a EList at a specific index.
	 */
	public static Command create(final EditingDomain editingDomain, final EList<?> ownerList, final Object value, final int index) {
		return new RemoveAtCommand(editingDomain, ownerList, value, index);
	}
	/**
	 * Index at which the value is removed in the list.
	 */
	private int index;
	
	/**
	 * Constructor for a RemoveAtCommand, which removes an entry of an EList feature at a specific index.
	 * @param editingDomain The used editing domain.
	 * @param owner The owning EObject of the EList.
	 * @param feature The feature of the owner which is an EList.
	 * @param value The value which will be removed.
	 * @param index Index at which the value is removed in the EList.
	 */
	public RemoveAtCommand(EditingDomain editingDomain, EObject owner, EStructuralFeature feature, Object value, int index) {
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
	public RemoveAtCommand(EditingDomain editingDomain, EList<?> ownerList, Object value, int index) {
		super(editingDomain, ownerList, Collections.singleton(value));
		this.index = index;
	}
	
	/**
	 * Returns the index at which the value will be removed.
	 * @return The index
	 */
	public int getIndex() {
		return this.index;
	}
	
	@Override
	public void doExecute() {
		ownerList.remove(index);
	}
	
	@Override
	public boolean prepare() {
		boolean result = super.prepare() &&
				0 <= index && index < ownerList.size();
	    EGenericType eType = owner == null ? feature.getEGenericType() : owner.eClass().getFeatureType(feature);
	    for (Object object : collection) {
	        if (!eType.isInstance(object)) {
	        	result = false;
	        }
	    }  	
		return result;
	}
	
	@Override
	public void doUndo() {
		for(Object object : collection) {
			ownerList.add(index, object);
		}
	}
	
	@Override
	public void doRedo() {
		ownerList.remove(index);
	}
}
