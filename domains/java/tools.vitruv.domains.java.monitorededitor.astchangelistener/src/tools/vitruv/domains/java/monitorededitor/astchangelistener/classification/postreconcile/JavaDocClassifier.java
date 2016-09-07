package tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postreconcile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTMatcher;
import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import tools.vitruv.domains.java.monitorededitor.changeclassification.events.AddJavaDocEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.ChangeJavaDocEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.JavaDocEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.RemoveJavaDocEvent;

public class JavaDocClassifier extends SingleNodeChangeClassifier {

    private final ASTMatcher astMatcher = new JavaDocIgnoringASTMatcher(true);

    @Override
    protected List<? extends JavaDocEvent> classifyChange(IJavaElementDelta delta,
            CompilationUnit currentCompilationUnit, CompilationUnit oldCompilationUnit) {
        return parseCommentLists(currentCompilationUnit, oldCompilationUnit);
    }

    private List<? extends JavaDocEvent> parseCommentLists(CompilationUnit currentCompilationUnit,
            CompilationUnit oldCompilationUnit) {
        List<JavaDocEvent> events = new LinkedList<JavaDocEvent>();
        if (oldCompilationUnit == null)
            return events;
        @SuppressWarnings("unchecked")
        List<Comment> currentComments = currentCompilationUnit.getCommentList();
        @SuppressWarnings("unchecked")
        List<Comment> oldComments = oldCompilationUnit.getCommentList();

        if ((currentComments == null || currentComments.size() == 0)
                && (oldComments == null || oldComments.size() == 0))
            return events;
        if ((currentComments == null || currentComments.size() == 0))
            return createRemoveJavaDocEventsForAll(oldComments);
        if ((oldComments == null || oldComments.size() == 0))
            return createAddJavaDocEventsForAll(currentComments);

        // diff both lists sequentially
        ListIterator<Comment> currentCommentsIt = currentComments.listIterator();
        ListIterator<Comment> oldCommentsIt = oldComments.listIterator();
        while (currentCommentsIt.hasNext() && oldCommentsIt.hasNext()) {
            Comment current = currentCommentsIt.next();
            if (!(current instanceof Javadoc))
                continue;
            Comment old = oldCommentsIt.next();
            if (!(old instanceof Javadoc)) {
                currentCommentsIt.previous();
                continue;
            }
            if (current.subtreeMatch(this.astMatcher, old))
                continue;
            if (current.getParent().getStartPosition() == old.getParent().getStartPosition())
                events.add(new ChangeJavaDocEvent((Javadoc) old, (Javadoc) current));
            else {
                if (current.getStartPosition() < old.getStartPosition()) {
                    oldCommentsIt.previous();
                    events.add(new AddJavaDocEvent((Javadoc) current));
                } else if (current.getStartPosition() > old.getStartPosition()) {
                    currentCommentsIt.previous();
                    events.add(new RemoveJavaDocEvent((Javadoc) old));
                }
            }
        }
        events.addAll(createAddJavaDocEventsForAll(currentComments.subList(currentCommentsIt.nextIndex(),
                currentComments.size())));
        events.addAll(createRemoveJavaDocEventsForAll(oldComments.subList(oldCommentsIt.nextIndex(), oldComments.size())));
        return events;
    }

    private List<? extends JavaDocEvent> createAddJavaDocEventsForAll(List<Comment> comments) {
        List<AddJavaDocEvent> adds = new ArrayList<AddJavaDocEvent>(comments.size());
        for (Comment c : comments) {
            if (!(c instanceof Javadoc))
                continue;
            adds.add(new AddJavaDocEvent((Javadoc) c));
        }
        return adds;
    }

    private List<? extends JavaDocEvent> createRemoveJavaDocEventsForAll(List<Comment> comments) {
        List<RemoveJavaDocEvent> removes = new ArrayList<RemoveJavaDocEvent>(comments.size());
        for (Comment c : comments) {
            if (!(c instanceof Javadoc))
                continue;
            removes.add(new RemoveJavaDocEvent((Javadoc) c));
        }
        return removes;
    }

    class JavaDocIgnoringASTMatcher extends ASTMatcher {
        public JavaDocIgnoringASTMatcher(boolean b) {
            super(true);
        }

        @SuppressWarnings("deprecation")
		@Override
        public boolean match(MethodDeclaration node, Object other) {
            if (!(other instanceof MethodDeclaration)) {
                return false;
            }
            MethodDeclaration o = (MethodDeclaration) other;
            int level = node.getAST().apiLevel();
            if (level >= AST.JLS3) {
                if (!safeSubtreeListMatch(node.modifiers(), o.modifiers())) {
                    return false;
                }
                if (!safeSubtreeMatch(node.getReturnType2(), o.getReturnType2())) {
                    return false;
                }
                // n.b. compare type parameters even for constructors
                if (!safeSubtreeListMatch(node.typeParameters(), o.typeParameters())) {
                    return false;
                }
            }
            return ((node.isConstructor() == o.isConstructor())
                    && safeSubtreeMatch(node.getName(), o.getName())
                    // n.b. compare return type even for constructors
                    && safeSubtreeListMatch(node.parameters(), o.parameters())
                    && node.getExtraDimensions() == o.getExtraDimensions()
                    && safeSubtreeListMatch(node.thrownExceptions(), o.thrownExceptions()) && safeSubtreeMatch(
                        node.getBody(), o.getBody()));
        }

        @SuppressWarnings("deprecation")
		@Override
        public boolean match(org.eclipse.jdt.core.dom.FieldDeclaration node, Object other) {
            if (!(other instanceof FieldDeclaration)) {
                return false;
            }
            FieldDeclaration o = (FieldDeclaration) other;
            int level = node.getAST().apiLevel();
            if (level == AST.JLS2) {
                if (node.getModifiers() != o.getModifiers()) {
                    return false;
                }
            }
            if (level >= AST.JLS3) {
                if (!safeSubtreeListMatch(node.modifiers(), o.modifiers())) {
                    return false;
                }
            }
            return safeSubtreeMatch(node.getType(), o.getType())
                    && safeSubtreeListMatch(node.fragments(), o.fragments());
        }
    }
}
