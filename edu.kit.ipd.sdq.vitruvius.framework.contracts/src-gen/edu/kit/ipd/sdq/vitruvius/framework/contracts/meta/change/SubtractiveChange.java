/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Subtractive Change</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage#getSubtractiveChange()
 * @model abstract="true" TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface SubtractiveChange<T extends Object> extends EChange {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model kind="operation" required="true"
     * @generated
     */
    T getOldValue();

} // SubtractiveChange
