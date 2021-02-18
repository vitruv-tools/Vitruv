package tools.vitruv.framework.tests.vsum;

import java.io.IOException;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout;

public class VsumFileSystemLayoutTest extends VsumTest {
	private static final Logger logger = Logger.getLogger(VsumFileSystemLayoutTest.class);

	@Test
	public void testSaveAndLoadVURIs() throws IOException {
		final int nrOfVURIs = 1000;
		Set<VURI> vuris = PersistentTestUtil.createDummyVURIs(getCurrentProjectFolder(), nrOfVURIs);

		long start = System.currentTimeMillis();
		// save to disk
		VsumFileSystemLayout fsHelper = new VsumFileSystemLayout(getCurrentProjectFolder());
		fsHelper.prepare();
		fsHelper.saveVsumVURIs(vuris);
		long durationForSave = System.currentTimeMillis() - start;
		long startLoad = System.currentTimeMillis();
		// load from Disk
		Set<VURI> loadedVURIs = fsHelper.loadVsumVURIs();

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
