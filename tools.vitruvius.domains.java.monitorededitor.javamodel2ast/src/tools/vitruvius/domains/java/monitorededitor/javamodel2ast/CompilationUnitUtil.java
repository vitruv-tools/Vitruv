package tools.vitruvius.domains.java.monitorededitor.javamodel2ast;

import org.eclipse.jdt.core.IAnnotatable;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class CompilationUnitUtil {

    public static CompilationUnit parseCompilationUnit(final IJavaElementDelta delta) {
        final IJavaElement element = delta.getElement();
        if (!(element instanceof ICompilationUnit)) {
            return null;
        }
        return JavaModel2AST.parseCompilationUnit((ICompilationUnit) element);
    }

    public static int getLineNumberOfMethod(final IMethod imethod, final String typeName, final CompilationUnit unit) {
        final MethodDeclaration methodDeclaration = JavaModel2AST.getMethodDeclaration(imethod, unit);
        if (methodDeclaration == null) {
            return -1;
        }
        final int startPosition = methodDeclaration.getStartPosition();
        return unit.getLineNumber(startPosition);
    }

    public static int getLineNumberOfAnnotation(final IAnnotatable iAnnotatable, final CompilationUnit unit) {
        final BodyDeclaration bodyDeclaration = JavaModel2AST.getBodyDeclaration(iAnnotatable, unit);
        if (bodyDeclaration == null) {
            return -1;
        }
        final int startPosition = bodyDeclaration.getStartPosition();
        return unit.getLineNumber(startPosition);
    }

    public static int getLineNumberOfASTNode(final ASTNode node) {
        if (node == null) {
            return -1;
        }
        final ASTNode root = node.getRoot();
        if (!(root instanceof CompilationUnit)) {
            return -1;
        }
        final CompilationUnit unit = (CompilationUnit) root;
        return unit.getLineNumber(node.getStartPosition());
    }

//    private static TypeDeclaration findTopLevelType(final String typeName, final CompilationUnit unit) {
//        for (final Object type : unit.types()) {
//            if (!(type instanceof TypeDeclaration)) {
//                continue;
//            }
//            if (typeName.equals(((TypeDeclaration) type).getName().getIdentifier())) {
//                return (TypeDeclaration) type;
//            }
//        }
//        return null;
//    }

    public static BodyDeclaration findBodyDeclarationOnLine(final int line, final CompilationUnit compilationUnit) {
        final ASTNodeOnLineFinder visitor = new ASTNodeOnLineFinder(line, BodyDeclaration.class, compilationUnit);
        compilationUnit.accept(visitor);

        final ASTNode found = visitor.getFoundASTNode();
        if (found != null && !(found instanceof BodyDeclaration)) {
            throw new IllegalStateException("Expected MethodDeclaration on line " + line + ", but found "
                    + found.getClass().getSimpleName() + " instead.");
        }
        return (BodyDeclaration) found;
    }

    public static TypeDeclaration findTypeDeclarationOnLine(final int line, final CompilationUnit compilationUnit) {
        final ASTNodeOnLineFinder visitor = new ASTNodeOnLineFinder(line, TypeDeclaration.class, compilationUnit);
        compilationUnit.accept(visitor);

        final ASTNode found = visitor.getFoundASTNode();
        if (found != null && !(found instanceof TypeDeclaration)) {
            throw new IllegalStateException("Expected TypeDeclaration on line " + line + ", but found "
                    + found.getClass().getSimpleName() + " instead.");
        }
        return (TypeDeclaration) found;
    }

    public static MethodDeclaration findMethodDeclarationOnLine(final int line, final CompilationUnit compilationUnit) {
        final ASTNodeOnLineFinder visitor = new ASTNodeOnLineFinder(line, MethodDeclaration.class, compilationUnit);
        compilationUnit.accept(visitor);

        final ASTNode found = visitor.getFoundASTNode();
        if (found != null && !(found instanceof MethodDeclaration)) {
            throw new IllegalStateException("Expected MethodDeclaration on line " + line + ", but found "
                    + found.getClass().getSimpleName() + " instead.");
        }
        return (MethodDeclaration) found;
    }

}
