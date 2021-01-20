package tools.vitruv.framework.tests.vsum;

import java.nio.file.Path;
import java.util.concurrent.Callable;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import pcm_mockup.Component;
import pcm_mockup.PInterface;
import pcm_mockup.Pcm_mockupFactory;
import pcm_mockup.Pcm_mockupPackage;
import pcm_mockup.Repository;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver;
import tools.vitruv.framework.userinteraction.UserInteractionFactory;
import tools.vitruv.framework.util.bridges.EMFBridge;
import tools.vitruv.framework.util.datatypes.ModelInstance;
import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.framework.vsum.InternalVirtualModel;
import tools.vitruv.framework.vsum.VirtualModelConfiguration;
import tools.vitruv.framework.vsum.VirtualModelImpl;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestLogging;
import tools.vitruv.testutils.TestProject;
import tools.vitruv.testutils.TestProjectManager;
import tools.vitruv.testutils.domains.ConcreteTuidAwareVitruvDomain;
import uml_mockup.UClass;
import uml_mockup.UPackage;
import uml_mockup.Uml_mockupFactory;
import uml_mockup.Uml_mockupPackage;

@ExtendWith({ TestProjectManager.class, TestLogging.class, RegisterMetamodelsInStandalone.class })
public abstract class VsumTest {
	protected static final String PROJECT_FOLDER_NAME = "MockupProject";
	protected static final String VSUM_NAME = "VsumProject";

	protected static final String PCM_MM_URI = Pcm_mockupPackage.eNS_URI;
	protected static final String UML_MM_URI = Uml_mockupPackage.eNS_URI;
	protected static final String PCM_FILE_EXT = "pcm_mockup";
	protected static final String UML_FILE_EXT = "uml_mockup";

	private Path testProjectFolder = null;

	@BeforeEach
	void acquireTestProjectFolder(@TestProject final Path testProjectFolder) {
		this.testProjectFolder = testProjectFolder;
	}

	private static final VitruvDomain UmlDomain = new ConcreteTuidAwareVitruvDomain("UML", Uml_mockupPackage.eINSTANCE,
		new AttributeTuidCalculatorAndResolver(Uml_mockupPackage.eINSTANCE.getNsURI(), "id"), UML_FILE_EXT);

	private static final VitruvDomain PcmDomain = new ConcreteTuidAwareVitruvDomain("PCM", Pcm_mockupPackage.eINSTANCE,
		new AttributeTuidCalculatorAndResolver(Pcm_mockupPackage.eINSTANCE.getNsURI(), "id"), PCM_FILE_EXT);

	protected Path getCurrentProjectFolder() {
		return this.testProjectFolder;
	}

	protected Path getCurrentProjectModelFolder() {
		return this.testProjectFolder.resolve("model");
	}

	protected URI getDefaultPcmInstanceURI() {
		return EMFBridge.getEmfFileUriForFile(getCurrentProjectModelFolder().resolve("My.pcm_mockup").toFile());
	}

	protected URI getDefaultUMLInstanceURI() {
		return EMFBridge.getEmfFileUriForFile(getCurrentProjectModelFolder().resolve("My.uml_mockup").toFile());
	}

	protected URI getAlternativePcmInstanceURI() {
		return EMFBridge
			.getEmfFileUriForFile(getCurrentProjectModelFolder().resolve("NewPCMInstance.pcm_mockup").toFile());
	}

	protected URI getAlterantiveUMLInstanceURI() {
		return EMFBridge
			.getEmfFileUriForFile(getCurrentProjectModelFolder().resolve("NewUMLInstance.uml_mockup").toFile());
	}

	protected ModelInstance fillVsum(final InternalVirtualModel vsum) {
		// create PCM
		VURI vuri = VURI.getInstance(getAlternativePcmInstanceURI());
		ModelInstance mi = vsum.getModelInstance(vuri);
		final Repository repo = Pcm_mockupFactory.eINSTANCE.createRepository();
		vsum.persistRootElement(vuri, repo);
		vsum.executeCommand(new Callable<Void>() {
			@Override
			public Void call() {
				Component component = Pcm_mockupFactory.eINSTANCE.createComponent();
				repo.getComponents().add(component);
				return null;
			}
		});
		vsum.save();// (vuri);
		vsum.executeCommand(new Callable<Void>() {
			@Override
			public Void call() {
				PInterface mockIf = Pcm_mockupFactory.eINSTANCE.createPInterface();
				repo.getInterfaces().add(mockIf);
				return null;
			}
		});
		vsum.save();// (vuri);

		// create UML
		VURI vuriUML = VURI.getInstance(getAlterantiveUMLInstanceURI());
		final UPackage uPackage = Uml_mockupFactory.eINSTANCE.createUPackage();
		vsum.persistRootElement(vuriUML, uPackage);
		vsum.executeCommand(new Callable<Void>() {
			@Override
			public Void call() {
				UClass uClass = Uml_mockupFactory.eINSTANCE.createUClass();
				uPackage.getClasses().add(uClass);
				uml_mockup.UInterface uInterface = Uml_mockupFactory.eINSTANCE.createUInterface();
				uPackage.getInterfaces().add(uInterface);
				return null;
			}
		});
		vsum.save();// (vuriUML);

		return mi;
	}

	protected InternalVirtualModel createAlternativeVirtualModelAndModelInstances(final URI pcmModelUri,
		final URI umlModelUri) {
		InternalVirtualModel vsum = createVirtualModel(VSUM_NAME + "2");
		createMockupModels(pcmModelUri, umlModelUri, vsum);
		return vsum;
	}

	protected InternalVirtualModel createVirtualModelAndModelInstances() {
		InternalVirtualModel vsum = createDefaultVirtualModel();
		createMockupModelsWithDefaultUris(vsum);
		return vsum;
	}

	protected InternalVirtualModel createDefaultVirtualModel() {
		return createVirtualModel(VSUM_NAME);
	}

	protected InternalVirtualModel createVirtualModel(final String vsumName) {
		var interactionProvider = UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null);
		var userInteractor = UserInteractionFactory.instance.createUserInteractor(interactionProvider);
		var configuration = new VirtualModelConfiguration();
		configuration.addMetamodel(UmlDomain);
		configuration.addMetamodel(PcmDomain);
		return new VirtualModelImpl(this.testProjectFolder.resolve(vsumName).toFile(), userInteractor, configuration);
	}

	private void createMockupModelsWithDefaultUris(final InternalVirtualModel vsum) {
		createMockupModels(getDefaultPcmInstanceURI(), getDefaultUMLInstanceURI(), vsum);
	}

	protected void createMockupModels(final URI pcmModelUri, final URI umlModelUri, final InternalVirtualModel vsum) {
		createPcmMockupModel(VURI.getInstance(pcmModelUri), vsum);
		createUmlMockupModel(VURI.getInstance(umlModelUri), vsum);
	}

	private void createPcmMockupModel(final VURI modelURI, final InternalVirtualModel vsum) {
		ModelInstance model = vsum.getModelInstance(modelURI);
		final EList<EObject> contents = model.getResource().getContents();
		vsum.executeCommand(() -> {
			Repository repo = Pcm_mockupFactory.eINSTANCE.createRepository();
			repo.getInterfaces().add(Pcm_mockupFactory.eINSTANCE.createPInterface());
			repo.getComponents().add(Pcm_mockupFactory.eINSTANCE.createComponent());
			contents.add(repo);
			return null;
		});
		vsum.save();// (modelURI);
	}

	private void createUmlMockupModel(final VURI modelURI, final InternalVirtualModel vsum) {
		ModelInstance model = vsum.getModelInstance(modelURI);
		final EList<EObject> contents = model.getResource().getContents();
		vsum.executeCommand(() -> {
			UPackage pckg = Uml_mockupFactory.eINSTANCE.createUPackage();
			pckg.getInterfaces().add(Uml_mockupFactory.eINSTANCE.createUInterface());
			pckg.getClasses().add(Uml_mockupFactory.eINSTANCE.createUClass());
			contents.add(pckg);
			return null;
		});
		vsum.save();// (modelURI);
	}

}
