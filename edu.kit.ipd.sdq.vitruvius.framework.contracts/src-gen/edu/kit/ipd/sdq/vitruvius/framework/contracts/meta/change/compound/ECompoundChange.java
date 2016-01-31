/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ECompound Change</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#getECompoundChange()
 * @model abstract="true"
 * @generated
 */
public interface ECompoundChange extends EChange {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model kind="operation" required="true"
     * @generated
     */
    EList<EChange> getComposedChanges();

} // ECompoundChange
