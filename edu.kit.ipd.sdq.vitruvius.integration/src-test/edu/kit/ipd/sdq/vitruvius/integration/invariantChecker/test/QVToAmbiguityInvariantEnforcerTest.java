package edu.kit.ipd.sdq.vitruvius.integration.invariantChecker.test;

import static org.junit.Assert.assertTrue;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.uka.ipd.sdq.pcm.repository.Interface;
import de.uka.ipd.sdq.pcm.repository.OperationInterface;
import de.uka.ipd.sdq.pcm.repository.OperationSignature;
import de.uka.ipd.sdq.pcm.repository.Repository;
import edu.kit.ipd.sdq.vitruvius.integration.invariantcheckers.PCMRepositoryExtractor;
import edu.kit.ipd.sdq.vitruvius.integration.invariantcheckers.pcmjamoppenforcer.withocl.PJIE_ComponentInterfaceImplementsAmbiguity;
import edu.kit.ipd.sdq.vitruvius.integration.util.RepositoryModelLoader;

/**
 * Test against one specific model instance (twointerfaces.repository). The target Workspace must
 * contain this model under Testmodels/TwoInterfaces.repository. Must be started as Plugin-Test
 * 
 * 
 *
 * @author johannes hoor
 *
 */
public class QVToAmbiguityInvariantEnforcerTest {

    private Resource model;

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
     * Sets the up.
     *
     * @throws Exception
     *             the exception
     */
    @Before
    public void setUp() throws Exception {

        final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IWorkspaceRoot root = workspace.getRoot();
        final IProject testProject = root.getProjects()[0]; // assumption: only on project in test
        // workspace
        final IFile file = testProject.getFile("Testmodels/TwoInterfaces.repository");

        final String path = file.getLocation().toOSString();
        this.model = RepositoryModelLoader.loadPCMResource(path);

    }

    /**
     * 
     *
     * Tests if all necessary rename operations take place.
     */
    @Test
    public void testBasicRenameScheme() {
        final PJIE_ComponentInterfaceImplementsAmbiguity tester = new PJIE_ComponentInterfaceImplementsAmbiguity();

        // TODO: change output to test workspace folder
        tester.setOutFilePath("tmp/TwoInterfaces.repository"); // this is a subfolder in the current
        // workspace, not in test workspace

        final Resource rMod = tester.loadEnforceReturn(this.model);
        final PCMRepositoryExtractor rootExtractor = new PCMRepositoryExtractor();
        final Repository repository = rootExtractor.getImpl(rMod);
        final EList<Interface> interfaces = repository.getInterfaces__Repository();

        // TODO: unsafe casts?
        for (final Interface i : interfaces) {
            if (i.getId().equals("_KsB_YLEMEeSFxMnN7dzQyw")) {
                final EList<OperationSignature> operations = ((OperationInterface) i)
                        .getSignatures__OperationInterface();
                for (final OperationSignature sig : operations) {
                    if (sig.getId().equals("_SvnbULEMEeSFxMnN7dzQyw")) {
                        assertTrue(sig.getEntityName().equals("doSmth0"));
                    } else {
                        assertTrue(sig.getEntityName().equals("doSmthElse"));
                    }
                }
            } else if (i.getId().equals("_HQ7QALFEEeSysMm6CYQB2Q")) {
                final EList<OperationSignature> operations = ((OperationInterface) i)
                        .getSignatures__OperationInterface();
                assertTrue(operations.get(0).getEntityName().equals("doAnything"));
            } else if (i.getId().equals("_L2PIsLEMEeSFxMnN7dzQyw")) {
                final EList<OperationSignature> operations = ((OperationInterface) i)
                        .getSignatures__OperationInterface();
                for (final OperationSignature sig : operations) {
                    if (sig.getId().equals("_XVBgMLEMEeSFxMnN7dzQyw")) {
                        assertTrue(sig.getEntityName().equals("doSmth"));
                    } else if (sig.getId().equals("_dfA9QLEMEeSFxMnN7dzQyw")) {
                        assertTrue(sig.getEntityName().equals("doNextThing"));
                    } else if (sig.getId().equals("_e_3sALEMEeSFxMnN7dzQyw")) {
                        assertTrue(sig.getEntityName().equals("doSmth1"));
                    } else if (sig.getId().equals("_sdZrELkCEeSiVZOyR1nbpw")) {
                        assertTrue(sig.getEntityName().equals("doSmth"));
                    }
                }
            } else if (i.getId().equals("_OPuIkLEMEeSFxMnN7dzQyw")) {
                final EList<OperationSignature> operations = ((OperationInterface) i)
                        .getSignatures__OperationInterface();
                assertTrue(operations.get(0).getEntityName().equals("doAnything"));
            } else {
                final EList<OperationSignature> operations = ((OperationInterface) i)
                        .getSignatures__OperationInterface();
                assertTrue(operations.get(0).getEntityName().equals("ABCD"));
            }
        }

    }

}
