package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changeapplication;

import java.net.URL;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.After;
import org.junit.Before;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.ChangeDescription2ChangeConverter;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels.Models;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.utils.BasicTestCase;

public abstract class AbstractChangeApplicatorTests<T extends EObject> extends BasicTestCase {
    private boolean hasRegisteredMetaModels = false;

    protected Resource sourceRes;
    protected Resource targetRes;
    protected T sourceRoot;
    protected T targetRoot;

    private ChangeRecorder changeRecorder;

    private ChangeRecorder sourceResettingCR;
    private ChangeRecorder targetResettingCR;

    @Before
    public void setUp() {
        System.err.println("setting up");

        if (!hasRegisteredMetaModels) {
            registerMetamodels();
            hasRegisteredMetaModels = true;
        }

        sourceRes = Models.loadModel(getModel());
        targetRes = Models.loadModel(getModel());

        sourceRoot = (T) sourceRes.getContents().get(0);
        targetRoot = (T) targetRes.getContents().get(0);

        sourceResettingCR = new ChangeRecorder(sourceRes);
        targetResettingCR = new ChangeRecorder(targetRes);

        changeRecorder = new ChangeRecorder(sourceRes);
    }

    protected abstract URL getModel();

    protected abstract void registerMetamodels();

    @After
    public void tearDown() {
        System.err.println("tearing down");

        sourceResettingCR.endRecording().apply();
        targetResettingCR.endRecording().apply();
        sourceRes.unload();
        targetRes.unload();
    }

    protected List<Change> getChangesAndEndRecording() {
        ChangeDescription cd = changeRecorder.endRecording();
        ChangeDescription2ChangeConverter converter = new ChangeDescription2ChangeConverter();
        cd.applyAndReverse();
        List<Change> changes = converter.getChanges(cd);
        cd.applyAndReverse();

        return changes;
    }

    protected void assertSourceAndTargetStructuralEquality() {
        assert EcoreUtil.equals(sourceRoot, targetRoot) : "Models are not equal after synchronization";
    }

    protected void synchronizeChangesAndAssertEquality() {
        List<Change> changes = getChangesAndEndRecording();
        System.err.println("number of changes: " + changes.size());

        ChangeApplicator applicator = new ChangeApplicator(sourceRes, changes);
        applicator.applyChanges(targetRes);

        assertSourceAndTargetStructuralEquality();
    }
}
