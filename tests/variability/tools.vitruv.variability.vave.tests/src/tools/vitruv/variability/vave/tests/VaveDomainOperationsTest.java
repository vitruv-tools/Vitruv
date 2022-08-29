package tools.vitruv.variability.vave.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

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
import tools.vitruv.variability.vave.model.featuremodel.FeatureModel;
import tools.vitruv.variability.vave.model.featuremodel.FeaturemodelFactory;
import tools.vitruv.variability.vave.model.featuremodel.ViewFeature;
import tools.vitruv.variability.vave.model.featuremodel.ViewTreeConstraint;
import tools.vitruv.variability.vave.model.vave.GroupType;
import tools.vitruv.variability.vave.tests.VaveTest.RedundancyChangePropagationSpecification;
import tools.vitruv.variability.vave.util.FeatureModelUtil;

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
		FeatureModel fm = FeaturemodelFactory.eINSTANCE.createFeatureModel();

		// Feature a, Feature b, Feature c
		ViewFeature featureA = FeaturemodelFactory.eINSTANCE.createViewFeature();
		featureA.setName("featureA");
		ViewFeature featureB = FeaturemodelFactory.eINSTANCE.createViewFeature();
		featureB.setName("featureB");
		ViewFeature featureC = FeaturemodelFactory.eINSTANCE.createViewFeature();
		featureC.setName("featureC");

		ViewTreeConstraint treeCstr = FeaturemodelFactory.eINSTANCE.createViewTreeConstraint();
		treeCstr.setType(GroupType.OR);
		treeCstr.getChildFeatures().add(featureB);
		treeCstr.getChildFeatures().add(featureC);
		featureA.getChildTreeConstraints().add(treeCstr);
		fm.getRootFeatures().add(featureA);

		return fm;
	}

	@Test
	public void InternalizeFirstFeatureModelTest(@TestProject final Path projectFolder) throws Exception {
		FeatureModel fm = setupFM();
		VirtualVaVeModel vave = setupVave(projectFolder);
		vave.internalizeDomain(fm);

		assertEquals(1, vave.getSystem().getSystemRevisions().size());
		assertEquals(1, vave.getSystem().getSystemRevisions().get(0).getEnablesConstraints().size());
		assertEquals(3, vave.getSystem().getSystemRevisions().get(0).getEnablesFeatureOptions().size());
	}

	@Test
	public void InternalizeFeatureModelSystemRevisionsTest(@TestProject final Path projectFolder) throws Exception {
		FeatureModel fm = setupFM();
		VirtualVaVeModel vave = setupVave(projectFolder);
		vave.internalizeDomain(fm);

		assertEquals(1, vave.getSystem().getSystemRevisions().size());
		assertEquals(1, vave.getSystem().getSystemRevisions().get(0).getEnablesConstraints().size());
		assertEquals(3, vave.getSystem().getSystemRevisions().get(0).getEnablesFeatureOptions().size());

		// add new feature to or group
		fm = vave.externalizeDomain(vave.getSystem().getSystemRevisions().get(0)).getResult();

		ViewFeature featureD = FeaturemodelFactory.eINSTANCE.createViewFeature();
		featureD.setName("featureD");

		fm.getRootFeatures().get(0).getChildTreeConstraints().get(0).getChildFeatures().add(featureD);

		vave.internalizeDomain(fm);

		assertEquals(2, vave.getSystem().getSystemRevisions().size());
		assertEquals(1, vave.getSystem().getSystemRevisions().get(1).getEnablesConstraints().size());
		assertEquals(4, vave.getSystem().getSystemRevisions().get(1).getEnablesFeatureOptions().size());
	}

	@Test
	public void InternalizeFeatureModelRootTest(@TestProject final Path projectFolder) throws Exception {
		FeatureModel fm = setupFM();
		VirtualVaVeModel vave = setupVave(projectFolder);
		vave.internalizeDomain(fm); // internalize changes of fm setup

		assertEquals(1, vave.getSystem().getSystemRevisions().size());
		assertEquals(1, vave.getSystem().getSystemRevisions().get(0).getEnablesConstraints().size());
		assertEquals(3, vave.getSystem().getSystemRevisions().get(0).getEnablesFeatureOptions().size());
		assertEquals(3, vave.getSystem().getFeatures().size());
		assertEquals(1, vave.getSystem().getFeatures().stream().filter(p -> p.getName().equals("featureA")).findAny().get().getChildTreeConstraints().size());
		assertEquals(2, vave.getSystem().getFeatures().stream().filter(p -> p.getName().equals("featureA")).findAny().get().getChildTreeConstraints().get(0).getChildFeatures().size());

		// add new feature to or group
		fm = vave.externalizeDomain(vave.getSystem().getSystemRevisions().get(0)).getResult();

		assertEquals(3, FeatureModelUtil.collectFeatureOptions(fm).size());
		assertEquals(1, FeatureModelUtil.collectTreeConstraints(fm).size());

		ViewFeature featureD = FeaturemodelFactory.eINSTANCE.createViewFeature();
		featureD.setName("featureD");

		fm.getRootFeatures().get(0).getChildTreeConstraints().get(0).getChildFeatures().add(featureD);

		vave.internalizeDomain(fm);

		assertEquals(2, vave.getSystem().getSystemRevisions().size());
		assertEquals(1, vave.getSystem().getSystemRevisions().get(1).getEnablesConstraints().size());
		assertEquals(4, vave.getSystem().getSystemRevisions().get(1).getEnablesFeatureOptions().size());
		assertEquals(4, vave.getSystem().getFeatures().size());
		assertEquals(2, vave.getSystem().getFeatures().stream().filter(p -> p.getName().equals("featureA")).findAny().get().getChildTreeConstraints().size()); // this should return 2 as they are now two cross-tree constraints
		assertEquals(3, vave.getSystem().getFeatures().stream().filter(p -> p.getName().equals("featureA")).findAny().get().getChildTreeConstraints().get(1).getChildFeatures().size());

		// change root feature
		fm = vave.externalizeDomain(vave.getSystem().getSystemRevisions().get(1)).getResult();
		ViewFeature featureE = FeaturemodelFactory.eINSTANCE.createViewFeature();
		featureE.setName("featureE");
		ViewTreeConstraint viewTreeConstraint = FeaturemodelFactory.eINSTANCE.createViewTreeConstraint();
		viewTreeConstraint.setType(GroupType.OPTIONAL);
		viewTreeConstraint.getChildFeatures().add(fm.getRootFeatures().get(0));
		featureE.getChildTreeConstraints().add(viewTreeConstraint);
		fm.getRootFeatures().set(0, featureE);
		vave.internalizeDomain(fm);

		assertEquals(3, vave.getSystem().getSystemRevisions().size());
		assertEquals(1, vave.getSystem().getSystemRevisions().get(1).getEnablesConstraints().size());
		assertEquals(4, vave.getSystem().getSystemRevisions().get(1).getEnablesFeatureOptions().size());
	}

}
