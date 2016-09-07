package tools.vitruv.domains.java.monitorededitor.astchangelistener.classification;

import java.util.List;

import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.dom.ASTMatcher;
import org.eclipse.jdt.core.dom.CompilationUnit;

import tools.vitruv.domains.java.monitorededitor.astchangelistener.PreviousASTState;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEvent;

/**
 * @author messinger
 * 
 *         A {@link ConcreteChangeClassifier} analyzes a delta in the JDT Java model together with
 *         the current AST and the old previous ASTs. It creates a list of
 *         {@link ChangeClassifyingEvent}s as result of the analysis.
 * 
 */
public interface ConcreteChangeClassifier {

    ASTMatcher AST_MATCHER = new ASTMatcher(true);

    List<? extends ChangeClassifyingEvent> match(IJavaElementDelta delta, CompilationUnit currentCompilationUnit,
            PreviousASTState previousState);

}
