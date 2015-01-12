package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.jamopp2pcm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import de.uka.ipd.sdq.pcm.repository.Repository;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPTestUtils;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;

public class PackageMappingTransformationTest extends JaMoPP2PCMTransformationTest {

    /**
     * first package is created --> should be mapped to a repository
     *
     * @throws Throwable
     */
    @Test
    public void testAddFirstPackage() throws Throwable {
        final Repository repo = super.addFirstPackage();
        assertEquals("Name of the repository is not the same as the name of the package",
                PCM2JaMoPPTestUtils.REPOSITORY_NAME, repo.getEntityName());
    }

    @Test
    public void testAddFirstPackageWithoutFile() throws Throwable {
        super.createPackage(new String[] { PCM2JaMoPPTestUtils.REPOSITORY_NAME });
        final CorrespondenceInstance ci = this.getCorrespondenceInstance();
        assertTrue("CorrespondenceInstance == null", null != ci);
        assertTrue("No repository found in correspondence instance.",
                0 < ci.getAllEObjectsInCorrespondencesWithType(Repository.class).size());
    }

    /**
     * second packages is added --> should be mapped to a basic component
     *
     * @throws Throwable
     */
    @Test
    public void testAddSecondPackage() throws Throwable {
        final Repository repo = super.addFirstPackage();
        final BasicComponent bc = super.addSecondPackage();

        // repository of basic component has to be the repository
        assertEquals("Repository of basic compoennt is not the repository: " + repo, repo.getId(), bc
                .getRepository__RepositoryComponent().getId());
        assertTrue("The name of the basic component is not " + PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME, bc
                .getEntityName().equalsIgnoreCase(PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME));
    }

    @Test
    public void testRenamePackage() throws Throwable {
        final Repository repo = super.addFirstPackage();
        final BasicComponent bc = super.addSecondPackage();

        final String packageName = PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME + PCM2JaMoPPTestUtils.RENAME;

        super.renamePackage(this.secondPackage, packageName);

        // EcoreResourceBridge.saveEObjectAsOnlyContent(secondPackage, );
        TestUtil.waitForSynchronization();

        // repository of basic component has to be the repository
        assertEquals("Repository of basic compoennt is not the repository: " + repo, repo.getId(), bc
                .getRepository__RepositoryComponent().getId());
        // name should be changed since there is no implementing class (yet) fot the component
        assertTrue("The name of the basic component is not " + packageName,
                bc.getEntityName().equalsIgnoreCase(packageName));
    }
}
