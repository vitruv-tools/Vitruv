package edu.kit.ipd.sdq.vitruvius.tests.components;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;
import pcm_mockup.Component;
import pcm_mockup.Interface;
import pcm_mockup.Pcm_mockupFactory;
import pcm_mockup.Repository;
import uml_mockup.UClass;
import uml_mockup.UPackage;
import uml_mockup.Uml_mockupFactory;

public class VSUMTest extends AbstractVSUMTest {
    protected String getCurrentProjectModelFolder() {
        return getCurrentProjectFolderName() + "/model/";
    }

    protected String getPCMInstanceUri() {
        return getCurrentProjectModelFolder() + "My.pcm_mockup";
    }

    protected String getUMLInstanceURI() {
        return getCurrentProjectModelFolder() + "My.uml_mockup";
    }

    private String getPCMInstanceToCreateURI() {
        return getCurrentProjectModelFolder() + "NewPCMInstance.pcm_mockup";
    }

    private String getUMLInstanceToCreateURI() {
        return getCurrentProjectModelFolder() + "NewUMLInstance.uml_mockup";
    }

    @Override
    @Test
    public void testAll() {
        testMetaRepositoryVSUMAndModelInstancesCreation();
    }

    @Test
    public void testVSUMAddGetChangeAndSaveModel() {
        // create VSUM
        VSUMImpl vsum = this.testMetaRepositoryAndVSUMCreation();

        // create test model
        VURI vuri = VURI.getInstance(getPCMInstanceToCreateURI());
        ModelInstance mi = vsum.getAndLoadModelInstanceOriginal(vuri);
        final Repository repo = Pcm_mockupFactory.eINSTANCE.createRepository();
        vsum.saveModelInstanceOriginalWithEObjectAsOnlyContent(vuri, repo, null);
        final Component component = Pcm_mockupFactory.eINSTANCE.createComponent();
        TestUtil.createAndExecuteVitruviusRecordingCommand(new Runnable() {
            @Override
            public void run() {
                repo.getComponents().add(component);
            }
        }, vsum);

        // save test model
        vsum.saveExistingModelInstanceOriginal(vuri);

        // this is fine, the component is contained in the resource
        assertTrue("Resource of component is null", null != component.eResource());
        // causes a unload and a load of the model
        mi = vsum.getAndLoadModelInstanceOriginal(vuri);

        // not fine anymore: component is not contained in the resource and it is a proxy
        boolean isProxy = component.eIsProxy();
        Resource resource = component.eResource();
        // resolve the eObject in the new resource
        Component resolvedComponent = (Component) EcoreUtil.resolve(component, mi.getResource());
        isProxy = resolvedComponent.eIsProxy();
        resource = resolvedComponent.eResource();
        assertTrue("Resource of component is null", null != resource);
        assertTrue("Component is a proxy", !isProxy);

        assertTrue("Resource of model instance is null", null != mi.getResource());
    }

    @Test
    public void testVUMResourceIsChangedExternally() throws IOException {
        // same as above
        VSUMImpl vsum = this.testMetaRepositoryAndVSUMCreation();

        // create test model
        VURI vuri = VURI.getInstance(getPCMInstanceToCreateURI());
        ModelInstance mi = vsum.getAndLoadModelInstanceOriginal(vuri);
        Repository repo = Pcm_mockupFactory.eINSTANCE.createRepository();
        mi.getResource().getContents().clear();
        mi.getResource().getContents().add(repo);
        Component component = Pcm_mockupFactory.eINSTANCE.createComponent();
        repo.getComponents().add(component);
        vsum.saveExistingModelInstanceOriginal(vuri);
        // simulate external change
        changeTestModelExternally(vuri);

        // the interface should not be in the model instance (before the reload)
        Interface foundMockInterface = findInterfaceInModelInstance(mi);
        assertTrue("interface should not be in the model instance with uri: " + vuri.getEMFUri() + " and resource: "
                + mi.getResource(), null == foundMockInterface);

        // the interface should be in the model now.
        mi = vsum.getAndLoadModelInstanceOriginal(vuri);
        foundMockInterface = findInterfaceInModelInstance(mi);

        assertTrue("no interface found in the model instance with uri: " + vuri.getEMFUri() + " and resource: "
                + mi.getResource(), null != foundMockInterface);
    }

    @Test
    public void testLoadVSUMRepeadly() {
        VSUMImpl vsum = this.testMetaRepositoryAndVSUMCreation();

        ModelInstance mi = fillVSUM(vsum);

        ModelInstance interfaceMi = vsum.getAndLoadModelInstanceOriginal(mi.getURI());
        Interface foundInterface = findInterfaceInModelInstance(interfaceMi);
        assertTrue("The interface in " + foundInterface + " in the model instance: " + mi + " has no resource",
                null != foundInterface.eResource());

        ModelInstance compMi = vsum.getAndLoadModelInstanceOriginal(mi.getURI());
        Component foundComponent = findComponentInModelInstance(compMi);

        assertTrue("The component " + foundComponent + " in the model instance: " + mi + " has no resource",
                null != foundComponent.eResource());
        assertTrue("The interface " + foundInterface + " in the model instance: " + mi + " has no resource",
                null != foundInterface.eResource());

    }

    protected ModelInstance fillVSUM(final VSUMImpl vsum) {
        // create PCM
        VURI vuri = VURI.getInstance(getPCMInstanceToCreateURI());
        ModelInstance mi = vsum.getAndLoadModelInstanceOriginal(vuri);
        final Repository repo = Pcm_mockupFactory.eINSTANCE.createRepository();
        vsum.saveModelInstanceOriginalWithEObjectAsOnlyContent(vuri, repo, null);
        TestUtil.createAndExecuteVitruviusRecordingCommand(new Runnable() {
            @Override
            public void run() {
                Component component = Pcm_mockupFactory.eINSTANCE.createComponent();
                repo.getComponents().add(component);
            }
        }, vsum);
        vsum.saveExistingModelInstanceOriginal(vuri);
        TestUtil.createAndExecuteVitruviusRecordingCommand(new Runnable() {
            @Override
            public void run() {
                Interface mockIf = Pcm_mockupFactory.eINSTANCE.createInterface();
                repo.getInterfaces().add(mockIf);
            }
        }, vsum);
        vsum.saveExistingModelInstanceOriginal(vuri);

        // create UML
        VURI vuriUML = VURI.getInstance(getUMLInstanceToCreateURI());
        ModelInstance umlMi = vsum.getAndLoadModelInstanceOriginal(vuriUML);
        final UPackage uPackage = Uml_mockupFactory.eINSTANCE.createUPackage();
        vsum.saveModelInstanceOriginalWithEObjectAsOnlyContent(vuriUML, uPackage, null);
        TestUtil.createAndExecuteVitruviusRecordingCommand(new Runnable() {
            @Override
            public void run() {
                UClass uClass = Uml_mockupFactory.eINSTANCE.createUClass();
                uPackage.getClasses().add(uClass);
                uml_mockup.Interface uInterface = Uml_mockupFactory.eINSTANCE.createInterface();
                uPackage.getInterfaces().add(uInterface);
            }
        }, vsum);
        vsum.saveExistingModelInstanceOriginal(vuriUML);

        return mi;
    }

    private Component findComponentInModelInstance(final ModelInstance compMi) {
        return findEObjectInModelInstance(compMi, Component.class);
    }

    private Interface findInterfaceInModelInstance(final ModelInstance mi) {
        return findEObjectInModelInstance(mi, Interface.class);
    }

    private <T> T findEObjectInModelInstance(final ModelInstance mi, final Class<T> classToLookFor) {
        Iterator<EObject> iterator = mi.getResource().getAllContents();
        while (iterator.hasNext()) {
            EObject eObject = iterator.next();
            if (classToLookFor.isInstance(eObject)) {
                return classToLookFor.cast(eObject);
            }
        }
        return null;
    }

    private void changeTestModelExternally(final VURI vuri) throws IOException {
        // simulate external change. For example a change that is done by a user.
        // in this case we add an interface to the resource.
        ResourceSet extResourceSet = new ResourceSetImpl();
        Resource extRes = extResourceSet.createResource(vuri.getEMFUri());
        extRes.load(Collections.EMPTY_MAP);
        Interface mockIf = Pcm_mockupFactory.eINSTANCE.createInterface();
        Repository repo = (Repository) extRes.getContents().get(0);
        repo.getInterfaces().add(mockIf);
        extRes.save(Collections.EMPTY_MAP);
    }

    protected VSUMImpl testMetaRepositoryVSUMAndModelInstancesCreation() {
        VSUMImpl vsum = testMetaRepositoryAndVSUMCreation();
        testInstanceCreation(vsum);
        return vsum;
    }

    protected VSUMImpl testMetaRepositoryAndVSUMCreation() {
        return testMetaRepositoryAndVSUMCreation(PCM_MM_URI, PCM_FILE_EXT, UML_MM_URI, UML_FILE_EXT);
    }

    protected void testInstanceCreation(final VSUMImpl vsum) {
        testInstanceCreation(getPCMInstanceUri(), getUMLInstanceURI(), vsum);
    }

    private void testInstanceCreation(final String model1URIString, final String model2URIString, final VSUMImpl vsum) {
        VURI model1URI = VURI.getInstance(model1URIString);
        VURI model2URI = VURI.getInstance(model2URIString);
        ModelInstance model1 = vsum.getAndLoadModelInstanceOriginal(model1URI);
        ModelInstance model2 = vsum.getAndLoadModelInstanceOriginal(model2URI);
        EList<EObject> contents1 = model1.getResource().getContents();
        assertNotNull(contents1);
        EObject root1 = contents1.get(0);
        assertNotNull(root1);
        EList<EObject> contents2 = model2.getResource().getContents();
        assertNotNull(contents2);
        EObject root2 = contents2.get(0);
        assertNotNull(root2);
    }
}
