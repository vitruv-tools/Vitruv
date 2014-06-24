package edu.kit.ipd.sdq.vitruvius.tests.mockup;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.helper.FileSystemHelper;

public class FileSystemHelperTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testSaveAndLoadVURIs() {
        Set<VURI> vuris = new HashSet<VURI>();
        for (int i = 0; i < 10; ++i) {
            vuris.add(VURI.getInstance("testInstance_" + i));
        }
        Set<String> stringSet = new HashSet<String>();
        for (VURI vuri : vuris) {
            stringSet.add(vuri.getEMFUri().toString());
        }

        // save to disk
        FileSystemHelper.saveVSUMvURIsToFile(vuris);
        // load from Disk
        Set<VURI> loadedVURIs = FileSystemHelper.loadVSUMvURIsFromFile();

        // compare
        assertEquals("Loaded VURIs must have the same size as saved VURIs", loadedVURIs.size(), vuris.size());
        Iterator<VURI> loadedVURIsIterator = loadedVURIs.iterator();
        while (loadedVURIsIterator.hasNext()) {
            URI loadedURI = loadedVURIsIterator.next().getEMFUri();
            Iterator<VURI> vuriIterator = vuris.iterator();
            boolean found = false;
            URI uri = null;
            while (vuriIterator.hasNext() && !found) {
                uri = vuriIterator.next().getEMFUri();
                if (loadedURI.equals(uri)) {
                    // uri found in loaded URI
                    found = true;
                }
            }
            if (!found) {
                throw new RuntimeException("URI (" + uri + ") not found in loaded URIs: " + loadedURI);
            }

        }
    }
}
