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
package tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reference List Containing</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.ReferenceListContaining#getNonContainmentRefList <em>Non Containment Ref List</em>}</li>
 * </ul>
 * </p>
 *
 * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.AdvancedFeaturesPackage#getReferenceListContaining()
 * @model
 * @generated
 */
public interface ReferenceListContaining extends EObject {
    /**
     * Returns the value of the '<em><b>Non Containment Ref List</b></em>' reference list.
     * The list contents are of type {@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.DummyData}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Non Containment Ref List</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Non Containment Ref List</em>' reference list.
     * @see tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.AdvancedFeaturesPackage#getReferenceListContaining_NonContainmentRefList()
     * @model
     * @generated
     */
    EList<DummyData> getNonContainmentRefList();

} // ReferenceListContaining
