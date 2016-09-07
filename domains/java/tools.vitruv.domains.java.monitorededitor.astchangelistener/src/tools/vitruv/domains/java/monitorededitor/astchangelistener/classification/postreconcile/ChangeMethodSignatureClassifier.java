package tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tools.vitruv.domains.java.monitorededitor.astchangelistener.PreviousASTState;
import tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.ConcreteChangeClassifier;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.ChangeMethodSignatureEvent;
import tools.vitruv.domains.java.monitorededitor.javamodel2ast.CompilationUnitUtil;

/**
 * @author messinger
 * 
 *         Abstract superclass for {@link ConcreteChangeClassifier}s that identify method signature
 *         change events.
 * 
 */
public abstract class ChangeMethodSignatureClassifier implements ConcreteChangeClassifier {

    @Override
    public List<ChangeMethodSignatureEvent> match(IJavaElementDelta delta, CompilationUnit currentCompilationUnit,
            PreviousASTState previousState) {
        List<ChangeMethodSignatureEvent> renameEvents = new ArrayList<ChangeMethodSignatureEvent>();
        if (currentCompilationUnit == null)
            return renameEvents;
        CompilationUnit oldCompilationUnit = previousState.getOldCompilationUnit(currentCompilationUnit);

        for (@SuppressWarnings("rawtypes")
        Iterator types = currentCompilationUnit.types().iterator(); types.hasNext();) {
            Object t = types.next();
            if (!(t instanceof TypeDeclaration))
                continue;
            TypeDeclaration type = (TypeDeclaration) t;
            Queue<IJavaElementDelta> bfsQueue = new LinkedList<IJavaElementDelta>();
            bfsQueue.add(delta);

            while (!bfsQueue.isEmpty()) {
                IJavaElementDelta top = bfsQueue.remove();
                renameEvents.addAll(checkMethodChildrenOnSameLine(top.getAffectedChildren(), type,
                        currentCompilationUnit, oldCompilationUnit));
                for (IJavaElementDelta child : top.getAffectedChildren())
                    bfsQueue.add(child);
            }
        }
        return renameEvents;
    }

    // assumption: given a line there are max 2 methods on that line
    private List<ChangeMethodSignatureEvent> checkMethodChildrenOnSameLine(IJavaElementDelta[] children,
            TypeDeclaration type, CompilationUnit currentUnit, CompilationUnit oldUnit) {
        List<ChangeMethodSignatureEvent> changeEvents = new ArrayList<ChangeMethodSignatureEvent>();
        Map<Integer, IJavaElementDelta> methodsOnLine = new HashMap<Integer, IJavaElementDelta>();

        for (IJavaElementDelta childDelta : children) {
            IJavaElement element = childDelta.getElement();
            if (element.getElementType() == IJavaElement.METHOD) {
                IMethod imethod = (IMethod) element;
                IType itype = (IType) imethod.getParent();
                int line = getLineOfMethod(currentUnit, oldUnit, itype.getElementName().toString(), childDelta, imethod);
                addLineOrTriggerEventIfHit(changeEvents, methodsOnLine, childDelta, line, currentUnit, oldUnit);
            }
        }
        return changeEvents;
    }

    private void addLineOrTriggerEventIfHit(List<ChangeMethodSignatureEvent> changeEvents,
            Map<Integer, IJavaElementDelta> methods, IJavaElementDelta childDelta, int line,
            CompilationUnit currentUnit, CompilationUnit oldUnit) {
        if (methods.containsKey(line)) {
            ChangeMethodSignatureEvent event = classifyMethodChange(methods.get(line), childDelta, line, currentUnit,
                    oldUnit);
            if (event != null) {
                changeEvents.add(event);
            }
        } else {
            methods.put(line, childDelta);
        }
    }

    protected ChangeMethodSignatureEvent classifyMethodChange(IJavaElementDelta deltaMethod1,
            IJavaElementDelta deltaMethod2, int line, CompilationUnit currentUnit, CompilationUnit oldUnit) {

        MethodDeclaration original = null, changed = null;
        if (deltaMethod1.getKind() == IJavaElementDelta.REMOVED && deltaMethod2.getKind() == IJavaElementDelta.ADDED) {
            original = CompilationUnitUtil.findMethodDeclarationOnLine(line, oldUnit);
            changed = CompilationUnitUtil.findMethodDeclarationOnLine(line, currentUnit);
        } else if (deltaMethod2.getKind() == IJavaElementDelta.REMOVED
                && deltaMethod1.getKind() == IJavaElementDelta.ADDED) {
            changed = CompilationUnitUtil.findMethodDeclarationOnLine(line, currentUnit);
            original = CompilationUnitUtil.findMethodDeclarationOnLine(line, oldUnit);
        }
        return classifyChange(original, changed, line);
    }

    protected abstract ChangeMethodSignatureEvent classifyChange(MethodDeclaration original, MethodDeclaration changed,
            int line);

    private int getLineOfMethod(CompilationUnit currentUnit, CompilationUnit oldUnit, String typeName,
            IJavaElementDelta childDelta, IMethod imethod) {
        int line = -1;
        switch (childDelta.getKind()) {
        case IJavaElementDelta.ADDED:
            line = CompilationUnitUtil.getLineNumberOfMethod(imethod, typeName, currentUnit);
            break;
        case IJavaElementDelta.CHANGED:
            line = CompilationUnitUtil.getLineNumberOfMethod(imethod, typeName, currentUnit);
            break;
        case IJavaElementDelta.REMOVED:
            line = CompilationUnitUtil.getLineNumberOfMethod(imethod, typeName, oldUnit);
            break;
        default:
            break;
        }
        return line;
    }

}
