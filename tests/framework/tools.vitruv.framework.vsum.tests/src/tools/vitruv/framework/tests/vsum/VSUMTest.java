package tools.vitruv.framework.tests.vsum;

import java.util.concurrent.Callable;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import pcm_mockup.Component;
import pcm_mockup.PInterface;
import pcm_mockup.Pcm_mockupFactory;
import pcm_mockup.Repository;
import tools.vitruv.framework.util.datatypes.ModelInstance;
import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.framework.vsum.InternalVirtualModel;
import uml_mockup.UClass;
import uml_mockup.UPackage;
import uml_mockup.Uml_mockupFactory;

public abstract class VSUMTest extends AbstractVSUMTest {
    protected String getCurrentProjectModelFolder() {
        return getCurrentProjectFolderName() + "/model/";
    }

    protected String getDefaultPcmInstanceURI() {
        return getCurrentProjectModelFolder() + "My.pcm_mockup";
    }

    protected String getDefaultUMLInstanceURI() {
        return getCurrentProjectModelFolder() + "My.uml_mockup";
    }

    protected String getAlternativePcmInstanceURI() {
        return getCurrentProjectModelFolder() + "NewPCMInstance.pcm_mockup";
    }

    protected String getAlterantiveUMLInstanceURI() {
        return getCurrentProjectModelFolder() + "NewUMLInstance.uml_mockup";
    }

    protected ModelInstance fillVSUM(final InternalVirtualModel vsum) {
        // create PCM
        VURI vuri = VURI.getInstance(getAlternativePcmInstanceURI());
        ModelInstance mi = vsum.getModelInstance(vuri);
        final Repository repo = Pcm_mockupFactory.eINSTANCE.createRepository();
        vsum.createModel(vuri, repo);
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
        vsum.createModel(vuriUML, uPackage);
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

    protected InternalVirtualModel createMetaRepositoryVirtualModelAndModelInstances(final String pcmModelUriString,
            final String umlModelUriString) {
        InternalVirtualModel vsum = createMetaRepositoryAndVSUM();
        createMockupModels(pcmModelUriString, umlModelUriString, vsum);
        return vsum;
    }

    protected InternalVirtualModel createMetaRepositoryVSUMAndModelInstances() {
        InternalVirtualModel vsum = createMetaRepositoryAndVSUM();
        createMockupModelsWithDefaultUris(vsum);
        return vsum;
    }

    protected InternalVirtualModel createMetaRepositoryAndVSUM() {
        return createMetaRepositoryAndVSUM(PCM_MM_URI, PCM_FILE_EXT, UML_MM_URI, UML_FILE_EXT);
    }

    private void createMockupModelsWithDefaultUris(final InternalVirtualModel vsum) {
        createMockupModels(getDefaultPcmInstanceURI(), getDefaultUMLInstanceURI(), vsum);
    }

    protected void createMockupModels(final String pcmModelUriString, final String umlModelUriString,
            final InternalVirtualModel vsum) {
        createPcmMockupModel(pcmModelUriString, vsum);
        createUmlMockupModel(umlModelUriString, vsum);
    }

    private void createPcmMockupModel(final String modelURIString, final InternalVirtualModel vsum) {
        VURI modelURI = VURI.getInstance(modelURIString);
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

    private void createUmlMockupModel(final String modelURIString, final InternalVirtualModel vsum) {
        VURI modelURI = VURI.getInstance(modelURIString);
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
