package tools.vitruv.framework.tests.vsum;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import pcm_mockup.Component;
import pcm_mockup.PInterface;
import pcm_mockup.Pcm_mockupFactory;
import pcm_mockup.Pcm_mockupPackage;
import pcm_mockup.Repository;
import tools.vitruv.framework.domains.AbstractVitruvDomain;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.domains.VitruviusProjectBuilderApplicator;
import tools.vitruv.framework.tests.TestUserInteractor;
import tools.vitruv.framework.tests.VitruviusTest;
import tools.vitruv.framework.tests.util.TestUtil;
import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver;
import tools.vitruv.framework.util.bridges.EMFBridge;
import tools.vitruv.framework.util.datatypes.ModelInstance;
import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.framework.vsum.InternalVirtualModel;
import uml_mockup.UClass;
import uml_mockup.UPackage;
import uml_mockup.Uml_mockupFactory;
import uml_mockup.Uml_mockupPackage;

public abstract class VsumTest extends VitruviusTest {
    protected static final String PROJECT_FOLDER_NAME = "MockupProject";
    protected static final String VSUM_NAME = "VsumProject";

    protected static final String PCM_MM_URI = Pcm_mockupPackage.eNS_URI;
    protected static final String UML_MM_URI = Uml_mockupPackage.eNS_URI;
    protected static final String PCM_FILE_EXT = "pcm_mockup";
    protected static final String UML_FILE_EXT = "uml_mockup";

    public String getCurrentProjectFolderName() {
        return getCurrentTestProjectFolder().getName();
    }

    private static final VitruvDomain UmlDomain = new AbstractVitruvDomain("UML", Uml_mockupPackage.eINSTANCE,
            new AttributeTuidCalculatorAndResolver(Uml_mockupPackage.eINSTANCE.getNsURI(), "id"), UML_FILE_EXT) {
        @Override
        public VitruviusProjectBuilderApplicator getBuilderApplicator() {
            return null;
        };
    };

    private static final VitruvDomain PcmDomain = new AbstractVitruvDomain("PCM", Pcm_mockupPackage.eINSTANCE,
            new AttributeTuidCalculatorAndResolver(Pcm_mockupPackage.eINSTANCE.getNsURI(), "id"), PCM_FILE_EXT) {
        @Override
        public VitruviusProjectBuilderApplicator getBuilderApplicator() {
            return null;
        };
    };

    protected File getCurrentProjectModelFolder() {
        return new File(getCurrentTestProjectFolder(), "model");
    }

    protected URI getDefaultPcmInstanceURI() {
        return EMFBridge.getEmfFileUriForFile(new File(getCurrentProjectModelFolder(), "My.pcm_mockup"));
    }

    protected URI getDefaultUMLInstanceURI() {
        return EMFBridge.getEmfFileUriForFile(new File(getCurrentProjectModelFolder(), "My.uml_mockup"));
    }

    protected URI getAlternativePcmInstanceURI() {
        return EMFBridge.getEmfFileUriForFile(new File(getCurrentProjectModelFolder(), "NewPCMInstance.pcm_mockup"));
    }

    protected URI getAlterantiveUMLInstanceURI() {
        return EMFBridge.getEmfFileUriForFile(new File(getCurrentProjectModelFolder(), "NewUMLInstance.uml_mockup"));
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
        List<VitruvDomain> vitruvDomains = new ArrayList<VitruvDomain>();
        vitruvDomains.add(UmlDomain);
        vitruvDomains.add(PcmDomain);
        return TestUtil.createVirtualModel(vsumName, true, vitruvDomains, Collections.emptyList(),
                new TestUserInteractor());
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
        vsum.executeCommand(new Callable<Void>() {
            @Override
            public Void call() {
                Repository repo = Pcm_mockupFactory.eINSTANCE.createRepository();
                repo.getInterfaces().add(Pcm_mockupFactory.eINSTANCE.createPInterface());
                repo.getComponents().add(Pcm_mockupFactory.eINSTANCE.createComponent());
                contents.add(repo);
                return null;
            }
        });
        vsum.save();// (modelURI);
    }

    private void createUmlMockupModel(final VURI modelURI, final InternalVirtualModel vsum) {
        ModelInstance model = vsum.getModelInstance(modelURI);
        final EList<EObject> contents = model.getResource().getContents();
        vsum.executeCommand(new Callable<Void>() {
            @Override
            public Void call() {
                UPackage pckg = Uml_mockupFactory.eINSTANCE.createUPackage();
                pckg.getInterfaces().add(Uml_mockupFactory.eINSTANCE.createUInterface());
                pckg.getClasses().add(Uml_mockupFactory.eINSTANCE.createUClass());
                contents.add(pckg);
                return null;
            }
        });
        vsum.save();// (modelURI);
    }

}
