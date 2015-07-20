/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.usagemodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.palladiosimulator.pcm.core.entity.EntityPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta
 * objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc --> <!-- begin-model-doc -->
 * TODO:&nbsp;Put&nbsp;the&nbsp;3.2.2&nbsp;Context&nbsp;Model&nbsp;chapter&nbsp;of&nbsp;Steffens&
 * nbsp;Diss&nbsp;somewhere&nbsp;so&nbsp;that&nbsp;it&nbsp;can&nbsp;be&nbsp;referred&nbsp;to&nbsp;
 * here.&nbsp;It&nbsp;provides&nbsp;an&nbsp;important&nbsp;overview&nbsp;on&nbsp;why&nbsp;we&nbsp;
 * have&nbsp;the&nbsp;usage&nbsp;model.&nbsp;<br />
 * <br />
 * TODO:&nbsp;Include&nbsp;the&nbsp;example&nbsp;from&nbsp;Heiko's&nbsp;dissertation&nbsp;here?&nbsp
 * ;But&nbsp;how&nbsp;to&nbsp;include&nbsp;the&nbsp;picture?<br />
 * <br />
 * The&nbsp;usage&nbsp;of&nbsp;a&nbsp;software&nbsp;system&nbsp;by&nbsp;external&nbsp;clients&nbsp;
 * has&nbsp;to&nbsp;be&nbsp;captured&nbsp;in&nbsp;models&nbsp;to&nbsp;enable&nbsp;model-driven<br />
 * performance&nbsp;predictions.&nbsp;Here,&nbsp;the&nbsp;term&nbsp;usage&nbsp;refers&nbsp;to&nbsp;
 * workload&nbsp;(i.e.,&nbsp;the&nbsp;number&nbsp;of&nbsp;users&nbsp;concurrently<br />
 * present&nbsp;in&nbsp;the&nbsp;system),&nbsp;usage&nbsp;scenarios&nbsp;(i.e.,&nbsp;possible&nbsp;
 * sequences&nbsp;of&nbsp;invoking&nbsp;services&nbsp;at&nbsp;system&nbsp;provided<br />
 * roles),&nbsp;waiting&nbsp;delays&nbsp;between&nbsp;service&nbsp;invocations,&nbsp;and&nbsp;values
 * &nbsp;for&nbsp;parameters&nbsp;and&nbsp;component&nbsp;configurations.<br />
 * <br />
 * This&nbsp;package&nbsp;contains&nbsp;the&nbsp;usage&nbsp;specification&nbsp;language,&nbsp;which&
 * nbsp;(i)&nbsp;provides&nbsp;more&nbsp;expressiveness&nbsp;for<br />
 * characterising&nbsp;parameter&nbsp;instances&nbsp;than&nbsp;previous&nbsp;models,&nbsp;but&nbsp;(
 * ii)&nbsp;at&nbsp;the&nbsp;same&nbsp;time&nbsp;is&nbsp;restricted&nbsp;to&nbsp;concepts<br />
 * familiar&nbsp;to&nbsp;domain&nbsp;experts&nbsp;to&nbsp;create&nbsp;a&nbsp;domain&nbsp;specific&
 * nbsp;language.&nbsp;The&nbsp;language&nbsp;is&nbsp;called&nbsp;PCM&nbsp;usage<br />
 * model.<br />
 * <br />
 * The&nbsp;UsageModel&nbsp;specifies&nbsp;the&nbsp;whole&nbsp;user&nbsp;interaction&nbsp;with&nbsp;
 * a&nbsp;system&nbsp;from&nbsp;a&nbsp;performance&nbsp;viewpoint.&nbsp;It&nbsp;consists&nbsp;of&
 * nbsp;a&nbsp;number&nbsp;of&nbsp;concurrently&nbsp;executed&nbsp;UsageScenarios&nbsp;and&nbsp;a&
 * nbsp;set&nbsp;of&nbsp;global&nbsp;UserData&nbsp;specifications.&nbsp;Each&nbsp;UsageScenario&nbsp
 * ;includes&nbsp;a&nbsp;workload&nbsp;and&nbsp;a&nbsp;scenario&nbsp;behaviour.&nbsp;The&nbsp;
 * concepts&nbsp;are&nbsp;explained&nbsp;for&nbsp;the&nbsp;single&nbsp;meta&nbsp;classes&nbsp;
 * included&nbsp;in&nbsp;this&nbsp;package.&nbsp;<br />
 * <br />
 * Note&nbsp;that&nbsp;UsageModels&nbsp;are&nbsp;completely&nbsp;decoupled&nbsp;from&nbsp;the&nbsp;
 * inner&nbsp;contents&nbsp;of&nbsp;a&nbsp;system,&nbsp;which&nbsp;consists&nbsp;of&nbsp;an&nbsp;
 * assembly&nbsp;and&nbsp;a&nbsp;connected&nbsp;resource&nbsp;environment.&nbsp;The&nbsp;UsageModel&
 * nbsp;only&nbsp;refers&nbsp;to&nbsp;services&nbsp;of&nbsp;system&nbsp;provided&nbsp;roles.&nbsp;It
 * &nbsp;regards&nbsp;the&nbsp;component<br />
 * architecture&nbsp;(i.e.,&nbsp;the&nbsp;assembly)&nbsp;as&nbsp;well&nbsp;as&nbsp;used&nbsp;
 * resources&nbsp;(i.e.,&nbsp;hardware&nbsp;devices&nbsp;such&nbsp;as&nbsp;CPUs<br />
 * and&nbsp;harddisks&nbsp;or&nbsp;software&nbsp;entities&nbsp;such&nbsp;as&nbsp;threads,&nbsp;
 * semaphores)&nbsp;as&nbsp;hidden&nbsp;in&nbsp;the&nbsp;system.&nbsp;Thus,&nbsp;the&nbsp;UsageModel
 * &nbsp;only&nbsp;captures&nbsp;information&nbsp;that&nbsp;is&nbsp;available&nbsp;to&nbsp;domain&
 * nbsp;experts&nbsp;and&nbsp;can&nbsp;be&nbsp;changed&nbsp;by&nbsp;them.&nbsp;Resource<br />
 * environment&nbsp;and&nbsp;component&nbsp;architecture&nbsp;may&nbsp;be&nbsp;changed&nbsp;
 * independently&nbsp;from&nbsp;the&nbsp;UsageModel&nbsp;<br />
 * by&nbsp;system&nbsp;architects,&nbsp;if&nbsp;the&nbsp;system's&nbsp;ProvidedRoles&nbsp;remain&
 * nbsp;unchanged.<br />
 * <br />
 * Discussion:&nbsp;<br />
 * Notice,&nbsp;that&nbsp;unlike&nbsp;other&nbsp;behavioural&nbsp;description&nbsp;languages&nbsp;
 * for&nbsp;performance&nbsp;prediction&nbsp;(e.g.,&nbsp;[162,&nbsp;187,<br />
 * 78]),&nbsp;the&nbsp;PCM&nbsp;usage&nbsp;model&nbsp;specifically&nbsp;models&nbsp;user&nbsp;
 * behaviour&nbsp;and&nbsp;for&nbsp;example&nbsp;does&nbsp;not&nbsp;refer&nbsp;to&nbsp;resources.
 * <br />
 * Other&nbsp;performance&nbsp;meta-models&nbsp;mix&nbsp;up&nbsp;the&nbsp;specification&nbsp;of&nbsp
 * ;user&nbsp;behaviour,&nbsp;component&nbsp;behaviour,&nbsp;and<br />
 * resources,&nbsp;so&nbsp;that&nbsp;a&nbsp;single&nbsp;developer&nbsp;role&nbsp;(i.e.,&nbsp;a&nbsp;
 * performance&nbsp;analyst)&nbsp;needs&nbsp;to&nbsp;specify&nbsp;the&nbsp;performance<br />
 * model.&nbsp;Opposed&nbsp;to&nbsp;this,&nbsp;the&nbsp;PCM&nbsp;targets&nbsp;a&nbsp;division&nbsp;
 * of&nbsp;work&nbsp;for&nbsp;multiple&nbsp;developer&nbsp;roles&nbsp;(cf.&nbsp;Section&nbsp;3.1&
 * nbsp;of&nbsp;Heiko&nbsp;Koziolek's&nbsp;dissertation).<br />
 * <br />
 * Furthermore,&nbsp;none&nbsp;of&nbsp;the&nbsp;other&nbsp;performance&nbsp;meta-models&nbsp;support
 * &nbsp;explicit&nbsp;service&nbsp;parameter&nbsp;modelling.<br />
 * While&nbsp;CSM&nbsp;[162]&nbsp;includes&nbsp;a&nbsp;meta-class&nbsp;Message&nbsp;to&nbsp;specify&
 * nbsp;the&nbsp;amount&nbsp;of&nbsp;data&nbsp;transferred&nbsp;between<br />
 * two&nbsp;steps&nbsp;in&nbsp;the&nbsp;performance&nbsp;model,&nbsp;and&nbsp;KLAPER&nbsp;[78]&nbsp;
 * allows&nbsp;the&nbsp;specification&nbsp;of&nbsp;parameter&nbsp;values<br />
 * in&nbsp;principle,&nbsp;none&nbsp;of&nbsp;these&nbsp;language&nbsp;uses&nbsp;the&nbsp;information
 * &nbsp;to&nbsp;parameterise&nbsp;resource&nbsp;demands&nbsp;or&nbsp;component<br />
 * behaviour.&nbsp;Additionally,&nbsp;they&nbsp;do&nbsp;not&nbsp;provide&nbsp;the&nbsp;information&
 * nbsp;readily&nbsp;analysable&nbsp;by&nbsp;MDSD&nbsp;tools.<br />
 * <br />
 * The&nbsp;PCM&nbsp;usage&nbsp;model&nbsp;has&nbsp;been&nbsp;designed&nbsp;based&nbsp;on&nbsp;meta&
 * nbsp;models&nbsp;such&nbsp;as&nbsp;the&nbsp;performance&nbsp;domain&nbsp;model<br />
 * of&nbsp;the&nbsp;UML&nbsp;SPT&nbsp;profile&nbsp;[31],&nbsp;the&nbsp;Core&nbsp;Scenario&nbsp;Model
 * &nbsp;(CSM)&nbsp;[162],&nbsp;and&nbsp;KLAPER&nbsp;[78].&nbsp;It&nbsp;is&nbsp;furthermore<br />
 * related&nbsp;to&nbsp;usage&nbsp;models&nbsp;used&nbsp;in&nbsp;statistical&nbsp;testing&nbsp;[34].
 * &nbsp;Although&nbsp;the&nbsp;concepts&nbsp;included&nbsp;in&nbsp;the&nbsp;PCM&nbsp;usage<br />
 * model&nbsp;are&nbsp;quite&nbsp;similar&nbsp;to&nbsp;the&nbsp;modelling&nbsp;capabilities&nbsp;of&
 * nbsp;the&nbsp;UML&nbsp;SPT&nbsp;profile,&nbsp;there&nbsp;are&nbsp;some&nbsp;subtle<br />
 * differences:<br />
 * -
 * The&nbsp;usage&nbsp;model&nbsp;is&nbsp;aligned&nbsp;with&nbsp;the&nbsp;role&nbsp;of&nbsp;the&nbsp
 * ;domain&nbsp;expert,&nbsp;and&nbsp;uses&nbsp;only&nbsp;concepts&nbsp;known&nbsp;to<br />
 * this&nbsp;role.&nbsp;It&nbsp;is&nbsp;a&nbsp;domain&nbsp;specific&nbsp;language,&nbsp;whereas&nbsp
 * ;the&nbsp;UML&nbsp;SPT&nbsp;performance&nbsp;domain&nbsp;model&nbsp;is<br />
 * a&nbsp;general&nbsp;purpose&nbsp;language&nbsp;that&nbsp;includes&nbsp;information,&nbsp;which&
 * nbsp;is&nbsp;usually&nbsp;spread&nbsp;over&nbsp;multiple&nbsp;developer<br />
 * roles&nbsp;such&nbsp;as&nbsp;the&nbsp;component&nbsp;assembler&nbsp;and&nbsp;the&nbsp;system&nbsp
 * ;deployer,&nbsp;so&nbsp;that&nbsp;a&nbsp;domain&nbsp;expert&nbsp;without&nbsp;a&nbsp;technical&
 * nbsp;background&nbsp;could&nbsp;not&nbsp;specify&nbsp;an&nbsp;instance&nbsp;of&nbsp;it.&nbsp;
 * Nevertheless,&nbsp;domain&nbsp;experts<br />
 * should&nbsp;be&nbsp;able&nbsp;to&nbsp;create&nbsp;PCM&nbsp;usage&nbsp;models&nbsp;with&nbsp;
 * appropriate&nbsp;tools&nbsp;independently&nbsp;from&nbsp;other&nbsp;developer<br />
 * roles,&nbsp;because&nbsp;such&nbsp;models&nbsp;only&nbsp;contain&nbsp;concepts&nbsp;known&nbsp;to
 * &nbsp;them.<br />
 * -
 * The&nbsp;number&nbsp;of&nbsp;loop&nbsp;iterations&nbsp;is&nbsp;not&nbsp;bound&nbsp;to&nbsp;a&nbsp
 * ;constant&nbsp;value,&nbsp;but&nbsp;can&nbsp;be&nbsp;specified&nbsp;as&nbsp;a&nbsp;random<br />
 * variable.<br />
 * - The&nbsp;control&nbsp;flow&nbsp;constructs&nbsp;are&nbsp;arranged&nbsp;in&nbsp;a&nbsp;
 * hierarchical&nbsp;fashion&nbsp;to&nbsp;enable&nbsp;easy&nbsp;analyses.<br />
 * -
 * Users&nbsp;are&nbsp;restricted&nbsp;to&nbsp;non-concurrent&nbsp;behaviour,&nbsp;as&nbsp;it&nbsp;
 * is&nbsp;assumed,&nbsp;that&nbsp;users&nbsp;only&nbsp;execute&nbsp;the<br />
 * services&nbsp;of&nbsp;a&nbsp;system&nbsp;one&nbsp;at&nbsp;a&nbsp;time.<br />
 * - System&nbsp;service&nbsp;invocations&nbsp;can&nbsp;be&nbsp;enhanced&nbsp;with&nbsp;
 * characterisations&nbsp;of&nbsp;parameters&nbsp;values.<br />
 * <br />
 * [31]&nbsp;Object&nbsp;Management&nbsp;Group&nbsp;(OMG),&nbsp;
 * "UML&nbsp;Profile&nbsp;for&nbsp;Schedulability,&nbsp;Performance&nbsp;and&nbsp;Time,"<br />
 * http://www.omg.org/cgi-bin/doc?formal/2005-01-02,&nbsp;January&nbsp;2005.<br />
 * <br />
 * [34]&nbsp;James&nbsp;A.&nbsp;Whittaker&nbsp;and&nbsp;Michael&nbsp;G.&nbsp;Thomason,&nbsp;"A&nbsp;
 * Markov&nbsp;chain&nbsp;model&nbsp;for&nbsp;statistical&nbsp;software<br />
 * testing,"&nbsp;IEEE&nbsp;Transactions&nbsp;on&nbsp;Software&nbsp;Engineering,&nbsp;vol.&nbsp;20,&
 * nbsp;no.&nbsp;10,&nbsp;pp.&nbsp;812-824,&nbsp;Oct.&nbsp;1994.<br />
 * <br />
 * [78]&nbsp;V.&nbsp;Grassi,&nbsp;R.&nbsp;Mirandola,&nbsp;and&nbsp;A.&nbsp;Sabetta,&nbsp;"From&nbsp;
 * design&nbsp;to&nbsp;analysis&nbsp;models:&nbsp;a&nbsp;kernel&nbsp;language<br />
 * for&nbsp;performance&nbsp;and&nbsp;reliability&nbsp;analysis&nbsp;of&nbsp;component-based&nbsp;
 * systems,"&nbsp;in&nbsp;Proc.&nbsp;5th&nbsp;International<br />
 * Workshop&nbsp;on&nbsp;Software&nbsp;and&nbsp;Performance&nbsp;(WOSP&nbsp;'05).&nbsp;New&nbsp;York
 * ,&nbsp;NY,&nbsp;USA:&nbsp;ACM&nbsp;Press,&nbsp;2005,<br />
 * pp.&nbsp;25-36.<br />
 * <br />
 * [162]&nbsp;D.&nbsp;B.&nbsp;Petriu&nbsp;and&nbsp;M.&nbsp;Woodside,&nbsp;
 * "A&nbsp;metamodel&nbsp;for&nbsp;generating&nbsp;performance&nbsp;models&nbsp;from&nbsp;UML&nbsp;designs,"
 * <br />
 * in&nbsp;UML&nbsp;2004&nbsp;-&nbsp;The&nbsp;Unified&nbsp;Modeling&nbsp;Language.&nbsp;Model&nbsp;
 * Languages&nbsp;and&nbsp;Applications.&nbsp;7th<br />
 * International&nbsp;Conference,&nbsp;Lisbon,&nbsp;Portugal,&nbsp;October&nbsp;11-15,&nbsp;2004,&
 * nbsp;Proceedings,&nbsp;ser.&nbsp;LNCS,&nbsp;T.&nbsp;Baar,<br />
 * A.&nbsp;Strohmeier,&nbsp;A.&nbsp;Moreira,&nbsp;and&nbsp;S.&nbsp;J.&nbsp;Mellor,&nbsp;Eds.,&nbsp;
 * vol.&nbsp;3273.&nbsp;Springer,&nbsp;2004,&nbsp;pp.&nbsp;41-53.<br />
 * <br />
 * [187]&nbsp;C.&nbsp;U.&nbsp;Smith,&nbsp;C.&nbsp;M.&nbsp;Llado,&nbsp;V.&nbsp;Cortellessa,&nbsp;A.&
 * nbsp;D.&nbsp;Marco,&nbsp;and&nbsp;L.&nbsp;G.&nbsp;Williams,&nbsp;"From&nbsp;UML&nbsp;models<br />
 * to&nbsp;software&nbsp;performance&nbsp;results:&nbsp;an&nbsp;SPE&nbsp;process&nbsp;based&nbsp;on&
 * nbsp;XML&nbsp;interchange&nbsp;formats,"&nbsp;in&nbsp;Proc.&nbsp;5th<br />
 * international&nbsp;workshop&nbsp;on&nbsp;Software&nbsp;and&nbsp;performance&nbsp;(WOSP'05).&nbsp;
 * New&nbsp;York,&nbsp;NY,&nbsp;USA:&nbsp;ACM<br />
 * Press,&nbsp;2005,&nbsp;pp.&nbsp;87-98.<br />
 * <br />
 * <br />
 * <br />
 * <!-- end-model-doc -->
 *
 * @see org.palladiosimulator.pcm.usagemodel.UsagemodelFactory
 * @model kind="package" annotation=
 *        "http://www.eclipse.org/emf/2002/Ecore invocationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' settingDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' validationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot'"
 * @generated
 */
public interface UsagemodelPackage extends EPackage {

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
    String eNAME = "usagemodel";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String eNS_URI = "http://palladiosimulator.org/PalladioComponentModel/UsageModel/5.1";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String eNS_PREFIX = "usagemodel";

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    UsagemodelPackage eINSTANCE = org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl.init();

    /**
     * The meta object id for the '{@link org.palladiosimulator.pcm.usagemodel.impl.WorkloadImpl
     * <em>Workload</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.usagemodel.impl.WorkloadImpl
     * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getWorkload()
     * @generated
     */
    int WORKLOAD = 0;

    /**
     * The feature id for the '<em><b>Usage Scenario Workload</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int WORKLOAD__USAGE_SCENARIO_WORKLOAD = 0;

    /**
     * The number of structural features of the '<em>Workload</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int WORKLOAD_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.usagemodel.impl.UsageScenarioImpl <em>Usage Scenario</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.usagemodel.impl.UsageScenarioImpl
     * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getUsageScenario()
     * @generated
     */
    int USAGE_SCENARIO = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int USAGE_SCENARIO__ID = EntityPackage.ENTITY__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int USAGE_SCENARIO__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Usage Model Usage Scenario</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int USAGE_SCENARIO__USAGE_MODEL_USAGE_SCENARIO = EntityPackage.ENTITY_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Scenario Behaviour Usage Scenario</b></em>' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int USAGE_SCENARIO__SCENARIO_BEHAVIOUR_USAGE_SCENARIO = EntityPackage.ENTITY_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Workload Usage Scenario</b></em>' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int USAGE_SCENARIO__WORKLOAD_USAGE_SCENARIO = EntityPackage.ENTITY_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Usage Scenario</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int USAGE_SCENARIO_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link org.palladiosimulator.pcm.usagemodel.impl.UserDataImpl
     * <em>User Data</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.usagemodel.impl.UserDataImpl
     * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getUserData()
     * @generated
     */
    int USER_DATA = 2;

    /**
     * The feature id for the '<em><b>Assembly Context user Data</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int USER_DATA__ASSEMBLY_CONTEXT_USER_DATA = 0;

    /**
     * The feature id for the '<em><b>Usage Model User Data</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int USER_DATA__USAGE_MODEL_USER_DATA = 1;

    /**
     * The feature id for the '<em><b>User Data Parameter Usages User Data</b></em>' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int USER_DATA__USER_DATA_PARAMETER_USAGES_USER_DATA = 2;

    /**
     * The number of structural features of the '<em>User Data</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int USER_DATA_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link org.palladiosimulator.pcm.usagemodel.impl.UsageModelImpl
     * <em>Usage Model</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.usagemodel.impl.UsageModelImpl
     * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getUsageModel()
     * @generated
     */
    int USAGE_MODEL = 3;

    /**
     * The feature id for the '<em><b>Usage Scenario Usage Model</b></em>' containment reference
     * list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int USAGE_MODEL__USAGE_SCENARIO_USAGE_MODEL = 0;

    /**
     * The feature id for the '<em><b>User Data Usage Model</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int USAGE_MODEL__USER_DATA_USAGE_MODEL = 1;

    /**
     * The number of structural features of the '<em>Usage Model</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int USAGE_MODEL_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.usagemodel.impl.AbstractUserActionImpl
     * <em>Abstract User Action</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.usagemodel.impl.AbstractUserActionImpl
     * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getAbstractUserAction()
     * @generated
     */
    int ABSTRACT_USER_ACTION = 5;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ABSTRACT_USER_ACTION__ID = EntityPackage.ENTITY__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ABSTRACT_USER_ACTION__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Successor</b></em>' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ABSTRACT_USER_ACTION__SUCCESSOR = EntityPackage.ENTITY_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Predecessor</b></em>' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ABSTRACT_USER_ACTION__PREDECESSOR = EntityPackage.ENTITY_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Scenario Behaviour Abstract User Action</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ABSTRACT_USER_ACTION__SCENARIO_BEHAVIOUR_ABSTRACT_USER_ACTION = EntityPackage.ENTITY_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Abstract User Action</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ABSTRACT_USER_ACTION_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.usagemodel.impl.EntryLevelSystemCallImpl
     * <em>Entry Level System Call</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.usagemodel.impl.EntryLevelSystemCallImpl
     * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getEntryLevelSystemCall()
     * @generated
     */
    int ENTRY_LEVEL_SYSTEM_CALL = 4;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ENTRY_LEVEL_SYSTEM_CALL__ID = ABSTRACT_USER_ACTION__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ENTRY_LEVEL_SYSTEM_CALL__ENTITY_NAME = ABSTRACT_USER_ACTION__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Successor</b></em>' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ENTRY_LEVEL_SYSTEM_CALL__SUCCESSOR = ABSTRACT_USER_ACTION__SUCCESSOR;

    /**
     * The feature id for the '<em><b>Predecessor</b></em>' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ENTRY_LEVEL_SYSTEM_CALL__PREDECESSOR = ABSTRACT_USER_ACTION__PREDECESSOR;

    /**
     * The feature id for the '<em><b>Scenario Behaviour Abstract User Action</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ENTRY_LEVEL_SYSTEM_CALL__SCENARIO_BEHAVIOUR_ABSTRACT_USER_ACTION = ABSTRACT_USER_ACTION__SCENARIO_BEHAVIOUR_ABSTRACT_USER_ACTION;

    /**
     * The feature id for the '<em><b>Provided Role Entry Level System Call</b></em>' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ENTRY_LEVEL_SYSTEM_CALL__PROVIDED_ROLE_ENTRY_LEVEL_SYSTEM_CALL = ABSTRACT_USER_ACTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Operation Signature Entry Level System Call</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ENTRY_LEVEL_SYSTEM_CALL__OPERATION_SIGNATURE_ENTRY_LEVEL_SYSTEM_CALL = ABSTRACT_USER_ACTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Output Parameter Usages Entry Level System Call</b></em>'
     * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ENTRY_LEVEL_SYSTEM_CALL__OUTPUT_PARAMETER_USAGES_ENTRY_LEVEL_SYSTEM_CALL = ABSTRACT_USER_ACTION_FEATURE_COUNT
            + 2;

    /**
     * The feature id for the '<em><b>Priority</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ENTRY_LEVEL_SYSTEM_CALL__PRIORITY = ABSTRACT_USER_ACTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Input Parameter Usages Entry Level System Call</b></em>'
     * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ENTRY_LEVEL_SYSTEM_CALL__INPUT_PARAMETER_USAGES_ENTRY_LEVEL_SYSTEM_CALL = ABSTRACT_USER_ACTION_FEATURE_COUNT
            + 4;

    /**
     * The number of structural features of the '<em>Entry Level System Call</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ENTRY_LEVEL_SYSTEM_CALL_FEATURE_COUNT = ABSTRACT_USER_ACTION_FEATURE_COUNT + 5;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.usagemodel.impl.ScenarioBehaviourImpl
     * <em>Scenario Behaviour</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.usagemodel.impl.ScenarioBehaviourImpl
     * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getScenarioBehaviour()
     * @generated
     */
    int SCENARIO_BEHAVIOUR = 6;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int SCENARIO_BEHAVIOUR__ID = EntityPackage.ENTITY__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int SCENARIO_BEHAVIOUR__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Usage Scenario Senario Behaviour</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int SCENARIO_BEHAVIOUR__USAGE_SCENARIO_SENARIO_BEHAVIOUR = EntityPackage.ENTITY_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Branch Transition Scenario Behaviour</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int SCENARIO_BEHAVIOUR__BRANCH_TRANSITION_SCENARIO_BEHAVIOUR = EntityPackage.ENTITY_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Loop Scenario Behaviour</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SCENARIO_BEHAVIOUR__LOOP_SCENARIO_BEHAVIOUR = EntityPackage.ENTITY_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Actions Scenario Behaviour</b></em>' containment reference
     * list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int SCENARIO_BEHAVIOUR__ACTIONS_SCENARIO_BEHAVIOUR = EntityPackage.ENTITY_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Scenario Behaviour</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SCENARIO_BEHAVIOUR_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 4;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.usagemodel.impl.BranchTransitionImpl
     * <em>Branch Transition</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.usagemodel.impl.BranchTransitionImpl
     * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getBranchTransition()
     * @generated
     */
    int BRANCH_TRANSITION = 7;

    /**
     * The feature id for the '<em><b>Branch Probability</b></em>' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int BRANCH_TRANSITION__BRANCH_PROBABILITY = 0;

    /**
     * The feature id for the '<em><b>Branch Branch Transition</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BRANCH_TRANSITION__BRANCH_BRANCH_TRANSITION = 1;

    /**
     * The feature id for the '<em><b>Branched Behaviour Branch Transition</b></em>' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int BRANCH_TRANSITION__BRANCHED_BEHAVIOUR_BRANCH_TRANSITION = 2;

    /**
     * The number of structural features of the '<em>Branch Transition</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BRANCH_TRANSITION_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link org.palladiosimulator.pcm.usagemodel.impl.BranchImpl
     * <em>Branch</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.usagemodel.impl.BranchImpl
     * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getBranch()
     * @generated
     */
    int BRANCH = 8;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int BRANCH__ID = ABSTRACT_USER_ACTION__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int BRANCH__ENTITY_NAME = ABSTRACT_USER_ACTION__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Successor</b></em>' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int BRANCH__SUCCESSOR = ABSTRACT_USER_ACTION__SUCCESSOR;

    /**
     * The feature id for the '<em><b>Predecessor</b></em>' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int BRANCH__PREDECESSOR = ABSTRACT_USER_ACTION__PREDECESSOR;

    /**
     * The feature id for the '<em><b>Scenario Behaviour Abstract User Action</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int BRANCH__SCENARIO_BEHAVIOUR_ABSTRACT_USER_ACTION = ABSTRACT_USER_ACTION__SCENARIO_BEHAVIOUR_ABSTRACT_USER_ACTION;

    /**
     * The feature id for the '<em><b>Branch Transitions Branch</b></em>' containment reference
     * list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int BRANCH__BRANCH_TRANSITIONS_BRANCH = ABSTRACT_USER_ACTION_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Branch</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int BRANCH_FEATURE_COUNT = ABSTRACT_USER_ACTION_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link org.palladiosimulator.pcm.usagemodel.impl.LoopImpl
     * <em>Loop</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.usagemodel.impl.LoopImpl
     * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getLoop()
     * @generated
     */
    int LOOP = 9;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int LOOP__ID = ABSTRACT_USER_ACTION__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int LOOP__ENTITY_NAME = ABSTRACT_USER_ACTION__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Successor</b></em>' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int LOOP__SUCCESSOR = ABSTRACT_USER_ACTION__SUCCESSOR;

    /**
     * The feature id for the '<em><b>Predecessor</b></em>' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int LOOP__PREDECESSOR = ABSTRACT_USER_ACTION__PREDECESSOR;

    /**
     * The feature id for the '<em><b>Scenario Behaviour Abstract User Action</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int LOOP__SCENARIO_BEHAVIOUR_ABSTRACT_USER_ACTION = ABSTRACT_USER_ACTION__SCENARIO_BEHAVIOUR_ABSTRACT_USER_ACTION;

    /**
     * The feature id for the '<em><b>Loop Iteration Loop</b></em>' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LOOP__LOOP_ITERATION_LOOP = ABSTRACT_USER_ACTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Body Behaviour Loop</b></em>' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LOOP__BODY_BEHAVIOUR_LOOP = ABSTRACT_USER_ACTION_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Loop</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int LOOP_FEATURE_COUNT = ABSTRACT_USER_ACTION_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link org.palladiosimulator.pcm.usagemodel.impl.StopImpl
     * <em>Stop</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.usagemodel.impl.StopImpl
     * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getStop()
     * @generated
     */
    int STOP = 10;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int STOP__ID = ABSTRACT_USER_ACTION__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int STOP__ENTITY_NAME = ABSTRACT_USER_ACTION__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Successor</b></em>' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int STOP__SUCCESSOR = ABSTRACT_USER_ACTION__SUCCESSOR;

    /**
     * The feature id for the '<em><b>Predecessor</b></em>' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int STOP__PREDECESSOR = ABSTRACT_USER_ACTION__PREDECESSOR;

    /**
     * The feature id for the '<em><b>Scenario Behaviour Abstract User Action</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int STOP__SCENARIO_BEHAVIOUR_ABSTRACT_USER_ACTION = ABSTRACT_USER_ACTION__SCENARIO_BEHAVIOUR_ABSTRACT_USER_ACTION;

    /**
     * The number of structural features of the '<em>Stop</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int STOP_FEATURE_COUNT = ABSTRACT_USER_ACTION_FEATURE_COUNT + 0;

    /**
     * The meta object id for the '{@link org.palladiosimulator.pcm.usagemodel.impl.StartImpl
     * <em>Start</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.usagemodel.impl.StartImpl
     * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getStart()
     * @generated
     */
    int START = 11;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int START__ID = ABSTRACT_USER_ACTION__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int START__ENTITY_NAME = ABSTRACT_USER_ACTION__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Successor</b></em>' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int START__SUCCESSOR = ABSTRACT_USER_ACTION__SUCCESSOR;

    /**
     * The feature id for the '<em><b>Predecessor</b></em>' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int START__PREDECESSOR = ABSTRACT_USER_ACTION__PREDECESSOR;

    /**
     * The feature id for the '<em><b>Scenario Behaviour Abstract User Action</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int START__SCENARIO_BEHAVIOUR_ABSTRACT_USER_ACTION = ABSTRACT_USER_ACTION__SCENARIO_BEHAVIOUR_ABSTRACT_USER_ACTION;

    /**
     * The number of structural features of the '<em>Start</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int START_FEATURE_COUNT = ABSTRACT_USER_ACTION_FEATURE_COUNT + 0;

    /**
     * The meta object id for the '{@link org.palladiosimulator.pcm.usagemodel.impl.OpenWorkloadImpl
     * <em>Open Workload</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.usagemodel.impl.OpenWorkloadImpl
     * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getOpenWorkload()
     * @generated
     */
    int OPEN_WORKLOAD = 12;

    /**
     * The feature id for the '<em><b>Usage Scenario Workload</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OPEN_WORKLOAD__USAGE_SCENARIO_WORKLOAD = WORKLOAD__USAGE_SCENARIO_WORKLOAD;

    /**
     * The feature id for the '<em><b>Inter Arrival Time Open Workload</b></em>' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int OPEN_WORKLOAD__INTER_ARRIVAL_TIME_OPEN_WORKLOAD = WORKLOAD_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Open Workload</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int OPEN_WORKLOAD_FEATURE_COUNT = WORKLOAD_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link org.palladiosimulator.pcm.usagemodel.impl.DelayImpl
     * <em>Delay</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.usagemodel.impl.DelayImpl
     * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getDelay()
     * @generated
     */
    int DELAY = 13;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int DELAY__ID = ABSTRACT_USER_ACTION__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int DELAY__ENTITY_NAME = ABSTRACT_USER_ACTION__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Successor</b></em>' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int DELAY__SUCCESSOR = ABSTRACT_USER_ACTION__SUCCESSOR;

    /**
     * The feature id for the '<em><b>Predecessor</b></em>' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int DELAY__PREDECESSOR = ABSTRACT_USER_ACTION__PREDECESSOR;

    /**
     * The feature id for the '<em><b>Scenario Behaviour Abstract User Action</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int DELAY__SCENARIO_BEHAVIOUR_ABSTRACT_USER_ACTION = ABSTRACT_USER_ACTION__SCENARIO_BEHAVIOUR_ABSTRACT_USER_ACTION;

    /**
     * The feature id for the '<em><b>Time Specification Delay</b></em>' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELAY__TIME_SPECIFICATION_DELAY = ABSTRACT_USER_ACTION_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Delay</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int DELAY_FEATURE_COUNT = ABSTRACT_USER_ACTION_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.usagemodel.impl.ClosedWorkloadImpl <em>Closed Workload</em>}
     * ' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.usagemodel.impl.ClosedWorkloadImpl
     * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getClosedWorkload()
     * @generated
     */
    int CLOSED_WORKLOAD = 14;

    /**
     * The feature id for the '<em><b>Usage Scenario Workload</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CLOSED_WORKLOAD__USAGE_SCENARIO_WORKLOAD = WORKLOAD__USAGE_SCENARIO_WORKLOAD;

    /**
     * The feature id for the '<em><b>Population</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int CLOSED_WORKLOAD__POPULATION = WORKLOAD_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Think Time Closed Workload</b></em>' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int CLOSED_WORKLOAD__THINK_TIME_CLOSED_WORKLOAD = WORKLOAD_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Closed Workload</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CLOSED_WORKLOAD_FEATURE_COUNT = WORKLOAD_FEATURE_COUNT + 2;

    /**
     * Returns the meta object for class '{@link org.palladiosimulator.pcm.usagemodel.Workload
     * <em>Workload</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Workload</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.Workload
     * @generated
     */
    EClass getWorkload();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.usagemodel.Workload#getUsageScenario_Workload
     * <em>Usage Scenario Workload</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the container reference '<em>Usage Scenario Workload</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.Workload#getUsageScenario_Workload()
     * @see #getWorkload()
     * @generated
     */
    EReference getWorkload_UsageScenario_Workload();

    /**
     * Returns the meta object for class '{@link org.palladiosimulator.pcm.usagemodel.UsageScenario
     * <em>Usage Scenario</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Usage Scenario</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.UsageScenario
     * @generated
     */
    EClass getUsageScenario();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.usagemodel.UsageScenario#getUsageModel_UsageScenario
     * <em>Usage Model Usage Scenario</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the container reference '<em>Usage Model Usage Scenario</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.UsageScenario#getUsageModel_UsageScenario()
     * @see #getUsageScenario()
     * @generated
     */
    EReference getUsageScenario_UsageModel_UsageScenario();

    /**
     * Returns the meta object for the containment reference '
     * {@link org.palladiosimulator.pcm.usagemodel.UsageScenario#getScenarioBehaviour_UsageScenario
     * <em>Scenario Behaviour Usage Scenario</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the containment reference '
     *         <em>Scenario Behaviour Usage Scenario</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.UsageScenario#getScenarioBehaviour_UsageScenario()
     * @see #getUsageScenario()
     * @generated
     */
    EReference getUsageScenario_ScenarioBehaviour_UsageScenario();

    /**
     * Returns the meta object for the containment reference '
     * {@link org.palladiosimulator.pcm.usagemodel.UsageScenario#getWorkload_UsageScenario
     * <em>Workload Usage Scenario</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the containment reference '<em>Workload Usage Scenario</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.UsageScenario#getWorkload_UsageScenario()
     * @see #getUsageScenario()
     * @generated
     */
    EReference getUsageScenario_Workload_UsageScenario();

    /**
     * Returns the meta object for class '{@link org.palladiosimulator.pcm.usagemodel.UserData
     * <em>User Data</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>User Data</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.UserData
     * @generated
     */
    EClass getUserData();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.usagemodel.UserData#getAssemblyContext_userData
     * <em>Assembly Context user Data</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the reference '<em>Assembly Context user Data</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.UserData#getAssemblyContext_userData()
     * @see #getUserData()
     * @generated
     */
    EReference getUserData_AssemblyContext_userData();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.usagemodel.UserData#getUsageModel_UserData
     * <em>Usage Model User Data</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the container reference '<em>Usage Model User Data</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.UserData#getUsageModel_UserData()
     * @see #getUserData()
     * @generated
     */
    EReference getUserData_UsageModel_UserData();

    /**
     * Returns the meta object for the containment reference list '
     * {@link org.palladiosimulator.pcm.usagemodel.UserData#getUserDataParameterUsages_UserData
     * <em>User Data Parameter Usages User Data</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the meta object for the containment reference list '
     *         <em>User Data Parameter Usages User Data</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.UserData#getUserDataParameterUsages_UserData()
     * @see #getUserData()
     * @generated
     */
    EReference getUserData_UserDataParameterUsages_UserData();

    /**
     * Returns the meta object for class '{@link org.palladiosimulator.pcm.usagemodel.UsageModel
     * <em>Usage Model</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Usage Model</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.UsageModel
     * @generated
     */
    EClass getUsageModel();

    /**
     * Returns the meta object for the containment reference list '
     * {@link org.palladiosimulator.pcm.usagemodel.UsageModel#getUsageScenario_UsageModel
     * <em>Usage Scenario Usage Model</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the containment reference list '
     *         <em>Usage Scenario Usage Model</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.UsageModel#getUsageScenario_UsageModel()
     * @see #getUsageModel()
     * @generated
     */
    EReference getUsageModel_UsageScenario_UsageModel();

    /**
     * Returns the meta object for the containment reference list '
     * {@link org.palladiosimulator.pcm.usagemodel.UsageModel#getUserData_UsageModel
     * <em>User Data Usage Model</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the containment reference list '<em>User Data Usage Model</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.UsageModel#getUserData_UsageModel()
     * @see #getUsageModel()
     * @generated
     */
    EReference getUsageModel_UserData_UsageModel();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall
     * <em>Entry Level System Call</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Entry Level System Call</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall
     * @generated
     */
    EClass getEntryLevelSystemCall();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall#getProvidedRole_EntryLevelSystemCall
     * <em>Provided Role Entry Level System Call</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the meta object for the reference '<em>Provided Role Entry Level System Call</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall#getProvidedRole_EntryLevelSystemCall()
     * @see #getEntryLevelSystemCall()
     * @generated
     */
    EReference getEntryLevelSystemCall_ProvidedRole_EntryLevelSystemCall();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall#getOperationSignature__EntryLevelSystemCall
     * <em>Operation Signature Entry Level System Call</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the meta object for the reference '
     *         <em>Operation Signature Entry Level System Call</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall#getOperationSignature__EntryLevelSystemCall()
     * @see #getEntryLevelSystemCall()
     * @generated
     */
    EReference getEntryLevelSystemCall_OperationSignature__EntryLevelSystemCall();

    /**
     * Returns the meta object for the containment reference list '
     * {@link org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall#getOutputParameterUsages_EntryLevelSystemCall
     * <em>Output Parameter Usages Entry Level System Call</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the meta object for the containment reference list '
     *         <em>Output Parameter Usages Entry Level System Call</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall#getOutputParameterUsages_EntryLevelSystemCall()
     * @see #getEntryLevelSystemCall()
     * @generated
     */
    EReference getEntryLevelSystemCall_OutputParameterUsages_EntryLevelSystemCall();

    /**
     * Returns the meta object for the attribute '
     * {@link org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall#getPriority
     * <em>Priority</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the attribute '<em>Priority</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall#getPriority()
     * @see #getEntryLevelSystemCall()
     * @generated
     */
    EAttribute getEntryLevelSystemCall_Priority();

    /**
     * Returns the meta object for the containment reference list '
     * {@link org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall#getInputParameterUsages_EntryLevelSystemCall
     * <em>Input Parameter Usages Entry Level System Call</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the meta object for the containment reference list '
     *         <em>Input Parameter Usages Entry Level System Call</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall#getInputParameterUsages_EntryLevelSystemCall()
     * @see #getEntryLevelSystemCall()
     * @generated
     */
    EReference getEntryLevelSystemCall_InputParameterUsages_EntryLevelSystemCall();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.usagemodel.AbstractUserAction <em>Abstract User Action</em>}
     * '. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Abstract User Action</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.AbstractUserAction
     * @generated
     */
    EClass getAbstractUserAction();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.usagemodel.AbstractUserAction#getSuccessor
     * <em>Successor</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the reference '<em>Successor</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.AbstractUserAction#getSuccessor()
     * @see #getAbstractUserAction()
     * @generated
     */
    EReference getAbstractUserAction_Successor();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.usagemodel.AbstractUserAction#getPredecessor
     * <em>Predecessor</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the reference '<em>Predecessor</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.AbstractUserAction#getPredecessor()
     * @see #getAbstractUserAction()
     * @generated
     */
    EReference getAbstractUserAction_Predecessor();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.usagemodel.AbstractUserAction#getScenarioBehaviour_AbstractUserAction
     * <em>Scenario Behaviour Abstract User Action</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the meta object for the container reference '
     *         <em>Scenario Behaviour Abstract User Action</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.AbstractUserAction#getScenarioBehaviour_AbstractUserAction()
     * @see #getAbstractUserAction()
     * @generated
     */
    EReference getAbstractUserAction_ScenarioBehaviour_AbstractUserAction();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour <em>Scenario Behaviour</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Scenario Behaviour</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour
     * @generated
     */
    EClass getScenarioBehaviour();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour#getUsageScenario_SenarioBehaviour
     * <em>Usage Scenario Senario Behaviour</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the container reference '
     *         <em>Usage Scenario Senario Behaviour</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour#getUsageScenario_SenarioBehaviour()
     * @see #getScenarioBehaviour()
     * @generated
     */
    EReference getScenarioBehaviour_UsageScenario_SenarioBehaviour();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour#getBranchTransition_ScenarioBehaviour
     * <em>Branch Transition Scenario Behaviour</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the meta object for the container reference '
     *         <em>Branch Transition Scenario Behaviour</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour#getBranchTransition_ScenarioBehaviour()
     * @see #getScenarioBehaviour()
     * @generated
     */
    EReference getScenarioBehaviour_BranchTransition_ScenarioBehaviour();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour#getLoop_ScenarioBehaviour
     * <em>Loop Scenario Behaviour</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the container reference '<em>Loop Scenario Behaviour</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour#getLoop_ScenarioBehaviour()
     * @see #getScenarioBehaviour()
     * @generated
     */
    EReference getScenarioBehaviour_Loop_ScenarioBehaviour();

    /**
     * Returns the meta object for the containment reference list '
     * {@link org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour#getActions_ScenarioBehaviour
     * <em>Actions Scenario Behaviour</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the containment reference list '
     *         <em>Actions Scenario Behaviour</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour#getActions_ScenarioBehaviour()
     * @see #getScenarioBehaviour()
     * @generated
     */
    EReference getScenarioBehaviour_Actions_ScenarioBehaviour();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.usagemodel.BranchTransition <em>Branch Transition</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Branch Transition</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.BranchTransition
     * @generated
     */
    EClass getBranchTransition();

    /**
     * Returns the meta object for the attribute '
     * {@link org.palladiosimulator.pcm.usagemodel.BranchTransition#getBranchProbability
     * <em>Branch Probability</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the attribute '<em>Branch Probability</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.BranchTransition#getBranchProbability()
     * @see #getBranchTransition()
     * @generated
     */
    EAttribute getBranchTransition_BranchProbability();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.usagemodel.BranchTransition#getBranch_BranchTransition
     * <em>Branch Branch Transition</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the container reference '<em>Branch Branch Transition</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.BranchTransition#getBranch_BranchTransition()
     * @see #getBranchTransition()
     * @generated
     */
    EReference getBranchTransition_Branch_BranchTransition();

    /**
     * Returns the meta object for the containment reference '
     * {@link org.palladiosimulator.pcm.usagemodel.BranchTransition#getBranchedBehaviour_BranchTransition
     * <em>Branched Behaviour Branch Transition</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the meta object for the containment reference '
     *         <em>Branched Behaviour Branch Transition</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.BranchTransition#getBranchedBehaviour_BranchTransition()
     * @see #getBranchTransition()
     * @generated
     */
    EReference getBranchTransition_BranchedBehaviour_BranchTransition();

    /**
     * Returns the meta object for class '{@link org.palladiosimulator.pcm.usagemodel.Branch
     * <em>Branch</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Branch</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.Branch
     * @generated
     */
    EClass getBranch();

    /**
     * Returns the meta object for the containment reference list '
     * {@link org.palladiosimulator.pcm.usagemodel.Branch#getBranchTransitions_Branch
     * <em>Branch Transitions Branch</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the containment reference list '
     *         <em>Branch Transitions Branch</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.Branch#getBranchTransitions_Branch()
     * @see #getBranch()
     * @generated
     */
    EReference getBranch_BranchTransitions_Branch();

    /**
     * Returns the meta object for class '{@link org.palladiosimulator.pcm.usagemodel.Loop
     * <em>Loop</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Loop</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.Loop
     * @generated
     */
    EClass getLoop();

    /**
     * Returns the meta object for the containment reference '
     * {@link org.palladiosimulator.pcm.usagemodel.Loop#getLoopIteration_Loop
     * <em>Loop Iteration Loop</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the containment reference '<em>Loop Iteration Loop</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.Loop#getLoopIteration_Loop()
     * @see #getLoop()
     * @generated
     */
    EReference getLoop_LoopIteration_Loop();

    /**
     * Returns the meta object for the containment reference '
     * {@link org.palladiosimulator.pcm.usagemodel.Loop#getBodyBehaviour_Loop
     * <em>Body Behaviour Loop</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the containment reference '<em>Body Behaviour Loop</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.Loop#getBodyBehaviour_Loop()
     * @see #getLoop()
     * @generated
     */
    EReference getLoop_BodyBehaviour_Loop();

    /**
     * Returns the meta object for class '{@link org.palladiosimulator.pcm.usagemodel.Stop
     * <em>Stop</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Stop</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.Stop
     * @generated
     */
    EClass getStop();

    /**
     * Returns the meta object for class '{@link org.palladiosimulator.pcm.usagemodel.Start
     * <em>Start</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Start</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.Start
     * @generated
     */
    EClass getStart();

    /**
     * Returns the meta object for class '{@link org.palladiosimulator.pcm.usagemodel.OpenWorkload
     * <em>Open Workload</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Open Workload</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.OpenWorkload
     * @generated
     */
    EClass getOpenWorkload();

    /**
     * Returns the meta object for the containment reference '
     * {@link org.palladiosimulator.pcm.usagemodel.OpenWorkload#getInterArrivalTime_OpenWorkload
     * <em>Inter Arrival Time Open Workload</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the containment reference '
     *         <em>Inter Arrival Time Open Workload</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.OpenWorkload#getInterArrivalTime_OpenWorkload()
     * @see #getOpenWorkload()
     * @generated
     */
    EReference getOpenWorkload_InterArrivalTime_OpenWorkload();

    /**
     * Returns the meta object for class '{@link org.palladiosimulator.pcm.usagemodel.Delay
     * <em>Delay</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Delay</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.Delay
     * @generated
     */
    EClass getDelay();

    /**
     * Returns the meta object for the containment reference '
     * {@link org.palladiosimulator.pcm.usagemodel.Delay#getTimeSpecification_Delay
     * <em>Time Specification Delay</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the containment reference '<em>Time Specification Delay</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.Delay#getTimeSpecification_Delay()
     * @see #getDelay()
     * @generated
     */
    EReference getDelay_TimeSpecification_Delay();

    /**
     * Returns the meta object for class '{@link org.palladiosimulator.pcm.usagemodel.ClosedWorkload
     * <em>Closed Workload</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Closed Workload</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.ClosedWorkload
     * @generated
     */
    EClass getClosedWorkload();

    /**
     * Returns the meta object for the attribute '
     * {@link org.palladiosimulator.pcm.usagemodel.ClosedWorkload#getPopulation <em>Population</em>}
     * '. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the attribute '<em>Population</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.ClosedWorkload#getPopulation()
     * @see #getClosedWorkload()
     * @generated
     */
    EAttribute getClosedWorkload_Population();

    /**
     * Returns the meta object for the containment reference '
     * {@link org.palladiosimulator.pcm.usagemodel.ClosedWorkload#getThinkTime_ClosedWorkload
     * <em>Think Time Closed Workload</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the containment reference '<em>Think Time Closed Workload</em>'.
     * @see org.palladiosimulator.pcm.usagemodel.ClosedWorkload#getThinkTime_ClosedWorkload()
     * @see #getClosedWorkload()
     * @generated
     */
    EReference getClosedWorkload_ThinkTime_ClosedWorkload();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the factory that creates the instances of the model.
     * @generated
     */
    UsagemodelFactory getUsagemodelFactory();

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
         * {@link org.palladiosimulator.pcm.usagemodel.impl.WorkloadImpl <em>Workload</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.usagemodel.impl.WorkloadImpl
         * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getWorkload()
         * @generated
         */
        EClass WORKLOAD = eINSTANCE.getWorkload();

        /**
         * The meta object literal for the '<em><b>Usage Scenario Workload</b></em>' container
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference WORKLOAD__USAGE_SCENARIO_WORKLOAD = eINSTANCE.getWorkload_UsageScenario_Workload();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.usagemodel.impl.UsageScenarioImpl
         * <em>Usage Scenario</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.usagemodel.impl.UsageScenarioImpl
         * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getUsageScenario()
         * @generated
         */
        EClass USAGE_SCENARIO = eINSTANCE.getUsageScenario();

        /**
         * The meta object literal for the '<em><b>Usage Model Usage Scenario</b></em>' container
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference USAGE_SCENARIO__USAGE_MODEL_USAGE_SCENARIO = eINSTANCE.getUsageScenario_UsageModel_UsageScenario();

        /**
         * The meta object literal for the '<em><b>Scenario Behaviour Usage Scenario</b></em>'
         * containment reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference USAGE_SCENARIO__SCENARIO_BEHAVIOUR_USAGE_SCENARIO = eINSTANCE
                .getUsageScenario_ScenarioBehaviour_UsageScenario();

        /**
         * The meta object literal for the '<em><b>Workload Usage Scenario</b></em>' containment
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference USAGE_SCENARIO__WORKLOAD_USAGE_SCENARIO = eINSTANCE.getUsageScenario_Workload_UsageScenario();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.usagemodel.impl.UserDataImpl <em>User Data</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.usagemodel.impl.UserDataImpl
         * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getUserData()
         * @generated
         */
        EClass USER_DATA = eINSTANCE.getUserData();

        /**
         * The meta object literal for the '<em><b>Assembly Context user Data</b></em>' reference
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference USER_DATA__ASSEMBLY_CONTEXT_USER_DATA = eINSTANCE.getUserData_AssemblyContext_userData();

        /**
         * The meta object literal for the '<em><b>Usage Model User Data</b></em>' container
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference USER_DATA__USAGE_MODEL_USER_DATA = eINSTANCE.getUserData_UsageModel_UserData();

        /**
         * The meta object literal for the '<em><b>User Data Parameter Usages User Data</b></em>'
         * containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference USER_DATA__USER_DATA_PARAMETER_USAGES_USER_DATA = eINSTANCE
                .getUserData_UserDataParameterUsages_UserData();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.usagemodel.impl.UsageModelImpl <em>Usage Model</em>}'
         * class. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.usagemodel.impl.UsageModelImpl
         * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getUsageModel()
         * @generated
         */
        EClass USAGE_MODEL = eINSTANCE.getUsageModel();

        /**
         * The meta object literal for the '<em><b>Usage Scenario Usage Model</b></em>' containment
         * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference USAGE_MODEL__USAGE_SCENARIO_USAGE_MODEL = eINSTANCE.getUsageModel_UsageScenario_UsageModel();

        /**
         * The meta object literal for the '<em><b>User Data Usage Model</b></em>' containment
         * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference USAGE_MODEL__USER_DATA_USAGE_MODEL = eINSTANCE.getUsageModel_UserData_UsageModel();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.usagemodel.impl.EntryLevelSystemCallImpl
         * <em>Entry Level System Call</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.usagemodel.impl.EntryLevelSystemCallImpl
         * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getEntryLevelSystemCall()
         * @generated
         */
        EClass ENTRY_LEVEL_SYSTEM_CALL = eINSTANCE.getEntryLevelSystemCall();

        /**
         * The meta object literal for the '<em><b>Provided Role Entry Level System Call</b></em>'
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference ENTRY_LEVEL_SYSTEM_CALL__PROVIDED_ROLE_ENTRY_LEVEL_SYSTEM_CALL = eINSTANCE
                .getEntryLevelSystemCall_ProvidedRole_EntryLevelSystemCall();

        /**
         * The meta object literal for the '
         * <em><b>Operation Signature Entry Level System Call</b></em>' reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ENTRY_LEVEL_SYSTEM_CALL__OPERATION_SIGNATURE_ENTRY_LEVEL_SYSTEM_CALL = eINSTANCE
                .getEntryLevelSystemCall_OperationSignature__EntryLevelSystemCall();

        /**
         * The meta object literal for the '
         * <em><b>Output Parameter Usages Entry Level System Call</b></em>' containment reference
         * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference ENTRY_LEVEL_SYSTEM_CALL__OUTPUT_PARAMETER_USAGES_ENTRY_LEVEL_SYSTEM_CALL = eINSTANCE
                .getEntryLevelSystemCall_OutputParameterUsages_EntryLevelSystemCall();

        /**
         * The meta object literal for the '<em><b>Priority</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute ENTRY_LEVEL_SYSTEM_CALL__PRIORITY = eINSTANCE.getEntryLevelSystemCall_Priority();

        /**
         * The meta object literal for the '
         * <em><b>Input Parameter Usages Entry Level System Call</b></em>' containment reference
         * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference ENTRY_LEVEL_SYSTEM_CALL__INPUT_PARAMETER_USAGES_ENTRY_LEVEL_SYSTEM_CALL = eINSTANCE
                .getEntryLevelSystemCall_InputParameterUsages_EntryLevelSystemCall();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.usagemodel.impl.AbstractUserActionImpl
         * <em>Abstract User Action</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.usagemodel.impl.AbstractUserActionImpl
         * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getAbstractUserAction()
         * @generated
         */
        EClass ABSTRACT_USER_ACTION = eINSTANCE.getAbstractUserAction();

        /**
         * The meta object literal for the '<em><b>Successor</b></em>' reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ABSTRACT_USER_ACTION__SUCCESSOR = eINSTANCE.getAbstractUserAction_Successor();

        /**
         * The meta object literal for the '<em><b>Predecessor</b></em>' reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ABSTRACT_USER_ACTION__PREDECESSOR = eINSTANCE.getAbstractUserAction_Predecessor();

        /**
         * The meta object literal for the '<em><b>Scenario Behaviour Abstract User Action</b></em>'
         * container reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference ABSTRACT_USER_ACTION__SCENARIO_BEHAVIOUR_ABSTRACT_USER_ACTION = eINSTANCE
                .getAbstractUserAction_ScenarioBehaviour_AbstractUserAction();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.usagemodel.impl.ScenarioBehaviourImpl
         * <em>Scenario Behaviour</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.usagemodel.impl.ScenarioBehaviourImpl
         * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getScenarioBehaviour()
         * @generated
         */
        EClass SCENARIO_BEHAVIOUR = eINSTANCE.getScenarioBehaviour();

        /**
         * The meta object literal for the '<em><b>Usage Scenario Senario Behaviour</b></em>'
         * container reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference SCENARIO_BEHAVIOUR__USAGE_SCENARIO_SENARIO_BEHAVIOUR = eINSTANCE
                .getScenarioBehaviour_UsageScenario_SenarioBehaviour();

        /**
         * The meta object literal for the '<em><b>Branch Transition Scenario Behaviour</b></em>'
         * container reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference SCENARIO_BEHAVIOUR__BRANCH_TRANSITION_SCENARIO_BEHAVIOUR = eINSTANCE
                .getScenarioBehaviour_BranchTransition_ScenarioBehaviour();

        /**
         * The meta object literal for the '<em><b>Loop Scenario Behaviour</b></em>' container
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference SCENARIO_BEHAVIOUR__LOOP_SCENARIO_BEHAVIOUR = eINSTANCE
                .getScenarioBehaviour_Loop_ScenarioBehaviour();

        /**
         * The meta object literal for the '<em><b>Actions Scenario Behaviour</b></em>' containment
         * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference SCENARIO_BEHAVIOUR__ACTIONS_SCENARIO_BEHAVIOUR = eINSTANCE
                .getScenarioBehaviour_Actions_ScenarioBehaviour();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.usagemodel.impl.BranchTransitionImpl
         * <em>Branch Transition</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.usagemodel.impl.BranchTransitionImpl
         * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getBranchTransition()
         * @generated
         */
        EClass BRANCH_TRANSITION = eINSTANCE.getBranchTransition();

        /**
         * The meta object literal for the '<em><b>Branch Probability</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EAttribute BRANCH_TRANSITION__BRANCH_PROBABILITY = eINSTANCE.getBranchTransition_BranchProbability();

        /**
         * The meta object literal for the '<em><b>Branch Branch Transition</b></em>' container
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference BRANCH_TRANSITION__BRANCH_BRANCH_TRANSITION = eINSTANCE
                .getBranchTransition_Branch_BranchTransition();

        /**
         * The meta object literal for the '<em><b>Branched Behaviour Branch Transition</b></em>'
         * containment reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference BRANCH_TRANSITION__BRANCHED_BEHAVIOUR_BRANCH_TRANSITION = eINSTANCE
                .getBranchTransition_BranchedBehaviour_BranchTransition();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.usagemodel.impl.BranchImpl <em>Branch</em>}' class. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.usagemodel.impl.BranchImpl
         * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getBranch()
         * @generated
         */
        EClass BRANCH = eINSTANCE.getBranch();

        /**
         * The meta object literal for the '<em><b>Branch Transitions Branch</b></em>' containment
         * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference BRANCH__BRANCH_TRANSITIONS_BRANCH = eINSTANCE.getBranch_BranchTransitions_Branch();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.usagemodel.impl.LoopImpl <em>Loop</em>}' class. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.usagemodel.impl.LoopImpl
         * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getLoop()
         * @generated
         */
        EClass LOOP = eINSTANCE.getLoop();

        /**
         * The meta object literal for the '<em><b>Loop Iteration Loop</b></em>' containment
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference LOOP__LOOP_ITERATION_LOOP = eINSTANCE.getLoop_LoopIteration_Loop();

        /**
         * The meta object literal for the '<em><b>Body Behaviour Loop</b></em>' containment
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference LOOP__BODY_BEHAVIOUR_LOOP = eINSTANCE.getLoop_BodyBehaviour_Loop();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.usagemodel.impl.StopImpl <em>Stop</em>}' class. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.usagemodel.impl.StopImpl
         * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getStop()
         * @generated
         */
        EClass STOP = eINSTANCE.getStop();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.usagemodel.impl.StartImpl <em>Start</em>}' class. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.usagemodel.impl.StartImpl
         * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getStart()
         * @generated
         */
        EClass START = eINSTANCE.getStart();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.usagemodel.impl.OpenWorkloadImpl <em>Open Workload</em>}
         * ' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.usagemodel.impl.OpenWorkloadImpl
         * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getOpenWorkload()
         * @generated
         */
        EClass OPEN_WORKLOAD = eINSTANCE.getOpenWorkload();

        /**
         * The meta object literal for the '<em><b>Inter Arrival Time Open Workload</b></em>'
         * containment reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference OPEN_WORKLOAD__INTER_ARRIVAL_TIME_OPEN_WORKLOAD = eINSTANCE
                .getOpenWorkload_InterArrivalTime_OpenWorkload();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.usagemodel.impl.DelayImpl <em>Delay</em>}' class. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.usagemodel.impl.DelayImpl
         * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getDelay()
         * @generated
         */
        EClass DELAY = eINSTANCE.getDelay();

        /**
         * The meta object literal for the '<em><b>Time Specification Delay</b></em>' containment
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference DELAY__TIME_SPECIFICATION_DELAY = eINSTANCE.getDelay_TimeSpecification_Delay();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.usagemodel.impl.ClosedWorkloadImpl
         * <em>Closed Workload</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.usagemodel.impl.ClosedWorkloadImpl
         * @see org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl#getClosedWorkload()
         * @generated
         */
        EClass CLOSED_WORKLOAD = eINSTANCE.getClosedWorkload();

        /**
         * The meta object literal for the '<em><b>Population</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CLOSED_WORKLOAD__POPULATION = eINSTANCE.getClosedWorkload_Population();

        /**
         * The meta object literal for the '<em><b>Think Time Closed Workload</b></em>' containment
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference CLOSED_WORKLOAD__THINK_TIME_CLOSED_WORKLOAD = eINSTANCE.getClosedWorkload_ThinkTime_ClosedWorkload();

    }

} // UsagemodelPackage
