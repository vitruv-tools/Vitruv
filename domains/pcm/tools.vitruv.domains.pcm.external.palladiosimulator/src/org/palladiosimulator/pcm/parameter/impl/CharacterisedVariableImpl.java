/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.parameter.impl;

import org.eclipse.emf.ecore.EClass;
import org.palladiosimulator.pcm.parameter.CharacterisedVariable;
import org.palladiosimulator.pcm.parameter.ParameterPackage;
import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;

import de.uka.ipd.sdq.stoex.impl.VariableImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Characterised Variable</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.parameter.impl.CharacterisedVariableImpl#getCharacterisationType
 * <em>Characterisation Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CharacterisedVariableImpl extends VariableImpl implements CharacterisedVariable {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The default value of the '{@link #getCharacterisationType() <em>Characterisation Type</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getCharacterisationType()
     * @generated
     * @ordered
     */
    protected static final VariableCharacterisationType CHARACTERISATION_TYPE_EDEFAULT = VariableCharacterisationType.STRUCTURE;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected CharacterisedVariableImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ParameterPackage.Literals.CHARACTERISED_VARIABLE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public VariableCharacterisationType getCharacterisationType() {
        return (VariableCharacterisationType) this.eDynamicGet(
                ParameterPackage.CHARACTERISED_VARIABLE__CHARACTERISATION_TYPE,
                ParameterPackage.Literals.CHARACTERISED_VARIABLE__CHARACTERISATION_TYPE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setCharacterisationType(final VariableCharacterisationType newCharacterisationType) {
        this.eDynamicSet(ParameterPackage.CHARACTERISED_VARIABLE__CHARACTERISATION_TYPE,
                ParameterPackage.Literals.CHARACTERISED_VARIABLE__CHARACTERISATION_TYPE, newCharacterisationType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case ParameterPackage.CHARACTERISED_VARIABLE__CHARACTERISATION_TYPE:
            return this.getCharacterisationType();
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
        case ParameterPackage.CHARACTERISED_VARIABLE__CHARACTERISATION_TYPE:
            this.setCharacterisationType((VariableCharacterisationType) newValue);
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
        case ParameterPackage.CHARACTERISED_VARIABLE__CHARACTERISATION_TYPE:
            this.setCharacterisationType(CHARACTERISATION_TYPE_EDEFAULT);
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
        case ParameterPackage.CHARACTERISED_VARIABLE__CHARACTERISATION_TYPE:
            return this.getCharacterisationType() != CHARACTERISATION_TYPE_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

} // CharacterisedVariableImpl
