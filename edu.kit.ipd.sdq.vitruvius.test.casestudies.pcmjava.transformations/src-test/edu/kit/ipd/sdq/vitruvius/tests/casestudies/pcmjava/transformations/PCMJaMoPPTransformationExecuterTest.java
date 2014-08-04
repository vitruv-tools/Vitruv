package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.containers.CompilationUnit;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import de.uka.ipd.sdq.pcm.repository.Repository;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.PCMJaMoPPTransformationExecuter;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPUtils;

public class PCMJaMoPPTransformationExecuterTest extends JaMoPPPCMTransformationTest {

    private static final Logger logger = Logger.getLogger(PCMJaMoPPTransformationExecuterTest.class.getSimpleName());

    @BeforeClass
    public static void setUp() {
        JaMoPPPCMTransformationTest.setUp();
    }

    @Override
    @Before
    public void createNewCorrespondenceInstance() {
        super.createNewCorrespondenceInstance();
    }

    @Test
    public void testCreateNewResourceInPCM2JaMoPPTransformation() throws IOException {
        final PCMJaMoPPTransformationExecuter pcmJaMoPPTransformation = new PCMJaMoPPTransformationExecuter();
        final Repository repo = PCM2JaMoPPUtils.createAndSyncRepository(resourceSet, changeSynchronizer);
        final ModelInstance pcmModelInstance = PCM2JaMoPPUtils.createModelInstance(
                "MockupProject/test/testRepository.repository", resourceSet);
        final BasicComponent basicComponent = PCM2JaMoPPUtils.createBasicComponent(repo);
        final Change change = PCM2JaMoPPUtils.createCreateChange(basicComponent, repo, "components__Repository");

        final EMFChangeResult emfChangeResult = pcmJaMoPPTransformation.executeTransformation(change, pcmModelInstance,
                super.correspondenceInstance);
        logger.info("changed VURIs: " + emfChangeResult);

        PCM2JaMoPPUtils.saveEMFChangeResult(emfChangeResult, resourceSet);
        assertTrue("Existing VURIs to save should be 0", 0 == emfChangeResult.getExistingObjectsToSave().size());
        assertTrue("New root EObjects to save should be 1", 1 == emfChangeResult.getNewRootObjectsToSave().size());
        final Pair<EObject, VURI> eObjectVURIPair = emfChangeResult.getNewRootObjectsToSave().iterator().next();
        assertTrue("The EObject in the emfChangeResult has to be a compilation unit",
                eObjectVURIPair.getFirst() instanceof CompilationUnit);
        final CompilationUnit cu = (CompilationUnit) eObjectVURIPair.getFirst();
        assertEquals("Compilation unit name has to be the name of the BasicComponent + 'Impl.java‘", cu.getName(),
                basicComponent.getEntityName() + "Impl.java");
    }

}
