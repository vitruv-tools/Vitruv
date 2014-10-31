package edu.kit.ipd.sdq.vitruvius.framework.model.monitor;

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

import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.ConcreteChangeClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postchange.CreatePackageClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postchange.DeletePackageClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postchange.RemoveCompilationUnitClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postchange.RenamePackageClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile.AddFieldClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile.AddImportClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile.AddMethodClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile.ChangeFieldModifiersClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile.ChangeFieldTypeClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile.ChangeMethodAnnotationClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile.ChangeMethodModifiersClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile.ChangeMethodParameterClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile.ChangeMethodReturnTypeClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile.ChangePackageDeclarationClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile.ChangeSupertypeClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile.ChangeTypeModifiersClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile.CreateTypeClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile.HigherOperationEventsFilter;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile.JavaDocClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile.RemoveFieldClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile.RemoveImportClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile.RemoveMethodClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile.RemoveTypeClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile.RenameFieldClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile.RenameMethodClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.postreconcile.RenameTypeClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeClassifyingEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.util.CompilationUnitUtil;
import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EclipseBridge;

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

    public ASTChangeListener(String... projectNames) {
        this.monitoredProjectNames = Arrays.asList(projectNames);
        this.postReconcileClassifiers = getPostReconcileClassifiers();
        this.postChangeClassifiers = getPostChangeClassifiers();
        this.listeners = new ArrayList<ChangeOperationListener>();

        this.previousState = new PreviousASTState(projectNames);
        this.withholdChangeHistory = new ChangeHistory(CHANGE_HISTORY_MINUTES);
        this.editCommandListener = new EditCommandListener(this);
        startListening();
        JavaCore.addElementChangedListener(this);
    }

    private static ConcreteChangeClassifier[] getPostReconcileClassifiers() {
        List<ConcreteChangeClassifier> classifiers = new ArrayList<ConcreteChangeClassifier>(Arrays.asList(
                new AddFieldClassifier(), new RemoveFieldClassifier(), new RenameFieldClassifier(),
                new ChangeFieldTypeClassifier(), new ChangeFieldModifiersClassifier(), new RenameMethodClassifier(),
                new AddMethodClassifier(), new RemoveMethodClassifier(), new ChangeMethodParameterClassifier(),
                new ChangeMethodModifiersClassifier(), new ChangeMethodReturnTypeClassifier(),
                new CreateTypeClassifier(), new RemoveTypeClassifier(), new RenameTypeClassifier(),
                new ChangeTypeModifiersClassifier(), new ChangePackageDeclarationClassifier(),
                new AddImportClassifier(), new RemoveImportClassifier(), new ChangeSupertypeClassifier(),
                new ChangeMethodAnnotationClassifier(), new JavaDocClassifier()));
        classifiers
                .addAll(getRegisteredClassifiers("edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.classifiers.postreconcile"));
        return classifiers.toArray(new ConcreteChangeClassifier[0]);
    }

    private static ConcreteChangeClassifier[] getPostChangeClassifiers() {
        List<ConcreteChangeClassifier> classifiers = new ArrayList<ConcreteChangeClassifier>(Arrays.asList(
                new RemoveCompilationUnitClassifier(), new RenamePackageClassifier(), new CreatePackageClassifier(),
                new DeletePackageClassifier()));
        classifiers
                .addAll(getRegisteredClassifiers("edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.classifiers.postchange"));
        return classifiers.toArray(new ConcreteChangeClassifier[0]);
    }

    @Override
    public void earlyStartup() {
    }

    @Override
    public void elementChanged(ElementChangedEvent event) {
        if (!this.listening)
            return; // ignore event if not listening

        IJavaElementDelta delta = event.getDelta();
        if (!isMonitoredProject(delta))
            return; // ignore events in unmonitored projects

        LOG.debug(delta.toString());
        this.lastChangeTime = System.nanoTime();
        event.getSource();

        List<ChangeClassifyingEvent> events = null;
        CompilationUnit currentCompilationUnit = CompilationUnitUtil.parseCompilationUnit(delta);
        if (event.getType() == ElementChangedEvent.POST_CHANGE) {
            events = classifyPostChange(delta, currentCompilationUnit);
        } else if (event.getType() == ElementChangedEvent.POST_RECONCILE) {
            events = classifyPostReconcile(delta, currentCompilationUnit);
        }

        if (!events.isEmpty()) {

            LOG.trace("+++Classified changes:+++");
            for (ChangeClassifyingEvent e : events)
                LOG.trace(e.toString());
        }

        List<ChangeClassifyingEvent> filteredEvents = HigherOperationEventsFilter.filter(events,
                this.withholdChangeHistory);
        if (!filteredEvents.isEmpty()) {
            LOG.trace("+++Filtered classified changes:+++");
            for (ChangeClassifyingEvent e : filteredEvents) {
                if (this.withholding)
                    LOG.trace("[WITHHOLD] ");
                LOG.trace(e.toString());
            }
        }

        this.previousState.update(currentCompilationUnit);
        if (this.listening) {
            if (!this.withholding) {
                notifyAll(filteredEvents);
            } else if (this.withholding) {
                this.withholdChangeHistory.update(filteredEvents);
                this.withholding = false;
            }
        }
    }

    private boolean isMonitoredProject(IJavaElementDelta delta) {
        IJavaElement element = delta.getElement();
        if (element == null)
            return false;
        IJavaProject project = element.getJavaProject();
        if (project == null && delta.getAffectedChildren().length == 0)
            return false;
        if (project == null)
            project = delta.getAffectedChildren()[0].getElement().getJavaProject();
        return this.monitoredProjectNames.contains(project.getElementName());
    }

    private List<ChangeClassifyingEvent> classifyPostReconcile(IJavaElementDelta delta,
            CompilationUnit currentCompilationUnit) {
        return classifyChange(delta, this.postReconcileClassifiers, currentCompilationUnit);
    }

    private List<ChangeClassifyingEvent> classifyPostChange(IJavaElementDelta delta,
            CompilationUnit currentCompilationUnit) {
        return classifyChange(delta, this.postChangeClassifiers, currentCompilationUnit);
    }

    private List<ChangeClassifyingEvent> classifyChange(IJavaElementDelta delta,
            ConcreteChangeClassifier[] classifiers, CompilationUnit currentCompilationUnit) {
        List<ChangeClassifyingEvent> events = new ArrayList<ChangeClassifyingEvent>();
        for (ConcreteChangeClassifier classifier : classifiers) {
            events.addAll(classifier.match(delta, currentCompilationUnit, this.previousState));
        }
        return events;
    }

    private void notifyAll(List<ChangeClassifyingEvent> events) {
        for (ChangeClassifyingEvent event : events) {
            notifyAll(event);
        }
    }

    private void notifyAll(ChangeClassifyingEvent event) {
        for (ChangeOperationListener listener : this.listeners) {
            listener.update(event);
        }
    }

    public void addListener(ChangeOperationListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(ChangeOperationListener listener) {
        this.listeners.remove(listener);
    }

    protected void withholdEventsOnce(boolean b) {
        this.withholding = b;
    }

    public PreviousASTState getPreviousASTState() {
        return this.previousState;
    }

    private static List<ConcreteChangeClassifier> getRegisteredClassifiers(String extensionPointName) {
        return EclipseBridge.getRegisteredExtensions(extensionPointName, VitruviusConstants.getExtensionPropertyName(),
                ConcreteChangeClassifier.class);
    }

}
