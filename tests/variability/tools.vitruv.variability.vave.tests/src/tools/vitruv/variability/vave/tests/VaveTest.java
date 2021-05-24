package tools.vitruv.variability.vave.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import allElementTypes.Root;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil;
import tools.vitruv.framework.change.description.TransactionalChange;
import tools.vitruv.framework.change.recording.ChangeRecorder;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.domains.repository.VitruvDomainRepositoryImpl;
import tools.vitruv.framework.vsum.internal.ModelInstance;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestLogging;
import tools.vitruv.testutils.TestProject;
import tools.vitruv.testutils.TestProjectManager;
import tools.vitruv.testutils.domains.AllElementTypesDomainProvider;
import tools.vitruv.testutils.matchers.ModelMatchers;
import tools.vitruv.testutils.metamodels.AllElementTypesCreators;
import tools.vitruv.variability.vave.Vave;
import tools.vitruv.variability.vave.VirtualModelProduct;
import tools.vitruv.variability.vave.impl.VaveImpl;

@ExtendWith({ TestProjectManager.class, TestLogging.class, RegisterMetamodelsInStandalone.class })
public class VaveTest {

	private URI createTestModelResourceUri(final String suffix, Path projectFolder) {
		return URI.createFileURI(projectFolder.resolve((("root" + suffix) + ".allElementTypes")).toString());
	}

	private Path projectFolder;

	@BeforeEach
	public void initializeProjectFolder(@TestProject final Path projectFolder) {
		this.projectFolder = projectFolder;
	}

	@Test
	public void testVSUMCreation() throws Exception {
		Set<VitruvDomain> domains = new HashSet<>();
		final VitruvDomainRepositoryImpl targetDomains = new VitruvDomainRepositoryImpl(domains);
		Vave vave = new VaveImpl(targetDomains);
		VirtualModelProduct vsum = vave.externalizeProduct(this.projectFolder, "");
		assertNotNull(vsum);
	}

	@Test
	public void testVSUMPropagation() throws Exception {
		Set<VitruvDomain> domains = new HashSet<>();
		domains.add(new AllElementTypesDomainProvider().getDomain());
		final VitruvDomainRepositoryImpl targetDomains = new VitruvDomainRepositoryImpl(domains);
		Vave vave = new VaveImpl(targetDomains);
		final VirtualModelProduct virtualModel = vave.externalizeProduct(this.projectFolder.resolve("vsum"), "");

		final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		final ChangeRecorder changeRecorder = new ChangeRecorder(resourceSet);
		changeRecorder.addToRecording(resourceSet);
		changeRecorder.beginRecording();
		Resource _createResource = resourceSet.createResource(this.createTestModelResourceUri("", this.projectFolder));
		final Procedure1<Resource> _function = (Resource it) -> {
			EList<EObject> _contents = it.getContents();
			Root _Root = AllElementTypesCreators.aet.Root();
			final Procedure1<Root> _function_1 = (Root it_1) -> {
				it_1.setId("root");
			};
			Root _doubleArrow = ObjectExtensions.<Root>operator_doubleArrow(_Root, _function_1);
			_contents.add(_doubleArrow);
		};
		final Resource monitoredResource = ObjectExtensions.<Resource>operator_doubleArrow(_createResource, _function);
		final TransactionalChange recordedChange = changeRecorder.endRecording();
		virtualModel.propagateChange(recordedChange);
		final ModelInstance vsumModel = virtualModel
				.getModelInstance(this.createTestModelResourceUri("", this.projectFolder));
		MatcherAssert.<Resource>assertThat(vsumModel.getResource(), ModelMatchers.containsModelOf(monitoredResource));

		vave.internalizeChanges(virtualModel);
	}

	@Test
	public void testVSUMPropagationAndConsistency() {

	}

}
