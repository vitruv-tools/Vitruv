package edu.kit.ipd.sdq.vitruvius.integration.tests.casestudies.correspondence;

import java.util.Collections;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import org.junit.Test;

import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import de.uka.ipd.sdq.pcm.repository.RepositoryComponent;
import de.uka.ipd.sdq.pcm.repository.RepositoryPackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

/**
 * Class for testing correspondences between PCM components and Java classes.
 */
public class ComponentCorrespondenceTest extends BasicCorrespondenceTest {

    /**
     * Test rename basic component.
     *
     * @throws Throwable
     *             the throwable
     */
    @Test
    public void testRenameBasicComponent() throws Throwable {

        // Get a component and rename it
        this.changeRecorder.beginRecording(Collections.singletonList(pcmRepo));
        BasicComponent basicComponent = (BasicComponent) EcoreUtil.getObjectByType(pcmRepo.eContents(),
                RepositoryPackage.eINSTANCE.getBasicComponent());
        basicComponent.setEntityName("ComponentRenamed");

        // Execute the synchronization and check if the source code updated
        // correctly
        this.triggerSynchronization(VURI.getInstance(basicComponent.eResource()));
        this.assertBasicComponentCorrespondences(basicComponent);
    }

    /**
     * Checks if the corresponding jamopp elements (Comp.Unit, Class and package-info) of the given
     * pcm component can be retrieved from the {@link CorrespondenceInstance}.
     *
     * @param basicComponent
     *            the basic component
     * @throws Throwable
     *             the throwable
     */
    @SuppressWarnings("unchecked")
    private void assertBasicComponentCorrespondences(final RepositoryComponent basicComponent) throws Throwable {
        this.assertCorrespondnecesAndCompareNames(basicComponent, 3, new java.lang.Class[] { CompilationUnit.class,
                Package.class, Class.class },
                new String[] { basicComponent.getEntityName() + "Impl", basicComponent.getEntityName(),
                        "package-info.java" });

    }

}
