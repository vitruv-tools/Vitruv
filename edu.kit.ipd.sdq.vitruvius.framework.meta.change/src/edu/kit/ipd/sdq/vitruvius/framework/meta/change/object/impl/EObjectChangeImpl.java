/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.EChangeImpl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.EObjectChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ObjectPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EObject Change</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public abstract class EObjectChangeImpl<T extends EObject> extends EChangeImpl implements EObjectChange<T> {
	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected EObjectChangeImpl() {
        super();
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	protected EClass eStaticClass() {
        return ObjectPackage.Literals.EOBJECT_CHANGE;
    }

} //EObjectChangeImpl
