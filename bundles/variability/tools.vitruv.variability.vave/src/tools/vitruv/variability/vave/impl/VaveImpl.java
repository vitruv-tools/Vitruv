package tools.vitruv.variability.vave.impl;

import java.nio.file.Path;

import tools.vitruv.framework.domains.repository.VitruvDomainRepository;
import tools.vitruv.framework.userinteraction.UserInteractionFactory;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.framework.vsum.VirtualModelBuilder;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.variability.vave.Vave;
import vavemodel.VavemodelFactory;

public class VaveImpl implements Vave {

	private VitruvDomainRepository domainRepo;
	private final vavemodel.System system;

	public VaveImpl(VitruvDomainRepository domainRepo) {
		this.system = VavemodelFactory.eINSTANCE.createSystem();
		this.domainRepo = domainRepo;
	}

	public VirtualModel externalizeProduct(Path storageFolder) { // TODO: add configuration parameter

		final InternalVirtualModel virtualModel = new VirtualModelBuilder().withStorageFolder(storageFolder)
				.withDomainRepository(this.domainRepo)
				.withUserInteractor(UserInteractionFactory.instance.createUserInteractor(
						UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null)))
				.buildAndInitialize();

		// TODO: create model instances (for every domain) in the vsum instance

		// TODO: check what of the following should be part of eP and part of Test
//		final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
//		final ChangeRecorder changeRecorder = new ChangeRecorder(resourceSet);
//		changeRecorder.addToRecording(resourceSet);
//		changeRecorder.beginRecording();
//		Resource _createResource = resourceSet.createResource(this.createTestModelResourceUri(""));
//		final Procedure1<Resource> _function = (Resource it) -> {
//			EList<EObject> _contents = it.getContents();
//			Root _Root = AllElementTypesCreators.aet.Root();
//			final Procedure1<Root> _function_1 = (Root it_1) -> {
//				it_1.setId("root");
//			};
//			Root _doubleArrow = ObjectExtensions.<Root>operator_doubleArrow(_Root, _function_1);
//			_contents.add(_doubleArrow);
//		};
//		final Resource monitoredResource = ObjectExtensions.<Resource>operator_doubleArrow(_createResource, _function);
//		final TransactionalChange recordedChange = changeRecorder.endRecording();
//		virtualModel.propagateChange(recordedChange);
//		final ModelInstance vsumModel = virtualModel.getModelInstance(this.createTestModelResourceUri(""));
//		MatcherAssert.<Resource>assertThat(vsumModel.getResource(), ModelMatchers.containsModelOf(monitoredResource));

		// return vsum instance
		return virtualModel;

	}

	public void internalizeChanges(VirtualModel virtualModel) { // TODO: add expression parameter
		// store deltas of vsum in vave and map them to expression

	}

}
