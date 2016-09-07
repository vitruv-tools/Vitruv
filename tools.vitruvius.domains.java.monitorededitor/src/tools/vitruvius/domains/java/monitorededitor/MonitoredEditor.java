package tools.vitruvius.domains.java.monitorededitor;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.ui.IStartup;

import tools.vitruvius.domains.java.monitorededitor.astchangelistener.ASTChangeListener;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.ChangeOperationListener;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEvent;
import tools.vitruvius.domains.java.monitorededitor.refactoringlistener.RefactoringChangeListener;
import tools.vitruvius.domains.java.monitorededitor.refactoringlistener.RefactoringStatusListener;
import tools.vitruvius.framework.change.description.CompositeChange;
import tools.vitruvius.framework.change.description.VitruviusChangeFactory;
import tools.vitruvius.framework.change.description.VitruviusChange;
import tools.vitruvius.framework.metamodel.ModelProviding;
import tools.vitruvius.framework.monitorededitor.AbstractMonitoredEditor;
import tools.vitruvius.framework.modelsynchronization.ChangeSynchronizing;
import tools.vitruvius.framework.userinteraction.UserInteracting;
import tools.vitruvius.framework.userinteraction.UserInteractionType;
import tools.vitruvius.framework.userinteraction.impl.UserInteractor;

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
public class MonitoredEditor extends AbstractMonitoredEditor
        implements UserInteracting, ChangeOperationListener, ChangeSubmitter, IStartup {

    private final Logger log = Logger.getLogger(MonitoredEditor.class);

    /**
     * @author messinger
     *
     *         Rudimentary time measurement for performance evaluation. This is a DTO class.
     */
    static class TimeMeasurement {
        long createASTchangeEvent, createEMFchange, total;

        TimeMeasurement(final long t0, final long t1) {
            final long now = System.nanoTime();
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

    private final ASTChangeListener astListener;
    private final RefactoringChangeListener refactoringListener;
    private final ChangeResponder changeResponder;
    private final RefactoringStatusListener refactoringStatusListener = new RefactoringStatusListener() {
        // switch off AST Listening while a refactoring is being performed

        @Override
        public void preExecute() {
            MonitoredEditor.this.log.info("Stop AST Listening");
            MonitoredEditor.this.stopASTListening();
            MonitoredEditor.this.startCollectInCompositeChange();
        }

        @Override
        public void postExecute() {
            MonitoredEditor.this.log.info("Start AST Listening");
            MonitoredEditor.this.startASTListening();
            MonitoredEditor.this.lastRefactoringTime = System.nanoTime();
        }

        @Override
        public void aboutPostExecute() {
            MonitoredEditor.this.stopCollectInCompositeChange();
        }
    };

    private final String[] monitoredProjectNames;
    private final UserInteracting userInteractor;
    private long lastRefactoringTime;
    protected boolean refactoringInProgress = false;
    private CompositeChange changeStash = null;
    private boolean reportChanges;
    private static final String MY_MONITORED_PROJECT = "hadoop-hdfs";// "FooProject";

    // "MediaStore";

    public MonitoredEditor() {
        this(new ChangeSynchronizing() {

            @Override
            public List<List<VitruviusChange>> synchronizeChange(final VitruviusChange changes) {
                return null;
            }

        }, null, MY_MONITORED_PROJECT);
        this.reportChanges = true;
    }

    protected void stopCollectInCompositeChange() {
        this.log.debug("Stop collecting Changes in CompositeChange stash and submit stash");
        this.triggerChange(null);
        this.refactoringInProgress = false;
    }

    protected void startCollectInCompositeChange() {
        this.log.debug("Start collecting Changes in CompositeChange stash");
        this.changeStash = VitruviusChangeFactory.getInstance().createCompositeChange();
        this.refactoringInProgress = true;
    }

    public MonitoredEditor(final ChangeSynchronizing changeSynchronizing, final ModelProviding modelProviding,
            final String... monitoredProjectNames) {
        super(changeSynchronizing, modelProviding);

        this.configureLogger();

        this.monitoredProjectNames = monitoredProjectNames;
        this.astListener = new ASTChangeListener(this.monitoredProjectNames);
        this.astListener.addListener(this);
        this.refactoringListener = RefactoringChangeListener.getInstance(this.monitoredProjectNames);
        this.refactoringListener.addListener(this.refactoringStatusListener);
        this.refactoringListener.addListener(this);
        // dummy CorrespondenceModel
        // this.buildCorrespondenceModel();
        this.changeResponder = new ChangeResponder(this);
        this.userInteractor = new UserInteractor();
        this.reportChanges = true;
        // this.addDummyCorrespondencesForAllInterfaceMethods();
    }

    protected void revokeRegistrations() {
        this.astListener.removeListener(this);
        this.astListener.revokeRegistrations();
        this.refactoringListener.removeListener(this);
        this.refactoringListener.removeListener(this.refactoringStatusListener);
        RefactoringChangeListener.destroyInstance();
    }

    private void configureLogger() {
        Logger.getRootLogger().setLevel(Level.DEBUG);
        Logger.getRootLogger().removeAllAppenders();
        Logger.getRootLogger()
                .addAppender(new ConsoleAppender(new PatternLayout("[%-5p] %d{HH:mm:ss,SSS} %-30C{1} - %m%n")));
        try {
            final TimeFileLogAppender appender = TimeFileLogAppender.createInstanceFor(MY_MONITORED_PROJECT,
                    // "C:/Users/messinger/DominikMessinger/EvaluationData/hadoop-hdfs_monitor-overhead-measurements/time_measurements");
                    "EvaluationData/hadoop-hdfs_monitor-overhead-measurements/time_measurements");
            this.log.addAppender(appender);
        } catch (final IOException e) {
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
    public void submitChange(final VitruviusChange change) {

        // basic time measurement for thesis evaluation
        final long million = 1000 * 1000;

        if (this.astListener.lastChangeTime >= 0) {
            final TimeMeasurement time = new TimeMeasurement(this.astListener.lastChangeTime,
                    ChangeResponder.lastCallTime);
            this.log.debug("MonitoredEditor required " + time.total / million
                    + " msec for the last *AST* change observation.");
            this.log.info(time.toString());
        } else if (this.lastRefactoringTime >= 0) {
            final TimeMeasurement time = new TimeMeasurement(this.lastRefactoringTime, ChangeResponder.lastCallTime);
            this.log.debug("MonitoredEditor required " + time.total / million
                    + " msec for the last *refactoring* change observation.");
            this.log.info(time.toString());
        }
        this.astListener.lastChangeTime = -1;
        this.lastRefactoringTime = -1;

        this.synchronizeChangeOrAddToCompositeChange(change);
    }

    private void synchronizeChangeOrAddToCompositeChange(final VitruviusChange change) {
        if (this.changeStash == null) {
            this.triggerChange(change);
        }
    }

    @Override
    public void earlyStartup() {
        // TODO Auto-generated method stub
        System.err.println("MonitoredEditor plugin - earlyStartup");
    }

    @Override
    public void showMessage(final UserInteractionType type, final String message) {
        this.userInteractor.showMessage(type, message);
    }

    @Override
    public int selectFromMessage(final UserInteractionType type, final String message,
            final String... selectionDescriptions) {
        return this.userInteractor.selectFromMessage(type, message, selectionDescriptions);
    }

    @Override
    public String getTextInput(final String msg) {
        return this.userInteractor.getTextInput(msg);
    }

    public void startASTListening() {
        this.astListener.startListening();
    }

    public void stopASTListening() {
        this.astListener.stopListening();
    }

    protected void triggerChange(final VitruviusChange change) {
        if (!this.reportChanges) {
            this.log.trace("Do not report change : " + change + " because report changes is set to false.");
            return;
        }
        final Job triggerChangeJob = new Job("Code monitor trigger job") {

            @Override
            protected IStatus run(final IProgressMonitor monitor) {
                if (null != change) {
                    MonitoredEditor.this.changeSynchronizing.synchronizeChange(change);
                } else {
                    MonitoredEditor.this.changeSynchronizing.synchronizeChange(MonitoredEditor.this.changeStash);
                }
                return Status.OK_STATUS;
            }
        };
        triggerChangeJob.setPriority(Job.SHORT);
        triggerChangeJob.schedule();

    }

    public void setReportChanges(final boolean reportChanges) {
        this.reportChanges = reportChanges;
    }

    @Override
    public URI selectURI(final String message) {
        return this.userInteractor.selectURI(message);
    }

}
