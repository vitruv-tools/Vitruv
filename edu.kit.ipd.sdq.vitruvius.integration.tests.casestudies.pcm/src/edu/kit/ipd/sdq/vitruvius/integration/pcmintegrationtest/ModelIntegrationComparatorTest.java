package edu.kit.ipd.sdq.vitruvius.integration.pcmintegrationtest;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.util.PCMJavaRepositoryCreationUtil;
import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChange;
import edu.kit.ipd.sdq.vitruvius.framework.modelsynchronization.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EMFBridge;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.invariantcheckers.PCMRepositoryExtractor;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.strategies.PCMRepositoryIntegrationStrategy;
import edu.kit.ipd.sdq.vitruvius.integration.traversal.util.UnorderedReferencesRespectingEqualityHelper;
import edu.kit.ipd.sdq.vitruvius.integration.util.IntegrationUtil;
import edu.kit.ipd.sdq.vitruvius.integration.util.PCMMetaModelConverter;

/**
 * Compares the inputmodel with the integrated model. These should be equivalent in most of their
 * structure, depending on the currently supported feature set of the integration strategy. Because
 * of that, some model elements will not be compared.
 */
public class ModelIntegrationComparatorTest {

    /**
     * Sets the up before class.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * Since the test below creates folder and projects in the test workspace, we must delete these
     * before running the test again
     *
     * TODO: does not work as of 15.3.15
     *
     * @throws Exception
     *             the exception
     */
    @Before
    public void setUp() throws Exception {
        final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IWorkspaceRoot root = workspace.getRoot();
        root.refreshLocal(10, null);
        // simple check if integration has been applied to the test models / project
        if (root.getProjects().length > 1) {
            // delete metadata
            final IProject vitMetaData = root.getProjects()[1];
            vitMetaData.delete(true, true, null);
            root.clearHistory(null);
            root.refreshLocal(10, null);

            // delete created source files, and create a new empty folder
            final IProject testProject = root.getProjects()[0];
            testProject.clearHistory(null);
            testProject.refreshLocal(10, null);
            testProject.getFolder("src").delete(true, false, null);
            testProject.getFolder("bin").delete(true, false, null);
            final IFolder srcFolder = testProject.getFolder("src");
            srcFolder.create(true, true, null);
            root.refreshLocal(10, null);

        }

    }

    /**
     * See Class Description, duplicate code from integrationHandler
     */
    @Test
    public void repositoryModelIntegrationCompareTest() {

        final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IWorkspaceRoot root = workspace.getRoot();
        final IProject testProject = root.getProjects()[0];
        // workspace
        // final IFile file = testProject.getFile("Testmodels/ComparatorTestModel.repository");
        final IFile file = testProject.getFile("Testmodels/vitruvius.repository");
        IResource resource = file; // see type hierarchy

        // get URI from resource
        final URI uri = EMFBridge.getEMFPlatformUriForIResource(resource);

        // update the PCM parameter definitions
        resource = this.updateParameterDef(resource, uri);
        final PCMRepositoryIntegrationStrategy integrator = new PCMRepositoryIntegrationStrategy();
        ChangeSynchronizing changeSynchronizing = null;
        if (changeSynchronizing == null) {
            changeSynchronizing = IntegrationUtil.createVitruviusCore(PCMJavaRepositoryCreationUtil.createPCMJavaMetarepository());
        }

        List<VitruviusChange> changes = null;

        try {
            changes = integrator.integrateModel(resource, changeSynchronizing);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        final PCMRepositoryExtractor pre = new PCMRepositoryExtractor();

        final Repository originalRepo = pre.getImpl(integrator.getModel());
        final PCMModelBuilder builder = new PCMModelBuilder(changes);
        final Repository integratedRepo = builder.createPCMModel();

        // remove excluded elements from original model or else EqualityHelper will always say
        // 'false'
        for (final RepositoryComponent comp : originalRepo.getComponents__Repository()) {
            if (comp instanceof BasicComponent) {
                ((BasicComponent) comp).getServiceEffectSpecifications__BasicComponent().clear();
                ((BasicComponent) comp).getComponentParameterUsage_ImplementationComponentType().clear();
                ((BasicComponent) comp).getPassiveResource_BasicComponent().clear();
                comp.getResourceRequiredRoles__ResourceInterfaceRequiringEntity().clear();
            }
        }

        // ResourceSet solvedResSet = new ResourceSetImpl();
        // Resource solvedResource = solvedResSet.createResource(URI
        // .createURI("platform:/resource/ModelVergleich/Testmodels/solved.repository"));
        //
        // solvedResource.getContents().add(originalRepo);
        //
        // ResourceSet resSet = new ResourceSetImpl();
        // Resource integratedResource = resSet.createResource(URI
        // .createURI("platform:/resource/ModelVergleich/Testmodels/integrated.repository"));
        //
        // integratedResource.getContents().add(integratedRepo);
        //
        // try {
        // solvedResource.save(Collections.EMPTY_MAP);
        // integratedResource.save(Collections.EMPTY_MAP);
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }

        // compare
        final UnorderedReferencesRespectingEqualityHelper comparator = new UnorderedReferencesRespectingEqualityHelper();
        final boolean equal = comparator.equals(originalRepo, integratedRepo);
        assertTrue(equal);
    }

    /**
     * Updates PCM parameter definitions.
     *
     * @param resource
     *            : PCM repository
     * @param uri
     *            : PCM repository uri
     * @return : IResource to file with new parameter definitions
     */
    private IResource updateParameterDef(IResource resource, URI uri) {
        // 1. Update model to current PCM version
        final File updatedModel = PCMMetaModelConverter.convertPCM(resource.getLocation(), false);

        // 2. update resource if model got updated
        if (PCMMetaModelConverter.isModelUpdated()) {

            // create new uri
            uri = uri.trimSegments(1);
            final String fileName = updatedModel.getName();
            uri = uri.appendSegment(fileName);

            // set resource to new file
            final IProject project = resource.getProject();
            IPath iPathForEMFUri = EMFBridge.getIPathForEMFUri(uri);

            // remove project folder from path
            iPathForEMFUri = iPathForEMFUri.removeFirstSegments(1);
            final IFile file = project.getFile(iPathForEMFUri);
            resource = file;
        }
        return resource;
    }

}
