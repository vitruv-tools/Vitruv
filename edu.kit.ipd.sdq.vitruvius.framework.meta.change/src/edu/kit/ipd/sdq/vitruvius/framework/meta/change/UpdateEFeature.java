/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Update EFeature</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangePackage#getUpdateEFeature()
 * @model abstract="true" TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface UpdateEFeature<T extends Object> extends EObject {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model kind="operation" required="true"
     * @generated
     */
    T getNewValue();

} // UpdateEFeature
