package tools.vitruv.variability.vave.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.propagation.ChangePropagationSpecification;
import tools.vitruv.framework.userinteraction.UserInteractionFactory;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestLogging;
import tools.vitruv.testutils.TestProject;
import tools.vitruv.testutils.TestProjectManager;
import tools.vitruv.testutils.domains.AllElementTypesDomain;
import tools.vitruv.testutils.domains.AllElementTypesDomainProvider;
import tools.vitruv.variability.vave.VirtualVaVeModel;
import tools.vitruv.variability.vave.impl.VirtualVaVeModelImpl;
import tools.vitruv.variability.vave.tests.VaveTest.RedundancyChangePropagationSpecification;
import tools.vitruv.variability.vave.util.old.FeatureModel;
import vavemodel.CrossTreeConstraint;
import vavemodel.Feature;
import vavemodel.FeatureOption;
import vavemodel.GroupType;
import vavemodel.TreeConstraint;
import vavemodel.VavemodelFactory;

@ExtendWith({ TestProjectManager.class, TestLogging.class, RegisterMetamodelsInStandalone.class })
public class VaveDomainOperationsTest {

	private VirtualVaVeModel setupVave(final Path projectFolder) throws Exception {
		Set<VitruvDomain> domains = new HashSet<>();
		final AllElementTypesDomain aetDomain = new AllElementTypesDomainProvider().getDomain();
		domains.add(aetDomain);

		Set<ChangePropagationSpecification> changePropagationSpecifications = new HashSet<>();
		RedundancyChangePropagationSpecification _redundancyChangePropagationSpecification = new RedundancyChangePropagationSpecification(aetDomain, aetDomain);
		changePropagationSpecifications.add(_redundancyChangePropagationSpecification);

		VirtualVaVeModel vave = new VirtualVaVeModelImpl(domains, changePropagationSpecifications, UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null), projectFolder);
		return vave;
	}

	private FeatureModel setupFM() {
		FeatureModel fm = new FeatureModel(null, null, new HashSet<FeatureOption>(), new HashSet<TreeConstraint>(), new HashSet<CrossTreeConstraint>());

		// Feature a, Feature b, Feature c
		Feature featureA = VavemodelFactory.eINSTANCE.createFeature();
		featureA.setName("featureA");
		Feature featureB = VavemodelFactory.eINSTANCE.createFeature();
		featureB.setName("featureB");
		Feature featureC = VavemodelFactory.eINSTANCE.createFeature();
		featureC.setName("featureC");

		fm.getFeatureOptions().add(featureA);
		fm.getFeatureOptions().add(featureB);
		fm.getFeatureOptions().add(featureC);

		TreeConstraint treeCstr = VavemodelFactory.eINSTANCE.createTreeConstraint();
		treeCstr.setType(GroupType.OR);
		treeCstr.getFeature().add(featureB);
		treeCstr.getFeature().add(featureC);
		featureA.getTreeconstraint().add(treeCstr);
		fm.setRootFeature(featureA);
		fm.getTreeConstraints().add(treeCstr);
		return fm;
	}

	@Test
	public void InternalizeFirstFeatureModelTest(@TestProject final Path projectFolder) throws Exception {
		FeatureModel fm = setupFM();
		VirtualVaVeModel vave = setupVave(projectFolder);
		vave.internalizeDomain(fm);

		assertEquals(1, vave.getSystem().getSystemrevision().size());
		assertEquals(1, vave.getSystem().getSystemrevision().get(0).getEnablesconstraints().size());
		assertEquals(3, vave.getSystem().getSystemrevision().get(0).getEnablesoptions().size());
	}

	@Test
	public void InternalizeFeatureModelSystemRevisionsTest(@TestProject final Path projectFolder) throws Exception {
		FeatureModel fm = setupFM();
		VirtualVaVeModel vave = setupVave(projectFolder);
		vave.internalizeDomain(fm);

		assertEquals(1, vave.getSystem().getSystemrevision().size());
		assertEquals(1, vave.getSystem().getSystemrevision().get(0).getEnablesconstraints().size());
		assertEquals(3, vave.getSystem().getSystemrevision().get(0).getEnablesoptions().size());

		// add new feature to or group
		fm = vave.externalizeDomain(vave.getSystem().getSystemrevision().get(0));

		Feature featureD = VavemodelFactory.eINSTANCE.createFeature();
		featureD.setName("featureD");

		fm.getFeatureOptions().add(featureD);
		fm.getTreeConstraints().iterator().next().getFeature().add(featureD);

		vave.internalizeDomain(fm);

		assertEquals(2, vave.getSystem().getSystemrevision().size());
		assertEquals(1, vave.getSystem().getSystemrevision().get(1).getEnablesconstraints().size());
		assertEquals(4, vave.getSystem().getSystemrevision().get(1).getEnablesoptions().size());
	}

	@Test
	public void InternalizeFeatureModelRootTest(@TestProject final Path projectFolder) throws Exception {
		FeatureModel fm = setupFM();
		VirtualVaVeModel vave = setupVave(projectFolder);
		vave.internalizeDomain(fm); // internalize changes of fm setup

		assertEquals(1, vave.getSystem().getSystemrevision().size());
		assertEquals(1, vave.getSystem().getSystemrevision().get(0).getEnablesconstraints().size());
		assertEquals(3, vave.getSystem().getSystemrevision().get(0).getEnablesoptions().size());
		assertEquals(3, vave.getSystem().getFeature().size());
		assertEquals(1, vave.getSystem().getFeature().stream().filter(p -> p.getName().equals("featureA")).findAny().get().eContents().size());
		assertEquals(2, vave.getSystem().getFeature().stream().filter(p -> p.getName().equals("featureA")).findAny().get().getTreeconstraint().get(0).getFeature().size());

		// add new feature to or group
		fm = vave.externalizeDomain(vave.getSystem().getSystemrevision().get(0));

		assertEquals(3, fm.getFeatureOptions().size());
		assertEquals(1, fm.getTreeConstraints().size());

		Feature featureD = VavemodelFactory.eINSTANCE.createFeature();
		featureD.setName("featureD");

		fm.getFeatureOptions().add(featureD);
		fm.getTreeConstraints().iterator().next().getFeature().add(featureD);
		EList<Feature> test = fm.getTreeConstraints().iterator().next().getFeature();

		vave.internalizeDomain(fm);

		assertEquals(2, vave.getSystem().getSystemrevision().size());
		assertEquals(1, vave.getSystem().getSystemrevision().get(1).getEnablesconstraints().size());
		assertEquals(4, vave.getSystem().getSystemrevision().get(1).getEnablesoptions().size());
		assertEquals(4, vave.getSystem().getFeature().size());
		assertEquals(2, vave.getSystem().getFeature().stream().filter(p -> p.getName().equals("featureA")).findAny().get().eContents().size()); // this should return 2 as they are now two cross-tree constraints
		assertEquals(3, vave.getSystem().getFeature().stream().filter(p -> p.getName().equals("featureA")).findAny().get().getTreeconstraint().get(1).getFeature().size());

		// change root feature
		fm = vave.externalizeDomain(vave.getSystem().getSystemrevision().get(1));
		Feature featureE = VavemodelFactory.eINSTANCE.createFeature();
		featureE.setName("featureE");
		fm.setRootFeature(featureE);
		// fm.getFeatureOptions().remove(featureA);
		vave.internalizeDomain(fm);

		assertEquals(3, vave.getSystem().getSystemrevision().size());
		assertEquals(1, vave.getSystem().getSystemrevision().get(1).getEnablesconstraints().size());
		assertEquals(4, vave.getSystem().getSystemrevision().get(1).getEnablesoptions().size());
	}

}
