/**
 */
package multicontainment_b.impl;

import multicontainment_b.ChildB1;
import multicontainment_b.ChildB2;
import multicontainment_b.Multicontainment_bPackage;
import multicontainment_b.RootB;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Root B</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link multicontainment_b.impl.RootBImpl#getChildrenB1a <em>Children B1a</em>}</li>
 *   <li>{@link multicontainment_b.impl.RootBImpl#getChildrenB1b <em>Children B1b</em>}</li>
 *   <li>{@link multicontainment_b.impl.RootBImpl#getChildrenB2a <em>Children B2a</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RootBImpl extends IdentifiedImpl implements RootB {
	/**
	 * The cached value of the '{@link #getChildrenB1a() <em>Children B1a</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildrenB1a()
	 * @generated
	 * @ordered
	 */
	protected ChildB1 childrenB1a;

	/**
	 * The cached value of the '{@link #getChildrenB1b() <em>Children B1b</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildrenB1b()
	 * @generated
	 * @ordered
	 */
	protected ChildB1 childrenB1b;

	/**
	 * The cached value of the '{@link #getChildrenB2a() <em>Children B2a</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildrenB2a()
	 * @generated
	 * @ordered
	 */
	protected ChildB2 childrenB2a;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RootBImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return Multicontainment_bPackage.Literals.ROOT_B;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChildB1 getChildrenB1a() {
		return childrenB1a;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetChildrenB1a(ChildB1 newChildrenB1a, NotificationChain msgs) {
		ChildB1 oldChildrenB1a = childrenB1a;
		childrenB1a = newChildrenB1a;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, Multicontainment_bPackage.ROOT_B__CHILDREN_B1A, oldChildrenB1a, newChildrenB1a);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChildrenB1a(ChildB1 newChildrenB1a) {
		if (newChildrenB1a != childrenB1a) {
			NotificationChain msgs = null;
			if (childrenB1a != null)
				msgs = ((InternalEObject)childrenB1a).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Multicontainment_bPackage.ROOT_B__CHILDREN_B1A, null, msgs);
			if (newChildrenB1a != null)
				msgs = ((InternalEObject)newChildrenB1a).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Multicontainment_bPackage.ROOT_B__CHILDREN_B1A, null, msgs);
			msgs = basicSetChildrenB1a(newChildrenB1a, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Multicontainment_bPackage.ROOT_B__CHILDREN_B1A, newChildrenB1a, newChildrenB1a));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChildB1 getChildrenB1b() {
		return childrenB1b;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetChildrenB1b(ChildB1 newChildrenB1b, NotificationChain msgs) {
		ChildB1 oldChildrenB1b = childrenB1b;
		childrenB1b = newChildrenB1b;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, Multicontainment_bPackage.ROOT_B__CHILDREN_B1B, oldChildrenB1b, newChildrenB1b);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChildrenB1b(ChildB1 newChildrenB1b) {
		if (newChildrenB1b != childrenB1b) {
			NotificationChain msgs = null;
			if (childrenB1b != null)
				msgs = ((InternalEObject)childrenB1b).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Multicontainment_bPackage.ROOT_B__CHILDREN_B1B, null, msgs);
			if (newChildrenB1b != null)
				msgs = ((InternalEObject)newChildrenB1b).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Multicontainment_bPackage.ROOT_B__CHILDREN_B1B, null, msgs);
			msgs = basicSetChildrenB1b(newChildrenB1b, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Multicontainment_bPackage.ROOT_B__CHILDREN_B1B, newChildrenB1b, newChildrenB1b));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChildB2 getChildrenB2a() {
		return childrenB2a;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetChildrenB2a(ChildB2 newChildrenB2a, NotificationChain msgs) {
		ChildB2 oldChildrenB2a = childrenB2a;
		childrenB2a = newChildrenB2a;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, Multicontainment_bPackage.ROOT_B__CHILDREN_B2A, oldChildrenB2a, newChildrenB2a);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChildrenB2a(ChildB2 newChildrenB2a) {
		if (newChildrenB2a != childrenB2a) {
			NotificationChain msgs = null;
			if (childrenB2a != null)
				msgs = ((InternalEObject)childrenB2a).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Multicontainment_bPackage.ROOT_B__CHILDREN_B2A, null, msgs);
			if (newChildrenB2a != null)
				msgs = ((InternalEObject)newChildrenB2a).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Multicontainment_bPackage.ROOT_B__CHILDREN_B2A, null, msgs);
			msgs = basicSetChildrenB2a(newChildrenB2a, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Multicontainment_bPackage.ROOT_B__CHILDREN_B2A, newChildrenB2a, newChildrenB2a));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case Multicontainment_bPackage.ROOT_B__CHILDREN_B1A:
				return basicSetChildrenB1a(null, msgs);
			case Multicontainment_bPackage.ROOT_B__CHILDREN_B1B:
				return basicSetChildrenB1b(null, msgs);
			case Multicontainment_bPackage.ROOT_B__CHILDREN_B2A:
				return basicSetChildrenB2a(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case Multicontainment_bPackage.ROOT_B__CHILDREN_B1A:
				return getChildrenB1a();
			case Multicontainment_bPackage.ROOT_B__CHILDREN_B1B:
				return getChildrenB1b();
			case Multicontainment_bPackage.ROOT_B__CHILDREN_B2A:
				return getChildrenB2a();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case Multicontainment_bPackage.ROOT_B__CHILDREN_B1A:
				setChildrenB1a((ChildB1)newValue);
				return;
			case Multicontainment_bPackage.ROOT_B__CHILDREN_B1B:
				setChildrenB1b((ChildB1)newValue);
				return;
			case Multicontainment_bPackage.ROOT_B__CHILDREN_B2A:
				setChildrenB2a((ChildB2)newValue);
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
			case Multicontainment_bPackage.ROOT_B__CHILDREN_B1A:
				setChildrenB1a((ChildB1)null);
				return;
			case Multicontainment_bPackage.ROOT_B__CHILDREN_B1B:
				setChildrenB1b((ChildB1)null);
				return;
			case Multicontainment_bPackage.ROOT_B__CHILDREN_B2A:
				setChildrenB2a((ChildB2)null);
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
			case Multicontainment_bPackage.ROOT_B__CHILDREN_B1A:
				return childrenB1a != null;
			case Multicontainment_bPackage.ROOT_B__CHILDREN_B1B:
				return childrenB1b != null;
			case Multicontainment_bPackage.ROOT_B__CHILDREN_B2A:
				return childrenB2a != null;
		}
		return super.eIsSet(featureID);
	}

} //RootBImpl
