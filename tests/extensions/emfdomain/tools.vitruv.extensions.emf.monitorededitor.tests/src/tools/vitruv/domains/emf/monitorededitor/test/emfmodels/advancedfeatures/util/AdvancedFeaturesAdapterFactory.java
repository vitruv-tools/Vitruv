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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.AdvancedFeaturesPackage
 * @generated
 */
public class AdvancedFeaturesAdapterFactory extends AdapterFactoryImpl {
    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static AdvancedFeaturesPackage modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AdvancedFeaturesAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = AdvancedFeaturesPackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object.
     * <!-- begin-user-doc -->
     * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
     * <!-- end-user-doc -->
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object object) {
        if (object == modelPackage) {
            return true;
        }
        if (object instanceof EObject) {
            return ((EObject)object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch that delegates to the <code>createXXX</code> methods.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AdvancedFeaturesSwitch<Adapter> modelSwitch =
        new AdvancedFeaturesSwitch<Adapter>() {
            @Override
            public Adapter caseFeatureMapContaining(FeatureMapContaining object) {
                return createFeatureMapContainingAdapter();
            }
            @Override
            public Adapter caseReferenceListContaining(ReferenceListContaining object) {
                return createReferenceListContainingAdapter();
            }
            @Override
            public Adapter caseDummyData(DummyData object) {
                return createDummyDataAdapter();
            }
            @Override
            public Adapter caseDummyDataContainer(DummyDataContainer object) {
                return createDummyDataContainerAdapter();
            }
            @Override
            public Adapter caseAttributeListContaining(AttributeListContaining object) {
                return createAttributeListContainingAdapter();
            }
            @Override
            public Adapter caseRootContainer(RootContainer object) {
                return createRootContainerAdapter();
            }
            @Override
            public Adapter defaultCase(EObject object) {
                return createEObjectAdapter();
            }
        };

    /**
     * Creates an adapter for the <code>target</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param target the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(Notifier target) {
        return modelSwitch.doSwitch((EObject)target);
    }


    /**
     * Creates a new adapter for an object of class '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.FeatureMapContaining <em>Feature Map Containing</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.FeatureMapContaining
     * @generated
     */
    public Adapter createFeatureMapContainingAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.ReferenceListContaining <em>Reference List Containing</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.ReferenceListContaining
     * @generated
     */
    public Adapter createReferenceListContainingAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.DummyData <em>Dummy Data</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.DummyData
     * @generated
     */
    public Adapter createDummyDataAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.DummyDataContainer <em>Dummy Data Container</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.DummyDataContainer
     * @generated
     */
    public Adapter createDummyDataContainerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.AttributeListContaining <em>Attribute List Containing</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.AttributeListContaining
     * @generated
     */
    public Adapter createAttributeListContainingAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.RootContainer <em>Root Container</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.RootContainer
     * @generated
     */
    public Adapter createRootContainerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for the default case.
     * <!-- begin-user-doc -->
     * This default implementation returns null.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} //AdvancedFeaturesAdapterFactory
