package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.seffstatements;

import static org.junit.Assert.fail;

import java.util.Set;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.text.edits.InsertEdit;
import org.emftext.language.java.members.Method;
import org.junit.Test;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.seff.AbstractAction;
import org.palladiosimulator.pcm.seff.BranchAction;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.seff.InternalAction;
import org.palladiosimulator.pcm.seff.LoopAction;
import org.palladiosimulator.pcm.seff.ProbabilisticBranchTransition;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.somox.test.gast2seff.visitors.AssertSEFFHelper;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.jamopp2pcm.JaMoPP2PCMTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.PCM2JaMoPPTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;

public class SEFF2PCMTest extends JaMoPP2PCMTransformationTest {

    private static final String MEDIA_STORE = "MediaStore";
    private static final String WEBGUI = "WebGUI";
    private static final String UPLOAD = "upload";
    private static final String DOWNLOAD = "download";

    @SuppressWarnings("unused")
    private Repository repository;
    @SuppressWarnings("unused")
    private OperationSignature httpDownloadOpSig;
    @SuppressWarnings("unused")
    private OperationSignature httpUploadOpSig;
    @SuppressWarnings("unused")
    private OperationSignature uploadOpSig;
    private OperationSignature downloadOpSig;
    private OperationRequiredRole webGUIRequiresIMediaStoreRole;

    /**
     * Set up simple media store, which can be used for the tests. It consists of two components
     * (WebGUI and MediaStore) two Interfaces (IWebGUI and IMediastore). The interfaces have two
     * methods each ((web)download and (web)upload).
     */
    @Override
    protected void beforeTest() throws Throwable {
        // this.repository = this.createMediaStoreViaPCM();
        super.beforeTest();
        this.repository = this.createMediaStoreViaCode();
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
        final String code = this.getExternalCallContent();

        final ResourceDemandingSEFF seff = this.editWebGUIDownloadMethod(code);

        final ExternalCallAction eca = this.createSimpleExternalCallAction();
        final ResourceDemandingSEFF expectedSeff = this.createSEFFWithAbstractActions(eca);
        AssertSEFFHelper.assertSeffEquals(seff, expectedSeff);
    }

    @Test
    public void testAddForWithExternalCallStatement() throws Throwable {
        final String code = "for(int i = 0; i < 10; i++){\n" + this.getExternalCallContent() + "\n" + "}";

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
        this.addInternalMethodToWebGUI("internalMethod", "System.out.println(\"Test\");");
        final String code = "internalMethod();";

        final ResourceDemandingSEFF seff = this.editWebGUIDownloadMethod(code);

        final InternalAction ia = SeffFactory.eINSTANCE.createInternalAction();
        AssertSEFFHelper.assertSeffEquals(seff, this.createSEFFWithAbstractActions(ia));
    }

    @Test
    public void testAddInternalMethodCallContainingExternalCall() throws Throwable {
        this.addInternalMethodToWebGUI("internalMethod", this.getExternalCallContent());
        final String code = "internalMethod();";

        final ResourceDemandingSEFF seff = this.editWebGUIDownloadMethod(code);

        final ExternalCallAction eca = this.createSimpleExternalCallAction();
        final ResourceDemandingSEFF expectedSeff = this.createSEFFWithAbstractActions(eca);
        AssertSEFFHelper.assertSeffEquals(seff, expectedSeff);
    }

    @Test
    public void testAddIfStatementContainingExternalCall() throws Throwable {
        final String code = "int i =5;\nif(i<10){\n" + this.getExternalCallContent() + "\n}\n";

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

    private void addInternalMethodToWebGUI(final String methodName, final String methodContent) throws Throwable {
        final String compilationUnitName = WEBGUI + "Impl";
        final String methodHeader = "private void " + methodName + "(){\n}";
        this.addMethodToCompilationUnit(compilationUnitName, methodHeader);
        this.editMethod(methodContent, compilationUnitName, methodName, false);

    }

    private ExternalCallAction createSimpleExternalCallAction() {
        final ExternalCallAction eca = SeffFactory.eINSTANCE.createExternalCallAction();
        eca.setCalledService_ExternalService(this.downloadOpSig);
        eca.setRole_ExternalService(this.webGUIRequiresIMediaStoreRole);
        return eca;
    }

    private String getExternalCallContent() {
        final String code = "i" + MEDIA_STORE + "." + DOWNLOAD + "();";
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
        final ICompilationUnit iCu = super.findICompilationUnitWithClassName(compilationUnitName);
        final IMethod iMethod = super.findIMethodByName(compilationUnitName, methodName, iCu);
        int offset = iMethod.getSourceRange().getOffset();
        offset += iMethod.getSource().length() - 2;
        final InsertEdit insertEdit = new InsertEdit(offset, code);
        this.editCompilationUnit(iCu, insertEdit);
        TestUtil.waitForSynchronization(3 * 1000);
        final CorrespondenceInstance ci = this.getCorrespondenceInstance();
        final Method method = super.findJaMoPPMethodInICU(iCu, methodName);
        final Set<ResourceDemandingSEFF> seffs = ci.getCorrespondingEObjectsByType(method, ResourceDemandingSEFF.class);
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

    @SuppressWarnings("unused")
    private Repository createMediaStoreViaPCM() throws Throwable {
        final PCM2JaMoPPTransformationTest pcmJaMoPPTransformationTest = new PCM2JaMoPPTransformationTest();
        return pcmJaMoPPTransformationTest.createMediaStore(MEDIA_STORE, WEBGUI, DOWNLOAD, UPLOAD);
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

    private Repository createMediaStoreViaCode() throws Throwable {
        // create main package
        final Repository repo = super.addFirstPackage();

        // create packages
        this.addPackageAndImplementingClass(MEDIA_STORE);
        this.addPackageAndImplementingClass(WEBGUI);

        final String webGuiInterfaceName = "I" + WEBGUI;
        final String mediaStoreInterfaceName = "I" + MEDIA_STORE;

        // create interfaces
        final boolean throwExceptionIfFails = true;
        super.createInterfaceInPackage("contracts", throwExceptionIfFails, webGuiInterfaceName);
        super.createInterfaceInPackage("contracts", throwExceptionIfFails, mediaStoreInterfaceName);

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

        final String mediaStoreClassName = MEDIA_STORE + "Impl";
        final String webGUIClassName = WEBGUI + "Impl";

        // create implements
        super.addImplementsCorrespondingToOperationProvidedRoleToClass(webGUIClassName, webGuiInterfaceName);
        super.addImplementsCorrespondingToOperationProvidedRoleToClass(mediaStoreClassName, mediaStoreInterfaceName);

        // create class methods in component implementing classes in order to create SEFF
        // correspondences
        this.addClassMethodToClassThatOverridesInterfaceMethod(webGUIClassName, httpUploadMethodName);
        this.addClassMethodToClassThatOverridesInterfaceMethod(webGUIClassName, httpDownloadMethodName);
        this.addClassMethodToClassThatOverridesInterfaceMethod(mediaStoreClassName, uploadMethodName);
        this.addClassMethodToClassThatOverridesInterfaceMethod(mediaStoreClassName, downloadMethodName);

        // create requiredRole from webgui to IMediaStore
        this.webGUIRequiresIMediaStoreRole = this.addFieldToClassWithName(webGUIClassName, mediaStoreInterfaceName,
                "i" + MEDIA_STORE, OperationRequiredRole.class);
        return repo;
    }

}
