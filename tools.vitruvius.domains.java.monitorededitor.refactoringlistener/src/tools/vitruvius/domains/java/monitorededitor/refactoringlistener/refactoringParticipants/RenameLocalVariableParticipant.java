package tools.vitruvius.domains.java.monitorededitor.refactoringlistener.refactoringParticipants;

import org.eclipse.jdt.core.ILocalVariable;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;

import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.RenameParameterEvent;
import tools.vitruvius.domains.java.monitorededitor.javamodel2ast.CompilationUnitUtil;
import tools.vitruvius.domains.java.monitorededitor.javamodel2ast.JavaModel2AST;

public class RenameLocalVariableParticipant extends RenameParticipantAdapter {

    @Override
    public ChangeClassifyingEvent classifyRefactoring(Object element, RenameArguments args) {
        if (!(element instanceof ILocalVariable))
            return null;
        
        ILocalVariable locVar = (ILocalVariable)element;
        if (!(locVar.getParent() instanceof IMethod)) {
            return null;
        }
        
        VariableDeclaration originalVariableDeclaration = JavaModel2AST.getParameterVariableDeclarationByName(locVar);
        CompilationUnit cu = (CompilationUnit)originalVariableDeclaration.getRoot();
        CompilationUnit cuModified = (CompilationUnit) ASTNode.copySubtree(originalVariableDeclaration.getAST(), cu);
        VariableDeclaration renamedVariableDeclaration = JavaModel2AST.getParameterVariableDeclarationByName(locVar, cuModified);
        renamedVariableDeclaration.getName().setIdentifier(args.getNewName());
        int line = CompilationUnitUtil.getLineNumberOfASTNode(originalVariableDeclaration);
        return new RenameParameterEvent(originalVariableDeclaration, renamedVariableDeclaration, line);
    }

}
