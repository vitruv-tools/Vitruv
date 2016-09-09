package tools.vitruv.applications.pcmjava.seffstatements.tests.ejbtransformations

import org.emftext.language.java.classifiers.ConcreteClassifier
import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.OperationRequiredRole
import org.palladiosimulator.pcm.repository.Repository
import tools.vitruv.applications.pcmjava.tests.util.PCM2JaMoPPTestUtils
import org.junit.runner.Description
import tools.vitruv.framework.change.processing.impl.AbstractChange2CommandTransformingProviding
import tools.vitruv.applications.pcmjava.seffstatements.tests.pojotransformations.IncrementalSEFFReconstructionTest
import tools.vitruv.applications.pcmjava.tests.ejbtransformations.java2pcm.EJBClassMappingTest
import tools.vitruv.applications.pcmjava.tests.ejbtransformations.java2pcm.EJBJaMoPP2PCMTransformationTest
import tools.vitruv.applications.pcmjava.seffstatements.ejbtransformations.Change2CommandTransformingEjbJavaToPcmWithSeffstatements

class EJBIncrementalSEFFReconstructionTest extends IncrementalSEFFReconstructionTest {

	override protected createChange2CommandTransformingProviding() {
		AbstractChange2CommandTransformingProviding.createChange2CommandTransformingProviding(
			#[new Change2CommandTransformingEjbJavaToPcmWithSeffstatements()]);
	}

	@Override
	override protected void beforeTest(Description description) throws Throwable {
		super.beforeTest(description)
		super.setWebGUIPackageName = PCM2JaMoPPTestUtils.REPOSITORY_NAME
	}

	@Override
	override protected createMediaStoreViaCode() {
		// create main package
		val Repository repo = super.addRepoContractsAndDatatypesPackage();

		// create packages and component implementing classes
		val ConcreteClassifier classifier = super.createClassInPackage(this.mainPackage,
			MEDIA_STORE_CLASSNAME) as ConcreteClassifier
		this.addAnnotationToClassifier(classifier, EJBClassMappingTest.STATELESS_ANNOTATION_NAME, BasicComponent,
			MEDIA_STORE_CLASSNAME)

		val ConcreteClassifier webGUIclassifier = super.createClassInPackage(this.mainPackage,
			WEBGUI_CLASSNAME) as ConcreteClassifier
		this.addAnnotationToClassifier(webGUIclassifier, EJBClassMappingTest.STATELESS_ANNOTATION_NAME, BasicComponent,
			WEBGUI_CLASSNAME)

		val String webGuiInterfaceName = "I" + WEBGUI;
		val String mediaStoreInterfaceName = "I" + MEDIA_STORE;

		// create interfaces
		val webGuiIf = super.
			createInterfaceInPackageBasedOnJaMoPPPackageWithoutCorrespondence(PCM2JaMoPPTestUtils.REPOSITORY_NAME,
				webGuiInterfaceName)
		this.addAnnotationToClassifier(webGuiIf, EJBJaMoPP2PCMTransformationTest.REMOTE_ANNOTATION_NAME,
			OperationInterface, webGuiInterfaceName)

		val mediaStoreIf = super.
			createInterfaceInPackageBasedOnJaMoPPPackageWithoutCorrespondence(PCM2JaMoPPTestUtils.REPOSITORY_NAME,
				mediaStoreInterfaceName)
		this.addAnnotationToClassifier(mediaStoreIf, EJBJaMoPP2PCMTransformationTest.REMOTE_ANNOTATION_NAME,
			OperationInterface, mediaStoreInterfaceName)

		val String uploadMethodName = "upload";
		val String downloadMethodName = "download";

		val String httpDownloadMethodName = "httpDownload";
		val String httpUploadMethodName = "httpUpload";

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
		this.addFieldToClassWithName(WEBGUI_CLASSNAME, mediaStoreInterfaceName, "i" + MEDIA_STORE, null);
		this.webGUIRequiresIMediaStoreRole = super.addAnnotationToField("i" + MEDIA_STORE,
			EJBClassMappingTest.EJB_FIELD_ANNOTATION_NAME, OperationRequiredRole, WEBGUI_CLASSNAME)
		return repo;
	}

}
