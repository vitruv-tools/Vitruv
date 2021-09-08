package tools.vitruv.variability.vave.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.propagation.ChangePropagationSpecification;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestLogging;
import tools.vitruv.testutils.TestProject;
import tools.vitruv.testutils.TestProjectManager;
import tools.vitruv.testutils.domains.AllElementTypesDomain;
import tools.vitruv.testutils.domains.AllElementTypesDomainProvider;
import tools.vitruv.variability.vave.VirtualVaVeModel;
import tools.vitruv.variability.vave.impl.FeatureModel;
import tools.vitruv.variability.vave.impl.VirtualVaVeModeIImpl;
import tools.vitruv.variability.vave.tests.VaveTest.RedundancyChangePropagationSpecification;
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

		VirtualVaVeModel vave = new VirtualVaVeModeIImpl(domains, changePropagationSpecifications, projectFolder);
		return vave;
	}
	
	@Test
	public void InternalizeFirstFeatureModelTest(@TestProject final Path projectFolder) throws Exception {
		FeatureModel fm = new FeatureModel(null, null, new HashSet<FeatureOption>(), new HashSet<TreeConstraint>(), new HashSet<CrossTreeConstraint>());
		
		// Feature a, Feature b, Feature c
		Feature featureA = VavemodelFactory.eINSTANCE.createFeature();
		featureA.setName("featureA");
		Feature featureB = VavemodelFactory.eINSTANCE.createFeature();
		featureB.setName("featureB");
		Feature featureC = VavemodelFactory.eINSTANCE.createFeature();
		featureC.setName("featureC");
		
		fm.getFeatures().add(featureA);
		fm.getFeatures().add(featureB);
		fm.getFeatures().add(featureC);
		
		TreeConstraint treeCstr = VavemodelFactory.eINSTANCE.createTreeConstraint();
		treeCstr.setType(GroupType.OR);
		treeCstr.getFeature().add(featureB);
		treeCstr.getFeature().add(featureC);
		featureA.getTreeconstraint().add(treeCstr);
		fm.setRootFeature(featureA);
		
		VirtualVaVeModel vave = setupVave(projectFolder);
		vave.internalizeDomain(fm);
		
//		assertEquals(1, vave.getSystem().getSystemrevision().size());
//		assertEquals(1, vave.getSystem().getSystemrevision().get(0).getEnablesconstraints().size());
	}

}
