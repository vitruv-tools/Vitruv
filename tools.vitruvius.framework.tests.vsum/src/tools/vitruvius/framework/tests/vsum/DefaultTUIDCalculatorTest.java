package tools.vitruvius.framework.tests.vsum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EObject;
import org.junit.Test;

import tools.vitruvius.framework.metamodel.ModelInstance;
import tools.vitruvius.framework.tuid.DefaultTUIDCalculatorAndResolver;
import tools.vitruvius.framework.tuid.TUID;
import tools.vitruvius.framework.tuid.TUIDCalculatorAndResolver;
import tools.vitruvius.framework.util.datatypes.VURI;
import tools.vitruvius.framework.vsum.VSUMImpl;
import pcm_mockup.Component;
import pcm_mockup.Repository;

public class DefaultTUIDCalculatorTest extends VSUMTest {
    @Override
    @Test
    public void testAll() {
        VSUMImpl vsum = createMetaRepositoryVSUMAndModelInstances();
        VURI model1URI = VURI.getInstance(getDefaultPCMInstanceURI());
        ModelInstance model1 = vsum.getAndLoadModelInstanceOriginal(model1URI);
        Repository pcmRoot = (Repository) model1.getResource().getContents().get(0);
        String expectedTUID = pcmRoot.getId();
        EObject resolvedEObject = testTUIDCalculator(PCM_MM_URI, pcmRoot, pcmRoot, expectedTUID);
        assertEquals(resolvedEObject, pcmRoot);
        Component pcmComponent = (Component) pcmRoot.eContents().get(1);
        expectedTUID = pcmComponent.getId();
        resolvedEObject = testTUIDCalculator(PCM_MM_URI, pcmRoot, pcmComponent, expectedTUID);
        assertEquals(resolvedEObject, pcmComponent);
    }

    public EObject testTUIDCalculator(final String tuidPrefix, final EObject rootEObject, final EObject eObject,
            final String expectedTUID) {
        TUIDCalculatorAndResolver defaultTUIDCalculatorAndResolver = new DefaultTUIDCalculatorAndResolver(tuidPrefix);
        String calculatedTuid = defaultTUIDCalculatorAndResolver.calculateTUIDFromEObject(eObject);
        // Calculated TUID contains more than just the UUID itself. It also contains the resource
        // and the class name that was used to create the TUID. Hence, we just compare with contains
        // instead of equals
        assertNotNull("Calculated TUID is null", calculatedTuid);
        assertTrue("Calculated TUID does not contain expected TUID", calculatedTuid.contains(expectedTUID));
        TUID tuid = TUID.getInstance(calculatedTuid);
        String tuidString = tuid.toString();
        assertNotNull("TUID string is null", tuidString);
        EObject resolvedEObject = defaultTUIDCalculatorAndResolver.resolveEObjectFromRootAndFullTUID(rootEObject,
                tuidString);
        assertNotNull(resolvedEObject);
        return resolvedEObject;
    }
}
