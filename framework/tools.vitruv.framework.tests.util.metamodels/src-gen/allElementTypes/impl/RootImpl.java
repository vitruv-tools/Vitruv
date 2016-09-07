/**
 */
package allElementTypes.impl;

import allElementTypes.AllElementTypesPackage;
import allElementTypes.NonRoot;
import allElementTypes.NonRootObjectContainerHelper;
import allElementTypes.Root;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link allElementTypes.impl.RootImpl#getSingleValuedEAttribute <em>Single Valued EAttribute</em>}</li>
 *   <li>{@link allElementTypes.impl.RootImpl#getSingleValuedNonContainmentEReference <em>Single Valued Non Containment EReference</em>}</li>
 *   <li>{@link allElementTypes.impl.RootImpl#getSingleValuedContainmentEReference <em>Single Valued Containment EReference</em>}</li>
 *   <li>{@link allElementTypes.impl.RootImpl#getMultiValuedEAttribute <em>Multi Valued EAttribute</em>}</li>
 *   <li>{@link allElementTypes.impl.RootImpl#getMultiValuedNonContainmentEReference <em>Multi Valued Non Containment EReference</em>}</li>
 *   <li>{@link allElementTypes.impl.RootImpl#getMultiValuedContainmentEReference <em>Multi Valued Containment EReference</em>}</li>
 *   <li>{@link allElementTypes.impl.RootImpl#getNonRootObjectContainerHelper <em>Non Root Object Container Helper</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RootImpl extends IdentifiedImpl implements Root {
	/**
	 * The default value of the '{@link #getSingleValuedEAttribute() <em>Single Valued EAttribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleValuedEAttribute()
	 * @generated
	 * @ordered
	 */
	protected static final Integer SINGLE_VALUED_EATTRIBUTE_EDEFAULT = new Integer(0);

	/**
	 * The cached value of the '{@link #getSingleValuedEAttribute() <em>Single Valued EAttribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleValuedEAttribute()
	 * @generated
	 * @ordered
	 */
	protected Integer singleValuedEAttribute = SINGLE_VALUED_EATTRIBUTE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSingleValuedNonContainmentEReference() <em>Single Valued Non Containment EReference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleValuedNonContainmentEReference()
	 * @generated
	 * @ordered
	 */
	protected NonRoot singleValuedNonContainmentEReference;

	/**
	 * The cached value of the '{@link #getSingleValuedContainmentEReference() <em>Single Valued Containment EReference</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleValuedContainmentEReference()
	 * @generated
	 * @ordered
	 */
	protected NonRoot singleValuedContainmentEReference;

	/**
	 * The cached value of the '{@link #getMultiValuedEAttribute() <em>Multi Valued EAttribute</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultiValuedEAttribute()
	 * @generated
	 * @ordered
	 */
	protected EList<Integer> multiValuedEAttribute;

	/**
	 * The cached value of the '{@link #getMultiValuedNonContainmentEReference() <em>Multi Valued Non Containment EReference</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultiValuedNonContainmentEReference()
	 * @generated
	 * @ordered
	 */
	protected EList<NonRoot> multiValuedNonContainmentEReference;

	/**
	 * The cached value of the '{@link #getMultiValuedContainmentEReference() <em>Multi Valued Containment EReference</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultiValuedContainmentEReference()
	 * @generated
	 * @ordered
	 */
	protected EList<NonRoot> multiValuedContainmentEReference;

	/**
	 * The cached value of the '{@link #getNonRootObjectContainerHelper() <em>Non Root Object Container Helper</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNonRootObjectContainerHelper()
	 * @generated
	 * @ordered
	 */
	protected NonRootObjectContainerHelper nonRootObjectContainerHelper;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AllElementTypesPackage.Literals.ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getSingleValuedEAttribute() {
		return singleValuedEAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSingleValuedEAttribute(Integer newSingleValuedEAttribute) {
		Integer oldSingleValuedEAttribute = singleValuedEAttribute;
		singleValuedEAttribute = newSingleValuedEAttribute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AllElementTypesPackage.ROOT__SINGLE_VALUED_EATTRIBUTE, oldSingleValuedEAttribute, singleValuedEAttribute));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonRoot getSingleValuedNonContainmentEReference() {
		if (singleValuedNonContainmentEReference != null && singleValuedNonContainmentEReference.eIsProxy()) {
			InternalEObject oldSingleValuedNonContainmentEReference = (InternalEObject)singleValuedNonContainmentEReference;
			singleValuedNonContainmentEReference = (NonRoot)eResolveProxy(oldSingleValuedNonContainmentEReference);
			if (singleValuedNonContainmentEReference != oldSingleValuedNonContainmentEReference) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AllElementTypesPackage.ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE, oldSingleValuedNonContainmentEReference, singleValuedNonContainmentEReference));
			}
		}
		return singleValuedNonContainmentEReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonRoot basicGetSingleValuedNonContainmentEReference() {
		return singleValuedNonContainmentEReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSingleValuedNonContainmentEReference(NonRoot newSingleValuedNonContainmentEReference) {
		NonRoot oldSingleValuedNonContainmentEReference = singleValuedNonContainmentEReference;
		singleValuedNonContainmentEReference = newSingleValuedNonContainmentEReference;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AllElementTypesPackage.ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE, oldSingleValuedNonContainmentEReference, singleValuedNonContainmentEReference));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonRoot getSingleValuedContainmentEReference() {
		return singleValuedContainmentEReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSingleValuedContainmentEReference(NonRoot newSingleValuedContainmentEReference, NotificationChain msgs) {
		NonRoot oldSingleValuedContainmentEReference = singleValuedContainmentEReference;
		singleValuedContainmentEReference = newSingleValuedContainmentEReference;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AllElementTypesPackage.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE, oldSingleValuedContainmentEReference, newSingleValuedContainmentEReference);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSingleValuedContainmentEReference(NonRoot newSingleValuedContainmentEReference) {
		if (newSingleValuedContainmentEReference != singleValuedContainmentEReference) {
			NotificationChain msgs = null;
			if (singleValuedContainmentEReference != null)
				msgs = ((InternalEObject)singleValuedContainmentEReference).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AllElementTypesPackage.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE, null, msgs);
			if (newSingleValuedContainmentEReference != null)
				msgs = ((InternalEObject)newSingleValuedContainmentEReference).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AllElementTypesPackage.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE, null, msgs);
			msgs = basicSetSingleValuedContainmentEReference(newSingleValuedContainmentEReference, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AllElementTypesPackage.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE, newSingleValuedContainmentEReference, newSingleValuedContainmentEReference));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Integer> getMultiValuedEAttribute() {
		if (multiValuedEAttribute == null) {
			multiValuedEAttribute = new EDataTypeUniqueEList<Integer>(Integer.class, this, AllElementTypesPackage.ROOT__MULTI_VALUED_EATTRIBUTE);
		}
		return multiValuedEAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<NonRoot> getMultiValuedNonContainmentEReference() {
		if (multiValuedNonContainmentEReference == null) {
			multiValuedNonContainmentEReference = new EObjectResolvingEList<NonRoot>(NonRoot.class, this, AllElementTypesPackage.ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE);
		}
		return multiValuedNonContainmentEReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<NonRoot> getMultiValuedContainmentEReference() {
		if (multiValuedContainmentEReference == null) {
			multiValuedContainmentEReference = new EObjectContainmentEList<NonRoot>(NonRoot.class, this, AllElementTypesPackage.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE);
		}
		return multiValuedContainmentEReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonRootObjectContainerHelper getNonRootObjectContainerHelper() {
		return nonRootObjectContainerHelper;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNonRootObjectContainerHelper(NonRootObjectContainerHelper newNonRootObjectContainerHelper, NotificationChain msgs) {
		NonRootObjectContainerHelper oldNonRootObjectContainerHelper = nonRootObjectContainerHelper;
		nonRootObjectContainerHelper = newNonRootObjectContainerHelper;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AllElementTypesPackage.ROOT__NON_ROOT_OBJECT_CONTAINER_HELPER, oldNonRootObjectContainerHelper, newNonRootObjectContainerHelper);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNonRootObjectContainerHelper(NonRootObjectContainerHelper newNonRootObjectContainerHelper) {
		if (newNonRootObjectContainerHelper != nonRootObjectContainerHelper) {
			NotificationChain msgs = null;
			if (nonRootObjectContainerHelper != null)
				msgs = ((InternalEObject)nonRootObjectContainerHelper).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AllElementTypesPackage.ROOT__NON_ROOT_OBJECT_CONTAINER_HELPER, null, msgs);
			if (newNonRootObjectContainerHelper != null)
				msgs = ((InternalEObject)newNonRootObjectContainerHelper).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AllElementTypesPackage.ROOT__NON_ROOT_OBJECT_CONTAINER_HELPER, null, msgs);
			msgs = basicSetNonRootObjectContainerHelper(newNonRootObjectContainerHelper, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AllElementTypesPackage.ROOT__NON_ROOT_OBJECT_CONTAINER_HELPER, newNonRootObjectContainerHelper, newNonRootObjectContainerHelper));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE:
				return basicSetSingleValuedContainmentEReference(null, msgs);
			case AllElementTypesPackage.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE:
				return ((InternalEList<?>)getMultiValuedContainmentEReference()).basicRemove(otherEnd, msgs);
			case AllElementTypesPackage.ROOT__NON_ROOT_OBJECT_CONTAINER_HELPER:
				return basicSetNonRootObjectContainerHelper(null, msgs);
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
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_EATTRIBUTE:
				return getSingleValuedEAttribute();
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE:
				if (resolve) return getSingleValuedNonContainmentEReference();
				return basicGetSingleValuedNonContainmentEReference();
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE:
				return getSingleValuedContainmentEReference();
			case AllElementTypesPackage.ROOT__MULTI_VALUED_EATTRIBUTE:
				return getMultiValuedEAttribute();
			case AllElementTypesPackage.ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE:
				return getMultiValuedNonContainmentEReference();
			case AllElementTypesPackage.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE:
				return getMultiValuedContainmentEReference();
			case AllElementTypesPackage.ROOT__NON_ROOT_OBJECT_CONTAINER_HELPER:
				return getNonRootObjectContainerHelper();
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
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_EATTRIBUTE:
				setSingleValuedEAttribute((Integer)newValue);
				return;
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE:
				setSingleValuedNonContainmentEReference((NonRoot)newValue);
				return;
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE:
				setSingleValuedContainmentEReference((NonRoot)newValue);
				return;
			case AllElementTypesPackage.ROOT__MULTI_VALUED_EATTRIBUTE:
				getMultiValuedEAttribute().clear();
				getMultiValuedEAttribute().addAll((Collection<? extends Integer>)newValue);
				return;
			case AllElementTypesPackage.ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE:
				getMultiValuedNonContainmentEReference().clear();
				getMultiValuedNonContainmentEReference().addAll((Collection<? extends NonRoot>)newValue);
				return;
			case AllElementTypesPackage.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE:
				getMultiValuedContainmentEReference().clear();
				getMultiValuedContainmentEReference().addAll((Collection<? extends NonRoot>)newValue);
				return;
			case AllElementTypesPackage.ROOT__NON_ROOT_OBJECT_CONTAINER_HELPER:
				setNonRootObjectContainerHelper((NonRootObjectContainerHelper)newValue);
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
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_EATTRIBUTE:
				setSingleValuedEAttribute(SINGLE_VALUED_EATTRIBUTE_EDEFAULT);
				return;
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE:
				setSingleValuedNonContainmentEReference((NonRoot)null);
				return;
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE:
				setSingleValuedContainmentEReference((NonRoot)null);
				return;
			case AllElementTypesPackage.ROOT__MULTI_VALUED_EATTRIBUTE:
				getMultiValuedEAttribute().clear();
				return;
			case AllElementTypesPackage.ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE:
				getMultiValuedNonContainmentEReference().clear();
				return;
			case AllElementTypesPackage.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE:
				getMultiValuedContainmentEReference().clear();
				return;
			case AllElementTypesPackage.ROOT__NON_ROOT_OBJECT_CONTAINER_HELPER:
				setNonRootObjectContainerHelper((NonRootObjectContainerHelper)null);
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
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_EATTRIBUTE:
				return SINGLE_VALUED_EATTRIBUTE_EDEFAULT == null ? singleValuedEAttribute != null : !SINGLE_VALUED_EATTRIBUTE_EDEFAULT.equals(singleValuedEAttribute);
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE:
				return singleValuedNonContainmentEReference != null;
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE:
				return singleValuedContainmentEReference != null;
			case AllElementTypesPackage.ROOT__MULTI_VALUED_EATTRIBUTE:
				return multiValuedEAttribute != null && !multiValuedEAttribute.isEmpty();
			case AllElementTypesPackage.ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE:
				return multiValuedNonContainmentEReference != null && !multiValuedNonContainmentEReference.isEmpty();
			case AllElementTypesPackage.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE:
				return multiValuedContainmentEReference != null && !multiValuedContainmentEReference.isEmpty();
			case AllElementTypesPackage.ROOT__NON_ROOT_OBJECT_CONTAINER_HELPER:
				return nonRootObjectContainerHelper != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (singleValuedEAttribute: ");
		result.append(singleValuedEAttribute);
		result.append(", multiValuedEAttribute: ");
		result.append(multiValuedEAttribute);
		result.append(')');
		return result.toString();
	}

} //RootImpl
