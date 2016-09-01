package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.packagemapping.seffstatements;

import static org.junit.Assert.fail;

import java.util.Set;
import java.util.function.Supplier;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.text.edits.InsertEdit;
import org.emftext.language.java.members.Method;
import org.junit.Test;
import org.junit.runner.Description;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.seff.AbstractAction;
import org.palladiosimulator.pcm.seff.BranchAction;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.seff.InternalAction;
import org.palladiosimulator.pcm.seff.InternalCallAction;
import org.palladiosimulator.pcm.seff.LoopAction;
import org.palladiosimulator.pcm.seff.ProbabilisticBranchTransition;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.somox.test.gast2seff.visitors.AssertSEFFHelper;
import org.somox.test.gast2seff.visitors.InternalCallActionTestHelper;

import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel;
import edu.kit.ipd.sdq.vitruvius.extensions.tests.util.TestUtil;
import edu.kit.ipd.sdq.vitruvius.framework.change.processing.Change2CommandTransformingProviding;
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModelUtil;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.util.CompilationUnitManipulatorHelper;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.util.JaMoPP2PCMTransformationTest;

public class IncrementalSEFFReconstructionTest extends JaMoPP2PCMTransformationTest {

    protected static final String MEDIA_STORE = "MediaStore";
    protected static final String WEBGUI = "WebGUI";
    protected static final String UPLOAD = "upload";
    protected static final String DOWNLOAD = "download";

    protected Repository repository;
    protected OperationSignature httpDownloadOpSig;
    protected OperationSignature httpUploadOpSig;
    protected OperationSignature uploadOpSig;
    protected OperationSignature downloadOpSig;
    protected OperationRequiredRole webGUIRequiresIMediaStoreRole;
    protected String webGUIPackageName;
    protected static final String MEDIA_STORE_CLASSNAME = MEDIA_STORE + "Impl";;
    protected static final String WEBGUI_CLASSNAME = WEBGUI + "Impl";;

    public IncrementalSEFFReconstructionTest(Supplier<Change2CommandTransformingProviding> change2CommandTransformingProvidingSupplier) {
    	super(change2CommandTransformingProvidingSupplier);
    }
    
    public IncrementalSEFFReconstructionTest() {
		super();
	}
    
    /**
     * Set up simple media store, which can be used for the tests. It consists of two components
     * (WebGUI and MediaStore) two Interfaces (IWebGUI and IMediastore). The interfaces have two
     * methods each ((web)download and (web)upload).
     */
    @Override
    protected void beforeTest(final Description description) throws Throwable {
        super.beforeTest(description);
        this.repository = this.createMediaStoreViaCode();
        this.webGUIPackageName = WEBGUI;
    }

    @Test
    public void testAddNormalStatement() throws Throwable {
        final String code = "final int i = 5;\nfinal int j = i + 1;";

        // test SEFF creation
        final ResourceDemandingSEFF seff = this.editWebGUIDownloadMethod(code);

        final InternalAction ia = SeffFactory.eINSTANCE.createInternalAction();
        final ResourceDemandingSEFF expectedSeff = this.createSEFFWithAbstractActions(ia);
        AssertSEFFHelper.assertSeffEquals(seff, expectedSeff);
    }

    @Test
    public void testAddExternalCallStatement() throws Throwable {
        final String code = this.getExternalCallCode();

        final ResourceDemandingSEFF seff = this.editWebGUIDownloadMethod(code);

        this.assertSEFFWithExternalCall(seff, false);
    }

    @Test
    public void testAddForWithExternalCallStatement() throws Throwable {
        final String code = "for(int i = 0; i < 10; i++){\n" + this.getExternalCallCode() + "\n" + "}";

        final ResourceDemandingSEFF seff = this.editWebGUIDownloadMethod(code);

        final LoopAction loopAction = SeffFactory.eINSTANCE.createLoopAction();
        final ResourceDemandingBehaviour behaviour = this
                .createResourceBehaviourWithAbstractActions(this.createSimpleExternalCallAction());
        loopAction.setBodyBehaviour_Loop(behaviour);
        final ResourceDemandingSEFF expectedSEFF = this.createSEFFWithAbstractActions(loopAction);
        AssertSEFFHelper.assertSeffEquals(seff, expectedSEFF);
    }

    @Test
    public void testAddInternalMethodCallWithoutExternalCall() throws Throwable {
        final InternalAction ia = SeffFactory.eINSTANCE.createInternalAction();
        final InternalCallAction internalCallAction = InternalCallActionTestHelper.createInternalCallAction(ia);
        final ResourceDemandingSEFF expectedSEFF = this.createSEFFWithAbstractActions(internalCallAction);

        // do the test
        this.addInternalMethodToWebGUI("internalMethod", "System.out.println(\"Test\");");
        final String code = "internalMethod();";
        final ResourceDemandingSEFF seff = this.editWebGUIDownloadMethod(code);

        AssertSEFFHelper.assertSeffEquals(seff, expectedSEFF);
    }

    @Test
    public void testAddInternalMethodCallContainingExternalCall() throws Throwable {
        final ExternalCallAction externalCallAction = this.createSimpleExternalCallAction();
        final InternalCallAction ica = InternalCallActionTestHelper.createInternalCallAction(externalCallAction);
        final ResourceDemandingBehaviour expectedSEFF = this.createSEFFWithAbstractActions(ica);

        this.addInternalMethodToWebGUI("internalMethod", this.getExternalCallCode());
        final String code = "internalMethod();";
        final ResourceDemandingSEFF seff = this.editWebGUIDownloadMethod(code);

        AssertSEFFHelper.assertSeffEquals(seff, expectedSEFF);
    }

    @Test
    public void testAddIfStatementContainingExternalCall() throws Throwable {
        final String code = "int i =5;\nif(i<10){\n" + this.getExternalCallCode() + "\n}\n";

        final ResourceDemandingSEFF seff = this.editWebGUIDownloadMethod(code);

        final InternalAction ia = SeffFactory.eINSTANCE.createInternalAction();
        final ExternalCallAction eca = this.createSimpleExternalCallAction();
        final ResourceDemandingBehaviour behaviour = this.createResourceBehaviourWithAbstractActions(eca);
        final BranchAction ba = SeffFactory.eINSTANCE.createBranchAction();
        final ProbabilisticBranchTransition bt = SeffFactory.eINSTANCE.createProbabilisticBranchTransition();
        ba.getBranches_Branch().add(bt);
        bt.setBranchBehaviour_BranchTransition(behaviour);
        final ResourceDemandingSEFF expectedSEFF = this.createSEFFWithAbstractActions(ia, ba);
        AssertSEFFHelper.assertSeffEquals(seff, expectedSEFF);
    }

    @Test
    public void testForLoopStatementContainingExternalCall() throws Throwable {
        final String code = "for(int i = 0; i < 5; i++){\n" + this.getExternalCallCode() + "\n\n" + "}";

        final ResourceDemandingSEFF seff = this.editWebGUIDownloadMethod(code);

        final ExternalCallAction eca = this.createSimpleExternalCallAction();
        final ResourceDemandingBehaviour behaviour = this.createResourceBehaviourWithAbstractActions(eca);
        final LoopAction loopAction = SeffFactory.eINSTANCE.createLoopAction();
        loopAction.setEntityName("expectedLoopBehaviour");
        loopAction.setBodyBehaviour_Loop(behaviour);
        final ResourceDemandingSEFF expectedSEFF = this.createSEFFWithAbstractActions(loopAction);
        AssertSEFFHelper.assertSeffEquals(seff, expectedSEFF);
    }

    @Test
    public void testSwitchContainingExternalCall() throws Throwable {
        final String code = "final int i = 5;\n" + "switch (i) {\n" + "case 1: System.out.println(1);\n"
                + "    break;\n" + "case 2: System.out.println(2);\n" + "    break;\n" + "case 3:"
                + this.getExternalCallCode() + "\n" + "    break;\n" + "case 4: " + this.getExternalCallCode()
                + "\nbreak;\n" + "default:\n" + "System.out.println(-1);\n" + "break;\n" + "}\n";

        final ResourceDemandingSEFF seff = this.editWebGUIDownloadMethod(code);

        // internal action for int i
        final InternalAction ia = SeffFactory.eINSTANCE.createInternalAction();
        // behaviour for branches
        final ResourceDemandingBehaviour behaviourForCase1 = this
                .createResourceBehaviourWithAbstractActions(SeffFactory.eINSTANCE.createInternalAction());
        final ResourceDemandingBehaviour behaviourForCase2 = this
                .createResourceBehaviourWithAbstractActions(SeffFactory.eINSTANCE.createInternalAction());
        final ExternalCallAction ecaCase3 = this.createSimpleExternalCallAction();
        final ResourceDemandingBehaviour behaviourForCase3 = this.createResourceBehaviourWithAbstractActions(ecaCase3);
        final ExternalCallAction ecaCase4 = this.createSimpleExternalCallAction();
        final ResourceDemandingBehaviour behaviourForCase4 = this.createResourceBehaviourWithAbstractActions(ecaCase4);
        final ResourceDemandingBehaviour behaviourForDefaultCase = this
                .createResourceBehaviourWithAbstractActions(SeffFactory.eINSTANCE.createInternalAction());
        final BranchAction ba = SeffFactory.eINSTANCE.createBranchAction();
        final ProbabilisticBranchTransition btCase1 = SeffFactory.eINSTANCE.createProbabilisticBranchTransition();
        final ProbabilisticBranchTransition btCase2 = SeffFactory.eINSTANCE.createProbabilisticBranchTransition();
        final ProbabilisticBranchTransition btCase3 = SeffFactory.eINSTANCE.createProbabilisticBranchTransition();
        final ProbabilisticBranchTransition btCase4 = SeffFactory.eINSTANCE.createProbabilisticBranchTransition();
        final ProbabilisticBranchTransition btDefaultCase = SeffFactory.eINSTANCE.createProbabilisticBranchTransition();
        btCase1.setBranchBehaviour_BranchTransition(behaviourForCase1);
        btCase2.setBranchBehaviour_BranchTransition(behaviourForCase2);
        btCase3.setBranchBehaviour_BranchTransition(behaviourForCase3);
        btCase4.setBranchBehaviour_BranchTransition(behaviourForCase4);
        btDefaultCase.setBranchBehaviour_BranchTransition(behaviourForDefaultCase);
        ba.getBranches_Branch().add(btCase1);
        ba.getBranches_Branch().add(btCase2);
        ba.getBranches_Branch().add(btCase3);
        ba.getBranches_Branch().add(btCase4);
        ba.getBranches_Branch().add(btDefaultCase);
        final ResourceDemandingSEFF expectedSEFF = this.createSEFFWithAbstractActions(ia, ba);
        AssertSEFFHelper.assertSeffEquals(seff, expectedSEFF);
    }

    @Test
    public void testExternalCallToOtherComponentClass() throws Throwable {
        final String code = this.getExternalCallToOtherComponentClassCode();

        final ResourceDemandingSEFF seff = this.editWebGUIDownloadMethod(code);

        this.assertSEFFWithExternalCall(seff, true);
    }

    @Test
    public void testComponentInternalCallInSameComponent() throws Throwable {
        final String codeForHelper = "System.out.println(\"Test\");";
        final String webGuiHelper = "WebGUIHelper";
        final String downloadHelper = "downloadHelper";
        this.createHelperClassAndMethod(this.webGUIPackageName, webGuiHelper, downloadHelper, "", codeForHelper);
        final String code = "WebGUIHelper webGuiHelper = new WebGUIHelper();\nwebGuiHelper.downloadHelper();";

        final ResourceDemandingSEFF seff = this.editWebGUIDownloadMethod(code);

        final InternalAction ia = SeffFactory.eINSTANCE.createInternalAction();
        final InternalCallAction internalCallAction = InternalCallActionTestHelper.createInternalCallAction(ia);
        final ResourceDemandingSEFF expectedSEFF = this
                .createSEFFWithAbstractActions(SeffFactory.eINSTANCE.createInternalAction(), internalCallAction);
        AssertSEFFHelper.assertSeffEquals(seff, expectedSEFF);
    }

    @Test
    public void testComponentInternalCallWithExternalCallInSameComponent() throws Throwable {
        final String webGuiHelper = "WebGUIHelper";
        final String downloadHelper = "downloadHelper";
        final String parameterCode = "testRepository.contracts.IMediaStore iMediaStore";
        final String codeForHelper = this.getExternalCallCode();
        this.createHelperClassAndMethod(this.webGUIPackageName, webGuiHelper, downloadHelper, parameterCode,
                codeForHelper);
        final String code = "WebGUIHelper webGuiHelper = new WebGUIHelper();\nwebGuiHelper.downloadHelper(this.iMediaStore);";

        final ResourceDemandingSEFF seff = this.editWebGUIDownloadMethod(code);

        final ExternalCallAction eca = this.createSimpleExternalCallAction();
        final InternalCallAction internalCallAction = InternalCallActionTestHelper.createInternalCallAction(eca);
        final ResourceDemandingSEFF expectedSEFF = this
                .createSEFFWithAbstractActions(SeffFactory.eINSTANCE.createInternalAction(), internalCallAction);
        AssertSEFFHelper.assertSeffEquals(seff, expectedSEFF);
    }

    private void createHelperClassAndMethod(final String packageName, final String className, final String methodName,
            final String parameterCode, final String codeForHelper)
                    throws Throwable, CoreException, InterruptedException, JavaModelException {
        final String downloadHelperCode = "\npublic void " + methodName + "( " + parameterCode + "){\n}\n";
        this.createClassInPackage(this.getPackageWithNameFromCorrespondenceModel(packageName), className);
        CompilationUnitManipulatorHelper.addMethodToCompilationUnit(className, downloadHelperCode,
                this.currentTestProject);
        this.editMethod(codeForHelper, className, methodName, false);
    }

    private void assertSEFFWithExternalCall(final ResourceDemandingSEFF seff,
            final boolean addInternalActionBeforeExternalCall) {
        final ExternalCallAction eca = this.createSimpleExternalCallAction();
        ResourceDemandingSEFF expectedSeff = null;
        if (addInternalActionBeforeExternalCall) {
            final InternalAction ia = SeffFactory.eINSTANCE.createInternalAction();
            expectedSeff = this.createSEFFWithAbstractActions(ia, eca);
        } else {
            expectedSeff = this.createSEFFWithAbstractActions(eca);
        }
        AssertSEFFHelper.assertSeffEquals(seff, expectedSeff);
    }

    private void addInternalMethodToWebGUI(final String methodName, final String methodContent) throws Throwable {
        final String compilationUnitName = WEBGUI + "Impl";
        final String methodHeader = "private void " + methodName + "(){\n}";
        CompilationUnitManipulatorHelper.addMethodToCompilationUnit(compilationUnitName, methodHeader,
                this.currentTestProject);
        this.editMethod(methodContent, compilationUnitName, methodName, false);

    }

    private ExternalCallAction createSimpleExternalCallAction() {
        final ExternalCallAction eca = SeffFactory.eINSTANCE.createExternalCallAction();
        eca.setCalledService_ExternalService(this.downloadOpSig);
        eca.setRole_ExternalService(this.webGUIRequiresIMediaStoreRole);
        return eca;
    }

    private String getExternalCallCode() {
        final String code = "i" + MEDIA_STORE + "." + DOWNLOAD + "();";
        return code;
    }

    private String getExternalCallToOtherComponentClassCode() throws Throwable {
        final ICompilationUnit icu = CompilationUnitManipulatorHelper
                .findICompilationUnitWithClassName(WEBGUI_CLASSNAME, this.currentTestProject);
        super.importCompilationUnitWithName(MEDIA_STORE_CLASSNAME, icu);
        String code = MEDIA_STORE_CLASSNAME + " " + MEDIA_STORE_CLASSNAME.toLowerCase() + " = " + "new "
                + MEDIA_STORE_CLASSNAME + "();";
        code += MEDIA_STORE_CLASSNAME.toLowerCase() + "." + DOWNLOAD + "();";
        return code;
    }

    private ResourceDemandingSEFF editWebGUIDownloadMethod(final String code) throws Throwable, JavaModelException {
        final String compilationUnitName = WEBGUI + "Impl";
        final String methodName = "httpDownload";
        return this.editMethod(code, compilationUnitName, methodName, true);
    }

    private ResourceDemandingSEFF editMethod(final String code, final String compilationUnitName,
            final String methodName, final boolean shouldHaveCorrespndingSEFFAfterEdit)
                    throws Throwable, JavaModelException {
        final ICompilationUnit iCu = CompilationUnitManipulatorHelper
                .findICompilationUnitWithClassName(compilationUnitName, this.currentTestProject);
        final IMethod iMethod = super.findIMethodByName(compilationUnitName, methodName, iCu);
        int offset = iMethod.getSourceRange().getOffset();
        offset += iMethod.getSource().length() - 2;
        final InsertEdit insertEdit = new InsertEdit(offset, code);
        CompilationUnitManipulatorHelper.editCompilationUnit(iCu, insertEdit);
        TestUtil.waitForSynchronization(3 * 1000);
        final CorrespondenceModel ci = this.getCorrespondenceModel();
        final Method method = super.findJaMoPPMethodInICU(iCu, methodName);
        final Set<ResourceDemandingSEFF> seffs = CorrespondenceModelUtil.getCorrespondingEObjectsByType(ci, method,
                ResourceDemandingSEFF.class);
        if (null == seffs || 0 == seffs.size()) {
            if (shouldHaveCorrespndingSEFFAfterEdit) {
                fail("could not find corresponding seff for method " + method);
            } else {
                return null;
            }
        }
        if (!shouldHaveCorrespndingSEFFAfterEdit) {
            fail("method has a corresponding seff but it should not have one: Method: " + method + " SEFF: "
                    + seffs.iterator().next());
        }
        return seffs.iterator().next();

    }

    /*
     * @Test public void createMediaStoreViaCodeTest() throws Throwable { // nothing todo --> Media
     * store is created in before test }
     */

    private ResourceDemandingSEFF createSEFFWithAbstractActions(final AbstractAction... abstractActions) {
        final ResourceDemandingSEFF expectedSeff = SeffFactory.eINSTANCE.createResourceDemandingSEFF();
        this.addActionsToBehaviour(expectedSeff, abstractActions);
        return expectedSeff;
    }

    private ResourceDemandingBehaviour createResourceBehaviourWithAbstractActions(
            final AbstractAction... abstractActions) {
        final ResourceDemandingBehaviour behaviour = SeffFactory.eINSTANCE.createResourceDemandingBehaviour();
        this.addActionsToBehaviour(behaviour, abstractActions);
        return behaviour;
    }

    private void addActionsToBehaviour(final ResourceDemandingBehaviour behaviour,
            final AbstractAction... abstractActions) {
        behaviour.getSteps_Behaviour().add(SeffFactory.eINSTANCE.createStartAction());

        for (final AbstractAction abstractAction : abstractActions) {
            behaviour.getSteps_Behaviour().add(abstractAction);
        }

        behaviour.getSteps_Behaviour().add(SeffFactory.eINSTANCE.createStopAction());
    }

    protected Repository createMediaStoreViaCode() throws Throwable {
        // create main package
        final Repository repo = super.addRepoContractsAndDatatypesPackage();

        // create packages
        this.addPackageAndImplementingClass(MEDIA_STORE);
        this.addPackageAndImplementingClass(WEBGUI);

        final String webGuiInterfaceName = "I" + WEBGUI;
        final String mediaStoreInterfaceName = "I" + MEDIA_STORE;

        // create interfaces
        super.createInterfaceInPackageBasedOnJaMoPPPackageWithCorrespondence("contracts", webGuiInterfaceName);
        super.createInterfaceInPackageBasedOnJaMoPPPackageWithCorrespondence("contracts", mediaStoreInterfaceName);

        final String uploadMethodName = "upload";
        final String downloadMethodName = "download";

        final String httpDownloadMethodName = "httpDownload";
        final String httpUploadMethodName = "httpUpload";

        // create interface methods
        this.httpDownloadOpSig = super.addMethodToInterfaceWithCorrespondence(webGuiInterfaceName,
                httpDownloadMethodName);
        this.httpUploadOpSig = super.addMethodToInterfaceWithCorrespondence(webGuiInterfaceName, httpUploadMethodName);
        this.uploadOpSig = super.addMethodToInterfaceWithCorrespondence(mediaStoreInterfaceName, uploadMethodName);
        this.downloadOpSig = super.addMethodToInterfaceWithCorrespondence(mediaStoreInterfaceName, downloadMethodName);

        // create implements
        super.addImplementsCorrespondingToOperationProvidedRoleToClass(WEBGUI_CLASSNAME, webGuiInterfaceName);
        super.addImplementsCorrespondingToOperationProvidedRoleToClass(MEDIA_STORE_CLASSNAME, mediaStoreInterfaceName);

        // create class methods in component implementing classes in order to create SEFF
        // correspondences
        this.addClassMethodToClassThatOverridesInterfaceMethod(WEBGUI_CLASSNAME, httpUploadMethodName);
        this.addClassMethodToClassThatOverridesInterfaceMethod(WEBGUI_CLASSNAME, httpDownloadMethodName);
        this.addClassMethodToClassThatOverridesInterfaceMethod(MEDIA_STORE_CLASSNAME, uploadMethodName);
        this.addClassMethodToClassThatOverridesInterfaceMethod(MEDIA_STORE_CLASSNAME, downloadMethodName);

        // create requiredRole from webgui to IMediaStore
        this.webGUIRequiresIMediaStoreRole = this.addFieldToClassWithName(WEBGUI_CLASSNAME, mediaStoreInterfaceName,
                "i" + MEDIA_STORE, OperationRequiredRole.class);
        return repo;
    }

    protected void setWebGUIPackageName(final String webGUIPackageName) {
        this.webGUIPackageName = webGUIPackageName;
    }

}
