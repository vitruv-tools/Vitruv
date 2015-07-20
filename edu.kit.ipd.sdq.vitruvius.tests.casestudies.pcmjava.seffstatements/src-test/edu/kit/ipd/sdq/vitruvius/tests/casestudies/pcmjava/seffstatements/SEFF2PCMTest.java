package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.seffstatements;

import static org.junit.Assert.fail;

import java.util.Set;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.text.edits.InsertEdit;
import org.emftext.language.java.members.Method;
import org.junit.Test;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.seff.InternalAction;
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

    private Repository repository;

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
        final String text = "final int i = 5;\nfinal int j = i + 1;";

        final ResourceDemandingSEFF seff = this.editWebGUIDownloadMethod(text);

        final ResourceDemandingSEFF expectedSeff = SeffFactory.eINSTANCE.createResourceDemandingSEFF();
        expectedSeff.getSteps_Behaviour().add(SeffFactory.eINSTANCE.createStartAction());
        final InternalAction ia = SeffFactory.eINSTANCE.createInternalAction();
        expectedSeff.getSteps_Behaviour().add(ia);
        expectedSeff.getSteps_Behaviour().add(SeffFactory.eINSTANCE.createStopAction());

        AssertSEFFHelper.assertSeffEquals(seff, expectedSeff);
    }

    private ResourceDemandingSEFF editWebGUIDownloadMethod(final String text) throws Throwable, JavaModelException {
        final ICompilationUnit iCu = super.findICompilationUnitWithClassName(WEBGUI + "Impl");
        final IMethod iMethod = super.findIMethodByName(WEBGUI + "Impl", "httpDownload", iCu);
        int offset = iMethod.getSourceRange().getOffset();
        offset += iMethod.getSource().length() - 2;
        final InsertEdit insertEdit = new InsertEdit(offset, text);
        this.editCompilationUnit(iCu, insertEdit);
        TestUtil.waitForSynchronization(10 * 1000);
        final CorrespondenceInstance ci = this.getCorrespondenceInstance();
        final Method method = super.findJaMoPPMethodInICU(iCu, "httpDownload");
        final Set<ResourceDemandingSEFF> seffs = ci.getCorrespondingEObjectsByType(method, ResourceDemandingSEFF.class);
        if (null == seffs || 0 == seffs.size()) {
            fail("could not find corresponding seff for method " + method);
        }
        return seffs.iterator().next();
    }

    @SuppressWarnings("unused")
    private Repository createMediaStoreViaPCM() throws Throwable {
        final PCM2JaMoPPTransformationTest pcmJaMoPPTransformationTest = new PCM2JaMoPPTransformationTest();
        return pcmJaMoPPTransformationTest.createMediaStore(MEDIA_STORE, WEBGUI, DOWNLOAD, UPLOAD);
    }

    @Test
    public void createMediaStoreViaCodeTest() throws Throwable {
        // nothing todo --> Media store is created in before test
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
        super.addMethodToInterfaceWithCorrespondence(webGuiInterfaceName, httpDownloadMethodName);
        super.addMethodToInterfaceWithCorrespondence(webGuiInterfaceName, httpUploadMethodName);
        super.addMethodToInterfaceWithCorrespondence(mediaStoreInterfaceName, uploadMethodName);
        super.addMethodToInterfaceWithCorrespondence(mediaStoreInterfaceName, downloadMethodName);

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
        return repo;
    }

}
