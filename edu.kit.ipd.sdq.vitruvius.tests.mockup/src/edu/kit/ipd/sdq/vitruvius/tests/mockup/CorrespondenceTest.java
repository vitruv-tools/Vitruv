package edu.kit.ipd.sdq.vitruvius.tests.mockup;

import org.eclipse.emf.ecore.EObject;
import org.junit.Test;

public class CorrespondenceTest extends VSUMTest {
    @Override
    @Test
    public void testAll() {
        EObject pcmRoot = test("/MockupProject/metamodel/pcm_mockup.ecore", "pcm_mockup",
                "/MockupProject/metamodel/uml_mockup.ecore", "uml_mockup", "/MockupProject/model/My.pcm_mockup",
                "/MockupProject/model/My.uml_mockup");

    }
}
