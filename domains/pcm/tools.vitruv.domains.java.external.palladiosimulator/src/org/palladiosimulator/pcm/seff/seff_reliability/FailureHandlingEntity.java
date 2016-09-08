/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.seff_reliability;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.reliability.FailureType;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Failure Handling Entity</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * <p>
 * Failure handling entities are any program constructs that can handle failures. Instances of
 * failure handling entities specify any number of failure types that can be handled.
 * </p>
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.seff_reliability.FailureHandlingEntity#getFailureTypes_FailureHandlingEntity
 * <em>Failure Types Failure Handling Entity</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.seff.seff_reliability.SeffReliabilityPackage#getFailureHandlingEntity()
 * @model abstract="true"
 * @generated
 */
public interface FailureHandlingEntity extends Entity {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Failure Types Failure Handling Entity</b></em>' reference
     * list. The list contents are of type {@link org.palladiosimulator.pcm.reliability.FailureType}
     * . <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Failure Types Failure Handling Entity</em>' reference list isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Failure Types Failure Handling Entity</em>' reference list.
     * @see org.palladiosimulator.pcm.seff.seff_reliability.SeffReliabilityPackage#getFailureHandlingEntity_FailureTypes_FailureHandlingEntity()
     * @model ordered="false"
     * @generated
     */
    EList<FailureType> getFailureTypes_FailureHandlingEntity();

} // FailureHandlingEntity
