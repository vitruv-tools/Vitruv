package tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.system;

import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import org.junit.Test;
import org.palladiosimulator.pcm.system.System;

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.PCM2JaMoPPTransformationTest;
import tools.vitruv.applications.pcmjava.tests.util.PCM2JaMoPPTestUtils;
import tools.vitruv.framework.change.description.VitruviusChangeFactory.FileChangeKind;
import tools.vitruv.framework.util.bridges.EcoreResourceBridge;
import tools.vitruv.framework.util.datatypes.VURI;

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
        final System system = super.createAndSyncSystem(PCM2JaMoPPTestUtils.SYSTEM_NAME);
        this.assertSystem(system);
        
        VURI systemVuri = VURI.getInstance(system.eResource()); 
        system.eResource().delete(null);
        super.synchronizeFileChange(FileChangeKind.Delete, systemVuri);
        
        assertEmptyCorrespondence(system);
        assertCompilationUnitForSystemDeleted(system);
        // TODO There should may be more deletions here than only the system
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
