package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.system;

import org.junit.Test;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.PCM2JaMoPPTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPTestUtils;

public class AssemblyContextMappingTransformationTest extends PCM2JaMoPPTransformationTest {

    @Test
    public void testCreateAssemblyContext() throws Throwable {
        final System system = super.createAndSyncSystem(PCM2JaMoPPTestUtils.SYSTEM_NAME);
        final Repository repo = super.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);
        final BasicComponent basicComponent = super.addBasicComponentAndSync(repo);

        final AssemblyContext assemblyContext = this.createAndSyncAssemblyContext(system, basicComponent);

        this.assertAssemblyContext(assemblyContext);
    }

}
