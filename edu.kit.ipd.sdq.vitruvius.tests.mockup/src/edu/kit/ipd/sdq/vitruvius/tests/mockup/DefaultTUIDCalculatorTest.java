package edu.kit.ipd.sdq.vitruvius.tests.mockup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.ecore.EObject;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.VitruviusConstants;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.DefaultTUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.TUIDCalculatorAndResolver;

public class DefaultTUIDCalculatorTest extends VSUMTest {
    @Override
    @Test
    public void testAll() {
        String pcmURIString = "/MockupProject/model/My.pcm_mockup";
        EObject pcmRoot = testMetaRepositoryVSUMAndModelInstancesCreation(pcmURIString);
        VURI vURI = testTUIDCalculator(pcmRoot);
        assertEquals(vURI.toString(), VitruviusConstants.getPlatformResourcePrefix() + pcmURIString);
    }

    public VURI testTUIDCalculator(final EObject eObject) {
        TUIDCalculatorAndResolver defaultTUIDCalculatorAndResolver = new DefaultTUIDCalculatorAndResolver();
        String tuid = defaultTUIDCalculatorAndResolver.getTUID(eObject);
        assertNotNull(tuid);
        VURI vURI = defaultTUIDCalculatorAndResolver.getModelVURIContainingIdentifiedEObject(tuid);
        assertNotNull(vURI);
        return vURI;
    }
}
