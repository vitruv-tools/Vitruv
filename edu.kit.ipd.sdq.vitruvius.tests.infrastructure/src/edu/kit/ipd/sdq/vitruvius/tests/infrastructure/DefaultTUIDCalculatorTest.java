package edu.kit.ipd.sdq.vitruvius.tests.infrastructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EObject;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.tuid.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.metamodel.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.tests.vsum.VSUMTest;
import edu.kit.ipd.sdq.vitruvius.framework.tuid.DefaultTUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.framework.tuid.TUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;

public class DefaultTUIDCalculatorTest extends VSUMTest {
    private static final String PCM_REPO_ID = "_r5CW0PxiEeO_U4GJ6Zitkg";
    private static final String PCM_COMPONENT_ID = "_AeWN8PxjEeO_U4GJ6Zitkg";

    @Override
    @Test
    public void testAll() {
        VSUMImpl vsum = createMetaRepositoryVSUMAndModelInstances();
        VURI model1URI = VURI.getInstance(getDefaultPCMInstanceURI());
        ModelInstance model1 = vsum.getAndLoadModelInstanceOriginal(model1URI);
        EObject pcmRoot = model1.getResource().getContents().get(0);
        String expectedTUID = PCM_REPO_ID;
        EObject resolvedEObject = testTUIDCalculator(PCM_MM_URI, pcmRoot, pcmRoot, expectedTUID);
        assertEquals(resolvedEObject, pcmRoot);
        EObject pcmComponent = pcmRoot.eContents().get(1);
        expectedTUID = PCM_COMPONENT_ID;
        resolvedEObject = testTUIDCalculator(PCM_MM_URI, pcmRoot, pcmComponent, expectedTUID);
        assertEquals(resolvedEObject, pcmComponent);
    }

    public EObject testTUIDCalculator(final String tuidPrefix, final EObject rootEObject, final EObject eObject, final String expectedTUID) {
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
