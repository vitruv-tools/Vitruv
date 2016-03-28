package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.responses.pcm2jamopp.system;

import static org.junit.Assert.fail;

import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import org.junit.Test;
import org.palladiosimulator.pcm.system.System;

import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.responses.pcm2jamopp.PCM2JaMoPPTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.responses.utils.PCM2JaMoPPTestUtils;

public class SystemMappingTransformationTest extends PCM2JaMoPPTransformationTest {

    @Test
    public void testCreateSystem() throws Throwable {
        final System system = super.createAndSyncSystem(PCM2JaMoPPTestUtils.SYSTEM_NAME);

        this.assertSystem(system);
    }

    @Test
    public void testRenameSystem() throws Throwable {
        final System system = super.createAndSyncSystem(PCM2JaMoPPTestUtils.SYSTEM_NAME);

        system.setEntityName(PCM2JaMoPPTestUtils.SYSTEM_NAME + PCM2JaMoPPTestUtils.RENAME);
        EcoreResourceBridge.saveResource(system.eResource());
        super.triggerSynchronization(system);

        this.assertSystem(system);
    }

    @Test
    public void testRemoveSystem() throws Throwable {
        final System system = PCM2JaMoPPTestUtils.createSystem(this.resourceSet, PCM2JaMoPPTestUtils.SYSTEM_NAME,
                this.currentTestProjectName);

        //fail("Not yet implemented");
    }

    /**
     * a system should correspond to a Package with its name and a class with its name "Impl"
     *
     * @param system
     * @throws Throwable
     */
    @SuppressWarnings("unchecked")
    private void assertSystem(final System system) throws Throwable {
        final String expectedName = system.getEntityName();
        this.assertCorrespondnecesAndCompareNames(system, 3,
                new Class[] { Package.class, CompilationUnit.class, org.emftext.language.java.classifiers.Class.class },
                new String[] { expectedName, expectedName + "Impl", expectedName + "Impl" });
    }
}
