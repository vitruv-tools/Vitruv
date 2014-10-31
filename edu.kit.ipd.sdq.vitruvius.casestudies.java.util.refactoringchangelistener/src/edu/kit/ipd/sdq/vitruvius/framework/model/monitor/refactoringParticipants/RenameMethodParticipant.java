package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.refactoringParticipants;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;

import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeClassifyingEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.RenameMethodEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.util.CompilationUnitUtil;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.util.JavaModel2AST;

public class RenameMethodParticipant extends RenameParticipantAdapter {

    @Override
    public ChangeClassifyingEvent classifyRefactoring(Object element, RenameArguments args) {
        if (!(element instanceof IMethod))
            return null;
        MethodDeclaration original = JavaModel2AST.getMethodDeclaration((IMethod) element);
        CompilationUnit cu = (CompilationUnit) original.getRoot();
        CompilationUnit cuModified = (CompilationUnit) ASTNode.copySubtree(original.getAST(), cu);
        MethodDeclaration renamed = JavaModel2AST.getMethodDeclaration((IMethod) element, cuModified);
        renamed.getName().setIdentifier(args.getNewName());
        int line = CompilationUnitUtil.getLineNumberOfASTNode(original);
        return new RenameMethodEvent(original, renamed, line);
    }

}
