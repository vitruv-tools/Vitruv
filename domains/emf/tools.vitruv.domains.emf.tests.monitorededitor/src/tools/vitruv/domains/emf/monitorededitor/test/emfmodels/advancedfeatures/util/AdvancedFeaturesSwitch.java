/*******************************************************************************
 * Copyright (c) 2014 Felix Kutzner.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Felix Kutzner - initial implementation.
 ******************************************************************************/

/**
 */
package tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.AdvancedFeaturesPackage
 * @generated
 */
public class AdvancedFeaturesSwitch<T> extends Switch<T> {
    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static AdvancedFeaturesPackage modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AdvancedFeaturesSwitch() {
        if (modelPackage == null) {
            modelPackage = AdvancedFeaturesPackage.eINSTANCE;
        }
    }

    /**
     * Checks whether this is a switch for the given package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @parameter ePackage the package in question.
     * @return whether this is a switch for the given package.
     * @generated
     */
    @Override
    protected boolean isSwitchFor(EPackage ePackage) {
        return ePackage == modelPackage;
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    @Override
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
            case AdvancedFeaturesPackage.FEATURE_MAP_CONTAINING: {
                FeatureMapContaining featureMapContaining = (FeatureMapContaining)theEObject;
                T result = caseFeatureMapContaining(featureMapContaining);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case AdvancedFeaturesPackage.REFERENCE_LIST_CONTAINING: {
                ReferenceListContaining referenceListContaining = (ReferenceListContaining)theEObject;
                T result = caseReferenceListContaining(referenceListContaining);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case AdvancedFeaturesPackage.DUMMY_DATA: {
                DummyData dummyData = (DummyData)theEObject;
                T result = caseDummyData(dummyData);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case AdvancedFeaturesPackage.DUMMY_DATA_CONTAINER: {
                DummyDataContainer dummyDataContainer = (DummyDataContainer)theEObject;
                T result = caseDummyDataContainer(dummyDataContainer);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case AdvancedFeaturesPackage.ATTRIBUTE_LIST_CONTAINING: {
                AttributeListContaining attributeListContaining = (AttributeListContaining)theEObject;
                T result = caseAttributeListContaining(attributeListContaining);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case AdvancedFeaturesPackage.ROOT_CONTAINER: {
                RootContainer rootContainer = (RootContainer)theEObject;
                T result = caseRootContainer(rootContainer);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Feature Map Containing</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Feature Map Containing</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFeatureMapContaining(FeatureMapContaining object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Reference List Containing</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Reference List Containing</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseReferenceListContaining(ReferenceListContaining object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Dummy Data</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Dummy Data</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDummyData(DummyData object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Dummy Data Container</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Dummy Data Container</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDummyDataContainer(DummyDataContainer object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Attribute List Containing</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Attribute List Containing</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAttributeListContaining(AttributeListContaining object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Root Container</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Root Container</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRootContainer(RootContainer object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch, but this is the last case anyway.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    @Override
    public T defaultCase(EObject object) {
        return null;
    }

} //AdvancedFeaturesSwitch
