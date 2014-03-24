/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Same Type Correspondence</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.SameTypeCorrespondenceImpl#getElementA <em>Element A</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.SameTypeCorrespondenceImpl#getElementB <em>Element B</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class SameTypeCorrespondenceImpl extends CorrespondenceImpl implements SameTypeCorrespondence {
	/**
     * The cached value of the '{@link #getElementA() <em>Element A</em>}' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getElementA()
     * @generated
     * @ordered
     */
	protected EObject elementA;

	/**
     * The cached value of the '{@link #getElementB() <em>Element B</em>}' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getElementB()
     * @generated
     * @ordered
     */
	protected EObject elementB;

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected SameTypeCorrespondenceImpl() {
        super();
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	protected EClass eStaticClass() {
        return CorrespondencePackage.Literals.SAME_TYPE_CORRESPONDENCE;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@SuppressWarnings("unchecked")
	public EObject getElementA() {
        if (elementA != null && elementA.eIsProxy()) {
            InternalEObject oldElementA = (InternalEObject)elementA;
            elementA = eResolveProxy(oldElementA);
            if (elementA != oldElementA) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, CorrespondencePackage.SAME_TYPE_CORRESPONDENCE__ELEMENT_A, oldElementA, elementA));
            }
        }
        return elementA;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EObject basicGetElementA() {
        return elementA;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setElementA(EObject newElementA) {
        EObject oldElementA = elementA;
        elementA = newElementA;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CorrespondencePackage.SAME_TYPE_CORRESPONDENCE__ELEMENT_A, oldElementA, elementA));
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@SuppressWarnings("unchecked")
	public EObject getElementB() {
        if (elementB != null && elementB.eIsProxy()) {
            InternalEObject oldElementB = (InternalEObject)elementB;
            elementB = eResolveProxy(oldElementB);
            if (elementB != oldElementB) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, CorrespondencePackage.SAME_TYPE_CORRESPONDENCE__ELEMENT_B, oldElementB, elementB));
            }
        }
        return elementB;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EObject basicGetElementB() {
        return elementB;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setElementB(EObject newElementB) {
        EObject oldElementB = elementB;
        elementB = newElementB;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CorrespondencePackage.SAME_TYPE_CORRESPONDENCE__ELEMENT_B, oldElementB, elementB));
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case CorrespondencePackage.SAME_TYPE_CORRESPONDENCE__ELEMENT_A:
                if (resolve) return getElementA();
                return basicGetElementA();
            case CorrespondencePackage.SAME_TYPE_CORRESPONDENCE__ELEMENT_B:
                if (resolve) return getElementB();
                return basicGetElementB();
        }
        return super.eGet(featureID, resolve, coreType);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case CorrespondencePackage.SAME_TYPE_CORRESPONDENCE__ELEMENT_A:
                setElementA((EObject)newValue);
                return;
            case CorrespondencePackage.SAME_TYPE_CORRESPONDENCE__ELEMENT_B:
                setElementB((EObject)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public void eUnset(int featureID) {
        switch (featureID) {
            case CorrespondencePackage.SAME_TYPE_CORRESPONDENCE__ELEMENT_A:
                setElementA((EObject)null);
                return;
            case CorrespondencePackage.SAME_TYPE_CORRESPONDENCE__ELEMENT_B:
                setElementB((EObject)null);
                return;
        }
        super.eUnset(featureID);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public boolean eIsSet(int featureID) {
        switch (featureID) {
            case CorrespondencePackage.SAME_TYPE_CORRESPONDENCE__ELEMENT_A:
                return elementA != null;
            case CorrespondencePackage.SAME_TYPE_CORRESPONDENCE__ELEMENT_B:
                return elementB != null;
        }
        return super.eIsSet(featureID);
    }

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<EObject> getAllInvolvedEObjects() {
		BasicEList<EObject> involvedEObjects = new BasicEList<EObject>();
		if (elementA instanceof EObject) {
			involvedEObjects.add((EObject) elementA);
		}
		if (elementB instanceof EObject) {
			involvedEObjects.add((EObject) elementB);
		}
		return involvedEObjects;
	}
} //SameTypeCorrespondenceImpl
