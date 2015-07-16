package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.repository;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;

import org.emftext.language.java.containers.Package;
import org.junit.Test;

import org.palladiosimulator.pcm.repository.Repository;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.PCM2JaMoPPTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPTestUtils;

public class RepositoryMappingTransformaitonTest extends PCM2JaMoPPTransformationTest {

    @Test
    public void testAddRepository() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);

        this.assertRepositoryCorrespondences(repo);
    }

    @Test
    public void testRepositoryNameChange() throws Throwable {
        // setup
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);

        // Test
        repo.setEntityName(PCM2JaMoPPTestUtils.REPOSITORY_NAME + PCM2JaMoPPTestUtils.RENAME);
        EcoreResourceBridge.saveResource(repo.eResource());
        super.triggerSynchronization(VURI.getInstance(repo.eResource()));

        // check
        this.assertRepositoryCorrespondences(repo);
    }

    private void assertRepositoryCorrespondences(final Repository repo) throws Throwable {
        if (null == this.getCorrespondenceInstance()) {
            fail("correspondence instance is still null - no transformation was executed.");
            return;
        }
        final Set<Package> jaMoPPPackages = this.getCorrespondenceInstance().getCorrespondingEObjectsByType(repo,
                Package.class);
        boolean foundRepositoryPackage = false;
        boolean foundDatatypesPackage = false;
        boolean foundContractsPackage = false;
        for (final Package pack : jaMoPPPackages) {
            if (pack.getName().equals(repo.getEntityName())) {
                foundRepositoryPackage = true;
            }
            if (pack.getName().equals("contracts")) {
                foundContractsPackage = true;
            }
            if (pack.getName().equals("datatypes")) {
                foundDatatypesPackage = true;
            }
        }
        assertTrue("No correspondeing repository package found", foundRepositoryPackage);
        assertTrue("No correspondeing datatype package found", foundDatatypesPackage);
        assertTrue("No correspondeing contracts package found", foundContractsPackage);
    }
}
