/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Infrastructure Interface</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.InfrastructureInterface#getInfrastructureSignatures__InfrastructureInterface
 * <em>Infrastructure Signatures Infrastructure Interface</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getInfrastructureInterface()
 * @model
 * @generated
 */
public interface InfrastructureInterface extends Interface {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Infrastructure Signatures Infrastructure Interface</b></em>'
     * containment reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.repository.InfrastructureSignature}. It is bidirectional and
     * its opposite is '
     * {@link org.palladiosimulator.pcm.repository.InfrastructureSignature#getInfrastructureInterface__InfrastructureSignature
     * <em>Infrastructure Interface Infrastructure Signature</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Infrastructure Signatures Infrastructure Interface</em>'
     * containment reference list isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Infrastructure Signatures Infrastructure Interface</em>'
     *         containment reference list.
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getInfrastructureInterface_InfrastructureSignatures__InfrastructureInterface()
     * @see org.palladiosimulator.pcm.repository.InfrastructureSignature#getInfrastructureInterface__InfrastructureSignature
     * @model opposite="infrastructureInterface__InfrastructureSignature" containment="true"
     *        ordered="false"
     * @generated
     */
    EList<InfrastructureSignature> getInfrastructureSignatures__InfrastructureInterface();

} // InfrastructureInterface
