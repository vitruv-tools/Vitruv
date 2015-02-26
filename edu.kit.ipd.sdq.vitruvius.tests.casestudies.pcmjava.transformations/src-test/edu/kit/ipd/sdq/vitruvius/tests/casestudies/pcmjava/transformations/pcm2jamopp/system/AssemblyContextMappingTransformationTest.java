package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.system;

import org.emftext.language.java.imports.Import;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.Field;
import org.junit.Test;

import de.uka.ipd.sdq.pcm.core.composition.AssemblyContext;
import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.system.System;
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

    /**
     * assemblyContext should correspond to a field
     *
     * @param assemblyContext
     * @throws Throwable
     */
    @SuppressWarnings("unchecked")
    private void assertAssemblyContext(final AssemblyContext assemblyContext) throws Throwable {
        final BasicComponent basicComponent = (BasicComponent) assemblyContext
                .getEncapsulatedComponent__AssemblyContext();
        this.assertCorrespondnecesAndCompareNames(assemblyContext, 4, new Class[] { Import.class, Field.class,
                Constructor.class }, new String[] { basicComponent.getEntityName(), assemblyContext.getEntityName() });
    }
}
