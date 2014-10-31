package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.classification;

import java.util.List;

import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.dom.ASTMatcher;
import org.eclipse.jdt.core.dom.CompilationUnit;

import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.PreviousASTState;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeClassifyingEvent;

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
