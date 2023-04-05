package tools.vitruv.testutils

import java.nio.file.Path
import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.change.propagation.ChangePropagationSpecification
import tools.vitruv.framework.vsum.VirtualModelBuilder
import tools.vitruv.framework.vsum.internal.InternalVirtualModel
import tools.vitruv.testutils.views.ChangePublishingTestView
import tools.vitruv.testutils.views.NonTransactionalTestView
import tools.vitruv.testutils.views.UriMode

class DefaultVirtualModelBasedTestView implements VirtualModelBasedTestView, NonTransactionalTestView {
	InternalVirtualModel virtualModel
	@Delegate
	NonTransactionalTestView testView
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

	private def NonTransactionalTestView generateTestView(Path testProjectPath, TestUserInteraction userInteraction) {
		new ChangePublishingTestView(testProjectPath, userInteraction, this.uriMode, virtualModel,
			virtualModel.uuidResolver)[virtualModel.getModelInstance(it)?.resource]
	}

	override getVirtualModel() {
		return virtualModel
	}

	override void close() {
		virtualModel?.dispose()
		testView?.close()
	}

}
