/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.parameter;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.qosannotations.SpecifiedOutputParameterAbstraction;
import org.palladiosimulator.pcm.seff.CallAction;
import org.palladiosimulator.pcm.seff.CallReturnAction;
import org.palladiosimulator.pcm.seff.SetVariableAction;
import org.palladiosimulator.pcm.seff.SynchronisationPoint;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.UserData;

import de.uka.ipd.sdq.stoex.AbstractNamedReference;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Variable Usage</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> Variable usages are used to characterise variables like input and output
 * variables or component parameters. They contain the specification of the variable as
 * VariableCharacterisation and also refer to the name of the characterised variable in its
 * namedReference association. Note that it was an explicit design decision to refer to variable
 * names instead of the actual variables (i.e., by refering to Parameter class). It eased the
 * writing of transformations (DSolver as well as SimuCom) but put some complexity in the frontend
 * for entering the variable usages. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getVariableCharacterisation_VariableUsage
 * <em>Variable Characterisation Variable Usage</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.parameter.VariableUsage#getUserData_VariableUsage
 * <em>User Data Variable Usage</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.parameter.VariableUsage#getCallAction__VariableUsage
 * <em>Call Action Variable Usage</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getSynchronisationPoint_VariableUsage
 * <em>Synchronisation Point Variable Usage</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.parameter.VariableUsage#getCallReturnAction__VariableUsage
 * <em>Call Return Action Variable Usage</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.parameter.VariableUsage#getSetVariableAction_VariableUsage
 * <em>Set Variable Action Variable Usage</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getSpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage
 * <em>Specified Output Parameter Abstraction expected External Outputs Variable Usage</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.parameter.VariableUsage#getAssemblyContext__VariableUsage
 * <em>Assembly Context Variable Usage</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getEntryLevelSystemCall_InputParameterUsage
 * <em>Entry Level System Call Input Parameter Usage</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getEntryLevelSystemCall_OutputParameterUsage
 * <em>Entry Level System Call Output Parameter Usage</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.parameter.VariableUsage#getNamedReference__VariableUsage
 * <em>Named Reference Variable Usage</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.parameter.ParameterPackage#getVariableUsage()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface VariableUsage extends CDOObject {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Variable Characterisation Variable Usage</b></em>'
     * containment reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.parameter.VariableCharacterisation}. It is bidirectional and
     * its opposite is '
     * {@link org.palladiosimulator.pcm.parameter.VariableCharacterisation#getVariableUsage_VariableCharacterisation
     * <em>Variable Usage Variable Characterisation</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc --> <!-- begin-model-doc --> This association contains the information which
     * abstract information on a specific variable is available. For example, whether we know
     * something on the variable's value, its structure or memory footprint. There can be multiple
     * characterisations of the same variable if more than one type of information is available.
     * <!-- end-model-doc -->
     *
     * @return the value of the '<em>Variable Characterisation Variable Usage</em>' containment
     *         reference list.
     * @see org.palladiosimulator.pcm.parameter.ParameterPackage#getVariableUsage_VariableCharacterisation_VariableUsage()
     * @see org.palladiosimulator.pcm.parameter.VariableCharacterisation#getVariableUsage_VariableCharacterisation
     * @model opposite="variableUsage_VariableCharacterisation" containment="true" ordered="false"
     * @generated
     */
    EList<VariableCharacterisation> getVariableCharacterisation_VariableUsage();

    /**
     * Returns the value of the '<em><b>User Data Variable Usage</b></em>' container reference. It
     * is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.usagemodel.UserData#getUserDataParameterUsages_UserData
     * <em>User Data Parameter Usages User Data</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>User Data Variable Usage</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>User Data Variable Usage</em>' container reference.
     * @see #setUserData_VariableUsage(UserData)
     * @see org.palladiosimulator.pcm.parameter.ParameterPackage#getVariableUsage_UserData_VariableUsage()
     * @see org.palladiosimulator.pcm.usagemodel.UserData#getUserDataParameterUsages_UserData
     * @model opposite="userDataParameterUsages_UserData" transient="false" ordered="false"
     * @generated
     */
    UserData getUserData_VariableUsage();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getUserData_VariableUsage
     * <em>User Data Variable Usage</em>}' container reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>User Data Variable Usage</em>' container reference.
     * @see #getUserData_VariableUsage()
     * @generated
     */
    void setUserData_VariableUsage(UserData value);

    /**
     * Returns the value of the '<em><b>Call Action Variable Usage</b></em>' container reference. It
     * is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.seff.CallAction#getInputVariableUsages__CallAction
     * <em>Input Variable Usages Call Action</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Call Action Variable Usage</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Call Action Variable Usage</em>' container reference.
     * @see #setCallAction__VariableUsage(CallAction)
     * @see org.palladiosimulator.pcm.parameter.ParameterPackage#getVariableUsage_CallAction__VariableUsage()
     * @see org.palladiosimulator.pcm.seff.CallAction#getInputVariableUsages__CallAction
     * @model opposite="inputVariableUsages__CallAction" transient="false" ordered="false"
     * @generated
     */
    CallAction getCallAction__VariableUsage();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getCallAction__VariableUsage
     * <em>Call Action Variable Usage</em>}' container reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Call Action Variable Usage</em>' container reference.
     * @see #getCallAction__VariableUsage()
     * @generated
     */
    void setCallAction__VariableUsage(CallAction value);

    /**
     * Returns the value of the '<em><b>Synchronisation Point Variable Usage</b></em>' container
     * reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.seff.SynchronisationPoint#getOutputParameterUsage_SynchronisationPoint
     * <em>Output Parameter Usage Synchronisation Point</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Synchronisation Point Variable Usage</em>' container reference
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Synchronisation Point Variable Usage</em>' container reference.
     * @see #setSynchronisationPoint_VariableUsage(SynchronisationPoint)
     * @see org.palladiosimulator.pcm.parameter.ParameterPackage#getVariableUsage_SynchronisationPoint_VariableUsage()
     * @see org.palladiosimulator.pcm.seff.SynchronisationPoint#getOutputParameterUsage_SynchronisationPoint
     * @model opposite="outputParameterUsage_SynchronisationPoint" transient="false" ordered="false"
     * @generated
     */
    SynchronisationPoint getSynchronisationPoint_VariableUsage();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getSynchronisationPoint_VariableUsage
     * <em>Synchronisation Point Variable Usage</em>}' container reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Synchronisation Point Variable Usage</em>' container
     *            reference.
     * @see #getSynchronisationPoint_VariableUsage()
     * @generated
     */
    void setSynchronisationPoint_VariableUsage(SynchronisationPoint value);

    /**
     * Returns the value of the '<em><b>Call Return Action Variable Usage</b></em>' container
     * reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.seff.CallReturnAction#getReturnVariableUsage__CallReturnAction
     * <em>Return Variable Usage Call Return Action</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Call Return Action Variable Usage</em>' container reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Call Return Action Variable Usage</em>' container reference.
     * @see #setCallReturnAction__VariableUsage(CallReturnAction)
     * @see org.palladiosimulator.pcm.parameter.ParameterPackage#getVariableUsage_CallReturnAction__VariableUsage()
     * @see org.palladiosimulator.pcm.seff.CallReturnAction#getReturnVariableUsage__CallReturnAction
     * @model opposite="returnVariableUsage__CallReturnAction" transient="false" ordered="false"
     * @generated
     */
    CallReturnAction getCallReturnAction__VariableUsage();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getCallReturnAction__VariableUsage
     * <em>Call Return Action Variable Usage</em>}' container reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Call Return Action Variable Usage</em>' container
     *            reference.
     * @see #getCallReturnAction__VariableUsage()
     * @generated
     */
    void setCallReturnAction__VariableUsage(CallReturnAction value);

    /**
     * Returns the value of the '<em><b>Set Variable Action Variable Usage</b></em>' container
     * reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.seff.SetVariableAction#getLocalVariableUsages_SetVariableAction
     * <em>Local Variable Usages Set Variable Action</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Set Variable Action Variable Usage</em>' container reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Set Variable Action Variable Usage</em>' container reference.
     * @see #setSetVariableAction_VariableUsage(SetVariableAction)
     * @see org.palladiosimulator.pcm.parameter.ParameterPackage#getVariableUsage_SetVariableAction_VariableUsage()
     * @see org.palladiosimulator.pcm.seff.SetVariableAction#getLocalVariableUsages_SetVariableAction
     * @model opposite="localVariableUsages_SetVariableAction" transient="false" ordered="false"
     * @generated
     */
    SetVariableAction getSetVariableAction_VariableUsage();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getSetVariableAction_VariableUsage
     * <em>Set Variable Action Variable Usage</em>}' container reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Set Variable Action Variable Usage</em>' container
     *            reference.
     * @see #getSetVariableAction_VariableUsage()
     * @generated
     */
    void setSetVariableAction_VariableUsage(SetVariableAction value);

    /**
     * Returns the value of the '
     * <em><b>Specified Output Parameter Abstraction expected External Outputs Variable Usage</b></em>
     * ' container reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.qosannotations.SpecifiedOutputParameterAbstraction#getExpectedExternalOutputs_SpecifiedOutputParameterAbstraction
     * <em>Expected External Outputs Specified Output Parameter Abstraction</em>}'. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '
     * <em>Specified Output Parameter Abstraction expected External Outputs Variable Usage</em>'
     * container reference isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '
     *         <em>Specified Output Parameter Abstraction expected External Outputs Variable Usage</em>
     *         ' container reference.
     * @see #setSpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage(SpecifiedOutputParameterAbstraction)
     * @see org.palladiosimulator.pcm.parameter.ParameterPackage#getVariableUsage_SpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage()
     * @see org.palladiosimulator.pcm.qosannotations.SpecifiedOutputParameterAbstraction#getExpectedExternalOutputs_SpecifiedOutputParameterAbstraction
     * @model opposite="expectedExternalOutputs_SpecifiedOutputParameterAbstraction"
     *        transient="false"
     * @generated
     */
    SpecifiedOutputParameterAbstraction getSpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getSpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage
     * <em>Specified Output Parameter Abstraction expected External Outputs Variable Usage</em>}'
     * container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '
     *            <em>Specified Output Parameter Abstraction expected External Outputs Variable Usage</em>
     *            ' container reference.
     * @see #getSpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage()
     * @generated
     */
    void setSpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage(
            SpecifiedOutputParameterAbstraction value);

    /**
     * Returns the value of the '<em><b>Assembly Context Variable Usage</b></em>' container
     * reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext#getConfigParameterUsages__AssemblyContext
     * <em>Config Parameter Usages Assembly Context</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Assembly Context Variable Usage</em>' container reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Assembly Context Variable Usage</em>' container reference.
     * @see #setAssemblyContext__VariableUsage(AssemblyContext)
     * @see org.palladiosimulator.pcm.parameter.ParameterPackage#getVariableUsage_AssemblyContext__VariableUsage()
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext#getConfigParameterUsages__AssemblyContext
     * @model opposite="configParameterUsages__AssemblyContext" transient="false" ordered="false"
     * @generated
     */
    AssemblyContext getAssemblyContext__VariableUsage();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getAssemblyContext__VariableUsage
     * <em>Assembly Context Variable Usage</em>}' container reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Assembly Context Variable Usage</em>' container
     *            reference.
     * @see #getAssemblyContext__VariableUsage()
     * @generated
     */
    void setAssemblyContext__VariableUsage(AssemblyContext value);

    /**
     * Returns the value of the '<em><b>Entry Level System Call Input Parameter Usage</b></em>'
     * container reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall#getInputParameterUsages_EntryLevelSystemCall
     * <em>Input Parameter Usages Entry Level System Call</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Entry Level System Call Input Parameter Usage</em>' container
     * reference isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Entry Level System Call Input Parameter Usage</em>' container
     *         reference.
     * @see #setEntryLevelSystemCall_InputParameterUsage(EntryLevelSystemCall)
     * @see org.palladiosimulator.pcm.parameter.ParameterPackage#getVariableUsage_EntryLevelSystemCall_InputParameterUsage()
     * @see org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall#getInputParameterUsages_EntryLevelSystemCall
     * @model opposite="inputParameterUsages_EntryLevelSystemCall" transient="false" ordered="false"
     * @generated
     */
    EntryLevelSystemCall getEntryLevelSystemCall_InputParameterUsage();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getEntryLevelSystemCall_InputParameterUsage
     * <em>Entry Level System Call Input Parameter Usage</em>}' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Entry Level System Call Input Parameter Usage</em>'
     *            container reference.
     * @see #getEntryLevelSystemCall_InputParameterUsage()
     * @generated
     */
    void setEntryLevelSystemCall_InputParameterUsage(EntryLevelSystemCall value);

    /**
     * Returns the value of the '<em><b>Entry Level System Call Output Parameter Usage</b></em>'
     * container reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall#getOutputParameterUsages_EntryLevelSystemCall
     * <em>Output Parameter Usages Entry Level System Call</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Entry Level System Call Output Parameter Usage</em>' container
     * reference isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Entry Level System Call Output Parameter Usage</em>' container
     *         reference.
     * @see #setEntryLevelSystemCall_OutputParameterUsage(EntryLevelSystemCall)
     * @see org.palladiosimulator.pcm.parameter.ParameterPackage#getVariableUsage_EntryLevelSystemCall_OutputParameterUsage()
     * @see org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall#getOutputParameterUsages_EntryLevelSystemCall
     * @model opposite="outputParameterUsages_EntryLevelSystemCall" transient="false"
     *        ordered="false"
     * @generated
     */
    EntryLevelSystemCall getEntryLevelSystemCall_OutputParameterUsage();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getEntryLevelSystemCall_OutputParameterUsage
     * <em>Entry Level System Call Output Parameter Usage</em>}' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Entry Level System Call Output Parameter Usage</em>'
     *            container reference.
     * @see #getEntryLevelSystemCall_OutputParameterUsage()
     * @generated
     */
    void setEntryLevelSystemCall_OutputParameterUsage(EntryLevelSystemCall value);

    /**
     * Returns the value of the '<em><b>Named Reference Variable Usage</b></em>' containment
     * reference. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Named Reference Variable Usage</em>' containment reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Named Reference Variable Usage</em>' containment reference.
     * @see #setNamedReference__VariableUsage(AbstractNamedReference)
     * @see org.palladiosimulator.pcm.parameter.ParameterPackage#getVariableUsage_NamedReference__VariableUsage()
     * @model containment="true" required="true" ordered="false"
     * @generated
     */
    AbstractNamedReference getNamedReference__VariableUsage();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getNamedReference__VariableUsage
     * <em>Named Reference Variable Usage</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Named Reference Variable Usage</em>' containment
     *            reference.
     * @see #getNamedReference__VariableUsage()
     * @generated
     */
    void setNamedReference__VariableUsage(AbstractNamedReference value);

} // VariableUsage
