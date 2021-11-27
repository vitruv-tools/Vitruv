package tools.vitruv.variability.vave.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import allElementTypes.Root;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil;
import tools.vitruv.framework.change.description.TransactionalChange;
import tools.vitruv.framework.change.recording.ChangeRecorder;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.userinteraction.UserInteractionFactory;
import tools.vitruv.framework.vsum.internal.ModelInstance;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestLogging;
import tools.vitruv.testutils.TestProject;
import tools.vitruv.testutils.TestProjectManager;
import tools.vitruv.testutils.domains.AllElementTypesDomainProvider;
import tools.vitruv.testutils.matchers.ModelMatchers;
import tools.vitruv.testutils.metamodels.AllElementTypesCreators;
import tools.vitruv.variability.vave.VirtualProductModel;
import tools.vitruv.variability.vave.VirtualVaVeModel;
import tools.vitruv.variability.vave.impl.VirtualVaVeModeIImpl;
import vavemodel.Configuration;
import vavemodel.FeatureOption;
import vavemodel.VavemodelFactory;

@ExtendWith({ TestProjectManager.class, TestLogging.class, RegisterMetamodelsInStandalone.class })
public class VaveMultiProductEditTest {

	Configuration config = VavemodelFactory.eINSTANCE.createConfiguration();

	private URI createTestModelResourceUri(final String suffix, Path projectFolder) {
		return URI.createFileURI(projectFolder.resolve((("root" + suffix) + ".allElementTypes")).toString());
	}

	@Test
	public void OneEditStep(@TestProject final Path projectFolder) throws Exception {
		// collect domains
		Set<VitruvDomain> domains = new HashSet<>();
		domains.add(new AllElementTypesDomainProvider().getDomain());
		// create vave system
		VirtualVaVeModel vave = new VirtualVaVeModeIImpl(domains, new HashSet<>(), UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null), projectFolder);

		// externalize virtual model products (vmp)
		final VirtualProductModel vmp1 = vave.externalizeProduct(projectFolder.resolve("vmp1"), config); // empty product

		// modify vmp1
		// record changes
		final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		final ChangeRecorder changeRecorder = new ChangeRecorder(resourceSet);
		changeRecorder.addToRecording(resourceSet);
		changeRecorder.beginRecording();
		Resource monitoredResource = resourceSet.createResource(this.createTestModelResourceUri("", projectFolder));
		Root root = AllElementTypesCreators.aet.Root();
		root.setId("root");
		monitoredResource.getContents().add(root);
		final TransactionalChange recordedChange = changeRecorder.endRecording();
		// propagate recorded changes into vmp1
		vmp1.propagateChange(recordedChange);
		final ModelInstance vsumModel1 = vmp1.getModelInstance(this.createTestModelResourceUri("", projectFolder));
		// assert that the modified view is equal to the model instance in vmp1 into which the changes to the view were propagated
		MatcherAssert.<Resource>assertThat(vsumModel1.getResource(), ModelMatchers.containsModelOf(monitoredResource));
	}

	// Products are indepedent over time (multiple system revisions)
	@Test
	public void MultipleEditAndInternalizationSteps(@TestProject final Path projectFolder) throws Exception {
//		Conjunction<FeatureOption> conjunction = VavemodelFactory.eINSTANCE.createConjunction();

		// collect domains
		Set<VitruvDomain> domains = new HashSet<>();
		domains.add(new AllElementTypesDomainProvider().getDomain());
		// create vave system
		VirtualVaVeModel vave = new VirtualVaVeModeIImpl(domains, new HashSet<>(), UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null), projectFolder);
//		System system = vave.getSystem();
//		vavemodel.Feature car = VavemodelFactory.eINSTANCE.createFeature();
//		vavemodel.Feature engineType = VavemodelFactory.eINSTANCE.createFeature();
//		system.getFeature().add(car);
//		system.getFeature().add(engineType);
//		vavemodel.Variable<FeatureOption> variable1 = VavemodelFactory.eINSTANCE.createVariable();
//		vavemodel.Variable<FeatureOption> variable2 = VavemodelFactory.eINSTANCE.createVariable();
//		variable1.setOption(car);
//		variable2.setOption(engineType);
//		conjunction.getTerm().add(variable1);
//		conjunction.getTerm().add(variable2);
		vavemodel.True<FeatureOption> trueConstant = VavemodelFactory.eINSTANCE.createTrue();

		// externalize virtual model product (vmp)
		final VirtualProductModel vmp1 = vave.externalizeProduct(projectFolder.resolve("vmp1"), config); // empty product
		Resource vmp1Resource = null;
		Resource vmp1copyResource = null;
		Resource vmp1extResource = null;

		// modify vmp1
		// record changes
		final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		final ChangeRecorder changeRecorder = new ChangeRecorder(resourceSet);
		changeRecorder.addToRecording(resourceSet);
		changeRecorder.beginRecording();
		// final Resource monitoredResource = resourceSet.createResource(this.createTestModelResourceUri("", projectFolder));
		vmp1copyResource = resourceSet.createResource(this.createTestModelResourceUri("", projectFolder));
		final Resource monitoredResource = vmp1copyResource;
		Root root = AllElementTypesCreators.aet.Root();
		root.setId("root");
		monitoredResource.getContents().add(root);
		final TransactionalChange recordedChange = changeRecorder.endRecording();
		// propagate recorded changes into vmp1
		vmp1.propagateChange(recordedChange);
		vmp1Resource = vmp1.getModelInstance(this.createTestModelResourceUri("", projectFolder)).getResource();
		// assert that the modified view is equal to the model instance in vmp1 into which the changes to the view were propagated
		Assert.assertEquals(vmp1Resource.getContents().size(), 1);
		Assert.assertEquals(vmp1copyResource.getContents().size(), 1);
		MatcherAssert.<Resource>assertThat(vmp1Resource, ModelMatchers.containsModelOf(monitoredResource));

		vave.internalizeChanges(vmp1, trueConstant); // system revision 1

		assertEquals(vave.getSystem().getSystemrevision().size(), 1);
		assertEquals(vave.getSystem().getSystemrevision().get(0).getRevisionID(), 1);

		config.getOption().add(vave.getSystem().getSystemrevision().get(0));

		final VirtualProductModel vmp1ext = vave.externalizeProduct(projectFolder.resolve("vmp1ext"), config);
		final ModelInstance vmp1extModelInstance = vmp1ext.getModelInstance(this.createTestModelResourceUri("", projectFolder));
		vmp1extResource = vmp1extModelInstance.getResource();
		Assert.assertEquals(vmp1extResource.getContents().size(), 1);
		MatcherAssert.<Resource>assertThat(vmp1extResource, ModelMatchers.containsModelOf(vmp1copyResource));

		final VirtualProductModel vmp1ext4 = vave.externalizeProduct(projectFolder.resolve("vmp1ext4"), config);
		final ModelInstance vmp1ext4ModelInstance = vmp1ext4.getModelInstance(this.createTestModelResourceUri("", projectFolder));
		Resource vmp1ext4Resource = vmp1ext4ModelInstance.getResource();
		Assert.assertEquals(vmp1extResource.getContents().size(), 1);
		MatcherAssert.<Resource>assertThat(vmp1extResource, ModelMatchers.containsModelOf(vmp1copyResource));

		// -------------------------------

		final ResourceSet resourceSet2 = vmp1ext.getModelInstance(this.createTestModelResourceUri("", projectFolder)).getResource().getResourceSet();
		final ChangeRecorder changeRecorder2 = new ChangeRecorder(resourceSet2);
		changeRecorder2.addToRecording(resourceSet2);
		changeRecorder2.beginRecording();
		final Resource monitoredResource2 = vmp1ext.getModelInstance(this.createTestModelResourceUri("", projectFolder)).getResource();
		Root root2 = AllElementTypesCreators.aet.Root();
		root2.setId("root2");
		monitoredResource2.getContents().add(root2);
		final TransactionalChange recordedChange2 = changeRecorder2.endRecording();
		// propagate recorded changes into vmp1
		Assert.assertEquals(vmp1Resource.getContents().size(), 1);
		Assert.assertEquals(vmp1extResource.getContents().size(), 2);
		Assert.assertEquals(monitoredResource2.getContents().size(), 2);
		Assert.assertEquals(vmp1ext4Resource.getContents().size(), 1);
		vmp1.propagateChange(recordedChange2);
		// assert that the modified view is equal to the model instance in vmp1 into which the changes to the view were propagated
		Assert.assertEquals(vmp1Resource.getContents().size(), 2);
		Assert.assertEquals(vmp1extResource.getContents().size(), 2);
		Assert.assertEquals(monitoredResource2.getContents().size(), 2);
		Assert.assertEquals(vmp1ext4Resource.getContents().size(), 1);
//		MatcherAssert.<Resource>assertThat(vmp1Resource, ModelMatchers.containsModelOf(monitoredResource2));

		// before internalization of changes the product must contain only one root
		final VirtualProductModel vmp1ext2 = vave.externalizeProduct(projectFolder.resolve("vmp1ext2"), config);
		final ModelInstance vmp1ext2ModelInstance = vmp1ext2.getModelInstance(this.createTestModelResourceUri("", projectFolder));
		Resource vmp1ext2Resource = vmp1ext2ModelInstance.getResource();
		Assert.assertEquals(vmp1ext2Resource.getContents().size(), 1);
		Assert.assertEquals(vmp1ext4Resource.getContents().size(), 1);
		MatcherAssert.<Resource>assertThat(vmp1ext2Resource, ModelMatchers.containsModelOf(vmp1copyResource));

		vave.internalizeChanges(vmp1, trueConstant); // system revision 2

		assertEquals(vave.getSystem().getSystemrevision().size(), 2);
		assertEquals(vave.getSystem().getSystemrevision().get(1).getRevisionID(), 2);

		config.getOption().clear();
		config.getOption().add(vave.getSystem().getSystemrevision().get(1));

		// after internalization of changes the product must contain two roots
		final VirtualProductModel vmp1ext3 = vave.externalizeProduct(projectFolder.resolve("vmp1ext3"), config);
		final ModelInstance vmp1ext3ModelInstance = vmp1ext3.getModelInstance(this.createTestModelResourceUri("", projectFolder));
		Resource vmp1ext3Resource = vmp1ext3ModelInstance.getResource();
		Assert.assertEquals(vmp1ext2Resource.getContents().size(), 1);
		Assert.assertEquals(2, vmp1ext3Resource.getContents().size());
		Assert.assertEquals(vmp1ext4Resource.getContents().size(), 1);
//		MatcherAssert.<Resource>assertThat(vmp1ext3Resource, ModelMatchers.containsModelOf(monitoredResource2));

		final ModelInstance vmp1ext4tempModelInstance = vmp1ext4.getModelInstance(this.createTestModelResourceUri("", projectFolder));
		Resource vmp1ext4tempResource = vmp1ext4tempModelInstance.getResource();
		Assert.assertEquals(vmp1ext4tempResource.getContents().size(), 1);
	}
}
