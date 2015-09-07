package edu.kit.ipd.sdq.vitruvius.integration.tests.casestudies.correspondence;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.junit.After;
import org.junit.Before;

import de.uka.ipd.sdq.pcm.repository.Repository;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.integration.transformations.PCMJaMoPPCorrespondenceModelTransformation;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.PCM2JaMoPPTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;

/*
 * HOW TO USE THIS UNITTESTS:
 * 
 * 1. Create folder "junit-workspace" as sibling to your vitruvius folder (or run the testcase which will create the folder for you)
 * 2. Create the project "MockupProject" which contains your src folder in the junit-workspace
 * 3. Run Somox on this project
 * 4. Switch to junit-workspace and import the MockupProject manually in order to avoid I/O exceptions during the testcase
 * 5. Save a copy of the project somewhere else, so you can reuse it if transformations alter your code or models during Vitruvius execution
 * 6. Run any test-case that is derived from this class (disable 'clear workspace' in the run configurations!)
 */

/**
 * The Class BasicCorrespondenceTest. Should be extended by custom test classes.
 */
public class BasicCorrespondenceTest extends PCM2JaMoPPTransformationTest {

    /** The Constant PROJECT_NAME. */
    private static final String PROJECT_NAME = "MockupProject" + IPath.SEPARATOR;

    private static final String PROJECT_NAME_COPY = "MockupProjectCopy" + IPath.SEPARATOR;

    /** The Constant MODEL_PATH. */
    private static final String MODEL_PATH = "model";

    /** The Constant CODE_PATH. */
    private static final String CODE_PATH = "src";

    /** The Constant SCDM_PATH. */
    private static final String SCDM_PATH = MODEL_PATH + IPath.SEPARATOR
            + "internal_architecture_model.sourcecodedecorator";

    /** The Constant PCM_REPOSITORY. */
    private static final String PCM_REPOSITORY = MODEL_PATH + IPath.SEPARATOR
            + "internal_architecture_model.repository";

    /** The Constant pcmMMUri. */
    private static final VURI pcmMMUri = VURI.getInstance(PCMJaMoPPNamespace.PCM.PCM_METAMODEL_NAMESPACE);

    /** The transformation. */
    private PCMJaMoPPCorrespondenceModelTransformation transformation;

    /** The pcm repo. */
    protected Repository pcmRepo;

    private IWorkspace workspace;

    /**
     * Sets up the correspondence model that should be tested.
     * 
     * @throws CoreException
     */
    @Before
    public void setUp() throws CoreException {

        // Initialize workspace
        workspace = ResourcesPlugin.getWorkspace();
        IPath root = workspace.getRoot().getLocation();
        IPath projectPath = root.append(PROJECT_NAME);

        // Copy MockupProject and rename it in order to have valid a instance for each test
        final IProject mockupProject = workspace.getRoot().getProject(PROJECT_NAME);
        renameFoldersIfNecessary(mockupProject);
        IPath copyPath = new Path(PROJECT_NAME_COPY);
        mockupProject.copy(copyPath, true, null);

        // Initialize vsum
        vsum.getOrCreateAllCorrespondenceInstances(metaRepository.getMetamodel(pcmMMUri));

        // Create and execute the transformation
        transformation = new PCMJaMoPPCorrespondenceModelTransformation(projectPath + SCDM_PATH, projectPath
                + PCM_REPOSITORY, projectPath + CODE_PATH, vsum);
        try {
            transformation.createCorrespondences();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Add the PCM model to Vitruvius
        VURI sourceModelURI = VURI.getInstance(TestUtil.PROJECT_URI + "/" + PCM_REPOSITORY);
        ModelInstance pcmInstance = vsum.getAndLoadModelInstanceOriginal(sourceModelURI);

        pcmRepo = pcmInstance.getUniqueRootEObjectIfCorrectlyTyped(Repository.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.
     * PCM2JaMoPPTransformationTest#getCorrespondenceInstance()
     */
    protected CorrespondenceInstance getCorrespondenceInstance() throws Throwable {
        CorrespondenceInstance correspondenceInstance2 = transformation.getCorrespondenceInstance();
        return correspondenceInstance2;
    }

    @After
    public void cleanWorkspace() throws CoreException {

        // Clear vsum
        this.vsum = null;
        this.correspondenceInstance = null;

        // Delete modified project
        final IWorkspaceRoot root = workspace.getRoot();
        final IProject modifiedProject = root.getProject(PROJECT_NAME);
        modifiedProject.delete(true, null);

        // Move copy back
        final IProject ProjectCopy = root.getProject(PROJECT_NAME_COPY);
        IPath oldPath = new Path(PROJECT_NAME);
        ProjectCopy.move(oldPath, true, null);
    }

    /**
     * Rename src and model folder of the given project, as their names sometimes get changed during
     * a test
     * 
     * @param project
     * @throws CoreException
     */
    private void renameFoldersIfNecessary(IProject project) throws CoreException {
        IResource[] projectMembers = project.members();
        for (IResource member : projectMembers) {
            if (member.getType() == IResource.FOLDER) {
                String folderName = member.getName();
                if (folderName.startsWith("modeltest") && folderName.length() > 20) {
                    // model folder
                    member.move(project.getProjectRelativePath().append(IPath.SEPARATOR + MODEL_PATH), true, null);
                } else if (folderName.startsWith("test") && folderName.length() > 20) {
                    // src folder
                    member.move(project.getProjectRelativePath().append(IPath.SEPARATOR + CODE_PATH), true, null);
                }

            }
        }
    }
}
