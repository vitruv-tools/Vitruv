package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations;

import java.io.IOException;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import de.uka.ipd.sdq.pcm.repository.Repository;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.PCMJaMoPPTransformationExecuter;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
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
    public void testExecutePCM2JaMoPPTransformation() throws IOException {
        final PCMJaMoPPTransformationExecuter pcmJaMoPPTransformation = new PCMJaMoPPTransformationExecuter();
        final Repository repo = PCM2JaMoPPUtils.createAndSyncRepository(resourceSet, changeSynchronizer);
        final ModelInstance pcmModelInstance = PCM2JaMoPPUtils.createModelInstance("testRepository.repository",
                resourceSet);
        final BasicComponent basicComponent = PCM2JaMoPPUtils.createBasicComponent(repo);
        final Change change = PCM2JaMoPPUtils.createCreateChange(basicComponent, repo, "components__Repository");

        final Set<VURI> changedVURIs = pcmJaMoPPTransformation.executeTransformation(change, pcmModelInstance,
                super.correspondenceInstance);
        logger.info("changed VURIs: " + changedVURIs);

        PCM2JaMoPPUtils.saveVURIs(changedVURIs, resourceSet);
    }
}
