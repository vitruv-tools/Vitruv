/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Additive Change</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage#getAdditiveChange()
 * @model abstract="true" TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface AdditiveChange<T extends Object> extends EChange {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model kind="operation" required="true"
     * @generated
     */
    T getNewValue();

} // AdditiveChange
