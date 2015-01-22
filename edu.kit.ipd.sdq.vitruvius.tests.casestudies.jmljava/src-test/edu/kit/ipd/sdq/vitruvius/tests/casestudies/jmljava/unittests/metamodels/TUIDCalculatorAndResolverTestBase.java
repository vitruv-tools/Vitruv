package edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.metamodels;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.eclipse.emf.ecore.EObject;
import org.junit.Before;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.TUIDCalculatorAndResolverBase;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.utils.ModelLoader;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.utils.ModelLoader.IResourceFiles;

public abstract class TUIDCalculatorAndResolverTestBase<T extends TUIDCalculatorAndResolverBase, S extends EObject> {

    protected T tuidGenerator;
    protected S rootObject;
    
    @Before
    public void setup() throws IOException {     
        rootObject = loadResourceModel(getDefaultResourceFile());
        tuidGenerator = getNewTUIDGenerator();
    }
    
    protected abstract T getNewTUIDGenerator();
    
    protected abstract IResourceFiles getDefaultResourceFile();
    
    protected S loadResourceModel(IResourceFiles file) throws IOException {
        return ModelLoader.loadRelativeResourceModel(file, getClass());
    }
 
    protected <X extends EObject >void testCalculationAndResolution(EObject originalObject, Class<X> type) {
        assertThat(originalObject, instanceOf(type));
        testCalculationAndResolution(originalObject);
    }
    
    private void testCalculationAndResolution(EObject originalObject) {
        String tuid = tuidGenerator.calculateTUIDFromEObject(originalObject);
        assertNotEquals(tuidGenerator.getDefaultTUID(), tuid);
        
        EObject possibleMatch = tuidGenerator.resolveEObjectFromRootAndFullTUID(rootObject, tuid);
        
        assertNotNull(possibleMatch);
        assertEquals(originalObject, possibleMatch);
    }
    
}