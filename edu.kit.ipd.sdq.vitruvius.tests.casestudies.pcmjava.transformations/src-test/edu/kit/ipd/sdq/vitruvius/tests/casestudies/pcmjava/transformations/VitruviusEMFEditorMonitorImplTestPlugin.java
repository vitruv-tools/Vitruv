package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations;

import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.Before;
import org.junit.Test;

import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.ChangeDescription2ChangeConverter;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;

public class VitruviusEMFEditorMonitorImplTestPlugin {

    private static final Logger logger = Logger
            .getLogger(VitruviusEMFEditorMonitorImplTestPlugin.class.getSimpleName());

    // private DefaultEditorPartAdapterFactoryImpl factory;
    int detectedChanges = 0;

    @Before
    public void setUp() throws Exception {
        TestUtil.initializeLogger();
    }

    @Test
    public void testChangeDescription2Change() throws Throwable {
        final ChangeRecorder changeRecorder = new ChangeRecorder();
        final ResourceSet rs = new ResourceSetImpl();
        final VURI vuri = VURI.getInstance(TestUtil.PROJECT_URI + "/modelTest/repo.repository");
        final Resource resource = rs.createResource(vuri.getEMFUri());
        final Repository repo = RepositoryFactory.eINSTANCE.createRepository();
        repo.setEntityName("name");
        EcoreResourceBridge.saveEObjectAsOnlyContent(repo, resource);
        resource.eAdapters().add(changeRecorder);
        changeRecorder.beginRecording(Collections.EMPTY_LIST);
        repo.setEntityName("TestNewName");
        final ChangeDescription changeDescription = changeRecorder.endRecording();
        final ChangeDescription2ChangeConverter cs2cc = new ChangeDescription2ChangeConverter();
        final List<Change> changes = cs2cc.getChanges(changeDescription);
        for (final Change change : changes) {
            logger.warn(change);
        }
        assertTrue("No changes detected ", 0 < changes.size());
    }
}
