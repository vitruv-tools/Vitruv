/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Operation Signature</b></em>
 * '. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> An interface specific to operations and a operation specific association
 * to parameters and return values. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.OperationSignature#getInterface__OperationSignature
 * <em>Interface Operation Signature</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.OperationSignature#getParameters__OperationSignature
 * <em>Parameters Operation Signature</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.OperationSignature#getReturnType__OperationSignature
 * <em>Return Type Operation Signature</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getOperationSignature()
 * @model
 * @generated
 */
public interface OperationSignature extends Signature {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Interface Operation Signature</b></em>' container reference.
     * It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.repository.OperationInterface#getSignatures__OperationInterface
     * <em>Signatures Operation Interface</em>}'. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
     * begin-model-doc --> This property represents the interface that contains the method with this
     * signature. <!-- end-model-doc -->
     *
     * @return the value of the '<em>Interface Operation Signature</em>' container reference.
     * @see #setInterface__OperationSignature(OperationInterface)
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getOperationSignature_Interface__OperationSignature()
     * @see org.palladiosimulator.pcm.repository.OperationInterface#getSignatures__OperationInterface
     * @model opposite="signatures__OperationInterface" required="true" transient="false"
     * @generated
     */
    OperationInterface getInterface__OperationSignature();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.repository.OperationSignature#getInterface__OperationSignature
     * <em>Interface Operation Signature</em>}' container reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Interface Operation Signature</em>' container reference.
     * @see #getInterface__OperationSignature()
     * @generated
     */
    void setInterface__OperationSignature(OperationInterface value);

    /**
     * Returns the value of the '<em><b>Parameters Operation Signature</b></em>' containment
     * reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.repository.Parameter}. It is bidirectional and its opposite
     * is '{@link org.palladiosimulator.pcm.repository.Parameter#getOperationSignature__Parameter
     * <em>Operation Signature Parameter</em>}'. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
     * begin-model-doc --> This property represents the list of parameters of the corresponding
     * method. <!-- end-model-doc -->
     *
     * @return the value of the '<em>Parameters Operation Signature</em>' containment reference
     *         list.
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getOperationSignature_Parameters__OperationSignature()
     * @see org.palladiosimulator.pcm.repository.Parameter#getOperationSignature__Parameter
     * @model opposite="operationSignature__Parameter" containment="true"
     * @generated
     */
    EList<Parameter> getParameters__OperationSignature();

    /**
     * Returns the value of the '<em><b>Return Type Operation Signature</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> This property represents
     * the return type of the corresponding method. <!-- end-model-doc -->
     *
     * @return the value of the '<em>Return Type Operation Signature</em>' reference.
     * @see #setReturnType__OperationSignature(DataType)
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getOperationSignature_ReturnType__OperationSignature()
     * @model
     * @generated
     */
    DataType getReturnType__OperationSignature();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.repository.OperationSignature#getReturnType__OperationSignature
     * <em>Return Type Operation Signature</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Return Type Operation Signature</em>' reference.
     * @see #getReturnType__OperationSignature()
     * @generated
     */
    void setReturnType__OperationSignature(DataType value);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='self.parameters__OperationSignature->isUnique(p : Parameter |\r\n\tp.parameterName\r\n)'"
     * @generated
     */
    boolean ParameterNamesHaveToBeUniqueForASignature(DiagnosticChain diagnostics, Map<Object, Object> context);

} // OperationSignature
