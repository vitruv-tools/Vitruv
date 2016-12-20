/**
 */
package tools.vitruv.framework.change.echange.compound;

import org.eclipse.emf.common.util.EList;

import tools.vitruv.framework.change.echange.AtomicEChange;
import tools.vitruv.framework.change.echange.EChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getCompoundEChange()
 * @model abstract="true"
 * @generated
 */
public interface CompoundEChange extends EChange {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	EList<AtomicEChange> getAtomicChanges();

} // CompoundEChange
