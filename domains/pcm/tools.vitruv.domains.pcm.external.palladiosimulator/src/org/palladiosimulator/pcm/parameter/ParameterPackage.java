/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.parameter;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import de.uka.ipd.sdq.stoex.StoexPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta
 * objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc --> <!-- begin-model-doc --> The parameter package allows to model data
 * dependent performance characteristics of software systems. It is mainly used to specify
 * performance dependencies on input and output parameters of single service calls. It can also be
 * used to describe dependencies on the state of components by the use of component parameters. The
 * latter describe stochastically a component state which does not change over time.
 *
 * Parameters are described by the use of variable usages which on the one side contain a
 * performance abstraction of the variable's value and on the other side the name of the variable
 * for refering to the variable. Characterisations available include Structure (information on the
 * data's internal structure like "sorted" or "unsorted" for an array), Number of Elements (size of
 * a collection), Value (the actuall variable value), Bytesize (the variable's memory footprint), or
 * type (the type of the variable in polymorphic cases).
 *
 * Example for variable usages may be a.NUMBER_OF_ELEMENTS = 10 (array "a" contains 10 elements),
 * tree.STRUCTURE = "balanced" (tree "tree" is a balanced tree), and so on. <!-- end-model-doc -->
 *
 * @see org.palladiosimulator.pcm.parameter.ParameterFactory
 * @model kind="package"
 * @generated
 */
public interface ParameterPackage extends EPackage {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String eNAME = "parameter";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String eNS_URI = "http://palladiosimulator.org/PalladioComponentModel/Parameter/5.1";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String eNS_PREFIX = "parameter";

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    ParameterPackage eINSTANCE = org.palladiosimulator.pcm.parameter.impl.ParameterPackageImpl.init();

    /**
     * The meta object id for the '{@link org.palladiosimulator.pcm.parameter.impl.VariableUsageImpl
     * <em>Variable Usage</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.parameter.impl.VariableUsageImpl
     * @see org.palladiosimulator.pcm.parameter.impl.ParameterPackageImpl#getVariableUsage()
     * @generated
     */
    int VARIABLE_USAGE = 0;

    /**
     * The feature id for the '<em><b>Variable Characterisation Variable Usage</b></em>' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int VARIABLE_USAGE__VARIABLE_CHARACTERISATION_VARIABLE_USAGE = 0;

    /**
     * The feature id for the '<em><b>User Data Variable Usage</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int VARIABLE_USAGE__USER_DATA_VARIABLE_USAGE = 1;

    /**
     * The feature id for the '<em><b>Call Action Variable Usage</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int VARIABLE_USAGE__CALL_ACTION_VARIABLE_USAGE = 2;

    /**
     * The feature id for the '<em><b>Synchronisation Point Variable Usage</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int VARIABLE_USAGE__SYNCHRONISATION_POINT_VARIABLE_USAGE = 3;

    /**
     * The feature id for the '<em><b>Call Return Action Variable Usage</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int VARIABLE_USAGE__CALL_RETURN_ACTION_VARIABLE_USAGE = 4;

    /**
     * The feature id for the '<em><b>Set Variable Action Variable Usage</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int VARIABLE_USAGE__SET_VARIABLE_ACTION_VARIABLE_USAGE = 5;

    /**
     * The feature id for the '
     * <em><b>Specified Output Parameter Abstraction expected External Outputs Variable Usage</b></em>
     * ' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int VARIABLE_USAGE__SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION_EXPECTED_EXTERNAL_OUTPUTS_VARIABLE_USAGE = 6;

    /**
     * The feature id for the '<em><b>Assembly Context Variable Usage</b></em>' container reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int VARIABLE_USAGE__ASSEMBLY_CONTEXT_VARIABLE_USAGE = 7;

    /**
     * The feature id for the '<em><b>Entry Level System Call Input Parameter Usage</b></em>'
     * container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_INPUT_PARAMETER_USAGE = 8;

    /**
     * The feature id for the '<em><b>Entry Level System Call Output Parameter Usage</b></em>'
     * container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_OUTPUT_PARAMETER_USAGE = 9;

    /**
     * The feature id for the '<em><b>Named Reference Variable Usage</b></em>' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int VARIABLE_USAGE__NAMED_REFERENCE_VARIABLE_USAGE = 10;

    /**
     * The number of structural features of the '<em>Variable Usage</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int VARIABLE_USAGE_FEATURE_COUNT = 11;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.parameter.impl.VariableCharacterisationImpl
     * <em>Variable Characterisation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.parameter.impl.VariableCharacterisationImpl
     * @see org.palladiosimulator.pcm.parameter.impl.ParameterPackageImpl#getVariableCharacterisation()
     * @generated
     */
    int VARIABLE_CHARACTERISATION = 1;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int VARIABLE_CHARACTERISATION__TYPE = 0;

    /**
     * The feature id for the '<em><b>Specification Variable Characterisation</b></em>' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int VARIABLE_CHARACTERISATION__SPECIFICATION_VARIABLE_CHARACTERISATION = 1;

    /**
     * The feature id for the '<em><b>Variable Usage Variable Characterisation</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int VARIABLE_CHARACTERISATION__VARIABLE_USAGE_VARIABLE_CHARACTERISATION = 2;

    /**
     * The number of structural features of the '<em>Variable Characterisation</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int VARIABLE_CHARACTERISATION_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.parameter.impl.CharacterisedVariableImpl
     * <em>Characterised Variable</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.parameter.impl.CharacterisedVariableImpl
     * @see org.palladiosimulator.pcm.parameter.impl.ParameterPackageImpl#getCharacterisedVariable()
     * @generated
     */
    int CHARACTERISED_VARIABLE = 2;

    /**
     * The feature id for the '<em><b>Id Variable</b></em>' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHARACTERISED_VARIABLE__ID_VARIABLE = StoexPackage.VARIABLE__ID_VARIABLE;

    /**
     * The feature id for the '<em><b>Characterisation Type</b></em>' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int CHARACTERISED_VARIABLE__CHARACTERISATION_TYPE = StoexPackage.VARIABLE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Characterised Variable</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHARACTERISED_VARIABLE_FEATURE_COUNT = StoexPackage.VARIABLE_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.parameter.VariableCharacterisationType
     * <em>Variable Characterisation Type</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.parameter.VariableCharacterisationType
     * @see org.palladiosimulator.pcm.parameter.impl.ParameterPackageImpl#getVariableCharacterisationType()
     * @generated
     */
    int VARIABLE_CHARACTERISATION_TYPE = 3;

    /**
     * Returns the meta object for class '{@link org.palladiosimulator.pcm.parameter.VariableUsage
     * <em>Variable Usage</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Variable Usage</em>'.
     * @see org.palladiosimulator.pcm.parameter.VariableUsage
     * @generated
     */
    EClass getVariableUsage();

    /**
     * Returns the meta object for the containment reference list '
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getVariableCharacterisation_VariableUsage
     * <em>Variable Characterisation Variable Usage</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the meta object for the containment reference list '
     *         <em>Variable Characterisation Variable Usage</em>'.
     * @see org.palladiosimulator.pcm.parameter.VariableUsage#getVariableCharacterisation_VariableUsage()
     * @see #getVariableUsage()
     * @generated
     */
    EReference getVariableUsage_VariableCharacterisation_VariableUsage();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getUserData_VariableUsage
     * <em>User Data Variable Usage</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the container reference '<em>User Data Variable Usage</em>'.
     * @see org.palladiosimulator.pcm.parameter.VariableUsage#getUserData_VariableUsage()
     * @see #getVariableUsage()
     * @generated
     */
    EReference getVariableUsage_UserData_VariableUsage();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getCallAction__VariableUsage
     * <em>Call Action Variable Usage</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the container reference '<em>Call Action Variable Usage</em>'.
     * @see org.palladiosimulator.pcm.parameter.VariableUsage#getCallAction__VariableUsage()
     * @see #getVariableUsage()
     * @generated
     */
    EReference getVariableUsage_CallAction__VariableUsage();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getSynchronisationPoint_VariableUsage
     * <em>Synchronisation Point Variable Usage</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the meta object for the container reference '
     *         <em>Synchronisation Point Variable Usage</em>'.
     * @see org.palladiosimulator.pcm.parameter.VariableUsage#getSynchronisationPoint_VariableUsage()
     * @see #getVariableUsage()
     * @generated
     */
    EReference getVariableUsage_SynchronisationPoint_VariableUsage();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getCallReturnAction__VariableUsage
     * <em>Call Return Action Variable Usage</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the container reference '
     *         <em>Call Return Action Variable Usage</em>'.
     * @see org.palladiosimulator.pcm.parameter.VariableUsage#getCallReturnAction__VariableUsage()
     * @see #getVariableUsage()
     * @generated
     */
    EReference getVariableUsage_CallReturnAction__VariableUsage();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getSetVariableAction_VariableUsage
     * <em>Set Variable Action Variable Usage</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the container reference '
     *         <em>Set Variable Action Variable Usage</em>'.
     * @see org.palladiosimulator.pcm.parameter.VariableUsage#getSetVariableAction_VariableUsage()
     * @see #getVariableUsage()
     * @generated
     */
    EReference getVariableUsage_SetVariableAction_VariableUsage();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getSpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage
     * <em>Specified Output Parameter Abstraction expected External Outputs Variable Usage</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the container reference '
     *         <em>Specified Output Parameter Abstraction expected External Outputs Variable Usage</em>
     *         '.
     * @see org.palladiosimulator.pcm.parameter.VariableUsage#getSpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage()
     * @see #getVariableUsage()
     * @generated
     */
    EReference getVariableUsage_SpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getAssemblyContext__VariableUsage
     * <em>Assembly Context Variable Usage</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the container reference '<em>Assembly Context Variable Usage</em>
     *         '.
     * @see org.palladiosimulator.pcm.parameter.VariableUsage#getAssemblyContext__VariableUsage()
     * @see #getVariableUsage()
     * @generated
     */
    EReference getVariableUsage_AssemblyContext__VariableUsage();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getEntryLevelSystemCall_InputParameterUsage
     * <em>Entry Level System Call Input Parameter Usage</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the meta object for the container reference '
     *         <em>Entry Level System Call Input Parameter Usage</em>'.
     * @see org.palladiosimulator.pcm.parameter.VariableUsage#getEntryLevelSystemCall_InputParameterUsage()
     * @see #getVariableUsage()
     * @generated
     */
    EReference getVariableUsage_EntryLevelSystemCall_InputParameterUsage();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getEntryLevelSystemCall_OutputParameterUsage
     * <em>Entry Level System Call Output Parameter Usage</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the meta object for the container reference '
     *         <em>Entry Level System Call Output Parameter Usage</em>'.
     * @see org.palladiosimulator.pcm.parameter.VariableUsage#getEntryLevelSystemCall_OutputParameterUsage()
     * @see #getVariableUsage()
     * @generated
     */
    EReference getVariableUsage_EntryLevelSystemCall_OutputParameterUsage();

    /**
     * Returns the meta object for the containment reference '
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getNamedReference__VariableUsage
     * <em>Named Reference Variable Usage</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the containment reference '
     *         <em>Named Reference Variable Usage</em>'.
     * @see org.palladiosimulator.pcm.parameter.VariableUsage#getNamedReference__VariableUsage()
     * @see #getVariableUsage()
     * @generated
     */
    EReference getVariableUsage_NamedReference__VariableUsage();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.parameter.VariableCharacterisation
     * <em>Variable Characterisation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Variable Characterisation</em>'.
     * @see org.palladiosimulator.pcm.parameter.VariableCharacterisation
     * @generated
     */
    EClass getVariableCharacterisation();

    /**
     * Returns the meta object for the attribute '
     * {@link org.palladiosimulator.pcm.parameter.VariableCharacterisation#getType <em>Type</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see org.palladiosimulator.pcm.parameter.VariableCharacterisation#getType()
     * @see #getVariableCharacterisation()
     * @generated
     */
    EAttribute getVariableCharacterisation_Type();

    /**
     * Returns the meta object for the containment reference '
     * {@link org.palladiosimulator.pcm.parameter.VariableCharacterisation#getSpecification_VariableCharacterisation
     * <em>Specification Variable Characterisation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the meta object for the containment reference '
     *         <em>Specification Variable Characterisation</em>'.
     * @see org.palladiosimulator.pcm.parameter.VariableCharacterisation#getSpecification_VariableCharacterisation()
     * @see #getVariableCharacterisation()
     * @generated
     */
    EReference getVariableCharacterisation_Specification_VariableCharacterisation();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.parameter.VariableCharacterisation#getVariableUsage_VariableCharacterisation
     * <em>Variable Usage Variable Characterisation</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the meta object for the container reference '
     *         <em>Variable Usage Variable Characterisation</em>'.
     * @see org.palladiosimulator.pcm.parameter.VariableCharacterisation#getVariableUsage_VariableCharacterisation()
     * @see #getVariableCharacterisation()
     * @generated
     */
    EReference getVariableCharacterisation_VariableUsage_VariableCharacterisation();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.parameter.CharacterisedVariable
     * <em>Characterised Variable</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Characterised Variable</em>'.
     * @see org.palladiosimulator.pcm.parameter.CharacterisedVariable
     * @generated
     */
    EClass getCharacterisedVariable();

    /**
     * Returns the meta object for the attribute '
     * {@link org.palladiosimulator.pcm.parameter.CharacterisedVariable#getCharacterisationType
     * <em>Characterisation Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the attribute '<em>Characterisation Type</em>'.
     * @see org.palladiosimulator.pcm.parameter.CharacterisedVariable#getCharacterisationType()
     * @see #getCharacterisedVariable()
     * @generated
     */
    EAttribute getCharacterisedVariable_CharacterisationType();

    /**
     * Returns the meta object for enum '
     * {@link org.palladiosimulator.pcm.parameter.VariableCharacterisationType
     * <em>Variable Characterisation Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for enum '<em>Variable Characterisation Type</em>'.
     * @see org.palladiosimulator.pcm.parameter.VariableCharacterisationType
     * @generated
     */
    EEnum getVariableCharacterisationType();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the factory that creates the instances of the model.
     * @generated
     */
    ParameterFactory getParameterFactory();

    /**
     * <!-- begin-user-doc --> Defines literals for the meta objects that represent
     * <ul>
     * <li>each class,</li>
     * <li>each feature of each class,</li>
     * <li>each enum,</li>
     * <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    interface Literals {

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.parameter.impl.VariableUsageImpl <em>Variable Usage</em>
         * }' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.parameter.impl.VariableUsageImpl
         * @see org.palladiosimulator.pcm.parameter.impl.ParameterPackageImpl#getVariableUsage()
         * @generated
         */
        EClass VARIABLE_USAGE = eINSTANCE.getVariableUsage();

        /**
         * The meta object literal for the '<em><b>Variable Characterisation Variable Usage</b></em>
         * ' containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference VARIABLE_USAGE__VARIABLE_CHARACTERISATION_VARIABLE_USAGE = eINSTANCE
                .getVariableUsage_VariableCharacterisation_VariableUsage();

        /**
         * The meta object literal for the '<em><b>User Data Variable Usage</b></em>' container
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference VARIABLE_USAGE__USER_DATA_VARIABLE_USAGE = eINSTANCE.getVariableUsage_UserData_VariableUsage();

        /**
         * The meta object literal for the '<em><b>Call Action Variable Usage</b></em>' container
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference VARIABLE_USAGE__CALL_ACTION_VARIABLE_USAGE = eINSTANCE.getVariableUsage_CallAction__VariableUsage();

        /**
         * The meta object literal for the '<em><b>Synchronisation Point Variable Usage</b></em>'
         * container reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference VARIABLE_USAGE__SYNCHRONISATION_POINT_VARIABLE_USAGE = eINSTANCE
                .getVariableUsage_SynchronisationPoint_VariableUsage();

        /**
         * The meta object literal for the '<em><b>Call Return Action Variable Usage</b></em>'
         * container reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference VARIABLE_USAGE__CALL_RETURN_ACTION_VARIABLE_USAGE = eINSTANCE
                .getVariableUsage_CallReturnAction__VariableUsage();

        /**
         * The meta object literal for the '<em><b>Set Variable Action Variable Usage</b></em>'
         * container reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference VARIABLE_USAGE__SET_VARIABLE_ACTION_VARIABLE_USAGE = eINSTANCE
                .getVariableUsage_SetVariableAction_VariableUsage();

        /**
         * The meta object literal for the '
         * <em><b>Specified Output Parameter Abstraction expected External Outputs Variable Usage</b></em>
         * ' container reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference VARIABLE_USAGE__SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION_EXPECTED_EXTERNAL_OUTPUTS_VARIABLE_USAGE = eINSTANCE
                .getVariableUsage_SpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage();

        /**
         * The meta object literal for the '<em><b>Assembly Context Variable Usage</b></em>'
         * container reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference VARIABLE_USAGE__ASSEMBLY_CONTEXT_VARIABLE_USAGE = eINSTANCE
                .getVariableUsage_AssemblyContext__VariableUsage();

        /**
         * The meta object literal for the '
         * <em><b>Entry Level System Call Input Parameter Usage</b></em>' container reference
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_INPUT_PARAMETER_USAGE = eINSTANCE
                .getVariableUsage_EntryLevelSystemCall_InputParameterUsage();

        /**
         * The meta object literal for the '
         * <em><b>Entry Level System Call Output Parameter Usage</b></em>' container reference
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_OUTPUT_PARAMETER_USAGE = eINSTANCE
                .getVariableUsage_EntryLevelSystemCall_OutputParameterUsage();

        /**
         * The meta object literal for the '<em><b>Named Reference Variable Usage</b></em>'
         * containment reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference VARIABLE_USAGE__NAMED_REFERENCE_VARIABLE_USAGE = eINSTANCE
                .getVariableUsage_NamedReference__VariableUsage();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.parameter.impl.VariableCharacterisationImpl
         * <em>Variable Characterisation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.parameter.impl.VariableCharacterisationImpl
         * @see org.palladiosimulator.pcm.parameter.impl.ParameterPackageImpl#getVariableCharacterisation()
         * @generated
         */
        EClass VARIABLE_CHARACTERISATION = eINSTANCE.getVariableCharacterisation();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute VARIABLE_CHARACTERISATION__TYPE = eINSTANCE.getVariableCharacterisation_Type();

        /**
         * The meta object literal for the '<em><b>Specification Variable Characterisation</b></em>'
         * containment reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference VARIABLE_CHARACTERISATION__SPECIFICATION_VARIABLE_CHARACTERISATION = eINSTANCE
                .getVariableCharacterisation_Specification_VariableCharacterisation();

        /**
         * The meta object literal for the '<em><b>Variable Usage Variable Characterisation</b></em>
         * ' container reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference VARIABLE_CHARACTERISATION__VARIABLE_USAGE_VARIABLE_CHARACTERISATION = eINSTANCE
                .getVariableCharacterisation_VariableUsage_VariableCharacterisation();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.parameter.impl.CharacterisedVariableImpl
         * <em>Characterised Variable</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.parameter.impl.CharacterisedVariableImpl
         * @see org.palladiosimulator.pcm.parameter.impl.ParameterPackageImpl#getCharacterisedVariable()
         * @generated
         */
        EClass CHARACTERISED_VARIABLE = eINSTANCE.getCharacterisedVariable();

        /**
         * The meta object literal for the '<em><b>Characterisation Type</b></em>' attribute
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EAttribute CHARACTERISED_VARIABLE__CHARACTERISATION_TYPE = eINSTANCE
                .getCharacterisedVariable_CharacterisationType();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.parameter.VariableCharacterisationType
         * <em>Variable Characterisation Type</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc
         * -->
         *
         * @see org.palladiosimulator.pcm.parameter.VariableCharacterisationType
         * @see org.palladiosimulator.pcm.parameter.impl.ParameterPackageImpl#getVariableCharacterisationType()
         * @generated
         */
        EEnum VARIABLE_CHARACTERISATION_TYPE = eINSTANCE.getVariableCharacterisationType();

    }

} // ParameterPackage
