package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.repository;

import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import org.junit.Test;

import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import de.uka.ipd.sdq.pcm.repository.Repository;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.PCM2JaMoPPTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPUtils;

public class BasicComponentMappingTransformationTest extends PCM2JaMoPPTransformationTest {

    @Test
    public void testAddBasicComponent() throws Throwable {
    	final String RESOURCETYPE_URI = "platform:/plugin/de.uka.ipd.sdq.pcm.resources/defaultModels/Palladio.resourcetype";
    	final String PRIMITIVETYPES_URI = "platform:/plugin/de.uka.ipd.sdq.pcm.resources/defaultModels/PrimitiveTypes.repository";
    	saveResourceFromDefaultModelTo(RESOURCETYPE_URI, "Palladio.resourcetype", "resourcetype");
    	saveResourceFromDefaultModelTo(PRIMITIVETYPES_URI, "PrimitiveTypes.repository", "repository");
        
    	final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPUtils.REPOSITORY_NAME);

        final BasicComponent basicComponent = this.addBasicComponentAndSync(repo);

        this.assertBasicComponentCorrespondences(basicComponent);
    }

	private void saveResourceFromDefaultModelTo(final String RESOURCETYPE_URI, String name, String fileEnding) throws IOException {
		final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        final Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put(fileEnding, new XMIResourceFactoryImpl());

        final URI uri = URI.createURI(RESOURCETYPE_URI);

        final ResourceSet resSet = new ResourceSetImpl();
        final Resource resource = resSet.getResource(uri, true);
        VURI vuri = VURI.getInstance("MockupProject/" + name);
        EObject root = resource.getContents().get(0);
        Resource newRes = this.resourceSet.createResource(vuri.getEMFUri());
        EcoreResourceBridge.saveEObjectAsOnlyContent(root, newRes);
	}

    @Test
    public void testRenameBasicComponent() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPUtils.REPOSITORY_NAME);
        final BasicComponent basicComponent = this.addBasicComponentAndSync(repo);

        basicComponent.setEntityName(PCM2JaMoPPUtils.BASIC_COMPONENT_NAME + PCM2JaMoPPUtils.RENAME);
        this.triggerSynchronization(VURI.getInstance(repo.eResource()));

        this.assertBasicComponentCorrespondences(basicComponent);
    }

    @Test
    public void testDeleteBasicComponent() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPUtils.REPOSITORY_NAME);
        final BasicComponent basicComponent = this.addBasicComponentAndSync(repo);

        EcoreUtil.delete(basicComponent);
        super.triggerSynchronization(repo);

        this.assertEmptyCorrespondence(basicComponent);
    }

    @Test
    public void testAddTwoBasicComponentAndDeleteOne() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPUtils.REPOSITORY_NAME);
        final BasicComponent basicComponent = this.addBasicComponentAndSync(repo);
        final BasicComponent basicComponent2 = this.addBasicComponentAndSync(repo, "SecondBasicComponent");

        EcoreUtil.delete(basicComponent);
        super.triggerSynchronization(repo);

        this.assertEmptyCorrespondence(basicComponent);
        this.assertBasicComponentCorrespondences(basicComponent2);
    }

    @SuppressWarnings("unchecked")
    private void assertBasicComponentCorrespondences(final BasicComponent basicComponent) throws Throwable {
        this.assertCorrespondnecesAndCompareNames(
                basicComponent,
                3,
                new java.lang.Class[] { CompilationUnit.class, Package.class, Class.class },
                new String[] { basicComponent.getEntityName() + "Impl", basicComponent.getEntityName(),
                        basicComponent.getEntityName() + "Impl" });

    }

}
