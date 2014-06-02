/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Correspondence</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondenceImpl#getDependentCorrespondences
 * <em>Dependent Correspondences</em>}</li>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondenceImpl#getParent
 * <em>Parent</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public abstract class CorrespondenceImpl extends EObjectImpl implements Correspondence {
    /**
     * The cached value of the '{@link #getDependentCorrespondences()
     * <em>Dependent Correspondences</em>}' reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getDependentCorrespondences()
     * @generated
     * @ordered
     */
    protected EList<Correspondence> dependentCorrespondences;

    /**
     * The cached value of the '{@link #getParent() <em>Parent</em>}' reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @see #getParent()
     * @generated
     * @ordered
     */
    protected Correspondence parent;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected CorrespondenceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CorrespondencePackage.Literals.CORRESPONDENCE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EList<Correspondence> getDependentCorrespondences() {
        if (this.dependentCorrespondences == null) {
            this.dependentCorrespondences = new EObjectResolvingEList<Correspondence>(Correspondence.class, this,
                    CorrespondencePackage.CORRESPONDENCE__DEPENDENT_CORRESPONDENCES);
        }
        return this.dependentCorrespondences;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Correspondence getParent() {
        if (this.parent != null && this.parent.eIsProxy()) {
            final InternalEObject oldParent = (InternalEObject) this.parent;
            this.parent = (Correspondence) this.eResolveProxy(oldParent);
            if (this.parent != oldParent) {
                if (this.eNotificationRequired()) {
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            CorrespondencePackage.CORRESPONDENCE__PARENT, oldParent, this.parent));
                }
            }
        }
        return this.parent;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public Correspondence basicGetParent() {
        return this.parent;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> Set parent also adds the current object as
     * dependent correspondence to the new parent
     * 
     * @generated NOT
     */
    @Override
    public void setParent(final Correspondence newParent) {
        final Correspondence oldParent = this.parent;
        this.parent = newParent;
        if (this.eNotificationRequired()) {
            this.eNotify(new ENotificationImpl(this, Notification.SET, CorrespondencePackage.CORRESPONDENCE__PARENT,
                    oldParent, this.parent));
        }
        if (null != oldParent) {
            oldParent.getDependentCorrespondences().remove(this);
        }
        if (null != newParent) {
            this.parent.getDependentCorrespondences().add(this);
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public abstract EList<EObject> getAllInvolvedEObjects();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case CorrespondencePackage.CORRESPONDENCE__DEPENDENT_CORRESPONDENCES:
            return this.getDependentCorrespondences();
        case CorrespondencePackage.CORRESPONDENCE__PARENT:
            if (resolve) {
                return this.getParent();
            }
            return this.basicGetParent();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(final int featureID, final Object newValue) {
        switch (featureID) {
        case CorrespondencePackage.CORRESPONDENCE__DEPENDENT_CORRESPONDENCES:
            this.getDependentCorrespondences().clear();
            this.getDependentCorrespondences().addAll((Collection<? extends Correspondence>) newValue);
            return;
        case CorrespondencePackage.CORRESPONDENCE__PARENT:
            this.setParent((Correspondence) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eUnset(final int featureID) {
        switch (featureID) {
        case CorrespondencePackage.CORRESPONDENCE__DEPENDENT_CORRESPONDENCES:
            this.getDependentCorrespondences().clear();
            return;
        case CorrespondencePackage.CORRESPONDENCE__PARENT:
            this.setParent((Correspondence) null);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean eIsSet(final int featureID) {
        switch (featureID) {
        case CorrespondencePackage.CORRESPONDENCE__DEPENDENT_CORRESPONDENCES:
            return this.dependentCorrespondences != null && !this.dependentCorrespondences.isEmpty();
        case CorrespondencePackage.CORRESPONDENCE__PARENT:
            return this.parent != null;
        }
        return super.eIsSet(featureID);
    }

} // CorrespondenceImpl
