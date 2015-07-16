/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.InternalEList;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.EventType;
import org.palladiosimulator.pcm.repository.SourceRole;
import org.palladiosimulator.pcm.seff.CallAction;
import org.palladiosimulator.pcm.seff.EmitEventAction;
import org.palladiosimulator.pcm.seff.SeffPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Emit Event Action</b></em>
 * '. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.impl.EmitEventActionImpl#getInputVariableUsages__CallAction
 * <em>Input Variable Usages Call Action</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.seff.impl.EmitEventActionImpl#getEventType__EmitEventAction
 * <em>Event Type Emit Event Action</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.seff.impl.EmitEventActionImpl#getSourceRole__EmitEventAction
 * <em>Source Role Emit Event Action</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EmitEventActionImpl extends AbstractActionImpl implements EmitEventAction {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected EmitEventActionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SeffPackage.Literals.EMIT_EVENT_ACTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<VariableUsage> getInputVariableUsages__CallAction() {
        return (EList<VariableUsage>) this.eDynamicGet(SeffPackage.EMIT_EVENT_ACTION__INPUT_VARIABLE_USAGES_CALL_ACTION,
                SeffPackage.Literals.CALL_ACTION__INPUT_VARIABLE_USAGES_CALL_ACTION, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EventType getEventType__EmitEventAction() {
        return (EventType) this.eDynamicGet(SeffPackage.EMIT_EVENT_ACTION__EVENT_TYPE_EMIT_EVENT_ACTION,
                SeffPackage.Literals.EMIT_EVENT_ACTION__EVENT_TYPE_EMIT_EVENT_ACTION, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public EventType basicGetEventType__EmitEventAction() {
        return (EventType) this.eDynamicGet(SeffPackage.EMIT_EVENT_ACTION__EVENT_TYPE_EMIT_EVENT_ACTION,
                SeffPackage.Literals.EMIT_EVENT_ACTION__EVENT_TYPE_EMIT_EVENT_ACTION, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setEventType__EmitEventAction(final EventType newEventType__EmitEventAction) {
        this.eDynamicSet(SeffPackage.EMIT_EVENT_ACTION__EVENT_TYPE_EMIT_EVENT_ACTION,
                SeffPackage.Literals.EMIT_EVENT_ACTION__EVENT_TYPE_EMIT_EVENT_ACTION, newEventType__EmitEventAction);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public SourceRole getSourceRole__EmitEventAction() {
        return (SourceRole) this.eDynamicGet(SeffPackage.EMIT_EVENT_ACTION__SOURCE_ROLE_EMIT_EVENT_ACTION,
                SeffPackage.Literals.EMIT_EVENT_ACTION__SOURCE_ROLE_EMIT_EVENT_ACTION, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public SourceRole basicGetSourceRole__EmitEventAction() {
        return (SourceRole) this.eDynamicGet(SeffPackage.EMIT_EVENT_ACTION__SOURCE_ROLE_EMIT_EVENT_ACTION,
                SeffPackage.Literals.EMIT_EVENT_ACTION__SOURCE_ROLE_EMIT_EVENT_ACTION, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setSourceRole__EmitEventAction(final SourceRole newSourceRole__EmitEventAction) {
        this.eDynamicSet(SeffPackage.EMIT_EVENT_ACTION__SOURCE_ROLE_EMIT_EVENT_ACTION,
                SeffPackage.Literals.EMIT_EVENT_ACTION__SOURCE_ROLE_EMIT_EVENT_ACTION, newSourceRole__EmitEventAction);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID,
            final NotificationChain msgs) {
        switch (featureID) {
        case SeffPackage.EMIT_EVENT_ACTION__INPUT_VARIABLE_USAGES_CALL_ACTION:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getInputVariableUsages__CallAction())
                    .basicAdd(otherEnd, msgs);
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
        case SeffPackage.EMIT_EVENT_ACTION__INPUT_VARIABLE_USAGES_CALL_ACTION:
            return ((InternalEList<?>) this.getInputVariableUsages__CallAction()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case SeffPackage.EMIT_EVENT_ACTION__INPUT_VARIABLE_USAGES_CALL_ACTION:
            return this.getInputVariableUsages__CallAction();
        case SeffPackage.EMIT_EVENT_ACTION__EVENT_TYPE_EMIT_EVENT_ACTION:
            if (resolve) {
                return this.getEventType__EmitEventAction();
            }
            return this.basicGetEventType__EmitEventAction();
        case SeffPackage.EMIT_EVENT_ACTION__SOURCE_ROLE_EMIT_EVENT_ACTION:
            if (resolve) {
                return this.getSourceRole__EmitEventAction();
            }
            return this.basicGetSourceRole__EmitEventAction();
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
        case SeffPackage.EMIT_EVENT_ACTION__INPUT_VARIABLE_USAGES_CALL_ACTION:
            this.getInputVariableUsages__CallAction().clear();
            this.getInputVariableUsages__CallAction().addAll((Collection<? extends VariableUsage>) newValue);
            return;
        case SeffPackage.EMIT_EVENT_ACTION__EVENT_TYPE_EMIT_EVENT_ACTION:
            this.setEventType__EmitEventAction((EventType) newValue);
            return;
        case SeffPackage.EMIT_EVENT_ACTION__SOURCE_ROLE_EMIT_EVENT_ACTION:
            this.setSourceRole__EmitEventAction((SourceRole) newValue);
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
        case SeffPackage.EMIT_EVENT_ACTION__INPUT_VARIABLE_USAGES_CALL_ACTION:
            this.getInputVariableUsages__CallAction().clear();
            return;
        case SeffPackage.EMIT_EVENT_ACTION__EVENT_TYPE_EMIT_EVENT_ACTION:
            this.setEventType__EmitEventAction((EventType) null);
            return;
        case SeffPackage.EMIT_EVENT_ACTION__SOURCE_ROLE_EMIT_EVENT_ACTION:
            this.setSourceRole__EmitEventAction((SourceRole) null);
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
        case SeffPackage.EMIT_EVENT_ACTION__INPUT_VARIABLE_USAGES_CALL_ACTION:
            return !this.getInputVariableUsages__CallAction().isEmpty();
        case SeffPackage.EMIT_EVENT_ACTION__EVENT_TYPE_EMIT_EVENT_ACTION:
            return this.basicGetEventType__EmitEventAction() != null;
        case SeffPackage.EMIT_EVENT_ACTION__SOURCE_ROLE_EMIT_EVENT_ACTION:
            return this.basicGetSourceRole__EmitEventAction() != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(final int derivedFeatureID, final Class<?> baseClass) {
        if (baseClass == CallAction.class) {
            switch (derivedFeatureID) {
            case SeffPackage.EMIT_EVENT_ACTION__INPUT_VARIABLE_USAGES_CALL_ACTION:
                return SeffPackage.CALL_ACTION__INPUT_VARIABLE_USAGES_CALL_ACTION;
            default:
                return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(final int baseFeatureID, final Class<?> baseClass) {
        if (baseClass == CallAction.class) {
            switch (baseFeatureID) {
            case SeffPackage.CALL_ACTION__INPUT_VARIABLE_USAGES_CALL_ACTION:
                return SeffPackage.EMIT_EVENT_ACTION__INPUT_VARIABLE_USAGES_CALL_ACTION;
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

} // EmitEventActionImpl
