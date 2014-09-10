package edu.kit.ipd.sdq.vitruvius.tests.mockup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EObject;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.DefaultTUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.TUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;

public class DefaultTUIDCalculatorTest extends VSUMTest {
    private static final String PCM_REPO_ID = "_r5CW0PxiEeO_U4GJ6Zitkg";
    private static final String PCM_COMPONENT_ID = "_AeWN8PxjEeO_U4GJ6Zitkg";

    @Override
    @Test
    public void testAll() {
        VSUMImpl vsum = testMetaRepositoryVSUMAndModelInstancesCreation();
        VURI model1URI = VURI.getInstance(PCM_INSTANCE_URI);
        ModelInstance model1 = vsum.getAndLoadModelInstanceOriginal(model1URI);
        EObject pcmRoot = model1.getResource().getContents().get(0);
        String expectedTUID = PCM_REPO_ID;
        EObject resolvedEObject = testTUIDCalculator(pcmRoot, pcmRoot, expectedTUID);
        assertEquals(resolvedEObject, pcmRoot);
        EObject pcmComponent = pcmRoot.eContents().get(1);
        expectedTUID = PCM_COMPONENT_ID;
        resolvedEObject = testTUIDCalculator(pcmRoot, pcmComponent, expectedTUID);
        assertEquals(resolvedEObject, pcmComponent);
    }

    public EObject testTUIDCalculator(final EObject rootEObject, final EObject eObject, final String expectedTUID) {
        TUIDCalculatorAndResolver defaultTUIDCalculatorAndResolver = new DefaultTUIDCalculatorAndResolver();
        String tuid = defaultTUIDCalculatorAndResolver.calculateTUIDFromEObject(eObject);
        // Calculated TUID contains more than just the UUID itself. It also contains the resource
        // and the class name that was used to create the TUID. Hence, we just compare with contains
        // instead of equals
        assertNotNull("Calculated TUID is null", tuid);
        assertTrue("Calculated TUID does not contain expected TUID", tuid.contains(expectedTUID));
        String tuidSuffix = TUID.getInstance(tuid).getMinimalSuffix();
        assertNotNull("TUID Suffix is null", tuidSuffix);
        EObject resolvedEObject = defaultTUIDCalculatorAndResolver.resolveEObjectFromRootAndFullTUID(rootEObject,
                tuidSuffix);
        assertNotNull(resolvedEObject);
        return resolvedEObject;
    }
}
