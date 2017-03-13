package tools.vitruv.framework.tests.vsum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.Test;

import pcm_mockup.Component;
import pcm_mockup.Repository;
import tools.vitruv.framework.tuid.AttributeTUIDCalculatorAndResolver;
import tools.vitruv.framework.tuid.HierarchicalTUIDCalculatorAndResolver;
import tools.vitruv.framework.tuid.TUID;
import tools.vitruv.framework.tuid.TUIDCalculatorAndResolver;
import tools.vitruv.framework.util.datatypes.ModelInstance;
import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.framework.vsum.InternalVirtualModel;

public class DefaultTUIDCalculatorTest extends VSUMTest {
    @Test
    public void testAll() {
        InternalVirtualModel vsum = createMetaRepositoryVSUMAndModelInstances();
        VURI model1URI = VURI.getInstance(getDefaultPCMInstanceURI());
        ModelInstance model1 = vsum.getModelInstance(model1URI);
        Repository pcmRoot = (Repository) model1.getResource().getContents().get(0);
        String expectedTUID = "<root>" + HierarchicalTUIDCalculatorAndResolver.SUBDIVIDER + pcmRoot.eClass().getName()
                + HierarchicalTUIDCalculatorAndResolver.SUBDIVIDER + "id=" + pcmRoot.getId();
        EObject resolvedEObject = testTUIDCalculator(PCM_MM_URI, pcmRoot, pcmRoot, expectedTUID, "id");
        assertEquals(resolvedEObject, pcmRoot);
        Component pcmComponent = (Component) pcmRoot.eContents().get(1);
        expectedTUID = pcmComponent.getId();
        resolvedEObject = testTUIDCalculator(PCM_MM_URI, pcmRoot, pcmComponent, expectedTUID, "id");
        assertEquals(resolvedEObject, pcmComponent);
    }

    @Test
    public void testTUIDCalculationAndResolutionForUnsettedElement() {
        // create UML class with empty Property
        // TODO ML: use mockup UML metamodel instead of real UML metamodel
        Property umlProperty = createUmlModelWithProperty();
        String expectedTUID = "name";
        String umlPrefix = umlProperty.eClass().getEPackage().getNsPrefix();
        EObject resolvedEObject = testTUIDCalculator(umlPrefix, umlProperty.getClass_().getPackage(), umlProperty,
                expectedTUID, "name");
        assertEquals("UAttribute could not be correctly resolved", resolvedEObject, umlProperty);
    }

    private Property createUmlModelWithProperty() {
        Package umlPackage = UMLFactory.eINSTANCE.createPackage();
        Class umlClass = UMLFactory.eINSTANCE.createClass();
        umlPackage.getPackagedElements().add(umlClass);
        Property umlProperty = UMLFactory.eINSTANCE.createProperty();
        umlClass.getOwnedAttributes().add(umlProperty);
        return umlProperty;
    }

    private EObject testTUIDCalculator(final String tuidPrefix, final EObject rootEObject, final EObject eObject,
            final String expectedTUID, final String... attributeNames) {
        TUIDCalculatorAndResolver defaultTUIDCalculatorAndResolver = new AttributeTUIDCalculatorAndResolver(tuidPrefix,
                attributeNames);
        boolean hasTUID = defaultTUIDCalculatorAndResolver.calculateTUIDFromEObject(eObject) != null;
        assertTrue("TUID Calculator is not able to calculate TUID for EObject " + eObject, hasTUID);
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
