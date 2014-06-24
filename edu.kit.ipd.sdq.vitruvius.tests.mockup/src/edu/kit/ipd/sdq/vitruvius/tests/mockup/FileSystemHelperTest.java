package edu.kit.ipd.sdq.vitruvius.tests.mockup;

import java.util.Set;

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
        final int nrOfVURIs = 1000;
        Set<VURI> vuris = PersistentTestUtil.createDummyVURIs(nrOfVURIs);

        long start = System.currentTimeMillis();
        // save to disk
        FileSystemHelper.saveVSUMvURIsToFile(vuris);
        long durationForSave = System.currentTimeMillis() - start;
        long startLoad = System.currentTimeMillis();
        // load from Disk
        Set<VURI> loadedVURIs = FileSystemHelper.loadVSUMvURIsFromFile();

        long durationForLoad = System.currentTimeMillis() - startLoad;
        long durationForLoadAndSave = System.currentTimeMillis() - start;
        String vuriCount = "" + nrOfVURIs + " VURIs: ";
        System.out.println("Duration for save " + vuriCount + durationForSave);
        System.out.println("Duration for load " + vuriCount + durationForLoad);
        System.out.println("Duration for save and load " + vuriCount + durationForLoadAndSave);
        // compare
        PersistentTestUtil.assertEqualsSets(vuris, loadedVURIs);
    }

}
