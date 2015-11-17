/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl;

import java.io.Serializable;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
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

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Correspondence</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondenceImpl#getParent
 * <em>Parent</em>}</li>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondenceImpl#getDependsOn
 * <em>Depends On</em>}</li>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondenceImpl#getDependedOnBy
 * <em>Depended On By</em>}</li>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondenceImpl#getATUIDs
 * <em>ATUI Ds</em>}</li>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondenceImpl#getBTUIDs
 * <em>BTUI Ds</em>}</li>
 * </ul>
 *
 * @generated
 */
//FIXME MAX (deco): try to generate use of serializable marker interface from ecore mm 
public class CorrespondenceImpl extends EObjectImpl implements Correspondence, Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5644019695411508924L;

	/**
     * The cached value of the '{@link #getDependsOn() <em>Depends On</em>}' reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDependsOn()
     * @generated
     * @ordered
     */
    protected EList<Correspondence> dependsOn;

    /**
     * The cached value of the '{@link #getDependedOnBy() <em>Depended On By</em>}' reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDependedOnBy()
     * @generated
     * @ordered
     */
    protected EList<Correspondence> dependedOnBy;

    /**
     * The cached value of the '{@link #getATUIDs() <em>ATUI Ds</em>}' attribute list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getATUIDs()
     * @generated
     * @ordered
     */
    protected EList<TUID> aTUIDs;

    /**
     * The cached value of the '{@link #getBTUIDs() <em>BTUI Ds</em>}' attribute list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getBTUIDs()
     * @generated
     * @ordered
     */
    protected EList<TUID> bTUIDs;

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
    public Correspondences getParent() {
        if (this.eContainerFeatureID() != CorrespondencePackage.CORRESPONDENCE__PARENT) {
            return null;
        }
        return (Correspondences) this.eInternalContainer();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetParent(final Correspondences newParent, NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newParent, CorrespondencePackage.CORRESPONDENCE__PARENT, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setParent(final Correspondences newParent) {
        if (newParent != this.eInternalContainer()
                || (this.eContainerFeatureID() != CorrespondencePackage.CORRESPONDENCE__PARENT && newParent != null)) {
            if (EcoreUtil.isAncestor(this, newParent)) {
                throw new IllegalArgumentException("Recursive containment not allowed for " + this.toString());
            }
            NotificationChain msgs = null;
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            if (newParent != null) {
                msgs = ((InternalEObject) newParent).eInverseAdd(this,
                        CorrespondencePackage.CORRESPONDENCES__CORRESPONDENCES, Correspondences.class, msgs);
            }
            msgs = this.basicSetParent(newParent, msgs);
            if (msgs != null) {
                msgs.dispatch();
            }
        } else if (this.eNotificationRequired()) {
            this.eNotify(new ENotificationImpl(this, Notification.SET, CorrespondencePackage.CORRESPONDENCE__PARENT,
                    newParent, newParent));
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Correspondence> getDependsOn() {
        if (this.dependsOn == null) {
            this.dependsOn = new EObjectWithInverseResolvingEList.ManyInverse<Correspondence>(Correspondence.class,
                    this, CorrespondencePackage.CORRESPONDENCE__DEPENDS_ON,
                    CorrespondencePackage.CORRESPONDENCE__DEPENDED_ON_BY);
        }
        return this.dependsOn;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Correspondence> getDependedOnBy() {
        if (this.dependedOnBy == null) {
            this.dependedOnBy = new EObjectWithInverseResolvingEList.ManyInverse<Correspondence>(Correspondence.class,
                    this, CorrespondencePackage.CORRESPONDENCE__DEPENDED_ON_BY,
                    CorrespondencePackage.CORRESPONDENCE__DEPENDS_ON);
        }
        return this.dependedOnBy;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<TUID> getATUIDs() {
        if (this.aTUIDs == null) {
            this.aTUIDs = new EDataTypeUniqueEList<TUID>(TUID.class, this,
                    CorrespondencePackage.CORRESPONDENCE__ATUI_DS);
        }
        return this.aTUIDs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<TUID> getBTUIDs() {
        if (this.bTUIDs == null) {
            this.bTUIDs = new EDataTypeUniqueEList<TUID>(TUID.class, this,
                    CorrespondencePackage.CORRESPONDENCE__BTUI_DS);
        }
        return this.bTUIDs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<EObject> getAs() {
        // TODO: implement this method
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<EObject> getBs() {
        // TODO: implement this method
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public TUID getElementATUID() {
        if (0 < this.getATUIDs().size()) {
            return this.getATUIDs().get(0);
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public TUID getElementBTUID() {
        if (0 < this.getBTUIDs().size()) {
            return this.getBTUIDs().get(0);
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public void setElementATUID(final TUID tuid) {
        this.getATUIDs().clear();
        this.getATUIDs().add(tuid);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public void setElementBTUID(final TUID tuid) {
        this.getBTUIDs().clear();
        this.getBTUIDs().add(tuid);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case CorrespondencePackage.CORRESPONDENCE__PARENT:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetParent((Correspondences) otherEnd, msgs);
        case CorrespondencePackage.CORRESPONDENCE__DEPENDS_ON:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getDependsOn()).basicAdd(otherEnd, msgs);
        case CorrespondencePackage.CORRESPONDENCE__DEPENDED_ON_BY:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getDependedOnBy()).basicAdd(otherEnd,
                    msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(final InternalEObject otherEnd, final int featureID,
            final NotificationChain msgs) {
        switch (featureID) {
        case CorrespondencePackage.CORRESPONDENCE__PARENT:
            return this.basicSetParent(null, msgs);
        case CorrespondencePackage.CORRESPONDENCE__DEPENDS_ON:
            return ((InternalEList<?>) this.getDependsOn()).basicRemove(otherEnd, msgs);
        case CorrespondencePackage.CORRESPONDENCE__DEPENDED_ON_BY:
            return ((InternalEList<?>) this.getDependedOnBy()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eBasicRemoveFromContainerFeature(final NotificationChain msgs) {
        switch (this.eContainerFeatureID()) {
        case CorrespondencePackage.CORRESPONDENCE__PARENT:
            return this.eInternalContainer().eInverseRemove(this,
                    CorrespondencePackage.CORRESPONDENCES__CORRESPONDENCES, Correspondences.class, msgs);
        }
        return super.eBasicRemoveFromContainerFeature(msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case CorrespondencePackage.CORRESPONDENCE__PARENT:
            return this.getParent();
        case CorrespondencePackage.CORRESPONDENCE__DEPENDS_ON:
            return this.getDependsOn();
        case CorrespondencePackage.CORRESPONDENCE__DEPENDED_ON_BY:
            return this.getDependedOnBy();
        case CorrespondencePackage.CORRESPONDENCE__ATUI_DS:
            return this.getATUIDs();
        case CorrespondencePackage.CORRESPONDENCE__BTUI_DS:
            return this.getBTUIDs();
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
        case CorrespondencePackage.CORRESPONDENCE__PARENT:
            this.setParent((Correspondences) newValue);
            return;
        case CorrespondencePackage.CORRESPONDENCE__DEPENDS_ON:
            this.getDependsOn().clear();
            this.getDependsOn().addAll((Collection<? extends Correspondence>) newValue);
            return;
        case CorrespondencePackage.CORRESPONDENCE__DEPENDED_ON_BY:
            this.getDependedOnBy().clear();
            this.getDependedOnBy().addAll((Collection<? extends Correspondence>) newValue);
            return;
        case CorrespondencePackage.CORRESPONDENCE__ATUI_DS:
            this.getATUIDs().clear();
            this.getATUIDs().addAll((Collection<? extends TUID>) newValue);
            return;
        case CorrespondencePackage.CORRESPONDENCE__BTUI_DS:
            this.getBTUIDs().clear();
            this.getBTUIDs().addAll((Collection<? extends TUID>) newValue);
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
        case CorrespondencePackage.CORRESPONDENCE__PARENT:
            this.setParent((Correspondences) null);
            return;
        case CorrespondencePackage.CORRESPONDENCE__DEPENDS_ON:
            this.getDependsOn().clear();
            return;
        case CorrespondencePackage.CORRESPONDENCE__DEPENDED_ON_BY:
            this.getDependedOnBy().clear();
            return;
        case CorrespondencePackage.CORRESPONDENCE__ATUI_DS:
            this.getATUIDs().clear();
            return;
        case CorrespondencePackage.CORRESPONDENCE__BTUI_DS:
            this.getBTUIDs().clear();
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
        case CorrespondencePackage.CORRESPONDENCE__PARENT:
            return this.getParent() != null;
        case CorrespondencePackage.CORRESPONDENCE__DEPENDS_ON:
            return this.dependsOn != null && !this.dependsOn.isEmpty();
        case CorrespondencePackage.CORRESPONDENCE__DEPENDED_ON_BY:
            return this.dependedOnBy != null && !this.dependedOnBy.isEmpty();
        case CorrespondencePackage.CORRESPONDENCE__ATUI_DS:
            return this.aTUIDs != null && !this.aTUIDs.isEmpty();
        case CorrespondencePackage.CORRESPONDENCE__BTUI_DS:
            return this.bTUIDs != null && !this.bTUIDs.isEmpty();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        if (this.eIsProxy()) {
            return super.toString();
        }

        final StringBuffer result = new StringBuffer(super.toString());
        result.append(" (aTUIDs: ");
        result.append(this.aTUIDs);
        result.append(", bTUIDs: ");
        result.append(this.bTUIDs);
        result.append(')');
        return result.toString();
    }

} // CorrespondenceImpl
