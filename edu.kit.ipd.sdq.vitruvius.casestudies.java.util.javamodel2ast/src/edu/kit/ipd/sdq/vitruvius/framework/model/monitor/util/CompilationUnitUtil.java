package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.util;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class CompilationUnitUtil {

    public static CompilationUnit parseCompilationUnit(IJavaElementDelta delta) {
        IJavaElement element = delta.getElement();
        if (!(element instanceof ICompilationUnit))
            return null;
        return JavaModel2AST.parseCompilationUnit((ICompilationUnit) element);
    }

    public static int getLineNumberOfMethod(IMethod imethod, String typeName, CompilationUnit unit) {
        MethodDeclaration methodDeclaration = JavaModel2AST.getMethodDeclaration(imethod, unit);
        if (methodDeclaration == null)
            return -1;
        int startPosition = methodDeclaration.getStartPosition();
        return unit.getLineNumber(startPosition);
    }

    public static int getLineNumberOfASTNode(ASTNode node) {
        if (node == null)
            return -1;
        ASTNode root = node.getRoot();
        if (!(root instanceof CompilationUnit))
            return -1;
        CompilationUnit unit = (CompilationUnit) root;
        return unit.getLineNumber(node.getStartPosition());
    }

    private static TypeDeclaration findTopLevelType(String typeName, CompilationUnit unit) {
        for (Object type : unit.types()) {
            if (!(type instanceof TypeDeclaration))
                continue;
            if (typeName.equals(((TypeDeclaration) type).getName().getIdentifier()))
                return (TypeDeclaration) type;
        }
        return null;
    }

    public static MethodDeclaration findMethodDeclarationOnLine(int line, CompilationUnit compilationUnit) {
        ASTNodeOnLineFinder visitor = new ASTNodeOnLineFinder(line, MethodDeclaration.class, compilationUnit);
        compilationUnit.accept(visitor);

        ASTNode found = visitor.getFoundASTNode();
        if ((found != null) && !(found instanceof MethodDeclaration))
            throw new IllegalStateException("Expected MethodDeclaration on line " + line + ", but found "
                    + found.getClass().getSimpleName() + " instead.");
        return (MethodDeclaration) found;
    }

    public static TypeDeclaration findTypeDeclarationOnLine(int line, CompilationUnit compilationUnit) {
        ASTNodeOnLineFinder visitor = new ASTNodeOnLineFinder(line, TypeDeclaration.class, compilationUnit);
        compilationUnit.accept(visitor);

        ASTNode found = visitor.getFoundASTNode();
        if ((found != null) && !(found instanceof TypeDeclaration))
            throw new IllegalStateException("Expected TypeDeclaration on line " + line + ", but found "
                    + found.getClass().getSimpleName() + " instead.");
        return (TypeDeclaration) found;
    }

}
