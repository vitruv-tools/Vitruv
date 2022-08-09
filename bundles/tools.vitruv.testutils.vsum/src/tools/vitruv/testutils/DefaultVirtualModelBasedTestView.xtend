package tools.vitruv.testutils

import tools.vitruv.framework.vsum.internal.InternalVirtualModel
import org.eclipse.xtend.lib.annotations.Delegate
import java.nio.file.Path
import tools.vitruv.framework.vsum.VirtualModelBuilder
import tools.vitruv.change.propagation.ChangePropagationSpecification
import tools.vitruv.testutils.views.TestView
import tools.vitruv.testutils.views.UriMode
import tools.vitruv.testutils.views.ChangePublishingTestView

class DefaultVirtualModelBasedTestView implements VirtualModelBasedTestView {
	InternalVirtualModel virtualModel
	@Delegate
	TestView testView
	Iterable<ChangePropagationSpecification> changePropagationSpecifications
	UriMode uriMode

	new(
		Path testProjectPath,
		Path vsumPath,
		Iterable<? extends ChangePropagationSpecification> changePropagationSpecifications,
		UriMode uriMode
	) {
		this.changePropagationSpecifications = newArrayList(changePropagationSpecifications)
		this.uriMode = uriMode
		val userInteraction = new TestUserInteraction
		this.virtualModel = generateVirtualModel(testProjectPath, vsumPath, userInteraction)
		this.testView = generateTestView(testProjectPath, userInteraction)
	}

	private def InternalVirtualModel generateVirtualModel(Path testProjectPath, Path vsumPath,
		TestUserInteraction userInteraction) {
		new VirtualModelBuilder() //
		.withStorageFolder(vsumPath) //
		.withUserInteractorForResultProvider(new TestUserInteraction.ResultProvider(userInteraction)) //
		.withChangePropagationSpecifications(changePropagationSpecifications).buildAndInitialize()
	}

	private def TestView generateTestView(Path testProjectPath, TestUserInteraction userInteraction) {
		new ChangePublishingTestView(testProjectPath, userInteraction, this.uriMode, virtualModel)
	}

	override getVirtualModel() {
		return virtualModel
	}

	override void close() {
		virtualModel?.dispose()
		testView?.close()
	}

}
