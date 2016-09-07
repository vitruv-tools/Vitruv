package tools.vitruv.domains.java.monitorededitor.astchangelistener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.ui.IStartup;

import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.ConcreteChangeClassifier;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postchange.CreatePackageClassifier;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postchange.DeletePackageClassifier;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postchange.RemoveCompilationUnitClassifier;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postchange.RenamePackageClassifier;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile.AddFieldClassifier;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile.AddImportClassifier;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile.AddMethodClassifier;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile.ChangeAnnotationClassifier;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile.ChangeFieldModifiersClassifier;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile.ChangeFieldTypeClassifier;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile.ChangeMethodModifiersClassifier;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile.ChangeMethodParameterClassifier;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile.ChangeMethodReturnTypeClassifier;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile.ChangePackageDeclarationClassifier;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile.ChangeSupertypeClassifier;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile.ChangeTypeModifiersClassifier;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile.CreateTypeClassifier;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile.HigherOperationEventsFilter;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile.JavaDocClassifier;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile.RemoveFieldClassifier;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile.RemoveImportClassifier;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile.RemoveMethodClassifier;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile.RemoveTypeClassifier;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile.RenameFieldClassifier;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile.RenameMethodClassifier;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile.RenameTypeClassifier;
import tools.vitruv.domains.java.monitorededitor.changeclassification.ChangeOperationListener;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEvent;
import tools.vitruv.domains.java.monitorededitor.javamodel2ast.CompilationUnitUtil;
import tools.vitruv.framework.util.VitruviusConstants;
import tools.vitruv.framework.util.bridges.EclipseBridge;

/**
 * The {@link ASTChangeListener} reacts to changes in the Eclipse JDT AST and generates
 * {@link ChangeClassifyingEvent}s from the AST {@link IJavaElementDelta}. Registered
 * {@link ChangeOperationListener}s are notified when new code changes occur. The ASTChangeListener
 * holds an array of {@link ConcreteChangeClassifier}s who are responsible for classifying changes
 * given the AST delta and the current and previous AST state.
 */
public class ASTChangeListener implements IStartup, IElementChangedListener {

    private static final Logger LOG = Logger.getLogger(ASTChangeListener.class);
    private final ConcreteChangeClassifier[] postReconcileClassifiers;
    private final ConcreteChangeClassifier[] postChangeClassifiers;
    private final PreviousASTState previousState;
    private final ChangeHistory withholdChangeHistory;
    private final List<ChangeOperationListener> listeners;

    private static final int CHANGE_HISTORY_MINUTES = 4;

    private boolean listening = false;
    private boolean withholding = false;
    private final EditCommandListener editCommandListener;
    private final List<String> monitoredProjectNames;

    public long lastChangeTime;

    public boolean isListening() {
        return this.listening;
    }

    public void startListening() {
        this.editCommandListener.startListening();
        this.listening = true;
    }

    public void stopListening() {
        this.editCommandListener.stopListening();
        this.listening = false;
    }

    public ASTChangeListener(final String... projectNames) {
        this.monitoredProjectNames = Arrays.asList(projectNames);
        this.postReconcileClassifiers = getPostReconcileClassifiers();
        this.postChangeClassifiers = getPostChangeClassifiers();
        this.listeners = new ArrayList<ChangeOperationListener>();

        this.previousState = new PreviousASTState(projectNames);
        this.withholdChangeHistory = new ChangeHistory(CHANGE_HISTORY_MINUTES);
        this.editCommandListener = new EditCommandListener(this);
        this.startListening();
        JavaCore.addElementChangedListener(this);
    }

    public void revokeRegistrations() {
        this.editCommandListener.revokeRegistrations();
        JavaCore.removeElementChangedListener(this);
    }

    private static ConcreteChangeClassifier[] getPostReconcileClassifiers() {
        final List<ConcreteChangeClassifier> classifiers = new ArrayList<ConcreteChangeClassifier>(Arrays.asList(
                new AddFieldClassifier(), new RemoveFieldClassifier(), new RenameFieldClassifier(),
                new ChangeFieldTypeClassifier(), new ChangeFieldModifiersClassifier(), new RenameMethodClassifier(),
                new AddMethodClassifier(), new RemoveMethodClassifier(), new ChangeMethodParameterClassifier(),
                new ChangeMethodModifiersClassifier(), new ChangeMethodReturnTypeClassifier(),
                new CreateTypeClassifier(), new RemoveTypeClassifier(), new RenameTypeClassifier(),
                new ChangeTypeModifiersClassifier(), new ChangePackageDeclarationClassifier(),
                new AddImportClassifier(), new RemoveImportClassifier(), new ChangeSupertypeClassifier(),
                new ChangeAnnotationClassifier(), new JavaDocClassifier()));
        classifiers.addAll(getRegisteredClassifiers(
                "tools.vitruv.domains.java.monitorededitor.astchangelistener.postreconcile"));
        return classifiers.toArray(new ConcreteChangeClassifier[0]);
    }

    private static ConcreteChangeClassifier[] getPostChangeClassifiers() {
        final List<ConcreteChangeClassifier> classifiers = new ArrayList<ConcreteChangeClassifier>(
                Arrays.asList(new RemoveCompilationUnitClassifier(), new RenamePackageClassifier(),
                        new CreatePackageClassifier(), new DeletePackageClassifier()));
        classifiers.addAll(getRegisteredClassifiers(
                "tools.vitruv.domains.java.monitorededitor.astchangelistener.postchange"));
        return classifiers.toArray(new ConcreteChangeClassifier[0]);
    }

    @Override
    public void earlyStartup() {
    }

    @Override
    public void elementChanged(final ElementChangedEvent event) {
        if (!this.listening) {
            return; // ignore event if not listening
        }

        final IJavaElementDelta delta = event.getDelta();
        if (!this.isMonitoredProject(delta)) {
            return; // ignore events in unmonitored projects
        }

        LOG.debug(delta.toString());
        this.lastChangeTime = System.nanoTime();
        event.getSource();

        List<ChangeClassifyingEvent> events = null;
        final CompilationUnit currentCompilationUnit = CompilationUnitUtil.parseCompilationUnit(delta);
        if (event.getType() == ElementChangedEvent.POST_CHANGE) {
            events = this.classifyPostChange(delta, currentCompilationUnit);
        } else if (event.getType() == ElementChangedEvent.POST_RECONCILE) {
            events = this.classifyPostReconcile(delta, currentCompilationUnit);
        }

        if (!events.isEmpty()) {

            LOG.trace("+++Classified changes:+++");
            for (final ChangeClassifyingEvent e : events) {
                LOG.trace(e.toString());
            }
        }

        final List<ChangeClassifyingEvent> filteredEvents = HigherOperationEventsFilter.filter(events,
                this.withholdChangeHistory);
        if (!filteredEvents.isEmpty()) {
            LOG.trace("+++Filtered classified changes:+++");
            for (final ChangeClassifyingEvent e : filteredEvents) {
                if (this.withholding) {
                    LOG.trace("[WITHHOLD] ");
                }
                LOG.trace(e.toString());
            }
        }

        this.previousState.update(currentCompilationUnit);
        if (this.listening) {
            if (!this.withholding) {
                this.notifyAll(filteredEvents);
            } else if (this.withholding) {
                this.withholdChangeHistory.update(filteredEvents);
                this.withholding = false;
            }
        }
    }

    private boolean isMonitoredProject(final IJavaElementDelta delta) {
        final IJavaElement element = delta.getElement();
        if (element == null) {
            return false;
        }
        IJavaProject project = element.getJavaProject();
        if (project == null && delta.getAffectedChildren().length == 0) {
            return false;
        }
        if (project == null) {
            project = delta.getAffectedChildren()[0].getElement().getJavaProject();
        }
        return this.monitoredProjectNames.contains(project.getElementName());
    }

    private List<ChangeClassifyingEvent> classifyPostReconcile(final IJavaElementDelta delta,
            final CompilationUnit currentCompilationUnit) {
        return this.classifyChange(delta, this.postReconcileClassifiers, currentCompilationUnit);
    }

    private List<ChangeClassifyingEvent> classifyPostChange(final IJavaElementDelta delta,
            final CompilationUnit currentCompilationUnit) {
        return this.classifyChange(delta, this.postChangeClassifiers, currentCompilationUnit);
    }

    private List<ChangeClassifyingEvent> classifyChange(final IJavaElementDelta delta,
            final ConcreteChangeClassifier[] classifiers, final CompilationUnit currentCompilationUnit) {
        final List<ChangeClassifyingEvent> events = new ArrayList<ChangeClassifyingEvent>();
        for (final ConcreteChangeClassifier classifier : classifiers) {
            events.addAll(classifier.match(delta, currentCompilationUnit, this.previousState));
        }
        return events;
    }

    private void notifyAll(final List<ChangeClassifyingEvent> events) {
        for (final ChangeClassifyingEvent event : events) {
            this.notifyAll(event);
        }
    }

    private void notifyAll(final ChangeClassifyingEvent event) {
        for (final ChangeOperationListener listener : this.listeners) {
            listener.update(event);
        }
    }

    public void addListener(final ChangeOperationListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(final ChangeOperationListener listener) {
        this.listeners.remove(listener);
    }

    protected void withholdEventsOnce(final boolean b) {
        this.withholding = b;
    }

    public PreviousASTState getPreviousASTState() {
        return this.previousState;
    }

    private static List<ConcreteChangeClassifier> getRegisteredClassifiers(final String extensionPointName) {
        return EclipseBridge.getRegisteredExtensions(extensionPointName, VitruviusConstants.getExtensionPropertyName(),
                ConcreteChangeClassifier.class);
    }

}
