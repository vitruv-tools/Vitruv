/**
 */
package tools.vitruv.framework.correspondence.impl;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import tools.vitruv.framework.correspondence.Correspondence;
import tools.vitruv.framework.correspondence.CorrespondencePackage;
import tools.vitruv.framework.correspondence.Correspondences;

import tools.vitruv.framework.tuid.Tuid;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Correspondence</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.correspondence.impl.CorrespondenceImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link tools.vitruv.framework.correspondence.impl.CorrespondenceImpl#getDependsOn <em>Depends On</em>}</li>
 *   <li>{@link tools.vitruv.framework.correspondence.impl.CorrespondenceImpl#getDependedOnBy <em>Depended On By</em>}</li>
 *   <li>{@link tools.vitruv.framework.correspondence.impl.CorrespondenceImpl#getATuids <em>ATuids</em>}</li>
 *   <li>{@link tools.vitruv.framework.correspondence.impl.CorrespondenceImpl#getBTuids <em>BTuids</em>}</li>
 *   <li>{@link tools.vitruv.framework.correspondence.impl.CorrespondenceImpl#getTag <em>Tag</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class CorrespondenceImpl extends EObjectImpl implements Correspondence {
	/**
     * @generated NOT
     */
    @SuppressWarnings("unused")
	private static final long serialVersionUID = -1129928608053571384L;
    
	/**
	 * The cached value of the '{@link #getDependsOn() <em>Depends On</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDependsOn()
	 * @generated
	 * @ordered
	 */
	protected EList<Correspondence> dependsOn;

	/**
	 * The cached value of the '{@link #getDependedOnBy() <em>Depended On By</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDependedOnBy()
	 * @generated
	 * @ordered
	 */
	protected EList<Correspondence> dependedOnBy;

	/**
	 * The cached value of the '{@link #getATuids() <em>ATuids</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getATuids()
	 * @generated
	 * @ordered
	 */
	protected EList<Tuid> aTuids;

	/**
	 * The cached value of the '{@link #getBTuids() <em>BTuids</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBTuids()
	 * @generated
	 * @ordered
	 */
	protected EList<Tuid> bTuids;

	/**
	 * The default value of the '{@link #getTag() <em>Tag</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTag()
	 * @generated
	 * @ordered
	 */
	protected static final String TAG_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTag() <em>Tag</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTag()
	 * @generated
	 * @ordered
	 */
	protected String tag = TAG_EDEFAULT;

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

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Correspondences getParent() {
		if (eContainerFeatureID() != CorrespondencePackage.CORRESPONDENCE__PARENT) return null;
		return (Correspondences)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParent(Correspondences newParent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParent, CorrespondencePackage.CORRESPONDENCE__PARENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(Correspondences newParent) {
		if (newParent != eInternalContainer() || (eContainerFeatureID() != CorrespondencePackage.CORRESPONDENCE__PARENT && newParent != null)) {
			if (EcoreUtil.isAncestor(this, newParent))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParent != null)
				msgs = ((InternalEObject)newParent).eInverseAdd(this, CorrespondencePackage.CORRESPONDENCES__CORRESPONDENCES, Correspondences.class, msgs);
			msgs = basicSetParent(newParent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondencePackage.CORRESPONDENCE__PARENT, newParent, newParent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Correspondence> getDependsOn() {
		if (dependsOn == null) {
			dependsOn = new EObjectWithInverseResolvingEList.ManyInverse<Correspondence>(Correspondence.class, this, CorrespondencePackage.CORRESPONDENCE__DEPENDS_ON, CorrespondencePackage.CORRESPONDENCE__DEPENDED_ON_BY);
		}
		return dependsOn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Correspondence> getDependedOnBy() {
		if (dependedOnBy == null) {
			dependedOnBy = new EObjectWithInverseResolvingEList.ManyInverse<Correspondence>(Correspondence.class, this, CorrespondencePackage.CORRESPONDENCE__DEPENDED_ON_BY, CorrespondencePackage.CORRESPONDENCE__DEPENDS_ON);
		}
		return dependedOnBy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Tuid> getATuids() {
		if (aTuids == null) {
			aTuids = new EDataTypeUniqueEList<Tuid>(Tuid.class, this, CorrespondencePackage.CORRESPONDENCE__ATUIDS);
		}
		return aTuids;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Tuid> getBTuids() {
		if (bTuids == null) {
			bTuids = new EDataTypeUniqueEList<Tuid>(Tuid.class, this, CorrespondencePackage.CORRESPONDENCE__BTUIDS);
		}
		return bTuids;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTag(String newTag) {
		String oldTag = tag;
		tag = newTag;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondencePackage.CORRESPONDENCE__TAG, oldTag, tag));
	}

	/**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public EList<EObject> getAs() {
        return resolveEObjects(getATuids());
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public EList<EObject> getBs() {
        return resolveEObjects(getBTuids());
    }

    /**
     * @generated NOT
     */
    private EList<EObject> resolveEObjects(List<Tuid> tuids) {
    	EList<EObject> result = new BasicEList<EObject>();
    	for (Tuid tuid : tuids) {
    		result.add(getParent().getCorrespondenceModel().resolveEObjectFromTuid(tuid));
    	}
    	return result;
    }
    
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CorrespondencePackage.CORRESPONDENCE__PARENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParent((Correspondences)otherEnd, msgs);
			case CorrespondencePackage.CORRESPONDENCE__DEPENDS_ON:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getDependsOn()).basicAdd(otherEnd, msgs);
			case CorrespondencePackage.CORRESPONDENCE__DEPENDED_ON_BY:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getDependedOnBy()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CorrespondencePackage.CORRESPONDENCE__PARENT:
				return basicSetParent(null, msgs);
			case CorrespondencePackage.CORRESPONDENCE__DEPENDS_ON:
				return ((InternalEList<?>)getDependsOn()).basicRemove(otherEnd, msgs);
			case CorrespondencePackage.CORRESPONDENCE__DEPENDED_ON_BY:
				return ((InternalEList<?>)getDependedOnBy()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case CorrespondencePackage.CORRESPONDENCE__PARENT:
				return eInternalContainer().eInverseRemove(this, CorrespondencePackage.CORRESPONDENCES__CORRESPONDENCES, Correspondences.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CorrespondencePackage.CORRESPONDENCE__PARENT:
				return getParent();
			case CorrespondencePackage.CORRESPONDENCE__DEPENDS_ON:
				return getDependsOn();
			case CorrespondencePackage.CORRESPONDENCE__DEPENDED_ON_BY:
				return getDependedOnBy();
			case CorrespondencePackage.CORRESPONDENCE__ATUIDS:
				return getATuids();
			case CorrespondencePackage.CORRESPONDENCE__BTUIDS:
				return getBTuids();
			case CorrespondencePackage.CORRESPONDENCE__TAG:
				return getTag();
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
			case CorrespondencePackage.CORRESPONDENCE__PARENT:
				setParent((Correspondences)newValue);
				return;
			case CorrespondencePackage.CORRESPONDENCE__DEPENDS_ON:
				getDependsOn().clear();
				getDependsOn().addAll((Collection<? extends Correspondence>)newValue);
				return;
			case CorrespondencePackage.CORRESPONDENCE__DEPENDED_ON_BY:
				getDependedOnBy().clear();
				getDependedOnBy().addAll((Collection<? extends Correspondence>)newValue);
				return;
			case CorrespondencePackage.CORRESPONDENCE__ATUIDS:
				getATuids().clear();
				getATuids().addAll((Collection<? extends Tuid>)newValue);
				return;
			case CorrespondencePackage.CORRESPONDENCE__BTUIDS:
				getBTuids().clear();
				getBTuids().addAll((Collection<? extends Tuid>)newValue);
				return;
			case CorrespondencePackage.CORRESPONDENCE__TAG:
				setTag((String)newValue);
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
			case CorrespondencePackage.CORRESPONDENCE__PARENT:
				setParent((Correspondences)null);
				return;
			case CorrespondencePackage.CORRESPONDENCE__DEPENDS_ON:
				getDependsOn().clear();
				return;
			case CorrespondencePackage.CORRESPONDENCE__DEPENDED_ON_BY:
				getDependedOnBy().clear();
				return;
			case CorrespondencePackage.CORRESPONDENCE__ATUIDS:
				getATuids().clear();
				return;
			case CorrespondencePackage.CORRESPONDENCE__BTUIDS:
				getBTuids().clear();
				return;
			case CorrespondencePackage.CORRESPONDENCE__TAG:
				setTag(TAG_EDEFAULT);
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
			case CorrespondencePackage.CORRESPONDENCE__PARENT:
				return getParent() != null;
			case CorrespondencePackage.CORRESPONDENCE__DEPENDS_ON:
				return dependsOn != null && !dependsOn.isEmpty();
			case CorrespondencePackage.CORRESPONDENCE__DEPENDED_ON_BY:
				return dependedOnBy != null && !dependedOnBy.isEmpty();
			case CorrespondencePackage.CORRESPONDENCE__ATUIDS:
				return aTuids != null && !aTuids.isEmpty();
			case CorrespondencePackage.CORRESPONDENCE__BTUIDS:
				return bTuids != null && !bTuids.isEmpty();
			case CorrespondencePackage.CORRESPONDENCE__TAG:
				return TAG_EDEFAULT == null ? tag != null : !TAG_EDEFAULT.equals(tag);
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
		result.append(" (aTuids: ");
		result.append(aTuids);
		result.append(", bTuids: ");
		result.append(bTuids);
		result.append(", tag: ");
		result.append(tag);
		result.append(')');
		return result.toString();
	}

} //CorrespondenceImpl
