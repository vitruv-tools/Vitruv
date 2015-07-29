/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Correspondence</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class CorrespondenceImpl extends EObjectImpl implements Correspondence {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CorrespondenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorrespondencePackage.Literals.CORRESPONDENCE;
	}

} //CorrespondenceImpl
