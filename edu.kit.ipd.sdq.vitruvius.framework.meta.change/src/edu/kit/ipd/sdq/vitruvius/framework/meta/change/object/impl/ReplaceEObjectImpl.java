/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ObjectPackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ReplaceEObject;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Replace EObject</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public abstract class ReplaceEObjectImpl<T extends EObject> extends EObjectChangeImpl<T> implements ReplaceEObject<T> {
	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected ReplaceEObjectImpl() {
        super();
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	protected EClass eStaticClass() {
        return ObjectPackage.Literals.REPLACE_EOBJECT;
    }

} //ReplaceEObjectImpl
