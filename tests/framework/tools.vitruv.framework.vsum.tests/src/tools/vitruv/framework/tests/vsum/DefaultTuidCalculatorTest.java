package tools.vitruv.framework.tests.vsum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.jupiter.api.Test;

import pcm_mockup.Component;
import pcm_mockup.Repository;
import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver;
import tools.vitruv.framework.tuid.HierarchicalTuidCalculatorAndResolver;
import tools.vitruv.framework.tuid.Tuid;
import tools.vitruv.framework.tuid.TuidCalculatorAndResolver;
import tools.vitruv.framework.util.datatypes.ModelInstance;
import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.framework.vsum.InternalVirtualModel;

public class DefaultTuidCalculatorTest extends VsumTest {
    @Test
    public void testAll() {
        InternalVirtualModel vsum = createVirtualModelAndModelInstances();
        VURI model1URI = VURI.getInstance(getDefaultPcmInstanceURI());
        ModelInstance model1 = vsum.getModelInstance(model1URI);
        Repository pcmRoot = (Repository) model1.getResource().getContents().get(0);
        String expectedTuid = "<root>" + HierarchicalTuidCalculatorAndResolver.SUBDIVIDER + pcmRoot.eClass().getName()
                + HierarchicalTuidCalculatorAndResolver.SUBDIVIDER + "id=" + pcmRoot.getId();
        EObject resolvedEObject = testTuidCalculator(PCM_MM_URI, pcmRoot, pcmRoot, expectedTuid, "id");
        assertEquals(resolvedEObject, pcmRoot);
        Component pcmComponent = (Component) pcmRoot.eContents().get(1);
        expectedTuid = pcmComponent.getId();
        resolvedEObject = testTuidCalculator(PCM_MM_URI, pcmRoot, pcmComponent, expectedTuid, "id");
        assertEquals(resolvedEObject, pcmComponent);
    }

    @Test
    public void testTuidCalculationAndResolutionForUnsettedElement() {
        // create UML class with empty Property
        // TODO ML: use mockup UML metamodel instead of real UML metamodel
        Property umlProperty = createUmlModelWithProperty();
        String expectedTuid = "name";
        String umlPrefix = umlProperty.eClass().getEPackage().getNsPrefix();
        EObject resolvedEObject = testTuidCalculator(umlPrefix, umlProperty.getClass_().getPackage(), umlProperty,
                expectedTuid, "name");
        assertEquals(resolvedEObject, umlProperty, "UAttribute could not be correctly resolved");
    }

    private Property createUmlModelWithProperty() {
        Package umlPackage = UMLFactory.eINSTANCE.createPackage();
        Class umlClass = UMLFactory.eINSTANCE.createClass();
        umlPackage.getPackagedElements().add(umlClass);
        Property umlProperty = UMLFactory.eINSTANCE.createProperty();
        umlClass.getOwnedAttributes().add(umlProperty);
        return umlProperty;
    }

    private EObject testTuidCalculator(final String tuidPrefix, final EObject rootEObject, final EObject eObject,
            final String expectedTuid, final String... attributeNames) {
        TuidCalculatorAndResolver defaultTuidCalculatorAndResolver = new AttributeTuidCalculatorAndResolver(tuidPrefix,
                attributeNames);
        boolean hasTuid = defaultTuidCalculatorAndResolver.calculateTuidFromEObject(eObject) != null;
        assertTrue(hasTuid, "Tuid Calculator is not able to calculate Tuid for EObject " + eObject);
        String calculatedTuid = defaultTuidCalculatorAndResolver.calculateTuidFromEObject(eObject);
        // Calculated Tuid contains more than just the UUID itself. It also contains the resource
        // and the class name that was used to create the Tuid. Hence, we just compare with contains
        // instead of equals
        assertNotNull("Calculated Tuid is null", calculatedTuid);
        assertTrue(calculatedTuid.contains(expectedTuid), "Calculated Tuid does not contain expected Tuid");
        Tuid tuid = Tuid.getInstance(calculatedTuid);
        String tuidString = tuid.toString();
        assertNotNull("Tuid string is null", tuidString);
        EObject resolvedEObject = defaultTuidCalculatorAndResolver.resolveEObjectFromRootAndFullTuid(rootEObject,
                tuidString);
        assertNotNull(resolvedEObject);
        return resolvedEObject;
    }

}
