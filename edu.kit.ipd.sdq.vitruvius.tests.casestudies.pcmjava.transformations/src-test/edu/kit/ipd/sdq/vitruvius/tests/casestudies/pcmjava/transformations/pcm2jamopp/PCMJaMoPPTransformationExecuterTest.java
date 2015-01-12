package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.containers.CompilationUnit;
import org.junit.Test;

import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import de.uka.ipd.sdq.pcm.repository.Repository;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.PCMJaMoPPTransformationExecuter;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPTestUtils;

public class PCMJaMoPPTransformationExecuterTest extends PCM2JaMoPPTransformationTest {

    private static final Logger logger = Logger.getLogger(PCMJaMoPPTransformationExecuterTest.class.getSimpleName());

    @Test
    public void testCreateNewResourceInPCM2JaMoPPTransformation() throws Throwable {
        final PCMJaMoPPTransformationExecuter pcmJaMoPPTransformation = new PCMJaMoPPTransformationExecuter();
        final Repository repo = PCM2JaMoPPTestUtils.createRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);
        final BasicComponent basicComponent = PCM2JaMoPPTestUtils.createBasicComponent(repo);
        final EMFModelChange change = PCM2JaMoPPTestUtils.createCreateChange(basicComponent, repo, repo,
                "components__Repository");

        final EMFChangeResult emfChangeResult = pcmJaMoPPTransformation.executeTransformation(change,
                super.getCorrespondenceInstance());
        logger.info("changed VURIs: " + emfChangeResult);

        PCM2JaMoPPTestUtils.saveEMFChangeResult(emfChangeResult, this.resourceSet);
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
