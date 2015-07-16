/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.protocol.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;
import org.palladiosimulator.pcm.protocol.Protocol;
import org.palladiosimulator.pcm.protocol.ProtocolPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Protocol</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.protocol.impl.ProtocolImpl#getProtocolTypeID
 * <em>Protocol Type ID</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ProtocolImpl extends CDOObjectImpl implements Protocol {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The default value of the '{@link #getProtocolTypeID() <em>Protocol Type ID</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getProtocolTypeID()
     * @generated
     * @ordered
     */
    protected static final String PROTOCOL_TYPE_ID_EDEFAULT = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ProtocolImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ProtocolPackage.Literals.PROTOCOL;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected int eStaticFeatureCount() {
        return 0;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getProtocolTypeID() {
        return (String) this.eDynamicGet(ProtocolPackage.PROTOCOL__PROTOCOL_TYPE_ID,
                ProtocolPackage.Literals.PROTOCOL__PROTOCOL_TYPE_ID, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setProtocolTypeID(final String newProtocolTypeID) {
        this.eDynamicSet(ProtocolPackage.PROTOCOL__PROTOCOL_TYPE_ID,
                ProtocolPackage.Literals.PROTOCOL__PROTOCOL_TYPE_ID, newProtocolTypeID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case ProtocolPackage.PROTOCOL__PROTOCOL_TYPE_ID:
            return this.getProtocolTypeID();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eSet(final int featureID, final Object newValue) {
        switch (featureID) {
        case ProtocolPackage.PROTOCOL__PROTOCOL_TYPE_ID:
            this.setProtocolTypeID((String) newValue);
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
        case ProtocolPackage.PROTOCOL__PROTOCOL_TYPE_ID:
            this.setProtocolTypeID(PROTOCOL_TYPE_ID_EDEFAULT);
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
        case ProtocolPackage.PROTOCOL__PROTOCOL_TYPE_ID:
            return PROTOCOL_TYPE_ID_EDEFAULT == null ? this.getProtocolTypeID() != null
                    : !PROTOCOL_TYPE_ID_EDEFAULT.equals(this.getProtocolTypeID());
        }
        return super.eIsSet(featureID);
    }

} // ProtocolImpl
