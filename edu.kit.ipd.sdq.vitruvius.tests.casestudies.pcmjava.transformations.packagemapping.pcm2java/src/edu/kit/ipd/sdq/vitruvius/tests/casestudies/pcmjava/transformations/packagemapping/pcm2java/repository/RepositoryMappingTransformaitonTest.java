package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.packagemapping.pcm2java.repository;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;

import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import org.junit.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Repository;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.CorrespondenceModelUtil;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.packagemapping.pcm2java.PCM2JaMoPPTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.util.PCM2JaMoPPTestUtils;

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

    @Test
    public void testRepositoryNameChangeWithComponents() throws Throwable {
        // setup
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);
        final BasicComponent basicComponent = this.addBasicComponentAndSync(repo);
        
        // Test
        repo.setEntityName(PCM2JaMoPPTestUtils.REPOSITORY_NAME + PCM2JaMoPPTestUtils.RENAME);
        EcoreResourceBridge.saveResource(repo.eResource());
        super.triggerSynchronization(VURI.getInstance(repo.eResource()));

        // check
        this.assertRepositoryCorrespondences(repo);
        this.assertBasicComponentCorrespondences(repo, basicComponent);
    }
    
    @SuppressWarnings("unchecked")
    private void assertBasicComponentCorrespondences(final Repository repository, final BasicComponent basicComponent) throws Throwable {
        this.assertCorrespondnecesAndCompareNames(basicComponent, 3,
                new java.lang.Class[] { CompilationUnit.class, Package.class, Class.class },
                new String[] { repository.getEntityName() + "." + basicComponent.getEntityName() + "." + basicComponent.getEntityName() + "Impl", basicComponent.getEntityName(),
                        basicComponent.getEntityName() + "Impl" });

    }
    
    private void assertRepositoryCorrespondences(final Repository repo) throws Throwable {
        if (null == this.getCorrespondenceModel()) {
            fail("correspondence instance is still null - no transformation was executed.");
            return;
        }
        final Set<Package> jaMoPPPackages = CorrespondenceModelUtil
                .getCorrespondingEObjectsByType(this.getCorrespondenceModel(), repo, Package.class);
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
