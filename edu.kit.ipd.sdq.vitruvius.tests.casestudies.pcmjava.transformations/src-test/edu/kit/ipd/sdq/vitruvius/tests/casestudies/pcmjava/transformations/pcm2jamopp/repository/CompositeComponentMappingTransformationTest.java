package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.repository;

import org.emftext.language.java.containers.Package;
import org.junit.Test;

import de.uka.ipd.sdq.pcm.repository.CompositeComponent;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.PCM2JaMoPPTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPTestUtils;

public class CompositeComponentMappingTransformationTest extends PCM2JaMoPPTransformationTest {

    @Test
    public void testCreateCompositeComponent() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);

        final CompositeComponent compositeComponent = this.addCompositeComponentAndSync(repo);

        this.assertCompositeComponentCorrespondences(compositeComponent, PCM2JaMoPPTestUtils.COMPOSITE_COMPONENT_NAME);
    }

    private CompositeComponent addCompositeComponentAndSync(final Repository repo) {
        final CompositeComponent cc = RepositoryFactory.eINSTANCE.createCompositeComponent();
        cc.setEntityName(PCM2JaMoPPTestUtils.COMPOSITE_COMPONENT_NAME);
        cc.setRepository__RepositoryComponent(repo);
        super.triggerSynchronization(repo);
        return cc;
    }

    /**
     * a composite component should correspond to a class and a package
     *
     * @param compositeComponent
     * @throws Throwable
     */
    @SuppressWarnings("unchecked")
    private void assertCompositeComponentCorrespondences(final CompositeComponent compositeComponent,
            final String expectedName) throws Throwable {
        super.assertCorrespondnecesAndCompareNames(compositeComponent, 1, new java.lang.Class[] { Package.class,
                org.emftext.language.java.classifiers.Class.class },
                new String[] { expectedName, expectedName + "Impl" });
    }

}
