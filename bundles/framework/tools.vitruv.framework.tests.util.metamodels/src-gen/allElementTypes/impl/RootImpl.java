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
 *   <li>{@link allElementTypes.impl.RootImpl#getSingleValuedPrimitiveTypeEAttribute <em>Single Valued Primitive Type EAttribute</em>}</li>
 *   <li>{@link allElementTypes.impl.RootImpl#getSingleValuedUnsettableEAttribute <em>Single Valued Unsettable EAttribute</em>}</li>
 *   <li>{@link allElementTypes.impl.RootImpl#getSingleValuedNonContainmentEReference <em>Single Valued Non Containment EReference</em>}</li>
 *   <li>{@link allElementTypes.impl.RootImpl#getSingleValuedUnsettableNonContainmentEReference <em>Single Valued Unsettable Non Containment EReference</em>}</li>
 *   <li>{@link allElementTypes.impl.RootImpl#getSingleValuedContainmentEReference <em>Single Valued Containment EReference</em>}</li>
 *   <li>{@link allElementTypes.impl.RootImpl#getSingleValuedUnsettableContainmentEReference <em>Single Valued Unsettable Containment EReference</em>}</li>
 *   <li>{@link allElementTypes.impl.RootImpl#getMultiValuedEAttribute <em>Multi Valued EAttribute</em>}</li>
 *   <li>{@link allElementTypes.impl.RootImpl#getMultiValuedUnsettableEAttribute <em>Multi Valued Unsettable EAttribute</em>}</li>
 *   <li>{@link allElementTypes.impl.RootImpl#getMultiValuedNonContainmentEReference <em>Multi Valued Non Containment EReference</em>}</li>
 *   <li>{@link allElementTypes.impl.RootImpl#getMultiValuedUnsettableNonContainmentEReference <em>Multi Valued Unsettable Non Containment EReference</em>}</li>
 *   <li>{@link allElementTypes.impl.RootImpl#getMultiValuedContainmentEReference <em>Multi Valued Containment EReference</em>}</li>
 *   <li>{@link allElementTypes.impl.RootImpl#getMultiValuedUnsettableContainmentEReference <em>Multi Valued Unsettable Containment EReference</em>}</li>
 *   <li>{@link allElementTypes.impl.RootImpl#getNonRootObjectContainerHelper <em>Non Root Object Container Helper</em>}</li>
 *   <li>{@link allElementTypes.impl.RootImpl#getRecursiveRoot <em>Recursive Root</em>}</li>
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
	 * The default value of the '{@link #getSingleValuedPrimitiveTypeEAttribute() <em>Single Valued Primitive Type EAttribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleValuedPrimitiveTypeEAttribute()
	 * @generated
	 * @ordered
	 */
	protected static final int SINGLE_VALUED_PRIMITIVE_TYPE_EATTRIBUTE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getSingleValuedPrimitiveTypeEAttribute() <em>Single Valued Primitive Type EAttribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleValuedPrimitiveTypeEAttribute()
	 * @generated
	 * @ordered
	 */
	protected int singleValuedPrimitiveTypeEAttribute = SINGLE_VALUED_PRIMITIVE_TYPE_EATTRIBUTE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSingleValuedUnsettableEAttribute() <em>Single Valued Unsettable EAttribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleValuedUnsettableEAttribute()
	 * @generated
	 * @ordered
	 */
	protected static final Integer SINGLE_VALUED_UNSETTABLE_EATTRIBUTE_EDEFAULT = new Integer(0);

	/**
	 * The cached value of the '{@link #getSingleValuedUnsettableEAttribute() <em>Single Valued Unsettable EAttribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleValuedUnsettableEAttribute()
	 * @generated
	 * @ordered
	 */
	protected Integer singleValuedUnsettableEAttribute = SINGLE_VALUED_UNSETTABLE_EATTRIBUTE_EDEFAULT;

	/**
	 * This is true if the Single Valued Unsettable EAttribute attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean singleValuedUnsettableEAttributeESet;

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
	 * The cached value of the '{@link #getSingleValuedUnsettableNonContainmentEReference() <em>Single Valued Unsettable Non Containment EReference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleValuedUnsettableNonContainmentEReference()
	 * @generated
	 * @ordered
	 */
	protected NonRoot singleValuedUnsettableNonContainmentEReference;

	/**
	 * This is true if the Single Valued Unsettable Non Containment EReference reference has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean singleValuedUnsettableNonContainmentEReferenceESet;

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
	 * The cached value of the '{@link #getSingleValuedUnsettableContainmentEReference() <em>Single Valued Unsettable Containment EReference</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleValuedUnsettableContainmentEReference()
	 * @generated
	 * @ordered
	 */
	protected NonRoot singleValuedUnsettableContainmentEReference;

	/**
	 * This is true if the Single Valued Unsettable Containment EReference containment reference has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean singleValuedUnsettableContainmentEReferenceESet;

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
	 * The cached value of the '{@link #getMultiValuedUnsettableEAttribute() <em>Multi Valued Unsettable EAttribute</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultiValuedUnsettableEAttribute()
	 * @generated
	 * @ordered
	 */
	protected EList<Integer> multiValuedUnsettableEAttribute;

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
	 * The cached value of the '{@link #getMultiValuedUnsettableNonContainmentEReference() <em>Multi Valued Unsettable Non Containment EReference</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultiValuedUnsettableNonContainmentEReference()
	 * @generated
	 * @ordered
	 */
	protected EList<NonRoot> multiValuedUnsettableNonContainmentEReference;

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
	 * The cached value of the '{@link #getMultiValuedUnsettableContainmentEReference() <em>Multi Valued Unsettable Containment EReference</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultiValuedUnsettableContainmentEReference()
	 * @generated
	 * @ordered
	 */
	protected EList<NonRoot> multiValuedUnsettableContainmentEReference;

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
	 * The cached value of the '{@link #getRecursiveRoot() <em>Recursive Root</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRecursiveRoot()
	 * @generated
	 * @ordered
	 */
	protected Root recursiveRoot;

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
	public int getSingleValuedPrimitiveTypeEAttribute() {
		return singleValuedPrimitiveTypeEAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSingleValuedPrimitiveTypeEAttribute(int newSingleValuedPrimitiveTypeEAttribute) {
		int oldSingleValuedPrimitiveTypeEAttribute = singleValuedPrimitiveTypeEAttribute;
		singleValuedPrimitiveTypeEAttribute = newSingleValuedPrimitiveTypeEAttribute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AllElementTypesPackage.ROOT__SINGLE_VALUED_PRIMITIVE_TYPE_EATTRIBUTE, oldSingleValuedPrimitiveTypeEAttribute, singleValuedPrimitiveTypeEAttribute));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getSingleValuedUnsettableEAttribute() {
		return singleValuedUnsettableEAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSingleValuedUnsettableEAttribute(Integer newSingleValuedUnsettableEAttribute) {
		Integer oldSingleValuedUnsettableEAttribute = singleValuedUnsettableEAttribute;
		singleValuedUnsettableEAttribute = newSingleValuedUnsettableEAttribute;
		boolean oldSingleValuedUnsettableEAttributeESet = singleValuedUnsettableEAttributeESet;
		singleValuedUnsettableEAttributeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AllElementTypesPackage.ROOT__SINGLE_VALUED_UNSETTABLE_EATTRIBUTE, oldSingleValuedUnsettableEAttribute, singleValuedUnsettableEAttribute, !oldSingleValuedUnsettableEAttributeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetSingleValuedUnsettableEAttribute() {
		Integer oldSingleValuedUnsettableEAttribute = singleValuedUnsettableEAttribute;
		boolean oldSingleValuedUnsettableEAttributeESet = singleValuedUnsettableEAttributeESet;
		singleValuedUnsettableEAttribute = SINGLE_VALUED_UNSETTABLE_EATTRIBUTE_EDEFAULT;
		singleValuedUnsettableEAttributeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, AllElementTypesPackage.ROOT__SINGLE_VALUED_UNSETTABLE_EATTRIBUTE, oldSingleValuedUnsettableEAttribute, SINGLE_VALUED_UNSETTABLE_EATTRIBUTE_EDEFAULT, oldSingleValuedUnsettableEAttributeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetSingleValuedUnsettableEAttribute() {
		return singleValuedUnsettableEAttributeESet;
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
	public NonRoot getSingleValuedUnsettableNonContainmentEReference() {
		if (singleValuedUnsettableNonContainmentEReference != null && singleValuedUnsettableNonContainmentEReference.eIsProxy()) {
			InternalEObject oldSingleValuedUnsettableNonContainmentEReference = (InternalEObject)singleValuedUnsettableNonContainmentEReference;
			singleValuedUnsettableNonContainmentEReference = (NonRoot)eResolveProxy(oldSingleValuedUnsettableNonContainmentEReference);
			if (singleValuedUnsettableNonContainmentEReference != oldSingleValuedUnsettableNonContainmentEReference) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AllElementTypesPackage.ROOT__SINGLE_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE, oldSingleValuedUnsettableNonContainmentEReference, singleValuedUnsettableNonContainmentEReference));
			}
		}
		return singleValuedUnsettableNonContainmentEReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonRoot basicGetSingleValuedUnsettableNonContainmentEReference() {
		return singleValuedUnsettableNonContainmentEReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSingleValuedUnsettableNonContainmentEReference(NonRoot newSingleValuedUnsettableNonContainmentEReference) {
		NonRoot oldSingleValuedUnsettableNonContainmentEReference = singleValuedUnsettableNonContainmentEReference;
		singleValuedUnsettableNonContainmentEReference = newSingleValuedUnsettableNonContainmentEReference;
		boolean oldSingleValuedUnsettableNonContainmentEReferenceESet = singleValuedUnsettableNonContainmentEReferenceESet;
		singleValuedUnsettableNonContainmentEReferenceESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AllElementTypesPackage.ROOT__SINGLE_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE, oldSingleValuedUnsettableNonContainmentEReference, singleValuedUnsettableNonContainmentEReference, !oldSingleValuedUnsettableNonContainmentEReferenceESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetSingleValuedUnsettableNonContainmentEReference() {
		NonRoot oldSingleValuedUnsettableNonContainmentEReference = singleValuedUnsettableNonContainmentEReference;
		boolean oldSingleValuedUnsettableNonContainmentEReferenceESet = singleValuedUnsettableNonContainmentEReferenceESet;
		singleValuedUnsettableNonContainmentEReference = null;
		singleValuedUnsettableNonContainmentEReferenceESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, AllElementTypesPackage.ROOT__SINGLE_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE, oldSingleValuedUnsettableNonContainmentEReference, null, oldSingleValuedUnsettableNonContainmentEReferenceESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetSingleValuedUnsettableNonContainmentEReference() {
		return singleValuedUnsettableNonContainmentEReferenceESet;
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
	public NonRoot getSingleValuedUnsettableContainmentEReference() {
		return singleValuedUnsettableContainmentEReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSingleValuedUnsettableContainmentEReference(NonRoot newSingleValuedUnsettableContainmentEReference, NotificationChain msgs) {
		NonRoot oldSingleValuedUnsettableContainmentEReference = singleValuedUnsettableContainmentEReference;
		singleValuedUnsettableContainmentEReference = newSingleValuedUnsettableContainmentEReference;
		boolean oldSingleValuedUnsettableContainmentEReferenceESet = singleValuedUnsettableContainmentEReferenceESet;
		singleValuedUnsettableContainmentEReferenceESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AllElementTypesPackage.ROOT__SINGLE_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE, oldSingleValuedUnsettableContainmentEReference, newSingleValuedUnsettableContainmentEReference, !oldSingleValuedUnsettableContainmentEReferenceESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSingleValuedUnsettableContainmentEReference(NonRoot newSingleValuedUnsettableContainmentEReference) {
		if (newSingleValuedUnsettableContainmentEReference != singleValuedUnsettableContainmentEReference) {
			NotificationChain msgs = null;
			if (singleValuedUnsettableContainmentEReference != null)
				msgs = ((InternalEObject)singleValuedUnsettableContainmentEReference).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AllElementTypesPackage.ROOT__SINGLE_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE, null, msgs);
			if (newSingleValuedUnsettableContainmentEReference != null)
				msgs = ((InternalEObject)newSingleValuedUnsettableContainmentEReference).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AllElementTypesPackage.ROOT__SINGLE_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE, null, msgs);
			msgs = basicSetSingleValuedUnsettableContainmentEReference(newSingleValuedUnsettableContainmentEReference, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldSingleValuedUnsettableContainmentEReferenceESet = singleValuedUnsettableContainmentEReferenceESet;
			singleValuedUnsettableContainmentEReferenceESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, AllElementTypesPackage.ROOT__SINGLE_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE, newSingleValuedUnsettableContainmentEReference, newSingleValuedUnsettableContainmentEReference, !oldSingleValuedUnsettableContainmentEReferenceESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicUnsetSingleValuedUnsettableContainmentEReference(NotificationChain msgs) {
		NonRoot oldSingleValuedUnsettableContainmentEReference = singleValuedUnsettableContainmentEReference;
		singleValuedUnsettableContainmentEReference = null;
		boolean oldSingleValuedUnsettableContainmentEReferenceESet = singleValuedUnsettableContainmentEReferenceESet;
		singleValuedUnsettableContainmentEReferenceESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, AllElementTypesPackage.ROOT__SINGLE_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE, oldSingleValuedUnsettableContainmentEReference, null, oldSingleValuedUnsettableContainmentEReferenceESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetSingleValuedUnsettableContainmentEReference() {
		if (singleValuedUnsettableContainmentEReference != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)singleValuedUnsettableContainmentEReference).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AllElementTypesPackage.ROOT__SINGLE_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE, null, msgs);
			msgs = basicUnsetSingleValuedUnsettableContainmentEReference(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldSingleValuedUnsettableContainmentEReferenceESet = singleValuedUnsettableContainmentEReferenceESet;
			singleValuedUnsettableContainmentEReferenceESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, AllElementTypesPackage.ROOT__SINGLE_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE, null, null, oldSingleValuedUnsettableContainmentEReferenceESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetSingleValuedUnsettableContainmentEReference() {
		return singleValuedUnsettableContainmentEReferenceESet;
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
	public EList<Integer> getMultiValuedUnsettableEAttribute() {
		if (multiValuedUnsettableEAttribute == null) {
			multiValuedUnsettableEAttribute = new EDataTypeUniqueEList.Unsettable<Integer>(Integer.class, this, AllElementTypesPackage.ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE);
		}
		return multiValuedUnsettableEAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMultiValuedUnsettableEAttribute() {
		if (multiValuedUnsettableEAttribute != null) ((InternalEList.Unsettable<?>)multiValuedUnsettableEAttribute).unset();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMultiValuedUnsettableEAttribute() {
		return multiValuedUnsettableEAttribute != null && ((InternalEList.Unsettable<?>)multiValuedUnsettableEAttribute).isSet();
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
	public EList<NonRoot> getMultiValuedUnsettableNonContainmentEReference() {
		if (multiValuedUnsettableNonContainmentEReference == null) {
			multiValuedUnsettableNonContainmentEReference = new EObjectResolvingEList.Unsettable<NonRoot>(NonRoot.class, this, AllElementTypesPackage.ROOT__MULTI_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE);
		}
		return multiValuedUnsettableNonContainmentEReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMultiValuedUnsettableNonContainmentEReference() {
		if (multiValuedUnsettableNonContainmentEReference != null) ((InternalEList.Unsettable<?>)multiValuedUnsettableNonContainmentEReference).unset();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMultiValuedUnsettableNonContainmentEReference() {
		return multiValuedUnsettableNonContainmentEReference != null && ((InternalEList.Unsettable<?>)multiValuedUnsettableNonContainmentEReference).isSet();
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
	public EList<NonRoot> getMultiValuedUnsettableContainmentEReference() {
		if (multiValuedUnsettableContainmentEReference == null) {
			multiValuedUnsettableContainmentEReference = new EObjectContainmentEList.Unsettable<NonRoot>(NonRoot.class, this, AllElementTypesPackage.ROOT__MULTI_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE);
		}
		return multiValuedUnsettableContainmentEReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMultiValuedUnsettableContainmentEReference() {
		if (multiValuedUnsettableContainmentEReference != null) ((InternalEList.Unsettable<?>)multiValuedUnsettableContainmentEReference).unset();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMultiValuedUnsettableContainmentEReference() {
		return multiValuedUnsettableContainmentEReference != null && ((InternalEList.Unsettable<?>)multiValuedUnsettableContainmentEReference).isSet();
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
	public Root getRecursiveRoot() {
		return recursiveRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRecursiveRoot(Root newRecursiveRoot, NotificationChain msgs) {
		Root oldRecursiveRoot = recursiveRoot;
		recursiveRoot = newRecursiveRoot;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AllElementTypesPackage.ROOT__RECURSIVE_ROOT, oldRecursiveRoot, newRecursiveRoot);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRecursiveRoot(Root newRecursiveRoot) {
		if (newRecursiveRoot != recursiveRoot) {
			NotificationChain msgs = null;
			if (recursiveRoot != null)
				msgs = ((InternalEObject)recursiveRoot).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AllElementTypesPackage.ROOT__RECURSIVE_ROOT, null, msgs);
			if (newRecursiveRoot != null)
				msgs = ((InternalEObject)newRecursiveRoot).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AllElementTypesPackage.ROOT__RECURSIVE_ROOT, null, msgs);
			msgs = basicSetRecursiveRoot(newRecursiveRoot, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AllElementTypesPackage.ROOT__RECURSIVE_ROOT, newRecursiveRoot, newRecursiveRoot));
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
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE:
				return basicUnsetSingleValuedUnsettableContainmentEReference(msgs);
			case AllElementTypesPackage.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE:
				return ((InternalEList<?>)getMultiValuedContainmentEReference()).basicRemove(otherEnd, msgs);
			case AllElementTypesPackage.ROOT__MULTI_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE:
				return ((InternalEList<?>)getMultiValuedUnsettableContainmentEReference()).basicRemove(otherEnd, msgs);
			case AllElementTypesPackage.ROOT__NON_ROOT_OBJECT_CONTAINER_HELPER:
				return basicSetNonRootObjectContainerHelper(null, msgs);
			case AllElementTypesPackage.ROOT__RECURSIVE_ROOT:
				return basicSetRecursiveRoot(null, msgs);
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
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_PRIMITIVE_TYPE_EATTRIBUTE:
				return getSingleValuedPrimitiveTypeEAttribute();
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_UNSETTABLE_EATTRIBUTE:
				return getSingleValuedUnsettableEAttribute();
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE:
				if (resolve) return getSingleValuedNonContainmentEReference();
				return basicGetSingleValuedNonContainmentEReference();
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE:
				if (resolve) return getSingleValuedUnsettableNonContainmentEReference();
				return basicGetSingleValuedUnsettableNonContainmentEReference();
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE:
				return getSingleValuedContainmentEReference();
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE:
				return getSingleValuedUnsettableContainmentEReference();
			case AllElementTypesPackage.ROOT__MULTI_VALUED_EATTRIBUTE:
				return getMultiValuedEAttribute();
			case AllElementTypesPackage.ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE:
				return getMultiValuedUnsettableEAttribute();
			case AllElementTypesPackage.ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE:
				return getMultiValuedNonContainmentEReference();
			case AllElementTypesPackage.ROOT__MULTI_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE:
				return getMultiValuedUnsettableNonContainmentEReference();
			case AllElementTypesPackage.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE:
				return getMultiValuedContainmentEReference();
			case AllElementTypesPackage.ROOT__MULTI_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE:
				return getMultiValuedUnsettableContainmentEReference();
			case AllElementTypesPackage.ROOT__NON_ROOT_OBJECT_CONTAINER_HELPER:
				return getNonRootObjectContainerHelper();
			case AllElementTypesPackage.ROOT__RECURSIVE_ROOT:
				return getRecursiveRoot();
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
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_PRIMITIVE_TYPE_EATTRIBUTE:
				setSingleValuedPrimitiveTypeEAttribute((Integer)newValue);
				return;
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_UNSETTABLE_EATTRIBUTE:
				setSingleValuedUnsettableEAttribute((Integer)newValue);
				return;
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE:
				setSingleValuedNonContainmentEReference((NonRoot)newValue);
				return;
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE:
				setSingleValuedUnsettableNonContainmentEReference((NonRoot)newValue);
				return;
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE:
				setSingleValuedContainmentEReference((NonRoot)newValue);
				return;
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE:
				setSingleValuedUnsettableContainmentEReference((NonRoot)newValue);
				return;
			case AllElementTypesPackage.ROOT__MULTI_VALUED_EATTRIBUTE:
				getMultiValuedEAttribute().clear();
				getMultiValuedEAttribute().addAll((Collection<? extends Integer>)newValue);
				return;
			case AllElementTypesPackage.ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE:
				getMultiValuedUnsettableEAttribute().clear();
				getMultiValuedUnsettableEAttribute().addAll((Collection<? extends Integer>)newValue);
				return;
			case AllElementTypesPackage.ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE:
				getMultiValuedNonContainmentEReference().clear();
				getMultiValuedNonContainmentEReference().addAll((Collection<? extends NonRoot>)newValue);
				return;
			case AllElementTypesPackage.ROOT__MULTI_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE:
				getMultiValuedUnsettableNonContainmentEReference().clear();
				getMultiValuedUnsettableNonContainmentEReference().addAll((Collection<? extends NonRoot>)newValue);
				return;
			case AllElementTypesPackage.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE:
				getMultiValuedContainmentEReference().clear();
				getMultiValuedContainmentEReference().addAll((Collection<? extends NonRoot>)newValue);
				return;
			case AllElementTypesPackage.ROOT__MULTI_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE:
				getMultiValuedUnsettableContainmentEReference().clear();
				getMultiValuedUnsettableContainmentEReference().addAll((Collection<? extends NonRoot>)newValue);
				return;
			case AllElementTypesPackage.ROOT__NON_ROOT_OBJECT_CONTAINER_HELPER:
				setNonRootObjectContainerHelper((NonRootObjectContainerHelper)newValue);
				return;
			case AllElementTypesPackage.ROOT__RECURSIVE_ROOT:
				setRecursiveRoot((Root)newValue);
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
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_PRIMITIVE_TYPE_EATTRIBUTE:
				setSingleValuedPrimitiveTypeEAttribute(SINGLE_VALUED_PRIMITIVE_TYPE_EATTRIBUTE_EDEFAULT);
				return;
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_UNSETTABLE_EATTRIBUTE:
				unsetSingleValuedUnsettableEAttribute();
				return;
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE:
				setSingleValuedNonContainmentEReference((NonRoot)null);
				return;
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE:
				unsetSingleValuedUnsettableNonContainmentEReference();
				return;
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE:
				setSingleValuedContainmentEReference((NonRoot)null);
				return;
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE:
				unsetSingleValuedUnsettableContainmentEReference();
				return;
			case AllElementTypesPackage.ROOT__MULTI_VALUED_EATTRIBUTE:
				getMultiValuedEAttribute().clear();
				return;
			case AllElementTypesPackage.ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE:
				unsetMultiValuedUnsettableEAttribute();
				return;
			case AllElementTypesPackage.ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE:
				getMultiValuedNonContainmentEReference().clear();
				return;
			case AllElementTypesPackage.ROOT__MULTI_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE:
				unsetMultiValuedUnsettableNonContainmentEReference();
				return;
			case AllElementTypesPackage.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE:
				getMultiValuedContainmentEReference().clear();
				return;
			case AllElementTypesPackage.ROOT__MULTI_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE:
				unsetMultiValuedUnsettableContainmentEReference();
				return;
			case AllElementTypesPackage.ROOT__NON_ROOT_OBJECT_CONTAINER_HELPER:
				setNonRootObjectContainerHelper((NonRootObjectContainerHelper)null);
				return;
			case AllElementTypesPackage.ROOT__RECURSIVE_ROOT:
				setRecursiveRoot((Root)null);
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
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_PRIMITIVE_TYPE_EATTRIBUTE:
				return singleValuedPrimitiveTypeEAttribute != SINGLE_VALUED_PRIMITIVE_TYPE_EATTRIBUTE_EDEFAULT;
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_UNSETTABLE_EATTRIBUTE:
				return isSetSingleValuedUnsettableEAttribute();
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE:
				return singleValuedNonContainmentEReference != null;
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE:
				return isSetSingleValuedUnsettableNonContainmentEReference();
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE:
				return singleValuedContainmentEReference != null;
			case AllElementTypesPackage.ROOT__SINGLE_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE:
				return isSetSingleValuedUnsettableContainmentEReference();
			case AllElementTypesPackage.ROOT__MULTI_VALUED_EATTRIBUTE:
				return multiValuedEAttribute != null && !multiValuedEAttribute.isEmpty();
			case AllElementTypesPackage.ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE:
				return isSetMultiValuedUnsettableEAttribute();
			case AllElementTypesPackage.ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE:
				return multiValuedNonContainmentEReference != null && !multiValuedNonContainmentEReference.isEmpty();
			case AllElementTypesPackage.ROOT__MULTI_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE:
				return isSetMultiValuedUnsettableNonContainmentEReference();
			case AllElementTypesPackage.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE:
				return multiValuedContainmentEReference != null && !multiValuedContainmentEReference.isEmpty();
			case AllElementTypesPackage.ROOT__MULTI_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE:
				return isSetMultiValuedUnsettableContainmentEReference();
			case AllElementTypesPackage.ROOT__NON_ROOT_OBJECT_CONTAINER_HELPER:
				return nonRootObjectContainerHelper != null;
			case AllElementTypesPackage.ROOT__RECURSIVE_ROOT:
				return recursiveRoot != null;
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
		result.append(", singleValuedPrimitiveTypeEAttribute: ");
		result.append(singleValuedPrimitiveTypeEAttribute);
		result.append(", singleValuedUnsettableEAttribute: ");
		if (singleValuedUnsettableEAttributeESet) result.append(singleValuedUnsettableEAttribute); else result.append("<unset>");
		result.append(", multiValuedEAttribute: ");
		result.append(multiValuedEAttribute);
		result.append(", multiValuedUnsettableEAttribute: ");
		result.append(multiValuedUnsettableEAttribute);
		result.append(')');
		return result.toString();
	}

} //RootImpl
