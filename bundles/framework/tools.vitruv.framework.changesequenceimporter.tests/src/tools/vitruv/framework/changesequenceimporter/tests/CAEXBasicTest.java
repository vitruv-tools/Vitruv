package tools.vitruv.framework.changesequenceimporter.tests;

import static org.junit.Assert.*;
import org.junit.Test;

import CAEX.DocumentRoot;
import tools.vitruv.framework.change.description.TransactionalChange;
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder;
import tools.vitruv.framework.change.recording.impl.AtomicEmfChangeRecorderImpl;
import tools.vitruv.framework.util.datatypes.VURI;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.merge.BatchMerger;
import org.eclipse.emf.compare.merge.IBatchMerger;
import org.eclipse.emf.compare.merge.IMerger;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

// 1. modell 1 und 2 laden
// 2. programmatisch das diff mit emfcompare ausführen
// 3. Änderungen von Modell 2 auf Modell 1 (left) ausführen
// 4. bevor EMF Compare ausgeführt wird, Monitor aktivieren 
// 5. schauen, ob entsprechende Changes ausgeführt werden

public class CAEXBasicTest {

	private static final String PLUGIN_PATH = "platform:/plugin/tools.vitruv.framework.changesequenceimporter.tests";
	private static final String MODEL_PATH = "models";
	private static final String MODEL_CAEX_LEFT = MODEL_PATH + "/" + "leftModel.caex";
	private static final String MODEL_CAEX_RIGHT = MODEL_PATH + "/" + "rightModel.caex";

	// Obtain a new resource set
	ResourceSet resSet = new ResourceSetImpl();

	@Test
	public void test() {

		// Create new change recorder to monitor changes performed by EMFCompare
		AtomicEmfChangeRecorder changerec = new AtomicEmfChangeRecorderImpl();

		// Get the resource
		Resource resLeftModel = resSet.getResource(URI.createURI(MODEL_CAEX_LEFT), true);
		Resource resRightModel = resSet.getResource(URI.createURI(MODEL_CAEX_RIGHT), true);
		DocumentRoot rootLeft = (DocumentRoot) resLeftModel.getContents().get(0);
		DocumentRoot rootRight = (DocumentRoot) resRightModel.getContents().get(0);

		// begin recording of changes
		changerec.beginRecording(VURI.getInstance(resLeftModel), Collections.singletonList(resLeftModel));

		// Configure EMF Compare
		EMFCompare comparator = EMFCompare.builder().build();
		// Compare the two models
		IComparisonScope scope = new DefaultComparisonScope(rootLeft, rootRight, null);
		Comparison res = comparator.compare(scope);

		EList<Diff> diffs = res.getDifferences();
		IMerger.Registry mergerRegistry = IMerger.RegistryImpl.createStandaloneInstance();
		IBatchMerger merger = new BatchMerger(mergerRegistry);
		merger.copyAllRightToLeft(diffs, null); // new BasicMonitor();

		// end recording of changes
		List<TransactionalChange> changeseq = changerec.endRecording();

		fail("Not yet implemented");
	}

}
