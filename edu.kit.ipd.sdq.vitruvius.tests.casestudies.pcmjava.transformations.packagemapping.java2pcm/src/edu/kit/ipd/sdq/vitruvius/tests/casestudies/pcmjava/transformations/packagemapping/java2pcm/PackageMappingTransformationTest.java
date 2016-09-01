package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.packagemapping.java2pcm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.containers.Package;
import org.junit.Assert;
import org.junit.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;

import edu.kit.ipd.sdq.vitruvius.extensions.tests.util.TestUtil;
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel;
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModelUtil;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.util.PCM2JaMoPPTestUtils;

public class PackageMappingTransformationTest extends Java2PCMPackageMappingTransformationTest {

    /**
     * first package is created --> should be mapped to a repository
     *
     * @throws Throwable
     */
    @Test
    public void testAddFirstPackage() throws Throwable {
        final Repository repo = super.addRepoContractsAndDatatypesPackage();
        assertEquals("Name of the repository is not the same as the name of the package",
                PCM2JaMoPPTestUtils.REPOSITORY_NAME, repo.getEntityName());
        this.assertResourceAndFileForEObjects(repo);
        this.assertFilesOnlyForEObjects(repo);
    }

    @Test
    public void testAddFirstPackageWithoutFile() throws Throwable {
        super.createPackage(new String[] { PCM2JaMoPPTestUtils.REPOSITORY_NAME });
        final CorrespondenceModel ci = this.getCorrespondenceModel();
        assertTrue("CorrespondenceModel == null", null != ci);
        assertTrue("No repository found in correspondence instance.",
                0 < CorrespondenceModelUtil.getAllEObjectsOfTypeInCorrespondences(ci, Repository.class).size());
    }

    /**
     * second packages is added --> should be mapped to a basic component
     *
     * @throws Throwable
     */
    @Test
    public void testAddSecondPackage() throws Throwable {
        final Repository repo = super.addRepoContractsAndDatatypesPackage();

        final BasicComponent bc = super.addSecondPackageCorrespondsToBasicComponent();

        this.assertRepositoryAndPCMName(repo, bc, PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME);
        this.assertFilesOnlyForEObjects(bc);
    }

    @Test
    public void testCreateCompositeComponent() throws Throwable {
        final Repository repo = super.addRepoContractsAndDatatypesPackage();

        final CompositeComponent cc = super.addSecondPackageCorrespondsToCompositeComponent();

        this.assertRepositoryAndPCMName(repo, cc, PCM2JaMoPPTestUtils.COMPOSITE_COMPONENT_NAME);
        this.assertFilesOnlyForEObjects(cc);
    }

    @Test
    public void testCreateSystem() throws Throwable {
        final Repository repository = super.addRepoContractsAndDatatypesPackage();

        final System system = super.addSecondPackageCorrespondsToSystem();

        this.assertPCMNamedElement(system, PCM2JaMoPPTestUtils.SYSTEM_NAME);
        this.assertFilesOnlyForEObjects(repository, system);
    }

    @Test
    public void testCreateNoCorrespondingObject() throws Throwable {
        final Repository repo = super.addRepoContractsAndDatatypesPackage();

        super.addSecondPackageCorrespondsWithoutCorrespondences();
        Assert.assertNotNull(repo);
        // TODO:what to check?
    }

    @Test
    public void testRenamePackage() throws Throwable {
        final Repository repo = super.addRepoContractsAndDatatypesPackage();
        super.addSecondPackageCorrespondsToBasicComponent();

        final String packageName = PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME + PCM2JaMoPPTestUtils.RENAME;

        final Package renamedPackage = super.renamePackage(this.secondPackage, packageName);

        // EcoreResourceBridge.saveEObjectAsOnlyContent(secondPackage, );
        TestUtil.waitForSynchronization();

        final Set<EObject> correspondingEObjects = CorrespondenceModelUtil
                .getCorrespondingEObjects(this.getCorrespondenceModel(), renamedPackage);
        final EObject correspondingEObject = CollectionBridge.claimOne(correspondingEObjects);
        assertTrue("The corresponding EObject for the package has to be a BasicComponent",
                correspondingEObject instanceof BasicComponent);
        final BasicComponent bc = (BasicComponent) correspondingEObject;
        // repository of basic component has to be the repository
        assertEquals("Repository of basic compoennt is not the repository: " + repo, repo.getId(),
                bc.getRepository__RepositoryComponent().getId());
        // name should be changed since there is no implementing class (yet) fot the component
        assertTrue(
                "The name of the basic component, which is '" + bc.getEntityName()
                        + "', is not contained in the name of the package: " + packageName + " ",
                packageName.toLowerCase().contains(bc.getEntityName().toLowerCase()));
        this.assertResourceAndFileForEObjects(bc);
    }
}
