/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Correspondence</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondenceImpl#getDependentCorrespondences <em>Dependent Correspondences</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondenceImpl#getParent <em>Parent</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class CorrespondenceImpl extends EObjectImpl implements Correspondence {
    /**
	 * The cached value of the '{@link #getDependentCorrespondences() <em>Dependent Correspondences</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getDependentCorrespondences()
	 * @generated
	 * @ordered
	 */
    protected EList<Correspondence> dependentCorrespondences;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected CorrespondenceImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return CorrespondencePackage.Literals.CORRESPONDENCE;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Correspondence> getDependentCorrespondences() {
		if (dependentCorrespondences == null) {
			dependentCorrespondences = new EObjectContainmentWithInverseEList<Correspondence>(Correspondence.class, this, CorrespondencePackage.CORRESPONDENCE__DEPENDENT_CORRESPONDENCES, CorrespondencePackage.CORRESPONDENCE__PARENT);
		}
		return dependentCorrespondences;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Correspondence getParent() {
		if (eContainerFeatureID() != CorrespondencePackage.CORRESPONDENCE__PARENT) return null;
		return (Correspondence)eInternalContainer();
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParent(Correspondence newParent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParent, CorrespondencePackage.CORRESPONDENCE__PARENT, msgs);
		return msgs;
	}

				/**
     * <!-- begin-user-doc --> <!-- end-user-doc --> Set parent also adds the current object as
     * dependent correspondence to the new parent
     * 
     * @generated
     */
    @Override
    public void setParent(Correspondence newParent) {
		if (newParent != eInternalContainer() || (eContainerFeatureID() != CorrespondencePackage.CORRESPONDENCE__PARENT && newParent != null)) {
			if (EcoreUtil.isAncestor(this, newParent))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParent != null)
				msgs = ((InternalEObject)newParent).eInverseAdd(this, CorrespondencePackage.CORRESPONDENCE__DEPENDENT_CORRESPONDENCES, Correspondence.class, msgs);
			msgs = basicSetParent(newParent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondencePackage.CORRESPONDENCE__PARENT, newParent, newParent));
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public abstract EList<EObject> getAllInvolvedEObjects();

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CorrespondencePackage.CORRESPONDENCE__DEPENDENT_CORRESPONDENCES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getDependentCorrespondences()).basicAdd(otherEnd, msgs);
			case CorrespondencePackage.CORRESPONDENCE__PARENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParent((Correspondence)otherEnd, msgs);
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
			case CorrespondencePackage.CORRESPONDENCE__DEPENDENT_CORRESPONDENCES:
				return ((InternalEList<?>)getDependentCorrespondences()).basicRemove(otherEnd, msgs);
			case CorrespondencePackage.CORRESPONDENCE__PARENT:
				return basicSetParent(null, msgs);
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
				return eInternalContainer().eInverseRemove(this, CorrespondencePackage.CORRESPONDENCE__DEPENDENT_CORRESPONDENCES, Correspondence.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

				/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CorrespondencePackage.CORRESPONDENCE__DEPENDENT_CORRESPONDENCES:
				return getDependentCorrespondences();
			case CorrespondencePackage.CORRESPONDENCE__PARENT:
				return getParent();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CorrespondencePackage.CORRESPONDENCE__DEPENDENT_CORRESPONDENCES:
				getDependentCorrespondences().clear();
				getDependentCorrespondences().addAll((Collection<? extends Correspondence>)newValue);
				return;
			case CorrespondencePackage.CORRESPONDENCE__PARENT:
				setParent((Correspondence)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eUnset(int featureID) {
		switch (featureID) {
			case CorrespondencePackage.CORRESPONDENCE__DEPENDENT_CORRESPONDENCES:
				getDependentCorrespondences().clear();
				return;
			case CorrespondencePackage.CORRESPONDENCE__PARENT:
				setParent((Correspondence)null);
				return;
		}
		super.eUnset(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CorrespondencePackage.CORRESPONDENCE__DEPENDENT_CORRESPONDENCES:
				return dependentCorrespondences != null && !dependentCorrespondences.isEmpty();
			case CorrespondencePackage.CORRESPONDENCE__PARENT:
				return getParent() != null;
		}
		return super.eIsSet(featureID);
	}

} // CorrespondenceImpl
