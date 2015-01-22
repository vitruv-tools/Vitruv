package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.refactoringParticipants;

import org.eclipse.jdt.core.ILocalVariable;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;

import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeClassifyingEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.RenameParameterEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.util.CompilationUnitUtil;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.util.JavaModel2AST;

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
