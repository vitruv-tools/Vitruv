package tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tools.vitruv.domains.java.monitorededitor.changeclassification.events.AddSuperClassEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.AddSuperInterfaceEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.ChangedSuperTypesEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.RemoveSuperClassEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.RemoveSuperInterfaceEvent;
import tools.vitruv.domains.java.monitorededitor.javamodel2ast.JavaModel2AST;

public class ChangeSupertypeClassifier extends SingleNodeChangeClassifier {

    @Override
    protected List<? extends ChangeClassifyingEvent> classifyChange(IJavaElementDelta delta,
            CompilationUnit currentCompilationUnit, CompilationUnit oldCompilationUnit) {
        List<ChangedSuperTypesEvent> returns = new ArrayList<ChangedSuperTypesEvent>(1);
        IJavaElement element = delta.getElement();
        if (element.getElementType() == IJavaElement.TYPE && delta.getKind() == IJavaElementDelta.CHANGED) {
            TypeDeclaration changed = JavaModel2AST.getTypeDeclaration((IType) element, currentCompilationUnit);
            TypeDeclaration original = JavaModel2AST.getTypeDeclaration((IType) element, oldCompilationUnit);
            if (changed == null || original == null) // e.g. the case when they are anonymous
                                                     // classes
                return returns;
            returns.addAll(checkSuperTypes(original, changed));
        }
        return returns;
    }

    private List<ChangedSuperTypesEvent> checkSuperTypes(TypeDeclaration original, TypeDeclaration changed) {
        List<ChangedSuperTypesEvent> events = new ArrayList<ChangedSuperTypesEvent>(5);
        events.addAll(checkSuperClass(original, changed));
        events.addAll(checkSuperInterfaces(original, changed));
        return events;
    }

    private List<ChangedSuperTypesEvent> checkSuperClass(TypeDeclaration original, TypeDeclaration changed) {
        List<ChangedSuperTypesEvent> events = new ArrayList<ChangedSuperTypesEvent>(1);
        Type originalSuperClass = original.getSuperclassType();
        Type changedSuperClass = changed.getSuperclassType();
        if ((originalSuperClass == null) && (changedSuperClass != null)) {
            events.add(new AddSuperClassEvent(original, changedSuperClass));
        } else if ((originalSuperClass != null) && (changedSuperClass == null)) {
            events.add(new RemoveSuperClassEvent(original, originalSuperClass));
        } else if ((originalSuperClass != null) && (changedSuperClass != null)) {
            events.add(new AddSuperClassEvent(original, changedSuperClass));
            events.add(new RemoveSuperClassEvent(original, originalSuperClass));
        }
        return events;
    }

    @SuppressWarnings("unchecked")
	private List<ChangedSuperTypesEvent> checkSuperInterfaces(TypeDeclaration original, TypeDeclaration changed) {
        List<ChangedSuperTypesEvent> events = new ArrayList<ChangedSuperTypesEvent>(5);
        List<Object> originalSuperInterfaces = new LinkedList<Object>();
        originalSuperInterfaces.addAll(original.superInterfaceTypes());
        List<Object> changedSuperInterfaces = new LinkedList<Object>();
        changedSuperInterfaces.addAll(changed.superInterfaceTypes());

        filterOutSameTypes(originalSuperInterfaces, changedSuperInterfaces);
        events.addAll(handleRemainingOldSuperTypes(original, originalSuperInterfaces));
        events.addAll(handleRemainingNewSuperTypes(original, changedSuperInterfaces));
        return events;
    }

    private List<RemoveSuperInterfaceEvent> handleRemainingOldSuperTypes(TypeDeclaration baseType,
            List<Object> originalSuperTypes) {
        List<RemoveSuperInterfaceEvent> events = new ArrayList<RemoveSuperInterfaceEvent>(originalSuperTypes.size());
        for (Object o : originalSuperTypes) {
            events.add(new RemoveSuperInterfaceEvent(baseType, (Type) o));
        }
        return events;
    }

    private List<AddSuperInterfaceEvent> handleRemainingNewSuperTypes(TypeDeclaration baseType,
            List<Object> changedSuperTypes) {
        List<AddSuperInterfaceEvent> events = new ArrayList<AddSuperInterfaceEvent>(changedSuperTypes.size());
        for (Object o : changedSuperTypes) {
            events.add(new AddSuperInterfaceEvent(baseType, (Type) o));
        }
        return events;
    }

    private void filterOutSameTypes(List<Object> originalSuperTypes, List<Object> changedSuperTypes) {
        ListIterator<Object> originalSuperTypesIt = originalSuperTypes.listIterator();
        while (originalSuperTypesIt.hasNext()) {
            Type oSuperType = (Type) originalSuperTypesIt.next();
            ListIterator<Object> changedSuperTypesIt = changedSuperTypes.listIterator();
            while (changedSuperTypesIt.hasNext()) {
                if (oSuperType.subtreeMatch(AST_MATCHER, changedSuperTypesIt.next())) {
                    changedSuperTypesIt.remove();
                    originalSuperTypesIt.remove();
                }
            }
        }
    }

}
