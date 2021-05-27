package tools.vitruv.variability.vave.impl;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import tools.vitruv.framework.change.description.VitruviusChange;
import tools.vitruv.framework.domains.repository.VitruvDomainRepository;
import tools.vitruv.framework.propagation.ChangePropagationSpecification;
import tools.vitruv.framework.propagation.ChangePropagationSpecificationRepository;
import tools.vitruv.framework.userinteraction.InternalUserInteractor;
import tools.vitruv.framework.userinteraction.UserInteractionFactory;
import tools.vitruv.framework.vsum.VirtualModelManager;
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout;
import tools.vitruv.variability.vave.Vave;
import tools.vitruv.variability.vave.VirtualModelProduct;
import vavemodel.DeltaModule;
import vavemodel.VavemodelFactory;

public class VaveImpl implements Vave {

	private VitruvDomainRepository domainRepository = null;
	private final vavemodel.System system;

	public VaveImpl(VitruvDomainRepository domainRepository) {
		this.system = VavemodelFactory.eINSTANCE.createSystem();
		this.domainRepository = domainRepository;
	}

	public VirtualModelProduct externalizeProduct(Path storageFolder, String configuration) throws Exception {

//		final VirtualModelProductImpl vsum = new VirtualModelProductBuilder().withStorageFolder(storageFolder)
//				.withDomainRepository(this.domainRepository)
//				.withUserInteractor(UserInteractionFactory.instance.createUserInteractor(UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null)))
//				.buildAndInitialize();

		final Set<ChangePropagationSpecification> changePropagationSpecifications = new HashSet<ChangePropagationSpecification>();
		InternalUserInteractor userInteractor = UserInteractionFactory.instance
				.createUserInteractor(UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null));

		if (storageFolder == null)
			throw new Exception("No storage folder was configured!");
		if (userInteractor == null)
			throw new Exception("No user interactor was configured!");
		final ChangePropagationSpecificationRepository changeSpecificationRepository = new ChangePropagationSpecificationRepository(
				changePropagationSpecifications);
//		for (final ChangePropagationSpecification changePropagationSpecification : changePropagationSpecifications) {
//			{
//				Preconditions.checkState(
//						IterableExtensions.contains(this.domainRepository,
//								changePropagationSpecification.getSourceDomain()),
//						"The change propagation specification’s source domain ‹%s› has not been configured: %s",
//						changePropagationSpecification.getSourceDomain(), changePropagationSpecification);
//				Preconditions.checkState(
//						IterableExtensions.contains(this.domainRepository,
//								changePropagationSpecification.getTargetDomain()),
//						"The change propagation specification’s target domain ‹%s› has not been configured: %s",
//						changePropagationSpecification.getTargetDomain(), changePropagationSpecification);
//			}
//		}
		for (final ChangePropagationSpecification changePropagationSpecification_1 : changePropagationSpecifications) {
			changePropagationSpecification_1.setUserInteractor(userInteractor);
		}
		final VsumFileSystemLayout fileSystemLayout = new VsumFileSystemLayout(storageFolder);
		fileSystemLayout.prepare();
		final VirtualModelProductImpl vsum = new VirtualModelProductImpl(configuration, fileSystemLayout,
				userInteractor, this.domainRepository, changeSpecificationRepository);
		vsum.loadExistingModels();
		VirtualModelManager.getInstance().putVirtualModel(vsum);

		// TODO: create model instances (for every domain) in the vsum instance by
		// applying the stored deltas
		// for each delta: check if its mapping is true given configuration. if yes:
		// propagate it in vsum.

		EList<DeltaModule> deltamodules = this.system.getDeltamodule();
		for (DeltaModule deltamodule : deltamodules) {
			EStructuralFeature eStructFeature = deltamodule.eClass().getEStructuralFeature("delta");
			VitruviusChange vitruvchange = (VitruviusChange) deltamodule.eGet(eStructFeature);
			vsum.propagateChange(vitruvchange);
		}

		// return vsum instance
		return vsum;
	}

	public void internalizeChanges(VirtualModelProduct virtualModel) { // TODO: add expression parameter
		// TODO: store deltas of vsum in vave and map them to expression
		for (VitruviusChange change : virtualModel.getDeltas()) {
			System.out.println("DELTA: " + change);
			DeltaModule dm = VavemodelFactory.eINSTANCE.createDeltaModule();
			dm.setDelta(change);
			this.system.getDeltamodule().add(dm);
		}
		virtualModel.clearDeltas();
	}

}
