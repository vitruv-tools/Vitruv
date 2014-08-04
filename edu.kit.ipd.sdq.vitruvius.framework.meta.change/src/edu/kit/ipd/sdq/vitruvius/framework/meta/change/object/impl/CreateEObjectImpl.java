/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ObjectPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Create EObject</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public abstract class CreateEObjectImpl<T extends EObject> extends EObjectChangeImpl<T> implements CreateEObject<T> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CreateEObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ObjectPackage.Literals.CREATE_EOBJECT;
	}

} //CreateEObjectImpl
