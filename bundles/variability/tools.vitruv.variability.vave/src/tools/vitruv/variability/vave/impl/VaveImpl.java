package tools.vitruv.variability.vave.impl;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;

import tools.vitruv.framework.change.description.VitruviusChange;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.domains.repository.VitruvDomainRepository;
import tools.vitruv.framework.domains.repository.VitruvDomainRepositoryImpl;
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
	private final Set<ChangePropagationSpecification> changePropagationSpecifications = new HashSet<ChangePropagationSpecification>();

	public VaveImpl(Set<VitruvDomain> domains, Set<ChangePropagationSpecification> changePropagationSpecifications) {
		this.system = VavemodelFactory.eINSTANCE.createSystem();

		this.domainRepository = new VitruvDomainRepositoryImpl(domains);

		this.changePropagationSpecifications.addAll(changePropagationSpecifications);
	}

	public VirtualModelProduct externalizeProduct(Path storageFolder, String configuration) throws Exception {

//		final VirtualModelProductImpl vsum = new VirtualModelProductBuilder().withStorageFolder(storageFolder)
//				.withDomainRepository(this.domainRepository)
//				.withUserInteractor(UserInteractionFactory.instance.createUserInteractor(UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null)))
//				.buildAndInitialize();

//		final Set<ChangePropagationSpecification> changePropagationSpecifications = new HashSet<ChangePropagationSpecification>();
		InternalUserInteractor userInteractor = UserInteractionFactory.instance
				.createUserInteractor(UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null));

		if (storageFolder == null)
			throw new Exception("No storage folder was configured!");

		if (userInteractor == null)
			throw new Exception("No user interactor was configured!");

		final ChangePropagationSpecificationRepository changeSpecificationRepository = new ChangePropagationSpecificationRepository(
				changePropagationSpecifications);
		for (final ChangePropagationSpecification changePropagationSpecification : changePropagationSpecifications) {
			{
				boolean containsSourceDomain = false;
				boolean containsTargetDomain = false;
				for (VitruvDomain domain : this.domainRepository) {
					if (domain.equals(changePropagationSpecification.getSourceDomain())) {
						containsSourceDomain = true;
					}
					if (domain.equals(changePropagationSpecification.getTargetDomain())) {
						containsTargetDomain = true;
					}
				}
				if (!containsTargetDomain)
					throw new Exception("The change propagation specification’s source domain ‹"
							+ changePropagationSpecification.getSourceDomain() + "› has not been configured: "
							+ changePropagationSpecification);
				if (!containsSourceDomain)
					throw new Exception("The change propagation specification’s target domain ‹"
							+ changePropagationSpecification.getTargetDomain() + "› has not been configured: "
							+ changePropagationSpecification);
			}
		}

		for (final ChangePropagationSpecification changePropagationSpecification_1 : this.changePropagationSpecifications) {
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
