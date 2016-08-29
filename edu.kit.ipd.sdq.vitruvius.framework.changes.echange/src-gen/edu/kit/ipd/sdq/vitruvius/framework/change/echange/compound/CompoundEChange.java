/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.change.echange.compound;

import edu.kit.ipd.sdq.vitruvius.framework.change.echange.AtomicEChange;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.EChange;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.change.echange.compound.CompoundPackage#getCompoundEChange()
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
