package edu.kit.ipd.sdq.vitruvius.tests.infrastructure;

import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.helper.FileSystemHelper;
import edu.kit.ipd.sdq.vitruvius.tests.mockup.PersistentTestUtil;

public class FileSystemHelperTest {

    private static final Logger logger = Logger.getLogger(FileSystemHelperTest.class.getSimpleName());

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
        logger.info("Duration for save " + vuriCount + durationForSave);
        logger.info("Duration for load " + vuriCount + durationForLoad);
        logger.info("Duration for save and load " + vuriCount + durationForLoadAndSave);
        // compare
        PersistentTestUtil.assertEqualsSets(vuris, loadedVURIs);
    }

}
