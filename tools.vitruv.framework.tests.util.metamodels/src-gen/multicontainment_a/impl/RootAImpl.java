/**
 */
package multicontainment_a.impl;

import multicontainment_a.ChildA1;
import multicontainment_a.ChildA2;
import multicontainment_a.Multicontainment_aPackage;
import multicontainment_a.RootA;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Root A</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link multicontainment_a.impl.RootAImpl#getChildrenA1a <em>Children A1a</em>}</li>
 *   <li>{@link multicontainment_a.impl.RootAImpl#getChildrenA1b <em>Children A1b</em>}</li>
 *   <li>{@link multicontainment_a.impl.RootAImpl#getChildrenA2a <em>Children A2a</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RootAImpl extends IdentifiedImpl implements RootA {
	/**
	 * The cached value of the '{@link #getChildrenA1a() <em>Children A1a</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildrenA1a()
	 * @generated
	 * @ordered
	 */
	protected ChildA1 childrenA1a;

	/**
	 * The cached value of the '{@link #getChildrenA1b() <em>Children A1b</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildrenA1b()
	 * @generated
	 * @ordered
	 */
	protected ChildA1 childrenA1b;

	/**
	 * The cached value of the '{@link #getChildrenA2a() <em>Children A2a</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildrenA2a()
	 * @generated
	 * @ordered
	 */
	protected ChildA2 childrenA2a;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RootAImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return Multicontainment_aPackage.Literals.ROOT_A;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChildA1 getChildrenA1a() {
		return childrenA1a;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetChildrenA1a(ChildA1 newChildrenA1a, NotificationChain msgs) {
		ChildA1 oldChildrenA1a = childrenA1a;
		childrenA1a = newChildrenA1a;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, Multicontainment_aPackage.ROOT_A__CHILDREN_A1A, oldChildrenA1a, newChildrenA1a);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChildrenA1a(ChildA1 newChildrenA1a) {
		if (newChildrenA1a != childrenA1a) {
			NotificationChain msgs = null;
			if (childrenA1a != null)
				msgs = ((InternalEObject)childrenA1a).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Multicontainment_aPackage.ROOT_A__CHILDREN_A1A, null, msgs);
			if (newChildrenA1a != null)
				msgs = ((InternalEObject)newChildrenA1a).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Multicontainment_aPackage.ROOT_A__CHILDREN_A1A, null, msgs);
			msgs = basicSetChildrenA1a(newChildrenA1a, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Multicontainment_aPackage.ROOT_A__CHILDREN_A1A, newChildrenA1a, newChildrenA1a));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChildA1 getChildrenA1b() {
		return childrenA1b;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetChildrenA1b(ChildA1 newChildrenA1b, NotificationChain msgs) {
		ChildA1 oldChildrenA1b = childrenA1b;
		childrenA1b = newChildrenA1b;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, Multicontainment_aPackage.ROOT_A__CHILDREN_A1B, oldChildrenA1b, newChildrenA1b);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChildrenA1b(ChildA1 newChildrenA1b) {
		if (newChildrenA1b != childrenA1b) {
			NotificationChain msgs = null;
			if (childrenA1b != null)
				msgs = ((InternalEObject)childrenA1b).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Multicontainment_aPackage.ROOT_A__CHILDREN_A1B, null, msgs);
			if (newChildrenA1b != null)
				msgs = ((InternalEObject)newChildrenA1b).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Multicontainment_aPackage.ROOT_A__CHILDREN_A1B, null, msgs);
			msgs = basicSetChildrenA1b(newChildrenA1b, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Multicontainment_aPackage.ROOT_A__CHILDREN_A1B, newChildrenA1b, newChildrenA1b));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChildA2 getChildrenA2a() {
		return childrenA2a;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetChildrenA2a(ChildA2 newChildrenA2a, NotificationChain msgs) {
		ChildA2 oldChildrenA2a = childrenA2a;
		childrenA2a = newChildrenA2a;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, Multicontainment_aPackage.ROOT_A__CHILDREN_A2A, oldChildrenA2a, newChildrenA2a);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChildrenA2a(ChildA2 newChildrenA2a) {
		if (newChildrenA2a != childrenA2a) {
			NotificationChain msgs = null;
			if (childrenA2a != null)
				msgs = ((InternalEObject)childrenA2a).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Multicontainment_aPackage.ROOT_A__CHILDREN_A2A, null, msgs);
			if (newChildrenA2a != null)
				msgs = ((InternalEObject)newChildrenA2a).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Multicontainment_aPackage.ROOT_A__CHILDREN_A2A, null, msgs);
			msgs = basicSetChildrenA2a(newChildrenA2a, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Multicontainment_aPackage.ROOT_A__CHILDREN_A2A, newChildrenA2a, newChildrenA2a));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case Multicontainment_aPackage.ROOT_A__CHILDREN_A1A:
				return basicSetChildrenA1a(null, msgs);
			case Multicontainment_aPackage.ROOT_A__CHILDREN_A1B:
				return basicSetChildrenA1b(null, msgs);
			case Multicontainment_aPackage.ROOT_A__CHILDREN_A2A:
				return basicSetChildrenA2a(null, msgs);
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
			case Multicontainment_aPackage.ROOT_A__CHILDREN_A1A:
				return getChildrenA1a();
			case Multicontainment_aPackage.ROOT_A__CHILDREN_A1B:
				return getChildrenA1b();
			case Multicontainment_aPackage.ROOT_A__CHILDREN_A2A:
				return getChildrenA2a();
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
			case Multicontainment_aPackage.ROOT_A__CHILDREN_A1A:
				setChildrenA1a((ChildA1)newValue);
				return;
			case Multicontainment_aPackage.ROOT_A__CHILDREN_A1B:
				setChildrenA1b((ChildA1)newValue);
				return;
			case Multicontainment_aPackage.ROOT_A__CHILDREN_A2A:
				setChildrenA2a((ChildA2)newValue);
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
			case Multicontainment_aPackage.ROOT_A__CHILDREN_A1A:
				setChildrenA1a((ChildA1)null);
				return;
			case Multicontainment_aPackage.ROOT_A__CHILDREN_A1B:
				setChildrenA1b((ChildA1)null);
				return;
			case Multicontainment_aPackage.ROOT_A__CHILDREN_A2A:
				setChildrenA2a((ChildA2)null);
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
			case Multicontainment_aPackage.ROOT_A__CHILDREN_A1A:
				return childrenA1a != null;
			case Multicontainment_aPackage.ROOT_A__CHILDREN_A1B:
				return childrenA1b != null;
			case Multicontainment_aPackage.ROOT_A__CHILDREN_A2A:
				return childrenA2a != null;
		}
		return super.eIsSet(featureID);
	}

} //RootAImpl
