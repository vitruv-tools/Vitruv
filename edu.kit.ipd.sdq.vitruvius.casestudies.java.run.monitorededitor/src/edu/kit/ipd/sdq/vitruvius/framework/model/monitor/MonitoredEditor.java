package edu.kit.ipd.sdq.vitruvius.framework.model.monitor;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.ui.IStartup;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelCopyProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeClassifyingEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.userinteractor.UserInteractor;
import edu.kit.ipd.sdq.vitruvius.framework.run.monitorededitor.AbstractMonitoredEditor;

/**
 * @author messinger
 *         <p>
 *         Extends {@link AbstractMonitoredEditor} and implements {@link UserInteracting} by
 *         delegation to a {@link UserInteractor}. The {@link MonitoredEditor} uses the
 *         {@link ASTChangeListener} and the {@link RefactoringChangeListener} to monitor changes in
 *         Java source code. Both listeners generate {@link ChangeClassifyingEvent}s which are
 *         transferred to the {@link ChangeResponder} who builds and returns {@link EMFModelChange}
 *         objects. These change objects are then used by the {@link ChangeSynchronizing} to
 *         propagate changes to other with the code affiliated EMF models.
 * 
 */
public class MonitoredEditor extends AbstractMonitoredEditor implements UserInteracting, ChangeOperationListener,
        ChangeSubmitter, IStartup {

    private final Logger log = Logger.getLogger(MonitoredEditor.class);

    /**
     * @author messinger
     * 
     *         Rudimentary time measurement for performance evaluation. This is a DTO class.
     */
    static class TimeMeasurement {
        long createASTchangeEvent, createEMFchange, total;

        TimeMeasurement(long t0, long t1) {
            long now = System.nanoTime();
            this.total = now - t0;
            this.createASTchangeEvent = t1 - t0;
            this.createEMFchange = now - t1;
        }

        @Override
        public String toString() {
            // return "TimeMeasurement [createASTchangeEvent=" + this.createASTchangeEvent +
            // ", createEMFchange="
            // + this.createEMFchange + ", total=" + this.total + "]";
            return "TimeMeasurement:" + this.createASTchangeEvent + "," + this.createEMFchange + "," + this.total;
        }

    }

    protected final ASTChangeListener astListener;
    private final RefactoringChangeListener refactoringListener;
    // private CorrespondenceInstance correspondenceInstance;
    private final ChangeResponder changeResponder;
    private final RefactoringStatusListener refactoringStatusListener = new RefactoringStatusListener() {
        // switch off AST Listening while a refactoring is being performed

        @Override
        public void preExecute() {
            MonitoredEditor.this.log.info("Stop AST Listening");
            MonitoredEditor.this.astListener.stopListening();
            MonitoredEditor.this.startCollectInCompositeChange();
        }

        @Override
        public void postExecute() {
            MonitoredEditor.this.log.info("Start AST Listening");
            MonitoredEditor.this.astListener.startListening();
            MonitoredEditor.this.stopCollectInCompositeChange();
            MonitoredEditor.this.lastRefactoringTime = System.nanoTime();
        }
    };

    private final String[] monitoredProjectNames;
    private final UserInteracting userInteractor;
    private long lastRefactoringTime;
    private CompositeChange changeStash = null;
    private static final String MY_MONITORED_PROJECT = "hadoop-hdfs"; // "FooProject";
                                                                      // "MediaStore";

    public MonitoredEditor() {
        this(new ChangeSynchronizing() {

            @Override
            public void synchronizeChanges(List<Change> changes) {
                // TODO Auto-generated method stub

            }

            @Override
            public void synchronizeChange(Change change) {
                // TODO Auto-generated method stub

            }

        }, null, MY_MONITORED_PROJECT);
        // this(SyncManagerImpl.getSyncManagerInstance(), SyncManagerImpl
        // .getSyncManagerInstance().getModelProviding());
    }

    protected void stopCollectInCompositeChange() {
        this.log.debug("Stop collecting Changes in CompositeChange stash and submit stash");
        this.changeSynchronizing.synchronizeChange(this.changeStash);
        this.changeStash = null;
    }

    protected void startCollectInCompositeChange() {
        this.log.debug("Start collecting Changes in CompositeChange stash");
        this.changeStash = new CompositeChange(new Change[] {});
    }

    public MonitoredEditor(final ChangeSynchronizing changeSynchronizing, final ModelCopyProviding modelCopyProviding,
            String... monitoredProjectNames) {
        super(changeSynchronizing, modelCopyProviding);

        configureLogger();

        this.monitoredProjectNames = monitoredProjectNames;
        this.astListener = new ASTChangeListener(this.monitoredProjectNames);
        this.astListener.addListener(this);
        this.refactoringListener = RefactoringChangeListener.getInstance(this.monitoredProjectNames);
        this.refactoringListener.addListener(this.refactoringStatusListener);
        this.refactoringListener.addListener(this);
        // dummy CorrespondenceInstance
        // this.buildCorrespondenceInstance();
        this.changeResponder = new ChangeResponder(this);
        this.userInteractor = new UserInteractor();
        // this.addDummyCorrespondencesForAllInterfaceMethods();
    }

    private void configureLogger() {
        Logger.getRootLogger().setLevel(Level.DEBUG);
        Logger.getRootLogger().removeAllAppenders();
        Logger.getRootLogger().addAppender(
                new ConsoleAppender(new PatternLayout("[%-5p] %d{HH:mm:ss,SSS} %-30C{1} - %m%n")));
        try {
            TimeFileLogAppender appender = TimeFileLogAppender
                    .createInstanceFor(MY_MONITORED_PROJECT,
                            "C:/Users/messinger/DominikMessinger/EvaluationData/hadoop-hdfs_monitor-overhead-measurements/time_measurements");
            this.log.addAppender(appender);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getMonitoredProjectNames() {
        return this.monitoredProjectNames;
    }

    @Override
    public void update(final ChangeClassifyingEvent event) {
        this.log.info("React to " + event.toString());
        event.accept(this.changeResponder);
    }

    @Override
    public void submitChange(final Change change) {

        // basic time measurement for thesis evaluation
        long million = 1000 * 1000;

        if (this.astListener.lastChangeTime >= 0) {
            TimeMeasurement time = new TimeMeasurement(this.astListener.lastChangeTime, ChangeResponder.lastCallTime);
            this.log.debug("MonitoredEditor required " + (time.total / million)
                    + " msec for the last *AST* change observation.");
            this.log.info(time.toString());
        } else if (this.lastRefactoringTime >= 0) {
            TimeMeasurement time = new TimeMeasurement(this.lastRefactoringTime, ChangeResponder.lastCallTime);
            this.log.debug("MonitoredEditor required " + (time.total / million)
                    + " msec for the last *refactoring* change observation.");
            this.log.info(time.toString());
        }
        this.astListener.lastChangeTime = -1;
        this.lastRefactoringTime = -1;

        synchronizeChangeOrAddToCompositeChange(change);
    }

    private void synchronizeChangeOrAddToCompositeChange(Change change) {
        if (this.changeStash == null)
            this.changeSynchronizing.synchronizeChange(change);
    }

    @Override
    public void earlyStartup() {
        // TODO Auto-generated method stub
        System.err.println("MonitoredEditor plugin - earlyStartup");
    }

    @Override
    public void showMessage(UserInteractionType type, String message) {
        this.userInteractor.showMessage(type, message);
    }

    @Override
    public int selectFromMessage(UserInteractionType type, String message, String... selectionDescriptions) {
        return this.userInteractor.selectFromMessage(type, message, selectionDescriptions);
    }

    @Override
    public int selectFromModel(UserInteractionType type, String message, ModelInstance... modelInstances) {
        return this.userInteractor.selectFromModel(type, message, modelInstances);
    }

}
