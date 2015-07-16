/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.usagemodel;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.parameter.VariableUsage;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>User Data</b></em>'. <!--
 * end-user-doc -->
 *
 * <!-- begin-model-doc --> UserData characterises data used in specific assembly contexts in the
 * system. This data is the same for all UsageScenarios, i.e., multiple users accessing the same
 * components access the same data. This UserData refers to component parameters of the system
 * publicized by the software architect (see pcm::parameters package). The domain expert
 * characterises the values of component parameters related to business concepts (e.g., user
 * specific data, data specific for a business domain), whereas the software architect characterises
 * the values of component parameters related to technical concepts (e.g., size of caches, size of a
 * thread pool, configuration data, etc.). One UserData instance includes all parameter
 * characterisation for the annotated entity. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.usagemodel.UserData#getAssemblyContext_userData
 * <em>Assembly Context user Data</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.usagemodel.UserData#getUsageModel_UserData
 * <em>Usage Model User Data</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.usagemodel.UserData#getUserDataParameterUsages_UserData
 * <em>User Data Parameter Usages User Data</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.usagemodel.UsagemodelPackage#getUserData()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface UserData extends CDOObject {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Assembly Context user Data</b></em>' reference. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Assembly Context user Data</em>' reference isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Assembly Context user Data</em>' reference.
     * @see #setAssemblyContext_userData(AssemblyContext)
     * @see org.palladiosimulator.pcm.usagemodel.UsagemodelPackage#getUserData_AssemblyContext_userData()
     * @model required="true"
     * @generated
     */
    AssemblyContext getAssemblyContext_userData();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.usagemodel.UserData#getAssemblyContext_userData
     * <em>Assembly Context user Data</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @param value
     *            the new value of the '<em>Assembly Context user Data</em>' reference.
     * @see #getAssemblyContext_userData()
     * @generated
     */
    void setAssemblyContext_userData(AssemblyContext value);

    /**
     * Returns the value of the '<em><b>Usage Model User Data</b></em>' container reference. It is
     * bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.usagemodel.UsageModel#getUserData_UsageModel
     * <em>User Data Usage Model</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Usage Model User Data</em>' container reference isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Usage Model User Data</em>' container reference.
     * @see #setUsageModel_UserData(UsageModel)
     * @see org.palladiosimulator.pcm.usagemodel.UsagemodelPackage#getUserData_UsageModel_UserData()
     * @see org.palladiosimulator.pcm.usagemodel.UsageModel#getUserData_UsageModel
     * @model opposite="userData_UsageModel" required="true" transient="false" ordered="false"
     * @generated
     */
    UsageModel getUsageModel_UserData();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.usagemodel.UserData#getUsageModel_UserData
     * <em>Usage Model User Data</em>}' container reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Usage Model User Data</em>' container reference.
     * @see #getUsageModel_UserData()
     * @generated
     */
    void setUsageModel_UserData(UsageModel value);

    /**
     * Returns the value of the '<em><b>User Data Parameter Usages User Data</b></em>' containment
     * reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage}. It is bidirectional and its
     * opposite is '
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getUserData_VariableUsage
     * <em>User Data Variable Usage</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>User Data Parameter Usages User Data</em>' containment reference
     * list isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>User Data Parameter Usages User Data</em>' containment
     *         reference list.
     * @see org.palladiosimulator.pcm.usagemodel.UsagemodelPackage#getUserData_UserDataParameterUsages_UserData()
     * @see org.palladiosimulator.pcm.parameter.VariableUsage#getUserData_VariableUsage
     * @model opposite="userData_VariableUsage" containment="true" ordered="false"
     * @generated
     */
    EList<VariableUsage> getUserDataParameterUsages_UserData();

} // UserData
