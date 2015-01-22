package edu.kit.ipd.sdq.seifermann.thesis.monitorededitor.java.changeclassifiers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

import edu.kit.ipd.sdq.seifermann.thesis.monitorededitor.java.events.MethodBodyChangedEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.PreviousASTState;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification.ConcreteChangeClassifier;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeClassifyingEvent;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

/**
 * Extension classifier for the method body change event.
 * 
 * @author Stephan Seifermann
 *
 */
public class MethodBodyChangedClassifier implements ConcreteChangeClassifier {

    @Override
    public List<? extends ChangeClassifyingEvent> match(IJavaElementDelta delta,
            CompilationUnit currentCompilationUnit, PreviousASTState previousState) {

        if (delta.getAffectedChildren() != null && delta.getAffectedChildren().length == 0
                && (delta.getFlags() & (IJavaElementDelta.F_FINE_GRAINED | IJavaElementDelta.F_CONTENT)) != 0) {
            return findChangedMethodBodyEvents(previousState.getOldCompilationUnit(currentCompilationUnit),
                    currentCompilationUnit);
        }

        return new ArrayList<ChangeClassifyingEvent>();
    }

    /**
     * AST visitor implementation, which finds all contained method declarations and stores them.
     * 
     * @author Stephan Seifermann
     *
     */
    private static class ASTMethodVisitor extends ASTVisitor {
        private final List<MethodDeclaration> methodDeclarations = new ArrayList<MethodDeclaration>();

        @Override
        public boolean visit(MethodDeclaration md) {
            methodDeclarations.add(md);
            return false;
        }

        /**
         * @return All collected method declarations.
         */
        public List<MethodDeclaration> getMethodDeclarations() {
            return methodDeclarations;
        }
    }

    /**
     * Finds changed method bodies and creates events. First all method declarations are found.
     * Second the methods are matched, compared and events are created.
     * 
     * @param old
     *            The unchanged compilation unit.
     * @param changed
     *            The possibly changed compilation unit.
     * @return The set of change events.
     */
    private List<ChangeClassifyingEvent> findChangedMethodBodyEvents(CompilationUnit old, CompilationUnit changed) {
        ASTMethodVisitor oldVisitor = new ASTMethodVisitor();
        old.accept(oldVisitor);
        List<MethodDeclaration> oldMethodDeclarations = oldVisitor.getMethodDeclarations();

        ASTMethodVisitor changedVisitor = new ASTMethodVisitor();
        changed.accept(changedVisitor);
        List<MethodDeclaration> changedMethodDeclarations = changedVisitor.getMethodDeclarations();

        if (oldMethodDeclarations.size() != changedMethodDeclarations.size()) {
            return new ArrayList<ChangeClassifyingEvent>();
        }

        return findChangedMethodBodyEvents(oldMethodDeclarations, changedMethodDeclarations);
    }

    /**
     * Finds changed method bodies and creates event for every detected change. First the matching
     * method declarations are found by comparing their signature. Second the bodies are compared
     * and in case of a difference an event is created.
     * 
     * @param oldMethodDeclarations
     *            The unchanged method declarations.
     * @param changedMethodDeclarations
     *            The method declarations which might be changed.
     * @return The set of change events.
     */
    private List<ChangeClassifyingEvent> findChangedMethodBodyEvents(List<MethodDeclaration> oldMethodDeclarations,
            List<MethodDeclaration> changedMethodDeclarations) {
        List<ChangeClassifyingEvent> events = new ArrayList<ChangeClassifyingEvent>();

        List<Pair<MethodDeclaration, MethodDeclaration>> matches = new ArrayList<Pair<MethodDeclaration, MethodDeclaration>>();
        for (MethodDeclaration oldMd : oldMethodDeclarations) {
            for (MethodDeclaration changedMd : changedMethodDeclarations) {
                if (matchesSignature(oldMd, changedMd)) {
                    matches.add(new Pair<MethodDeclaration, MethodDeclaration>(oldMd, changedMd));
                    break;
                }
            }
        }

        if (matches.size() != oldMethodDeclarations.size()) {
            return events;
        }

        for (Pair<MethodDeclaration, MethodDeclaration> match : matches) {
            if (!AST_MATCHER.match(match.getFirst(), match.getSecond())) {
                events.add(new MethodBodyChangedEvent(match.getFirst(), match.getSecond()));
            }
        }
        return events;
    }

    /**
     * Compares the signature of two method declarations and indicates if they are compatible.
     * 
     * @param md1
     *            The first method declaration.
     * @param md2
     *            The second method declaration.
     * @return True if the signatures are compatible.
     */
    private static boolean matchesSignature(MethodDeclaration md1, MethodDeclaration md2) {
        if (!md1.getName().getFullyQualifiedName().equals(md2.getName().getFullyQualifiedName())) {
            return false;
        }

        if (md1.parameters().size() != md2.parameters().size()) {
            return false;
        }

        for (int i = 0; i < md1.parameters().size(); ++i) {
            if (!AST_MATCHER.match((SingleVariableDeclaration) md1.parameters().get(i), md2.parameters().get(i))) {
                return false;
            }
        }

        return true;

    }

}
